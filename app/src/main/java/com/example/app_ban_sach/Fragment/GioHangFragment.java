package com.example.app_ban_sach.Fragment;

import android.content.Intent;
import android.icu.text.DecimalFormat;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_ban_sach.Adapter.GioHangAdapter;
import com.example.app_ban_sach.Customer.ThanhToanActivity;
import com.example.app_ban_sach.Models.Sach;
import com.example.app_ban_sach.Pattern.SingletonPattern.Singleton;
import com.example.app_ban_sach.R;
import com.example.app_ban_sach.UI.DangNhapActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class GioHangFragment extends Fragment implements GioHangAdapter.CallBack{
    private ArrayList<Sach> listSach ;
    private RecyclerView rcGioHang;
    private GioHangAdapter gioHangAdapter;
    TextView tvTongTien,tvTrong;
    ImageView imTrong;
    Button btnMua;
    private int tong = 0;
    private String idUSer = DangNhapActivity.auth.getUid();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_gio_hang, container, false);;
        //Ánh Xạ
        tvTongTien=v.findViewById(R.id.tvTongTien);
        btnMua = v.findViewById(R.id.btnMua);
        tvTrong = v.findViewById(R.id.tv_trong);
        imTrong = v.findViewById(R.id.im_empty);



        getActivity().setTitle("Trang chủ");
        listSach = new ArrayList<>();
        //SingleTon
//        Singleton.GetFirebaseDatabase();
        //Adapter sách
        rcGioHang = v.findViewById(R.id.rcGioHang);
        LinearLayoutManager linearLayout = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        rcGioHang.setLayoutManager(linearLayout);

        gioHangAdapter = new GioHangAdapter(getContext(),listSach,this);
        rcGioHang.setAdapter(gioHangAdapter);
        Singleton.db.getReference("GioHang").child(idUSer).child("Sach").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listSach.clear();
                tong = 0;
                for(DataSnapshot danhsach : snapshot.getChildren())
                {
                    Sach sach = danhsach.getValue(Sach.class);
                    listSach.add(sach);
                    tong+=(sach.getGia() * sach.getSoLuong());
                    DecimalFormat decimalFormat = new DecimalFormat("#,###");
                    tvTongTien.setText(decimalFormat.format(tong) +" VND");
                }
                if(listSach.isEmpty()){
                    tvTongTien.setText("0 VND");
                    imTrong.setVisibility(v.VISIBLE);
                    tvTrong.setVisibility(v.VISIBLE);
                }else {
                    imTrong.setVisibility(v.INVISIBLE);
                    tvTrong.setVisibility(v.INVISIBLE);
                }
                gioHangAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        btnMua.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmmss");
//                SimpleDateFormat ngayThue = new SimpleDateFormat("ddMMyyyyHHmm");
//
//                String currentDateandTime = sdf.format(new Date());
//                String ngayThueSach = ngayThue.format(new Date());
//
//                HoaDon hd = new HoaDon();
//                hd.setMaHoaDon(ngayThueSach);
//                hd.setMaKhachHang(DangNhapActivity.auth.getUid());
//                hd.setTenKhachHang(DangNhapActivity.curUser.getTenNguoiDung());
//                hd.setThanhTien(tong);
//
//                db.getReference("HoaDon").child(ngayThueSach).setValue(hd);
//                db.getReference("HoaDon").child(ngayThueSach).child("chiTietGioHang").setValue(listSach);
//                listSach.clear();
//                tvTongTien.setText("0 VND");
//                gioHangAdapter.notifyDataSetChanged();
//
//                showAlertDialogButtonClicked(v);
//            }
//        });

        imTrong.setVisibility(v.INVISIBLE);
        tvTrong.setVisibility(v.INVISIBLE);

        if(listSach.isEmpty())
        {
            imTrong.setVisibility(v.VISIBLE);
            tvTrong.setVisibility(v.VISIBLE);
        }



        btnMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), ThanhToanActivity.class);
                startActivity(i);
            }
        });
        return v;
    }

//    public void showAlertDialogButtonClicked(View view) {
//        // Create an alert builder
//        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//
//        // set the custom layout
//        final View customLayout = getLayoutInflater().inflate(R.layout.fragment_thanh_toan_, null);
//        builder.setView(customLayout);
//
//        // add a button
//        builder.setPositiveButton("OK", (dialog, which) -> {
//            // send data from the AlertDialog to the Activity
////            EditText editText = customLayout.findViewById(R.id.editText);
////            sendDialogDataToActivity(editText.getText().toString());
//        });
//        // create and show the alert dialog
//        AlertDialog dialog = builder.create();
//        dialog.show();
//    }

    private void sendDialogDataToActivity(String data) {
        Toast.makeText(getContext(), data, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onClick(int position, Sach sach) {
    }
}