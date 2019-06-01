package com.example.rup.halisahakiralama;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.api.client.json.Json;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.Gson;
import android.provider.Settings.Secure;
public class ChooseJob extends Activity {

    TextView  userText;
    GoogleSignInClient mGoogleSignInClient;
    Button findHaliSahaButton,findPlayerButton,findTeamButton, profilEdit, signOutButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true); // for add back arrow in action bar

        setContentView(R.layout.activity_choose_job);

        final Gson gson=new Gson();
        Bundle extras = getIntent().getExtras();
        final User user= gson.fromJson(extras.getString("user"),User.class);




        findHaliSahaButton=findViewById(R.id.rezervationbuttuon);
        findPlayerButton=findViewById(R.id.oyuncubul);
        findTeamButton=findViewById(R.id.rakipbul);
        profilEdit=findViewById(R.id.editprofil);
        findHaliSahaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(ChooseJob.this, CitySelect.class);
                intent.putExtra("user",gson.toJson(user));
                intent.putExtra("option","Rezervation");
                ChooseJob.this.startActivity(intent);
            }
        });
        findPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ChooseJob.this, CitySelect.class);
                intent.putExtra("user",gson.toJson(user));
                intent.putExtra("option","FindPlayer");
                ChooseJob.this.startActivity(intent);
            }
        });

        findTeamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ChooseJob.this, CitySelect.class);
                intent.putExtra("user",gson.toJson(user));
                intent.putExtra("option","FindTeam");
                ChooseJob.this.startActivity(intent);
            }
        });

        profilEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ChooseJob.this, Profil.class);
                intent.putExtra("user",gson.toJson(user));
                ChooseJob.this.startActivity(intent);
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
                        Intent intent=new Intent(ChooseJob.this,SignIn.class);
                        ChooseJob.this.startActivity(intent);
                    }
                });
        Intent intent=new Intent(ChooseJob.this,ChooseAuth.class);
        ChooseJob.this.startActivity(intent);
    }
}
