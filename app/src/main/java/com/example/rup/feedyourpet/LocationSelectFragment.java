package com.example.rup.feedyourpet;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;


public class LocationSelectFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ViewPager pager;
    Button toNextFrag;

    private Spinner spinner,spinner2;
    private static final String[] paths = {"istanbul", "izmir", "item 3"};
    private static final String[] ilceler = {"kadıköy", "maltepe"};
    private static final String[] ilceler2 = {"bornova"};
    ArrayAdapter<String>adapter;
    ArrayAdapter<String>adapter2;
    ListView listView;

    public LocationSelectFragment() {
        // Required empty public constructor
    }

    public static LocationSelectFragment newInstance(String param1, String param2) {
        LocationSelectFragment fragment = new LocationSelectFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment




        return inflater.inflate(R.layout.fragment_location_select, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        halisaha halisaha1=new halisaha(1,"ayışığı spor tesisleri","istanbul","maltepe","yucelen sokak",123,155);
        halisaha halisaha2=new halisaha(2,"caferaga tesisleri","istanbul","kadıköy","yucelesadasdsadasdadn sokak",160,152);
        halisaha halisaha3=new halisaha(2,"bornova spor","izmir","bornova","yucelesadasdsadasdadn sokak",160,152);
        final ArrayList<halisaha> halisahaList=new ArrayList<>();
        halisahaList.add(halisaha1);
        halisahaList.add(halisaha2);


        spinner = (Spinner)getView().findViewById(R.id.il_spinner);
        adapter = new ArrayAdapter<String>(getActivity().getBaseContext(),
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        spinner2 = (Spinner)getView().findViewById(R.id.ilce_spinner);
        adapter2 = new ArrayAdapter<String>(getActivity().getBaseContext(),
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(paths[position]=="izmir"){
                    adapter2 = new ArrayAdapter<String>(getActivity().getBaseContext(),
                            android.R.layout.simple_spinner_item,ilceler2);

                }else if(paths[position]=="istanbul"){
                    adapter2 = new ArrayAdapter<String>(getActivity().getBaseContext(),
                            android.R.layout.simple_spinner_item,ilceler);
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

        toNextFrag=getView().findViewById(R.id.location_to_button);
        toNextFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Dashboard dashboard=(Dashboard) getActivity();
               pager=dashboard.pager;
                pager.setCurrentItem(1);
            }
        });
        
        
  }

    public static LocationSelectFragment newInstance() {
        
        Bundle args = new Bundle();
        
        LocationSelectFragment fragment = new LocationSelectFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
