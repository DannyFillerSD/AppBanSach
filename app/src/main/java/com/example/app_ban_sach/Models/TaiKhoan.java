package com.example.app_ban_sach.Models;

public class TaiKhoan {
    private String Email;
    private String MatKhau;
    private String TenNguoiDung;

    public TaiKhoan(){}

    public TaiKhoan(String email, String matKhau, String tenNguoiDung) {
        Email = email;
        MatKhau = matKhau;
        TenNguoiDung = tenNguoiDung;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getMatKhau() {
        return MatKhau;
    }

    public void setMatKhau(String matKhau) {
        MatKhau = matKhau;
    }

    public String getTenNguoiDung() {
        return TenNguoiDung;
    }

    public void setTenNguoiDung(String tenNguoiDung) {
        TenNguoiDung = tenNguoiDung;
    }
}
