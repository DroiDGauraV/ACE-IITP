package com.ace.ace_iitp.Adapters_Interfaces;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ace.ace_iitp.DataItems.FeedNewsItems;
import com.ace.ace_iitp.R;

import java.util.List;

public class FeedNewsAdapter extends RecyclerView.Adapter<FeedNewsAdapter.DataViewHolder> {

    private List<FeedNewsItems> newsList;

    public FeedNewsAdapter() {
    }

    public void setNewsList(List<FeedNewsItems> newsList) {
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FeedNewsAdapter.DataViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_layout, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        holder.et_text.setText(newsList.get(position).getTxt().replaceAll("<br>", "\n"));
    }

    @Override
    public int getItemCount() {
        if (newsList == null) {
            return 0;
        } else
            return newsList.size();
    }

    static class DataViewHolder extends RecyclerView.ViewHolder {

        public final TextView et_text;

        public DataViewHolder(View view) {
            super(view);
            et_text = view.findViewById(R.id.tv_feed_news);
        }

    }
}