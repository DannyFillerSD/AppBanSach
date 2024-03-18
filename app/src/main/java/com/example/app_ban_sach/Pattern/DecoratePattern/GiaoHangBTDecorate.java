package com.example.app_ban_sach.Pattern.DecoratePattern;

public class GiaoHangBTDecorate extends GiaoHangAccessories{
    public GiaoHangBTDecorate(IGiaoHang giaoHang) {
        super(giaoHang);
    }
    public double GetGiaCoGiaoHang(double gia){
        return gia + super.GetGia();
    }
}
