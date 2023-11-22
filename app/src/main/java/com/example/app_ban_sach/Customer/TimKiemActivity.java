package com.example.app_ban_sach.Customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;

import com.example.app_ban_sach.Adapter.SachAdapter;
import com.example.app_ban_sach.MainActivity;
import com.example.app_ban_sach.Models.Sach;
import com.example.app_ban_sach.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class TimKiemActivity extends AppCompatActivity implements SachAdapter.CallBack {
    private SearchView searchView;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    ArrayList<Sach> listSach = new ArrayList<>();
    private RecyclerView rvSach;
    private SachAdapter sachAdapter;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tim_kiem);

        back = findViewById(R.id.imbacktimkiem);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TimKiemActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        db = FirebaseDatabase.getInstance();
        listSach = new ArrayList<>();

        //Adapter sách
        rvSach = findViewById(R.id.rcTimKiem);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(TimKiemActivity.this,2);
        rvSach.setLayoutManager(gridLayoutManager);

        sachAdapter = new SachAdapter(TimKiemActivity.this,listSach,this);
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

        searchView = findViewById(R.id.svTimKiem);

        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return false;
            }
        });

    }

    private void filterList(String text) {
        ArrayList<Sach> filterList = new ArrayList<>();
        for (Sach b : listSach){
            if(b.getTenSach().toLowerCase().contains(text.toLowerCase())){
                filterList.add(b);
            }
        }

        if(filterList.isEmpty()){

        }
        else {
            setFilteredList(filterList);
        }
    }

    @Override
    public void onClick(int position, Sach sach) {
        Intent i = new Intent(TimKiemActivity.this, ChiTietSachActivity.class);
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
        SachAdapter.sachList =  filteredList;
        sachAdapter.notifyDataSetChanged();
    }
}