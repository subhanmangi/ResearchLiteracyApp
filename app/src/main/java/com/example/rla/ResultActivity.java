package com.example.rla;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ResultActivity extends AppCompatActivity {

    CircularProgressBar circularProgressBar;

    SharedPreferences sharedpreferences;

    TextView resultText;

    int correct, wrong;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        circularProgressBar = findViewById(R.id.circularProgressBar);



        resultText = findViewById(R.id.resultText);

        correct = getIntent().getIntExtra("correct", 0);

        wrong = getIntent().getIntExtra("wrong", 0);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(now));

        sharedpreferences = getSharedPreferences("Result", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt("correct", correct);
        editor.putString("date", dtf.format(now));
        editor.commit();

        resultText.setText(""+correct+"/10");

        circularProgressBar.setProgress(correct);

    }

    public void goToHome(View v){
     Intent intent = new Intent(this, Home.class);
     startActivity(intent);
    }
}