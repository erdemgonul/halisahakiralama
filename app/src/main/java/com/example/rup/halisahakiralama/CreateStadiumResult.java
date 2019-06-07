package com.example.rup.halisahakiralama;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.rup.halisahakiralama.client.User;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CreateStadiumResult extends AppCompatActivity {



    Bundle extras;
    User user;
    TextView stadiumname,stadiumphone,stadiumtime,stadiumcity,stadiumdistrict;
    Button createstadium;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_stadium_result);


        final Gson gson=new Gson();
        extras = getIntent().getExtras();
        user= gson.fromJson(extras.getString("user"),User.class);


        stadiumname=findViewById(R.id.halisaha_name);
        stadiumphone=findViewById(R.id.createhalisahaphoneresult);
        stadiumtime=findViewById(R.id.intervalMinutes_text);
        stadiumcity=findViewById(R.id.cityhalisaharesult);
        stadiumdistrict=findViewById(R.id.districthalisaharesult);
        createstadium=findViewById(R.id.createhalisaharesult);


        stadiumname.setText("Halısaha Adı: " + extras.getString("stadium_name"));
        stadiumphone.setText("Telefon Numarası: " + extras.getString("stadium_phone"));
        stadiumtime.setText("Maç süresi (dakika): " + extras.getString("stadium_time"));
        stadiumcity.setText("Şehir : " + extras.getString("il"));
        stadiumdistrict.setText("İlçe : " + extras.getString("ilce"));



        createstadium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callCreateStadium();
            }
        });
    }

    private void callCreateStadium(){
        RequestQueue queue = Volley.newRequestQueue(CreateStadiumResult.this);
        String url = StaticVariables.ip_address + "stadium";

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("name", extras.getString("stadium_name"));
            jsonBody.put("districtName", extras.getString("ilce"));
            jsonBody.put("cityName", extras.getString("il"));
            jsonBody.put("phoneNumber", extras.getString("stadium_phone"));
            jsonBody.put("intervalMinutes", extras.getString("stadium_time"));
            jsonBody.put("owner", user.id);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("mustafaaaa" + response.toString());

                Intent intent = new Intent(CreateStadiumResult.this, ChooseJobOwner.class);
                final Gson gson=new Gson();
                intent.putExtra("user",gson.toJson(user));
                CreateStadiumResult.this.startActivity(intent);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                String creds = String.format("%s:%s",user.username,user.password);
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.NO_WRAP);
                params.put("Authorization", auth);
                return params;
            }
        };;

        queue.add(jsonObject);

    }
}
