package com.ace.ace_iitp.Adapters_Interfaces;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ace.ace_iitp.DataItems.EventsDataItems;
import com.ace.ace_iitp.R;

import java.util.ArrayList;
import java.util.List;

public class UpcomingEventsRecyclerViewAdapter extends RecyclerView.Adapter<UpcomingEventsRecyclerViewAdapter.DataViewHolder>
        implements Filterable {

    private List<EventsDataItems> eventsDataItems;
    private List<EventsDataItems> eventsListFull;
    private EventClick recyclerViewClick;

    public UpcomingEventsRecyclerViewAdapter(EventClick recyclerViewClick) {
        this.recyclerViewClick = recyclerViewClick;
    }

    public void setEventsDataItems(List<EventsDataItems> eventsDataItems) {
        this.eventsDataItems = eventsDataItems;
        eventsListFull = new ArrayList<>(this.eventsDataItems);
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.events_test_layout, parent, false);
        return new UpcomingEventsRecyclerViewAdapter.DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {

        EventsDataItems dataItems = eventsDataItems.get(position);

        String imgUrl = dataItems.getImg();
        Glide.with(holder.itemView.getContext())
                .load(imgUrl)
                .centerCrop()
                .placeholder(R.mipmap.ic_ace_logo_foreground)
                .into(holder.img_logo);
        holder.et_name.setText(dataItems.getName());

    }

    @Override
    public int getItemCount() {
        if (eventsDataItems == null) {
            return 0;
        } else {
            return eventsDataItems.size();
        }
    }

    @Override
    public Filter getFilter() {
        return valuesFilter;
    }

    private Filter valuesFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<EventsDataItems> filteredList = new ArrayList<>();

            if (constraint.toString().isEmpty()) {
                filteredList.addAll(eventsListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (EventsDataItems item : eventsListFull) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            eventsDataItems.clear();
            eventsDataItems.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    class DataViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView et_name;
        public final ImageView img_logo;

        public DataViewHolder(View view) {
            super(view);
            mView = view;
            img_logo = view.findViewById(R.id.view_img_logo);
            et_name = view.findViewById(R.id.view_tv_name);

            view.setOnClickListener(v -> recyclerViewClick.nonactiveClick(getAdapterPosition()));
        }

    }

}
