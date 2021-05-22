package com.ace.ace_iitp.UI.entryPoint;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.ace.ace_iitp.R;
import com.ace.ace_iitp.ViewModel.SharedViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    NavController navController;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w(TAG, "getInstanceId failed", task.getException());
                        return;
                    }

                    // Get new Instance ID token
                    String token = Objects.requireNonNull(task.getResult()).getToken();

                    // Log and toast
                    String msg = getString(R.string.msg_token_fmt, token);
                    Log.d(TAG, msg);
                });

        //view model here
        SharedViewModel sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);

        sharedViewModel.callEventsData();//move this in main acts on create function
        sharedViewModel.callStoriesData();
        sharedViewModel.callBannerData();
        sharedViewModel.callGalleryData();

        //bottom navigation view hosted here
        BottomNavigationView bottomNavigationView = findViewById(R.id.view_bottom_nav);

        //replacement is handled by navigation component
        navController = Navigation.findNavController(this, R.id.fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);


        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if(destination.getId() == R.id.mainFragment) {
                bottomNavigationView.setVisibility(View.VISIBLE);
            } else if (destination.getId() == R.id.eventsFragment){
                bottomNavigationView.setVisibility(View.VISIBLE);
            } else if (destination.getId() == R.id.feedFragment) {
                bottomNavigationView.setVisibility(View.VISIBLE);
            } else {
                bottomNavigationView.setVisibility(View.GONE);
            }
        });

        final int screenWidth = getScreenDimensions(this).x;
        final int waveImgWidth = Objects.requireNonNull(
                ResourcesCompat.getDrawable(getResources(), R.drawable.bg3, null)).getIntrinsicWidth();
        int animatedViewWidth = 0;
        while (animatedViewWidth < screenWidth) {
            animatedViewWidth += waveImgWidth;
        }
        animatedViewWidth += waveImgWidth;


        View animatedView = findViewById(R.id.animated_view);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) animatedView.getLayoutParams();
        layoutParams.width = animatedViewWidth;
        animatedView.setLayoutParams(layoutParams);


        Animation waveAnimation = new TranslateAnimation(0, -waveImgWidth, 0, 0);
        waveAnimation.setInterpolator(new LinearInterpolator());
        waveAnimation.setRepeatCount(Animation.INFINITE);
        waveAnimation.setDuration(25000);

        animatedView.startAnimation(waveAnimation);
    }

    public static Point getScreenDimensions(Context context) {
        int width = context.getResources().getDisplayMetrics().widthPixels;
        int height = context.getResources().getDisplayMetrics().heightPixels;
        return new Point(width, height);
    }

}
