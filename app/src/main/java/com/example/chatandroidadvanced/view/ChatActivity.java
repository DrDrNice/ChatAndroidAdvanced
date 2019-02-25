package com.example.chatandroidadvanced.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chatandroidadvanced.R;
import com.example.chatandroidadvanced.model.Conversation;
import com.example.chatandroidadvanced.model.Message;
import com.example.chatandroidadvanced.viewmodel.MessageListAdapter;

import java.util.LinkedList;

public class ChatActivity extends AppCompatActivity {

    private Conversation conversation;
    private EditText inputText;

    private MessageListAdapter messageListAdapter;
    private LinkedList<Message> messageList = new LinkedList<>();;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbarChat);
        setSupportActionBar(toolbar);

        //todo get clicked contact from contactlistadapter and set it as toolbar header
        conversation = (Conversation)getIntent().getSerializableExtra("contact");
        //String firstName = conversation.getFirstName();
        //String lastName = conversation.getLastName();
        //toolbar.setTitle(firstName + " " + lastName);
        toolbar.setTitle(conversation.getCreatedBy());

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
        messageList.addLast(
                new Message(inputText.getText().toString(), conversation.getId(), conversation.getCreatedBy(),
                        conversation.getLastModifiedBy(), /*user.getid*/"1234", "789"));
        inputText.setText("");
        messageListAdapter = new MessageListAdapter(this, messageList);
        recyclerView.setAdapter(messageListAdapter);

    }
}
