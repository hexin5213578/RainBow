package com.YiDian.RainBow.setup.bean;

public class GetBindPhoneMsgBean {

    /**
     * msg : 您已绑定手机号！
     * type : OK
     * object : {"id":null,"phoneNum":"17698567777","nickName":null,"headImg":null,"backImg":null,"explains":null,"birthday":null,"userType":null,"userRole":null,"isSingle":null,"lng":null,"lat":null,"ratio":null,"attestation":null,"createTime":null,"isFans":null,"countNum":null,"distance":null,"distancing":null,"age":null,"invitationCode":null}
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
         * id : null
         * phoneNum : 17698567777
         * nickName : null
         * headImg : null
         * backImg : null
         * explains : null
         * birthday : null
         * userType : null
         * userRole : null
         * isSingle : null
         * lng : null
         * lat : null
         * ratio : null
         * attestation : null
         * createTime : null
         * isFans : null
         * countNum : null
         * distance : null
         * distancing : null
         * age : null
         * invitationCode : null
         */

        private Object id;
        private String phoneNum;
        private Object nickName;
        private Object headImg;
        private Object backImg;
        private Object explains;
        private Object birthday;
        private Object userType;
        private Object userRole;
        private Object isSingle;
        private Object lng;
        private Object lat;
        private Object ratio;
        private Object attestation;
        private Object createTime;
        private Object isFans;
        private Object countNum;
        private Object distance;
        private Object distancing;
        private Object age;
        private Object invitationCode;

        public Object getId() {
            return id;
        }

        public void setId(Object id) {
            this.id = id;
        }

        public String getPhoneNum() {
            return phoneNum;
        }

        public void setPhoneNum(String phoneNum) {
            this.phoneNum = phoneNum;
        }

        public Object getNickName() {
            return nickName;
        }

        public void setNickName(Object nickName) {
            this.nickName = nickName;
        }

        public Object getHeadImg() {
            return headImg;
        }

        public void setHeadImg(Object headImg) {
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

        public Object getUserType() {
            return userType;
        }

        public void setUserType(Object userType) {
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

        public Object getLng() {
            return lng;
        }

        public void setLng(Object lng) {
            this.lng = lng;
        }

        public Object getLat() {
            return lat;
        }

        public void setLat(Object lat) {
            this.lat = lat;
        }

        public Object getRatio() {
            return ratio;
        }

        public void setRatio(Object ratio) {
            this.ratio = ratio;
        }

        public Object getAttestation() {
            return attestation;
        }

        public void setAttestation(Object attestation) {
            this.attestation = attestation;
        }

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
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
