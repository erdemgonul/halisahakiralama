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
import com.example.rup.halisahakiralama.client.District;
import com.example.rup.halisahakiralama.client.User;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CreateTeamResult extends AppCompatActivity {


    Bundle extras;
    User user;
    Button createteam;
    TextView teamname,city,possible_districts,teamowner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_team_result);

        Gson gson=new Gson();
        extras=getIntent().getExtras();
        user= gson.fromJson(extras.getString("user"),User.class);

        createteam=findViewById(R.id.create_team_button);
        teamname=findViewById(R.id.teamnametext);
        possible_districts=findViewById(R.id.teamsummarydistricts);
        teamowner=findViewById(R.id.teamownertext);
        city=findViewById(R.id.teamsummarycity);
        teamname.setText("Takım Adı : " + extras.getString("name"));
        teamowner.setText("Takım Kurucusu: " +user.username);

        city.setText("Oynayabileceği Şehirler: " +extras.getString("il"));


        District[] districtsList=gson.fromJson(extras.getString("districts"),District[].class);
        String districtsStr = "";
        for(District district: districtsList) {
            districtsStr = districtsStr + ";" + district.name;
        }
        possible_districts.setText("Oynayabileceği Şehirler: " + districtsStr);


        final String finalDistrictsStr = districtsStr;
        createteam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callCreateTeam(finalDistrictsStr);
            }
        });
    }

    private void callCreateTeam(String districts){
        RequestQueue queue = Volley.newRequestQueue(CreateTeamResult.this);
        String url = StaticVariables.ip_address + "team";

        JSONObject jsonBody = new JSONObject();
        Gson gson2 = new Gson();
        try {
            jsonBody.put("name", extras.getString("name"));
            jsonBody.put("cityName", extras.getString("il"));
            jsonBody.put("availableDistricts", districts);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("mustafaaaa" + response.toString());

                Intent intent = new Intent(CreateTeamResult.this, Profil.class);
                final Gson gson=new Gson();
                intent.putExtra("user",gson.toJson(user));
                CreateTeamResult.this.startActivity(intent);

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
