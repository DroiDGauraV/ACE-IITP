package com.ace.ace_iitp.UI.otherFrags;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ace.ace_iitp.R;
import com.google.android.material.appbar.MaterialToolbar;

public class ContactFragment extends Fragment {

    NavController navController;

    public ContactFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        navController = Navigation.findNavController(view);
        setUpToolbar(view);
        super.onViewCreated(view, savedInstanceState);
    }

    private void setUpToolbar (View view) {
        MaterialToolbar toolbar = view.findViewById(R.id.view_contact_toolbar);
        toolbar.setTitle("Contact Us");
        toolbar.setNavigationOnClickListener(v -> navController.navigateUp());
    }

}