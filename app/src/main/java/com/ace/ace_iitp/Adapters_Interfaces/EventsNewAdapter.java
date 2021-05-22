package com.ace.ace_iitp.Adapters_Interfaces;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ace.ace_iitp.DataItems.EventsDataItems;
import com.ace.ace_iitp.R;

import java.util.List;

public class EventsNewAdapter extends RecyclerView.Adapter<EventsNewAdapter.NewViewHolder>{

    private List<EventsDataItems> newEventsDataItems;
    private RecyclerViewClick recyclerViewClick;

    public EventsNewAdapter(RecyclerViewClick recyclerViewClick) {
        this.recyclerViewClick = recyclerViewClick;
    }

    public void setNewEventsDataItems(List<EventsDataItems> newEventsDataItems) {
        this.newEventsDataItems = newEventsDataItems;
    }

    @NonNull
    @Override
    public NewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.events_new_banner, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewViewHolder holder, int position) {
        holder.setup(newEventsDataItems.get(position));
    }

    @Override
    public int getItemCount() {
        if (newEventsDataItems == null) {
            return 0;
        } else {
            //TODO: fix this statement to desired numbers of displayed cards.
            return newEventsDataItems.size();
        }
    }

    class NewViewHolder extends RecyclerView.ViewHolder {
        public final ImageView img_new;
        public final TextView new_title;

        public NewViewHolder(@NonNull View itemView) {
            super(itemView);
            img_new = itemView.findViewById(R.id.view_img_new_event);
            new_title = itemView.findViewById(R.id.view_tv_new_event);

            itemView.setOnClickListener(v -> recyclerViewClick.OnItemClick(getAdapterPosition()));
        }

        public void setup(EventsDataItems eventsDataItems) {
            //to display from firebase use picasso or glide
            //img_new.setImageResource(homeSlideItems.getImage());
            new_title.setText(eventsDataItems.getName());
        }
    }

}
