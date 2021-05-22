package com.ace.ace_iitp.Adapters_Interfaces;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ace.ace_iitp.DataItems.ImageItems;
import com.ace.ace_iitp.R;

import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.DataViewHolder> {

    private RecyclerViewClick recyclerViewClick;
    private List<ImageItems> homeSlideItems;
    private Context context;

    public GalleryAdapter(RecyclerViewClick recyclerViewClick, Context context) {
        this.recyclerViewClick = recyclerViewClick;
        this.context = context;
    }

    public void setHomeSlideItems(List<ImageItems> homeSlideItems) {
        this.homeSlideItems = homeSlideItems;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GalleryAdapter.DataViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gallery_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        Glide.with(context)
                .load(homeSlideItems.get(position).getImg())
                .fitCenter()
                .placeholder(R.mipmap.ic_ace_logo_foreground)
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        if (homeSlideItems == null) {
            return 0;
        } else
            return homeSlideItems.size();
    }

    class DataViewHolder extends RecyclerView.ViewHolder {

        public final ImageView img;

        public DataViewHolder(View view) {
            super(view);
            img = view.findViewById(R.id.view_img_gallery);

            view.setOnClickListener(v -> recyclerViewClick.OnItemClick(getAdapterPosition()));
        }

    }

}
