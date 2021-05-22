package com.ace.ace_iitp.Adapters_Interfaces;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.ace.ace_iitp.R;

/**
 * {@link android.view.View.OnClickListener} used to translate the product grid sheet downward on
 * the Y-axis when the navigation icon in the toolbar is pressed.
 */
public class NavigationIconClickListener implements View.OnClickListener {

    private final AnimatorSet animatorSet = new AnimatorSet();
    private Context context;
    private View sheet;
    private View backdrop;
    private Interpolator interpolator;
    private boolean backdropShown = false;


    public NavigationIconClickListener(
            Context context, View sheet, @Nullable Interpolator interpolator) {
        this.context = context;
        this.sheet = sheet;
        this.interpolator = interpolator;

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
    }

    @Override
    public void onClick(View view) {
        backdropShown = !backdropShown;

        // Cancel the existing animations
        animatorSet.removeAllListeners();
        animatorSet.end();
        animatorSet.cancel();

        updateIcon(view);

        final int translateY = context.getResources().getDimensionPixelSize(R.dimen.shr_product_grid_reveal_height);

        ObjectAnimator animator = ObjectAnimator.ofFloat(sheet, "translationY", backdropShown ? translateY : 0);
        animator.setDuration(500);
        if (interpolator != null) {
            animator.setInterpolator(interpolator);
        }
        animatorSet.play(animator);
        animator.start();
    }

    private void updateIcon(View view) {
        if (!(view instanceof ImageView)) {
            throw new IllegalArgumentException("updateIcon() must be called on an ImageView");
        }
        if (backdropShown) {
            int closeIcon = R.drawable.menu_close_24;
            ((ImageView) view).setImageResource(closeIcon);
        } else {
            int openIcon = R.drawable.ic_baseline_menu_24;
            ((ImageView) view).setImageResource(openIcon);
        }

    }
}
