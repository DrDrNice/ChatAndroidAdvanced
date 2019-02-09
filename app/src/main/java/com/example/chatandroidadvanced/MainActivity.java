package com.example.chatandroidadvanced;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
            //todo create the user on server if all required inputs are valid start activity give parameters with putextra that in conversionactivity all
            //conversations of the user can be loaded
            Toast.makeText(getApplicationContext() ,"firstName: " + firstName + " lastName: " + lastName + " email: " + eMail, Toast.LENGTH_LONG).show();
            //nicht sicher ob man andere screens mit intents aufruft bei mvvm
            Intent intentConversations = new Intent(this, ConversationActivity.class);
            startActivity(intentConversations);
            finish();
        } else {
            //todo inform user that something went wrong and he has to retry
            Toast.makeText(getApplicationContext() ,"Something went wrong during creating user, please try again.", Toast.LENGTH_LONG).show();
        }

    }

    public void cancelCreateUserClicked(View view) {
        //todo do something if button cancel create user is clicked
        Toast.makeText(getApplicationContext() ,"cancel create user clicked", Toast.LENGTH_SHORT).show();
        txtFristname.setText("");
        txtLastname.setText("");
        txtEmail.setText("");
    }
}
