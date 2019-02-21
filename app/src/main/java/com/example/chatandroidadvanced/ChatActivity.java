package com.example.chatandroidadvanced;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class ChatActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        toolbar = (Toolbar)findViewById(R.id.toolbarChat);
        setSupportActionBar(toolbar);

        //TODO change title of toolbar to name of person which was clicked in recycler view
        toolbar.setTitle("Name of clicked Person");
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
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void sendText(View view) {
        //Todo send text when click on send happens
    }
}
