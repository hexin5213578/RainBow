package com.YiDian.RainBow.login.bean;

public class ComPleteMsgBean {

    /**
     * msg : 返回信息
     * type : OK
     * object : null
     */

    private String msg;
    private String type;
    private Object object;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
