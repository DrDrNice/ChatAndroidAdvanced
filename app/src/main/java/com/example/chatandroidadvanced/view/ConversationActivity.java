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

import com.example.chatandroidadvanced.R;
import com.example.chatandroidadvanced.model.Conversation;
import com.example.chatandroidadvanced.viewmodel.ConversationListAdapter;

import java.util.LinkedList;

public class ConversationActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ConversationListAdapter conversationListAdapter;
    private LinkedList<Conversation> conversationList = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbarConversations);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.rcvConversations);
        conversationListAdapter = new ConversationListAdapter(this, conversationList);
        recyclerView.setAdapter(conversationListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Todo get list of conversations from server with get method should be done everytime this activity is started to get actual list
        //conversationslist = get....;

        //todo delete this example list
        //only for testing self defined fix list.
        int wordListSize = conversationList.size();
        conversationList.addLast(new Conversation("jim rogers", "jim rogers", "bla bla bla"));
        conversationList.addLast(new Conversation("jim sanders", "jim sanders", "keine ahnung"));
        recyclerView.getAdapter().notifyItemInserted(wordListSize);
        recyclerView.smoothScrollToPosition(wordListSize);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_conversation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.btnAddConversation){
            Toast.makeText(getApplicationContext() ,"Add new conversation!", Toast.LENGTH_LONG).show();
            //create new conversation if button is clicked
            Intent intentNewConversation = new Intent(this, AddConversationActivity.class);
            startActivity(intentNewConversation);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
