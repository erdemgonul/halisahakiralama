package com.example.rup.halisahakiralama;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.rup.halisahakiralama.client.Reservation;
import com.example.rup.halisahakiralama.client.User;
import com.google.gson.Gson;

public class ShowReservation extends AppCompatActivity {
    Button approve,cancel;
    User user;
    Reservation reservation;
    TextView header, tarih, time, stad, userName, yorum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_reservation);

        Gson g = new Gson();
        Bundle extras = getIntent().getExtras();

        user= g.fromJson(extras.getString("user"),User.class);
        reservation= g.fromJson(extras.getString("reservation"),Reservation.class);

        header=findViewById(R.id.textView12);
        header.setText(StaticVariables.title);

        tarih=findViewById(R.id.tarih_text);
        tarih.setText("Tarih: " +reservation.reservationDate);

        time=findViewById(R.id.adres_text);
        time.setText("Zaman: "+ reservation.beginHour + " - " + reservation.endHour);

        stad=findViewById(R.id.ucret_text);
        stad.setText("Halısaha Adı: " + reservation.stadium);

        userName=findViewById(R.id.halisahasahibi_text);
        userName.setText("Rezervasyon Yapan: " +user.username);

        yorum=findViewById(R.id.yorum_text);
        yorum.setText("Yorum: " + reservation.comment);

        approve=findViewById(R.id.button3);
        cancel=findViewById(R.id.button4);

        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { approve(view); }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { cancel(view); }
        });
    }

    public void approve(View view){
        Intent intent = new Intent(this, SuccessLastPageReservation.class);
        Gson gson=new Gson();
        intent.putExtra("user",gson.toJson(user));
        gson=new Gson();
        intent.putExtra("reservation",gson.toJson(reservation));
        ShowReservation.this.startActivity(intent);
    }

    public void cancel(View view){
        Intent intent = new Intent(this, CancelLastPageReservation.class);
        Gson gson=new Gson();
        intent.putExtra("user",gson.toJson(user));
        gson=new Gson();
        intent.putExtra("reservation",gson.toJson(reservation));
        ShowReservation.this.startActivity(intent);
    }
}
