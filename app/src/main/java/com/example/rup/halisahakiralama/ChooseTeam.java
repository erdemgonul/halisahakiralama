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
import com.example.rup.halisahakiralama.client.ReservationTime;
import com.example.rup.halisahakiralama.client.TeamListResponse;
import com.example.rup.halisahakiralama.client.User;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChooseTeam extends AppCompatActivity {

    String[] teamArray;
    User user;
    Bundle b;
    ListView listView;
    Button nextbutton;
    String stadiumId, reservationDate;
    ReservationTime hours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_team);

        b=getIntent().getExtras();

        final Gson gson=new Gson();
        user= gson.fromJson(b.getString("user"),User.class);
        stadiumId = b.getString("stadiumId");
        hours= gson.fromJson(b.getString("hours"),ReservationTime.class);
        reservationDate = b.getString("date");

        listView=findViewById(R.id.list_teams);
        nextbutton=findViewById(R.id.nextfromteams);
        nextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        getTeams(stadiumId);
    }

    private void getTeams(String stadiumId){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = StaticVariables.ip_address + "team/stadium/" + stadiumId;
        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("d",response);
                        Gson g = new Gson();

                        final TeamListResponse p = g.fromJson(response, TeamListResponse.class);
                        List<String> list=new ArrayList<>();
                        for(int i=0;i<p.teamDTOS.size();i++){
                            list.add(p.teamDTOS.get(i).name);
                        }
                        teamArray=list.toArray(new String[0]);
                        ArrayAdapter<String> veriAdaptoru=new ArrayAdapter<String>
                                (ChooseTeam.this, android.R.layout.simple_list_item_1, android.R.id.text1, teamArray);

                        listView.setAdapter(veriAdaptoru);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {


                                nextbutton.setVisibility(View.VISIBLE);
                                nextbutton.setText( teamArray[i] +" görüntüle");
                                nextbutton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#22B473")));


                                nextbutton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(ChooseTeam.this, ChooseTeamResult.class);
                                        intent.putExtra("name", p.teamDTOS.get(i).name);
                                        intent.putExtra("team_id", p.teamDTOS.get(i).id + "");
                                        intent.putExtra("option", b.getString("option"));
                                        Gson gson = new Gson();
                                        intent.putExtra("user", gson.toJson(user));
                                        intent.putExtra("team", gson.toJson(p.teamDTOS.get(i)));
                                        intent.putExtra("stadiumId", b.getString("stadiumId"));
                                        intent.putExtra("stadium", b.getString("stadium"));
                                        intent.putExtra("date", reservationDate);
                                        intent.putExtra("hours", gson.toJson(hours));

                                        ChooseTeam.this.startActivity(intent);
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
