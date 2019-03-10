package com.example.chatandroidadvanced.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.chatandroidadvanced.R;
import com.example.chatandroidadvanced.model.Conversation;
import com.example.chatandroidadvanced.model.ConversationService;
import com.example.chatandroidadvanced.model.GlideApp;
import com.example.chatandroidadvanced.model.Message;
import com.example.chatandroidadvanced.model.MessageService;
import com.example.chatandroidadvanced.model.Participant;
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


    private EditText inputText;
    private RecyclerView recyclerView;
    private String mReceiverID;
    private String mSenderId;
    private SharedPreferences preferences;
    private String mFirstName;
    private String mLastName;
    private String mEMail;
   private ImageView toolbarImage;
    //private Handler mHandler;

    private MessageViewModel mMessageViewModel;
    private ConversationViewModel mConversationViewModel;

    public static MessageListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarChat);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        

toolbarImage = findViewById(R.id.toolbarImage);
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


        String image = "https://robohash.org/" + mEMail;
        GlideApp.with(getApplicationContext())
                .load(image)
                .placeholder(R.drawable.ic_loading_image)
                .error(R.drawable.ic_loading_error)
                .into(toolbarImage);
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

                RetrofitInstance retrofitInstance = new RetrofitInstance();
                adapter.setmMessages(messages);
                retrofitInstance.getAllMessages(getApplicationContext(), mMessageViewModel, mConversationViewModel, mReceiverID);

               recyclerView.smoothScrollToPosition(adapter.getItemCount());

            }
        });

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
        /* this.mHandler = new Handler();
        mRunnable.run();*/
    }





    //ToDo JobSchedular
    public void sendText(View view) {

        final ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {


        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        final String message = inputText.getText().toString();
        final Conversation  mConversation = new Conversation("er", "");


        inputText.setText("");
        final RetrofitInstance retrofitInstance = new RetrofitInstance();

        int nrMessages = recyclerView.getAdapter().getItemCount()+1;
        Log.d("Heureka",String.valueOf(nrMessages));


        retrofitInstance.getAllMessages(getApplicationContext(), mMessageViewModel, mConversationViewModel, mReceiverID);

        if(recyclerView.getAdapter().getItemCount() > 0 ) {
            Toast.makeText(getApplicationContext(),"Iemcount: "+ String.valueOf(recyclerView.getAdapter().getItemCount()), Toast.LENGTH_SHORT).show();
            Message test = adapter.getMessageAtPosition(0);
            Toast.makeText(getApplicationContext(),"Adapter: "+test.getConversationId() , Toast.LENGTH_LONG).show();
            addMessage(new Message(message, mReceiverID, mSenderId, test.getConversationId()));
            Log.d("Heurekainside", String.valueOf(test.getConversationId()));

            Message mMessage = new Message(message, mReceiverID, mSenderId, String.valueOf(test.getConversationId()));
            MessageService messageService = retrofitInstance.getMessageService();
            Call<Message> callMessage = messageService.createMessage(mMessage);
            callMessage.enqueue(new Callback<Message>() {
                @Override
                public void onResponse(Call<Message> call, Response<Message> response) {//ToDO response to list
                    if (!response.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Something went wrong during creating user, please try again.", Toast.LENGTH_LONG).show();
                        return;

                    }

                   // adapter.addMessage(response.body());
                   Toast.makeText(getApplicationContext(), "1", Toast.LENGTH_LONG).show();

                }

                @Override
                public void onFailure(Call<Message> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "2", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(getApplicationContext(),"id: " + response.body().getId() , Toast.LENGTH_LONG).show();
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
                            Toast.makeText(getApplicationContext(), "3", Toast.LENGTH_LONG).show();
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
        } else {
            Toast.makeText(this,"Network Disconnect", Toast.LENGTH_SHORT).show();
        }
    }

    private void addMessage(Message message) {
        adapter.addMessage(message);
        recyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);
    }

    /*private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            ChatActivity.this.mHandler.postDelayed(mRunnable, 1000);
            RetrofitInstance retrofitInstance = new RetrofitInstance();
            retrofitInstance.getAllMessages(getApplicationContext(), mMessageViewModel, mConversationViewModel, mReceiverID);
        }
    };*/

}
