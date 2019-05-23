package com.example.rup.halisahakiralama;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;

public class ChooseJobOwner extends Activity {
    TextView  userText;

    GoogleSignInClient mGoogleSignInClient;
    Button reservationButton,findPlayerButton,signOutButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_job_owner);

        final Gson gson=new Gson();
        Bundle extras = getIntent().getExtras();
        final User user= gson.fromJson(extras.getString("user"),User.class);


        userText=findViewById(R.id.textView3);

        userText.setText("Hoşgeldiniz : " + user.username);

        reservationButton=findViewById(R.id.reservationbutton);
        findPlayerButton=findViewById(R.id.playerbutton);
        reservationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(ChooseJobOwner.this, ApproveReservation.class);
               intent.putExtra("user",gson.toJson(user));
                ChooseJobOwner.this.startActivity(intent);
            }
        });
        findPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ChooseJobOwner.this, CitySelect.class);
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
                        Intent intent=new Intent(ChooseJobOwner.this,SignIn.class);
                        ChooseJobOwner.this.startActivity(intent);
                    }
                });
        Intent intent=new Intent(ChooseJobOwner.this,SignIn.class);
        ChooseJobOwner.this.startActivity(intent);
    }
}
