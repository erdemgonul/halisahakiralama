package com.example.rup.halisahakiralama;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rup.halisahakiralama.client.Reservation;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class SuccessLastPageReservation extends AppCompatActivity {
    User user;
    Bundle extras;
    Reservation reservation;
    Button goHomeButton;
    TextView header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_last_page_reservation);

        header=findViewById(R.id.textView2);
        header.setText(StaticVariables.title);

        final Gson gson=new Gson();
        extras = getIntent().getExtras();
        user= gson.fromJson(extras.getString("user"),User.class);
        reservation= gson.fromJson(extras.getString("reservation"),Reservation.class);

        goHomeButton=findViewById(R.id.gohomebutton);
        goHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goHome(view);
            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = StaticVariables.ip_address + "approve/reservation/" + user.id + "/" + reservation.id + "/" + true;
        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("d",response);
                        Gson g = new Gson();

                        final Reservation p = g.fromJson(response, Reservation.class);
                        System.out.println("mustafaaaa : " + p );
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
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                params.put("Authorization", auth);
                return params;
            }
        };
        queue.add(getRequest);
    }
    public void goHome(View v){
        Intent intent = new Intent(this, ChooseJobOwner.class);
        final Gson gson=new Gson();
        intent.putExtra("user",gson.toJson(user));
        startActivity(intent);
    }
}
