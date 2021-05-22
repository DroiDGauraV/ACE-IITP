package com.ace.ace_iitp.UI.eventsFrags;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.ace.ace_iitp.Adapters_Interfaces.EventClick;
import com.ace.ace_iitp.Adapters_Interfaces.NavigationIconClickListener;
import com.ace.ace_iitp.Adapters_Interfaces.OngoingEventsRecyclerViewAdapter;
import com.ace.ace_iitp.Adapters_Interfaces.UpcomingEventsRecyclerViewAdapter;
import com.ace.ace_iitp.DataItems.EventsDataItems;
import com.ace.ace_iitp.R;
//import com.example.ace_iitp.UI.EventsFragmentDirections;
import com.ace.ace_iitp.ViewModel.SharedViewModel;

import java.util.ArrayList;
import java.util.List;

public class EventsFragment extends Fragment implements EventClick {

    private List<EventsDataItems> eventsDataItemsListActive = new ArrayList<>();
    private List<EventsDataItems> eventsDataItemsListNonActive = new ArrayList<>();
    private NavController navController;
    private OngoingEventsRecyclerViewAdapter EventAdapter = new OngoingEventsRecyclerViewAdapter(this);
    private UpcomingEventsRecyclerViewAdapter upcomingEventAdapter = new UpcomingEventsRecyclerViewAdapter(this);

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EventsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events, container, false);

        //toolbar here
        setUpToolbar(view);
        setUpBackdrop(view);
        //category view here, currently not in use
        //setUpCatView(view);
        //new event view here, currently not in use
        //setupNewEventsView(view);
        //ongoing events view here
        setUpOngoingEventsView(view);
        //upcoming events view here
        setUpUpcomingEventsView(view);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //nav controller here
        navController = Navigation.findNavController(view);

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //view model here
        SharedViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        //calling for getEvents here

        sharedViewModel.getEventsListLiveData().observe(getViewLifecycleOwner(), eventsDataItemsList -> {
            for (EventsDataItems item1 : eventsDataItemsList) {
                if (item1.isActive()) {
                    this.eventsDataItemsListActive.add(item1);
                }
            }
            //ongoing events adapter here
            EventAdapter.setEventsDataItems(eventsDataItemsListActive);
            EventAdapter.notifyDataSetChanged();

            for (EventsDataItems item2: eventsDataItemsList) {
                if (!item2.isActive()) {
                    this.eventsDataItemsListNonActive.add(item2);
                }
            }
            //upcoming events adapter here
            upcomingEventAdapter.setEventsDataItems(eventsDataItemsListNonActive);
            upcomingEventAdapter.notifyDataSetChanged();
        });
    }


    @Override
    public void activeClick(int position) {
        navController.navigate(EventsFragmentDirections
                .actionEventsFragmentToEventInfoFragment(eventsDataItemsListActive.get(position)));
    }

    @Override
    public void nonactiveClick(int position) {
        navController.navigate(EventsFragmentDirections
                .actionEventsFragmentToEventInfoFragment(eventsDataItemsListNonActive.get(position)));
    }

    //perfectly working method for toolbar here :)
    private void setUpToolbar(View view) {
        Toolbar toolbar = view.findViewById(R.id.view_events_toolbar);
        toolbar.setTitle("EVENTS");

        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.searchItem) {
                navController.navigate(R.id.action_eventsFragment_to_searchFragment);
            }
            return true;
        });
        toolbar.setNavigationOnClickListener(new NavigationIconClickListener(getContext(),
                view.findViewById(R.id.view_events_scroll), new AccelerateDecelerateInterpolator()));
    }

    private void setUpBackdrop(View view) {
        Button gallery = view.findViewById(R.id.menu_btn_gallery);
        Button ace = view.findViewById(R.id.menu_btn_ace);
        Button contact = view.findViewById(R.id.menu_btn_contact);
        ImageView fb = view.findViewById(R.id.menu_btn_fb);
        ImageView insta = view.findViewById(R.id.menu_btn_insta);
        ImageView linkd = view.findViewById(R.id.menu_btn_linkd);

        fb.setOnClickListener(v1-> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/ACE.IITP/"))));
        insta.setOnClickListener(v2-> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/ace_iitp/"))));
        linkd.setOnClickListener(v3 -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://in.linkedin.com/company/ace-iit-patna"))));
        contact.setOnClickListener(v->navController.navigate(R.id.action_eventsFragment_to_contactFragment));
        gallery.setOnClickListener(v -> navController.navigate(R.id.action_eventsFragment_to_galleryFragment));
        ace.setOnClickListener(v1->navController.navigate(R.id.action_eventsFragment_to_aboutFragment));
    }
    //top category recycler view here

    /**
     * not working right now and is disabled by setting view.INVISIBLE in xml file
     * @param view view for cat view
     */
    private void setUpCatView(View view) {

        RecyclerView catIcon = view.findViewById(R.id.recycler_view_events_cat);
        catIcon.setHasFixedSize(true);
        catIcon.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));

        //catIcon.setAdapter(new EventsCatAdapter(slideItems));
    }

    //new events recycler view here
    /**
     * not working right now and is disabled by setting view.INVISIBLE in xml file
     * @param view view for new view
     */
    private void setupNewEventsView(View view) {

        RecyclerView newBanner = view.findViewById(R.id.recycler_view_new_events_banner);
        newBanner.setHasFixedSize(true);
        newBanner.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        //newBanner.setAdapter(NewEventsAdapter);

        //snapHelper provides snappy slide
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(newBanner);
    }

    //actual events recycler view here
    private void setUpOngoingEventsView(View view) {

        RecyclerView recyclerView = view.findViewById(R.id.ongoing_events_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(EventAdapter);
        recyclerView.setNestedScrollingEnabled(false);
    }

    private void setUpUpcomingEventsView(View view) {

        RecyclerView recyclerView = view.findViewById(R.id.upcoming_events_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(upcomingEventAdapter);
        recyclerView.setNestedScrollingEnabled(false);
    }

    @Override
    public void onDestroyView() {
        eventsDataItemsListNonActive.clear();
        eventsDataItemsListActive.clear();
        super.onDestroyView();
    }
}
