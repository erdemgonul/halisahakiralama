package com.example.rup.halisahakiralama;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

import com.google.firebase.FirebaseApp;
import com.google.gson.Gson;


public class StartApp extends Activity {

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 3000;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_start_app);

        final Gson gson=new Gson();


        SharedPreferences myPreferences
                = PreferenceManager.getDefaultSharedPreferences(StartApp.this);
        final User user =gson.fromJson(myPreferences.getString("user", null),User.class);



        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */


                if(user!=null && !user.equals("unknown")){
                    Intent mainIntent = new Intent(StartApp.this, ChooseJob.class);
                    mainIntent.putExtra("user",gson.toJson(user));
                    StartApp.this.startActivity(mainIntent);
                }else{
                    Intent mainIntent = new Intent(StartApp.this, ChooseAuth.class);
                    StartApp.this.startActivity(mainIntent);
                    StartApp.this.finish();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}