package com.example.rup.halisahakiralama;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ChooseRolePlayer extends AppCompatActivity {


    Button keeper_btn,def1_btn,def2_btn,mid1_btn,mid2_btn,mid3_btn,forward_btn,tonextbutton;
    ListView listView;
    User user;
    Bundle extras;
    ArrayAdapter<String> adapter;
    String username,surname,address,phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_role_player);

        final Gson gson=new Gson();
        extras = getIntent().getExtras();
        user= gson.fromJson(extras.getString("user"),User.class);
        username=extras.getString("name");
        surname=extras.getString("name");
        address=extras.getString("name");
        phone=extras.getString("name");
        System.out.println(username);
        keeper_btn=findViewById(R.id.buttonkeeper);
        def1_btn=findViewById(R.id.buttondef1);
        def2_btn=findViewById(R.id.buttondef2);
        mid1_btn=findViewById(R.id.buttonmid1);
        mid2_btn=findViewById(R.id.buttonmid2);
        mid3_btn=findViewById(R.id.buttonmid3);
        forward_btn=findViewById(R.id.buttonforward);
        tonextbutton=findViewById(R.id.tonextpossiblecities);
        listView=findViewById(R.id.mevki_list);

        final Button[] roleList=new Button[]{keeper_btn,def1_btn,def2_btn,mid1_btn,mid2_btn,mid3_btn,forward_btn};


        final List<String> sentRoles=new ArrayList<>();
        for (int i = 0; i < roleList.length; i++) {
            roleList[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(v.getBackgroundTintList().getDefaultColor()==getResources().getColor(R.color.Crimson)){
                        v.setBackgroundTintList(getBaseContext().getResources().getColorStateList(R.color.myBlue));

                        switch (v.getId()){

                            case R.id.buttonkeeper:
                                sentRoles.remove("GOALKEEPER");
                                break;
                            case R.id.buttondef1:
                                sentRoles.remove("DEFENDER_1");
                                break;
                            case R.id.buttondef2:
                                sentRoles.remove("DEFENDER_2");
                                break;
                            case R.id.buttonmid1:
                                sentRoles.remove("MIDFIELDER_1");
                                break;
                            case R.id.buttonmid2:
                                sentRoles.remove("MIDFIELDER_2");
                                break;
                            case R.id.buttonmid3:
                                sentRoles.remove("MIDFIELDER_3");
                                break;
                            case R.id.buttonforward:
                                sentRoles.remove("STRIKER");
                                break;
                        }
                    }else{
                        v.setBackgroundTintList(getBaseContext().getResources().getColorStateList(R.color.Crimson));

                        switch (v.getId()){

                            case R.id.buttonkeeper:
                                sentRoles.add("GOALKEEPER");
                                break;
                            case R.id.buttondef1:
                                sentRoles.add("DEFENDER_1");
                                break;
                            case R.id.buttondef2:
                                sentRoles.add("DEFENDER_2");
                                break;
                            case R.id.buttonmid1:
                                sentRoles.add("MIDFIELDER_1");
                                break;
                            case R.id.buttonmid2:
                                sentRoles.add("MIDFIELDER_2");
                                break;
                            case R.id.buttonmid3:
                                sentRoles.add("MIDFIELDER_3");
                                break;
                            case R.id.buttonforward:
                                sentRoles.add("STRIKER");
                                break;
                        }
                    }

                    adapter = new ArrayAdapter<String>(getBaseContext(),
                            android.R.layout.simple_list_item_1, sentRoles);


                    listView.setAdapter(adapter);
                }
            });
        }

        tonextbutton.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Intent intent = new Intent(ChooseRolePlayer.this, CitySelect.class);


                                                String x=extras.getString("name");
                                                intent.putExtra("name",username);
                                                intent.putExtra("surname",extras.getString("surname"));
                                                intent.putExtra("phone",extras.getString("phone"));
                                                intent.putExtra("address",extras.getString("address"));
                                                intent.putExtra("roles",gson.toJson(sentRoles));
                                                intent.putExtra("user",gson.toJson(user));
                                                intent.putExtra("fromCreatePlayer",true);
                                                ChooseRolePlayer.this.startActivity(intent);
                                            }
                                        });



    }
}
