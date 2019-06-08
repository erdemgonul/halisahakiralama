package com.example.rup.halisahakiralama;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;

import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rup.halisahakiralama.client.ClaimDTO;
import com.example.rup.halisahakiralama.client.ClaimResponse;
import com.example.rup.halisahakiralama.client.PlayerListResponse;
import com.example.rup.halisahakiralama.client.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static com.example.rup.halisahakiralama.Notifications.goHome;
import static com.example.rup.halisahakiralama.Notifications.notifications;
import static com.example.rup.halisahakiralama.Notifications.user;


public class Adapter  extends BaseAdapter {

    private Context context;
     Button giveRateBtn,dismissBtn,acceptRequest,declineRequest;

    Button rate1,rate2,rate3,rate4,rate5;
    TextView requestfrom,requeststadium,requestdate;
    int point=0;

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
        return notifications.size();
    }

    @Override
    public Object getItem(int position) {
        return notifications.get(position);
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

        holder.willinguser.setText(notifications.get(position).getUser());
        holder.date.setText(String.valueOf(notifications.get(position).getDate()));
        holder.textView.setText(String.valueOf(notifications.get(position).getText()));
        if(notifications.get(position).getRead()){
            if(notifications.get(position).getRead()){
                holder.btn_ok.setVisibility(View.GONE);
                holder.btn_no.setVisibility(View.GONE);
            }else{

                holder.btn_ok.setVisibility(View.VISIBLE);
                holder.btn_no.setVisibility(View.VISIBLE);
            }
        }else{
            if(notifications.get(position).getType().equals("PlayerVote") || notifications.get(position).getType().equals("TeamVote")) {
                holder.btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ratePlayerOrTeam(position);
                    }
                });
                holder.btn_no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
            else if(notifications.get(position).getType().equals("PlayerClaim") || notifications.get(position).getType().equals("TeamClaim")){
                holder.btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        try {
                            requestDetails(position);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                holder.btn_no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });


            }


                if(notifications.get(position).getApproval()){
                    if(notifications.get(position).getRead()){
                        holder.btn_ok.setVisibility(View.GONE);
                        holder.btn_no.setVisibility(View.GONE);
                    }else{

                        holder.btn_ok.setVisibility(View.VISIBLE);
                        holder.btn_no.setVisibility(View.VISIBLE);
                    }

                }else{
                    holder.btn_ok.setVisibility(View.GONE);
                    holder.btn_no.setVisibility(View.GONE);
                }



        }




        return convertView;
    }

    private class ViewHolder {

        protected Button btn_ok, btn_no;
        private TextView willinguser, date,textView,approvedText;

    }

    public  void ratePlayerOrTeam(final int i){


        final Dialog dialog=new Dialog(Notifications.context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialograte);

        rate1=(Button) dialog.findViewById(R.id.rate1);
        rate2=(Button) dialog.findViewById(R.id.rate2);
        rate3=(Button) dialog.findViewById(R.id.rate3);
        rate4=(Button) dialog.findViewById(R.id.rate4);
        rate5=(Button) dialog.findViewById(R.id.rate5);
        giveRateBtn=dialog.findViewById(R.id.button2);
        dismissBtn=dialog.findViewById(R.id.button);
        rate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                point=1;
                rate1.setBackground(Notifications.context.getResources().getDrawable(R.drawable.button_fullstar));
                rate2.setBackground(Notifications.context.getResources().getDrawable(R.drawable.button_emptystar));
                rate3.setBackground(Notifications.context.getResources().getDrawable(R.drawable.button_emptystar));
                rate4.setBackground(Notifications.context.getResources().getDrawable(R.drawable.button_emptystar));
                rate5.setBackground(Notifications.context.getResources().getDrawable(R.drawable.button_emptystar));
            }
        });
        rate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                point=2;
                rate1.setBackground(Notifications.context.getResources().getDrawable(R.drawable.button_fullstar));
                rate2.setBackground(Notifications.context.getResources().getDrawable(R.drawable.button_fullstar));
                rate3.setBackground(Notifications.context.getResources().getDrawable(R.drawable.button_emptystar));
                rate4.setBackground(Notifications.context.getResources().getDrawable(R.drawable.button_emptystar));
                rate5.setBackground(Notifications.context.getResources().getDrawable(R.drawable.button_emptystar));
            }
        });
        rate3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                point=3;
                rate1.setBackground(Notifications.context.getResources().getDrawable(R.drawable.button_fullstar));
                rate2.setBackground(Notifications.context.getResources().getDrawable(R.drawable.button_fullstar));
                rate3.setBackground(Notifications.context.getResources().getDrawable(R.drawable.button_fullstar));
                rate4.setBackground(Notifications.context.getResources().getDrawable(R.drawable.button_emptystar));
                rate5.setBackground(Notifications.context.getResources().getDrawable(R.drawable.button_emptystar));
            }
        });
        rate4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                point=4;
                rate1.setBackground(Notifications.context.getResources().getDrawable(R.drawable.button_fullstar));
                rate2.setBackground(Notifications.context.getResources().getDrawable(R.drawable.button_fullstar));
                rate3.setBackground(Notifications.context.getResources().getDrawable(R.drawable.button_fullstar));
                rate4.setBackground(Notifications.context.getResources().getDrawable(R.drawable.button_fullstar));
                rate5.setBackground(Notifications.context.getResources().getDrawable(R.drawable.button_emptystar));
            }
        });
        rate5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                point=5;
                rate1.setBackground(Notifications.context.getResources().getDrawable(R.drawable.button_fullstar));
                rate2.setBackground(Notifications.context.getResources().getDrawable(R.drawable.button_fullstar));
                rate3.setBackground(Notifications.context.getResources().getDrawable(R.drawable.button_fullstar));
                rate4.setBackground(Notifications.context.getResources().getDrawable(R.drawable.button_fullstar));
                rate5.setBackground(Notifications.context.getResources().getDrawable(R.drawable.button_fullstar));
            }
        });

        dismissBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        giveRateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    callRate(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


        dialog.show();
    }
    public void callRate(int i) throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(Notifications.context);
        String url="";
        JSONObject jsonBody=null;
        if(notifications.get(i).getType().equals("PlayerVote")){
            url = StaticVariables.ip_address + "update/player/rate";
            jsonBody = new JSONObject();


            //olması gereken notifications.get(i).getWillingUser();
            //ama bu string olarak user ismi dönüyo bana player id'si dönmesi lazım
            jsonBody.put("notificationId", notifications.get(i).getId()); // buradaki value için
            jsonBody.put("username", notifications.get(i).getWillingUser()); // buradaki value için
            jsonBody.put("rate", point);
        }else if(notifications.get(i).getType().equals("TeamVote")){

            url = StaticVariables.ip_address + "update/team/rate";
            jsonBody = new JSONObject();

            jsonBody.put("notificationId", notifications.get(i).getId()); // buradaki value için
            jsonBody.put("username", notifications.get(i).getWillingUser());
            jsonBody.put("rate", point);
        }



        JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
               goHome();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });

        queue.add(jsonObject);

    }
    public void approveRequest(int i,Context context,final User user){
        System.out.println("deneme");
        RequestQueue queue = Volley.newRequestQueue(Notifications.context);
        String url="" ;
        if(notifications.get(i).getType().equals("TeamClaim")){
            url = StaticVariables.ip_address + "team/claim/approve/" + notifications.get(i).getId();

        }else if(notifications.get(i).getType().equals("PlayerClaim")){
            url = StaticVariables.ip_address + "player/claim/approve/" + notifications.get(i).getId();
        }

        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        System.out.println(response);

                        Notifications.goHome();
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
        String url = StaticVariables.ip_address + "player/claim/cancel/" + notifications.get(i).getId();
        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        goHome();
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

    public void requestDetails(final int i) throws JSONException {


        RequestQueue queue = Volley.newRequestQueue(Notifications.context);
        String url="";
        JSONObject jsonBody=null;
        if(notifications.get(i).getType().equals("PlayerClaim")){
            url = StaticVariables.ip_address + "player/claim/" +  notifications.get(i).getClaimId();

        }else if(notifications.get(i).getType().equals("TeamClaim")){

            url = StaticVariables.ip_address + "team/claim/" + notifications.get(i).getClaimId();

        }


        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        final Dialog dialog=new Dialog(Notifications.context);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.dialogdetails);

                        requestfrom= dialog.findViewById(R.id.requestfrom);
                        requestdate= dialog.findViewById(R.id.requestdate);
                        requeststadium= dialog.findViewById(R.id.requeststadium);

                        acceptRequest=dialog.findViewById(R.id.button2);
                        declineRequest=dialog.findViewById(R.id.button);

                        JSONObject jsonObject= null;
                        Gson g = new Gson();
                        ClaimResponse claimResponse = g.fromJson(response, ClaimResponse.class);
                        ClaimDTO claimDTO= claimResponse.claimDTO;
                        requestfrom.setText(claimDTO.willing);
                        requeststadium.setText(claimDTO.stadium);

                        declineRequest.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                cancelRequest(i,Notifications.context,Notifications.user);
                            }
                        });
                        acceptRequest.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                approveRequest(i,Notifications.context,Notifications.user);

                            }
                        });


                        dialog.show();
                    }
                },
                new com.android.volley.Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

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