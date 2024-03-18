package com.example.app_ban_sach.Pattern.StragetyPattern;

public class GiamGia1Stragety implements IDonHang{
    @Override
    public double GetGia(double rawPrice) {
        return rawPrice * 0.1;
    }
}
