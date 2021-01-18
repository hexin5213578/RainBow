package com.YiDian.RainBow.main.fragment.mine.bean;

public class LoginUserInfoBean {

    /**
     * msg : 查询成功
     * type : OK
     * object : {"userInfo":null,"countFollowNum":0,"countFansNum":0,"countFriendNum":0,"countGroupNum":0,"countVisitorNum":null,"countGoldNum":0}
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
         * userInfo : null
         * countFollowNum : 0
         * countFansNum : 0
         * countFriendNum : 0
         * countGroupNum : 0
         * countVisitorNum : null
         * countGoldNum : 0
         */

        private Object userInfo;
        private int countFollowNum;
        private int countFansNum;
        private int countFriendNum;
        private int countGroupNum;
        private Integer countVisitorNum;
        private int countGoldNum;

        public Object getUserInfo() {
            return userInfo;
        }

        public void setUserInfo(Object userInfo) {
            this.userInfo = userInfo;
        }

        public int getCountFollowNum() {
            return countFollowNum;
        }

        public void setCountFollowNum(int countFollowNum) {
            this.countFollowNum = countFollowNum;
        }

        public int getCountFansNum() {
            return countFansNum;
        }

        public void setCountFansNum(int countFansNum) {
            this.countFansNum = countFansNum;
        }

        public int getCountFriendNum() {
            return countFriendNum;
        }

        public void setCountFriendNum(int countFriendNum) {
            this.countFriendNum = countFriendNum;
        }

        public int getCountGroupNum() {
            return countGroupNum;
        }

        public void setCountGroupNum(int countGroupNum) {
            this.countGroupNum = countGroupNum;
        }

        public Integer getCountVisitorNum() {
            return countVisitorNum;
        }

        public void setCountVisitorNum(Integer countVisitorNum) {
            this.countVisitorNum = countVisitorNum;
        }

        public int getCountGoldNum() {
            return countGoldNum;
        }

        public void setCountGoldNum(int countGoldNum) {
            this.countGoldNum = countGoldNum;
        }
    }
}
