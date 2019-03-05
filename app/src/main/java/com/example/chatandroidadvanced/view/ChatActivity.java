package com.example.chatandroidadvanced.view;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.chatandroidadvanced.model.ParticipantService;
import com.example.chatandroidadvanced.viewmodel.MessageListAdapter;
import com.example.chatandroidadvanced.viewmodel.RetrofitInstance;

import java.util.LinkedList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.chatandroidadvanced.view.MainActivity.MY_PREFERENCES;

public class ChatActivity extends AppCompatActivity {

    private Conversation mConversation;
    private EditText inputText;

    private MessageListAdapter messageListAdapter;
    private LinkedList<Message> messageList = new LinkedList<>();;

    private RecyclerView recyclerView;
    private String mReciverID;
    private String mSenderId;
    private String mConversationId;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbarChat);
        setSupportActionBar(toolbar);
        preferences = getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE);
        mSenderId = preferences.getString(MainActivity.ID,"");
        Log.d("fooExtra" , mSenderId);

        //todo get clicked contact from contactlistadapter and set it as toolbar header
        if(getIntent().getExtras() != null) {
          Participant  participant = (Participant) getIntent().getSerializableExtra("contact");
            String firstName = participant.getfirstName();
            String lastName = participant.getlastName();
            mReciverID = participant.getIDServer();
            Log.d("fooExtra" , mReciverID);
            Log.d("fooExtra" , participant.getfirstName());
            //toolbar.setTitle(firstName + " " + lastName);
            //toolbar.setTitle(conversation.getCreatedBy());
            mConversation = new Conversation(firstName + " / " + lastName, "" );
        }

        inputText = (EditText)findViewById(R.id.chatInputText);

        recyclerView = (RecyclerView)findViewById(R.id.rcvChat);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.btnBackToConversations){
            Toast.makeText(getApplicationContext() ,"Back to conversations!", Toast.LENGTH_LONG).show();
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

        final RetrofitInstance retrofitInstance = new RetrofitInstance();
        ConversationService conversationService = retrofitInstance.getConversationService();
        Call<Conversation> call = conversationService.createConversation(mConversation);
        call.enqueue(new Callback<Conversation>() {
            @Override
            public void onResponse(Call<Conversation> call, Response<Conversation> response) {
                if (!response.isSuccessful()) {
                    Log.d("fooRes", String.valueOf(response.code()));
                    Toast.makeText(getApplicationContext() ,"Something went wrong during creating user, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }

                mConversationId = response.body().getId();
 //public Message(String content, String receiverId, String senderId, String conversationId)
                Message mMessage = new Message(inputText.getText().toString(),mReciverID,mSenderId,mConversationId);
                MessageService messageService = retrofitInstance.getMessageService();
                Call<Message> callMessage = messageService.createMessage(mMessage);
                callMessage.enqueue(new Callback<Message>() {
                    @Override
                    public void onResponse(Call<Message> call, Response<Message> response) {
                        if (!response.isSuccessful()) {
                            Log.d("fooRes", String.valueOf(response.code()));
                            Toast.makeText(getApplicationContext() ,"Something went wrong during creating user, please try again.", Toast.LENGTH_LONG).show();
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
                Toast.makeText(getApplicationContext() ,"Something went wrong during creating user, please try again.", Toast.LENGTH_LONG).show();
            }
        });



        inputText.setText("");
        messageListAdapter = new MessageListAdapter(this, messageList);
        recyclerView.setAdapter(messageListAdapter);

    }
}
