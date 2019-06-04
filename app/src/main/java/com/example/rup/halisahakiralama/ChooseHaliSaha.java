package com.example.rup.halisahakiralama;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
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
import com.example.rup.halisahakiralama.client.City;
import com.google.gson.Gson;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChooseHaliSaha extends AppCompatActivity {

    City[] array;
    String[] halisahaArray;
    Button nextbutton;
    ListView listView;
    TextView textView, header;
    User user;
    String il,ilce, option;
    public ChooseHaliSaha() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_hali_saha);


        Bundle  b=getIntent().getExtras();
        il=b.getString("il");
        ilce=b.getString("ilce") ;
        option=b.getString("option") ;
        final Gson gson=new Gson();
        user= gson.fromJson(b.getString("user"),User.class);

        listView=findViewById(R.id.list_halisaha);
        textView=findViewById(R.id.halisahalar_text);
        textView.setText(ilce + " 'deki Halı Sahalar");

        nextbutton=findViewById(R.id.button9);

        getHalisahalar(il,ilce);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), IlceSelect.class);

        final Gson gson=new Gson();
        Bundle extras = getIntent().getExtras();
        user= gson.fromJson(extras.getString("user"),User.class);
        myIntent.putExtra("user",gson.toJson(user));
        myIntent.putExtra("il",il);
        myIntent.putExtra("ilce",ilce);
        startActivityForResult(myIntent, 0);
        return true;
    }


    public void getHalisahalar(String city,String district){

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = StaticVariables.ip_address + "stadium/" + city + "/" + district;
        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("d",response);
                        Gson g = new Gson();

                        final StadiumListResponse p = g.fromJson(response, StadiumListResponse.class);
                        List<String> list=new ArrayList<>();
                        for(int i=0;i<p.stadiums.size();i++){
                            list.add(p.stadiums.get(i).name);
                        }
                        halisahaArray=list.toArray(new String[0]);
                        ArrayAdapter<String> veriAdaptoru=new ArrayAdapter<String>
                                (ChooseHaliSaha.this, android.R.layout.simple_list_item_1, android.R.id.text1, halisahaArray);

                        listView.setAdapter(veriAdaptoru);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view,final int i, long l) {


                               nextbutton.setVisibility(View.VISIBLE);
                               nextbutton.setText( halisahaArray[i] +" görüntüle");
                               nextbutton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#22B473")));


                                nextbutton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(ChooseHaliSaha.this, SetDate.class);
                                        intent.putExtra("name", p.stadiums.get(i).name);
                                        intent.putExtra("stadium_id", p.stadiums.get(i).id + "");
                                        intent.putExtra("option", option);
                                        Gson gson = new Gson();
                                        intent.putExtra("user", gson.toJson(user));
                                        gson = new Gson();
                                        intent.putExtra("stadium", gson.toJson(p.stadiums.get(i)));
                                        Toast.makeText(ChooseHaliSaha.this, p.stadiums.get(i).id + "", Toast.LENGTH_SHORT).show();
                                        ChooseHaliSaha.this.startActivity(intent);
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
                params.put("Authorization", auth);
                return params;
            }
        };
        queue.add(getRequest);
    }
}
