package com.example.rup.halisahakiralama;


import android.content.Context;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rup.halisahakiralama.client.User;

import java.util.HashMap;
import java.util.Map;


public class Adapter  extends BaseAdapter {

    private Context context;

    public Adapter(Context context) {

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
        return Notifications.notifications.size();
    }

    @Override
    public Object getItem(int position) {
        return Notifications.notifications.get(position);
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
            convertView = inflater.inflate(R.layout.notify_item, null, true);


            holder.willinguser = (TextView) convertView.findViewById(R.id.willing_username);
            holder.date = (TextView) convertView.findViewById(R.id.notify_date);
            holder.textView=(TextView) convertView.findViewById(R.id.notify_text);
            holder.btn_ok = (Button) convertView.findViewById(R.id.notify_ok_button);
            holder.btn_no = (Button) convertView.findViewById(R.id.notify_no_button);
            holder.approvedText=convertView.findViewById(R.id.approved_text);


            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }

        holder.willinguser.setText(Notifications.notifications.get(position).getUser());
        holder.date.setText(String.valueOf(Notifications.notifications.get(position).getDate()));
        holder.textView.setText(String.valueOf(Notifications.notifications.get(position).getText()));


        if(Notifications.notifications.get(position).getApproval()){
            holder.btn_ok.setVisibility(View.VISIBLE);
            holder.btn_no.setVisibility(View.VISIBLE);
        }else{
            holder.btn_ok.setVisibility(View.INVISIBLE);
            holder.btn_no.setVisibility(View.INVISIBLE);
        }

        holder.btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                  approveRequest(position,Notifications.context,Notifications.user);
                    holder.btn_ok.setVisibility(View.GONE);
                    holder.btn_no.setVisibility(View.GONE);
                    holder.approvedText.setVisibility(View.VISIBLE);
            }
        });
        holder.btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cancelRequest(position,Notifications.context,Notifications.user);
                holder.btn_ok.setVisibility(View.GONE);
                holder.btn_no.setVisibility(View.GONE);
                holder.approvedText.setVisibility(View.VISIBLE);
            }
        });

        return convertView;
    }

    private class ViewHolder {

        protected Button btn_ok, btn_no;
        private TextView willinguser, date,textView,approvedText;

    }
    public void approveRequest(int i,Context context,final User user){
        System.out.println("deneme");
        RequestQueue queue = Volley.newRequestQueue(Notifications.context);
        String url="" ;
        if(Notifications.notifications.get(i).getType().equals("TeamClaim")){
            url = StaticVariables.ip_address + "team/claim/approve/" + Notifications.notifications.get(i).getId();

        }else if(Notifications.notifications.get(i).getType().equals("PlayerClaim")){
            url = StaticVariables.ip_address + "player/claim/approve/" + Notifications.notifications.get(i).getId();
        }

        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        System.out.println(response);
                    }
                },
                new com.android.volley.Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                String creds = String.format("%s:%s",user.username,user.password);
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.NO_WRAP);
                params.put("Authorization", auth);
                return params;
            }
        };
        queue.add(getRequest);
    }
    public void cancelRequest(int i,Context context,final User user){

        RequestQueue queue = Volley.newRequestQueue(context);
        String url = StaticVariables.ip_address + "player/claim/cancel/" + Notifications.notifications.get(i).getId();
        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {


                    }
                },
                new com.android.volley.Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                String creds = String.format("%s:%s",user.username,user.password);
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.NO_WRAP);
                params.put("Authorization", auth);
                return params;
            }
        };
        queue.add(getRequest);
    }


}