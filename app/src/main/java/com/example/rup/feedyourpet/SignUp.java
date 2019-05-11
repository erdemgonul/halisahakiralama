package com.example.rup.feedyourpet;

import android.content.Intent;
import android.graphics.Color;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.io.Console;

public class SignUp extends AppCompatActivity {
    EditText mailtext;
    EditText passwordtext;
    CheckBox termbox;
    Button signupbutton;
    Button haveaccountbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mailtext=findViewById(R.id.signupmail_input);
        passwordtext=findViewById(R.id.signuppassword_input);
        termbox=findViewById(R.id.acceptterms_checkbox);
        signupbutton=findViewById(R.id.signupsignup_button);
        haveaccountbutton=findViewById(R.id.haveaccount_button);

        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(termbox.isChecked())
                    newuser(mailtext.getEditableText().toString(),passwordtext.getEditableText().toString());
                else
                {
                    termbox.setTextColor(Color.RED);
                    System.out.println("BAS BAKAYIM !!!!!!!!!!!!!!!!!!!!");
                }

            }
        });

        haveaccountbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignUp.this.startActivity(new Intent(SignUp.this,SignIn.class));
            }
        });
    }


    private void newuser(String mail,String password){

    }


}
