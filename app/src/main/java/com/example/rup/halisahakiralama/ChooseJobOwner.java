package com.example.rup.halisahakiralama;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.rup.halisahakiralama.client.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;

public class ChooseJobOwner extends Activity {
    TextView  userText;

    GoogleSignInClient mGoogleSignInClient;
    Button reservationButton,addhalisaha,signOutButton,profileButton,reservationButton2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_job_owner);

        final Gson gson=new Gson();
        Bundle extras = getIntent().getExtras();
        final User user= gson.fromJson(extras.getString("user"),User.class);


        reservationButton=findViewById(R.id.rezervationbutton);
        addhalisaha=findViewById(R.id.addhalisaha);
        profileButton=findViewById(R.id.ownerprofile);
        reservationButton2=findViewById(R.id.rezervationbutton2);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(ChooseJobOwner.this, Profil.class);
                intent.putExtra("user",gson.toJson(user));
                ChooseJobOwner.this.startActivity(intent);
            }
        });
        reservationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(ChooseJobOwner.this, ApproveReservation.class);
                intent.putExtra("user",gson.toJson(user));
                ChooseJobOwner.this.startActivity(intent);
            }
        });
        reservationButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(ChooseJobOwner.this, Reservations.class);
                intent.putExtra("user",gson.toJson(user));
                ChooseJobOwner.this.startActivity(intent);
            }
        });
        addhalisaha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ChooseJobOwner.this, CreateStadium.class);
                intent.putExtra("user",gson.toJson(user));
                ChooseJobOwner.this.startActivity(intent);
            }
        });

        signOutButton=findViewById(R.id.signoutbutton);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().requestProfile()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);



    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent=new Intent(ChooseJobOwner.this,ChooseAuth.class);
                        ChooseJobOwner.this.startActivity(intent);
                    }
                });
        Intent intent=new Intent(ChooseJobOwner.this,SignIn.class);
        ChooseJobOwner.this.startActivity(intent);
    }
}
