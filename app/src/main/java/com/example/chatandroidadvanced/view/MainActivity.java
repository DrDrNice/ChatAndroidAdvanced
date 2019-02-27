package com.example.chatandroidadvanced.view;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
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
import com.example.chatandroidadvanced.viewmodel.ParticipantViewModel;

import java.util.List;

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
    }

    public void createUserClicked(View view) {
        //boolean to check if all inupts are correct
        boolean missedInput = false;

        //get text from textlabels and store
        String firstName = txtFristname.getText().toString();
        String lastName = txtLastname.getText().toString();
        String eMail = txtEmail.getText().toString();

        if(firstName.isEmpty()) {
            Toast.makeText(getApplicationContext() ,"Please enter valid first name!", Toast.LENGTH_LONG).show();
            missedInput = true;
        }
        if(lastName.isEmpty()) {
            Toast.makeText(getApplicationContext() ,"Please enter valid last name!", Toast.LENGTH_LONG).show();
            missedInput = true;
        }
        if(eMail.isEmpty()) {
            Toast.makeText(getApplicationContext() ,"Please enter valid e-mail!", Toast.LENGTH_LONG).show();
            missedInput = true;
        }

        if(!missedInput){
            //conversations of the user can be loaded
            Toast.makeText(getApplicationContext() ,"firstName: " + firstName + " lastName: " + lastName + " email: " + eMail, Toast.LENGTH_LONG).show();

            //todo save user in room after login
            Participant participant = new Participant(eMail, firstName, lastName);
            mParticipantViewModel.insert(participant);

            Intent intentConversations = new Intent(this, ConversationActivity.class);
            startActivity(intentConversations);
            finish();
        } else {
            Toast.makeText(getApplicationContext() ,"Something went wrong while creating user, please try again.", Toast.LENGTH_LONG).show();
        }

    }

    public void cancelCreateUserClicked(View view) {
        Toast.makeText(getApplicationContext() ,"cancel create user clicked", Toast.LENGTH_SHORT).show();
        txtFristname.setText("");
        txtLastname.setText("");
        txtEmail.setText("");
    }
}
