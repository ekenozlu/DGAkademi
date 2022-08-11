package com.ekenozlu.dgpays_w01.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ekenozlu.dgpays_w01.databinding.ActivityMainBinding;
import com.ekenozlu.dgpays_w01.model.Car;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    String databasePath = "https://dgpays-w01-default-rtdb.europe-west1.firebasedatabase.app";
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;

    String userEmail,userPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        database = FirebaseDatabase.getInstance(databasePath);
        databaseReference = FirebaseDatabase.getInstance(databasePath).getReference();

        auth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = auth.getCurrentUser();

        if(currentUser != null){
            Intent intent = new Intent(MainActivity.this,FactoryInfoActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void signInFunc(View view){
        userEmail = binding.emailEditText.getText().toString();
        userPass = binding.passwordEditText.getText().toString();

        if(NotEmpty()){
            auth.signInWithEmailAndPassword(userEmail,userPass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Intent intent = new Intent(MainActivity.this,FactoryInfoActivity.class);
                    startActivity(intent);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public void signUpFunc(View view){
        userEmail = binding.emailEditText.getText().toString();
        userPass = binding.passwordEditText.getText().toString();

        if(NotEmpty()){
            auth.createUserWithEmailAndPassword(userEmail,userPass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Intent intent = new Intent(MainActivity.this,FactoryInfoActivity.class);
                    startActivity(intent);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public boolean NotEmpty(){
        if (binding.emailEditText.getText().toString().matches("") || binding.passwordEditText.getText().toString().matches("")){
            Toast.makeText(MainActivity.this,"Please fill the necessary fields",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

}