package com.YiDian.RainBow.main.fragment.mine.bean;

public class LoginUserInfoBean {

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
         * userInfo : {"id":1030,"phoneNum":"15652578310","nickName":"何梦洋","headImg":"http://img.rianbow.cn/202012091309129337159.jpg","backImg":"http://img.rianbow.cn/20201221143021562790.jpg","explains":"喔、卟説·~伱\u2018*卟懂\u2019\u2018！","birthday":"2020-12-21","userType":1,"userRole":"保密","isSingle":1,"lng":116.38990212891386,"lat":40.01636001886424,"ratio":0.5,"attestation":5,"createTime":"2020-12-11 15:46:34","isFans":null,"countNum":null,"distance":null,"distancing":null,"age":0,"invitationCode":null}
         * countFollowNum : 47
         * countFansNum : 20
         * countFriendNum : 17
         * countGroupNum : 4
         * countVisitorNum : 212
         * countGoldNum : 19851
         */

        private UserInfoBean userInfo;
        private int countFollowNum;
        private int countFansNum;
        private int countFriendNum;
        private int countGroupNum;
        private int countVisitorNum;
        private int countGoldNum;

        public UserInfoBean getUserInfo() {
            return userInfo;
        }

        public void setUserInfo(UserInfoBean userInfo) {
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

        public int getCountVisitorNum() {
            return countVisitorNum;
        }

        public void setCountVisitorNum(int countVisitorNum) {
            this.countVisitorNum = countVisitorNum;
        }

        public int getCountGoldNum() {
            return countGoldNum;
        }

        public void setCountGoldNum(int countGoldNum) {
            this.countGoldNum = countGoldNum;
        }

        public static class UserInfoBean {
            /**
             * id : 1030
             * phoneNum : 15652578310
             * nickName : 何梦洋
             * headImg : http://img.rianbow.cn/202012091309129337159.jpg
             * backImg : http://img.rianbow.cn/20201221143021562790.jpg
             * explains : 喔、卟説·~伱‘*卟懂’‘！
             * birthday : 2020-12-21
             * userType : 1
             * userRole : 保密
             * isSingle : 1
             * lng : 116.38990212891386
             * lat : 40.01636001886424
             * ratio : 0.5
             * attestation : 5
             * createTime : 2020-12-11 15:46:34
             * isFans : null
             * countNum : null
             * distance : null
             * distancing : null
             * age : 0
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
}
