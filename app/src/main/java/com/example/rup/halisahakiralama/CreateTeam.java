package com.example.rup.halisahakiralama;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

public class CreateTeam extends AppCompatActivity {


    Button tonext;
    EditText createname;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_team);




        final Gson gson=new Gson();
        Bundle extras = getIntent().getExtras();
        user= gson.fromJson(extras.getString("user"),User.class);

        tonext=findViewById(R.id.nextteamcreate);
        createname=findViewById(R.id.createteamname);

        tonext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateTeam.this, ChooseRolePlayer.class);

                intent.putExtra("name",createname.getText() + "");
                final Gson gson=new Gson();
                intent.putExtra("user",gson.toJson(user));
                CreateTeam.this.startActivity(intent);
            }
        });

    }
}
