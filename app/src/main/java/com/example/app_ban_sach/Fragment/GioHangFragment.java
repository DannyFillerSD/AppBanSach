package com.example.app_ban_sach.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_ban_sach.Adapter.GioHangAdapter;
import com.example.app_ban_sach.Adapter.SachAdapter;
import com.example.app_ban_sach.Models.Sach;
import com.example.app_ban_sach.R;
import com.example.app_ban_sach.UI.DangNhapActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class GioHangFragment extends Fragment implements GioHangAdapter.CallBack{
    private ArrayList<Sach> listSach ;
    private RecyclerView rcGioHang;
    private GioHangAdapter gioHangAdapter;
    FirebaseDatabase db;
    TextView tvTongTien;
    private int tong = 0;
    private String idUSer = DangNhapActivity.auth.getUid();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_gio_hang, container, false);;
        //Ánh Xạ
        tvTongTien=v.findViewById(R.id.tvTongTien);


        getActivity().setTitle("Trang chủ");
        listSach = new ArrayList<>();
        db = FirebaseDatabase.getInstance();
        //Adapter sách
        rcGioHang = v.findViewById(R.id.rcGioHang);
        LinearLayoutManager linearLayout = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        rcGioHang.setLayoutManager(linearLayout);

        gioHangAdapter = new GioHangAdapter(getContext(),listSach,this);
        rcGioHang.setAdapter(gioHangAdapter);
        db.getReference("GioHang").child(idUSer).child("Sach").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listSach.clear();
                tong = 0;
                for(DataSnapshot danhsach : snapshot.getChildren())
                {
                    Sach sach = danhsach.getValue(Sach.class);
                    listSach.add(sach);
                    tong+=sach.getGia();
                    tvTongTien.setText(String.valueOf(tong) +" VND");
                }
                gioHangAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return v;
    }

    @Override
    public void onClick(int position, Sach sach) {

    }
}