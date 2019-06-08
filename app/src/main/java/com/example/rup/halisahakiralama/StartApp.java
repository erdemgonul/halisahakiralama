package com.example.rup.halisahakiralama;
import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

import com.example.rup.halisahakiralama.client.User;
import com.google.firebase.FirebaseApp;
import com.google.gson.Gson;


public class StartApp extends Activity {

    private final int SPLASH_DISPLAY_LENGTH = 3000;
    User user;
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_start_app);
    }
    @Override
    protected void onStart() {
        super.onStart();

        final Gson gson=new Gson();
        SharedPreferences myPreferences
                = PreferenceManager.getDefaultSharedPreferences(StartApp.this);
        user =gson.fromJson(myPreferences.getString("user", null),User.class);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */


                if(user!=null && !user.equals("unknown")){
                    if(user.role.equals("ROLE_USER")){
                        Intent mainIntent = new Intent(StartApp.this, ChooseJob.class);
                        mainIntent.putExtra("user",gson.toJson(user));
                        StartApp.this.startActivity(mainIntent);

                    }else {
                        Intent mainIntent = new Intent(StartApp.this, ChooseJobOwner.class);
                        mainIntent.putExtra("user",gson.toJson(user));
                        StartApp.this.startActivity(mainIntent);
                    }

                }else{
                    Intent mainIntent = new Intent(StartApp.this, ChooseAuth.class);
                    StartApp.this.startActivity(mainIntent);
                    StartApp.this.finish();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}