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
import com.example.rup.halisahakiralama.client.User;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApproveReservation extends AppCompatActivity {
    String[] reservationArray;
    Button nextbutton;
    ListView listView;
    TextView textView;
    User user;
    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_reservation);

        final Gson gson=new Gson();
        extras = getIntent().getExtras();
        user= gson.fromJson(extras.getString("user"),User.class);

        listView=findViewById(R.id.list_reservation);
        textView=findViewById(R.id.reservation_text);
        nextbutton=findViewById(R.id.button9);



        getPendingReservations();
    }

    public void getPendingReservations(){

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = StaticVariables.ip_address + "reservation/time/sheet/user/" + user.id;
        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("d",response);
                        Gson g = new Gson();

                        final ReservationListResponse p = g.fromJson(response, ReservationListResponse.class);
                        List<String> list=new ArrayList<>();
                        for(int i=0;i<p.reservations.size();i++){
                            list.add(p.reservations.get(i).stadium + "-" + p.reservations.get(i).user + "-" +
                                    p.reservations.get(i).reservationDate + "-" + p.reservations.get(i).beginHour + "-" + p.reservations.get(i).endHour);
                        }
                        reservationArray=list.toArray(new String[0]);
                        ArrayAdapter<String> veriAdaptoru=new ArrayAdapter<String>
                                (ApproveReservation.this, android.R.layout.simple_list_item_1, android.R.id.text1, reservationArray);

                        listView.setAdapter(veriAdaptoru);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view,final int i, long l) {
                                nextbutton.setVisibility(View.VISIBLE);
                                nextbutton.setText( reservationArray[i] +" görüntüle");
                                nextbutton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#22B473")));


                                nextbutton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(ApproveReservation.this, ShowReservation.class);
                                        Gson gson=new Gson();
                                        intent.putExtra("reservation",gson.toJson(p.reservations.get(i)));
                                        gson=new Gson();
                                        intent.putExtra("user",gson.toJson(user));
                                        ApproveReservation.this.startActivity(intent);
                                    }
                                });
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
