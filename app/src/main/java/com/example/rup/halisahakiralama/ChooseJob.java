package com.example.rup.halisahakiralama;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.api.client.json.Json;
import com.google.gson.Gson;

public class ChooseJob extends Activity {

    GoogleSignInClient mGoogleSignInClient;
    Button findHaliSahaButton,findPlayerButton,signOutButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_job);

        final Gson gson=new Gson();
        Bundle extras = getIntent().getExtras();
        final User user= gson.fromJson(extras.getString("user"),User.class);

        findHaliSahaButton=findViewById(R.id.button2);
        findPlayerButton=findViewById(R.id.button3);
        findHaliSahaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(ChooseJob.this, CitySelect.class);
               intent.putExtra("user",gson.toJson(user));
                ChooseJob.this.startActivity(intent);
            }
        });
        findPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ChooseJob.this, CitySelect.class);
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
        Intent intent=new Intent(ChooseJob.this,SignIn.class);
        ChooseJob.this.startActivity(intent);
    }
}
