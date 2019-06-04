package com.example.rup.halisahakiralama;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class SignInAsOwner extends AppCompatActivity {
    EditText mailtext;
    EditText passwordtext;
    SignInButton signinbutton;
    Button forgotbutton,registerbutton,changeactivity,signasbackend;
   
    int RC_SIGN_IN=1;
    GoogleSignInClient mGoogleSignInClient;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);





        setContentView(R.layout.activity_sign_in_as_owner);
        com.google.android.gms.common.SignInButton  b=findViewById(R.id.sign_in_button_owner);
        setGoogleButtonText(b,"Google İle Oturum Aç");


        mailtext=findViewById(R.id.signinmailowner_input);
        passwordtext=findViewById(R.id.signinpasswordowner_input);

        signinbutton=findViewById(R.id.sign_in_button_owner);
        forgotbutton=findViewById(R.id.signinforgotpassword_button_owner);

        changeactivity=findViewById(R.id.hey123);

        System.out.println("SELAM");
        forgotbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignInAsOwner.this.startActivity(new Intent(SignInAsOwner.this,ForgotPassword.class));
            }
        });


        signasbackend=findViewById(R.id.buttonowner);
        signasbackend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                                    signUserAsOwner(mailtext.getEditableText().toString(),passwordtext.getEditableText().toString(), token);
                                } catch (JSONException e) {
                                    System.out.println("FUCK2");
                                    Toast.makeText(SignInAsOwner.this, "FUCKKKK2 ", Toast.LENGTH_SHORT).show();
                                }
                                Toast.makeText(SignInAsOwner.this, "helalll", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        signinbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

        changeactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignInAsOwner.this.startActivity(new Intent(SignInAsOwner.this,SignIn.class));
            }
        });




        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);



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
            final GoogleSignInAccount account = completedTask.getResult(ApiException.class);
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
                                signUserAsOwner(account.getDisplayName(),account.getEmail(), token);
                            } catch (JSONException e) {
                                System.out.println("FUCK2");
                                Toast.makeText(SignInAsOwner.this, "FUCKKKK2 ", Toast.LENGTH_SHORT).show();
                            }
                            Toast.makeText(SignInAsOwner.this, "helalll", Toast.LENGTH_SHORT).show();
                        }
                    });
            Toast.makeText(this, "successful", Toast.LENGTH_SHORT).show();
            SignInAsOwner.this.startActivity(new Intent(SignInAsOwner.this,ChooseJob.class));

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), ChooseAuth.class);
        startActivityForResult(myIntent, 0);
        return true;
    }
    @Override
    protected void onStart() {
        super.onStart();

        final GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account!=null){
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
                                signUserAsOwner(account.getDisplayName(),account.getEmail(), token);
                            } catch (JSONException e) {
                                System.out.println("FUCK2");
                                Toast.makeText(SignInAsOwner.this, "FUCKKKK2 ", Toast.LENGTH_SHORT).show();
                            }
                            Toast.makeText(SignInAsOwner.this, "helalll", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
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
    private void signUserAsOwner(final String username, final String password, final String token) throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = StaticVariables.ip_address + "login";
        JSONObject jsonBody = new JSONObject();

        jsonBody.put("username", username);
        jsonBody.put("password", password);
        jsonBody.put("role", "ROLE_STD_OWNER");
        jsonBody.put("fcmPushToken", token);


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
                    user.email=object.getString("email");
                    user.id=object.getString("id");

                    System.out.println("---------- DENEME ");
                    Toast.makeText(SignInAsOwner.this, "HELALL", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(SignInAsOwner.this,ChooseJobOwner.class);

                    Gson gson=new Gson();
                    intent.putExtra("user",gson.toJson(user));
                    SignInAsOwner.this.startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                System.out.println("FUCK");
                Toast.makeText(SignInAsOwner.this, "FUCKKKK ", Toast.LENGTH_SHORT).show();

            }
        });

        queue.add(jsonObject);
    }
    private void newUser(final String username, final String password, final String email, final String token) throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = StaticVariables.ip_address + "user";
        JSONObject jsonBody = new JSONObject();

        jsonBody.put("username", username);
        jsonBody.put("password", password);
        jsonBody.put("email", email);
        jsonBody.put("role", "ROLE_STD_OWNER");
        jsonBody.put("fcmPushToken", token);

        JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                User user=new User();
                try {
                    JSONObject object=response.getJSONObject("playerDTO");
                    user.username=object.getString("username");
                    user.password=object.getString("originalPassword");
                    user.role=object.getString("role");
                    user.id=object.getString("id");
                    user.email=object.getString("email");
                } catch (JSONException e) {
                    try {
                        signUserAsOwner(username,password, token);
                    } catch (JSONException e1) {
                        mailtext.setError("Kullanıcı Adı veya Şifre Yanlış");
                        passwordtext.setError("Kullanıcı Adı veya Şifre Yanlış");
                        Toast.makeText(SignInAsOwner.this, "Giriş yapılamadı", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mailtext.setError("Kullanıcı Adı veya Şifre Yanlış");
                passwordtext.setError("Kullanıcı Adı veya Şifre Yanlış");
                Toast.makeText(SignInAsOwner.this, "Giriş yapılamadı", Toast.LENGTH_SHORT).show();

            }
        });

        queue.add(jsonObject);
    }
}
