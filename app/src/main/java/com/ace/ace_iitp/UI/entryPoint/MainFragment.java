package com.ace.ace_iitp.UI.entryPoint;

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
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ace.ace_iitp.Adapters_Interfaces.EventClick;
import com.ace.ace_iitp.Adapters_Interfaces.MainBannerViewAdapter;
import com.ace.ace_iitp.Adapters_Interfaces.MainEventsNewAdapter;
import com.ace.ace_iitp.Adapters_Interfaces.MainEventsOnAdapter;
import com.ace.ace_iitp.Adapters_Interfaces.NavigationIconClickListener;
import com.ace.ace_iitp.Adapters_Interfaces.RecyclerViewClick;
import com.ace.ace_iitp.DataItems.EventsDataItems;
import com.ace.ace_iitp.R;
import com.ace.ace_iitp.ViewModel.SharedViewModel;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment implements EventClick, RecyclerViewClick {

    NavController navController;
    SharedViewModel sharedViewModel;
    private final List<EventsDataItems> eventsDataItemsListActive = new ArrayList<>();
    private final List<EventsDataItems> eventsDataItemsListNonActive = new ArrayList<>();
    //private List<FeedStoriesData> feedStoriesDataList = new ArrayList<>();
    private final MainEventsNewAdapter newAdapter = new MainEventsNewAdapter(this);
    private final MainEventsOnAdapter onAdapter = new MainEventsOnAdapter(this);
    //private MainStoriesNewAdapter storiesAdapter = new MainStoriesNewAdapter(this);
    private final MainBannerViewAdapter bannerAdapter = new MainBannerViewAdapter();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        setUpToolbar(view);
        setUpBackdrop(view);
        setUpBannerView(view);
        setUpCardsMenu(view);
        //setupUserEventsView(view);
        setUpNewEventsView(view);
        //setUpOnEventsView(view);
        //setUpNewStoriesView(view);
        setUpSocials(view);

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
        //view model here
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        sharedViewModel.getEventsListLiveData().observe(getViewLifecycleOwner(), eventsDataItemsList -> {

            for (EventsDataItems item1 : eventsDataItemsList) {
                if (item1.isActive()) {
                    this.eventsDataItemsListActive.add(item1);
                }
            }
            newAdapter.setSlideItems(eventsDataItemsListActive);
            newAdapter.notifyDataSetChanged();

            for (EventsDataItems item2: eventsDataItemsList) {
                if (!item2.isActive()) {
                    this.eventsDataItemsListNonActive.add(item2);
                }
            }
            onAdapter.setSlideItems(eventsDataItemsListNonActive);
            onAdapter.notifyDataSetChanged();
        });

        sharedViewModel.getBannerListLiveData().observe(getViewLifecycleOwner(), imageItems -> {
            bannerAdapter.setSlideItems(imageItems);
            bannerAdapter.notifyDataSetChanged();
        });
        super.onActivityCreated(savedInstanceState);
    }

    /*
        toolbar or appbar setup here
         */
    private void setUpToolbar(View view) {
        Toolbar toolbar = view.findViewById(R.id.view_main_toolbar);

        toolbar.setNavigationOnClickListener(new NavigationIconClickListener(getContext(),
                view.findViewById(R.id.view_main_scroll), new AccelerateDecelerateInterpolator()));
    }

    private void setUpBackdrop(View view) {
        Button gallery = view.findViewById(R.id.menu_btn_gallery);
        Button ace = view.findViewById(R.id.menu_btn_ace);
        ImageView fb = view.findViewById(R.id.menu_btn_fb);
        ImageView insta = view.findViewById(R.id.menu_btn_insta);
        ImageView linkd = view.findViewById(R.id.menu_btn_linkd);

        fb.setOnClickListener(v1-> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/ACE.IITP/"))));
        insta.setOnClickListener(v2-> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/ace_iitp/"))));
        linkd.setOnClickListener(v3 -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://in.linkedin.com/company/ace-iit-patna"))));
        gallery.setOnClickListener(v -> navController.navigate(R.id.action_mainFragment_to_galleryFragment));
        ace.setOnClickListener(v->navController.navigate(R.id.action_mainFragment_to_aboutFragment));
        Button contact = view.findViewById(R.id.menu_btn_contact);
        contact.setOnClickListener(v->navController.navigate(R.id.action_mainFragment_to_contactFragment));
    }

    /*
    top auto sliding banner here
     */
    private void setUpBannerView (View view) {
        SliderView sliderView = view.findViewById(R.id.MainTopImageSlider);

        sliderView.setSliderAdapter(bannerAdapter);

        sliderView.setIndicatorAnimation(IndicatorAnimationType.THIN_WORM);//set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
        sliderView.startAutoCycle();


    }

    //cards navigation menu here
    private void setUpCardsMenu (View view) {
        CardView eventsCard = view.findViewById(R.id.view_card_events);
        CardView storiesCard = view.findViewById(R.id.view_card_stories);
        CardView newsCard = view.findViewById(R.id.view_card_news);
        CardView qCard = view.findViewById(R.id.view_card_queries);
        CardView aceCard = view.findViewById(R.id.view_card_ace);
        CardView galleryCard = view.findViewById(R.id.view_card_gallery);

        eventsCard.setOnClickListener(v -> navController.navigate(R.id.action_mainFragment_to_eventsFragment));

        storiesCard.setOnClickListener(v1 -> navController.navigate(R.id.action_mainFragment_to_feedFragment));

        newsCard.setOnClickListener(v2 -> navController.navigate(R.id.action_mainFragment_to_feedFragment));

        galleryCard.setOnClickListener(v3 -> navController.navigate(R.id.action_mainFragment_to_galleryFragment));

        aceCard.setOnClickListener(v4 -> navController.navigate(R.id.action_mainFragment_to_aboutFragment));

        qCard.setOnClickListener(v5->navController.navigate(R.id.action_mainFragment_to_contactFragment));
    }

    /*
    events which are selected here are displayed in a recycler view here
     */
   //private void setupUserEventsView(View view) {
   //    List<ImageItems> slideItems = new ArrayList<>();

   //    RecyclerView userEvents = view.findViewById(R.id.recycler_view_user_events);
   //    userEvents.setHasFixedSize(true);
   //    userEvents.setLayoutManager(new LinearLayoutManager(getContext(),
   //            LinearLayoutManager.HORIZONTAL, false));

   //    userEvents.setAdapter(new MainUserEventsAdapter(slideItems));
   //}

    /*
    new events are displayed in a recycler view here
     */
    private void setUpNewEventsView(View view) {
        RecyclerView userEvents = view.findViewById(R.id.recycler_view_new_events);
        userEvents.setHasFixedSize(true);
        userEvents.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));

        userEvents.setAdapter(newAdapter);
    }

    //private void setUpOnEventsView(View view) {
//
    //    RecyclerView userEvents = view.findViewById(R.id.recycler_view_upcom_events);
    //    userEvents.setHasFixedSize(true);
    //    userEvents.setLayoutManager(new LinearLayoutManager(getContext(),
    //            LinearLayoutManager.HORIZONTAL, false));
//
    //    userEvents.setAdapter(onAdapter);
    //}
    /*
    new stories are put here in a recycler view
     */
    //private void setUpNewStoriesView(View view) {
//
    //    RecyclerView userEvents = view.findViewById(R.id.recycler_view_new_stories);
    //    userEvents.setHasFixedSize(true);
    //    userEvents.setLayoutManager(new LinearLayoutManager(getContext(),
    //            LinearLayoutManager.HORIZONTAL, false));
    //    userEvents.setAdapter(storiesAdapter);
    //}

    private void setUpSocials(View view) {
        ImageView fb = view.findViewById(R.id.main_btn_fb);
        ImageView insta = view.findViewById(R.id.main_btn_insta);
        ImageView linked = view.findViewById(R.id.main_btn_linkd);

        fb.setOnClickListener(v1-> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/ACE.IITP/"))));
        insta.setOnClickListener(v2-> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/ace_iitp/"))));
        linked.setOnClickListener(v3 -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://in.linkedin.com/company/ace-iit-patna"))));
    }

    @Override
    public void activeClick(int position) {
        navController.navigate(MainFragmentDirections
                .actionMainFragmentToEventInfoFragment(eventsDataItemsListActive.get(position)));
    }

    @Override
    public void nonactiveClick(int position) {
        navController.navigate(MainFragmentDirections
                .actionMainFragmentToEventInfoFragment(eventsDataItemsListNonActive.get(position)));
    }

    @Override
    public void OnItemClick(int position) {
        navController.navigate(R.id.action_mainFragment_to_feedFragment);
    }

    @Override
    public void onDestroyView() {
        eventsDataItemsListNonActive.clear();
        eventsDataItemsListActive.clear();
        super.onDestroyView();
    }

}
