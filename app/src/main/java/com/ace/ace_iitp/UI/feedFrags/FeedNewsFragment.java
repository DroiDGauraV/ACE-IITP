package com.ace.ace_iitp.UI.feedFrags;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ace.ace_iitp.Adapters_Interfaces.FeedNewsAdapter;
import com.ace.ace_iitp.DataItems.FeedNewsItems;
import com.ace.ace_iitp.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FeedNewsFragment extends Fragment {

    private static final String TAG = "FeedNewsFragment";
    private FeedNewsAdapter adapter = new FeedNewsAdapter();

    public FeedNewsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.feed_news_tab, container, false);
        RecyclerView rcv = view.findViewById(R.id.feed_news_rcv);
        rcv.setHasFixedSize(true);
        rcv.setNestedScrollingEnabled(false);
        rcv.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        rcv.setAdapter(adapter);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        FirebaseFirestore.getInstance()
                .collection("FeedNewsList")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (task.getResult() == null) {
                            Log.e(TAG, "onActivityCreated: eroor null" );
                        }
                        List<FeedNewsItems> list = new ArrayList<>
                                (Objects.requireNonNull(task.getResult()).toObjects(FeedNewsItems.class));
                        adapter.setNewsList(list);
                        adapter.notifyDataSetChanged();
                    } else {
                        Log.e(TAG, "onActivityCreated: firebase failed");
                    }
                });

        super.onActivityCreated(savedInstanceState);
    }
}
