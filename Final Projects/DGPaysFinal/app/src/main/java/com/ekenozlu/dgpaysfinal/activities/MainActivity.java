package com.ekenozlu.dgpaysfinal.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ekenozlu.dgpaysfinal.R;
import com.ekenozlu.dgpaysfinal.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private FirebaseAuth auth;
    private FirebaseFirestore firebaseFirestore;
    String userEmail,userPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        FirebaseUser currentUser = auth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(MainActivity.this,SecondActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void signInFunc(View view){
        userEmail = binding.emailEditText.getText().toString();
        userPass = binding.passEditText.getText().toString();

        if(NotEmpty()){
            auth.signInWithEmailAndPassword(userEmail,userPass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Intent intent = new Intent(MainActivity.this,SecondActivity.class);
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
        userPass = binding.passEditText.getText().toString();

        if(NotEmpty()){
            auth.createUserWithEmailAndPassword(userEmail,userPass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    ArrayList<Long> tempList = new ArrayList<>();
                    Map<String,Object> tempObject = new HashMap<>();
                    tempObject.put("name","name");
                    tempObject.put("surname","surname");
                    tempObject.put("email",userEmail);
                    tempObject.put("savedMovies",tempList);
                    firebaseFirestore.collection("users").document(auth.getUid()).set(tempObject).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(MainActivity.this,"FOLDERS ADDED",Toast.LENGTH_LONG).show();
                        }
                    });
                    Intent intent = new Intent(MainActivity.this,SecondActivity.class);
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

    public void forgotPassFunc(View view){
        userEmail = binding.emailEditText.getText().toString();
        if(!(binding.emailEditText.getText().toString().matches(""))){
            auth.sendPasswordResetEmail(userEmail).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(MainActivity.this,"E-Mail sent, Please check your spam folder",Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }
        else{
            Toast.makeText(MainActivity.this,"Please provide an E-Mail",Toast.LENGTH_LONG).show();
        }
    }

    public boolean NotEmpty(){
        if (binding.emailEditText.getText().toString().matches("") || binding.passEditText.getText().toString().matches("")){
            Toast.makeText(MainActivity.this,"Please fill the necessary fields",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

}