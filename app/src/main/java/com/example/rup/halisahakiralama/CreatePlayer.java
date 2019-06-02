package com.example.rup.halisahakiralama;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class CreatePlayer extends AppCompatActivity {


    Button toButton;
    EditText name,surname,phone,address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_player);


        toButton=findViewById(R.id.possiblecitiesbutton);
        name=findViewById(R.id.createplayername);
        surname=findViewById(R.id.createplayersurname);
        phone=findViewById(R.id.createplayeraddress);
        address=findViewById(R.id.createplayerphone);
    }
}
