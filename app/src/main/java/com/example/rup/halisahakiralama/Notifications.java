package com.example.rup.halisahakiralama;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rup.halisahakiralama.client.Notification;
import com.example.rup.halisahakiralama.client.NotificationListResponse;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Notifications extends AppCompatActivity {


    static List<Notification> notifications;

    ListView listView;
    Adapter adapter;
    static User user;
    static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        context=this;

        listView = (ListView) findViewById(R.id.notifications_listview);

        final Gson gson=new Gson();
        Bundle  b=getIntent().getExtras();

        user= gson.fromJson(b.getString("user"),User.class);
        adapter = new Adapter(this);
        getNotificationsFrom();

    }

    public  void getNotificationsFrom(){





        RequestQueue queue = Volley.newRequestQueue(this);
        String url = StaticVariables.ip_address + "notification/getAll";
        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        Gson g = new Gson();

                        NotificationListResponse p = g.fromJson(response, NotificationListResponse.class);
                        notifications=p.notificationDTOS;

                        listView.setAdapter(adapter);



                    }
                },
                new com.android.volley.Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Toast.makeText(Notifications.this, "HATA", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                String creds = String.format("%s:%s",user.username,user.password);
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                params.put("Authorization", auth);
                return params;
            }
        };
        queue.add(getRequest);
    }
}
