package com.example.chatandroidadvanced.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;


import com.example.chatandroidadvanced.model.Participant;
import com.example.chatandroidadvanced.viewmodel.ParticipantListAdapter;
import com.example.chatandroidadvanced.R;
import com.example.chatandroidadvanced.viewmodel.ParticipantViewModel;

import java.util.List;


public class AddConversationActivity extends AppCompatActivity {

    private ParticipantViewModel mParticipantViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_conversation);

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

        adapter.setOnItemClickListener(new ParticipantListAdapter.ClickListener() {

            @Override
            public void onItemClick(View v, int position) {
                //start chat activity and put clicked participant as extra to chatactiviy
                Participant myParticipent = adapter.getWordAtPosition(position);
                Intent intentChat = new Intent(AddConversationActivity.this, ChatActivity.class);
                intentChat.putExtra("contact", myParticipent);
                startActivity(intentChat);
            }
        });
    }
}
