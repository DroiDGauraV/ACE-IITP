package com.ace.ace_iitp.UI.feedFrags;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ace.ace_iitp.Adapters_Interfaces.EventClick;
import com.ace.ace_iitp.Adapters_Interfaces.FeedStoriesAdapter;
import com.ace.ace_iitp.R;
import com.ace.ace_iitp.ViewModel.SharedViewModel;
import com.ace.ace_iitp.DataItems.FeedStoriesData;

import java.util.ArrayList;
import java.util.List;


public class FeedStoryFragment extends Fragment implements EventClick {

    private FeedStoriesAdapter adapter = new FeedStoriesAdapter(this);
    private List<FeedStoriesData> list;

    public FeedStoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        //view model here
        SharedViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        sharedViewModel.getStorieslistLiveData().observe(getViewLifecycleOwner(), storiesDataList -> {
            list = new ArrayList<>(storiesDataList);
            adapter.setFeedStoriesList(storiesDataList);
            adapter.notifyDataSetChanged();
        });

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.feed_story_tab, container, false);

        setUprcv(view);

        return view;
    }

    private void setUprcv(View view) {

        RecyclerView rcv = view.findViewById(R.id.feed_story_rcv);
        rcv.setHasFixedSize(true);
        rcv.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        rcv.setAdapter(adapter);
    }

    @Override
    public void activeClick(int position) {
        startActivity(new Intent(Intent.ACTION_VIEW,
                Uri.parse(list.get(position).getFb())));
    }

    @Override
    public void nonactiveClick(int position) {
        startActivity(new Intent(Intent.ACTION_VIEW,
                Uri.parse(list.get(position).getInsta())));
    }
}