package com.example.rup.halisahakiralama;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.rup.halisahakiralama.client.ReservationTime;
import com.example.rup.halisahakiralama.client.Stadium;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.InputStreamReader;
import java.io.StringReader;

public class ShowHaliSaha extends AppCompatActivity {

    Stadium stadium;
    String date;
    ReservationTime time;
    TextView  name, address, amount, intervalMinutes, dateText;
    Button approve,arabutton;
    User user;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // for add back arrow in action bar


        setContentView(R.layout.activity_show_hali_saha);

        Bundle b = getIntent().getExtras();
        final Gson gson=new Gson();
        stadium= gson.fromJson(b.getString("stadium"),Stadium.class);
        user= gson.fromJson(b.getString("user"),User.class);
        date= gson.fromJson(b.getString("date"),String.class);
        time = gson.fromJson(b.getString("hours"),ReservationTime.class);



        name=findViewById(R.id.textView12);
        name.setText( stadium.name);

        address=findViewById(R.id.adres_text);
        address.setText("Adres: " + stadium.address);

        amount=findViewById(R.id.ucret_text);
        amount.setText("Ücret: " + stadium.amount);

        dateText=findViewById(R.id.date_text);
        dateText.setText("Tarih: " + date + " " + time.beginHour + "-" + time.endHour);

        intervalMinutes=findViewById(R.id.intervalMinutes_text);
        intervalMinutes.setText("Başlangıç Aralığı: " + stadium.intervalMinutes);

        approve=findViewById(R.id.button3);
        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { approve(view); }
        });

        editText=findViewById(R.id.editText);

        arabutton=findViewById(R.id.ara_button);
        arabutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPhoneNumber();
            }
        });
    }

    public void approve(View v){
        Intent intent = new Intent(this, ApprovedHaliSaha.class);
        intent.putExtra("date",date);
        intent.putExtra("comment", editText.getEditableText().toString());
        Gson gson=new Gson();
        intent.putExtra("user",gson.toJson(user));
        gson=new Gson();
        intent.putExtra("stadium",gson.toJson(stadium));
        gson=new Gson();
        intent.putExtra("hours",gson.toJson(time));
        startActivity(intent);
    }

    public void callPhoneNumber()
    {
        try
        {
            if(Build.VERSION.SDK_INT > 22)
            {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling

                    ActivityCompat.requestPermissions(ShowHaliSaha.this, new String[]{Manifest.permission.CALL_PHONE}, 101);

                    return;
                }

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + "05388508090"));
                startActivity(callIntent);

            }
            else {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + "05366594646"));
                startActivity(callIntent);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
