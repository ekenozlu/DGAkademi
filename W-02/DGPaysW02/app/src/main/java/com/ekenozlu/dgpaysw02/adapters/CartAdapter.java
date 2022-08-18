package com.ekenozlu.dgpaysw02.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import com.ekenozlu.dgpaysw02.databinding.CartitemRecyclerviewBinding;
import com.ekenozlu.dgpaysw02.models.Product;
import com.ekenozlu.dgpaysw02.roomdb.CartDatabase;
import com.ekenozlu.dgpaysw02.roomdb.ProductDAO;
import com.squareup.picasso.Picasso;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.cartVH> {

    CartDatabase cartDatabase;
    ProductDAO productDAO;

    private List<Product> cartItemList;

    public CartAdapter(List<Product> cartItemList) {
        this.cartItemList = cartItemList;
    }

    @NonNull
    @Override
    public cartVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        cartDatabase = Room.databaseBuilder(parent.getContext(),CartDatabase.class,"Cart").allowMainThreadQueries().build();
        productDAO = cartDatabase.productDAO();

        CartitemRecyclerviewBinding cartitemRecyclerviewBinding = CartitemRecyclerviewBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new cartVH(cartitemRecyclerviewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull cartVH holder, int position) {
        Picasso.get().load(cartItemList.get(position).productPhoto).into(holder.cartitemRecyclerviewBinding.imageView);
        holder.cartitemRecyclerviewBinding.cartProductName.setText(cartItemList.get(position).productName);
        holder.cartitemRecyclerviewBinding.cartProductPrice.setText(cartItemList.get(position).productPrice.toString() + " TL");
        holder.cartitemRecyclerviewBinding.cartItemQuantity.setText(productDAO.getQuantity(cartItemList.get(position).productName) + "");
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    public class cartVH extends RecyclerView.ViewHolder{
        CartitemRecyclerviewBinding cartitemRecyclerviewBinding;
        public cartVH(CartitemRecyclerviewBinding cartitemRecyclerviewBinding) {
            super(cartitemRecyclerviewBinding.getRoot());
            this.cartitemRecyclerviewBinding = cartitemRecyclerviewBinding;
        }
    }
}
