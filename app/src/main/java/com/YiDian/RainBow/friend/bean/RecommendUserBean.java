package com.YiDian.RainBow.friend.bean;

import java.util.List;

public class RecommendUserBean {

    /**
     * msg : 查询成功
     * type : OK
     * object : [{"id":10683,"phoneNum":null,"nickName":"第三方9414","headImg":"http://img.rianbow.cn/202012091309129337159.jpg","backImg":null,"explains":null,"birthday":null,"userType":null,"userRole":"H","isSingle":null,"lng":null,"lat":null,"ratio":null,"attestation":null,"createTime":null,"isFans":null,"countNum":null,"distance":null,"distancing":null,"age":null,"invitationCode":null},{"id":9668,"phoneNum":null,"nickName":"第三方8399","headImg":"http://img.rianbow.cn/202012091309129337159.jpg","backImg":null,"explains":null,"birthday":null,"userType":null,"userRole":"H","isSingle":null,"lng":null,"lat":null,"ratio":null,"attestation":null,"createTime":null,"isFans":null,"countNum":null,"distance":null,"distancing":null,"age":null,"invitationCode":null},{"id":2716,"phoneNum":null,"nickName":"第三方1447","headImg":"http://img.rianbow.cn/202012091309129337159.jpg","backImg":null,"explains":null,"birthday":null,"userType":null,"userRole":"H","isSingle":null,"lng":null,"lat":null,"ratio":null,"attestation":null,"createTime":null,"isFans":null,"countNum":null,"distance":null,"distancing":null,"age":null,"invitationCode":null},{"id":10544,"phoneNum":null,"nickName":"第三方9275","headImg":"http://img.rianbow.cn/202012091309129337159.jpg","backImg":null,"explains":null,"birthday":null,"userType":null,"userRole":"H","isSingle":null,"lng":null,"lat":null,"ratio":null,"attestation":null,"createTime":null,"isFans":null,"countNum":null,"distance":null,"distancing":null,"age":null,"invitationCode":null}]
     */

    private String msg;
    private String type;
    private List<ObjectBean> object;

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

    public List<ObjectBean> getObject() {
        return object;
    }

    public void setObject(List<ObjectBean> object) {
        this.object = object;
    }

    public static class ObjectBean {
        /**
         * id : 10683
         * phoneNum : null
         * nickName : 第三方9414
         * headImg : http://img.rianbow.cn/202012091309129337159.jpg
         * backImg : null
         * explains : null
         * birthday : null
         * userType : null
         * userRole : H
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

        private int id;
        private Object phoneNum;
        private String nickName;
        private String headImg;
        private Object backImg;
        private String explains;
        private Object birthday;
        private Object userType;
        private String userRole;
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

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getPhoneNum() {
            return phoneNum;
        }

        public void setPhoneNum(Object phoneNum) {
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

        public String getExplains() {
            return explains;
        }

        public void setExplains(String explains) {
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

        public String getUserRole() {
            return userRole;
        }

        public void setUserRole(String userRole) {
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
