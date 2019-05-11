package com.example.rup.feedyourpet;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CreateAccount extends AppCompatActivity {
    Button signinbutton;
    Button signupbutton;
    Button istekbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        signinbutton=findViewById(R.id.signin_button);
        signupbutton=findViewById(R.id.signup_button);
        signinbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateAccount.this.startActivity(new Intent(CreateAccount.this,SignIn.class));
            }
        });
        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateAccount.this.startActivity(new Intent(CreateAccount.this,SignUp.class));
            }
        });

        istekbutton=findViewById(R.id.istek);
        istekbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                //FILL HERE
            }
        });
    }




}
