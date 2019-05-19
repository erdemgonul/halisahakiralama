package com.example.rup.halisahakiralama;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rup.halisahakiralama.client.City;
import com.example.rup.halisahakiralama.client.District;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class SetDate extends AppCompatActivity {


    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    TextView textView;
    EditText edittext;
    Button toNextFrag;
    ListView listView;
    int  stadiumId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_date);

        Gson g=new Gson();
        Bundle extras = getIntent().getExtras();
        String  stadiumName =  extras.getString("name");
        stadiumId =Integer.valueOf(extras.getString("stadium_id"));

        textView=findViewById(R.id.textView3);
        textView.setText(stadiumName +  " için bir tarih belirleyin");

        myCalendar = Calendar.getInstance();

        edittext= (EditText) findViewById(R.id.Birthday);
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        edittext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(SetDate.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        toNextFrag=findViewById(R.id.setdate_to_button);
        toNextFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SetDate.this,ChooseHaliSaha.class);
                intent.putExtra("date",edittext.getText());

                SetDate.this.startActivity(intent);

            }
        });









        listView=findViewById(R.id.list_hours);


    }
    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        String dateStr=sdf.format(myCalendar.getTime());
        edittext.setText(dateStr);

        getTimeSlotsOfStadium(dateStr,stadiumId);
    }
    public void getTimeSlotsOfStadium(String date,int id){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://192.168.1.103:8080/" + "reservation/time/sheet/" + id + "/" + date;
        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("d",response);
                        Gson g = new Gson();

                        final TimeSlotResponseList p = g.fromJson(response, TimeSlotResponseList.class);

                        List<String> list=new ArrayList<>();
                        for(int i=0;i<p.timeSlotDTOs.size();i++){
                            list.add(p.timeSlotDTOs.get(i).beginHour + "  ---  " + p.timeSlotDTOs.get(i).endHour);
                        }
                        Toast.makeText(SetDate.this, list.size()+"", Toast.LENGTH_SHORT).show();
                        ArrayAdapter<String> veriAdaptoru=new ArrayAdapter<String>(SetDate.this, android.R.layout.simple_list_item_1, android.R.id.text1, list.toArray(new String[0])){
                            @Override
                            public View getView(int position, View convertView, ViewGroup parent) {


                                View view =super.getView(position, convertView, parent);
                                TextView textView=(TextView) view.findViewById(android.R.id.text1);
                               if(!p.timeSlotDTOs.get(position).reservationStatus.equals("EMPTY")) {

                                    Log.d("d" , position + "");
                                   /*YOUR CHOICE OF COLOR*/
                                   textView.setBackgroundColor(Color.RED);
                                   textView.setClickable(false);
                                    textView.setTextColor(Color.WHITE);

                               }
                                return view;
                            }

                        };
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                TextView v=(TextView) view;

                                if(v.getCurrentTextColor()!= Color.WHITE) {
                                    System.out.println(i);
                                    Intent intent = new Intent(SetDate.this, ShowHaliSaha.class);
                                    //intent.putExtra("name",p.stadiums.get(i).name);
                                    //intent.putExtra("id",p.stadiums.get(i).id);

                                    SetDate.this.startActivity(intent);

                                }
                            }
                        });

                        listView.setAdapter(veriAdaptoru);



                    }
                },
                new com.android.volley.Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                String creds = String.format("%s:%s","admin","admin");
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                params.put("Authorization", auth);
                return params;
            }
        };
        queue.add(getRequest);
    }
}
