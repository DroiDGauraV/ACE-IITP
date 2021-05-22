package com.ace.ace_iitp.Adapters_Interfaces;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.ace.ace_iitp.R;

public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private String[] imgUrls;

    public ViewPagerAdapter(Context context, String[] imgUrls) {
        this.context = context;
        this.imgUrls = imgUrls;
    }

    @Override
    public int getCount() {
        return imgUrls.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
        imageView.setAdjustViewBounds(true);
        Glide.with(context)
                .load(imgUrls[position])
                .fitCenter()
                .placeholder(R.mipmap.ic_ace_logo_foreground)
                .into(imageView);

        container.addView(imageView, 0);

        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
