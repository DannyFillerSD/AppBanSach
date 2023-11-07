package com.example.app_ban_sach.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.app_ban_sach.Customer.SuaThongTinActivity;
import com.example.app_ban_sach.Models.Sach;
import com.example.app_ban_sach.Models.TaiKhoan;
import com.example.app_ban_sach.R;
import com.example.app_ban_sach.UI.DangNhapActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Console;

public class TaiKhoanFragment extends Fragment {
    TextView tvDangXuat,tvLienHe;
    FirebaseDatabase db;
    TextView tvSuaThongTin;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tai_khoan, container, false);
        getActivity().setTitle("Tài Khoản");


        tvDangXuat = v.findViewById(R.id.tvDangXuat);
        tvLienHe = v.findViewById(R.id.tvLienHe);
        tvSuaThongTin = v.findViewById(R.id.tvChinhSuaThongTin);

//        Sach sach = new Sach("Ngoại Ngữ","Giáo Dục",20000);
        db = FirebaseDatabase.getInstance();


        tvLienHe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                db.getReference("Sach").child(sach.getTenSach()).setValue(sach);
            }
        });


        tvDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), DangNhapActivity.class);
                startActivity(i);
            }
        });

        tvSuaThongTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), SuaThongTinActivity.class);
                startActivity(i);
            }
        });
        return v;
    }
}