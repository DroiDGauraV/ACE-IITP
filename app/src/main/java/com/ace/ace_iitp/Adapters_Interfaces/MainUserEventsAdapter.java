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

public class MainUserEventsAdapter extends RecyclerView.Adapter<MainUserEventsAdapter.UserEventViewHolder> {

    private List<ImageItems> slideItems;

    public MainUserEventsAdapter(List<ImageItems> slideItems) {
        this.slideItems = slideItems;
    }

    @NonNull
    @Override
    public MainUserEventsAdapter.UserEventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainUserEventsAdapter.UserEventViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_user_events_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MainUserEventsAdapter.UserEventViewHolder holder, int position) {
        holder.setup(slideItems.get(position));
    }

    @Override
    public int getItemCount() {
        return slideItems.size();
    }

    static class UserEventViewHolder extends RecyclerView.ViewHolder {
        public final ImageView img_user;
        public final TextView title_user;

        public UserEventViewHolder(@NonNull View itemView) {
            super(itemView);
            img_user = itemView.findViewById(R.id.view_img_user_event);
            title_user = itemView.findViewById(R.id.view_tv_user_event);
        }

        public void setup(ImageItems homeSlideItems) {
            //to display from firebase use picasso or glide
            //img_user.setImageResource(homeSlideItems.getImage());
            //title_user.setText(homeSlideItems.getTitle());
        }
    }

}
