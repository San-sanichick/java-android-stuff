package com.example.mytestapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button btn;
    private TextView txtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn     = findViewById(R.id.button);
        txtView = findViewById(R.id.textView);

        txtView.setText("Hello World!");

        Intent i = new Intent(this, SecondActivity.class);

        btn.setOnClickListener(v -> {
            i.putExtra("cityName", "Khabarovsk");
            startActivity(i);
        });
    }
}