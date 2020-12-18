package com.YiDian.RainBow.main.fragment.home.bean;

import java.util.List;

public class FirstCommentBean {

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
         * id : 1
         * userid : 1030
         * commentinfo : sd发顺丰
         * lng : null
         * lat : null
         * commenttype : 0
         * fatherid : 214
         * createtime : null
         * clickNum : 0
         * children : null
         * userInfo : {"id":1030,"phoneNum":null,"nickName":"何梦阳","headImg":"http://img.rianbow.cn/202012091309129337159.jpg","backImg":null,"explains":null,"birthday":null,"userType":null,"userRole":"保密","isSingle":null,"lng":115.298833,"lat":34.097004,"ratio":null,"attestation":1,"createTime":null,"countNum":null,"distance":null,"distancing":null,"age":null,"invitationCode":null}
         */

        private int id;
        private int userid;
        private String commentinfo;
        private Object lng;
        private Object lat;
        private int commenttype;
        private int fatherid;
        private Object createtime;
        private int clickNum;
        private Object children;
        private UserInfoBean userInfo;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public String getCommentinfo() {
            return commentinfo;
        }

        public void setCommentinfo(String commentinfo) {
            this.commentinfo = commentinfo;
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

        public int getCommenttype() {
            return commenttype;
        }

        public void setCommenttype(int commenttype) {
            this.commenttype = commenttype;
        }

        public int getFatherid() {
            return fatherid;
        }

        public void setFatherid(int fatherid) {
            this.fatherid = fatherid;
        }

        public Object getCreatetime() {
            return createtime;
        }

        public void setCreatetime(Object createtime) {
            this.createtime = createtime;
        }

        public int getClickNum() {
            return clickNum;
        }

        public void setClickNum(int clickNum) {
            this.clickNum = clickNum;
        }

        public Object getChildren() {
            return children;
        }

        public void setChildren(Object children) {
            this.children = children;
        }

        public UserInfoBean getUserInfo() {
            return userInfo;
        }

        public void setUserInfo(UserInfoBean userInfo) {
            this.userInfo = userInfo;
        }

        public static class UserInfoBean {
            /**
             * id : 1030
             * phoneNum : null
             * nickName : 何梦阳
             * headImg : http://img.rianbow.cn/202012091309129337159.jpg
             * backImg : null
             * explains : null
             * birthday : null
             * userType : null
             * userRole : 保密
             * isSingle : null
             * lng : 115.298833
             * lat : 34.097004
             * ratio : null
             * attestation : 1
             * createTime : null
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
            private Object explains;
            private Object birthday;
            private Object userType;
            private String userRole;
            private Object isSingle;
            private double lng;
            private double lat;
            private Object ratio;
            private int attestation;
            private Object createTime;
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

            public int getAttestation() {
                return attestation;
            }

            public void setAttestation(int attestation) {
                this.attestation = attestation;
            }

            public Object getCreateTime() {
                return createTime;
            }

            public void setCreateTime(Object createTime) {
                this.createTime = createTime;
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
}
