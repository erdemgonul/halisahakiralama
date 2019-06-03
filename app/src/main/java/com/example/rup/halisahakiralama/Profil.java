package com.example.rup.halisahakiralama;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
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
import com.example.rup.halisahakiralama.client.Player;
import com.example.rup.halisahakiralama.client.Team;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Profil extends AppCompatActivity {

    User user;
    Button profil,team,player,editprofile, back,createplayer;
    EditText username,email,teamname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);

        final Gson gson=new Gson();
        Bundle extras = getIntent().getExtras();
        user= gson.fromJson(extras.getString("user"),User.class);


        //userText=findViewById(R.id.textView3);

        //userText.setText("Hoşgeldiniz : " + user.username);

        username=findViewById(R.id.usernameprofiletext);
        email=findViewById(R.id.mailprofiletext);
        teamname=findViewById(R.id.teamnameprofiletext);
        editprofile=findViewById(R.id.editprofilebutton);
        createplayer=findViewById(R.id.create_player_button);
        back=findViewById(R.id.backbutton);
        String text = "Kullanıcı Adı : " + user.username + "\n" + "E-Posta Adresi : " + user.email;



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(Profil.this, ChooseJob.class);
                intent.putExtra("user",gson.toJson(user));
                Profil.this.startActivity(intent);
            }
        });

        createplayer.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          Intent intent=new Intent(Profil.this, CreatePlayer.class);
                                          intent.putExtra("user",gson.toJson(user));
                                          Profil.this.startActivity(intent);
                                      }


            });
        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Profil.this, CreatePlayer.class);
                intent.putExtra("user",gson.toJson(user));
                Profil.this.startActivity(intent);
            }


        });


        getPlayer();
        getTeam();



    }

    public void getPlayer(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = StaticVariables.ip_address + "player/user/" + user.id + "";
        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Profil.this,"hey", Toast.LENGTH_LONG).show();
                        // response
                        Log.d("d",response);
                        Gson g = new Gson();

                        Player p = g.fromJson(response, Player.class);
                        if(p != null) {
                            System.out.println("mustafaaaaa   " + p);
                            String text = "Oyuncu Adı : " + p.name + "\n" + "Oyuncu Soyadı : " + p.surName + "Oyuncu Mevki : " + p.position + "\n" + "Puan : " + p.rate;
                            player.setText(text);
                        }
                        else {
                            player.setText("Oyuncu Yarat");

                        }



                    }
                },
                new com.android.volley.Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Toast.makeText(Profil.this, "HATA", Toast.LENGTH_SHORT).show();
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

    public void getTeam(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = StaticVariables.ip_address + "team/user/" + user.id + "";
        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Profil.this,"hey", Toast.LENGTH_LONG).show();
                        // response
                        Log.d("d",response);
                        Gson g = new Gson();

                        Team p = g.fromJson(response, Team.class);
                        if(p != null) {
                            System.out.println("mustafaaaaa   " + p);
                            String text = "Takım Adı : " + p.name + "\n" + "Puan : " + p.rate;
                            team.setText(text);
                        }
                        else {
                            team.setText("Takım Yarat");
                            team.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            });
                        }



                    }
                },
                new com.android.volley.Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Toast.makeText(Profil.this, "HATA", Toast.LENGTH_SHORT).show();
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
        Intent myIntent = new Intent(getApplicationContext(), ChooseJob.class);
        final Gson gson=new Gson();
        Bundle extras = getIntent().getExtras();
        user= gson.fromJson(extras.getString("user"),User.class);
        myIntent.putExtra("user",gson.toJson(user));
        startActivityForResult(myIntent, 0);
        return true;
    }
}
