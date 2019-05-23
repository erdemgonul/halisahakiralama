package com.example.rup.halisahakiralama;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.rup.halisahakiralama.R;

public class ForgotPassword extends AppCompatActivity {
    EditText mailtext;
    Button submitbutton;
    TextView header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // for add back arrow in action bar

        setContentView(R.layout.activity_forgot_password);

        header=findViewById(R.id.title_app);
        header.setText(StaticVariables.title);

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
