package com.example.app_ban_sach.Pattern.BuilderPattern;

import com.example.app_ban_sach.Models.Sach;

public class SachBuilder {
    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public void setTheLoai(String theLoai) {
        this.theLoai = theLoai;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
    public Sach build(){
        return new Sach( tenSach,  theLoai,  gia,  hinhAnh,  maSach,  soLuong,  moTa);
    }
    private String tenSach;
    private String theLoai;
    private double gia;
    private String hinhAnh;
    private String maSach;
    private int soLuong = 1;
    private String moTa ;
}
