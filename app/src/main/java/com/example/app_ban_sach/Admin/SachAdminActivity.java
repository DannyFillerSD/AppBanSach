package com.example.app_ban_sach.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.app_ban_sach.Adapter.SachAdminAdapter;
import com.example.app_ban_sach.Models.Sach;
import com.example.app_ban_sach.Pattern.SingletonPattern.Singleton;
import com.example.app_ban_sach.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SachAdminActivity extends AppCompatActivity implements SachAdminAdapter.CallBack {

//    FirebaseDatabase db;

    //sách
    private RecyclerView rvSach;
    private SachAdminAdapter sachAdminAdapter;
    private ArrayList<Sach> listSach ;
    ImageView imback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sach_admin);

        //Singleton
//        Singleton.GetFirebaseDatabase();

        imback = findViewById(R.id.imTroVeThemSachAdmin);
        imback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SachAdminActivity.this,TrangChuAdminActivity.class);
                startActivity(i);
            }
        });


        listSach = new ArrayList<>();

        //Adapter sách
        rvSach = findViewById(R.id.rcSachAdmin);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(SachAdminActivity.this,2);
        rvSach.setLayoutManager(gridLayoutManager);

        sachAdminAdapter = new SachAdminAdapter(SachAdminActivity.this,listSach,this);
        rvSach.setAdapter(sachAdminAdapter);
        Singleton.db.getReference("Sach").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot danhsach : snapshot.getChildren())
                {
                    Sach sach = danhsach.getValue(Sach.class);
                    listSach.add(sach);
                }
                sachAdminAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onClick(int position, Sach sach) {
        Intent i = new Intent(SachAdminActivity.this, ChinhSuaSachActivity.class);
        i.putExtra("tenSach",sach.getTenSach());
        i.putExtra("hinhAnh",sach.getHinhAnh());
        i.putExtra("theLoai",sach.getTheLoai());
        i.putExtra("gia",sach.getGia());
        i.putExtra("maSach",sach.getMaSach());
        i.putExtra("soLuong",sach.getSoLuong());
        i.putExtra("moTa",sach.getMoTa());
        startActivity(i);
    }
}