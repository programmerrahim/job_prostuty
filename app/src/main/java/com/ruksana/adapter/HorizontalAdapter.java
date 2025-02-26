package com.ruksana.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.ruksana.model.HorizontalItem;
import com.ruksana.jobprostuti.R;
import com.ruksana.view.AllSubjectActivity;

import java.util.List;

public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.HorizontalViewHolder> {

    private List<HorizontalItem> horizontalItemList;
    private Context context;

    public HorizontalAdapter(List<HorizontalItem> horizontalItemList, Context context) {
        this.horizontalItemList = horizontalItemList;
        this.context = context;
    }

    @NonNull
    @Override
    public HorizontalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_horizontal, parent, false);
        return new HorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalViewHolder holder, int position) {
        HorizontalItem horizontalItem = horizontalItemList.get(position);

        // Load image using Glide
        Glide.with(holder.itemView.getContext())
                .load(horizontalItem.getImageUrl())
                .into(holder.ivHorizontalImage);

        // Set click listener
        holder.itemView.setOnClickListener(v -> {
            // Start DetailActivity and pass data
            Intent intent = new Intent(context, AllSubjectActivity.class);
            intent.putExtra("imageUrl", horizontalItem.getImageUrl());
            intent.putExtra("CATEGORY", horizontalItem.getText()); // it is come from realtime and going into firebase data
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return horizontalItemList.size();
    }

    public static class HorizontalViewHolder extends RecyclerView.ViewHolder {
        ImageView ivHorizontalImage;

        public HorizontalViewHolder(@NonNull View itemView) {
            super(itemView);
            ivHorizontalImage = itemView.findViewById(R.id.ivHorizontalImage);
        }
    }
}