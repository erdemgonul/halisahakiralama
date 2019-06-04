package com.example.rup.halisahakiralama;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.rup.halisahakiralama.client.Player;
import com.example.rup.halisahakiralama.client.ReservationTime;
import com.example.rup.halisahakiralama.client.Team;
import com.example.rup.halisahakiralama.client.User;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class ChooseTeamResult extends AppCompatActivity {

    Team team;
    String reservationDate,il,ilce,stadium,teamId;
    ReservationTime reservationTime;
    TextView name, hours, dateText;
    Button approve,arabutton;
    User user;
    EditText note;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_team_result);

        Bundle b = getIntent().getExtras();
        final Gson gson=new Gson();
        user= gson.fromJson(b.getString("user"),User.class);
        il=b.getString("il");
        ilce=b.getString("ilce");
        stadium=b.getString("stadiumId");
        reservationDate = b.getString("date");
        reservationTime= gson.fromJson(b.getString("hours"),ReservationTime.class);
        team= gson.fromJson(b.getString("team"),Team.class);


        name=findViewById(R.id.rakiptakimname);
        dateText=findViewById(R.id.rakiptakimdate);
        hours=findViewById(R.id.rakiptakimsaat);
        approve=findViewById(R.id.takimrakiponay);
        arabutton=findViewById(R.id.takimiara_button);


        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    approve(v);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
        arabutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPhoneNumber();
            }
        });
    }

    public void approve(View v) throws JSONException {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = StaticVariables.ip_address + "team/claim";
        JSONObject jsonBody = new JSONObject();

        jsonBody.put("teamId", team.id);
        jsonBody.put("willingUserId", user.id);
        jsonBody.put("stadiumId", stadium);
        jsonBody.put("reservationDate", reservationDate);
        jsonBody.put("beginHour", reservationTime.beginHour);
        jsonBody.put("endHour",reservationTime.endHour);



        JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.POST, url, jsonBody,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Intent intent = new Intent(ChooseTeamResult.this,ChooseJob.class);
                        Gson gson=new Gson();
                        intent.putExtra("user", gson.toJson(user));
                        ChooseTeamResult.this.startActivity(intent);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(jsonObject);

    }
    public void callPhoneNumber()
    {
        try
        {
            if(Build.VERSION.SDK_INT > 22)
            {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling

                    ActivityCompat.requestPermissions(ChooseTeamResult.this, new String[]{Manifest.permission.CALL_PHONE}, 101);

                    return;
                }

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + "05388508090"));
                startActivity(callIntent);

            }
            else {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + "05366594646"));
                startActivity(callIntent);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
