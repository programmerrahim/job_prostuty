package com.ruksana.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ruksana.model.VerticalItem;
import com.ruksana.jobprostuti.R;

import java.util.List;

public class VerticalAdapter extends RecyclerView.Adapter<VerticalAdapter.VerticalViewHolder> {

    private List<VerticalItem> verticalItemList;
    private Context context;

    public VerticalAdapter(List<VerticalItem> verticalItemList, Context context) {
        this.verticalItemList = verticalItemList;
        this.context = context;
    }

    @NonNull
    @Override
    public VerticalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vertical, parent, false);
        return new VerticalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VerticalViewHolder holder, int position) {
        VerticalItem verticalItem = verticalItemList.get(position);
        holder.tvVerticalTitle.setText(verticalItem.getTitle());

        // Set up horizontal RecyclerView
        HorizontalAdapter horizontalAdapter = new HorizontalAdapter(verticalItem.getHorizontalItems(), context);

        // Create a LinearLayoutManager and set reverseLayout to true
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(
                holder.itemView.getContext(),
                LinearLayoutManager.HORIZONTAL,
                false // layout in normal order (left to right)
        );
        horizontalLayoutManager.setReverseLayout(true); // Reverse the order of items

        holder.rvHorizontal.setLayoutManager(horizontalLayoutManager);
        holder.rvHorizontal.setAdapter(horizontalAdapter);

        // Scroll to the end (start from the last item)
        holder.rvHorizontal.smoothScrollToPosition(verticalItem.getHorizontalItems().size() - 1);
    }

    @Override
    public int getItemCount() {
        return verticalItemList.size();
    }

    public static class VerticalViewHolder extends RecyclerView.ViewHolder {
        TextView tvVerticalTitle;
        RecyclerView rvHorizontal;

        public VerticalViewHolder(@NonNull View itemView) {
            super(itemView);
            tvVerticalTitle = itemView.findViewById(R.id.tvVerticalTitle);
            rvHorizontal = itemView.findViewById(R.id.rvHorizontal);
        }
    }
}