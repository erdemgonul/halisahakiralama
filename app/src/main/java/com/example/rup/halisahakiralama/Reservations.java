package com.example.rup.halisahakiralama;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rup.halisahakiralama.client.ReservationListResponse;
import com.example.rup.halisahakiralama.client.Stadium;
import com.example.rup.halisahakiralama.client.StadiumListResponse;
import com.example.rup.halisahakiralama.client.User;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Reservations extends AppCompatActivity {
    String[] stadiumArray;
    Button nextbutton;
    ListView listView;
    TextView textView;
    User user;
    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservations);

        final Gson gson=new Gson();
        extras = getIntent().getExtras();
        user= gson.fromJson(extras.getString("user"),User.class);

        listView=findViewById(R.id.list_reservation);
        textView=findViewById(R.id.reservation_text);
        nextbutton=findViewById(R.id.button9);

        nextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Reservations.this, ChooseJobOwner.class);
                Gson gson=new Gson();
                intent.putExtra("user",gson.toJson(user));
                Reservations.this.startActivity(intent);
            }
        });

        getStadiumsOfUser();
    }

    public void getStadiumsOfUser(){

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = StaticVariables.ip_address + "stadium/user/" + user.id;
        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("d",response);
                        Gson g = new Gson();
                        System.out.println(response);
                        final Stadium[] p = g.fromJson(response, Stadium[].class);
                        List<String> list=new ArrayList<>();
                        for(int i=0;i<p.length;i++){
                            list.add(p[i].name);
                        }
                        stadiumArray=list.toArray(new String[0]);
                        ArrayAdapter<String> veriAdaptoru=new ArrayAdapter<String>
                                (Reservations.this, android.R.layout.simple_list_item_1, android.R.id.text1, stadiumArray);

                        listView.setAdapter(veriAdaptoru);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view,final int i, long l) {
                                Intent intent = new Intent(Reservations.this, SetDate.class);
                                Gson gson=new Gson();
                                intent.putExtra("user",gson.toJson(user));
                                intent.putExtra("stadium_id",p[i].id + "");
                                intent.putExtra("option","fromShowReservations");
                                Reservations.this.startActivity(intent);
                            }
                        });


                    }
                },
                new com.android.volley.Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {


                HashMap<String, String> params = new HashMap<String, String>();
                String creds = String.format("%s:%s",user.username,user.password);
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.NO_WRAP);
                System.out.println("mustafaaaaa" +user.username + "   " + user.password);
                params.put("Authorization", auth);
                return params;
            }
        };
        queue.add(getRequest);
    }
}
