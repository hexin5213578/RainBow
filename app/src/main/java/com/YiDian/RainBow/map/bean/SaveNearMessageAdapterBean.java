package com.YiDian.RainBow.map.bean;

import com.amap.api.services.core.LatLonPoint;

public class SaveNearMessageAdapterBean {
    private String title;
    private String Shengfen;
    private String shiqu;
    private String xian;
    private String address;
    private LatLonPoint locataion;
    private boolean isselect;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShengfen() {
        return Shengfen;
    }

    public void setShengfen(String shengfen) {
        Shengfen = shengfen;
    }

    public String getShiqu() {
        return shiqu;
    }

    public void setShiqu(String shiqu) {
        this.shiqu = shiqu;
    }

    public String getXian() {
        return xian;
    }

    public void setXian(String xian) {
        this.xian = xian;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LatLonPoint getLocataion() {
        return locataion;
    }

    public void setLocataion(LatLonPoint locataion) {
        this.locataion = locataion;
    }

    public boolean isIsselect() {
        return isselect;
    }

    public void setIsselect(boolean isselect) {
        this.isselect = isselect;
    }
}
