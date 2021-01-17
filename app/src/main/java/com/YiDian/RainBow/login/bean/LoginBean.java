package com.YiDian.RainBow.login.bean;

public class LoginBean {

    /**
     * msg : 登录成功！
     * type : OK
     * object : {"id":1030,"phoneNum":"15652578310","nickName":"恭喜嘻嘻明","headImg":"http://img.rianbow.cn/f86d726e20210117175619.jpg","backImg":"http://img.rianbow.cn/5fab96d020210115175710.jpg","explains":"主要是移民洗衣液","birthday":"1998-01-01","userType":1,"userRole":"T","isSingle":2,"lng":115.298826,"lat":34.097066,"ratio":0.5,"attestation":5,"createTime":"2020-12-11 15:46:34","isFans":null,"countNum":null,"distance":null,"distancing":null,"age":23,"invitationCode":null}
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
         * id : 1030
         * phoneNum : 15652578310
         * nickName : 恭喜嘻嘻明
         * headImg : http://img.rianbow.cn/f86d726e20210117175619.jpg
         * backImg : http://img.rianbow.cn/5fab96d020210115175710.jpg
         * explains : 主要是移民洗衣液
         * birthday : 1998-01-01
         * userType : 1
         * userRole : T
         * isSingle : 2
         * lng : 115.298826
         * lat : 34.097066
         * ratio : 0.5
         * attestation : 5
         * createTime : 2020-12-11 15:46:34
         * isFans : null
         * countNum : null
         * distance : null
         * distancing : null
         * age : 23
         * invitationCode : null
         */

        private int id;
        private String phoneNum;
        private String nickName;
        private String headImg;
        private String backImg;
        private String explains;
        private String birthday;
        private int userType;
        private String userRole;
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
        private int age;
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

        public String getBackImg() {
            return backImg;
        }

        public void setBackImg(String backImg) {
            this.backImg = backImg;
        }

        public String getExplains() {
            return explains;
        }

        public void setExplains(String explains) {
            this.explains = explains;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public int getUserType() {
            return userType;
        }

        public void setUserType(int userType) {
            this.userType = userType;
        }

        public String getUserRole() {
            return userRole;
        }

        public void setUserRole(String userRole) {
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

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
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
