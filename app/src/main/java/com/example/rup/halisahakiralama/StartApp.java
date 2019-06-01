package com.example.rup.halisahakiralama;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.FirebaseApp;


public class StartApp extends Activity {

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 3000;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_start_app);

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(StartApp.this, ChooseAuth.class);
               StartApp.this.startActivity(mainIntent);
                StartApp.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}