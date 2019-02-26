package com.example.chatandroidadvanced.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.chatandroidadvanced.R;

public class CreateParticipantActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY_EMAIL =
            "com.example.android.roomwordssample.EMAIL";
    public static final String EXTRA_REPLY_FIRST =
            "com.example.android.roomwordssample.FIRST";
    public static final String EXTRA_REPLY_LAST =
            "com.example.android.roomwordssample.LAST";

    private EditText mEditMailView;
    private EditText mEditLastView;
    private EditText mEditFirstView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_participant);
        mEditMailView = findViewById(R.id.edit_eMail);
        mEditLastView = findViewById(R.id.edit_lastName);
        mEditFirstView = findViewById(R.id.edit_firstName);


        final Button button = findViewById(R.id.button_save);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mEditMailView.getText()) || TextUtils.isEmpty(mEditLastView.getText()) || TextUtils.isEmpty(mEditFirstView.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String email = mEditMailView.getText().toString();
                    String first = mEditFirstView.getText().toString();
                    String last = mEditLastView.getText().toString();

                    Bundle extras = new Bundle();
                    extras.putString(EXTRA_REPLY_EMAIL, email);
                    extras.putString(EXTRA_REPLY_FIRST, first);
                    extras.putString(EXTRA_REPLY_LAST, last);
                    replyIntent.putExtras(extras);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }
}
