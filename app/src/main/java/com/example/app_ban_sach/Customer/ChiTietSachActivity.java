package com.example.app_ban_sach.Customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_ban_sach.Adapter.SachAdapter;
import com.example.app_ban_sach.Models.Sach;
import com.example.app_ban_sach.Models.TaiKhoan;
import com.example.app_ban_sach.R;
import com.example.app_ban_sach.UI.DangNhapActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.platforminfo.DefaultUserAgentPublisher;
import com.squareup.picasso.Picasso;

import java.io.PipedInputStream;
import java.util.ArrayList;

public class ChiTietSachActivity extends AppCompatActivity implements SachAdapter.CallBack{
    TextView tvTenSach,tvTheLoai,tvGiaSach;
    ImageView imageHinh;
    RecyclerView rcSachTuongTu;
    private ArrayList<Sach> listSach;
    SachAdapter sachAdapter;
    FirebaseDatabase db;
    Button btnThemGH,btnMua;
    Sach curSach;
    TextView tvTroVe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_sach);
        db = FirebaseDatabase.getInstance();

        tvTenSach = findViewById(R.id.tv_TenSach);
        tvGiaSach = findViewById(R.id.tv_Gia);
        tvTheLoai = findViewById(R.id.tv_TheLoai);
        imageHinh = findViewById(R.id.image_hinh);
        btnThemGH = findViewById(R.id.btn_ThemGioHang);
        tvTroVe = findViewById(R.id.tvTrove);
        btnMua = findViewById(R.id.btn_MuaNgay);

        rcSachTuongTu = findViewById(R.id.rc_tuongTu);
        listSach = new ArrayList<>();

        Intent i = getIntent();
        String tenSach = i.getStringExtra("tenSach");
        String theLoai = i.getStringExtra("theLoai");
        double gia = i.getDoubleExtra("gia",1);
        String hinh = i.getStringExtra("hinhAnh");
        String maSach = i.getStringExtra("maSach");

        tvTenSach.setText(tenSach);
        tvTheLoai.setText(theLoai);
        tvGiaSach.setText(String.valueOf(gia));
        Picasso.get().load(hinh).into(imageHinh);
        curSach = new Sach(tenSach,theLoai,gia,hinh,maSach);

        btnMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ChiTietSachActivity.this, curSach.getMaSach(), Toast.LENGTH_SHORT).show();
            }
        });


        // RecycleView Sách tương tự
        LinearLayoutManager linearLayout = new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
        rcSachTuongTu.setLayoutManager(linearLayout);

        sachAdapter = new SachAdapter(ChiTietSachActivity.this,listSach,this);
        rcSachTuongTu.setAdapter(sachAdapter);
        db.getReference("Sach").orderByChild("theLoai").equalTo(theLoai).addValueEventListener(new ValueEventListener() {
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

        // Thêm giỏ hàng
        btnThemGH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.getReference("GioHang").child(DangNhapActivity.auth.getUid()).child("Sach").child(maSach).setValue(curSach);
            }
        });

        tvTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void onClick(int position, Sach sach) {
        Intent i = new Intent(ChiTietSachActivity.this, ChiTietSachActivity.class);
        i.putExtra("tenSach",sach.getTenSach());
        i.putExtra("hinhAnh",sach.getHinhAnh());
        i.putExtra("theLoai",sach.getTheLoai());
        i.putExtra("gia",sach.getGia());
        startActivity(i);
    }
}