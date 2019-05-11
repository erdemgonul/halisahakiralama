package com.example.rup.feedyourpet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class ForgotPassword extends AppCompatActivity {
    EditText mailtext;
    Button submitbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);


        mailtext=findViewById(R.id.forgotmail_input);
        submitbutton=findViewById(R.id.forgotsubmit_button);

        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendActivationMail(mailtext.getEditableText().toString());
            }
        });
    }
    private void sendActivationMail(String mail){

    }
}
