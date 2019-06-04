package com.example.rup.halisahakiralama;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rup.halisahakiralama.client.Player;
import com.example.rup.halisahakiralama.client.Team;
import com.example.rup.halisahakiralama.client.User;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Profil extends AppCompatActivity {

    User user;
    Button team,editprofile, back,toplayer,changepassword;
    EditText username,email,teamname,password,playernameEdit;
    LinearLayout teamlayout,playerlayout,maillayout,parolalayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_profile);

        final Gson gson=new Gson();
        Bundle extras = getIntent().getExtras();
        user= gson.fromJson(extras.getString("user"),User.class);
        playerlayout=findViewById(R.id.profileplayername);
        playernameEdit=findViewById(R.id.playernameprofile);
        parolalayout=findViewById(R.id.passwordbar);
        teamlayout=findViewById(R.id.profileteam2layout);
        username=findViewById(R.id.usernameprofiletext);
        email=findViewById(R.id.mailprofiletext);
        teamname=findViewById(R.id.teamnameprofiletext);
        editprofile=findViewById(R.id.editprofilebutton);
        toplayer=findViewById(R.id.toplayerpage);
        password=findViewById(R.id.passwordtextprofile);
        changepassword=findViewById(R.id.changepasswordbutton);
        back=findViewById(R.id.tonext123);
        maillayout=findViewById(R.id.profileuserlayout2);
        team=findViewById(R.id.toteampage);


        if(user.isGoogleSign){
            parolalayout.setVisibility(View.GONE);
            maillayout.setVisibility(View.GONE);
        }


        team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Profil.this, CreateTeam.class);
                intent.putExtra("user",gson.toJson(user));
                Profil.this.startActivity(intent);
            }
        });
        toplayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Profil.this, ShowPlayer.class);
                intent.putExtra("user",gson.toJson(user));
                Profil.this.startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(Profil.this, ChooseJob.class);
                intent.putExtra("user",gson.toJson(user));
                Profil.this.startActivity(intent);
            }
        });
        toplayer.setOnClickListener(new View.OnClickListener() {
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

                try {
                    callEditProfile();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent intent=new Intent(Profil.this, ChooseJob.class);
                intent.putExtra("user",gson.toJson(user));
                Profil.this.startActivity(intent);
            }
        });
        changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Profil.this, ChangePassword.class);
                intent.putExtra("user",gson.toJson(user));
                Profil.this.startActivity(intent);
            }
        });

        username.setText("" + user.username);
        email.setText("" + user.email);
        getTeam();
        getPlayer();
    }

    public void getPlayer(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = StaticVariables.ip_address + "player/user/" + user.id + "";
        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Gson g = new Gson();

                            if(response==null || response.equals("")){
                                playerlayout.setVisibility(View.GONE);
                                toplayer.setText("Oyuncu Yarat");
                            }else{

                                JSONObject jsonObject= null;
                                try {
                                    jsonObject = new JSONObject(response);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                String x= null;
                                try {
                                    x = jsonObject.getString("playerDTO");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Player p = g.fromJson(x, Player.class);
                                    if(p != null) {
                                        playernameEdit.setText("" + p.name);
                                        toplayer.setText("Oyuncuyu Değiştir");
                                    }
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
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.NO_WRAP);
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
                        Gson g = new Gson();
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String x=jsonObject.getString("teamDTO");
                            if(x.equals("null") || x.isEmpty()){
                               teamlayout.setVisibility(View.GONE);
                            }else{
                                Team p = g.fromJson(x, Team.class);
                                if(p != null) {
                                    teamname.setText("" + p.name);
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
                        } catch (JSONException e) {

                        }


                    }
                },
                new com.android.volley.Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        teamlayout.setVisibility(View.GONE);
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
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
        Intent myIntent = new Intent(getApplicationContext(), ChooseJob.class);
        final Gson gson=new Gson();
        Bundle extras = getIntent().getExtras();
        user= gson.fromJson(extras.getString("user"),User.class);
        myIntent.putExtra("user",gson.toJson(user));
        startActivityForResult(myIntent, 0);
        return true;
    }

    private void callEditProfile() throws JSONException {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = StaticVariables.ip_address + "update/user";
        JSONObject jsonBody = new JSONObject();

        jsonBody.put("username", username.getText()+"");
        jsonBody.put("id", user.id);
        jsonBody.put("password", "");
        jsonBody.put("email", email.getText()+"");

        JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
               signOut();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                System.out.println("FUCK");
                Toast.makeText(Profil.this, "FUCKKKK ", Toast.LENGTH_SHORT).show();

            }
        });

        queue.add(jsonObject);
    }
    private void signOut() {


        SharedPreferences myPreferences
                = PreferenceManager.getDefaultSharedPreferences(Profil.this);
        myPreferences.edit().remove("user").commit();


        Intent intent=new Intent(Profil.this,ChooseAuth.class);
        Profil.this.startActivity(intent);
    }
}
