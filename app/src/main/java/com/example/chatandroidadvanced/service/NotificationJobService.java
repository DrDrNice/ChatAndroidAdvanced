package com.example.chatandroidadvanced.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.chatandroidadvanced.R;
import com.example.chatandroidadvanced.model.Conversation;
import com.example.chatandroidadvanced.model.Message;
import com.example.chatandroidadvanced.model.MessageRoomDatabase;
import com.example.chatandroidadvanced.model.MessageService;
import com.example.chatandroidadvanced.model.Participant;
import com.example.chatandroidadvanced.model.ParticipantService;
import com.example.chatandroidadvanced.view.ChatActivity;
import com.example.chatandroidadvanced.view.ConversationActivity;
import com.example.chatandroidadvanced.view.MainActivity;
import com.example.chatandroidadvanced.viewmodel.ConversationViewModel;
import com.example.chatandroidadvanced.viewmodel.MessageViewModel;
import com.example.chatandroidadvanced.viewmodel.ParticipantViewModel;
import com.example.chatandroidadvanced.viewmodel.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.chatandroidadvanced.view.MainActivity.MY_PREFERENCES;


public class NotificationJobService extends JobService {

    private MessageViewModel mMessageViewModel;
    NotificationManager mNotifyManager;
    public static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    private SharedPreferences preferences;
    private String mReciverId;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {

        preferences = getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE);
        mReciverId = preferences.getString(MainActivity.ID, "");
        JobScheduler scheduler = (JobScheduler) this.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        JobInfo job = new JobInfo.Builder(1, new ComponentName(this, NotificationJobService.class))
                .setMinimumLatency(1000)
                .build();
        scheduler.schedule(job);

        final RetrofitInstance retrofitInstance = new RetrofitInstance();


        final MessageViewModel mMessageViewModel = new MessageViewModel(getApplication());

        //Fetch Room for Saved Messages
        mMessageViewModel.getAllMessagesByReciverID(Integer.valueOf(mReciverId)).observeForever(new Observer<List<Message>>() {
            @Override
            public void onChanged(@Nullable List<Message> messages) {

                final int size = messages.size();
                final List<Message> rommMessages = messages;
                //--------- FETCH SERVER for saved messages
                MessageService mMessageService = retrofitInstance.getMessageService();
                Call<List<Message>> call = mMessageService.getAllMessagesbyreceiverID(Integer.valueOf(mReciverId), 0, 1000);
                call.enqueue(new Callback<List<Message>>() {
                    @Override
                    public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                        if (!response.isSuccessful()) {
                            return;
                        }
                        final List<Message> posts = response.body();
                        for (int i = 0; i < posts.size(); i++) {
                        }
                        if (posts.size() > size) {
                            int sizeMin = Math.min(posts.size(), size);
                            int sizeMax = Math.max(posts.size(), size);
                            for (int i = sizeMin; i < sizeMax; i++) {

                                //Send Notification
                                createNotificationChannel();
                                createPending();

                                mMessageViewModel.insert(posts.get(i));
                                final Message messageTemp = posts.get(i);
                                final ConversationViewModel mConversationViewModel = new ConversationViewModel(getApplication());

                                //Fetch Conversation Room
                                mConversationViewModel.getAllConversations().observeForever(new Observer<List<Conversation>>() {
                                    @Override
                                    public void onChanged(@Nullable List<Conversation> conversations) {
                                        int counterTemp = 0;
                                        for (final Conversation conv : conversations) {
                                            if (!conv.getId().equals(messageTemp.getConversationId())) {
                                                counterTemp++;
                                            }
                                        }
                                        if (counterTemp == conversations.size()) {
                                            //Fetch from Server information about sender of the message
                                            ParticipantService participantService = retrofitInstance.getParticipantService();
                                            Call<Participant> call = participantService.getParticipant(Integer.valueOf(messageTemp.getSenderId()));
                                            call.enqueue(new Callback<Participant>() {

                                                @Override
                                                public void onResponse(Call<Participant> call, Response<Participant> response) {
                                                    Participant p = response.body();
                                                    mConversationViewModel.insert(new Conversation(messageTemp.getContent()
                                                            , messageTemp.getConversationId(), messageTemp.getReceiverId()
                                                            , messageTemp.getSenderId(), p.getEmail(), messageTemp.getContent(), p.getfirstName(), p.getlastName()));
                                                }

                                                @Override
                                                public void onFailure(Call<Participant> call, Throwable t) {

                                                }
                                            });
                                        }
                                    }

                                });
                            }
                        }


                    }

                    @Override
                    public void onFailure(Call<List<Message>> call, Throwable t) {
                        Log.d("get Message failed", t.toString());
                    }
                });
            }
        });
        stopSelf();
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }


    public void createNotificationChannel() {

        mNotifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID,
                    "Job Service notification",
                    NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notification from Job Service");

            mNotifyManager.createNotificationChannel(notificationChannel);

        }

    }

    public void createPending() {
        PendingIntent contentPendingIntent = PendingIntent.getActivity(this,
                0, new Intent(this, MainActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
                .setContentTitle("Android Chat App")
                .setContentText("New Message")
                .setContentIntent(contentPendingIntent)
                .setSmallIcon(R.drawable.ic_message_notification)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setAutoCancel(true);

        mNotifyManager.notify(0, builder.build());

    }
}
