package com.example.rup.halisahakiralama;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rup.halisahakiralama.client.City;
import com.example.rup.halisahakiralama.client.PlayerListResponse;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ChoosePlayer extends AppCompatActivity {

    City[] array;
    String[] playerArray;
    Button nextbutton;
    ListView listView;
    TextView textView;
    User user;
    String il,ilce;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    EditText dateText;

    public ChoosePlayer() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // for add back arrow in action bar

        setContentView(R.layout.activity_choose_player);


        Bundle  b=getIntent().getExtras();
        il=b.getString("il");
        ilce=b.getString("ilce") ;
        final Gson gson=new Gson();
        user= gson.fromJson(b.getString("user"),User.class);

        listView=findViewById(R.id.list_player);
        textView=findViewById(R.id.playerselect_text);

        nextbutton=findViewById(R.id.button9);

        myCalendar = Calendar.getInstance();

        dateText= (EditText) findViewById(R.id.dateselect);
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        dateText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                createDatePicker();

            }
        });
        dateText.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int inType = dateText.getInputType(); // backup the input type
                dateText.setInputType(InputType.TYPE_NULL); // disable soft input
                dateText.onTouchEvent(event); // call native handler
                dateText.setInputType(inType); // restore input type
                return true; // consume touch even
            }
        });

        String[] items = new String[24];
        for(int i = 0; i<24; i++) {
            items[i] = i + "";
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        Spinner dropdown = findViewById(R.id.beginhour_input);
        dropdown.setAdapter(adapter);
        Spinner dropdown2 = findViewById(R.id.endhour_input);
        dropdown2.setAdapter(adapter);

    }

    private void createDatePicker(){
        new DatePickerDialog(ChoosePlayer.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }
    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        String dateStr=sdf.format(myCalendar.getTime());
        dateText.setText(dateStr);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), IlceSelect.class);

        final Gson gson=new Gson();
        Bundle extras = getIntent().getExtras();
        user= gson.fromJson(extras.getString("user"),User.class);
        myIntent.putExtra("user",gson.toJson(user));
        myIntent.putExtra("il",il);
        myIntent.putExtra("ilce",ilce);
        startActivityForResult(myIntent, 0);
        return true;
    }
}
