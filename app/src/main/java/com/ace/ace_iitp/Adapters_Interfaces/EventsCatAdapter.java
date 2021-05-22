package com.ace.ace_iitp.Adapters_Interfaces;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ace.ace_iitp.DataItems.ImageItems;
import com.ace.ace_iitp.R;

import java.util.List;

public class EventsCatAdapter extends RecyclerView.Adapter<EventsCatAdapter.CatViewHolder> {

    private List<ImageItems> slideItems;

    public EventsCatAdapter(List<ImageItems> slideItems) {
        this.slideItems = slideItems;
    }

    @NonNull
    @Override
    public EventsCatAdapter.CatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.events_category_layout, parent, false);
        return new CatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventsCatAdapter.CatViewHolder holder, int position) {
        holder.setup(slideItems.get(position));
    }

    @Override
    public int getItemCount() {
        return slideItems.size();
    }

    static class CatViewHolder extends RecyclerView.ViewHolder {
        public final ImageView img_cat;
        public final TextView cat_title;

        public CatViewHolder(@NonNull View itemView) {
            super(itemView);
            img_cat = itemView.findViewById(R.id.view_img_cat_logo);
            cat_title = itemView.findViewById(R.id.view_tv_cat_title);
        }

        public void setup(ImageItems homeSlideItems) {
            //to display from firebase use picasso or glide
            //img_cat.setImageResource(homeSlideItems.getImage());
            //cat_title.setText(homeSlideItems.getTitle());
        }
    }

}
