package com.ace.ace_iitp.Adapters_Interfaces;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ace.ace_iitp.DataItems.EventsProblems;
import com.ace.ace_iitp.R;

import java.util.List;

public class ProblemRcvAdapter extends RecyclerView.Adapter<ProblemRcvAdapter.DataViewHolder> {

    RecyclerViewClick recyclerViewClick;
    List<EventsProblems> eventsProblemsList;

    private static final String TAG = "ProblemRcvAdapter";

    public void setEventsProblemsList(List<EventsProblems> eventsProblemsList) {
        this.eventsProblemsList = eventsProblemsList;
    }

    public ProblemRcvAdapter(RecyclerViewClick recyclerViewClick) {
        this.recyclerViewClick = recyclerViewClick;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProblemRcvAdapter.DataViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.problem_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        if (eventsProblemsList.get(position).getImg_prob()==null)
            Log.e(TAG, "onBindViewHolder: empty");
        Glide.with(holder.itemView.getContext())
                .load(eventsProblemsList.get(position).getImg_prob())
                .placeholder(R.mipmap.ic_ace_logo_foreground)
                .into(holder.img_prob);
        int num = position + 1;
        holder.tv_number.setText("Problem Statement " + num);
        holder.tv_title.setText(eventsProblemsList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        if (eventsProblemsList == null) {
            return 0;
        } else {
            return eventsProblemsList.size();
        }
    }

    class DataViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView tv_title;
        public final TextView tv_number;
        public final ImageView img_prob;

        public DataViewHolder(View view) {
            super(view);
            mView = view;
            img_prob = view.findViewById(R.id.view_img_prob);
            tv_title = view.findViewById(R.id.view_text_prob);
            tv_number = view.findViewById(R.id.view_text_prob_number);


            view.setOnClickListener(v -> recyclerViewClick.OnItemClick(getAdapterPosition()));
        }
    }
}
