package com.example.rup.halisahakiralama;

import android.content.Intent;
import android.support.annotation.Nullable;
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
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
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

        mailtext=findViewById(R.id.signinmailowner_input);
        passwordtext=findViewById(R.id.signinpasswordowner_input);

        signinbutton=findViewById(R.id.sign_in_button_owner);
        forgotbutton=findViewById(R.id.signinforgotpassword_button_owner);
        registerbutton=findViewById(R.id.signinsignup_button_owner);
        changeactivity=findViewById(R.id.hey123);

        System.out.println("SELAM");
        forgotbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignInAsOwner.this.startActivity(new Intent(SignInAsOwner.this,ForgotPassword.class));
            }
        });
        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignInAsOwner.this.startActivity(new Intent(SignInAsOwner.this,SignUp.class));
            }
        });

        signasbackend=findViewById(R.id.buttonowner);
        signasbackend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    signUserAsOwner(mailtext.getEditableText().toString(),passwordtext.getEditableText().toString());
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
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            newUser(account.getDisplayName(),account.getEmail());
            Toast.makeText(this, "successful", Toast.LENGTH_SHORT).show();
            SignInAsOwner.this.startActivity(new Intent(SignInAsOwner.this,ChooseJob.class));

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
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
                signUserAsOwner(account.getDisplayName(),account.getEmail());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void signUserAsOwner(final String username, final String password) throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = StaticVariables.ip_address + "login";
        JSONObject jsonBody = new JSONObject();

        jsonBody.put("username", username);
        jsonBody.put("password", password);
        jsonBody.put("role", "ROLE_STD_OWNER");

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

                    System.out.println("---------- DENEME ");
                    Toast.makeText(SignInAsOwner.this, "HELALL", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(SignInAsOwner.this,ChooseJob.class);

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
    private void newUser(final String username, final String password) throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = StaticVariables.ip_address + "user";
        JSONObject jsonBody = new JSONObject();

        jsonBody.put("username", username);
        jsonBody.put("password", password);
        jsonBody.put("role", "ROLE_STD_OWNER");

        JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                User user=new User();
                try {
                    JSONObject object=response.getJSONObject("playerDTO");
                    user.username=object.getString("username");
                    user.password=object.getString("originalPassword");
                    user.role=object.getString("role");
                } catch (JSONException e) {
                    try {
                        signUserAsOwner(username,password);
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
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
}
