package com.ekenozlu.dgpays_w01.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ekenozlu.dgpays_w01.R;
import com.ekenozlu.dgpays_w01.adapter.InfoAdapter;
import com.ekenozlu.dgpays_w01.databinding.ActivityFactoryInfoBinding;
import com.ekenozlu.dgpays_w01.model.Car;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class FactoryInfoActivity extends AppCompatActivity {
    private ActivityFactoryInfoBinding binding;

    String databasePath = "https://dgpays-w01-default-rtdb.europe-west1.firebasedatabase.app";

    private FirebaseAuth auth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance(databasePath);
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance(databasePath).getReference().child("carBrands");

    ArrayList<Car> carInfoArraylist;
    InfoAdapter infoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFactoryInfoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        auth = FirebaseAuth.getInstance();

        carInfoArraylist = new ArrayList<>();

        getData();
        binding.infoRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        infoAdapter = new InfoAdapter(carInfoArraylist);
        binding.infoRecyclerView.setAdapter(infoAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.options_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.logOutButton){
            auth.signOut();
            Intent intentToMain = new Intent(FactoryInfoActivity.this,MainActivity.class);
            startActivity(intentToMain);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void getData(){

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                carInfoArraylist.clear();
                setDataToClass(snapshot);
                printAll();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(FactoryInfoActivity.this,error.toException().getLocalizedMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setDataToClass(DataSnapshot snapshot){
        for(DataSnapshot dataSnapshot : snapshot.getChildren()){
            String brandName = dataSnapshot.child("brandName").getValue().toString();
            String imageURL = dataSnapshot.child("imageURL").getValue().toString();
            int doorNumber = Integer.parseInt(dataSnapshot.child("doorNumber").getValue().toString());
            int engineNumber = Integer.parseInt(dataSnapshot.child("engineNumber").getValue().toString());
            int wheelNumber = Integer.parseInt(dataSnapshot.child("wheelNumber").getValue().toString());
            Car car = new Car(brandName,imageURL,doorNumber,engineNumber,wheelNumber);
            carInfoArraylist.add(car);
            System.out.println(car.getImageURL());
        }
        infoAdapter.notifyDataSetChanged();
    }

    private void printAll(){
        for (Car car : carInfoArraylist){
            System.out.println(car.getBrandName());
        }
    }
}