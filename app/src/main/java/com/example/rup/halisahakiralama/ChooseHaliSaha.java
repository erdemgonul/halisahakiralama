package com.example.rup.halisahakiralama;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
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
import com.example.rup.halisahakiralama.client.City;
import com.google.gson.Gson;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChooseHaliSaha extends Activity {

    City[] array;
    String[] nameCities;
    Button nextbutton;
    ViewPager viewPager;
    ListView listView;

    public ChooseHaliSaha() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_hali_saha);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://192.168.56.1:8080/cities";
        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("d",response);
                        Gson g = new Gson();

                        City[] p = g.fromJson(response, City[].class);
                        Log.d("d",p[0].name);
                        List<String> list=new ArrayList<>();
                        for(int i=0;i<p.length;i++){
                            list.add(p[i].name);
                        }
                        Log.d("d",list.get(0));

                        ArrayAdapter<String> veriAdaptoru=new ArrayAdapter<String>
                                (ChooseHaliSaha.this, android.R.layout.simple_list_item_1, android.R.id.text1, list.toArray(new String[0]));

                        listView.setAdapter(veriAdaptoru);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                System.out.println(i);
                                Intent intent = new Intent(ChooseHaliSaha.this,ShowHaliSaha.class);

                                intent.putExtra("name",nameCities[i]);
                                ChooseHaliSaha.this.startActivity(intent);


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
                String creds = String.format("%s:%s","admin","admin");
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                params.put("Authorization", auth);
                return params;
            }
        };
        queue.add(getRequest);





        listView=findViewById(R.id.list_halisaha);
        //(B) adımı


        nextbutton=findViewById(R.id.createpetnext2_button);

    }
}
