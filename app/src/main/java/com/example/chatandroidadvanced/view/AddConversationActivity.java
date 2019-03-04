package com.example.chatandroidadvanced.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Debug;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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

    private ParticipantViewModel mParticipantViewModel;

    private int mTestID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_conversation);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarNewConversation);
        setSupportActionBar(toolbar);


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

        //todo glaub das participants nur bei login erstellt werden und in room gespeichert und danach in conversation nur die angezeigt werden die eingeloggt wurden
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddConversationActivity.this, CreateParticipantActivity.class);
                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
                Toast.makeText(AddConversationActivity.this, "Create Participant", Toast.LENGTH_LONG).show();
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

                        // Delete the word
                        mParticipantViewModel.deleteParticipant(myParticipent);
                        RetrofitInstance retrofitInstance = new RetrofitInstance();
                        ParticipantService participantService = retrofitInstance.getParticipantService();
                        Call<Participant> call = participantService.deleteParticipant(Integer.valueOf(myParticipent.getIDServer()));
                        call.enqueue(new Callback<Participant>() {
                            @Override
                            public void onResponse(Call<Participant> call, Response<Participant> response) {
                                if(!response.isSuccessful()){
                                    Log.d("create participant not successfull", String.valueOf(response.code()));
                                    return;
                                }
                            }

                            @Override
                            public void onFailure(Call<Participant> call, Throwable t) {
                                Log.d("foo", t.toString());
                                Log.d("create participant not successfull", t.toString());
                                Toast.makeText(getApplicationContext() ,"Something went wrong during creating user, please try again.", Toast.LENGTH_LONG).show();
                            }
                        });

                    }
                });

        helper.attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new ParticipantListAdapter.ClickListener()  {

            @Override
            public void onItemClick(View v, int position) {
                Participant myParticipent = adapter.getWordAtPosition(position);

            Log.d("fooz", myParticipent.getIDServer());
                Log.d("fooz", String.valueOf(myParticipent.getId()));
                Log.d("fooz", myParticipent.getfirstName());

               // mParticipantViewModel.update(new Participant(myParticipent.getId(),"2", "Email@update", "Walter", "Hello"));
               /* Intent intentChat = new Intent(AddConversationActivity.this, ChatActivity.class);
               startActivity(intentChat);*/
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

    //todo glaube das participants nur bei login erstellt werden
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            final Participant participant = new Participant("",extras.getString(CreateParticipantActivity.EXTRA_REPLY_EMAIL),
                    extras.getString(CreateParticipantActivity.EXTRA_REPLY_FIRST),
                            extras.getString(CreateParticipantActivity.EXTRA_REPLY_LAST));
           // mParticipantViewModel.insert(participant);



            //Participant participants = new Participant("test", "firstName", "lastName");
           RetrofitInstance retrofitInstance = new RetrofitInstance();
            ParticipantService participantService = retrofitInstance.getParticipantService();
            Call<Participant> call = participantService.createParticipant(participant);
            call.enqueue(new Callback<Participant>() {
                @Override
                public void onResponse(Call<Participant> call, Response<Participant> response) {
                    if(!response.isSuccessful()){
                        Log.d("create participant not successfull", String.valueOf(response.code()));
                        Toast.makeText(getApplicationContext() ,"Something went wrong during creating user, please try again.", Toast.LENGTH_LONG).show();
                        Log.d("foo", response.headers().toString());
                       // Log.d("foo", response.body().toString());
                        Log.d("foo", response.errorBody().toString());
                        return;
                    }
                    Participant participantRoom = new Participant(response.body().getIDServer(),participant.getmEmail(),participant.getfirstName(),participant.getlastName());
                    mParticipantViewModel.insert(participantRoom);
                  //  mParticipantViewModel.update(new Participant(participant.getId(),response.body().getIDServer(), participant.getmEmail(), participant.getfirstName(), participant.getlastName()));
                    Log.d("created participant", response.body().getIDServer() + "  / "+ participant.getId());
                    Toast.makeText(getApplicationContext() ,"firstName: " + response.body().getfirstName() + " lastName: " + response.body().getlastName() + " email: " + response.body().getmEmail(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<Participant> call, Throwable t) {
                    Log.d("foo", t.toString());
                    Log.d("create participant not successfull", t.toString());
                    Toast.makeText(getApplicationContext() ,"Something went wrong during creating user, please try again.", Toast.LENGTH_LONG).show();
                }
            });


        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }
}
