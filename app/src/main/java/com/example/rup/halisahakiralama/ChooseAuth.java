package com.example.rup.halisahakiralama;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChooseAuth extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();

        setContentView(R.layout.activity_choose_auth);


        Button b3=findViewById(R.id.go_signin2);
        Button b=findViewById(R.id.go_signup2);
        Button b1=findViewById(R.id.go_signup);
        Button b2=findViewById(R.id.go_signin);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChooseAuth.this.startActivity(new Intent( ChooseAuth.this,SignUp.class));
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChooseAuth.this.startActivity(new Intent( ChooseAuth.this,SignIn.class));
            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChooseAuth.this.startActivity(new Intent( ChooseAuth.this,SignUpAsOwner.class));
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChooseAuth.this.startActivity(new Intent( ChooseAuth.this,SignInAsOwner.class));
            }
        });
    }
}
