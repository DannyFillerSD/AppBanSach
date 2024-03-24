package com.example.app_ban_sach.Pattern.FactoryPattern;

import static com.example.app_ban_sach.Admin.ThemSachActivity.TaoMaSach;


import com.example.app_ban_sach.Models.Sach;
import com.example.app_ban_sach.Pattern.BuilderPattern.SachBuilder;

public class SachGiaoKhoa implements ISach{
    @Override
    public void CreateSach(String ten,double gia,String sl,String uri,String tl,String masach) {
        Sach sach = new Sach();
        sach.setTenSach(ten);
        sach.setGia(gia);
        sach.setSoLuong(Integer.parseInt(sl));
        sach.setHinhAnh(uri);
        sach.setTheLoai(tl);
        TaoMaSach(sach.getTheLoai(), sach);

//        SachBuilder builder = new SachBuilder();
//        builder.setTenSach(ten);
//        builder.setGia(gia);
//        builder.setSoLuong(Integer.parseInt(sl));
//        builder.setHinhAnh(uri);
//        builder.setTheLoai(tl);
//        TaoMaSach(builder.getTheLoai(),builder.build());
    }
    /*@Override
    public Sach TaoSach(){
        SachBuilder builder = new SachBuilder();
        builder.setMaSach("69");
        Sach sach = builder.build();
        return new sach;
    }*/
}
