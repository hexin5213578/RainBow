package com.YiDian.RainBow.imgroup.bean;

import java.io.Serializable;

public class SaveIdAndHeadImgBean implements Serializable {
    private int id;
    private String headimg;
    private int jgId;

    public int getJgId() {
        return jgId;
    }

    public void setJgId(int jgId) {
        this.jgId = jgId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }
}
