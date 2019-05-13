package com.example.rup.halisahakiralama;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.rup.halisahakiralama.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class CreateAccount extends AppCompatActivity {
    Button signinbutton;
    Button signupbutton;
    Button istekbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account!=null){
            CreateAccount.this.startActivity(new Intent(CreateAccount.this,ChooseJob.class));
        }
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
