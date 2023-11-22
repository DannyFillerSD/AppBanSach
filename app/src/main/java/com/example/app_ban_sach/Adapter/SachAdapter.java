package com.example.app_ban_sach.Adapter;

import android.content.Context;
import android.icu.text.DecimalFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_ban_sach.Models.Sach;
import com.example.app_ban_sach.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SachAdapter extends RecyclerView.Adapter<SachAdapter.MyViewHolder> {

    private Context context;
    public static ArrayList<Sach> sachList;
    CallBack sachCall;

    public SachAdapter(Context context, ArrayList<Sach> SachList,CallBack SachCall) {
        this.context = context;
        sachList = SachList;
        this.sachCall = SachCall;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sanpham,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Sach sach =sachList.get(position);
        holder.tvTenSach.setText(sachList.get(position).getTenSach());
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        holder.tvGia.setText(decimalFormat.format(sachList.get(position).getGia()) + " VNĐ");
        Picasso.get().load(sachList.get(position).getHinhAnh()).into(holder.imageSach);
        holder.itemView.setOnClickListener(view -> sachCall.onClick(position,sach));
    }

    @Override
    public int getItemCount() {
        if(sachList != null){
            return  sachList.size();
        }
        return 0;
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvTenSach,tvGia;
        ImageView imageSach;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenSach = itemView.findViewById(R.id.tvTenSach);
            tvGia = itemView.findViewById(R.id.tvGia);
            imageSach = itemView.findViewById(R.id.imageSach);
        }
    }
    public interface CallBack{
        void onClick(int position, Sach sach);
        void setFilteredList(ArrayList<Sach> filteredList);
    }
}




