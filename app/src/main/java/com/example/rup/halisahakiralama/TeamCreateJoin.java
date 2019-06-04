package com.example.rup.halisahakiralama;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.rup.halisahakiralama.client.User;
import com.google.gson.Gson;

public class TeamCreateJoin extends AppCompatActivity {
    User user;
    Button toCreateTeam,toJoinTeam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_create_join);


        final Gson gson=new Gson();
        Bundle extras = getIntent().getExtras();
        user= gson.fromJson(extras.getString("user"),User.class);

        toCreateTeam=findViewById(R.id.createteambutton);
        toJoinTeam=findViewById(R.id.jointeambutton);


        toCreateTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeamCreateJoin.this, CreateTeam.class);
                final Gson gson=new Gson();
                intent.putExtra("user",gson.toJson(user));
                TeamCreateJoin.this.startActivity(intent);
            }
        });
    }
}
