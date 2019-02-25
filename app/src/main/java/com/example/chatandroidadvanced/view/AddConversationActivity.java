package com.example.chatandroidadvanced.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.chatandroidadvanced.model.Conversation;
import com.example.chatandroidadvanced.viewmodel.ContactListAdapter;
import com.example.chatandroidadvanced.R;

import java.util.LinkedList;

public class AddConversationActivity extends AppCompatActivity {

    private RecyclerView recyclerview;
    private ContactListAdapter contactListAdapter;
    private final LinkedList<Conversation> contactList = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_conversation);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbarNewConversation);
        setSupportActionBar(toolbar);

        recyclerview = findViewById(R.id.rcvContacts);
        contactListAdapter = new ContactListAdapter(this, contactList);
        recyclerview.setAdapter(contactListAdapter);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));

        //Todo get list of contacts from service with get method
        //contactList = get....;

        //todo delete this example list
        //only for testing self defined fix list.
        int wordListSize = contactList.size();
        contactList.addLast(new Conversation("simon rayn", "simon rayn", "mip mip"));
        contactList.addLast(new Conversation("julia rayn", "julia rayn", "fasel fasel"));
        recyclerview.getAdapter().notifyItemInserted(wordListSize);
        recyclerview.smoothScrollToPosition(wordListSize);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_conversation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.btnCancelAddConversation){
            //cancel activity of creating new conversation and go back to list of conversations
            Toast.makeText(getApplicationContext() ,"Cancel create conversation!", Toast.LENGTH_LONG).show();
            Intent intentConversations = new Intent(this, ConversationActivity.class);
            startActivity(intentConversations);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
