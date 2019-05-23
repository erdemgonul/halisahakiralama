package com.example.rup.halisahakiralama;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.rup.halisahakiralama.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUp extends AppCompatActivity {
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
        setContentView(R.layout.activity_sign_up);

        header=findViewById(R.id.title_app);
        header.setText(StaticVariables.title);

        mailtext=findViewById(R.id.signupemail_inputowner);
        userNameText=findViewById(R.id.signupusername_input);
        passwordtext=findViewById(R.id.signuppassword_input);
        termbox=findViewById(R.id.acceptterms_checkbox);
        signupbutton=findViewById(R.id.signupsignup_button);
        haveaccountbutton=findViewById(R.id.haveaccount_button);

        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(termbox.isChecked()) {

                    FirebaseInstanceId.getInstance().getInstanceId()
                            .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                @Override
                                public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                    if (!task.isSuccessful()) {
                                        return;
                                    }

                                    // Get new Instance ID token
                                    String token = task.getResult().getToken();

                                    try {
                                        newUser(userNameText.getEditableText().toString(),passwordtext.getEditableText().toString(), mailtext.getEditableText().toString(), token);
                                    } catch (JSONException e) {
                                        System.out.println("FUCK2");
                                        Toast.makeText(SignUp.this, "FUCKKKK2 ", Toast.LENGTH_SHORT).show();
                                    }
                                    Toast.makeText(SignUp.this, "helalll", Toast.LENGTH_SHORT).show();
                                }
                            });


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


    private void newUser(String username,String password, String mail, String token) throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = StaticVariables.ip_address + "user";
        JSONObject jsonBody = new JSONObject();

        jsonBody.put("username", username);
        jsonBody.put("password", password);
        jsonBody.put("email", mail);
        jsonBody.put("fcmPushToken", token);
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
