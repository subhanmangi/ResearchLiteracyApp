package com.example.rla;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    EditText loginEmail, loginPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_main);

        loginEmail = findViewById(R.id.loginEmail);
        loginPassword = findViewById(R.id.loginPassword);
    }





    public void onLoginClick(View v){



        if(loginEmail.getText().equals("") || loginPassword.getText().equals("")){
            Toast.makeText(this, "Insuffecient Information", Toast.LENGTH_LONG).show();
        }
        else{
            signIn(""+loginEmail.getText(), ""+loginPassword.getText());
        }

    }

    @Override
    public void onStart() {
        super.onStart();

        SharedPreferences prefs = this.getSharedPreferences(
                "login", Context.MODE_PRIVATE);

        int loginToken = prefs.getInt("login", 0);

        if(loginToken==1){
            signIn(""+prefs.getString("username", "none"), ""+prefs.getString("password", "none"));
        }
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            //do nothing
        }
    }



    public void onSignUpClicked(View view){
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }

    public void signIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                           // Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if(user!=null){
            Intent intent = new Intent(this, Home.class);
            startActivity(intent);
        }
    }
}