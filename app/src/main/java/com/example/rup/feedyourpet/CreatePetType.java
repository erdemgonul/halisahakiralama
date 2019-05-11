package com.example.rup.feedyourpet;


import android.os.Bundle;
import android.app.Fragment;

import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



/**
 * A simple {@link Fragment} subclass.
 */
public class CreatePetType extends android.support.v4.app.Fragment{


    Button nextbutton;
    ViewPager viewPager;
    ListView listView;
    String nick;
    CreatePet x;
    public String typey;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_create_pet_type, container, false);

         x=(CreatePet) getActivity();
        listView=v.findViewById(R.id.pet_type_list);
        nextbutton=v.findViewById(R.id.createpetnext2_button);
        viewPager=getActivity().findViewById(R.id.viewPager);
        nextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                x.setType(typey);
                viewPager.setCurrentItem(2, true);
            }
        });
        Animal[] arrayAnimal=new Animal[]{
                new Animal("Cat","a sweet cat",R.drawable.cat),
                new Animal("Dog","barking lorem ipsum",R.drawable.dog),
                new Animal("Fish","easy to care",R.drawable.fish)
        };

        List<Animal> list=new ArrayList<Animal>(Arrays.asList(arrayAnimal));
        AnimalArrayAdapter adapter=new AnimalArrayAdapter(getActivity(),list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println(i);
                switch (i){
                    case 0:
                        typey="Cat";
                        break;
                    case 1:
                        typey="Dog";
                        break;
                    case 2:
                        typey="Fish";
                        break;
                }
            }
        });

        return v;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            this.nick=x.nick;
        }

    }

    public static CreatePetType newInstance() {
        return new CreatePetType();
    }

}
