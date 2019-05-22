package com.example.rup.halisahakiralama;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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
import com.example.rup.halisahakiralama.client.District;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignUpAsOwner extends AppCompatActivity {

    EditText mailtext;
    EditText userNameText;
    EditText passwordtext;
    CheckBox termbox;
    Button signupbutton;
    Button haveaccountbutton;
    TextView header;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_as_owner);

        header=findViewById(R.id.title_app);
        header.setText(StaticVariables.title);

        mailtext=findViewById(R.id.signupemail_inputowner);
        passwordtext=findViewById(R.id.signuppassword_inputowner);
        userNameText=findViewById(R.id.signupusername_inputowner);
        termbox=findViewById(R.id.acceptterms_checkboxowner);
        signupbutton=findViewById(R.id.signupsignup_buttonowner);
        haveaccountbutton=findViewById(R.id.haveaccount_buttonowner);

        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(termbox.isChecked()) {
                    try {
                        newUserAsOwner(userNameText.getEditableText().toString(),passwordtext.getEditableText().toString(), mailtext.getEditableText().toString());
                        Toast.makeText(SignUpAsOwner.this, "HELALL", Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    termbox.setTextColor(Color.RED);
                    System.out.println("BAS BAKAYIM !!!!!!!!!!!!!!!!!!!!");
                }

            }
        });

        haveaccountbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignUpAsOwner.this.startActivity(new Intent(SignUpAsOwner.this,SignInAsOwner.class));
            }
        });
    }

    private void newUserAsOwner(String username,String password, String email) throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = StaticVariables.ip_address + "user";
        JSONObject jsonBody = new JSONObject();

        jsonBody.put("username", username);
        jsonBody.put("password", password);
        jsonBody.put("email", email);
        jsonBody.put("role", "ROLE_STD_OWNER");

        JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                User user=new User();
                SignUpAsOwner.this.startActivity(new Intent(SignUpAsOwner.this,SignInAsOwner.class));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                System.out.println("FUCK");
                Toast.makeText(SignUpAsOwner.this, "FUCKKKK ", Toast.LENGTH_SHORT).show();

            }
        });

        queue.add(jsonObject);
    }
}
