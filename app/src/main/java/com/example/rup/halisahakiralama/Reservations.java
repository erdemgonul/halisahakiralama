package com.example.rup.halisahakiralama;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.rup.halisahakiralama.client.User;
import com.google.gson.Gson;

public class Reservations extends AppCompatActivity {



    ListView listView;
    Context context;
    User user;
    Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservations);

        context=this;

        listView = (ListView) findViewById(R.id.notifications_listview);

        final Gson gson=new Gson();
        Bundle  b=getIntent().getExtras();

        user= gson.fromJson(b.getString("user"), User.class);
        adapter = new Adapter(this);
    }
}
