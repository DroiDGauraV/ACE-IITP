package com.ace.ace_iitp.UI.otherFrags;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ace.ace_iitp.Adapters_Interfaces.ViewPagerAdapter;
import com.ace.ace_iitp.R;

public class GalleryFullscreenFragment extends Fragment {

    NavController navController;
    String[] urlList;
    public GalleryFullscreenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gallery_fullscreen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        navController = Navigation.findNavController(view);
        setupToolbar(view);
        setupViewpager(view);
        super.onViewCreated(view, savedInstanceState);
    }

    private void setupToolbar(View view) {
        Toolbar toolbar = view.findViewById(R.id.view_gallery_fullscreen_toolbar);
        toolbar.setTitle("Gallery");
        toolbar.setNavigationOnClickListener(v -> navController.navigateUp());
    }

    private void setupViewpager (View view) {
        if (getArguments() != null) {

            GalleryFullscreenFragmentArgs args = GalleryFullscreenFragmentArgs.fromBundle(getArguments());
            int position  = args.getPos();
            urlList = args.getGalleryArray();

            ViewPager viewPager = view.findViewById(R.id.view_gallery_viewpager);
            ViewPagerAdapter adapter = new ViewPagerAdapter(getContext(), urlList);
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(position);
        }

    }
}