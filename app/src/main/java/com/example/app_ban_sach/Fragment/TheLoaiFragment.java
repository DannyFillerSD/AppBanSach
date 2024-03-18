package com.example.app_ban_sach.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.app_ban_sach.Adapter.SachAdapter;
import com.example.app_ban_sach.Customer.ChiTietSachActivity;
import com.example.app_ban_sach.Models.Sach;
import com.example.app_ban_sach.Pattern.SingletonPattern.Singleton;
import com.example.app_ban_sach.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TheLoaiFragment extends Fragment implements SachAdapter.CallBack {

    private SachAdapter sachAdapter;
    private ArrayList<Sach> listSach;
    private RecyclerView rcTheLoai;
    private String theLoai = "Văn Học";
    TabLayout tabLayout;
    ViewPager viewPager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_the_loai, container, false);
        getActivity().setTitle("Danh Mục");

        //Singleton
        Singleton.db = FirebaseDatabase.getInstance();
        rcTheLoai = v.findViewById(R.id.rcTheLoai);
        listSach = new ArrayList<>();

        //Adapter sách
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        rcTheLoai.setLayoutManager(gridLayoutManager);

        sachAdapter = new SachAdapter(getContext(),listSach,this);
        rcTheLoai.setAdapter(sachAdapter);

        Singleton.db.getReference("Sach").orderByChild("theLoai").equalTo("Văn Học").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listSach.clear();
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


        //tab item
        tabLayout = v.findViewById(R.id.tabLayout);
        TabLayout.Tab tab = tabLayout.getTabAt(0); // Count Starts From 0
        tab.select();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        listSach.clear();
                        theLoai = "Văn Học";
                        getListSach(theLoai);
                        sachAdapter.notifyDataSetChanged();
                        break;
                    case 1:
                        listSach.clear();
                        theLoai = "Kinh Tế";
                        getListSach(theLoai);
                        sachAdapter.notifyDataSetChanged();
                        break;
                    case 2:
                        listSach.clear();
                        theLoai = "Thiếu Nhi";
                        getListSach(theLoai);
                        sachAdapter.notifyDataSetChanged();
                        break;
                    case 3:
                        listSach.clear();
                        theLoai = "Giáo Khoa";
                        getListSach(theLoai);
                        sachAdapter.notifyDataSetChanged();
                        break;
                    case 4:
                        listSach.clear();
                        theLoai = "Ngoại Ngữ";
                        getListSach(theLoai);
                        sachAdapter.notifyDataSetChanged();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                listSach.clear();
                getListSach(theLoai);
                sachAdapter.notifyDataSetChanged();
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                listSach.clear();
                getListSach(theLoai);
                sachAdapter.notifyDataSetChanged();
            }
        });

        return v;
    }

    public void getListSach(String getTheLoai){
        Singleton.db.getReference("Sach").orderByChild("theLoai").equalTo(getTheLoai).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listSach.clear();
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
    }

    @Override
    public void onClick(int position, Sach sach) {
        Intent i = new Intent(getContext(), ChiTietSachActivity.class);
        i.putExtra("tenSach",sach.getTenSach());
        i.putExtra("hinhAnh",sach.getHinhAnh());
        i.putExtra("theLoai",sach.getTheLoai());
        i.putExtra("gia",sach.getGia());
        i.putExtra("maSach",sach.getMaSach());

        startActivity(i);
    }

    @Override
    public void setFilteredList(ArrayList<Sach> filteredList) {

    }
}