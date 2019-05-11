package com.example.rup.feedyourpet;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.GenericArrayType;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreatePetSummary extends android.support.v4.app.Fragment {

    Button nextbutton;
    TextView infotext;
    TextView nametext;
    TextView descriptiontext;
    TextView typetext;
    ImageView imageanimal;
    CreatePet x;
    ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        viewPager=getActivity().findViewById(R.id.viewPager);
        View v = inflater.inflate(R.layout.fragment_create_pet_summary, container, false);
        x=(CreatePet) getActivity();
        nextbutton=v.findViewById(R.id.sumsubmit_button);
        infotext=v.findViewById(R.id.suminfo);
        nametext=(TextView) v.findViewById(R.id.sumpetname);
        descriptiontext=v.findViewById(R.id.sumdescriptionanimal);
        typetext=v.findViewById(R.id.sumtypeanimal2);
        imageanimal=v.findViewById(R.id.sumimageanimal);
        viewPager=getActivity().findViewById(R.id.viewPager);

        nextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("höstt");
                x.startActivity(new Intent(getActivity(),Dashboard.class));

            }
        });

        return v;


    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser)
        {
            if(x.nick!=null && x.type!=null){
                nametext.setText("Name:   " + x.nick);
                typetext.setText("Pet:    " + x.type);
                descriptiontext.setText("2aylık bir hayvan");
                imageanimal.setImageResource(x.image);
            }


        }
    }

    public static CreatePetSummary newInstance() {
        

        return new CreatePetSummary();
    }

}
