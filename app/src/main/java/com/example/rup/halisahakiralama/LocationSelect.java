package com.example.rup.halisahakiralama;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.rup.halisahakiralama.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


public class LocationSelect extends Activity {

    ViewPager pager;
    Button toNextFrag;

    private Spinner spinner,spinner2;
    private static final String[] paths = {"istanbul", "izmir", "item 3"};
    private static final String[] ilceler = {"kadıköy", "maltepe"};
    private static final String[] ilceler2 = {"bornova"};
    ArrayAdapter<String>adapter;
    ArrayAdapter<String>adapter2;
    ListView listView;

    public LocationSelect() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_select);
        halisaha halisaha1 = new halisaha(1, "ayışığı spor tesisleri", "istanbul", "maltepe", "yucelen sokak", 123, 155);
        halisaha halisaha2 = new halisaha(2, "caferaga tesisleri", "istanbul", "kadıköy", "yucelesadasdsadasdadn sokak", 160, 152);
        halisaha halisaha3 = new halisaha(2, "bornova spor", "izmir", "bornova", "yucelesadasdsadasdadn sokak", 160, 152);
        final ArrayList<halisaha> halisahaList = new ArrayList<>();
        halisahaList.add(halisaha1);
        halisahaList.add(halisaha2);


        spinner = (Spinner) findViewById(R.id.il_spinner);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        spinner2 = (Spinner) findViewById(R.id.ilce_spinner);
        adapter2 = new ArrayAdapter<String>(getBaseContext(),
                android.R.layout.simple_spinner_item, paths);

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (paths[position] == "izmir") {
                    adapter2 = new ArrayAdapter<String>(getBaseContext(),
                            android.R.layout.simple_spinner_item, ilceler2);

                } else if (paths[position] == "istanbul") {
                    adapter2 = new ArrayAdapter<String>(getBaseContext(),
                            android.R.layout.simple_spinner_item, ilceler);
                }

                spinner2.setAdapter(adapter2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                /*
                ArrayList<String> a=new ArrayList<>();
                for(int i=0;i<halisahaList.size();i++){
                    if(halisahaList.get(i).ilce.equals(spinner2.getSelectedItem()))
                        a.add(halisahaList.get(i).name);
                }
                ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>
                        (getActivity().getBaseContext(), android.R.layout.simple_list_item_1, a);

                listView.setAdapter(arrayAdapter);
                */
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        toNextFrag = findViewById(R.id.location_to_button);
        toNextFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LocationSelect.this, SetDate.class);
                intent.putExtra("il", spinner.getSelectedItem().toString());
                intent.putExtra("ilce", spinner2.getSelectedItem().toString());
                LocationSelect.this.startActivity(intent);

            }
        });


    }
}
