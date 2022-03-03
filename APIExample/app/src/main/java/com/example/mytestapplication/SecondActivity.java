package com.example.mytestapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class SecondActivity extends AppCompatActivity {

    private TextView nameView;
    private Button toSurfaceButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        nameView = findViewById(R.id.textView2);

        toSurfaceButton = findViewById(R.id.toSurfaceButton);

        Intent intent = getIntent();
        String city = intent.getStringExtra("city");

        toSurfaceButton.setOnClickListener(v -> {
            Intent i = new Intent(this, SurfaceViewActivity.class);

            startActivity(i);
        });

//        Toast.makeText(this, "ckauhfvba", Toast.LENGTH_LONG).show();

        getWeather(city);
    }

    static class Weather {
        public Current current;

        private class Current {
            public  int temp_c;
        }
    }

    private void getWeather(String city) {
        AsyncTask.execute(() -> {
            try {
                URL endPoint = new URL("https://api.weatherapi.com/v1/current.json?key=709678ab4e5240c791e115427212509&q=" + city);
                HttpsURLConnection connection = (HttpsURLConnection) endPoint.openConnection();

                int responseCode = connection.getResponseCode();
//                Log.d("CODE", String.valueOf(responseCode));
                if (responseCode == 200) {
                    InputStream responseBody = connection.getInputStream();
                    InputStreamReader reader = new InputStreamReader(responseBody, "UTF-8");

                    Weather weather = new Gson().fromJson(reader, Weather.class);

                    nameView.setText(String.format("%s C", weather.current.temp_c));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}