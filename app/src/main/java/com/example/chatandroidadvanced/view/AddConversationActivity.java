package com.example.chatandroidadvanced.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Debug;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
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
import android.widget.Toast;

import com.example.chatandroidadvanced.model.Conversation;
import com.example.chatandroidadvanced.model.Participant;
import com.example.chatandroidadvanced.model.ParticipantService;
import com.example.chatandroidadvanced.viewmodel.ParticipantListAdapter;
import com.example.chatandroidadvanced.R;
import com.example.chatandroidadvanced.viewmodel.ParticipantViewModel;
import com.example.chatandroidadvanced.viewmodel.RetrofitInstance;

import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddConversationActivity extends AppCompatActivity {
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    public static final String NEW_SELECTED_PARTICIPANT = "newSelectedParticipant";

    private ParticipantViewModel mParticipantViewModel;
    private RetrofitInstance retrofitInstance;
    private int mTestID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_conversation);

        retrofitInstance= new RetrofitInstance();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarNewConversation);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        RecyclerView recyclerView = findViewById(R.id.rcvContacts);
        final ParticipantListAdapter adapter = new ParticipantListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        mParticipantViewModel = ViewModelProviders.of(this).get(ParticipantViewModel.class);
        mParticipantViewModel.getAllParticipants().observe(this, new Observer<List<Participant>>() {
            @Override
            public void onChanged(@Nullable List<Participant> participants) {
                //sets the adapter to the recyclerview and keeps all updated
                adapter.setmParticipants(participants);
            }
        });

        ItemTouchHelper helper = new ItemTouchHelper(
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
                        Participant myParticipent = adapter.getWordAtPosition(position);
                        Toast.makeText(AddConversationActivity.this, "Deleting " +
                                myParticipent.getfirstName(), Toast.LENGTH_LONG).show();

                        // Delete participant from room
                        mParticipantViewModel.deleteParticipant(myParticipent);

                        //delete participant from database
                        retrofitInstance.deleteParticipant(Integer.valueOf(myParticipent.getIDServer()));
                    }
                });

        helper.attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new ParticipantListAdapter.ClickListener()  {

            @Override
            public void onItemClick(View v, int position) {
                //start chat activity and put clicked participant as extra to chatactiviy
                Participant myParticipent = adapter.getWordAtPosition(position);
                Intent intentChat = new Intent(AddConversationActivity.this, ChatActivity.class);
                intentChat.putExtra("contact", myParticipent);
                //todo acitvate put extra to get it in next activity
                //intentChat.putExtra(NEW_SELECTED_PARTICIPANT, myParticipent);
                startActivity(intentChat);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_conversation, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.btnCancelAddConversation) {
            //cancel activity of creating new conversation and go back to list of conversations
            Toast.makeText(getApplicationContext(), "Cancel create conversation!", Toast.LENGTH_LONG).show();
            Intent intentConversations = new Intent(this, ConversationActivity.class);
            startActivity(intentConversations);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
