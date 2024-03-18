package com.example.app_ban_sach.Pattern.DecoratePattern;

public abstract class GiaoHangAccessories implements IGiaoHang {
    private IGiaoHang _giaoHang;
    public GiaoHangAccessories(IGiaoHang giaoHang)
    {
        _giaoHang = giaoHang;
    }
    public double GetGia()
    {
        return _giaoHang.GetGia();
    }

}
