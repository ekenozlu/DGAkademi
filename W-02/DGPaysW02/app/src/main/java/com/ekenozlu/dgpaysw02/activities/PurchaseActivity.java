package com.ekenozlu.dgpaysw02.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ekenozlu.dgpaysw02.databinding.ActivityPurchaseBinding;
import com.ekenozlu.dgpaysw02.roomdb.CartDatabase;
import com.ekenozlu.dgpaysw02.roomdb.ProductDAO;
import com.google.firebase.auth.FirebaseAuth;

public class PurchaseActivity extends AppCompatActivity {

    private ActivityPurchaseBinding binding;
    FirebaseAuth auth;
    CartDatabase cartDatabase;
    ProductDAO productDAO;
    int backpressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPurchaseBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        auth = FirebaseAuth.getInstance();

        cartDatabase = Room.databaseBuilder(PurchaseActivity.this,CartDatabase.class,"Cart").allowMainThreadQueries().build();
        productDAO = cartDatabase.productDAO();

        binding.cartInfoTextView.setText("Your cart has " + productDAO.countItems() + " items");
        binding.totalCostTextView.setText("Your Total: " + productDAO.getTotalPrice() + " TL");

        backpressed = 0;
    }

    public void payFunc(View view){
        if(notEmpty() && isValid()){
            productDAO.deleteAll();
            Intent intentToBack = new Intent(PurchaseActivity.this,SecondActivity.class);
            Toast.makeText(PurchaseActivity.this,"ORDER SUCCESSFUL",Toast.LENGTH_LONG).show();
            startActivity(intentToBack);
        }
    }

    public boolean notEmpty(){
        if(binding.nameEditText.getText().toString().matches("") ||
           binding.cardNumberEditText.getText().toString().matches("") ||
           binding.expireDateEditText.getText().toString().matches("") ||
           binding.cvvEditText.getText().toString().matches("")){
            Toast.makeText(PurchaseActivity.this,"Please fill the necessary fields",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public boolean isValid(){
        boolean result;
        if(binding.cardNumberEditText.getText().toString().length() == 16 && isNumeric(binding.cardNumberEditText.getText().toString())){}
        else{
            Toast.makeText(PurchaseActivity.this,"Please provide correct card number",Toast.LENGTH_LONG).show();
            return false;
        }

        if(binding.expireDateEditText.getText().toString().length() == 5){}
        else{
            Toast.makeText(PurchaseActivity.this,"Please provide correct date type",Toast.LENGTH_LONG).show();
            return false;
        }

        if(binding.cvvEditText.getText().toString().length() == 3 && isNumeric(binding.cardNumberEditText.getText().toString())){
            result = true;
        }
        else{
            Toast.makeText(PurchaseActivity.this,"Please provide correct CVV",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public boolean isNumeric(String string) {
        if(string == null || string.equals("")) {
            return false;
        }
        try {
            long tempLong = Long.parseLong(string);
            return true;
        } catch (NumberFormatException e) {
            Toast.makeText(PurchaseActivity.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        backpressed++;
        Toast.makeText(PurchaseActivity.this,"Press again to navigate back",Toast.LENGTH_SHORT).show();
        if(backpressed == 2){
            Intent intentToBack = new Intent(PurchaseActivity.this,SecondActivity.class);
            startActivity(intentToBack);
        }
    }
}