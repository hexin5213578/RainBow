package com.YiDian.RainBow.topic;

import java.io.Serializable;

public class SaveIntentMsgBean implements Serializable {
    private String msg;//
    private int flag;//用户是自己还是别人
    private int id;//用户的id

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
