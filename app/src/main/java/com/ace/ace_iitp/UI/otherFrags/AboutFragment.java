package com.ace.ace_iitp.UI.otherFrags;

import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.ace.ace_iitp.R;


public class AboutFragment extends Fragment {

    NavController navController;

    public AboutFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        navController = Navigation.findNavController(view);
        setupToolbar(view);

        ImageView logo = view.findViewById(R.id.view_img_about);
        logo.setImageResource(R.mipmap.ic_ace_logo_foreground);
        super.onViewCreated(view, savedInstanceState);

        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        //    TextView tv1 = view.findViewById(R.id.tv1);
        //    TextView tv2 = view.findViewById(R.id.tv2);
        //    tv1.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);
        //    tv2.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);
        //}
    }

    private void setupToolbar(View view) {
        Toolbar toolbar = view.findViewById(R.id.view_about_toolbar);
        toolbar.setTitle("About Us - ACE");
        toolbar.setNavigationOnClickListener(v -> navController.navigateUp());
    }
}