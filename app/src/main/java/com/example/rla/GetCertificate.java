package com.example.rla;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GetCertificate extends AppCompatActivity {

    TextView name, date;

    DatabaseReference databaseReference;

    FirebaseAuth auth;

    String userId;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_certificate);

        name = findViewById(R.id.name);
        date = findViewById(R.id.date);


        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        auth = FirebaseAuth.getInstance();
        userId = auth.getCurrentUser().getUid();

        SharedPreferences sharedpreferences = getSharedPreferences("Result", Context.MODE_PRIVATE);
        int correct = sharedpreferences.getInt("correct", 0);

        if (correct >= 0) {
           String dateString = sharedpreferences.getString("date", "none");
           date.setText(dateString);
           name.setText(Values.NAME);
        }

    }
}