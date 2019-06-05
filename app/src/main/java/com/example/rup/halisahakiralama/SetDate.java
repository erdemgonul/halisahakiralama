package com.example.rup.halisahakiralama;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rup.halisahakiralama.client.ReservationTime;
import com.example.rup.halisahakiralama.client.Stadium;
import com.example.rup.halisahakiralama.client.User;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class SetDate extends AppCompatActivity {


    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    TextView textView;

    EditText edittext;
    Button toNextFrag,pickDateButton;
    String[] hours;
    ListView listView;
    String  stadiumId;
    User user;
    Stadium stadium;
    String option;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_date);



        Gson g=new Gson();
        Bundle extras = getIntent().getExtras();

        user = g.fromJson(extras.getString("user"),User.class);
        stadium = g.fromJson(extras.getString("stadium"),Stadium.class);
        stadiumId =extras.getString("stadium_id");
        option= extras.getString("option");

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
               createDatePicker();

            }
        });
        edittext.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int inType = edittext.getInputType(); // backup the input type
                edittext.setInputType(InputType.TYPE_NULL); // disable soft input
                edittext.onTouchEvent(event); // call native handler
                edittext.setInputType(inType); // restore input type
                return true; // consume touch even
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

        pickDateButton=findViewById(R.id.button10);
        pickDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDatePicker();
            }
        });




        listView=findViewById(R.id.list_hours);
        listView.setVisibility(View.INVISIBLE);

    }
    private void createDatePicker(){

        DatePickerDialog datePickerDialog =new DatePickerDialog(SetDate.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }
    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        String dateStr=sdf.format(myCalendar.getTime());
        edittext.setText(dateStr);

        getTimeSlotsOfStadium(dateStr,stadiumId);
    }
    public void getTimeSlotsOfStadium(final String date, String id){
        if(option.equals("Rezervation")) {
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = StaticVariables.ip_address + "reservation/time/sheet/" + id + "/" + date;
            StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                    new com.android.volley.Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // response
                            Log.d("d", response);
                            Gson g = new Gson();

                            final TimeSlotResponseList p = g.fromJson(response, TimeSlotResponseList.class);

                            List<String> list = new ArrayList<>();
                            for (int i = 0; i < p.timeSlotDTOs.size(); i++) {
                                list.add(p.timeSlotDTOs.get(i).beginHour + "  ---  " + p.timeSlotDTOs.get(i).endHour);
                            }
                            hours = list.toArray(new String[0]);

                            ArrayAdapter<String> veriAdaptoru = new ArrayAdapter<String>(SetDate.this, android.R.layout.simple_list_item_1, android.R.id.text1, list.toArray(new String[0])) {
                                @Override
                                public View getView(int position, View convertView, ViewGroup parent) {

                                    listView.setVisibility(View.VISIBLE);
                                    View view = super.getView(position, convertView, parent);
                                    TextView textView = (TextView) view.findViewById(android.R.id.text1);
                                    if (!p.timeSlotDTOs.get(position).reservationStatus.equals("EMPTY")) {

                                        Log.d("d", position + "");
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
                                public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                                    TextView v = (TextView) view;

                                    if (v.getCurrentTextColor() != Color.WHITE) {


                                        toNextFrag.setText(hours[i] + " için devam et");
                                        toNextFrag.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#22B473")));

                                        toNextFrag.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Intent intent = new Intent(SetDate.this, ShowHaliSaha.class);
                                                ReservationTime reservationTime = new ReservationTime();
                                                reservationTime.beginHour = hours[i].split("  ---  ")[0];
                                                reservationTime.endHour = hours[i].split("  ---  ")[1];
                                                intent.putExtra("date", date);
                                                Gson gson = new Gson();
                                                intent.putExtra("user", gson.toJson(user));
                                                gson = new Gson();
                                                intent.putExtra("stadium", gson.toJson(stadium));
                                                gson = new Gson();
                                                intent.putExtra("hours", gson.toJson(reservationTime));


                                                SetDate.this.startActivity(intent);
                                            }
                                        });

                                    }
                                }
                            });

                            listView.setAdapter(veriAdaptoru);


                        }
                    },
                    new com.android.volley.Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO Auto-generated method stub

                        }
                    }
            ) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> params = new HashMap<String, String>();
                    String creds = String.format("%s:%s", user.username, user.password);
                    String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.NO_WRAP);
                    params.put("Authorization", auth);
                    return params;
                }
            };
            queue.add(getRequest);
        }

        else if(option.equals("FindPlayer")) {
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = StaticVariables.ip_address + "reservation/time/sheet/" + id;
            StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                    new com.android.volley.Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // response
                            Log.d("d", response);
                            Gson g = new Gson();

                            final TimeSlotResponseList p = g.fromJson(response, TimeSlotResponseList.class);

                            List<String> list = new ArrayList<>();
                            for (int i = 0; i < p.timeSlotDTOs.size(); i++) {
                                list.add(p.timeSlotDTOs.get(i).beginHour + "  ---  " + p.timeSlotDTOs.get(i).endHour);
                            }
                            hours = list.toArray(new String[0]);

                            ArrayAdapter<String> veriAdaptoru = new ArrayAdapter<String>(SetDate.this, android.R.layout.simple_list_item_1, android.R.id.text1, list.toArray(new String[0])) {
                                @Override
                                public View getView(int position, View convertView, ViewGroup parent) {

                                    listView.setVisibility(View.VISIBLE);
                                    View view = super.getView(position, convertView, parent);
                                    TextView textView = (TextView) view.findViewById(android.R.id.text1);
                                    if (!p.timeSlotDTOs.get(position).reservationStatus.equals("EMPTY")) {

                                        Log.d("d", position + "");
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
                                public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                                    TextView v = (TextView) view;

                                    if (v.getCurrentTextColor() != Color.WHITE) {


                                        toNextFrag.setText(hours[i] + " için devam et");
                                        toNextFrag.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#22B473")));

                                        toNextFrag.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Intent intent = new Intent(SetDate.this, ChoosePlayer.class);
                                                ReservationTime reservationTime = new ReservationTime();
                                                reservationTime.beginHour = hours[i].split("  ---  ")[0];
                                                reservationTime.endHour = hours[i].split("  ---  ")[1];
                                                intent.putExtra("date", date);
                                                Gson gson = new Gson();
                                                intent.putExtra("user", gson.toJson(user));
                                                gson = new Gson();
                                                intent.putExtra("stadiumId", stadium.id + "");
                                                gson = new Gson();
                                                intent.putExtra("hours", gson.toJson(reservationTime));


                                                SetDate.this.startActivity(intent);
                                            }
                                        });

                                    }
                                }
                            });

                            listView.setAdapter(veriAdaptoru);


                        }
                    },
                    new com.android.volley.Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO Auto-generated method stub

                        }
                    }
            ) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> params = new HashMap<String, String>();
                    String creds = String.format("%s:%s", user.username, user.password);
                    String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.NO_WRAP);
                    params.put("Authorization", auth);
                    return params;
                }
            };
            queue.add(getRequest);
        } else if(option.equals("FindTeam")) {
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = StaticVariables.ip_address + "reservation/time/sheet/" + id;
            StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                    new com.android.volley.Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // response
                            Log.d("d", response);
                            Gson g = new Gson();

                            final TimeSlotResponseList p = g.fromJson(response, TimeSlotResponseList.class);

                            List<String> list = new ArrayList<>();
                            for (int i = 0; i < p.timeSlotDTOs.size(); i++) {
                                list.add(p.timeSlotDTOs.get(i).beginHour + "  ---  " + p.timeSlotDTOs.get(i).endHour);
                            }
                            hours = list.toArray(new String[0]);

                            ArrayAdapter<String> veriAdaptoru = new ArrayAdapter<String>(SetDate.this, android.R.layout.simple_list_item_1, android.R.id.text1, list.toArray(new String[0])) {
                                @Override
                                public View getView(int position, View convertView, ViewGroup parent) {

                                    listView.setVisibility(View.VISIBLE);
                                    View view = super.getView(position, convertView, parent);
                                    TextView textView = (TextView) view.findViewById(android.R.id.text1);
                                    if (!p.timeSlotDTOs.get(position).reservationStatus.equals("EMPTY")) {

                                        Log.d("d", position + "");
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
                                public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                                    TextView v = (TextView) view;

                                    if (v.getCurrentTextColor() != Color.WHITE) {


                                        toNextFrag.setText(hours[i] + " için devam et");
                                        toNextFrag.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#22B473")));

                                        toNextFrag.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Gson gson = new Gson();
                                                Intent intent = new Intent(SetDate.this, ChooseTeam.class);
                                                ReservationTime reservationTime = new ReservationTime();
                                                reservationTime.beginHour = hours[i].split("  ---  ")[0];
                                                reservationTime.endHour = hours[i].split("  ---  ")[1];
                                                intent.putExtra("date", date);
                                                intent.putExtra("user", gson.toJson(user));
                                                intent.putExtra("stadiumId", stadium.id + "");
                                                intent.putExtra("hours", gson.toJson(reservationTime));

                                                SetDate.this.startActivity(intent);
                                            }
                                        });

                                    }
                                }
                            });

                            listView.setAdapter(veriAdaptoru);


                        }
                    },
                    new com.android.volley.Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO Auto-generated method stub

                        }
                    }
            ) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> params = new HashMap<String, String>();
                    String creds = String.format("%s:%s", user.username, user.password);
                    String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.NO_WRAP);
                    params.put("Authorization", auth);
                    return params;
                }
            };
            queue.add(getRequest);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), ChooseHaliSaha.class);

        final Gson gson=new Gson();
        Bundle extras = getIntent().getExtras();
        user= gson.fromJson(extras.getString("user"),User.class);
        myIntent.putExtra("user",gson.toJson(user));
        myIntent.putExtra("stadium",gson.toJson(stadium));
        myIntent.putExtra("stadium_id",stadiumId);

        startActivityForResult(myIntent, 0);
        return true;
    }

}
