package com.ace.ace_iitp.UI.eventsFrags;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
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

import com.ace.ace_iitp.DataItems.EventsProblems;
import com.ace.ace_iitp.R;
import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class EventProblemFragment extends Fragment {

    NavController navController;
    EventsProblems eventsProblems;

    public EventProblemFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_event_problem, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ImageView imageView = view.findViewById(R.id.view_img_event_problem);
        CollapsingToolbarLayout mCollapsingToolbarLayout = view.findViewById(R.id.collapsing_toolbar_problem);

        navController = Navigation.findNavController(view);
        setUpToolbar(view);



        mCollapsingToolbarLayout.setExpandedTitleColor(Color.parseColor("#ffffff"));
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.parseColor("#ffffff"));


        if (getArguments() != null) {
            eventsProblems = EventProblemFragmentArgs.fromBundle(getArguments())
                    .getArgumentData();

            assert eventsProblems != null;
            mCollapsingToolbarLayout.setTitle(eventsProblems.getName());
            TextView tv_title = view.findViewById(R.id.tv_card_title);
            TextView tv_info = view.findViewById(R.id.tv_prob_desc);
            TextView tv_rules = view.findViewById(R.id.tv_prob_instruc);
            ExtendedFloatingActionButton pdfFab = view.findViewById(R.id.view_fab_pdf);

            tv_title.setText(eventsProblems.getName());

            tv_info.setText(eventsProblems.getInfo().replaceAll("<br>", "\n"));
            //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            //    tv_info.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);


            tv_rules.setText(eventsProblems.getRules().replaceAll("<br>", "\n"));

            if (eventsProblems.getLink().equals("null") || eventsProblems.getLink().trim().isEmpty()) {
                pdfFab.setVisibility(View.GONE);
            }
            pdfFab.setOnClickListener(v7 -> startActivity(
                    new Intent(Intent.ACTION_VIEW, Uri.parse(eventsProblems.getLink()))));

            Glide.with(requireContext())
                    .load(eventsProblems.getImg_prob())
                    .centerCrop()
                    .placeholder(R.mipmap.ic_ace_logo_foreground)
                    .into(imageView);
        }

        super.onViewCreated(view, savedInstanceState);
    }


    private void setUpToolbar (View view) {
        Toolbar toolbar = view.findViewById(R.id.view_eventsInfo_toolbar_problem);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(v -> navController.navigateUp());
    }
}