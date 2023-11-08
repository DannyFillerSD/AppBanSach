package com.example.app_ban_sach.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_ban_sach.Models.Sach;
import com.example.app_ban_sach.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
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

import java.util.ArrayList;

public class ThemSachActivity extends AppCompatActivity {

    EditText edTen, edGia, edSoLuong;
    Spinner spTheLoai;
    Button btnThemSach;
    TextView tvTroVe;
    Uri ImageUri;
    ImageView imThemSach;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    ArrayList<Sach> listSach = new ArrayList<>();
    private String masach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_sach);
        edTen = findViewById(R.id.edTenSachThem);
        edGia = findViewById(R.id.edGiaSachThemSach);
        edSoLuong = findViewById(R.id.edSoLuongThem);
        spTheLoai = findViewById(R.id.spTheLoai);
        btnThemSach = findViewById(R.id.btnAdd);
        tvTroVe = findViewById(R.id.tvTroVeThemSach);
        imThemSach = findViewById(R.id.imThemSach);

        String phanLoai[] = {"Văn Học", "Kinh Tế", "Thiếu Nhi", "Giáo Khoa", "Ngoại Ngữ"};
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, phanLoai);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spTheLoai.setAdapter(adapter);

        tvTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imThemSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadImage();
            }
        });

        //Upload ảnh lên storage trên firebase
        btnThemSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final StorageReference ref = firebaseStorage.getReference().child("sach")
                        .child(System.currentTimeMillis()+"");
                ref.putFile(ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(ThemSachActivity.this, "upload hinh thanh cong", Toast.LENGTH_SHORT).show();
                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String ten = edTen.getText().toString().trim();
                                Double gia =Double.parseDouble(edGia.getText().toString().trim());
                                String sl = edSoLuong.getText().toString().trim();
                                Sach sach = new Sach();
                                sach.setTenSach(ten);
                                sach.setGia(gia);
                                sach.setSoLuong(Integer.parseInt(sl));
                                sach.setHinhAnh(uri.toString());
                                sach.setTheLoai(spTheLoai.getSelectedItem().toString());
                                Toast.makeText(ThemSachActivity.this,spTheLoai.getSelectedItem().toString() , Toast.LENGTH_SHORT).show();
                                TaoMaSach(sach.getTheLoai(),sach);
                            }
                        });

                    }
                });
            }
        });
    }

    public void TaoMaSach(String theLoai,Sach sach) {

        db.getReference("Sach").orderByChild("theLoai").equalTo(theLoai).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listSach.clear();
                for(DataSnapshot danhsach : snapshot.getChildren())
                {
                    Sach sach = danhsach.getValue(Sach.class);
                    listSach.add(sach);
                }

                int i = listSach.size();
                i = i+1;
//                Toast.makeText(ThemSachActivity.this, String.valueOf(i), Toast.LENGTH_SHORT).show();
                String a = String.valueOf(i) ;
                if(theLoai.equals("Văn Học")){
                    masach = "VH"+a;
                    Toast.makeText(ThemSachActivity.this,masach, Toast.LENGTH_SHORT).show();
                }if(theLoai.equals("Kinh Tế")){
                    masach = "KT"+a;
                }if(theLoai.equals("Thiếu Nhi")){
                    masach = "TN"+a;
                }if(theLoai.equals("Giáo Khoa")){
                    masach = "GK"+a;
                }if(theLoai.equals("Ngoại Ngữ")){
                    masach = "NN"+a;
                }

                sach.setMaSach(masach);
                db.getReference("Sach").child(masach).setValue(sach).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(ThemSachActivity.this, "Luu7 sach1 thanh cong", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void UploadImage() {
        Dexter.withContext(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        Intent i = new Intent();
                        i.setType("image/*");
                        i.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(i,101);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        Toast.makeText(ThemSachActivity.this, "không lấy ảnh được", Toast.LENGTH_SHORT).show();
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
            imThemSach.setImageURI(ImageUri);
        }
    }
}