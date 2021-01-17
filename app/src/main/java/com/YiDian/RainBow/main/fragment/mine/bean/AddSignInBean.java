package com.YiDian.RainBow.main.fragment.mine.bean;

public class AddSignInBean {

    /**
     * msg : addSign
     * type : OK
     * object : {"签到信息":"签到成功","获得签到奖励":5,"连续签到天数":1}
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
         * 签到信息 : 签到成功
         * 获得签到奖励 : 5
         * 连续签到天数 : 1
         */

        private String 签到信息;
        private int 获得签到奖励;
        private int 连续签到天数;

        public String get签到信息() {
            return 签到信息;
        }

        public void set签到信息(String 签到信息) {
            this.签到信息 = 签到信息;
        }

        public int get获得签到奖励() {
            return 获得签到奖励;
        }

        public void set获得签到奖励(int 获得签到奖励) {
            this.获得签到奖励 = 获得签到奖励;
        }

        public int get连续签到天数() {
            return 连续签到天数;
        }

        public void set连续签到天数(int 连续签到天数) {
            this.连续签到天数 = 连续签到天数;
        }
    }
}
