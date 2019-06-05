package com.example.rup.halisahakiralama;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.rup.halisahakiralama.client.Player;
import com.example.rup.halisahakiralama.client.PlayerListResponse;
import com.example.rup.halisahakiralama.client.ReservationTime;
import com.example.rup.halisahakiralama.client.User;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ChoosePlayer extends AppCompatActivity {

    City[] array;
    String[] playerArray;
    Button nextbutton;
    ListView listView;
    TextView textView;
    User user;
    String option, stadiumId, reservationDate;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    EditText dateText;
    Player player;

    ArrayAdapter<String>adapter;
    ArrayAdapter<String>adapter2;
    List<Player>  playerList;
    List<District>  ilcelerList;
    ReservationTime hours;
    public ChoosePlayer() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_player);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Bundle  b=getIntent().getExtras();
        option=b.getString("option");
        final Gson gson=new Gson();
        user= gson.fromJson(b.getString("user"),User.class);
        hours= gson.fromJson(b.getString("hours"),ReservationTime.class);
        stadiumId = b.getString("stadiumId");
        reservationDate = b.getString("date");

        listView=findViewById(R.id.list_player);
        textView=findViewById(R.id.playerselect_text);

        nextbutton=findViewById(R.id.button9);




        RequestQueue queue = Volley.newRequestQueue(this);
        String url = StaticVariables.ip_address + "player/stadium/" + stadiumId;
        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(ChoosePlayer.this,"hey", Toast.LENGTH_LONG).show();
                        // response
                        Log.d("d",response);
                        Gson g = new Gson();

                        PlayerListResponse p = g.fromJson(response, PlayerListResponse.class);
                        playerList=p.players;
                        final List<String> list=new ArrayList<>();
                        for(int i=0;i<p.players.size();i++){//burası kullanıcının adını ve mevkiini yazcağımız yer
                            list.add(p.players.get(i).name + p.players.get(i).positions);
                        }

                        listView = (ListView) findViewById(R.id.list_player);
                        adapter = new ArrayAdapter<String>(getBaseContext(),
                                android.R.layout.simple_list_item_1, list.toArray(new String[0]));


                        listView.setAdapter(adapter);


                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view,final int i, long l) {


                                nextbutton.setVisibility(View.VISIBLE);
                                nextbutton.setText( "oyuncuyu görüntüle");
                                nextbutton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#22B473")));


                                player=playerList.get(i);

                                nextbutton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(ChoosePlayer.this,ChoosePlayerResult.class);

                                        Gson gson=new Gson();
                                        intent.putExtra("player",gson.toJson(player));
                                        intent.putExtra("date", reservationDate);
                                        intent.putExtra("user", gson.toJson(user));
                                        intent.putExtra("stadiumId", stadiumId);
                                        intent.putExtra("hours", gson.toJson(hours));
                                        ChoosePlayer.this.startActivity(intent);
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
                        Toast.makeText(ChoosePlayer.this, "HATA", Toast.LENGTH_SHORT).show();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), IlceSelect.class);

        final Gson gson=new Gson();
        Bundle extras = getIntent().getExtras();
        user= gson.fromJson(extras.getString("user"),User.class);
        myIntent.putExtra("user",gson.toJson(user));
        startActivityForResult(myIntent, 0);
        return true;
    }
}
