package com.example.rup.feedyourpet;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AnimalArrayAdapter extends BaseAdapter {
    Context context;
    List<Animal> list;
    public AnimalArrayAdapter(Context context, List<Animal> array){
        this.context=context;
        this.list=array;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Animal animal=getItem(position);
        convertView= LayoutInflater.from(context).inflate(R.layout.listelement,parent,false);


        ImageView imageView=(ImageView) convertView.findViewById(R.id.imageanimal);
        TextView nameView=(TextView) convertView.findViewById(R.id.sumtypeanimal);
        TextView descView=(TextView) convertView.findViewById(R.id.descriptionanimal);


        imageView.setImageResource(animal.imageIcon);
        nameView.setText(animal.type);
        descView.setText(animal.description);
        return convertView;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Animal getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
