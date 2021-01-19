package com.YiDian.RainBow.main.fragment.mine.bean;

public class SignNeedPayBean {

    /**
     * msg : getReSignInDays>>获取成功
     * type : OK
     * object : {"reSignMsg":"继续补签将花费5个金币","reSignInDays":3}
     */

    private String msg;
    private String type;
    private ObjectBean object;

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

    public ObjectBean getObject() {
        return object;
    }

    public void setObject(ObjectBean object) {
        this.object = object;
    }

    public static class ObjectBean {
        /**
         * reSignMsg : 继续补签将花费5个金币
         * reSignInDays : 3
         */

        private String reSignMsg;
        private int reSignInDays;

        public String getReSignMsg() {
            return reSignMsg;
        }

        public void setReSignMsg(String reSignMsg) {
            this.reSignMsg = reSignMsg;
        }

        public int getReSignInDays() {
            return reSignInDays;
        }

        public void setReSignInDays(int reSignInDays) {
            this.reSignInDays = reSignInDays;
        }
    }
}
