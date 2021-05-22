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
import com.ace.ace_iitp.DataItems.FeedStoriesData;
import com.ace.ace_iitp.R;

import java.util.ArrayList;
import java.util.List;

public class FeedStoriesAdapter extends RecyclerView.Adapter<FeedStoriesAdapter.DataViewHolder>
        implements Filterable {

    private List<FeedStoriesData> feedStoriesList;
    private List<FeedStoriesData> fullList;
    private EventClick eventClick;

    public FeedStoriesAdapter(EventClick eventClick) {
        this.eventClick = eventClick;
    }

    public void setFeedStoriesList(List<FeedStoriesData> feedStoriesList) {
        this.feedStoriesList = feedStoriesList;
        fullList = new ArrayList<>(this.feedStoriesList);
    }

    @NonNull
    @Override
    public FeedStoriesAdapter.DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feed_feed_card, parent, false);
        return new FeedStoriesAdapter.DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedStoriesAdapter.DataViewHolder holder, int position) {
        FeedStoriesData dataItems = feedStoriesList.get(position);
        String pfpurl = dataItems.getPfp();
        String imgUrl = dataItems.getImg();
        Glide.with(holder.itemView.getContext())
                .load(imgUrl)
                .placeholder(R.mipmap.ic_ace_logo_foreground)
                .into(holder.img);
        Glide.with(holder.itemView.getContext())
                .load(pfpurl)
                .placeholder(R.mipmap.ic_ace_logo_foreground)
                .into(holder.pfp);
        holder.tv_tile.setText(dataItems.getTitle());
        holder.tv_op.setText(dataItems.getOp());
        holder.desc.setText(dataItems.getDesc());

    }


    @Override
    public int getItemCount() {
        if (feedStoriesList == null) {
            return 0;
        } else {
            return feedStoriesList.size();
        }
    }

    @Override
    public Filter getFilter() {
        return valuesFilter;
    }

    private Filter valuesFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<FeedStoriesData> filteredList = new ArrayList<>();

            if (constraint.toString().isEmpty()) {
                filteredList.addAll(fullList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (FeedStoriesData item : fullList) {
                    if (item.getTitle().toLowerCase().contains(filterPattern)) {
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

            feedStoriesList.clear();
            feedStoriesList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };


    class DataViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView tv_tile;
        public final TextView tv_op;
        public final ImageView img;
        public final ImageView pfp;
        public final TextView desc;
        public final ImageView fb;
        public final ImageView insta;

        public DataViewHolder(View view) {
            super(view);
            mView = view;
            img = view.findViewById(R.id.view_img_post);
            tv_tile = view.findViewById(R.id.view_tv_Title);
            tv_op = view.findViewById(R.id.view_tv_op);
            pfp = view.findViewById(R.id.view_img_pfp);
            desc = view.findViewById(R.id.view_tv_post_desc);
            fb = view.findViewById(R.id.feed_btn_fb);
            insta = view.findViewById(R.id.feed_btn_insta);

            fb.setOnClickListener(v -> eventClick.activeClick(getAdapterPosition()));
            insta.setOnClickListener(v1 -> eventClick.nonactiveClick(getAdapterPosition()));
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + tv_op.getText() + "'";
        }
    }
}
