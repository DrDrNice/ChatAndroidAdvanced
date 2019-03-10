package com.example.chatandroidadvanced.view;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
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

import com.example.chatandroidadvanced.R;
import com.example.chatandroidadvanced.model.Conversation;
import com.example.chatandroidadvanced.model.Message;
import com.example.chatandroidadvanced.model.Participant;
import com.example.chatandroidadvanced.model.ParticipantService;
import com.example.chatandroidadvanced.service.NotificationJobService;
import com.example.chatandroidadvanced.viewmodel.ConversationListAdapter;
import com.example.chatandroidadvanced.viewmodel.ConversationViewModel;
import com.example.chatandroidadvanced.viewmodel.MessageViewModel;
import com.example.chatandroidadvanced.viewmodel.ParticipantListAdapter;
import com.example.chatandroidadvanced.viewmodel.ParticipantViewModel;
import com.example.chatandroidadvanced.viewmodel.RetrofitInstance;

import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConversationActivity extends AppCompatActivity {

    private ParticipantViewModel mParticipantViewModel;
    private ConversationViewModel mConversationViewModel;

    private MessageViewModel mMessageViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarConversations);
        setSupportActionBar(toolbar);


        JobScheduler scheduler = (JobScheduler) this.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        JobInfo job = new JobInfo.Builder(1, new ComponentName(this, NotificationJobService.class))
                .setMinimumLatency(1000)
                .build();
        scheduler.schedule(job);

        RetrofitInstance retrofitInstance = new RetrofitInstance();

        mParticipantViewModel = ViewModelProviders.of(this).get(ParticipantViewModel.class);
        mMessageViewModel = ViewModelProviders.of(this).get(MessageViewModel.class);
        mConversationViewModel = ViewModelProviders.of(this).get(ConversationViewModel.class);

        retrofitInstance.getAllConversations(getApplicationContext(), mConversationViewModel);
        retrofitInstance.getAllMessages(getApplicationContext(), mMessageViewModel, mConversationViewModel, "1");

        RecyclerView recyclerView = findViewById(R.id.rcvConversations);
        final ConversationListAdapter adapter = new ConversationListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        mConversationViewModel = ViewModelProviders.of(this).get(ConversationViewModel.class);
        mConversationViewModel.getAllConversations().observe(this, new Observer<List<Conversation>>() {
            @Override
            public void onChanged(@Nullable List<Conversation> conversations) {
                //sets the adapter to the recyclerview and keeps all updated
                adapter.setmParticipants(conversations);
            }
        });



        adapter.setOnItemClickListener(new ConversationListAdapter.ClickListener() {

            @Override
            public void onItemClick(View v, int position) {
                //start chat activity and put clicked participant as extra to chatactiviy
                Conversation myConversation = adapter.getConversationdAtPosition(position);

                Intent intentChat = new Intent(ConversationActivity.this, ChatActivity.class);
                intentChat.putExtra("CODE", 1);
                intentChat.putExtra("Conversation", myConversation);
                startActivity(intentChat);
            }
        });
    }

    public void addConversation(View view) {
        RetrofitInstance retrofitInstance = new RetrofitInstance();
        retrofitInstance.getAllParticipants(getApplicationContext(), mParticipantViewModel);
        Toast.makeText(getApplicationContext(), "Add new conversation!", Toast.LENGTH_LONG).show();
        //create new conversation if button is clicked
        Intent intentNewConversation = new Intent(this, AddConversationActivity.class);
        startActivity(intentNewConversation);
    }
}
