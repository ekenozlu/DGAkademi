package com.ekenozlu.dgpaysw02.activities;

import android.content.Intent;
import android.os.Bundle;
import com.ekenozlu.dgpaysw02.roomdb.CartDatabase;
import com.ekenozlu.dgpaysw02.roomdb.ProductDAO;
import com.google.android.material.tabs.TabLayout;
import androidx.room.Room;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import com.ekenozlu.dgpaysw02.ui.main.SectionsPagerAdapter;
import com.ekenozlu.dgpaysw02.databinding.ActivitySecondBinding;
import com.google.firebase.auth.FirebaseAuth;

public class SecondActivity extends AppCompatActivity {
    private ActivitySecondBinding binding;
    private FirebaseAuth auth;
    CartDatabase cartDatabase;
    ProductDAO productDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySecondBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);

        cartDatabase = Room.databaseBuilder(SecondActivity.this,CartDatabase.class,"Cart").allowMainThreadQueries().build();
        productDAO = cartDatabase.productDAO();
    }

    public void logOutFunc(View view){
        auth.signOut();
        Intent intentToMain = new Intent(SecondActivity.this,MainActivity.class);
        startActivity(intentToMain);
        finish();
    }

    public void buyFunc(View view){
        Intent intentToBuyScreen = new Intent(SecondActivity.this,PurchaseActivity.class);
        startActivity(intentToBuyScreen);
        finish();
    }
}