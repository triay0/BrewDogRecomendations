package com.sopra.brewdogrecomendations.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sopra.brewdogrecomendations.databinding.BeerBinding;
import com.sopra.brewdogrecomendations.viewmodel.BeerModel;

import java.util.ArrayList;

public class BeerAdapter extends RecyclerView.Adapter<BeerAdapter.BeerViewHolder> {

    private ArrayList<BeerModel> beers;
    private LayoutInflater layoutInflater;

    public BeerAdapter(ArrayList<BeerModel> beers) {
        this.beers = beers;
    }

    @NonNull
    @Override
    public BeerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }

        BeerBinding beerBinding = BeerBinding.inflate(layoutInflater, parent, false);
        return new BeerViewHolder(beerBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BeerViewHolder holder, int position) {
        BeerModel currentBeer = beers.get(position);
        holder.bind(currentBeer);
    }

    @Override
    public int getItemCount() {
        return beers.size();
    }

    class BeerViewHolder extends RecyclerView.ViewHolder {
        private final BeerBinding binding;

        BeerViewHolder(BeerBinding beerBinding) {
            super(beerBinding.getRoot());
            this.binding = beerBinding;
        }

        private void bind(BeerModel beerModel) {
            this.binding.setBeerview(beerModel);
            Glide.with(itemView).load(beerModel.image_url).into(this.binding.ivBeer);
        }

    }
}
