package com.example.app_ban_sach.UI;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app_ban_sach.Models.TaiKhoan;
import com.example.app_ban_sach.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DangKyActivity extends AppCompatActivity {
    EditText edEmail,edMatKhau,edNhapLaiMK,edTenTaiKhoan;
    Button btnDangKy;
    FirebaseDatabase db;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();

        edEmail  = findViewById(R.id.edEmailDK);
        edMatKhau = findViewById(R.id.edMatKhauDK);
        edNhapLaiMK = findViewById(R.id.edNhapLaiMK);
        edTenTaiKhoan = findViewById(R.id.edTenNguoiDung);
        btnDangKy = findViewById(R.id.btnDangKy);

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edEmail.getText().toString().trim();
                String matKhau = edMatKhau.getText().toString().trim();
                String nhapLaiMK = edNhapLaiMK.getText().toString().trim();
                String tenNguoiDung = edTenTaiKhoan.getText().toString().trim();

                // kiem tra co trong ko
                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(matKhau) || TextUtils.isEmpty(nhapLaiMK) || TextUtils.isEmpty(tenNguoiDung))
                {
                    Toast.makeText(DangKyActivity.this, "Vui Lòng Nhập Đầy Đủ Thông Tin", Toast.LENGTH_SHORT).show();
                }else{
                    if(matKhau.equals(nhapLaiMK))
                    {
                        //dang ky tai khoan firebase
                        auth.createUserWithEmailAndPassword(email,matKhau)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(task.isSuccessful())
                                        {
                                            TaiKhoan newTK = new TaiKhoan();
                                            newTK.setEmail(email);
                                            newTK.setMatKhau(matKhau);
                                            newTK.setTenNguoiDung(tenNguoiDung);
                                            newTK.setDiaChi("");
                                            newTK.setSoDienThoai("");

                                            db.getReference("TaiKhoan").child(auth.getUid()).setValue(newTK);

                                            Toast.makeText(DangKyActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                        else
                                        {
                                            Toast.makeText(DangKyActivity.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                    else
                    {
                        Toast.makeText(DangKyActivity.this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }
}