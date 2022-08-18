package com.ekenozlu.dgpaysw02.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import com.ekenozlu.dgpaysw02.databinding.ShopProductboxBinding;
import com.ekenozlu.dgpaysw02.models.Product;
import com.ekenozlu.dgpaysw02.roomdb.CartDatabase;
import com.ekenozlu.dgpaysw02.roomdb.ProductDAO;
import com.ekenozlu.dgpaysw02.ui.main.fragments.CartFragment;
import com.squareup.picasso.Picasso;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.productVH>{

    CartDatabase cartDatabase;
    ProductDAO productDAO;
    private List<Product> productArrayList;

    public ProductAdapter(List<Product> productArrayList) {
        this.productArrayList = productArrayList;
    }

    @NonNull
    @Override
    public productVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        cartDatabase = Room.databaseBuilder(parent.getContext(),CartDatabase.class,"Cart").allowMainThreadQueries().build();
        productDAO = cartDatabase.productDAO();
        ShopProductboxBinding shopProductboxBinding = ShopProductboxBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new productVH(shopProductboxBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull productVH holder, int position) {
        Picasso.get().load(productArrayList.get(position).productPhoto).into(holder.shopProductboxBinding.productImageView);
        holder.shopProductboxBinding.productNameTextView.setText(productArrayList.get(position).productName);
        holder.shopProductboxBinding.productPriceTextView.setText(productArrayList.get(position).productPrice.toString() + " TL");

        holder.shopProductboxBinding.productBuyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(productDAO.isExist(productArrayList.get(position).productName)){
                    productDAO.incrementQuantity(productArrayList.get(position).productName);
                }
                else{
                    productDAO.addToCart(productArrayList.get(position));
                    productDAO.incrementQuantity(productArrayList.get(position).productName);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    public class productVH extends RecyclerView.ViewHolder{
        ShopProductboxBinding shopProductboxBinding;
        public productVH(ShopProductboxBinding shopProductboxBinding) {
            super(shopProductboxBinding.getRoot());
            this.shopProductboxBinding = shopProductboxBinding;
        }
    }
}
