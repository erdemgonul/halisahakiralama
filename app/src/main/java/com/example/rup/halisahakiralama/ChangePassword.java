package com.example.rup.halisahakiralama;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class ChangePassword extends AppCompatActivity {


    Button changepassword;
    EditText oldpassword,newpassword,approvenewpassword;
    User user;
    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Gson gson=new Gson();
        Bundle extras = getIntent().getExtras();
        user= gson.fromJson(extras.getString("user"),User.class);

        setContentView(R.layout.activity_change_password);

        changepassword=findViewById(R.id.changepasswordonbutton);
        oldpassword=findViewById(R.id.oldpassword);
        newpassword=findViewById(R.id.changepasswordfirst);
        approvenewpassword=findViewById(R.id.changeapprovepassword);


        changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if((oldpassword.getText()+"").equals(user.password) && (newpassword.getText()+"").equals(approvenewpassword.getText()+""))
                        callChangePassword();
                    else{

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void callChangePassword() throws JSONException {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = StaticVariables.ip_address + "update/user";
        JSONObject jsonBody = new JSONObject();

        jsonBody.put("username", user.username);
        jsonBody.put("id", user.id);
        jsonBody.put("password", newpassword.getText()+"");
        jsonBody.put("email", "");

        JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
              signOut();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                System.out.println("FUCK");
                Toast.makeText(ChangePassword.this, "FUCKKKK ", Toast.LENGTH_SHORT).show();

            }
        });

        queue.add(jsonObject);
    }
    private void signOut() {


        SharedPreferences myPreferences
                = PreferenceManager.getDefaultSharedPreferences(ChangePassword.this);
        myPreferences.edit().remove("user").commit();


        Intent intent=new Intent(ChangePassword.this,ChooseAuth.class);
        ChangePassword.this.startActivity(intent);
    }
}
