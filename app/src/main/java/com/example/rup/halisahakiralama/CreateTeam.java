package com.example.rup.halisahakiralama;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rup.halisahakiralama.client.User;
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


        if(extras.getString("fromFindRakip")!=null){
            TextView changeText=findViewById(R.id.playerselect_text4);
            changeText.setText(extras.getString("fromFindRakip"));
        }
        tonext=findViewById(R.id.nextteamcreate);
        createname=findViewById(R.id.createteamname);

        tonext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if((createname.getEditableText()+"").equals("")){
                    Toast.makeText(CreateTeam.this, "Takımına bir isim vermelisin.", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(CreateTeam.this, CitySelect.class);

                    intent.putExtra("name",createname.getText() + "");
                    final Gson gson=new Gson();
                    intent.putExtra("user",gson.toJson(user));
                    intent.putExtra("fromCreateTeam",true);
                    CreateTeam.this.startActivity(intent);
                }

            }
        });

    }
}
