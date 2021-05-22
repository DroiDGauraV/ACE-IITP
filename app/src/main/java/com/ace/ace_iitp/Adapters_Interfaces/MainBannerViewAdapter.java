package com.ace.ace_iitp.Adapters_Interfaces;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ace.ace_iitp.DataItems.ImageItems;
import com.ace.ace_iitp.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

public class MainBannerViewAdapter extends SliderViewAdapter<MainBannerViewAdapter.SliderAdapterVH> {

    private List<ImageItems> slideItems;

    public MainBannerViewAdapter() {
    }

    public void setSlideItems(List<ImageItems> slideItems) {
        this.slideItems = slideItems;
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        return new SliderAdapterVH(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.main_scroll_banner_card, null));
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, int position) {
        ImageItems homeSlideItems = slideItems.get(position);
        String imgUrl = homeSlideItems.getImg();
        Glide.with(viewHolder.itemView.getContext())
                .load(imgUrl)
                .placeholder(R.mipmap.ic_ace_logo_foreground)
                .into(viewHolder.bannerView);
    }

    @Override
    public int getCount() {
        if (slideItems == null) {
            return 0;
        } else
            return slideItems.size();
    }

    static class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        public final ImageView bannerView;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            bannerView = itemView.findViewById(R.id.view_img_banner_scroll);
        }

    }
}
