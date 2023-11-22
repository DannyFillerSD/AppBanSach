package com.example.app_ban_sach.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app_ban_sach.R;
import com.example.app_ban_sach.UI.DangNhapActivity;

public class TrangChuAdminActivity extends AppCompatActivity {

    TextView tvThemSach,tvSach,tvTroVe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu_admin);

        tvThemSach = findViewById(R.id.tvThemSach);
        tvTroVe = findViewById(R.id.tvTroVeThemSachAdmin);
        tvSach = findViewById(R.id.tvSach);


        tvTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TrangChuAdminActivity.this, DangNhapActivity.class);
                startActivity(i);
            }
        });

        tvThemSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TrangChuAdminActivity.this,ThemSachActivity.class);
                startActivity(i);
            }
        });

        tvSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TrangChuAdminActivity.this,SachAdminActivity.class);
                startActivity(i);
            }
        });




    }
}