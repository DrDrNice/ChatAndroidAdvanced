package com.example.chatandroidadvanced.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.chatandroidadvanced.R;
import com.example.chatandroidadvanced.model.Conversation;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbarChat);
        setSupportActionBar(toolbar);

        //get clicked contact from contactlistadapter and set it as toolbar header
        Conversation conversation = (Conversation)getIntent().getSerializableExtra("contact");
        String firstName = conversation.getFirstName();
        String lastName = conversation.getLastName();
        toolbar.setTitle(firstName + " " + lastName);
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
            //TODO leafe chat and go back to conversations
            Toast.makeText(getApplicationContext() ,"Back to conversations!", Toast.LENGTH_LONG).show();
            Intent intentConversations = new Intent(this, ConversationActivity.class);
            startActivity(intentConversations);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void sendText(View view) {
        //Todo send text when click on send happens
    }
}
