package com.example.busapp.adapter;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.busapp.R;
import com.example.busapp.model.BusInfoItem;
import com.example.busapp.model.BusListItem;

import java.util.ArrayList;

public class BusStopInfoRecyclerAdapter extends RecyclerView.Adapter<BusStopInfoRecyclerAdapter.ItemViewHolder> {
    private ArrayList<BusInfoItem> busInfoItems = new ArrayList<>();

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_busstop_info, parent, false);

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        String firstMin = busInfoItems.get(position).getFirstMin();
        String secondMin = busInfoItems.get(position).getSecondMin();
        holder.textView_busStopName.setText(busInfoItems.get(position).getBusNo());
        if (firstMin == null)
            holder.textView_firstMin.setText("도착정보 없음");
        else
            holder.textView_firstMin.setText("약 " + firstMin + "  분");
        if (secondMin == null)
            holder.textView_secondMin.setText("도착정보 없음");
        else
            holder.textView_secondMin.setText("약 " + secondMin + "  분");
    }

    @Override
    public int getItemCount() {
        return busInfoItems.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView textView_busStopName;
        TextView textView_firstMin;
        TextView textView_secondMin;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_busStopName = itemView.findViewById(R.id.tv_busStopName);
            textView_firstMin = itemView.findViewById(R.id.tv_firstMin);
            textView_secondMin = itemView.findViewById(R.id.tv_secondMin);
        }
    }
    public void addItems(ArrayList<BusInfoItem> busInfoItems) {
        this.busInfoItems = busInfoItems;
    }

}
