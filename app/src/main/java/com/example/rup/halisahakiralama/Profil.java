package com.example.rup.halisahakiralama;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Profil extends AppCompatActivity {

    User user;
    Button profil,team,player,editprofile, back,createplayer,toplayer,changepassword;
    EditText username,email,teamname,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);

        final Gson gson=new Gson();
        Bundle extras = getIntent().getExtras();
        user= gson.fromJson(extras.getString("user"),User.class);

        username=findViewById(R.id.usernameprofiletext);
        email=findViewById(R.id.mailprofiletext);
        teamname=findViewById(R.id.teamnameprofiletext);
        editprofile=findViewById(R.id.editprofilebutton);
        toplayer=findViewById(R.id.toplayerpage);
        password=findViewById(R.id.passwordtextprofile);
        changepassword=findViewById(R.id.changepasswordbutton);
        back=findViewById(R.id.tonext123);

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
