package com.example.app_ban_sach.Models;

import com.example.app_ban_sach.Pattern.ProtoTypePattern.Prototype;

public class Sach implements Prototype {
    private String tenSach;
    private String theLoai;
    private double gia;
    private String hinhAnh;
    private String maSach;
    private int soLuong = 1;
    private String moTa ;
    public Sach (){}
    //Prototype
    public Sach(Sach sach){
        this.tenSach = sach.tenSach;
        this.theLoai = sach.theLoai;
        this.gia = sach.gia;
        this.hinhAnh = sach.hinhAnh;
        this.maSach = sach.maSach;
        this.soLuong = sach.soLuong;
        this.moTa = sach.moTa;
    }
    @Override
    public Sach clone(){
        return new Sach(this);
    }
    //Prototype-Ends
    //Builder thì sẽ private Constructor này, nhưng để suôn sẻ thì tui vẫn để public (k crash)

    public Sach(String tenSach, String theLoai, double gia, String hinhAnh, String maSach, int soLuong, String moTa) {
        this.tenSach = tenSach;
        this.theLoai = theLoai;
        this.gia = gia;
        this.hinhAnh = hinhAnh;
        this.maSach = maSach;
        this.soLuong = soLuong;
        this.moTa = moTa;
    }
    //Builder thì sẽ private Constructor này, nhưng để suôn sẻ thì tui vẫn để public (k crash)

    public Sach(String tenSach, String theLoai, double gia, String hinhAnh, String maSach, int soLuong) {
        this.tenSach = tenSach;
        this.theLoai = theLoai;
        this.gia = gia;
        this.hinhAnh = hinhAnh;
        this.maSach = maSach;
        this.soLuong = soLuong;
    }

    //Builder thì sẽ private Constructor này, nhưng để suôn sẻ thì tui vẫn để public (k crash)
    public Sach(String tenSach, String theLoai, double gia, String hinhAnh, String maSach,String moTa) {
        this.tenSach = tenSach;
        this.theLoai = theLoai;
        this.gia = gia;
        this.hinhAnh = hinhAnh;
        this.maSach = maSach;
        this.moTa = moTa;
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

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
}
