package com.example.app_ban_sach.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.icu.text.DecimalFormat;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.app_ban_sach.Models.Sach;
import com.example.app_ban_sach.Models.TaiKhoan;
import com.example.app_ban_sach.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.squareup.picasso.Picasso;

public class ChinhSuaSachActivity extends AppCompatActivity {

    EditText edTen,edSoLuong,edGia;
    Spinner spTheLoai;
    Button btnXoa,btnCapNhat;
    ImageView imSach;
    Uri ImageUri;
    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    public int a = 0;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chinh_sua_sach);
        edTen = findViewById(R.id.edSuaTenSach);
        edGia = findViewById(R.id.edSuaGia);
        spTheLoai = findViewById(R.id.spSuaTheLoai);
        edSoLuong = findViewById(R.id.edSuaSoLuong);
        btnXoa = findViewById(R.id.btnXoaSach);
        btnCapNhat = findViewById(R.id.btnCapNhatSach);
        imSach = findViewById(R.id.imSua);


        Intent i = getIntent();

        String tenSach = i.getStringExtra("tenSach");
        String theLoai = i.getStringExtra("theLoai");
        double gia = i.getDoubleExtra("gia",0);
        String hinh = i.getStringExtra("hinhAnh");
        String maSach = i.getStringExtra("maSach");
        int sl = i.getIntExtra("soLuong",0);

        System.out.println(sl);

        String phanLoai[] = {"Văn Học", "Kinh Tế", "Thiếu Nhi", "Giáo Khoa", "Ngoại Ngữ"};
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, phanLoai);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spTheLoai.setAdapter(adapter);

        edTen.setText(tenSach);
        edGia.setText(String.valueOf(gia));
        edSoLuong.setText(String.valueOf(sl));
        Picasso.get().load(hinh).into(imSach);


        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                a = 0;
//                DatabaseReference ref = db.getReference("GioHang");
//                Query query = ref.orderByChild("maSach").equalTo(maSach);
//
//                ref.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        for(DataSnapshot danhsach : snapshot.getChildren())
//                        {
//                            snapshot.getChildren().forEach(dataSnapshot -> dataSnapshot.getRef().child("Sach").orderByChild("maSach").equalTo(maSach).addValueEventListener(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                    for(DataSnapshot sach : snapshot.getChildren())
//                                    {
//                                        Sach sach1 = sach.getValue(Sach.class);
//                                        if(sach1.getMaSach().equals(maSach))
//                                        {
//                                            Toast.makeText(ChinhSuaSachActivity.this, "Đã Tồn Tại", Toast.LENGTH_SHORT).show();
//                                        }else{
//                                            Toast.makeText(ChinhSuaSachActivity.this, "Không tồn tại", Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//
//                                }
//                                @Override
//                                public void onCancelled(@NonNull DatabaseError error) {
//
//                                    a = 0;
//
//                                }
//                            }));
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });


                db.getReference().child("Sach").child(maSach).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(ChinhSuaSachActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        imSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadImage();
            }
        });


        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final StorageReference ref = firebaseStorage.getReference().child("sach")
                        .child(System.currentTimeMillis()+"");
                ref.putFile(ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(ChinhSuaSachActivity.this, "upload hinh thanh cong", Toast.LENGTH_SHORT).show();
                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String ten = edTen.getText().toString().trim();
                                Double gia = Double.parseDouble(edGia.getText().toString().trim());
                                int sl = Integer.parseInt(edSoLuong.getText().toString().trim());
                                String theLoai = spTheLoai.getSelectedItem().toString().trim();

                                Sach sach = new Sach();
                                sach.setTenSach(ten);
                                sach.setGia(gia);
                                sach.setMaSach(maSach);
                                sach.setSoLuong(sl);
                                sach.setHinhAnh(uri.toString());
                                sach.setTheLoai(theLoai);

                                Toast.makeText(ChinhSuaSachActivity.this, "Cập Nhật Thành Công", Toast.LENGTH_SHORT).show();
                                db.getReference("Sach").child(maSach).setValue(sach).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(ChinhSuaSachActivity.this, "Cập Nhật Thành Công", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });

                    }
                });
            }
        });
    }

    private void UploadImage() {
        Dexter.withContext(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        i.setType("image/*");
                        i.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(i,101);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        Toast.makeText(ChinhSuaSachActivity.this, "không lấy ảnh được", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK) {
            ImageUri = data.getData();
            imSach.setImageURI(ImageUri);
        }
    }
}