package com.example.app_ban_sach.Customer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app_ban_sach.MainActivity;
import com.example.app_ban_sach.Models.TaiKhoan;
import com.example.app_ban_sach.Pattern.SingletonPattern.Singleton;
import com.example.app_ban_sach.R;
import com.example.app_ban_sach.UI.DangNhapActivity;

public class SuaThongTinActivity extends AppCompatActivity {

    EditText edTenTaiKhoan,edSoDienThoai,edDiaChi;
    ImageView tvTroVeSua;
    Button btnCapNhat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_thong_tin);
        //Singleton
//        Singleton.GetFirebaseDatabase();

        edTenTaiKhoan = findViewById(R.id.edTen);
        edSoDienThoai = findViewById(R.id.edSDT);
        edDiaChi = findViewById(R.id.edDiaChi);
//        tvTroVeSua = findViewById(R.id.tvTroVeSua);
        btnCapNhat = findViewById(R.id.btnCapNhat);

        TaiKhoan tk = DangNhapActivity.curUser;

        tvTroVeSua =findViewById(R.id.img_TroVe);
        tvTroVeSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        edDiaChi.setText(tk.getDiaChi());
        edTenTaiKhoan.setText(tk.getTenNguoiDung());
        edSoDienThoai.setText(tk.getSoDienThoai());

        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten = edTenTaiKhoan.getText().toString().trim();
                String diaChi = edDiaChi.getText().toString().trim();
                String soDT = edSoDienThoai.getText().toString().trim();

                tk.setTenNguoiDung(ten);
                tk.setDiaChi(diaChi);
                tk.setSoDienThoai(soDT);
                Singleton.db.getReference("TaiKhoan").child(DangNhapActivity.auth.getUid()).setValue(tk);
                Toast.makeText(SuaThongTinActivity.this, "Cập nhật thành cồng", Toast.LENGTH_SHORT).show();
                DangNhapActivity.curUser = tk;
                Intent i = new Intent(SuaThongTinActivity.this, MainActivity.class);
                startActivity(i);

            }
        });



    }
}