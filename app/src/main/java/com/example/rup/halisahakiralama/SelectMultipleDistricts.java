package com.example.rup.halisahakiralama;

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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rup.halisahakiralama.client.District;
import com.example.rup.halisahakiralama.client.DistrictListResponse;
import com.example.rup.halisahakiralama.client.NotificationListResponse;
import com.example.rup.halisahakiralama.client.PlayerListResponse;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectMultipleDistricts extends AppCompatActivity {

    static List<District> districts;
    static List<District> addedDistricts;
    Bundle extras;
    RadioTextAdapter adapter;
    ListView listView;
    String cityname;
    Button tonext;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_multiple_districts);
        extras=getIntent().getExtras();
        cityname=extras.getString("il");
        Gson gson=new Gson();
        user= gson.fromJson(extras.getString("user"),User.class);
        listView=findViewById(R.id.possibledistrictslist);
        tonext=findViewById(R.id.tonext2);
        addedDistricts=new ArrayList<>();

        getDistrictsByCity(cityname);

        tonext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Gson gson=new Gson();
                if(extras.getBoolean("fromCreatePlayer")){
                    Intent intent = new Intent(SelectMultipleDistricts.this, ShowPlayerResult.class);
                    intent.putExtra("il",cityname);
                    intent.putExtra("user",gson.toJson(user));
                    intent.putExtra("name",extras.getString("name"));
                    intent.putExtra("surname",extras.getString("surname"));
                    intent.putExtra("phone",extras.getString("phone"));
                    intent.putExtra("address",extras.getString("address"));
                    intent.putExtra("roles",extras.getString("roles"));
                    intent.putExtra("districts",gson.toJson(addedDistricts));
                    SelectMultipleDistricts.this.startActivity(intent);
                }
                else if(extras.getBoolean("fromCreateTeam")){
                    Intent intent = new Intent(SelectMultipleDistricts.this, CreateTeamResult.class);
                    intent.putExtra("il",cityname);
                    intent.putExtra("districts",gson.toJson(addedDistricts));
                    intent.putExtra("user",gson.toJson(user));
                    intent.putExtra("name",extras.getString("name"));
                    intent.putExtra("fromCreateTeam",true);
                    SelectMultipleDistricts.this.startActivity(intent);
                }
            }
        });
    }

    public void getDistrictsByCity(final String cityName){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = StaticVariables.ip_address + cityName  + "/districts";
        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("d",response);
                        Gson g = new Gson();
                        District[] p = g.fromJson(response, District[].class);
                        districts=Arrays.asList(p);


                        adapter=new RadioTextAdapter(getBaseContext());
                        listView.setAdapter(adapter);
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
                params.put("Authorization", auth);
                return params;
            }
        };
        queue.add(getRequest);
    }


}
