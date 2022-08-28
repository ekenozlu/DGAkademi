package com.ekenozlu.dgpaysfinal.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ekenozlu.dgpaysfinal.databinding.GenreRecyclerrowBinding;

import java.util.List;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.GenreVH>{

    List<String> genreList;

    public GenreAdapter(List<String> genreList) {
        this.genreList = genreList;
    }

    @NonNull
    @Override
    public GenreVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        GenreRecyclerrowBinding genreRecyclerrowBinding = GenreRecyclerrowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new GenreVH(genreRecyclerrowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull GenreVH holder, int position) {
        holder.genreRecyclerrowBinding.genreNameTextView.setText(genreList.get(position));
    }

    @Override
    public int getItemCount() {
        return genreList.size();
    }

    public class GenreVH extends RecyclerView.ViewHolder{
        GenreRecyclerrowBinding genreRecyclerrowBinding;
        public GenreVH(GenreRecyclerrowBinding genreRecyclerrowBinding) {
            super(genreRecyclerrowBinding.getRoot());
            this.genreRecyclerrowBinding = genreRecyclerrowBinding;
        }
    }
}
