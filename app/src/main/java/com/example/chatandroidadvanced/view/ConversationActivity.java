package com.example.chatandroidadvanced.view;

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

    /* private RecyclerView recyclerView;
     private ConversationListAdapter conversationListAdapter;
     private LinkedList<Conversation> conversationList = new LinkedList<>();
 */
    private ParticipantViewModel mParticipantViewModel;
    private ConversationViewModel mConversationViewModel;

    private MessageViewModel mMessageViewModel;

   // private ParticipantViewModel mParticipantViewModeltest;
   // private RetrofitInstance retrofitInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarConversations);
        setSupportActionBar(toolbar);
       // retrofitInstance = new RetrofitInstance();


    /*-----------------------------
        recyclerView = findViewById(R.id.rcvConversations);
        conversationListAdapter = new ConversationListAdapter(this, conversationList);
        recyclerView.setAdapter(conversationListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Todo get list of conversations from server with get method should be done everytime this activity is started to get actual list
        //conversationslist = get....;

        //todo delete this example list
        //only for testing self defined fix list.
        int wordListSize = conversationList.size();

       /* conversationList.addLast(new Conversation("jim rogers", "jim rogers", "bla bla bla", "3"));
        conversationList.addLast(new Conversation("jim sanders", "jim sanders", "keine ahnung", "3"));*/

    /*
        recyclerView.getAdapter().notifyItemInserted(wordListSize);
        recyclerView.smoothScrollToPosition(wordListSize);
--------------------------------- */

        //allgemein machen
        RetrofitInstance retrofitInstance = new RetrofitInstance();
        mParticipantViewModel = ViewModelProviders.of(this).get(ParticipantViewModel.class);

        mMessageViewModel = ViewModelProviders.of(this).get(MessageViewModel.class);

      //  mParticipantViewModeltest = ViewModelProviders.of(this).get(ParticipantViewModel.class);



        mConversationViewModel = ViewModelProviders.of(this).get(ConversationViewModel.class);
        retrofitInstance.getAllConversations(getApplicationContext(), mConversationViewModel);

        retrofitInstance.getAllMessages(getApplicationContext(), mMessageViewModel);

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
                        Conversation myConversation = adapter.getConversationdAtPosition(position);
                        Toast.makeText(ConversationActivity.this, "Deleting " +
                                myConversation.getTopic(), Toast.LENGTH_LONG).show();

                        // Delete participant from room
                        mConversationViewModel.deleteConversation(myConversation);

                        //delete participant from database
                        RetrofitInstance retrofitInstance = new RetrofitInstance();
                        retrofitInstance.deleteConversation(Integer.valueOf(myConversation.getId()));
                    }
                });

        helper.attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new ConversationListAdapter.ClickListener() {

            @Override
            public void onItemClick(View v, int position) {
                //start chat activity and put clicked participant as extra to chatactiviy
                Conversation myConversation = adapter.getConversationdAtPosition(position);
                Intent intentChat = new Intent(ConversationActivity.this, ChatActivity.class);
                //todo acitvate put extra to get it in next activity
                //intentChat.putExtra(NEW_SELECTED_PARTICIPANT, myParticipent);
                startActivity(intentChat);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_conversation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.btnAddConversation) {
            //load all users from db and add user which are not in room into to room
            RetrofitInstance retrofitInstance = new RetrofitInstance();
            retrofitInstance.getAllParticipants(getApplicationContext(), mParticipantViewModel);

            //todo delete this cause it is working with retrofit class
            //load all users from db and add user which are not in room into to room
            /*RetrofitInstance retrofitInstance = new RetrofitInstance();
            ParticipantService participantService = retrofitInstance.getParticipantService();
            Call<List<Participant>> call = participantService.getAllParticipants();
            call.enqueue(new Callback<List<Participant>>() {
                @Override
                public void onResponse(Call<List<Participant>> call, Response<List<Participant>> response) {
                    if(!response.isSuccessful()){
                        Log.d("get participants not successfull", String.valueOf(response.code()));
                        return;
                    }

                    //todo is there a better solution than to add every element from online service each time?
                    SharedPreferences preferences = getSharedPreferences(MainActivity.MY_PREFERENCES, MODE_PRIVATE);
                    List<Participant> posts = response.body();
                    for (Participant participant : posts) {
                        //only insert element in room from db if it is not the current user
                        if(!preferences.getString(MainActivity.ID, "").equals(participant.getIDServer())){
                            mParticipantViewModel.insert(participant);
                        }
                    }
                }
                @Override
                public void onFailure(Call<List<Participant>> call, Throwable t) {
                    Log.d("get participants failed", t.toString());
                }
            });*/

            //start add conversation activity
            Toast.makeText(getApplicationContext(), "Add new conversation!", Toast.LENGTH_LONG).show();
            //create new conversation if button is clicked
            Intent intentNewConversation = new Intent(this, AddConversationActivity.class);
            startActivity(intentNewConversation);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
