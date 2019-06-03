package com.example.rup.halisahakiralama;


import android.content.Context;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class RadioTextAdapter extends BaseAdapter {

    private Context context;

    public RadioTextAdapter(Context context) {

        this.context = context;
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }
    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getCount() {
        return SelectMultipleDistricts.districts.size();
    }

    @Override
    public Object getItem(int position) {
        return SelectMultipleDistricts.districts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.possibledistricts_item, null, true);


            holder.district_text = (TextView) convertView.findViewById(R.id.districttext);
            holder.checkradio = (RadioButton) convertView.findViewById(R.id.adddistrictradio);



            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }

        holder.district_text.setText(SelectMultipleDistricts.districts.get(position).name+"");


        holder.checkradio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.checkradio.isChecked()){
                    SelectMultipleDistricts.addedDistricts.add(SelectMultipleDistricts.districts.get(position));
                }else{
                    SelectMultipleDistricts.addedDistricts.remove(SelectMultipleDistricts.districts.get(position));
                    holder.checkradio.setChecked(false);
                }
            }
        });

        return convertView;
    }

    private class ViewHolder {

        protected  RadioButton checkradio;
        private TextView district_text;

    }



}