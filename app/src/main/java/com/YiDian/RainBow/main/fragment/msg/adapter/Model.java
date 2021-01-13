package com.YiDian.RainBow.main.fragment.msg.adapter;

/**
 * Created by Administrator on 2016/11/17.
 */

public class Model {
    private String image;//图片地址
    private String money;//钻石
    private String name;
    private int id;

    private boolean isSelected;//

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public String toString() {
        return "Model:"+isSelected;
    }
}
