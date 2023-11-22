package com.example.app_ban_sach.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;

import com.example.app_ban_sach.Adapter.SachAdapter;
import com.example.app_ban_sach.Customer.ChiTietSachActivity;
import com.example.app_ban_sach.Customer.TimKiemActivity;
import com.example.app_ban_sach.MainActivity;
import com.example.app_ban_sach.Models.Sach;
import com.example.app_ban_sach.R;
import com.example.app_ban_sach.Slides.Banner;
import com.example.app_ban_sach.Slides.Banner_Adapter;
import com.example.app_ban_sach.Slides.Top3;
import com.example.app_ban_sach.Slides.Top3_Adapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class TrangChuFragment extends Fragment implements  SachAdapter.CallBack  {
    private ViewPager mViewPager, mViewPagerTop3;
    private CircleIndicator mCircleIndicator, mCircleIndicatorTop3;
    private List<Banner> mListBanner;
    private List<Top3> mListTop3;
    private ArrayList<Sach> listSach ;
    private Handler mHandler = new Handler();

    FirebaseDatabase db;

    ImageView imSearch;

    //sách
    private RecyclerView rvSach;
    private SachAdapter sachAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_trang_chu, container, false);

        getActivity().setTitle("Trang chủ");
        db = FirebaseDatabase.getInstance();

        imSearch = v.findViewById(R.id.img_Search);


        listSach = new ArrayList<>();

        //Adapter sách
        rvSach = v.findViewById(R.id.rvSachMoi);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        rvSach.setLayoutManager(gridLayoutManager);

        sachAdapter = new SachAdapter(getContext(),listSach,this);
        rvSach.setAdapter(sachAdapter);
        db.getReference("Sach").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot danhsach : snapshot.getChildren())
                {
                    Sach sach = danhsach.getValue(Sach.class);
                    listSach.add(sach);
                }
                sachAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        imSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), TimKiemActivity.class);
                startActivity(i);
            }
        });


        //------------------------------------------------------------------------------------------

        mCircleIndicator = v.findViewById(R.id.circle_indicator);
        mViewPager = v.findViewById(R.id.view_pager_Banner);
        mViewPagerTop3 = v.findViewById(R.id.view_pager_top3);
        mCircleIndicatorTop3 = v.findViewById(R.id.top3_indicator);
        //setting viewpager2
        mViewPagerTop3.setOffscreenPageLimit(3);
        mViewPagerTop3.setClipToPadding(false);
        mViewPagerTop3.setClipChildren(false);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(20));
        //banner
        mListBanner = getListBanner();
        Banner_Adapter adapter = new Banner_Adapter(mListBanner);
        mViewPager.setAdapter(adapter);
        mCircleIndicator.setViewPager(mViewPager);
        //Top3 sản phẩm bán chạy
        mListTop3 = getListTop3();
        Top3_Adapter top3_adapter = new Top3_Adapter(mListTop3);

        mViewPagerTop3.setAdapter(top3_adapter);
        mCircleIndicatorTop3.setViewPager(mViewPagerTop3);

        mHandler.postDelayed(mRunnable, 3500);
        mViewPagerTop3.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                mHandler.removeCallbacks(mRunnable2);
                mHandler.postDelayed(mRunnable2, 3500);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mHandler.postDelayed(mRunnable, 3000);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mHandler.removeCallbacks(mRunnable);
                mHandler.postDelayed(mRunnable, 3000);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return v;
    }

    private List<Banner> getListBanner() {
        List<Banner> list = new ArrayList<>();
        list.add(new Banner(R.drawable.banner1));
        list.add(new Banner(R.drawable.banner2));
        list.add(new Banner(R.drawable.banner3));
        return list;
    }

    private List<Top3> getListTop3() {
        List<Top3> list = new ArrayList<>();
        list.add(new Top3(R.drawable.top3_1));
        list.add(new Top3(R.drawable.top3_2));
        list.add(new Top3(R.drawable.top3_3));
        return list;
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (mViewPager.getCurrentItem() == mListBanner.size() - 1){
                mViewPager.setCurrentItem(0);
            }
            else {
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
            }
        }
    };

    private Runnable mRunnable2 = new Runnable() {
        @Override
        public void run() {
            if (mViewPagerTop3.getCurrentItem() == mListTop3.size() - 1){
                mViewPagerTop3.setCurrentItem(0);
            }
            else {
                mViewPagerTop3.setCurrentItem(mViewPagerTop3.getCurrentItem() + 1);
            }
        }
    };

    @Override
    public void onClick(int position, Sach sach) {
        Intent i = new Intent(getContext(), ChiTietSachActivity.class);
        i.putExtra("tenSach",sach.getTenSach());
        i.putExtra("hinhAnh",sach.getHinhAnh());
        i.putExtra("theLoai",sach.getTheLoai());
        i.putExtra("gia",sach.getGia());
        i.putExtra("maSach",sach.getMaSach());
        i.putExtra("moTa",sach.getMoTa());
        startActivity(i);

    }

    @Override
    public void setFilteredList(ArrayList<Sach> filteredList) {

    }
}