package com.example.rla;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Home extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void takeAssessment(View v){

        Intent intent = new Intent(this, TakeAssessment.class);
        startActivity(intent);

    }

    public void infoClicked(View v){

        Intent intent = new Intent(this, Info.class);
        startActivity(intent);

    }

    public void viewProfileClicked(View v){

        Intent intent = new Intent(this, ViewProfile.class);
        startActivity(intent);

    }

    public void getCertificateClicked(View v){

        Intent intent = new Intent(this, GetCertificate.class);
        startActivity(intent);

    }

    public void logout(View v){
        SharedPreferences sharedpreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt("login", 0);
        editor.commit();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}