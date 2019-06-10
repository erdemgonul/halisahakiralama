package com.example.rup.halisahakiralama;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
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
import com.example.rup.halisahakiralama.client.User;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class ChoosePlayerResult extends AppCompatActivity {

    Player player;
    String reservationDate,il,ilce,stadium;
    ReservationTime hours;
    TextView name, address, positions, playerRate;
    Button approve,arabutton;
    User user;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_choose_player_result);

        Bundle b = getIntent().getExtras();
        final Gson gson=new Gson();
        user= gson.fromJson(b.getString("user"),User.class);
        player= gson.fromJson(b.getString("player"), Player.class);
        il=b.getString("il");
        ilce=b.getString("ilce");
        stadium=b.getString("stadiumId");
        reservationDate = b.getString("date");
        hours= gson.fromJson(b.getString("hours"),ReservationTime.class);


        name=findViewById(R.id.textView12);
        name.setText( player.name + "   "  +player.surName);

        address=findViewById(R.id.adres_text);
        address.setText("Adres: " + player.address);

        positions=findViewById(R.id.ucret_text);
        positions.setText("Pozisyon:  " + player.positions.substring(0, player.positions.length() - 1).replaceAll(";",",").toLowerCase());

        playerRate =findViewById(R.id.date_text);
        playerRate.setText("Puan: " + player.rate);
        arabutton=findViewById(R.id.oyuncuyuara_button);
        arabutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              callPhoneNumber();
            }
        });

        approve=findViewById(R.id.button3);
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

        editText=findViewById(R.id.editText);
    }
    public void onClickWhatsApp(View view) {

        PackageManager pm=getPackageManager();
        try {

            Intent waIntent = new Intent(Intent.ACTION_SEND);
            waIntent.setType("text/plain");
            String text = "YOUR TEXT HERE";

            PackageInfo info=pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
            //Check if package exists or not. If not then code
            //in catch block will be called
            waIntent.setPackage("com.whatsapp");

            waIntent.putExtra(Intent.EXTRA_TEXT, text);
            startActivity(Intent.createChooser(waIntent, "Share with"));

        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(this, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                    .show();
        }

    }
    public void approve(View v) throws JSONException {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = StaticVariables.ip_address + "player/claim";
        JSONObject jsonBody = new JSONObject();

        jsonBody.put("playerId", player.id);
        jsonBody.put("willingUserId", user.id);
        jsonBody.put("address", false);
        jsonBody.put("stadiumId", stadium);
        jsonBody.put("reservationDate", reservationDate);
        jsonBody.put("beginHour", hours.beginHour);
        jsonBody.put("endHour", hours.endHour);



        JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.POST, url, jsonBody,

                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Intent intent = new Intent(ChoosePlayerResult.this,ChooseJob.class);
                Gson gson=new Gson();
                intent.putExtra("user", gson.toJson(user));
                ChoosePlayerResult.this.startActivity(intent);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(ChoosePlayerResult.this, "HATA ", Toast.LENGTH_SHORT).show();

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

                    ActivityCompat.requestPermissions(ChoosePlayerResult.this, new String[]{Manifest.permission.CALL_PHONE}, 101);

                    return;
                }

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + "+90" + player.phoneNumber));
                startActivity(callIntent);

            }
            else {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + "+90" + player.phoneNumber));
                startActivity(callIntent);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
