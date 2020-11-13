package com.YiDian.RainBow.main.fragment.find.bean;

import java.io.Serializable;

public class SaveFilterBean implements Serializable {
    private String Role;
    private String IsSingle;
    private String age;
    private int distance;

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public String getIsSingle() {
        return IsSingle;
    }

    public void setIsSingle(String isSingle) {
        IsSingle = isSingle;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
