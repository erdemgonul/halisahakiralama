package com.example.rup.halisahakiralama;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class SetDate extends AppCompatActivity {


    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    EditText edittext;
    Button toNextFrag;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_date);

        myCalendar = Calendar.getInstance();

        edittext= (EditText) findViewById(R.id.Birthday);
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

        edittext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(SetDate.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        toNextFrag=findViewById(R.id.setdate_to_button);
        toNextFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SetDate.this,ChooseHaliSaha.class);
                intent.putExtra("date",edittext.getText());

                SetDate.this.startActivity(intent);

            }
        });
        final String[] array=new String[]{
                "09:00","10:00","11:00","12:00","13:00","14:00","15:00","16:00","17:00","18:00","19:00"
        };

        listView=findViewById(R.id.list_hours);
        //(B) adımı
        ArrayAdapter<String> veriAdaptoru=new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, android.R.id.text1, array);

        //(C) adımı
        listView.setAdapter(veriAdaptoru);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println(i);
                Intent intent = new Intent(SetDate.this, ChooseHaliSaha.class);

                        intent.putExtra("date",edittext.getText());
                        intent.putExtra("hour",array[i]);
                        SetDate.this.startActivity(intent);


            }
        });

    }
    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edittext.setText(sdf.format(myCalendar.getTime()));
    }

}
