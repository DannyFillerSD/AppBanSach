package com.example.app_ban_sach.Models;

public class Sach {
    private String tenSach;
    private String theLoai;
    private double gia;
    private String hinhAnh;
    private String maSach;
    private int soLuong;
    public Sach (){}

    public Sach(String tenSach, String theLoai, double gia, String hinhAnh, String maSach, int soLuong) {
        this.tenSach = tenSach;
        this.theLoai = theLoai;
        this.gia = gia;
        this.hinhAnh = hinhAnh;
        this.maSach = maSach;
        this.soLuong = soLuong;
    }

    public Sach(String tenSach, String theLoai, double gia, String hinhAnh, String maSach) {
        this.tenSach = tenSach;
        this.theLoai = theLoai;
        this.gia = gia;
        this.hinhAnh = hinhAnh;
        this.maSach = maSach;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public String getTheLoai() {
        return theLoai;
    }

    public void setTheLoai(String theLoai) {
        this.theLoai = theLoai;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
