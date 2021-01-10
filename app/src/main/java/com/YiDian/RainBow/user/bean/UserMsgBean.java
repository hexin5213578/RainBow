package com.YiDian.RainBow.user.bean;

public class UserMsgBean {

    /**
     * msg : 查询成功
     * type : NULL
     * object : {"userInfo":{"id":1031,"phoneNum":null,"nickName":"李老四103","headImg":"http://img.rianbow.cn/202012091309129337159.jpg","backImg":"http://img.rianbow.cn/202012211434078716682.jpg","explains":"我李老四就是牛批冲天","birthday":null,"userType":null,"userRole":"BI","isSingle":null,"lng":null,"lat":null,"ratio":null,"attestation":null,"createTime":null,"isFans":1,"countNum":null,"distance":null,"distancing":null,"age":33,"invitationCode":null},"countFavoriteNum":1,"countFansNum":1,"countGiftNum":0}
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
         * userInfo : {"id":1031,"phoneNum":null,"nickName":"李老四103","headImg":"http://img.rianbow.cn/202012091309129337159.jpg","backImg":"http://img.rianbow.cn/202012211434078716682.jpg","explains":"我李老四就是牛批冲天","birthday":null,"userType":null,"userRole":"BI","isSingle":null,"lng":null,"lat":null,"ratio":null,"attestation":null,"createTime":null,"isFans":1,"countNum":null,"distance":null,"distancing":null,"age":33,"invitationCode":null}
         * countFavoriteNum : 1
         * countFansNum : 1
         * countGiftNum : 0
         */

        private UserInfoBean userInfo;
        private int countFavoriteNum;
        private int countFansNum;
        private int countGiftNum;

        public UserInfoBean getUserInfo() {
            return userInfo;
        }

        public void setUserInfo(UserInfoBean userInfo) {
            this.userInfo = userInfo;
        }

        public int getCountFavoriteNum() {
            return countFavoriteNum;
        }

        public void setCountFavoriteNum(int countFavoriteNum) {
            this.countFavoriteNum = countFavoriteNum;
        }

        public int getCountFansNum() {
            return countFansNum;
        }

        public void setCountFansNum(int countFansNum) {
            this.countFansNum = countFansNum;
        }

        public int getCountGiftNum() {
            return countGiftNum;
        }

        public void setCountGiftNum(int countGiftNum) {
            this.countGiftNum = countGiftNum;
        }

        public static class UserInfoBean {
            /**
             * id : 1031
             * phoneNum : null
             * nickName : 李老四103
             * headImg : http://img.rianbow.cn/202012091309129337159.jpg
             * backImg : http://img.rianbow.cn/202012211434078716682.jpg
             * explains : 我李老四就是牛批冲天
             * birthday : null
             * userType : null
             * userRole : BI
             * isSingle : null
             * lng : null
             * lat : null
             * ratio : null
             * attestation : null
             * createTime : null
             * isFans : 1
             * countNum : null
             * distance : null
             * distancing : null
             * age : 33
             * invitationCode : null
             */

            private int id;
            private Object phoneNum;
            private String nickName;
            private String headImg;
            private String backImg;
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
            private int isFans;
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

            public int getIsFans() {
                return isFans;
            }

            public void setIsFans(int isFans) {
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
}
