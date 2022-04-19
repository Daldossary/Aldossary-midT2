package com.example.aldossary_midt2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    /*
    ASSUMPTIONS
    - First activity is NOT splash screen as it does clearly
    state if it transfers to another activity or not. If it
    is supposed to be a splash screen then the java code would be:
    TimerTask task = new TimerTask() {
    @Override
    public void run() {
    finish();
    startActivity(MainActivity.this, ActivityMain.class);
    } };
    and it would have a textview with the title info.
     */

    TextView dateSelected, temp, humid;
    Button pickDateBttn, secondActiveBttn;
    Spinner sp;
    JSONObject jsonObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dateSelected = (TextView) findViewById(R.id.dateTxt);
        temp = (TextView) findViewById(R.id.tempTxt);
        humid = (TextView) findViewById(R.id.humidTxt);
        sp = (Spinner) findViewById(R.id.spinner);

        pickDateBttn = (Button) findViewById(R.id.dateBttn);
        secondActiveBttn = (Button) findViewById(R.id.secondActiveBttn);

        Calendar c = Calendar.getInstance();
        DateFormat formD = DateFormat.getDateInstance();

        sp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://api.openweathermap.org/data/2.5/weather?q=";
                String city = sp.getSelectedItem().toString();
                url = url + city + ",sa&appid=ce355355a86e6175580bb7e0b181f1bf&units=metric";
                weather(url);
            }
        });

        DatePickerDialog.OnDateSetListener d =
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        c.set(Calendar.YEAR, year);
                        c.set(Calendar.MONTH, monthOfYear);
                        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        dateSelected.setText("Date Selected is "+
                                formD.format(c.getTime()));
                    }
                };

        pickDateBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(MainActivity.this, d,
                        c.get(Calendar.YEAR),
                        c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        secondActiveBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Activity2.class));
            }
        });

    }

    public void weather(String url) {
        JsonObjectRequest jsonObj = new JsonObjectRequest(Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("Dalal", "Response Received");
                        Log.d("Dalal-JSON", response.toString());
                        try {
                            JSONObject jsonMain = response.getJSONObject("main");

                            double temper = jsonMain.getDouble("temp");
                            Log.d("Dalal-Temp", String.valueOf(temper));
                            temp.setText("Temp: " + String.valueOf(temper) + "C");

                            int humidity = jsonMain.getInt("humidity");
                            Log.d("Dalal-Humidity", String.valueOf(temp));
                            humid.setText("Humidity: " + String.valueOf(humidity) + "%");

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("Receive Error", e.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Dalal", "Error Retrieving URL");
            }
        }
        );
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObj);
    }
}
