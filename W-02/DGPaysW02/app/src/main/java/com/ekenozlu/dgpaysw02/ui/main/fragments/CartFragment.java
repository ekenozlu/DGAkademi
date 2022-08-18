package com.ekenozlu.dgpaysw02.ui.main.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import com.ekenozlu.dgpaysw02.R;
import com.ekenozlu.dgpaysw02.adapters.CartAdapter;
import com.ekenozlu.dgpaysw02.models.Product;
import com.ekenozlu.dgpaysw02.roomdb.CartDatabase;
import com.ekenozlu.dgpaysw02.roomdb.ProductDAO;
import java.util.ArrayList;

public class CartFragment extends Fragment {

    ArrayList<Product> cartArrayList;
    CartDatabase cartDatabase;
    ProductDAO productDAO;
    CartAdapter cartAdapter;

    public static CartFragment newInstance(){
        return new CartFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = getContext();
        cartArrayList = new ArrayList<>();
        cartDatabase = Room.databaseBuilder(context,CartDatabase.class,"Cart").allowMainThreadQueries().build();
        productDAO = cartDatabase.productDAO();
        getItems();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final RecyclerView cartItemRecyclerView = view.findViewById(R.id.cartRecyclerView);
        cartItemRecyclerView.setLayoutManager(new LinearLayoutManager(CartFragment.newInstance().getContext()));
        cartAdapter = new CartAdapter(cartArrayList);
        cartItemRecyclerView.setAdapter(cartAdapter);

        final TextView totalTextView = view.findViewById(R.id.totalTextView);
        totalTextView.setText("Your Total: " + productDAO.getTotalPrice() + "TL");
    }

    public void getItems() {
            cartArrayList = (ArrayList<Product>) cartDatabase.productDAO().getAllItems();
    }
}
