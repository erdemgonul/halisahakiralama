package com.example.rup.halisahakiralama;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.rup.halisahakiralama.client.District;
import com.google.gson.Gson;

public class ShowPlayerResult extends AppCompatActivity {

    Bundle extras;
    TextView name,address,phone,city,districts,roles;
    Button tonext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_player_result);


        name=findViewById(R.id.namesurnameplayer);
        address=findViewById(R.id.adres_text);
        phone=findViewById(R.id.phoneplayercreate);
        city=findViewById(R.id.playersummarycity);
        districts=findViewById(R.id.playersummarydistricts);
        roles=findViewById(R.id.mevkilersummary);

        extras=getIntent().getExtras();

        name.setText("İsim: " +extras.getString("name") + " " + extras.getString("surname"));
        address.setText("Oyuncu Adresi: " +extras.getString("address"));
        phone.setText("Telefon Numarası: " +extras.getString("phone"));
        city.setText("Oynayabileceği Şehirler: " +extras.getString("il"));
        Gson gson=new Gson();
        District[] districtsList=gson.fromJson(extras.getString("districts"),District[].class);
        districts.setText("Oynayabileceği İlçeler: " +printArrayIntoTextView(districtsList));

        String x="";
        String[] rol= gson.fromJson(extras.getString("roles"),String[].class);
        for (int i = 0; i <rol.length ; i++) {
            x+=rol[i] + ",";
        }
        roles.setText("Oynayabileceği Roller: " + x);
    }

    private String printArrayIntoTextView(District[] districts){
        String res="";
        for (int i = 0; i < districts.length; i++) {
            res+=districts[i].name + ",";
        }

        return  res;
    }
}
