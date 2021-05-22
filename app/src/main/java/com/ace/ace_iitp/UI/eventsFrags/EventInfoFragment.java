package com.ace.ace_iitp.UI.eventsFrags;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ace.ace_iitp.Adapters_Interfaces.ProblemRcvAdapter;
import com.ace.ace_iitp.Adapters_Interfaces.RecyclerViewClick;
import com.ace.ace_iitp.DataItems.EventsDataItems;
import com.ace.ace_iitp.DataItems.EventsProblems;
import com.ace.ace_iitp.R;
import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EventInfoFragment extends Fragment implements RecyclerViewClick {

    private NavController navController;
    private ImageView imageView;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private List<EventsProblems> problemsList;
    private final ProblemRcvAdapter adapter = new ProblemRcvAdapter(this);
    private EventsDataItems passedEvent;

    private static final String TAG = "EventInfoFragment";

    public EventInfoFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.events_fragment_eventinfo, container, false);

        imageView = view.findViewById(R.id.view_img_event);
        mCollapsingToolbarLayout = view.findViewById(R.id.collapsing_toolbar);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            passedEvent = EventInfoFragmentArgs.fromBundle(getArguments())
                    .getEventData();

            assert passedEvent != null;

            mCollapsingToolbarLayout.setTitle(passedEvent.getName());
            TextView tv_desc = view.findViewById(R.id.tv_event_desc);
            tv_desc.setText(passedEvent.getDesc().replaceAll("<br>", "\n"));

            Button paytbn = view.findViewById(R.id.view_btn_pay);
            if (passedEvent.getPay().equals("null") || passedEvent.getPay().trim().isEmpty()) {
                paytbn.setVisibility(View.GONE);
            }
            paytbn.setOnClickListener(v6 -> startActivity(
                    new Intent(Intent.ACTION_VIEW, Uri.parse(passedEvent.getPay()))));

            Button rulebook = view.findViewById(R.id.view_btn_rulebook);
            if (passedEvent.getRulebook().equals("null") || passedEvent.getRulebook().trim().isEmpty()) {
                rulebook.setVisibility(View.GONE);
            }
            rulebook.setOnClickListener(v9 -> startActivity(
                    new Intent(Intent.ACTION_VIEW, Uri.parse(passedEvent.getRulebook()))));

            Button extra = view.findViewById(R.id.view_btn_extra);
            if (passedEvent.getExtra().equals("null") || passedEvent.getExtra().trim().isEmpty()) {
                extra.setVisibility(View.GONE);
            } else {
                extra.setText(passedEvent.getExtra());
            }
            extra.setOnClickListener(v11 -> startActivity(
                    new Intent(Intent.ACTION_VIEW, Uri.parse((passedEvent.getExtraLink())))));

            ExtendedFloatingActionButton regBtn = view.findViewById(R.id.view_fab_reg);
            if (passedEvent.getReglink().equals("null") || passedEvent.getReglink().trim().isEmpty()) {
                regBtn.setVisibility(View.GONE);
            }
            regBtn.setOnClickListener(v7 -> startActivity(
                    new Intent(Intent.ACTION_VIEW, Uri.parse(passedEvent.getReglink()))));

            TextView problemCard = view.findViewById(R.id.view_prob_text);
            if (!passedEvent.isProb()) {
                problemCard.setVisibility(View.GONE);
            }

            Glide.with(requireContext())
                    .load(passedEvent.getImg())
                    .placeholder(R.mipmap.ic_ace_logo_foreground)
                    .into(imageView);
        }

        navController = Navigation.findNavController(view);
        setUpToolbar(view);

        mCollapsingToolbarLayout.setExpandedTitleColor(Color.parseColor("#ffffff"));
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.parseColor("#ffffff"));

        setuprcv(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        FirebaseFirestore.getInstance()
                .collection("ActiveEventsList")
                .document(passedEvent.getEvent_id())
                .collection("Problems").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        problemsList = new ArrayList<>
                                (Objects.requireNonNull(task.getResult()).toObjects(EventsProblems.class));
                        adapter.setEventsProblemsList(problemsList);
                        adapter.notifyDataSetChanged();
                    } else {
                        Log.e(TAG, "onActivityCreated: firebase failed");
                    }
                });
        super.onActivityCreated(savedInstanceState);
    }

    private void setUpToolbar(View view) {
        Toolbar toolbar = view.findViewById(R.id.view_eventsInfo_toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(v -> navController.navigateUp());
    }

    public void setuprcv(View view) {
        RecyclerView rcv = view.findViewById(R.id.problems_rcv);
        TextView probCard = view.findViewById(R.id.view_prob_text);

        if (passedEvent != null && !passedEvent.isActive()) {
            probCard.setVisibility(View.GONE);
            rcv.setVisibility(View.GONE);
        }

        rcv.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rcv.setAdapter(adapter);

    }

    @Override
    public void OnItemClick(int position) {
        navController.navigate((NavDirections) EventInfoFragmentDirections
                .actionEventInfoFragmentToEventProblemFragment(problemsList.get(position)));
    }
}
