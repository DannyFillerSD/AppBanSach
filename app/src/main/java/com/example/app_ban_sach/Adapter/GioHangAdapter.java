package com.example.app_ban_sach.Adapter;

import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_ban_sach.Fragment.GioHangFragment;
import com.example.app_ban_sach.MainActivity;
import com.example.app_ban_sach.Models.Sach;
import com.example.app_ban_sach.R;
import com.example.app_ban_sach.UI.DangNhapActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.MyViewHolder> {
    private Context context;
    public ArrayList<Sach> gioHangList;
    CallBack XoaCallBack;
    public GioHangAdapter(Context context, ArrayList<Sach> gioHangList, CallBack xoaCallBack) {
        this.context = context;
        this.gioHangList = gioHangList;
        this.XoaCallBack = xoaCallBack;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_giohang,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Sach sach =gioHangList.get(position);
        holder.tvTenSach.setText(gioHangList.get(position).getTenSach());

        holder.tvGia.setText(String.format("%.0f", gioHangList.get(position).getGia())  + " VNĐ");
        Picasso.get().load(gioHangList.get(position).getHinhAnh()).into(holder.imSachGH);
        holder.btnXoa.setOnClickListener(view -> XoaCallBack.onClick(position,sach));

        int sl =sach.getSoLuong();
        holder.edSoLuong.setText(String.valueOf(sl));
        System.out.println(sl);

        holder.btnCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cong = sach.getSoLuong() + 1;
                sach.setSoLuong(cong);
                FirebaseDatabase db = FirebaseDatabase.getInstance();
                db.getReference("GioHang").child(DangNhapActivity.auth.getUid()).child("Sach").child(sach.getMaSach()).setValue(sach);
            }
        });

        holder.btnTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tru = sach.getSoLuong() - 1;
                if (tru < 1 )
                {
                    tru = 1;
                }
                sach.setSoLuong(tru);
                FirebaseDatabase db = FirebaseDatabase.getInstance();
                db.getReference("GioHang").child(DangNhapActivity.auth.getUid()).child("Sach").child(sach.getMaSach()).setValue(sach);
            }
        });



        holder.btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), sach.getTenSach() + sach.getMaSach(), Toast.LENGTH_SHORT).show();
                FirebaseDatabase db = FirebaseDatabase.getInstance();
                db.getReference("GioHang").child(DangNhapActivity.auth.getUid()).child("Sach").child(sach.getMaSach()).removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(v.getContext(),"Đã Xóa",Toast.LENGTH_SHORT).show();
                        notifyDataSetChanged();

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        if(gioHangList != null){
            return  gioHangList.size();
        }
        return 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvTenSach,tvGia;
        EditText edSoLuong;
        ImageView imSachGH;
        ImageButton btnCong,btnTru,btnXoa;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenSach = itemView.findViewById(R.id.tvTenSachGH);
            tvGia = itemView.findViewById(R.id.tvGiaSachGH);
            edSoLuong = itemView.findViewById(R.id.edSoLuongGH);
            imSachGH = itemView.findViewById(R.id.im_GioHang);
            btnCong = itemView.findViewById(R.id.btnCong);
            btnTru   = itemView.findViewById(R.id.btnTru);
            btnXoa = itemView.findViewById(R.id.image_xoa);
        }
    }

    public interface CallBack{
        void onClick(int position, Sach sach);

    }
}
