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
import com.example.rup.halisahakiralama.client.District;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CitySelect extends AppCompatActivity {

    ViewPager pager;
    Button toNextFrag;


    private ListView listView;
    private  String[] iller={"null"};
    private  String[] ilceler = {"null"};
    String option;
    ArrayAdapter<String>adapter;
    ArrayAdapter<String>adapter2;
    List<City>  illerList;
    List<District>  ilcelerList;
    User user;
    public void getCities(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = StaticVariables.ip_address + "cities";
        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(CitySelect.this,"hey", Toast.LENGTH_LONG).show();
                        // response
                        Log.d("d",response);
                        Gson g = new Gson();

                        CityListResponse p = g.fromJson(response, CityListResponse.class);
                        illerList=p.cities;
                        final List<String> list=new ArrayList<>();
                        for(int i=0;i<p.cities.size();i++){
                            list.add(p.cities.get(i).name);
                        }
                        iller=list.toArray(new String[0]);
                        Log.d("d",""+iller.length);

                        listView = (ListView) findViewById(R.id.list_iller);
                        adapter = new ArrayAdapter<String>(getBaseContext(),
                                android.R.layout.simple_list_item_1, iller);


                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view,final int i, long l) {
                                toNextFrag.setVisibility(View.VISIBLE);
                                toNextFrag.setText( iller[i] +" için devam et");
                                toNextFrag.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#22B473")));

                                toNextFrag.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(CitySelect.this, IlceSelect.class);
                                        intent.putExtra("il",iller[i]);
                                        Gson gson=new Gson();
                                        intent.putExtra("user",gson.toJson(user));
                                        intent.putExtra("option",option);
                                        CitySelect.this.startActivity(intent);
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
                        Toast.makeText(CitySelect.this, "HATA", Toast.LENGTH_SHORT).show();
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

    public CitySelect() {
        // Required empty public constructor
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), ChooseJob.class);
        final Gson gson=new Gson();
        Bundle extras = getIntent().getExtras();
        user= gson.fromJson(extras.getString("user"),User.class);
        option= gson.fromJson(extras.getString("option"),String.class);
        myIntent.putExtra("user",gson.toJson(user));
        myIntent.putExtra("option",option);
        startActivityForResult(myIntent, 0);
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // for add back arrow in action bar

        setContentView(R.layout.activity_location_select);


        toNextFrag = findViewById(R.id.button7);

        final Gson gson=new Gson();
        Bundle extras = getIntent().getExtras();
        user= gson.fromJson(extras.getString("user"),User.class);
        option= gson.fromJson(extras.getString("option"),String.class);
        System.out.println("mustafaaaa " + option);
        getCities();
    }

}
