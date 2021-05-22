package com.ace.ace_iitp.UI.otherFrags;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.ace.ace_iitp.Adapters_Interfaces.GalleryAdapter;
import com.ace.ace_iitp.Adapters_Interfaces.RecyclerViewClick;
import com.ace.ace_iitp.DataItems.ImageItems;
import com.ace.ace_iitp.R;
import com.ace.ace_iitp.ViewModel.SharedViewModel;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment implements RecyclerViewClick {

    NavController navController;
    SharedViewModel sharedViewModel;
    List<ImageItems> galleryItems;
    GalleryAdapter galleryAdapter;
    String[] list;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        //view model here
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        sharedViewModel.getGalleryLiveData().observe(getViewLifecycleOwner(), strings -> {
            galleryItems = new ArrayList<>(strings);
            list = new String[galleryItems.size()];
            for(int i=0; i<galleryItems.size(); i++) {
                ImageItems items = galleryItems.get(i);
                list[i] = items.getImg();
            }
            galleryAdapter.setHomeSlideItems(galleryItems);
            galleryAdapter.notifyDataSetChanged();
        });

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        galleryAdapter = new GalleryAdapter(this, getContext());


        setUpToolbar (view);

        setUpRcv (view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        navController = Navigation.findNavController(view);
        super.onViewCreated(view, savedInstanceState);
    }

    private void setUpToolbar (View view) {
        MaterialToolbar toolbar = view.findViewById(R.id.view_gallery_toolbar);
        toolbar.setTitle("Gallery");
        toolbar.setNavigationOnClickListener(v -> navController.navigateUp());
    }

    private void setUpRcv (View view) {

        RecyclerView rcv = view.findViewById(R.id.gallery_rcv);
        rcv.setHasFixedSize(true);
        rcv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        rcv.setAdapter(galleryAdapter);



    }

    @Override
    public void OnItemClick(int position) {
        navController.navigate(GalleryFragmentDirections
                .actionGalleryFragmentToGalleryFullscreenFragment(list,position));
    }

    @Override
    public void onDetach() {
        galleryItems.clear();
        super.onDetach();
    }
}