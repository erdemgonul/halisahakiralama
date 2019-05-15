package com.example.rup.halisahakiralama;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rup.halisahakiralama.R;
import com.example.rup.halisahakiralama.client.City;
import com.example.rup.halisahakiralama.client.District;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class LocationSelect extends Activity {

    ViewPager pager;
    Button toNextFrag;

    private Spinner spinner,spinner2;
    private  String[] iller={"null"};
    private  String[] ilceler = {"null"};
    ArrayAdapter<String>adapter;
    ArrayAdapter<String>adapter2;
    ListView listView;
    List<City>  illerList;
    List<District>  ilcelerList;

    public void getCities(){
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
                        illerList= Arrays.asList(p);
                        List<String> list=new ArrayList<>();
                        for(int i=0;i<p.length;i++){
                            list.add(p[i].name);
                        }
                        iller=list.toArray(new String[0]);
                        Log.d("d",""+iller.length);

                        spinner = (Spinner) findViewById(R.id.il_spinner);
                        adapter = new ArrayAdapter<String>(getBaseContext(),
                                android.R.layout.simple_spinner_item, iller);

                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(adapter);

                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                getDistrictsByCity(iller[position]);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

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
    }
    public void getDistrictsByCity(String cityName){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://192.168.56.1:8080/"+ cityName  + "/districts";
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

                        spinner2 = (Spinner) findViewById(R.id.ilce_spinner);
                        adapter2 = new ArrayAdapter<String>(getBaseContext(),
                                android.R.layout.simple_spinner_item, ilceler);

                        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner2.setAdapter(adapter2);

                        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                toNextFrag.setText( ilceler[position] + " ilçesi için devam et");
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

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
    }
    public LocationSelect() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_select);

        toNextFrag = findViewById(R.id.location_to_button);
        toNextFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LocationSelect.this, SetDate.class);
                intent.putExtra("il",new Gson().toJson(illerList.get(spinner.getSelectedItemPosition())));
                intent.putExtra("ilce",new Gson().toJson(ilcelerList.get(spinner2.getSelectedItemPosition())));
                LocationSelect.this.startActivity(intent);

            }
        });

        getCities();















    }

}
