package com.YiDian.RainBow.login.bean;

public class LoginBean {

    /**
     * msg : 登录成功！
     * type : OK
     * object : {"id":100002,"phoneNum":"15652578312","nickName":"彩虹用户5531","headImg":"http://img.rianbow.cn/202101091337175732682.png","backImg":null,"explains":null,"birthday":null,"userType":1,"userRole":null,"isSingle":1,"lng":111,"lat":111,"ratio":0.5,"attestation":0,"createTime":"2021-01-16 19:32:49","isFans":null,"countNum":null,"distance":null,"distancing":null,"age":null,"invitationCode":null}
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
         * id : 100002
         * phoneNum : 15652578312
         * nickName : 彩虹用户5531
         * headImg : http://img.rianbow.cn/202101091337175732682.png
         * backImg : null
         * explains : null
         * birthday : null
         * userType : 1
         * userRole : null
         * isSingle : 1
         * lng : 111.0
         * lat : 111.0
         * ratio : 0.5
         * attestation : 0
         * createTime : 2021-01-16 19:32:49
         * isFans : null
         * countNum : null
         * distance : null
         * distancing : null
         * age : null
         * invitationCode : null
         */

        private int id;
        private String phoneNum;
        private String nickName;
        private String headImg;
        private Object backImg;
        private Object explains;
        private Object birthday;
        private int userType;
        private Object userRole;
        private int isSingle;
        private double lng;
        private double lat;
        private double ratio;
        private int attestation;
        private String createTime;
        private Object isFans;
        private Object countNum;
        private Object distance;
        private Object distancing;
        private Object age;
        private Object invitationCode;

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

        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public Object getBackImg() {
            return backImg;
        }

        public void setBackImg(Object backImg) {
            this.backImg = backImg;
        }

        public Object getExplains() {
            return explains;
        }

        public void setExplains(Object explains) {
            this.explains = explains;
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

        public int getIsSingle() {
            return isSingle;
        }

        public void setIsSingle(int isSingle) {
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

        public double getRatio() {
            return ratio;
        }

        public void setRatio(double ratio) {
            this.ratio = ratio;
        }

        public int getAttestation() {
            return attestation;
        }

        public void setAttestation(int attestation) {
            this.attestation = attestation;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public Object getIsFans() {
            return isFans;
        }

        public void setIsFans(Object isFans) {
            this.isFans = isFans;
        }

        public Object getCountNum() {
            return countNum;
        }

        public void setCountNum(Object countNum) {
            this.countNum = countNum;
        }

        public Object getDistance() {
            return distance;
        }

        public void setDistance(Object distance) {
            this.distance = distance;
        }

        public Object getDistancing() {
            return distancing;
        }

        public void setDistancing(Object distancing) {
            this.distancing = distancing;
        }

        public Object getAge() {
            return age;
        }

        public void setAge(Object age) {
            this.age = age;
        }

        public Object getInvitationCode() {
            return invitationCode;
        }

        public void setInvitationCode(Object invitationCode) {
            this.invitationCode = invitationCode;
        }
    }
}
