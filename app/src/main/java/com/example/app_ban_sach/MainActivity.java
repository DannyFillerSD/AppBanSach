package com.example.app_ban_sach;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.app_ban_sach.Fragment.GioHangFragment;
import com.example.app_ban_sach.Fragment.TaiKhoanFragment;
import com.example.app_ban_sach.Fragment.TheLoaiFragment;
import com.example.app_ban_sach.Fragment.TrangChuFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LoadFragment(new TrangChuFragment());

        bottomNavigationView = findViewById(R.id.bottom_menu);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.mnTrangChu:
                        LoadFragment(new TrangChuFragment());
                        return true;

                    case R.id.mnTheLoai:
                        LoadFragment(new TheLoaiFragment());
                        return true;

                    case R.id.mnGioHang:
                        LoadFragment(new GioHangFragment());
                        return true;

                    case R.id.mnTaiKhoan:
                        LoadFragment(new TaiKhoanFragment());
                        return true;
                }
                return true;
            }
        });

    }

    void LoadFragment(Fragment fmNew){
        FragmentTransaction fmTranform = getSupportFragmentManager().beginTransaction();
        fmTranform.replace(R.id.fragment_container,fmNew);
        fmTranform.addToBackStack(null);
        fmTranform.commit();
    }
}