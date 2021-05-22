package com.ace.ace_iitp.UI.eventsFrags;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ace.ace_iitp.Adapters_Interfaces.EventClick;
import com.ace.ace_iitp.Adapters_Interfaces.OngoingEventsRecyclerViewAdapter;
import com.ace.ace_iitp.Adapters_Interfaces.UpcomingEventsRecyclerViewAdapter;
import com.ace.ace_iitp.DataItems.EventsDataItems;
import com.ace.ace_iitp.R;
import com.ace.ace_iitp.ViewModel.SharedViewModel;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

public class EventSearchFragment extends Fragment implements EventClick, MaterialSearchBar.OnSearchActionListener {

    private OngoingEventsRecyclerViewAdapter EventAdapter;
    private UpcomingEventsRecyclerViewAdapter upcomingAdapter;
    private NavController navController;
    private List<EventsDataItems> eventsDataItemsListActive;
    private List<EventsDataItems> eventsDataItemsListNonActive;


    public EventSearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        eventsDataItemsListActive = new ArrayList<>();
        eventsDataItemsListNonActive = new ArrayList<>();
        EventAdapter = new OngoingEventsRecyclerViewAdapter(this);
        upcomingAdapter = new UpcomingEventsRecyclerViewAdapter(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //nav controller here
        navController = Navigation.findNavController(view);

        setupSearchView(view);

        setUpOngoingEventsView(view);

        setUpUpcomingEventsView(view);

        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //view model here
        SharedViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        //calling for getEvents here
        //sharedViewModel.callEventsData();
        sharedViewModel.getEventsListLiveData().observe(getViewLifecycleOwner(), eventsDataItemsList -> {

            for (EventsDataItems item1 : eventsDataItemsList) {
                if (item1.isActive()) {
                    this.eventsDataItemsListActive.add(item1);
                }
            }
            EventAdapter.setEventsDataItems(eventsDataItemsListActive);
            EventAdapter.notifyDataSetChanged();


            for (EventsDataItems item2: eventsDataItemsList) {
                if (!item2.isActive()) {
                    this.eventsDataItemsListNonActive.add(item2);
                }
            }
            upcomingAdapter.setEventsDataItems(eventsDataItemsListNonActive);
            upcomingAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public void activeClick(int position) {
        navController.navigate(EventSearchFragmentDirections
                .actionSearchFragmentToEventInfoFragment(eventsDataItemsListActive.get(position)));
    }

    @Override
    public void nonactiveClick(int position) {
        navController.navigate(EventSearchFragmentDirections
                .actionSearchFragmentToEventInfoFragment(eventsDataItemsListNonActive.get(position)));
    }

    private void setUpOngoingEventsView(View view) {

        RecyclerView recyclerView = view.findViewById(R.id.ongoing_search_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(EventAdapter);
        recyclerView.setNestedScrollingEnabled(false);
    }

    private void setUpUpcomingEventsView(View view) {

        RecyclerView recyclerView = view.findViewById(R.id.upcoming_search_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(upcomingAdapter);
        recyclerView.setNestedScrollingEnabled(false);
    }

    public void setupSearchView (View view) {

        //Toolbar toolbar = view.findViewById(R.id.toolbar_search);

        MaterialSearchBar searchBar = view.findViewById(R.id.eventsearchBar);
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
                EventAdapter.getFilter().filter(charSequence);
                upcomingAdapter.getFilter().filter(charSequence);
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
    public void onDestroyView() {
        eventsDataItemsListNonActive.clear();
        eventsDataItemsListActive.clear();
        super.onDestroyView();
    }
}