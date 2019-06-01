package com.example.rup.halisahakiralama;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IlceSelect extends AppCompatActivity {

    Button toNextFrag;


    private ListView listView;
    private  String[] ilceler = {"null"};
    ArrayAdapter<String>adapter;
    List<District>  ilcelerList;
    TextView textView, header;
    String  ilName, option;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // for add back arrow in action bar

        setContentView(R.layout.activity_ilce_select);



        textView=(TextView) findViewById(R.id.ilceler_text);
        header=(TextView) findViewById(R.id.textView4);
        header.setText(StaticVariables.title);
        listView=(ListView) findViewById(R.id.list_ilceler);
        toNextFrag=(Button) findViewById(R.id.button8);

        Bundle extras = getIntent().getExtras();
        ilName =  extras.getString("il");
        final Gson gson=new Gson();
        user= gson.fromJson(extras.getString("user"),User.class);
        option= extras.getString("option");
        System.out.println("mustafaaaa " + option);
        getDistrictsByCity(ilName);


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
                                            ilcelerList= Arrays.asList(p);
                                            List<String> list=new ArrayList<>();
                                            for(int i=0;i<p.length;i++){
                                                list.add(p[i].name);
                                            }
                                            ilceler=list.toArray(new String[0]);

                                            listView = (ListView) findViewById(R.id.list_ilceler);
                                            adapter = new ArrayAdapter<String>(getBaseContext(),
                                                    android.R.layout.simple_list_item_1, ilceler);


                                            listView.setAdapter(adapter);
                                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view,final int i, long l) {
                                toNextFrag.setVisibility(View.VISIBLE);
                                toNextFrag.setText( ilceler[i] +" için devam et");
                                toNextFrag.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#22B473")));

                                toNextFrag.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                            Intent intent = new Intent(IlceSelect.this, ChooseHaliSaha.class);
                                            intent.putExtra("ilce",ilceler[i]);
                                            intent.putExtra("il",ilName);
                                            intent.putExtra("option",option);
                                            final Gson gson=new Gson();
                                            intent.putExtra("user",gson.toJson(user));
                                            IlceSelect.this.startActivity(intent);

                                    }
                                });
                            }
                        });



                        textView.setText(cityName + " içinde bir ilçe seç");
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), CitySelect.class);
        final Gson gson=new Gson();
        Bundle extras = getIntent().getExtras();
        user= gson.fromJson(extras.getString("user"),User.class);
        option= gson.fromJson(extras.getString("option"),String.class);
        myIntent.putExtra("user",gson.toJson(user));
        myIntent.putExtra("il",ilName);
        myIntent.putExtra("option",option);
        startActivityForResult(myIntent, 0);
        return true;
    }

}
