package com.example.rup.halisahakiralama;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.rup.halisahakiralama.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ChooseHaliSaha extends Activity {


    Button nextbutton;
    ViewPager viewPager;
    ListView listView;

    public ChooseHaliSaha() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_hali_saha);

        final String[] array=new String[]{
                "ayışığı","caferağa","memleket","cips","papates spor tesisleri","ev","sporcu","asdsadasda","dadsad","dadsaads","dassdada"
        };

        listView=findViewById(R.id.list_halisaha);
        //(B) adımı
        ArrayAdapter<String> veriAdaptoru=new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, android.R.id.text1, array);

        //(C) adımı
        listView.setAdapter(veriAdaptoru);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println(i);
                Intent intent = new Intent(ChooseHaliSaha.this,ShowHaliSaha.class);

                intent.putExtra("name",array[i]);
                ChooseHaliSaha.this.startActivity(intent);


            }
        });

        nextbutton=findViewById(R.id.createpetnext2_button);

    }
}
