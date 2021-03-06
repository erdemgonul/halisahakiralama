package com.example.rup.halisahakiralama;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rup.halisahakiralama.client.User;
import com.google.gson.Gson;

public class CreateStadium extends AppCompatActivity {

    Bundle extras;
    User user;
    EditText stadiumname,stadiumphone,stadiumintervaltime,address,price;
    Button toCitySelect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_stadium);


        final Gson gson=new Gson();
        Bundle extras = getIntent().getExtras();
        user= gson.fromJson(extras.getString("user"),User.class);

        stadiumname=findViewById(R.id.createhalisahaname);
        stadiumphone=findViewById(R.id.createhalisahaphone);
        stadiumintervaltime=findViewById(R.id.createhalisahainterval);
        address=findViewById(R.id.createhalisahaaddress);
        price=findViewById(R.id.createhalisahamoney);

        toCitySelect=findViewById(R.id.nexthalisahacreate);

        toCitySelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if((stadiumname.getEditableText()+"").equals("") && (stadiumphone.getEditableText()+"").equals("") && (stadiumintervaltime.getEditableText()+"").equals("") &&
                        (address.getEditableText()+"").equals("") && (price.getEditableText()+"").equals("") ){
                    Toast.makeText(CreateStadium.this, "Boş alanları doldurmalısın.", Toast.LENGTH_SHORT).show();
                }else{
                    if(isValidPhoneNumber(stadiumphone.getEditableText() + "")) {
                        Intent intent = new Intent(CreateStadium.this, CitySelect.class);
                        intent.putExtra("stadium_name", stadiumname.getEditableText() + "");
                        intent.putExtra("stadium_phone", stadiumphone.getEditableText() + "");
                        intent.putExtra("stadium_time", stadiumintervaltime.getEditableText() + "");
                        intent.putExtra("stadium_address", address.getEditableText() + "");
                        intent.putExtra("stadium_price", price.getEditableText() + "");
                        intent.putExtra("option", "fromCreateStadium");
                        intent.putExtra("fromCreateStadium", true);
                        final Gson gson = new Gson();
                        intent.putExtra("user", gson.toJson(user));
                        CreateStadium.this.startActivity(intent);
                    }
                    else
                        Toast.makeText(CreateStadium.this, "Lütfen 10 karakterli geçerli bir telefon numarası giriniz. ", Toast.LENGTH_SHORT).show();
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
