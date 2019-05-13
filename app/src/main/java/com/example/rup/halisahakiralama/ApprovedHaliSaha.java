package com.example.rup.halisahakiralama;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ApprovedHaliSaha extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approved_hali_saha);
    }
    public void goHome(View v){
        Intent homePage = new Intent(this, ChooseJob.class);
        startActivity(homePage);
    }
}
