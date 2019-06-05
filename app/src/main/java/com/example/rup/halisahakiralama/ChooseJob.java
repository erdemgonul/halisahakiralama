package com.example.rup.halisahakiralama;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.codemybrainsout.ratingdialog.RatingDialog;
import com.example.rup.halisahakiralama.client.NotifyNumber;
import com.example.rup.halisahakiralama.client.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class ChooseJob extends Activity {

    TextView  userText,notificationText;
    GoogleSignInClient mGoogleSignInClient;
    Button findHaliSahaButton,findPlayerButton,findTeamButton, profilEdit, signOutButton,notificationButton;
    User user;

    @Override
    protected void onStart() {
        super.onStart();

        getNotificationNumber();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true); // for add back arrow in action bar
        setContentView(R.layout.activity_choose_job);

        final Gson gson=new Gson();
        Bundle extras = getIntent().getExtras();
        user= gson.fromJson(extras.getString("user"),User.class);




        findHaliSahaButton=findViewById(R.id.rezervationbutton);
        findPlayerButton=findViewById(R.id.oyuncubul);
        findTeamButton=findViewById(R.id.rakipbul);
        profilEdit=findViewById(R.id.editprofil);
        notificationButton=findViewById(R.id.requests_button);
        notificationText=findViewById(R.id.requests_text);




        notificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ChooseJob.this, Notifications.class);
                intent.putExtra("user",gson.toJson(user));
                intent.putExtra("option","Rezervation");
                ChooseJob.this.startActivity(intent);
            }
        });

        findHaliSahaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(ChooseJob.this, CitySelect.class);
                intent.putExtra("user",gson.toJson(user));
                intent.putExtra("option","Rezervation");
                ChooseJob.this.startActivity(intent);
            }
        });
        findPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ChooseJob.this, CitySelect.class);
                intent.putExtra("user",gson.toJson(user));
                intent.putExtra("option","FindPlayer");
                ChooseJob.this.startActivity(intent);
            }
        });

        findTeamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ChooseJob.this, IlceSelect.class);
                intent.putExtra("user",gson.toJson(user));
                intent.putExtra("option","FindTeam");
                ChooseJob.this.startActivity(intent);
            }
        });

        profilEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ChooseJob.this, Profil.class);
                intent.putExtra("user",gson.toJson(user));
                ChooseJob.this.startActivity(intent);
            }
        });

        signOutButton=findViewById(R.id.signoutbutton);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().requestProfile()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        System.out.println("BOŞ");
        getNotificationNumber();

    }

    private void signOut() {


        SharedPreferences myPreferences
                = PreferenceManager.getDefaultSharedPreferences(ChooseJob.this);
        myPreferences.edit().remove("user").commit();

        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent=new Intent(ChooseJob.this,SignIn.class);
                        ChooseJob.this.startActivity(intent);
                    }
                });
        Intent intent=new Intent(ChooseJob.this,ChooseAuth.class);
        ChooseJob.this.startActivity(intent);
    }
    private void getNotificationNumber(){

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = StaticVariables.ip_address + "notification/get/unread/number";
        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        Gson gson=new Gson();
                        NotifyNumber notifyNumber=gson.fromJson(response, NotifyNumber.class);
                        notificationText.setText(notifyNumber.id + "");
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
