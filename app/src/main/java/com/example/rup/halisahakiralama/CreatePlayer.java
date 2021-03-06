package com.example.rup.halisahakiralama;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rup.halisahakiralama.client.User;
import com.google.gson.Gson;

public class CreatePlayer extends AppCompatActivity {


    Button toButton;
    EditText name,surname,phone,address;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_player);


        final Gson gson=new Gson();
        Bundle extras = getIntent().getExtras();
        user= gson.fromJson(extras.getString("user"),User.class);

        toButton=findViewById(R.id.possiblecitiesbutton);
        name=findViewById(R.id.createplayername);
        surname=findViewById(R.id.createplayersurname);
        address=findViewById(R.id.createplayeraddress);
        phone=findViewById(R.id.createplayerphone);


        toButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!(name.getEditableText()+"").equals("") && !(surname.getEditableText()+"").equals("") &&
                        !(address.getEditableText()+"").equals("") && !(phone.getEditableText()+"").equals("")){
                    if(isValidPhoneNumber(phone.getEditableText() + "")) {
                        Intent intent = new Intent(CreatePlayer.this, ChooseRolePlayer.class);
                        System.out.println(name.getText());
                        intent.putExtra("name", name.getText() + "");
                        intent.putExtra("surname", surname.getText() + "");
                        intent.putExtra("phone", phone.getText() + "");
                        intent.putExtra("address", address.getText() + "");
                        final Gson gson = new Gson();
                        intent.putExtra("user", gson.toJson(user));
                        CreatePlayer.this.startActivity(intent);
                    }
                    else
                        Toast.makeText(CreatePlayer.this, "Lütfen 10 karakterli geçerli bir telefon numarası giriniz. ", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(CreatePlayer.this, "Tüm alanlar doldurulmalıdır.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public static boolean isValidPhoneNumber(String phone) {
        if (phone.length() == 10)
        {
            return Patterns.PHONE.matcher(phone).matches();
        }

        return false;
    }
}
