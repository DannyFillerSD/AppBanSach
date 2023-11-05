package com.example.app_ban_sach.Slides;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.app_ban_sach.R;

import java.util.List;

public class Banner_Adapter extends PagerAdapter{
    private List<Banner> mListBanner;
    public Banner_Adapter(List<Banner> mListBanner){
        this.mListBanner = mListBanner;
    }
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position){
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_banner,container, false);
        ImageView imgBanner = view.findViewById(R.id.img_photo);
        Banner banner = mListBanner.get(position);
        imgBanner.setImageResource(banner.getResourceId());
        container.addView(view);
        return view;
    }
    @Override
    public int getCount() {
        if(mListBanner != null){
            return  mListBanner.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object){
        container.removeView((View) object);
    }
}
