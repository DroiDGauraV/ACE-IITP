package com.ace.ace_iitp.UI.feedFrags;

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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.ace.ace_iitp.Adapters_Interfaces.NavigationIconClickListener;
import com.ace.ace_iitp.R;
//import com.example.ace_iitp.UI.FeedFragmentArgs;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class FeedFragment extends Fragment {

    private NavController navController;

    private String[] titles = new String[]{"Stories", "News"};

    public FeedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //navController here
        navController = Navigation.findNavController(view);
        //view pager here
        ViewPager2 viewPager = view.findViewById(R.id.feed_viewpager);
        //tab layout here
        TabLayout tabLayout = view.findViewById(R.id.feed_tab_layout);

        viewPager.setAdapter(new ViewPagerFragmentAdapter(this));

        setUpToolbar(view);
        setUpBackdrop(view);
        /*
         attaching tab mediator
         used to attach the viewpager with tabs layout
         */

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(titles[position])).attach();

    }

    private void setUpToolbar(View view) {
        Toolbar toolbar = view.findViewById(R.id.view_feed_toolbar);
        toolbar.setTitle("FEED");

        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.search_item) {
                navController.navigate(R.id.action_feedFragment_to_feedSearchFragment);
            }
            return true;
        });

        toolbar.setNavigationOnClickListener(new NavigationIconClickListener(getContext(),
                view.findViewById(R.id.view_feed_scroll), new AccelerateDecelerateInterpolator()));
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
        contact.setOnClickListener(v->navController.navigate(R.id.action_feedFragment_to_contactFragment));
        gallery.setOnClickListener(v -> navController.navigate(R.id.action_feedFragment_to_galleryFragment));
        ace.setOnClickListener(v->navController.navigate(R.id.action_feedFragment_to_aboutFragment));
    }

    private class ViewPagerFragmentAdapter extends FragmentStateAdapter {

        public ViewPagerFragmentAdapter(Fragment fragment) {
            super(fragment);
        }


        @NonNull
        @Override
        public Fragment createFragment(int position) {

            switch (position) {
                case 0:
                    return new FeedStoryFragment();
                case 1:
                    return new FeedNewsFragment();
            }
            return new FeedStoryFragment();
        }

        @Override
        public int getItemCount() {
            return titles.length;
        }
    }
}