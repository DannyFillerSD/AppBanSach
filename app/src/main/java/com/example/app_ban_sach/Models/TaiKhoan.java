package com.example.app_ban_sach.Models;

public class TaiKhoan {
    private String email;
    private String matKhau;
    private String tenNguoiDung;
    private String soDienThoai;
    private String diaChi;

    public TaiKhoan(){}

    public TaiKhoan(String email, String matKhau, String tenNguoiDung, String soDienThoai, String diaChi) {
        this.email = email;
        this.matKhau = matKhau;
        this.tenNguoiDung = tenNguoiDung;
        this.soDienThoai = soDienThoai;
        this.diaChi = diaChi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getTenNguoiDung() {
        return tenNguoiDung;
    }

    public void setTenNguoiDung(String tenNguoiDung) {
        this.tenNguoiDung = tenNguoiDung;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
}
