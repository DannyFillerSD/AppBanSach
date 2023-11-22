package com.example.app_ban_sach.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.icu.text.DecimalFormat;
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

public class ThanhToanAdapter extends RecyclerView.Adapter<ThanhToanAdapter.MyViewHolder> {
    private Context context;
    public ArrayList<Sach> gioHangList;
    public int slCon;
    CallBack XoaCallBack;
    public ThanhToanAdapter(Context context, ArrayList<Sach> gioHangList, CallBack xoaCallBack) {
        this.context = context;
        this.gioHangList = gioHangList;
        this.XoaCallBack = xoaCallBack;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thanhtoan,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvTenSach.setText(gioHangList.get(position).getTenSach());
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        holder.tvGia.setText( decimalFormat.format(gioHangList.get(position).getGia())  + " VNĐ");
        Picasso.get().load(gioHangList.get(position).getHinhAnh()).into(holder.imSachGH);
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
        ImageView imSachGH;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenSach = itemView.findViewById(R.id.tvTenSachTT);
            tvGia = itemView.findViewById(R.id.tvGiaSachTT);
            imSachGH = itemView.findViewById(R.id.im_GioHangTT);
        }
    }

    public interface CallBack{
        void onClick(int position, Sach sach);

    }
}
