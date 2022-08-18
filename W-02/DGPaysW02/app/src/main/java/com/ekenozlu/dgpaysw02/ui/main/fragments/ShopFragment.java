package com.ekenozlu.dgpaysw02.ui.main.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import com.ekenozlu.dgpaysw02.models.Product;
import com.ekenozlu.dgpaysw02.adapters.ProductAdapter;
import com.ekenozlu.dgpaysw02.R;
import com.ekenozlu.dgpaysw02.roomdb.CartDatabase;
import com.ekenozlu.dgpaysw02.roomdb.ProductDAO;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.Map;

public class ShopFragment extends Fragment {

    private FirebaseFirestore firebaseFirestore;
    ArrayList<Product> productArrayList;
    ProductAdapter productAdapter;
    CartDatabase cartDatabase;
    ProductDAO productDAO;

    public static ShopFragment newInstance() {
        return new ShopFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseFirestore = FirebaseFirestore.getInstance();
        productArrayList = new ArrayList<>();
        getProducts();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Context context = getContext();
        cartDatabase = Room.databaseBuilder(context,CartDatabase.class,"Cart").build();
        productDAO = cartDatabase.productDAO();
        return inflater.inflate(R.layout.fragment_shop,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final RecyclerView productRecyclerView = view.findViewById(R.id.shopRecyclerView);
        productRecyclerView.setLayoutManager(new GridLayoutManager(ShopFragment.newInstance().getContext(), 2));
        productAdapter = new ProductAdapter(productArrayList);
        productRecyclerView.setAdapter(productAdapter);
    }


    private void getProducts() {
        firebaseFirestore.collection("Products").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    Toast.makeText(getContext(),error.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }
                if(value != null){
                    productArrayList.clear();
                    for(DocumentSnapshot documentSnapshot : value.getDocuments()) {
                        Map<String, Object> data = documentSnapshot.getData();
                        String productName = (String) data.get("productName");
                        String productPhoto = (String) data.get("productImage");
                        Long productPrice = (Long) data.get("productPrice");
                        Long productStock = (Long) data.get("stock");


                        Product product = new Product(productName,productPrice,productStock,productPhoto);
                        System.out.println(product.productPhoto);
                        productArrayList.add(product);
                    }
                    productAdapter.notifyDataSetChanged();
                }
            }
        });
    }


}
