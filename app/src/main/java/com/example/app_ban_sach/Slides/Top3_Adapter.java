package com.example.app_ban_sach.Slides;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.app_ban_sach.R;

import java.util.List;

public class Top3_Adapter extends PagerAdapter {
    private List<Top3> mListTop3;

    public Top3_Adapter(List<Top3> mListTop3){
        this.mListTop3 = mListTop3;
    }
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position){
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_top3,container, false);
        ImageView imgBanner = view.findViewById(R.id.img_Top3);
        Top3 top3 = mListTop3.get(position);
        imgBanner.setImageResource(top3.getResourceId());
        container.addView(view);
        return view;
    }
    @Override
    public int getCount() {
        if(mListTop3 != null){
            return  mListTop3.size();
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
