package com.example.app_ban_sach.Pattern.FactoryPattern;

import com.example.app_ban_sach.Models.Sach;
import com.example.app_ban_sach.Pattern.BuilderPattern.SachBuilder;

public interface ISach {
    void CreateSach(String ten,double gia,String sl,String uri,String tl,String masach);
    //Sach TaoSach(); //BuilderPattern

}

