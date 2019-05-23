package com.example.rup.halisahakiralama;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.rup.halisahakiralama.R;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUp extends AppCompatActivity {
    EditText mailtext;
    EditText passwordtext;
    CheckBox termbox;
    Button signupbutton;
    Button haveaccountbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // for add back arrow in action bar
        setContentView(R.layout.activity_sign_up);

        mailtext=findViewById(R.id.signupmail_input);
        passwordtext=findViewById(R.id.signuppassword_input);
        termbox=findViewById(R.id.acceptterms_checkbox);
        signupbutton=findViewById(R.id.signupsignup_button);
        haveaccountbutton=findViewById(R.id.haveaccount_button);

        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(termbox.isChecked()) {
                    try {
                        newUser(mailtext.getEditableText().toString(),passwordtext.getEditableText().toString());
                        Toast.makeText(SignUp.this, "helalll", Toast.LENGTH_SHORT).show();
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
                SignUp.this.startActivity(new Intent(SignUp.this,SignIn.class));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), ChooseAuth.class);
        startActivityForResult(myIntent, 0);
        return true;
    }
    private void newUser(String username,String password) throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = StaticVariables.ip_address + "user";
        JSONObject jsonBody = new JSONObject();

        jsonBody.put("username", username);
        jsonBody.put("password", password);
        jsonBody.put("role", "ROLE_USER");

        JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                SignUp.this.startActivity(new Intent(SignUp.this,SignIn.class));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                System.out.println("FUCK");
                Toast.makeText(SignUp.this, "FUCKKKK ", Toast.LENGTH_SHORT).show();

            }
        });

        queue.add(jsonObject);
    }


}
