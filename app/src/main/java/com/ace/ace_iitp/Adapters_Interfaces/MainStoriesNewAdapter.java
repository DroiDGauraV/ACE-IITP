package com.ace.ace_iitp.Adapters_Interfaces;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ace.ace_iitp.DataItems.FeedStoriesData;
import com.ace.ace_iitp.R;

import java.util.List;

public class MainStoriesNewAdapter extends RecyclerView.Adapter<MainStoriesNewAdapter.NewViewHolder> {

    private List<FeedStoriesData> slideItems;
    private RecyclerViewClick recyclerViewClick;

    public void setSlideItems(List<FeedStoriesData> slideItems) {
        this.slideItems = slideItems;
    }

    public MainStoriesNewAdapter(RecyclerViewClick recyclerViewClick) {
        this.recyclerViewClick = recyclerViewClick;
    }

    @NonNull
    @Override
    public NewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_new_stories_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewViewHolder holder, int position) {
        FeedStoriesData feedStoriesData = slideItems.get(position);
        String imgUrl = feedStoriesData.getImg();
        Glide.with(holder.itemView.getContext())
                .load(imgUrl)
                .centerCrop()
                .placeholder(R.mipmap.ic_ace_logo_foreground)
                .into(holder.img_new);
        holder.new_title.setText(feedStoriesData.getTitle());
    }

    @Override
    public int getItemCount() {
        if (slideItems == null) {
            return 0;
        } else
            return slideItems.size();
    }

    class NewViewHolder extends RecyclerView.ViewHolder {
        public final ImageView img_new;
        public final TextView new_title;

        public NewViewHolder(@NonNull View itemView) {
            super(itemView);
            img_new = itemView.findViewById(R.id.view_img_new_story);
            new_title = itemView.findViewById(R.id.view_tv_new_story);
            itemView.setOnClickListener(v -> recyclerViewClick.OnItemClick(getAdapterPosition()));
        }
    }

}
