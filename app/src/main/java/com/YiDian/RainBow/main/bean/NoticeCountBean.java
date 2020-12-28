package com.YiDian.RainBow.main.bean;

public class NoticeCountBean {

    /**
     * msg : 查询成功
     * type : OK
     * object : {"fansMessAgeNum":3,"clickMessAgeNum":0,"commentMessAgeNum":4,"systemMessAgeNum":0}
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
         * fansMessAgeNum : 3
         * clickMessAgeNum : 0
         * commentMessAgeNum : 4
         * systemMessAgeNum : 0
         */

        private int fansMessAgeNum;
        private int clickMessAgeNum;
        private int commentMessAgeNum;
        private int systemMessAgeNum;

        public int getFansMessAgeNum() {
            return fansMessAgeNum;
        }

        public void setFansMessAgeNum(int fansMessAgeNum) {
            this.fansMessAgeNum = fansMessAgeNum;
        }

        public int getClickMessAgeNum() {
            return clickMessAgeNum;
        }

        public void setClickMessAgeNum(int clickMessAgeNum) {
            this.clickMessAgeNum = clickMessAgeNum;
        }

        public int getCommentMessAgeNum() {
            return commentMessAgeNum;
        }

        public void setCommentMessAgeNum(int commentMessAgeNum) {
            this.commentMessAgeNum = commentMessAgeNum;
        }

        public int getSystemMessAgeNum() {
            return systemMessAgeNum;
        }

        public void setSystemMessAgeNum(int systemMessAgeNum) {
            this.systemMessAgeNum = systemMessAgeNum;
        }
    }
}
