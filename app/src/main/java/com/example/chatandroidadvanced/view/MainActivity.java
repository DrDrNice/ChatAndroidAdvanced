package com.example.chatandroidadvanced.view;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chatandroidadvanced.R;
import com.example.chatandroidadvanced.model.Participant;
import com.example.chatandroidadvanced.model.ParticipantService;
import com.example.chatandroidadvanced.service.NotificationJobService;
import com.example.chatandroidadvanced.viewmodel.ParticipantViewModel;
import com.example.chatandroidadvanced.viewmodel.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    //variable layout edittext
    private TextInputLayout ltFirstName;
    private TextInputLayout ltLastName;
    private TextInputLayout ltEmail;

    //variable edittext
    private EditText txtFristname;
    private EditText txtLastname;
    private EditText txtEmail;

    //variable button
    private Button btnCreateUser;
    private Button btnCancelCreateUser;

    private ParticipantViewModel mParticipantViewModel;
    private SharedPreferences preferences;

    //constant for shared preferences
    public static final String MY_PREFERENCES = "UserLoggedIn";
    public static final String LASTNAME = "lastName";
    public static final String FIRSTNAME = "firstName";
    public static final String EMAIL = "email";
    public static final String ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get layout edittext
        ltFirstName = findViewById(R.id.layoutInputFirstName);
        ltLastName = findViewById(R.id.layoutInputLastName);
        ltEmail = findViewById(R.id.layoutInputEmail);

        //get edittext
        txtFristname = findViewById(R.id.textInputFirstName);
        txtLastname = findViewById(R.id.textInputLastName);
        txtEmail = findViewById(R.id.textInputEmail);

        //get buttons
        btnCreateUser = findViewById(R.id.btnCreateUser);
        btnCancelCreateUser = findViewById(R.id.btnCancelCreateUser);

        mParticipantViewModel = ViewModelProviders.of(this).get(ParticipantViewModel.class);

        //using shared preferences to load logged in user
        preferences = getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE);
        
        //todo delete this test parameters
        //Log.d("preference name", preferences.getString(FIRSTNAME, ""));
        //Log.d("preference name", preferences.getString(LASTNAME, ""));
        //Log.d("preference name", preferences.getString(ID, ""));

        //todo delete shared preferences if new user should be logged in
      //   preferences.edit().clear().apply();

        //if shared there exists a shared preferences file user is allredy logged in and conversationactivity starts
        if(!preferences.getString(FIRSTNAME, "").equals("") && !preferences.getString(LASTNAME, "").equals("") && !preferences.getString(EMAIL, "").equals("")){
            //start conversation activity
       /*     JobScheduler scheduler = (JobScheduler) this.getSystemService(Context.JOB_SCHEDULER_SERVICE);
            JobInfo job = new JobInfo.Builder(1,new ComponentName(this, NotificationJobService.class))
                    .setMinimumLatency(1000)
                    .build();
            scheduler.schedule(job);*/
            
            Intent intentConversations = new Intent(getApplicationContext(), ConversationActivity.class);
            startActivity(intentConversations);
            finish();
        }
    }

    public void createUserClicked(View view) {
        Participant participant = getUserInput();
        if(participant != null) {
            //todo works in externally class but to much on main thread
            //create new participant on server
            //RetrofitInstance retrofitInstance = new RetrofitInstance();
            //retrofitInstance.createParticipant(participant, getApplicationContext(), true);

            //if shared there exists a shared preferences file user is allredy logged in and conversationactivity starts
            /*if(!preferences.getString(FIRSTNAME, "").equals("") && !preferences.getString(LASTNAME, "").equals("") && !preferences.getString(EMAIL, "").equals("")){
                //start conversation activity
                Intent intentConversations = new Intent(getApplicationContext(), ConversationActivity.class);
                startActivity(intentConversations);
                finish();
            }*/

            RetrofitInstance retrofitInstance = new RetrofitInstance();
            ParticipantService participantService = retrofitInstance.getParticipantService();
            Call<Participant> call = participantService.createParticipant(participant);
            call.enqueue(new Callback<Participant>() {
                @Override
                public void onResponse(Call<Participant> call, Response<Participant> response) {
                    if (!response.isSuccessful()) {
                        Log.d("create participant not successfull", String.valueOf(response.code()));
                        Toast.makeText(getApplicationContext() ,"Something went wrong during creating user, please try again.", Toast.LENGTH_LONG).show();
                        return;
                    }
                    //save current user data in shared preferences
                    SharedPreferences.Editor sharedEditor = getSharedPreferences(MY_PREFERENCES, MODE_PRIVATE).edit();
                    sharedEditor.putString(FIRSTNAME, response.body().mfirstName);
                    sharedEditor.putString(LASTNAME, response.body().mlastName);
                    sharedEditor.putString(EMAIL, response.body().mEmail);
                    sharedEditor.putString(ID, response.body().getIDServer());
                    sharedEditor.apply();

                    //start conversation activity
                    Intent intentConversations = new Intent(getApplicationContext(), ConversationActivity.class);

                    startActivity(intentConversations);
                    finish();
                }

                @Override
                public void onFailure(Call<Participant> call, Throwable t) {
                    Log.d("create participant not successfull", t.toString());
                    Toast.makeText(getApplicationContext() ,"Something went wrong during creating user, please try again.", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            Toast.makeText(getApplicationContext() ,"Something went wrong during creating user, please try again.", Toast.LENGTH_LONG).show();
        }
    }

    //gets the input from the user as participant
    public Participant getUserInput() {
        //get text from textlabels and store
        String firstName = txtFristname.getText().toString();
        String lastName = txtLastname.getText().toString();
        String eMail = txtEmail.getText().toString();
        if(firstName.isEmpty()) {
            Toast.makeText(getApplicationContext() ,"Please enter valid first name!", Toast.LENGTH_LONG).show();
            return null;
        }
        if(lastName.isEmpty()) {
            Toast.makeText(getApplicationContext() ,"Please enter valid last name!", Toast.LENGTH_LONG).show();
            return null;
        }
        if(eMail.isEmpty()) {
            Toast.makeText(getApplicationContext() ,"Please enter valid e-mail!", Toast.LENGTH_LONG).show();
            return null;
        }
        return new Participant(eMail, firstName, lastName);
    }

    public void cancelCreateUserClicked(View view) {
        Toast.makeText(getApplicationContext() ,"cancel create user clicked", Toast.LENGTH_SHORT).show();
        txtFristname.setText("");
        txtLastname.setText("");
        txtEmail.setText("");
    }
}
