package com.example.rup.halisahakiralama;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.MenuItem;
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
import com.example.rup.halisahakiralama.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import static android.support.v4.content.ContextCompat.getSystemService;

public class SignIn extends AppCompatActivity {
    EditText mailtext;
    EditText passwordtext;
    SignInButton signinbutton;
    Button forgotbutton,registerbutton,tosignasownerbutton,signasbackend;


    int RC_SIGN_IN=1;
    GoogleSignInClient mGoogleSignInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // for add back arrow in action bar




        setContentView(R.layout.activity_sign_in);


        com.google.android.gms.common.SignInButton  b=findViewById(R.id.sign_in_button);
        setGoogleButtonText(b,"Google İle Oturum Aç");




        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().requestProfile()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);





        mailtext=findViewById(R.id.signinmail_input);
        passwordtext=findViewById(R.id.signinpassword_input);

        signinbutton=findViewById(R.id.sign_in_button);
        forgotbutton=findViewById(R.id.signinforgotpassword_button);
        registerbutton=findViewById(R.id.signinsignup_button);

        signasbackend=findViewById(R.id.button3);
        signasbackend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    signUser(mailtext.getEditableText().toString(),passwordtext.getEditableText().toString());


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        signinbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
        forgotbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignIn.this.startActivity(new Intent(SignIn.this,ForgotPassword.class));
            }
        });
        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignIn.this.startActivity(new Intent(SignIn.this,SignUp.class));
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), ChooseAuth.class);
        startActivityForResult(myIntent, 0);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            System.out.println(account.getDisplayName());
            newUser(account.getDisplayName(),account.getEmail(), account.getEmail());
            //BURDA HTTP POST ATICAN JAVAYA
            // Signed in successfully, show authenticated UI.
            Toast.makeText(this, "successful", Toast.LENGTH_SHORT).show();
            //SignIn.this.startActivity(new Intent(SignIn.this,ChooseJob.class));

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            System.out.println("mustafaaaa " + e.getMessage());
            Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account!=null){
            try {
                signUser(account.getDisplayName(),account.getEmail());
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private void signUser(final String username, final String password) throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = StaticVariables.ip_address + "login";
        JSONObject jsonBody = new JSONObject();

        jsonBody.put("username", username);
        jsonBody.put("password", password);
        jsonBody.put("role", "ROLE_USER");

        JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                User user=new User();
                try {
                    System.out.println(response);
                    JSONObject object=response.getJSONObject("playerDTO");
                    user.username=object.getString("username");
                    user.password=object.getString("originalPassword");
                    user.role=object.getString("role");
                    user.id=object.getString("id");
                    user.email=object.getString("email");
                    user.isGoogleSign=object.getBoolean("isGoogleSign");

                    System.out.println("---------- DENEME ");
                    Toast.makeText(SignIn.this, "HELALL", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(SignIn.this,ChooseJob.class);

                    Gson gson=new Gson();
                    intent.putExtra("user",gson.toJson(user));
                    SignIn.this.startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                System.out.println("FUCK" + error.getMessage());
                Toast.makeText(SignIn.this, "FUCKKKK ", Toast.LENGTH_SHORT).show();

            }
        });

        queue.add(jsonObject);
    }

    private void newUser(final String username, final String password, final String email) throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = StaticVariables.ip_address + "user";
        JSONObject jsonBody = new JSONObject();

        jsonBody.put("username", username);
        jsonBody.put("password", password);
        jsonBody.put("email", email);
        jsonBody.put("isGoogleSign", true);
        jsonBody.put("role", "ROLE_USER");

        JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                User user=new User();
                try {
                    JSONObject object=response.getJSONObject("playerDTO");
                    user.username=object.getString("username");
                    user.password=object.getString("originalPassword");
                    user.role=object.getString("role");
                    user.email=object.getString("email");
                    user.id=object.getString("id");
                    user.isGoogleSign=object.getBoolean("isGoogleSign");
                } catch (JSONException e) {
                    try {
                        signUser(username,password);
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                System.out.println("FUCK");
                Toast.makeText(SignIn.this, "FUCKKKK ", Toast.LENGTH_SHORT).show();

            }
        });

        queue.add(jsonObject);
    }

    protected void setGoogleButtonText(SignInButton signInButton, String buttonText) {
        // Find the TextView that is inside of the SignInButton and set its text
        for (int i = 0; i < signInButton.getChildCount(); i++) {
            View v = signInButton.getChildAt(i);

            if (v instanceof TextView) {
                TextView tv = (TextView) v;
                tv.setText(buttonText);
                tv.setTextColor(Color.parseColor("#333333"));
                tv.setTextSize(15);

                return;
            }
        }
    }
}
