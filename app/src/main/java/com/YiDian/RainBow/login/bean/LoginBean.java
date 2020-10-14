package com.YiDian.RainBow.login.bean;

public class LoginBean {

    /**
     * msg : 返回信息
     * type : OK
     * object : {"id":102,"phoneNum":"18738037948","nickName":"张三","headImg":null,"birthday":null,"userType":1,"userRole":null,"isSingle":null,"lng":null,"lat":null,"ratio":null,"createTime":"2020-10-12 10:19:33","countNum":null,"invitationCode":null,"jid":null}
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
         * id : 102
         * phoneNum : 18738037948
         * nickName : 张三
         * headImg : null
         * birthday : null
         * userType : 1
         * userRole : null
         * isSingle : null
         * lng : null
         * lat : null
         * ratio : null
         * createTime : 2020-10-12 10:19:33
         * countNum : null
         * invitationCode : null
         * jid : null
         */

        private int id;
        private String phoneNum;
        private String nickName;
        private Object headImg;
        private Object birthday;
        private int userType;
        private Object userRole;
        private Object isSingle;
        private double lng;
        private double lat;
        private Object ratio;
        private String createTime;
        private Object countNum;
        private Object invitationCode;
        private Object jid;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPhoneNum() {
            return phoneNum;
        }

        public void setPhoneNum(String phoneNum) {
            this.phoneNum = phoneNum;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public Object getHeadImg() {
            return headImg;
        }

        public void setHeadImg(Object headImg) {
            this.headImg = headImg;
        }

        public Object getBirthday() {
            return birthday;
        }

        public void setBirthday(Object birthday) {
            this.birthday = birthday;
        }

        public int getUserType() {
            return userType;
        }

        public void setUserType(int userType) {
            this.userType = userType;
        }

        public Object getUserRole() {
            return userRole;
        }

        public void setUserRole(Object userRole) {
            this.userRole = userRole;
        }

        public Object getIsSingle() {
            return isSingle;
        }

        public void setIsSingle(Object isSingle) {
            this.isSingle = isSingle;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public Object getRatio() {
            return ratio;
        }

        public void setRatio(Object ratio) {
            this.ratio = ratio;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public Object getCountNum() {
            return countNum;
        }

        public void setCountNum(Object countNum) {
            this.countNum = countNum;
        }

        public Object getInvitationCode() {
            return invitationCode;
        }

        public void setInvitationCode(Object invitationCode) {
            this.invitationCode = invitationCode;
        }

        public Object getJid() {
            return jid;
        }

        public void setJid(Object jid) {
            this.jid = jid;
        }
    }
}
