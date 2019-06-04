package com.example.rup.halisahakiralama;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.example.rup.halisahakiralama.client.District;
import com.example.rup.halisahakiralama.client.User;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowPlayerResult extends AppCompatActivity {

    Bundle extras;
    TextView name,address,phone,city,districts,roles;
    Button tonext;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_player_result);
        Gson gson=new Gson();


        name=findViewById(R.id.namesurnameplayer);
        address=findViewById(R.id.adres_text);
        phone=findViewById(R.id.phoneplayercreate);
        city=findViewById(R.id.playersummarycity);
        districts=findViewById(R.id.playersummarydistricts);
        roles=findViewById(R.id.mevkilersummary);
        tonext=findViewById(R.id.create_player_button);

        extras=getIntent().getExtras();
        user= gson.fromJson(extras.getString("user"),User.class);

        name.setText("İsim: " +extras.getString("name") + " " + extras.getString("surname"));
        address.setText("Oyuncu Adresi: " +extras.getString("address"));
        phone.setText("Telefon Numarası: " +extras.getString("phone"));
        city.setText("Oynayabileceği Şehirler: " +extras.getString("il"));

        District[] districtsList=gson.fromJson(extras.getString("districts"),District[].class);
        districts.setText("Oynayabileceği İlçeler: " +printArrayIntoTextView(districtsList));
        String districtsStr = "";
        for(District district: districtsList) {
            districtsStr = districtsStr + ";" + district.name;
        }
        final List<String> positions = new ArrayList<>();
        String x="";
        String positionStr = "";
        String[] rol= gson.fromJson(extras.getString("roles"),String[].class);
        for (int i = 0; i <rol.length ; i++) {
            x+=rol[i] + ",";
            if(!positions.contains(rol[i])) {
                positions.add(rol[i].split("_")[0]);
                positionStr = positionStr + ";" + rol[i].split("_")[0];
            }
        }
        roles.setText("Oynayabileceği Roller: " + x);

        final String finalPositionStr = positionStr;
        final String finalDistrictsStr = districtsStr;
        tonext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue queue = Volley.newRequestQueue(ShowPlayerResult.this);
                String url = StaticVariables.ip_address + "player";

                JSONObject jsonBody = new JSONObject();
                Gson gson2 = new Gson();
                try {
                    jsonBody.put("name", extras.getString("name"));
                    jsonBody.put("surName", extras.getString("surname"));
                    jsonBody.put("address", extras.getString("address"));
                    jsonBody.put("phoneNumber", extras.getString("phone"));
                    jsonBody.put("cityName", extras.getString("il"));
                    jsonBody.put("positions", finalPositionStr);
                    jsonBody.put("availableDistricts", finalDistrictsStr);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("mustafaaaa" + response.toString());

                        Intent intent = new Intent(ShowPlayerResult.this, Profil.class);
                        final Gson gson=new Gson();
                        intent.putExtra("user",gson.toJson(user));
                        ShowPlayerResult.this.startActivity(intent);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        System.out.println("FUCK");
                        Toast.makeText(ShowPlayerResult.this, "FUCKKKK ", Toast.LENGTH_SHORT).show();

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
        });
    }

    private String printArrayIntoTextView(District[] districts){
        String res="";
        for (int i = 0; i < districts.length; i++) {
            res+=districts[i].name + ",";
        }

        return  res;
    }
}
