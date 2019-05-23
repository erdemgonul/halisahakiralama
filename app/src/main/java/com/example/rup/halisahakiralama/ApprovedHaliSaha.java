package com.example.rup.halisahakiralama;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.rup.halisahakiralama.client.ReservationTime;
import com.example.rup.halisahakiralama.client.Stadium;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ApprovedHaliSaha extends AppCompatActivity {
    TextView header;
    Stadium stadium;
    String date, comment;
    ReservationTime time;
    User user;
    Button goHomeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approved_hali_saha);

        header=findViewById(R.id.textView5);
        header.setText(StaticVariables.title);

        Bundle b = getIntent().getExtras();
        final Gson gson=new Gson();
        stadium= gson.fromJson(b.getString("stadium"), Stadium.class);
        user= gson.fromJson(b.getString("user"),User.class);
        date= gson.fromJson(b.getString("date"),String.class);
        comment= gson.fromJson(b.getString("comment"),String.class);
        time = gson.fromJson(b.getString("hours"), ReservationTime.class);

        goHomeButton=findViewById(R.id.gohomebutton);
        goHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goHome(view);
            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = StaticVariables.ip_address + "reservation";

        JSONObject jsonBody = new JSONObject();

        try {
            jsonBody.put("stadiumId", stadium.id);
            jsonBody.put("reservationDate", date);
            jsonBody.put("beginHour", time.beginHour);
            jsonBody.put("endHour", time.endHour);
            jsonBody.put("comment", comment);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                System.out.println("FUCK");
                Toast.makeText(ApprovedHaliSaha.this, "FUCKKKK ", Toast.LENGTH_SHORT).show();

            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                String creds = String.format("%s:%s",user.username,user.password);
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                params.put("Authorization", auth);
                return params;
            }
        };;

        queue.add(jsonObject);
    }
    public void goHome(View v){
        Intent homePage;
        if(user.role.equals("ROLE_USER"))
            homePage = new Intent(this, ChooseJob.class);
        else
            homePage = new Intent(this, ChooseJobOwner.class);

        Gson gson=new Gson();
        homePage.putExtra("user",gson.toJson(user));

        startActivity(homePage);
    }
}
