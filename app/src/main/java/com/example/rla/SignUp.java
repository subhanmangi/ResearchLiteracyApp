package com.example.rla;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class SignUp extends AppCompatActivity {

    private FirebaseAuth mAuth;

    EditText signupEmail, signupPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signupEmail = findViewById(R.id.signupEmail);
        signupPassword = findViewById(R.id.signupPassword);
        // ...
// Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }

    public void onSignUpClicked(View view){
        String email = ""+signupEmail.getText();
        String password = ""+signupPassword.getText();



        createAccount(email, password);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            reload();
        }
    }

    private void reload() {
    }

    public void createAccount(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "createUserWithEmail:success");

                            Toast.makeText(SignUp
                                            .this, "Account created.",
                                    Toast.LENGTH_SHORT).show();


                            FirebaseUser user = mAuth.getCurrentUser();
                            StorageReference storageReference = FirebaseStorage.getInstance().getReference("Users");
                            storageReference.putFile(Uri.parse("android.resource://"+getPackageName()+"/drawable/profile.png"));
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUp
                                            .this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) {

        if(user!=null){

            Intent intent = new Intent(this, Home.class);

            SharedPreferences sharedpreferences = getSharedPreferences("Result", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putInt("correct", 0);
            editor.putString("name", "not specified");
            editor.commit();


             sharedpreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
            editor = sharedpreferences.edit();
            editor.putInt("login", 1);
            editor.putString("username", ""+signupEmail.getText());
            editor.putString("password", ""+signupPassword.getText());
            editor.commit();
            startActivity(intent);

        }
    }
}