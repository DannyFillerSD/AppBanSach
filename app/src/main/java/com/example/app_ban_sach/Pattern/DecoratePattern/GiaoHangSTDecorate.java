package com.example.app_ban_sach.Pattern.DecoratePattern;

public class GiaoHangSTDecorate extends GiaoHangAccessories{
    public GiaoHangSTDecorate(IGiaoHang giaoHang) {
        super(giaoHang);
    }
    public double GetGiaCoGiaoHang(double gia){
        return gia + super.GetGia();
    }
}
