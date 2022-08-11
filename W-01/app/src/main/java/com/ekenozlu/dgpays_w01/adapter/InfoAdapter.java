package com.ekenozlu.dgpays_w01.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ekenozlu.dgpays_w01.databinding.RecyclerRowBinding;
import com.ekenozlu.dgpays_w01.model.Car;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.InfoAdapterVH> {

    private ArrayList<Car> carInfoArrayList;

    public InfoAdapter(ArrayList<Car> carInfoArrayList) {
        this.carInfoArrayList = carInfoArrayList;
    }

    @NonNull
    @Override
    public InfoAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerRowBinding recyclerInfoBinding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new InfoAdapterVH(recyclerInfoBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull InfoAdapterVH holder, int position) {
        //holder.recyclerInfoBinding.brandLogoImageView
        Picasso.get().load(carInfoArrayList.get(position).getImageURL()).into(holder.recyclerInfoBinding.brandLogoImageView);
        holder.recyclerInfoBinding.brandNameTextView.setText(carInfoArrayList.get(position).getBrandName());
        holder.recyclerInfoBinding.doorTextView.setText((carInfoArrayList.get(position).getDoorNumber()) + "");
        holder.recyclerInfoBinding.engineTextView.setText((carInfoArrayList.get(position).getEngineNumber()) + "");
        holder.recyclerInfoBinding.wheelTextView.setText((carInfoArrayList.get(position).getWheelNumber()) + "");
    }

    @Override
    public int getItemCount() {
        return carInfoArrayList.size();
    }

    public class InfoAdapterVH extends RecyclerView.ViewHolder{
        RecyclerRowBinding recyclerInfoBinding;
        public InfoAdapterVH(RecyclerRowBinding recyclerInfoBinding) {
            super(recyclerInfoBinding.getRoot());
            this.recyclerInfoBinding = recyclerInfoBinding;
        }
    }
}
