package com.example.chatandroidadvanced.view;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chatandroidadvanced.R;
import com.example.chatandroidadvanced.model.Conversation;
import com.example.chatandroidadvanced.model.ConversationService;
import com.example.chatandroidadvanced.model.Message;
import com.example.chatandroidadvanced.model.MessageService;
import com.example.chatandroidadvanced.model.Participant;
import com.example.chatandroidadvanced.viewmodel.ConversationViewModel;
import com.example.chatandroidadvanced.viewmodel.MessageListAdapter;
import com.example.chatandroidadvanced.viewmodel.MessageViewModel;
import com.example.chatandroidadvanced.viewmodel.RetrofitInstance;

import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.chatandroidadvanced.view.MainActivity.MY_PREFERENCES;

public class ChatActivity extends AppCompatActivity {

    private Conversation mConversation;
    private EditText inputText;

    // private MessageListAdapter messageListAdapter;
    // private LinkedList<Message> messageList = new LinkedList<>();
    ;

    private RecyclerView recyclerView;
    private String mReciverID;
    private String mSenderId;
    private String mConversationId;
    private SharedPreferences preferences;
    private String mFirstName;
    private String mLastName;
    private String mEMail;
    private RetrofitInstance retrofitInstance;
    private MessageViewModel mMessageViewModel;
    private ConversationViewModel mConversationViewModel;

  private boolean newMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        retrofitInstance = new RetrofitInstance();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarChat);
        setSupportActionBar(toolbar);
        preferences = getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE);
        mSenderId = preferences.getString(MainActivity.ID, "");
        Log.d("fooExtra", mSenderId);
        newMessage = true;
        //todo get clicked contact from contactlistadapter and set it as toolbar header
        if (getIntent().getExtras() != null) {
            Participant participant = (Participant) getIntent().getSerializableExtra("contact");
            mFirstName = participant.getfirstName();
            mLastName = participant.getlastName();
            mReciverID = participant.getIDServer();
            mEMail = participant.getEmail();
            Log.d("fooExtra", mReciverID);
            Log.d("fooExtra", participant.getfirstName());
            //toolbar.setTitle(firstName + " " + lastName);
            //toolbar.setTitle(conversation.getCreatedBy());
            //mConversation = new Conversation(firstName + " / " + lastName, "");
        }

        inputText = (EditText) findViewById(R.id.chatInputText);

        recyclerView = (RecyclerView) findViewById(R.id.rcvChat);
        final MessageListAdapter adapter = new MessageListAdapter(this, preferences);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

       /* recyclerView = (RecyclerView)findViewById(R.id.rcvChat);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));*/

        // retrofitInstance.getAllConversations(getApplicationContext(), mConversationViewModel);
        //ToDo get all messages by ID
        mMessageViewModel = ViewModelProviders.of(this).get(MessageViewModel.class);

        mConversationViewModel = ViewModelProviders.of(this).get(ConversationViewModel.class);


        //ToDo delteAll ist nicht richtig.
        //  mMessageViewModel.deleteAll();
        // retrofitInstance.getAllMessagesByReciverId(getApplicationContext(), mMessageViewModel,Integer.valueOf(mReciverID),"192");
        retrofitInstance.getAllMessages(getApplicationContext(), mMessageViewModel);
        //   mMessageViewModel.getAllMessages().observe(this, new Observer<List<Message>>() {

        LiveData<List<Message>> messageList = mMessageViewModel.getAllMessages();
        if(messageList == null) {
            Log.d("foomm", "messages null");
        }


        //ToDO convID
      //  mMessageViewModel.getAllMessagesbyID(Integer.valueOf(mSenderId), 192).observe(this, new Observer<List<Message>>() {
      mMessageViewModel.getAllMessagesbyRecSendID(Integer.valueOf(mSenderId), Integer.valueOf(mReciverID)).observe(this, new Observer<List<Message>>() {
            @Override
            public void onChanged(@Nullable List<Message> messages) {
                //sets the adapter to the recyclerview and keeps all updated
                adapter.setmMessages(messages);
            }
        });
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

    public void sendText(View view) {
        //Todo send text when click on send happens push it to the server
        //todo method that data keeps updated and gets called when message is received
        //messageList.addLast(
        //        new Message(inputText.getText().toString(), conversation.getId(), conversation.getFirstName() + " " + conversation.getLastName(),
        //                conversation.getFirstName() + " " + conversation.getLastName(), /*user.getid*/"1234", "789"));
      /*  messageList.addLast(
                new Message(inputText.getText().toString(), conversation.getId(), conversation.getCreatedBy(),
                        conversation.getLastModifiedBy(),   /*user.getid*/  /*"1234", "789"));*/
        final String message = inputText.getText().toString();
        //Message mMessage = new Message(message, mReciverID, mSenderId, "1");
        //mMessageViewModel.insert(mMessage);
        mConversation = new Conversation(message, "");
        final RetrofitInstance retrofitInstance = new RetrofitInstance();
        ConversationService conversationService = retrofitInstance.getConversationService();
        Call<Conversation> call = conversationService.createConversation(mConversation);
        call.enqueue(new Callback<Conversation>() {
            @Override
            public void onResponse(Call<Conversation> call, Response<Conversation> response) {
                if (!response.isSuccessful()) {
                    Log.d("fooRes", String.valueOf(response.code()));
                    Toast.makeText(getApplicationContext(), "Something went wrong during creating user, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }

                Log.d("foog", response.body().getId());
                Conversation conversation = new Conversation(message, response.body().getId(), mSenderId, mReciverID, mEMail, message, mFirstName, mLastName);
                mConversationViewModel.insert(conversation);
                //public Message(String content, String receiverId, String senderId, String conversationId)

                //Message mMessage = new Message(message, mReciverID, mSenderId, mConversationId);

                // ToDO convID ändern
                Message mMessage = new Message(message, mReciverID, mSenderId, String.valueOf(192));
                MessageService messageService = retrofitInstance.getMessageService();
                Call<Message> callMessage = messageService.createMessage(mMessage);

                mMessageViewModel.insert(mMessage);


                callMessage.enqueue(new Callback<Message>() {
                    @Override
                    public void onResponse(Call<Message> call, Response<Message> response) {
                        if (!response.isSuccessful()) {
                            Log.d("fooRes", String.valueOf(response.code()));
                            Toast.makeText(getApplicationContext(), "Something went wrong during creating user, please try again.", Toast.LENGTH_LONG).show();
                            return;
                        }
                    }

                    @Override
                    public void onFailure(Call<Message> call, Throwable t) {
                        Log.d("foofail", t.toString());
                    }
                });
                //start conversation activity
                // Intent intentConversations = new Intent(getApplicationContext(), ConversationActivity.class);
                //startActivity(intentConversations);
                //finish();
            }

            @Override
            public void onFailure(Call<Conversation> call, Throwable t) {
                Log.d("foofail", t.toString());
                Toast.makeText(getApplicationContext(), "Something went wrong during creating user, please try again.", Toast.LENGTH_LONG).show();
            }
        });


     /*   inputText.setText("");
        //  messageListAdapter = new MessageListAdapter(this, messageList);
        recyclerView.setAdapter(messageListAdapter);*/

    }
}
