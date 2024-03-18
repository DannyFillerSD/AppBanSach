package com.example.app_ban_sach.Customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.icu.text.DecimalFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_ban_sach.Adapter.SachAdapter;
import com.example.app_ban_sach.MainActivity;
import com.example.app_ban_sach.Models.Sach;
import com.example.app_ban_sach.Pattern.SingletonPattern.Singleton;
import com.example.app_ban_sach.R;
import com.example.app_ban_sach.UI.DangNhapActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ChiTietSachActivity extends AppCompatActivity implements SachAdapter.CallBack{
    TextView tvTenSach,tvTheLoai,tvGiaSach;
    ImageView imageHinh;
    RecyclerView rcSachTuongTu;
    private ArrayList<Sach> listSach;
    SachAdapter sachAdapter;
    Button btnThemGH,btnMua;
    Sach curSach;
    TextView tvMota;
    ImageView tvTroVe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_sach);
        //Singleton
//        Singleton.GetFirebaseStorage();

        tvTenSach = findViewById(R.id.tv_TenSach);
        tvGiaSach = findViewById(R.id.tv_Gia);
        tvTheLoai = findViewById(R.id.tv_TheLoai);
        imageHinh = findViewById(R.id.image_hinh);
        btnThemGH = findViewById(R.id.btn_Themgiohang);
        tvTroVe = findViewById(R.id.imbackchitiet);
        tvMota = findViewById(R.id.tvMota);

        rcSachTuongTu = findViewById(R.id.rc_tuongTu);
        listSach = new ArrayList<>();

        Intent i = getIntent();
        String tenSach = i.getStringExtra("tenSach");
        String theLoai = i.getStringExtra("theLoai");
        double gia = i.getDoubleExtra("gia",0);
        String hinh = i.getStringExtra("hinhAnh");
        String maSach = i.getStringExtra("maSach");
        String mota = i.getStringExtra("moTa");

        tvTenSach.setText(tenSach);
        tvTheLoai.setText(theLoai);
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        tvGiaSach.setText(decimalFormat.format( gia) + " VNĐ");
        Picasso.get().load(hinh).into(imageHinh);
        tvMota.setText(mota);
        curSach = new Sach(tenSach,theLoai,gia,hinh,maSach,mota);




        // RecycleView Sách tương tự
        LinearLayoutManager linearLayout = new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
        rcSachTuongTu.setLayoutManager(linearLayout);

        sachAdapter = new SachAdapter(ChiTietSachActivity.this,listSach,this);
        rcSachTuongTu.setAdapter(sachAdapter);
        Singleton.db.getReference("Sach").orderByChild("theLoai").equalTo(theLoai).addValueEventListener(new ValueEventListener() {
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
                Singleton.db.getReference("GioHang").child(DangNhapActivity.auth.getUid()).child("Sach").child(maSach).setValue(curSach);
                Toast.makeText(ChiTietSachActivity.this, "Thêm Giỏ Hàng Thành Công", Toast.LENGTH_SHORT).show();
            }
        });

        tvTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChiTietSachActivity.this,MainActivity.class);
                startActivity(i);
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

    @Override
    public void setFilteredList(ArrayList<Sach> filteredList) {

    }
}