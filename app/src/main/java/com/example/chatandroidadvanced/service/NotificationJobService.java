package com.example.chatandroidadvanced.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.chatandroidadvanced.R;
import com.example.chatandroidadvanced.model.Message;
import com.example.chatandroidadvanced.model.MessageRoomDatabase;
import com.example.chatandroidadvanced.model.MessageService;
import com.example.chatandroidadvanced.view.ChatActivity;
import com.example.chatandroidadvanced.view.ConversationActivity;
import com.example.chatandroidadvanced.viewmodel.MessageViewModel;
import com.example.chatandroidadvanced.viewmodel.ParticipantViewModel;
import com.example.chatandroidadvanced.viewmodel.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NotificationJobService extends JobService {

    private MessageViewModel mMessageViewModel;
    NotificationManager mNotifyManager;
    public static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";

    @Override
    public boolean onStartJob(JobParameters jobParameters) {

        JobScheduler scheduler = (JobScheduler) this.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        JobInfo job = new JobInfo.Builder(1,new ComponentName(this, NotificationJobService.class))
                .setMinimumLatency(5000)
                .build();
        scheduler.schedule(job);

        final RetrofitInstance retrofitInstance = new RetrofitInstance();

         final MessageService messageService = retrofitInstance.getMessageService();

        final Call<Integer> callMessage = messageService.countMessages();
        callMessage.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> callMessage, Response<Integer> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Something went wrong during creating user, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }

           /*     final Message mMessage = new Message("Hello World", "196", "198","337");
                MessageService messageService = retrofitInstance.getMessageService();
                final Call<Message> message = messageService.createMessage(mMessage);
                message.enqueue(new Callback<Message>() {
                    @Override
                    public void onResponse(Call<Message> call, Response<Message> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Something went wrong during creating user, please try again.", Toast.LENGTH_LONG).show();
                            return;
                        }



                    }

                    @Override
                    public void onFailure(Call<Message> call, Throwable t) {
                    }
                });*/


                Toast.makeText(getApplicationContext(),"Succes",Toast.LENGTH_LONG).show();
                Log.d("foom", response.body().toString());
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Fail",Toast.LENGTH_LONG).show();
                Log.d("foom", t.toString());
            }
        });



       /* createNotificationChannel();
        PendingIntent contentPendingIntent = PendingIntent.getActivity(this,
                0, new Intent(this, ConversationActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
                .setContentTitle("Android Chat App")
                .setContentText("New Message")
                .setContentIntent(contentPendingIntent)
                .setSmallIcon(R.drawable.ic_autorenew_black_24dp)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setAutoCancel(true);

        mNotifyManager.notify(0, builder.build());*/
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

    private void broadcastNotification(Context context){
        Intent intent = new Intent("NOTIFICATION");
        intent.putExtra("MESSAGE", "your_message");
        context.sendBroadcast(intent);
    }
}
