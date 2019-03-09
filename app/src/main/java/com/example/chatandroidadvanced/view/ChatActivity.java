package com.example.chatandroidadvanced.view;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.chatandroidadvanced.R;
import com.example.chatandroidadvanced.model.Conversation;
import com.example.chatandroidadvanced.model.ConversationService;
import com.example.chatandroidadvanced.model.Message;
import com.example.chatandroidadvanced.model.MessageService;
import com.example.chatandroidadvanced.model.Participant;
import com.example.chatandroidadvanced.service.NotificationJobService;
import com.example.chatandroidadvanced.viewmodel.ConversationViewModel;
import com.example.chatandroidadvanced.viewmodel.MessageListAdapter;
import com.example.chatandroidadvanced.viewmodel.MessageViewModel;
import com.example.chatandroidadvanced.viewmodel.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.chatandroidadvanced.view.MainActivity.MY_PREFERENCES;

public class ChatActivity extends AppCompatActivity{

    private Conversation mConversation;
    private EditText inputText;
    private RecyclerView recyclerView;
    private String mReceiverID;
    private String mSenderId;
    private SharedPreferences preferences;
    private String mFirstName;
    private String mLastName;
    private String mEMail;
    private ScrollView scrollView;
    private Handler mHandler;

    private MessageViewModel mMessageViewModel;
    private ConversationViewModel mConversationViewModel;

    public static MessageListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarChat);
        setSupportActionBar(toolbar);



        preferences = getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE);
        mSenderId = preferences.getString(MainActivity.ID, "");


        //todo get clicked contact from contactlistadapter and set it as toolbar header
        if (getIntent().getExtras() != null) {
            if (getIntent().getIntExtra("CODE", 0) == 1) {
                Conversation conversation = (Conversation) getIntent().getSerializableExtra("Conversation");
                mFirstName = conversation.getFirstName();
                mLastName = conversation.getLastName();
                mReceiverID = conversation.getReceiverId();
                mEMail = conversation.getEmail();
                toolbar.setTitle(mFirstName + " " + mLastName);
            } else {
                Participant participant = (Participant) getIntent().getSerializableExtra("contact");
                mFirstName = participant.getfirstName();
                mLastName = participant.getlastName();
                mReceiverID = participant.getIDServer();
                mEMail = participant.getEmail();
                toolbar.setTitle(mFirstName + " " + mLastName);
            }
        }

        inputText = (EditText) findViewById(R.id.chatInputText);
        recyclerView = (RecyclerView) findViewById(R.id.rcvChat);

        adapter = new MessageListAdapter(this, preferences);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        mMessageViewModel = ViewModelProviders.of(this).get(MessageViewModel.class);
        mConversationViewModel = ViewModelProviders.of(this).get(ConversationViewModel.class);

        mMessageViewModel.getAllMessagesbyRecSendID(Integer.valueOf(mSenderId), Integer.valueOf(mReceiverID)).observe(this, new Observer<List<Message>>() {
            @Override
            public void onChanged(@Nullable List<Message> messages) {
                //sets the adapter to the recyclerview and keeps all updated
                Log.d("OnChangedLiveData", "ChangedLiveData");
                adapter.setmMessages(messages);
               recyclerView.smoothScrollToPosition(adapter.getItemCount());
            }
        });


     /*   ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder,
                                         int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Message myMessage = adapter.getMessageAtPosition(position);
                        Toast.makeText(ChatActivity.this, "Deleting " +
                                myMessage.getContent(), Toast.LENGTH_LONG).show();

                        // Delete participant from room
                        mMessageViewModel.deleteMessage(myMessage);

                        //delete participant from database
                        RetrofitInstance retrofitInstance = new RetrofitInstance();
                        retrofitInstance.deleteMessage(Integer.valueOf(myMessage.getId()));
                    }
                });

        helper.attachToRecyclerView(recyclerView);


    /*   RetrofitInstance retrofitInstance = new RetrofitInstance();
        MessageService messageService = retrofitInstance.getMessageService();
        final Call<List<Message>> callMessage = messageService.getAllMessages();
        callMessage.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> callMessage, Response<List<Message>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Something went wrong during creating user, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(getApplicationContext(),"Succes",Toast.LENGTH_LONG).show();
                Log.d("foom", response.body().toString());

            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Fail",Toast.LENGTH_LONG).show();
                Log.d("foom", t.toString());
            }
        });

*/
      JobScheduler scheduler = (JobScheduler) this.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        JobInfo job = new JobInfo.Builder(1,new ComponentName(this, NotificationJobService.class))
                .setMinimumLatency(5000)
                .build();
        scheduler.schedule(job);

/*
        //Checks Network state
        final ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            Toast.makeText(this,"NetworkConnect", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this,"Network Disconnect", Toast.LENGTH_SHORT).show();
        }
*/
        this.mHandler = new Handler();
        mRunnable.run();
       /* recyclerView.getAdapter().notifyDataSetChanged();
        int nrMessages = recyclerView.getAdapter().getItemCount();
        int nrMessage = recyclerView.getAdapter().getItemCount();
        Log.d("Heureka",String.valueOf(nrMessages) + " " + String.valueOf(nrMessage));
        recyclerView.smoothScrollToPosition(adapter.getItemCount() );*/


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.btnBackToConversations) {
            Toast.makeText(getApplicationContext(), "Back to conversations!", Toast.LENGTH_LONG).show();
            Intent intentConversations = new Intent(this, ConversationActivity.class);
            startActivity(intentConversations);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //ToDo JobSchedular
    public void sendText(View view) {

        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        final String message = inputText.getText().toString();
        mConversation = new Conversation(message, "");


        inputText.setText("");
        final RetrofitInstance retrofitInstance = new RetrofitInstance();

        int nrMessages = recyclerView.getAdapter().getItemCount()+1;
        Log.d("Heureka",String.valueOf(nrMessages));

        if(recyclerView.getAdapter().getItemCount()+1 > 1 ) {
            addMessage(new Message(message, mReceiverID, mSenderId, ""));
            Message test = adapter.getMessageAtPosition(0);
            Log.d("Heurekainside", String.valueOf(test.getConversationId()));
            Message mMessage = new Message(message, mReceiverID, mSenderId, String.valueOf(test.getConversationId()));
            MessageService messageService = retrofitInstance.getMessageService();
            Call<Message> callMessage = messageService.createMessage(mMessage);
            callMessage.enqueue(new Callback<Message>() {
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
            });

        }else {
            addMessage(new Message(message, mReceiverID, mSenderId, ""));
            ConversationService conversationService = retrofitInstance.getConversationService();
            Call<Conversation> call = conversationService.createConversation(mConversation);
            call.enqueue(new Callback<Conversation>() {
                @Override
                public void onResponse(Call<Conversation> call, Response<Conversation> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Something went wrong during creating user, please try again.", Toast.LENGTH_LONG).show();
                        return;
                    }

                    Conversation conversation = new Conversation(message, response.body().getId(), mSenderId, mReceiverID, mEMail, message, mFirstName, mLastName);
                    mConversationViewModel.insert(conversation);

                    Message mMessage = new Message(message, mReceiverID, mSenderId, response.body().getId());
                    MessageService messageService = retrofitInstance.getMessageService();
                    Call<Message> callMessage = messageService.createMessage(mMessage);
                    callMessage.enqueue(new Callback<Message>() {
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
                    });
                }

                @Override
                public void onFailure(Call<Conversation> call, Throwable t) {
                    Log.d("foofail", t.toString());
                    Toast.makeText(getApplicationContext(), "Something went wrong during creating user, please try again.", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void addMessage(Message message) {
        adapter.addMessage(message);
        recyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);
    }

    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            ChatActivity.this.mHandler.postDelayed(mRunnable, 4000);
            RetrofitInstance retrofitInstance = new RetrofitInstance();
            retrofitInstance.getAllMessages(getApplicationContext(), mMessageViewModel, mConversationViewModel, mReceiverID);
        }
    };

}
