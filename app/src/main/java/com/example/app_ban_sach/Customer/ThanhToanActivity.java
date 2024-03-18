package com.example.app_ban_sach.Customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.icu.text.DecimalFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app_ban_sach.Adapter.GioHangAdapter;
import com.example.app_ban_sach.Adapter.ThanhToanAdapter;
import com.example.app_ban_sach.MainActivity;
import com.example.app_ban_sach.Models.HoaDon;
import com.example.app_ban_sach.Models.Sach;
import com.example.app_ban_sach.Pattern.SingletonPattern.Singleton;
import com.example.app_ban_sach.R;
import com.example.app_ban_sach.UI.DangNhapActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ThanhToanActivity extends AppCompatActivity implements ThanhToanAdapter.CallBack {
    ImageView back;
    private ArrayList<Sach> list ;
    private RecyclerView rcGioHang;
    private ThanhToanAdapter thanhToanAdapter;
    private GioHangAdapter gioHangAdapter;
    private int tong = 0;
    TextView tvTongTien,tvsl;
    Button btnMua;
    private String idUSer = DangNhapActivity.auth.getUid();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);

        //Singleton
//        Singleton.GetFirebaseStorage();

        back = findViewById(R.id.tvthanhback);
        tvTongTien = findViewById(R.id.tv_TongTien);
        tvsl = findViewById(R.id.tvSoLuongMua);
        btnMua = findViewById(R.id.btn_Thanhtoan);

        list = new ArrayList<>();
        //Adapter sách
        rcGioHang = findViewById(R.id.spThanhToan);
        LinearLayoutManager linearLayout = new LinearLayoutManager(ThanhToanActivity.this, RecyclerView.VERTICAL,false);
        rcGioHang.setLayoutManager(linearLayout);
        thanhToanAdapter = new ThanhToanAdapter(ThanhToanActivity.this,list,this);
        rcGioHang.setAdapter(thanhToanAdapter);
        Singleton.db.getReference("GioHang").child(idUSer).child("Sach").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                tong = 0;
                for(DataSnapshot danhsach : snapshot.getChildren())
                {
                    Sach sach = danhsach.getValue(Sach.class);
                    list.add(sach);
                    tong+=(sach.getGia() * sach.getSoLuong());
                    DecimalFormat decimalFormat = new DecimalFormat("#,###");
                    tvTongTien.setText(decimalFormat.format(tong) +" VND");
                    System.out.println(sach.getTenSach());
                }
                int soLuong = list.size();
                tvsl.setText(String.valueOf(soLuong));
                thanhToanAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ThanhToanActivity.this, MainActivity.class);
                startActivity(i);
            }
        });



        btnMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmmss");
                SimpleDateFormat ngayThue = new SimpleDateFormat("ddMMyyyyHHmm");

                String currentDateandTime = sdf.format(new Date());
                String ngayThueSach = ngayThue.format(new Date());

                HoaDon hd = new HoaDon();
                hd.setMaHoaDon(ngayThueSach);
                hd.setMaKhachHang(DangNhapActivity.auth.getUid());
                hd.setTenKhachHang(DangNhapActivity.curUser.getTenNguoiDung());
                hd.setThanhTien(tong);

                Singleton.db.getReference("HoaDon").child(ngayThueSach).setValue(hd);
                Singleton.db.getReference("HoaDon").child(ngayThueSach).child("chiTietGioHang").setValue(list);
                Singleton.db.getReference("GioHang").child(DangNhapActivity.auth.getUid()).child("Sach").removeValue();

                showAlertDialogButtonClicked(v);
                list.clear();
                thanhToanAdapter.notifyDataSetChanged();

            }
        });


    }

    @Override
    public void onClick(int position, Sach sach) {

    }

    public void showAlertDialogButtonClicked(View view) {
        // Create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(ThanhToanActivity.this);

        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.thanh_toan_tc, null);
        builder.setView(customLayout);

        // add a button
        builder.setPositiveButton("OK", (dialog, which) -> {
            Intent i = new Intent(ThanhToanActivity.this,MainActivity.class);
            startActivity(i);
            // send data from the AlertDialog to the Activity
//            EditText editText = customLayout.findViewById(R.id.editText);
//            sendDialogDataToActivity(editText.getText().toString());
        });
        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}