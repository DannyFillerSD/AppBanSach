package com.example.app_ban_sach.Pattern.StragetyPattern;

public class DonHangCuaKhach {
    public IDonHang stragety ;

    public DonHangCuaKhach(IDonHang strategy)
    {
        this.stragety = strategy;
    }
    public double GetGiaDonHang(double rawPrice)
    {
        if (rawPrice > 100000)
        {
            return this.stragety.GetGia(rawPrice);
        }
        else if (rawPrice > 150000)
        {
            return this.stragety.GetGia(rawPrice);
        }else {
            return this.stragety.GetGia(rawPrice);
        }
    }
}
