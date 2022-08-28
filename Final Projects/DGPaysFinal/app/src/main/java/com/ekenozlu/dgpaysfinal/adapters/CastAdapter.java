package com.ekenozlu.dgpaysfinal.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ekenozlu.dgpaysfinal.databinding.CastRecyclerrowBinding;
import com.ekenozlu.dgpaysfinal.models.CastModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.CastVH> {

    List<CastModel> castModelList;

    public CastAdapter(List<CastModel> castModelList) {
        this.castModelList = castModelList;
    }

    @NonNull
    @Override
    public CastVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CastRecyclerrowBinding castRecyclerrowBinding = CastRecyclerrowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new CastVH(castRecyclerrowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CastVH holder, int position) {
        Picasso.get().load("https://image.tmdb.org/t/p/original" + castModelList.get(position).getPhotoURL()).into(holder.castRecyclerrowBinding.castImage);
        holder.castRecyclerrowBinding.castName.setText(castModelList.get(position).getName());
        holder.castRecyclerrowBinding.castCharacterName.setText(castModelList.get(position).getCharacterName());
    }

    @Override
    public int getItemCount() {
        return castModelList.size();
    }

    public class CastVH extends RecyclerView.ViewHolder{
        CastRecyclerrowBinding castRecyclerrowBinding;
        public CastVH(CastRecyclerrowBinding castRecyclerrowBinding) {
            super(castRecyclerrowBinding.getRoot());
            this.castRecyclerrowBinding = castRecyclerrowBinding;
        }
    }
}
