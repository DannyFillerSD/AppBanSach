package com.example.app_ban_sach.Pattern.FactoryPattern;

public class Factory {
    public static ISach GetSach(LoaiSach type)
    {
        switch (type)
        {
            case VanHoc:
                return new SachVanHoc();
            case KinhTe:
                return new SachKinhTe();
            case ThieuNhi:
                return new SachThieuNhi();
            case GiaoKhoa:
                return new SachGiaoKhoa();
            case NgoaiNgu:
                return new SachNgoaiNgu();
            default:
                throw new IllegalArgumentException("This bank type is unsupported");
        }
    }
}
