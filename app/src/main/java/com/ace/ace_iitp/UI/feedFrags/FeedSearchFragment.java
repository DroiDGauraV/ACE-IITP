package com.ace.ace_iitp.UI.feedFrags;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ace.ace_iitp.Adapters_Interfaces.EventClick;
import com.ace.ace_iitp.Adapters_Interfaces.FeedStoriesAdapter;
import com.ace.ace_iitp.DataItems.FeedStoriesData;
import com.ace.ace_iitp.R;
import com.ace.ace_iitp.ViewModel.SharedViewModel;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;


public class FeedSearchFragment extends Fragment implements EventClick, MaterialSearchBar.OnSearchActionListener{

    private FeedStoriesAdapter adapter;
    private NavController navController;
    private List<FeedStoriesData> list;

    public FeedSearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        list  = new ArrayList<>();
        adapter = new FeedStoriesAdapter(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //nav controller here
        navController = Navigation.findNavController(view);

        setupSearchView(view);

        setUprcv(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        SharedViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        sharedViewModel.getStorieslistLiveData().observe(getViewLifecycleOwner(), storiesDataList -> {
            this.list.addAll(storiesDataList);
            adapter.setFeedStoriesList(list);
            adapter.notifyDataSetChanged();
        });
    }


    private void setUprcv(View view) {

        RecyclerView rcv = view.findViewById(R.id.feed_search_story_rcv);
        rcv.setHasFixedSize(true);
        rcv.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        rcv.setAdapter(adapter);
    }

    public void setupSearchView (View view) {

        //Toolbar toolbar = view.findViewById(R.id.toolbar_search);

        MaterialSearchBar searchBar = view.findViewById(R.id.feedsearchBar);
        searchBar.setOnSearchActionListener(this);
        searchBar.setText("");
        searchBar.setCardViewElevation(10);
        searchBar.openSearch();


        searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });

    }

    @Override
    public void onSearchStateChanged(boolean enabled) {
        if (!enabled) {
            navController.navigateUp();
        }
    }

    @Override
    public void onSearchConfirmed(CharSequence text) {

    }

    @Override
    public void onButtonClicked(int buttonCode) {


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

    @Override
    public void onDestroyView() {
        list.clear();
        super.onDestroyView();
    }
}