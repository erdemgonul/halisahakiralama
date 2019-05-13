package com.example.rup.halisahakiralama;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.rup.halisahakiralama.R;

public class ChooseJob extends Activity {


    Button findHaliSahaButton,findPlayerButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_job);

        findHaliSahaButton=findViewById(R.id.button2);
        findPlayerButton=findViewById(R.id.button3);
        findHaliSahaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChooseJob.this.startActivity(new Intent(ChooseJob.this,LocationSelect.class));
            }
        });
        findPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChooseJob.this.startActivity(new Intent(ChooseJob.this,LocationSelect.class));
            }
        });
    }
}
