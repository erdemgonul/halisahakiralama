package com.example.rup.halisahakiralama;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.Gson;

import org.json.JSONArray;
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


    boolean isMailDuplicate,isUsernameDuplicate;
    String token;
    public void setMailDuplicate(boolean mailDuplicate) {
        isMailDuplicate = mailDuplicate;
    }

    public void setUsernameDuplicate(boolean usernameDuplicate) {
        isUsernameDuplicate = usernameDuplicate;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_as_owner);


        mailtext=findViewById(R.id.signupmail_input2);
        userNameText=findViewById(R.id.signupusername_input);
        passwordtext=findViewById(R.id.signuppassword_input);
        termbox=findViewById(R.id.acceptterms_checkbox);
        signupbutton=findViewById(R.id.signupsignup_button);
        haveaccountbutton=findViewById(R.id.haveaccount_button);

        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(termbox.isChecked()) {
                    if(isValidEmail(mailtext.getEditableText().toString())) {
                        FirebaseInstanceId.getInstance().getInstanceId()
                            .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                @Override
                                public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                    if (!task.isSuccessful()) {
                                        return;
                                    }

                                    // Get new Instance ID token

                                    token = task.getResult().getToken();
                                    setMailDuplicate(false);
                                    setUsernameDuplicate(false);
                                    userNameText.setError(null);
                                    mailtext.setError(null);
                                    checkForMailDuplicate(mailtext.getEditableText().toString()+"");
                                }
                            });


                    }
                    else {
                        System.out.println("FUCK");
                        Toast.makeText(SignUpAsOwner.this, "Lütfen Geçerli bir mail adresi giriniz. ", Toast.LENGTH_SHORT).show();
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), ChooseAuth.class);
        startActivityForResult(myIntent, 0);
        return true;
    }

    private void newUserAsOwner(String username,String password, String mail, String token) throws JSONException {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = StaticVariables.ip_address + "user";
        JSONObject jsonBody = new JSONObject();

        jsonBody.put("username", username);
        jsonBody.put("password", password);
        jsonBody.put("email", mail);
        jsonBody.put("fcmPushToken", token);
        jsonBody.put("role", "ROLE_STD_OWNER");

        JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                SignUpAsOwner.this.startActivity(new Intent(SignUpAsOwner.this,SignIn.class));

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

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
    public void checkForUsernameDuplicate(final String username){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = StaticVariables.ip_address + "user/all/username";
        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Gson g = new Gson();
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray x=obj.getJSONArray("result");
                            for (int i = 0; i < x.length(); i++) {
                                if(username.equals(x.get(i).toString())){
                                    setUsernameDuplicate(true);
                                    break;
                                }
                            }
                            if (!isUsernameDuplicate)
                                newUserAsOwner(userNameText.getEditableText().toString(), passwordtext.getEditableText().toString(), mailtext.getEditableText().toString(), token);
                            else
                                userNameText.setError("Bu kullanıcı adı zaten alınmış");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Toast.makeText(SignUpAsOwner.this, "HATA", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                String creds = String.format("%s:%s","rup1","rup1");
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.NO_WRAP);
                params.put("Authorization", auth);
                return params;
            }
        };
        queue.add(getRequest);

    }
    public void checkForMailDuplicate(final String mail){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = StaticVariables.ip_address + "user/all/email";
        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Gson g = new Gson();
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray x=obj.getJSONArray("result");
                            for (int i = 0; i < x.length(); i++) {
                                if(mail.equals(x.get(i).toString())){
                                    isMailDuplicate=true;

                                    break;
                                }
                            }


                            if(!isMailDuplicate)
                                checkForUsernameDuplicate(userNameText.getEditableText().toString()+"");
                            else{
                                mailtext.setError("Bu mail zaten alınmış");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Toast.makeText(SignUpAsOwner.this, "HATA", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        queue.add(getRequest);

    }
}
