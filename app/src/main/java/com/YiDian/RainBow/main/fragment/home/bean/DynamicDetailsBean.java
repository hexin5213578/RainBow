package com.YiDian.RainBow.main.fragment.home.bean;

import java.util.List;

public class DynamicDetailsBean {

    /**
     * msg : getContent
     * type : OK
     * object : {"id":214,"userId":1031,"contentInfo":"#热门话题1##热门话题2##热门话题3#我呢路哦哦马路","contentImg":"","lng":115.298766,"lat":34.097041,"createTime":"2020-12-16 09:39:52","status":1,"viewNum":null,"isOpen":1,"imgType":1,"isAttention":false,"isCollect":false,"isClick":false,"clickNum":0,"topics":[{"id":48,"topicTitle":"热门话题1","topicNum":1,"topicQuality":null,"topicViewNum":0},{"id":45,"topicTitle":"热门话题2","topicNum":4,"topicQuality":null,"topicViewNum":0},{"id":46,"topicTitle":"热门话题3","topicNum":3,"topicQuality":null,"topicViewNum":0}],"userInfo":{"id":1031,"phoneNum":null,"nickName":"李四","headImg":"http://img.rianbow.cn/202012091309325288188.jpg","explains":null,"birthday":null,"userType":null,"userRole":"T","isSingle":null,"lng":115.54894616,"lat":116.151641641,"ratio":null,"attestation":1,"createTime":null,"countNum":null,"distance":null,"distancing":null,"age":null,"invitationCode":null},"commentCount":2,"distance":7.415335882662847}
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
         * id : 214
         * userId : 1031
         * contentInfo : #热门话题1##热门话题2##热门话题3#我呢路哦哦马路
         * contentImg :
         * lng : 115.298766
         * lat : 34.097041
         * createTime : 2020-12-16 09:39:52
         * status : 1
         * viewNum : null
         * isOpen : 1
         * imgType : 1
         * isAttention : false
         * isCollect : false
         * isClick : false
         * clickNum : 0
         * topics : [{"id":48,"topicTitle":"热门话题1","topicNum":1,"topicQuality":null,"topicViewNum":0},{"id":45,"topicTitle":"热门话题2","topicNum":4,"topicQuality":null,"topicViewNum":0},{"id":46,"topicTitle":"热门话题3","topicNum":3,"topicQuality":null,"topicViewNum":0}]
         * userInfo : {"id":1031,"phoneNum":null,"nickName":"李四","headImg":"http://img.rianbow.cn/202012091309325288188.jpg","explains":null,"birthday":null,"userType":null,"userRole":"T","isSingle":null,"lng":115.54894616,"lat":116.151641641,"ratio":null,"attestation":1,"createTime":null,"countNum":null,"distance":null,"distancing":null,"age":null,"invitationCode":null}
         * commentCount : 2
         * distance : 7.415335882662847
         */

        private int id;
        private int userId;
        private String contentInfo;
        private String contentImg;
        private double lng;
        private double lat;
        private String createTime;
        private int status;
        private Object viewNum;
        private int isOpen;
        private int imgType;
        private boolean isAttention;
        private boolean isCollect;
        private boolean isClick;
        private int clickNum;
        private UserInfoBean userInfo;
        private int commentCount;
        private String distance;
        private List<TopicsBean> topics;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getContentInfo() {
            return contentInfo;
        }

        public void setContentInfo(String contentInfo) {
            this.contentInfo = contentInfo;
        }

        public String getContentImg() {
            return contentImg;
        }

        public void setContentImg(String contentImg) {
            this.contentImg = contentImg;
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

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public Object getViewNum() {
            return viewNum;
        }

        public void setViewNum(Object viewNum) {
            this.viewNum = viewNum;
        }

        public int getIsOpen() {
            return isOpen;
        }

        public void setIsOpen(int isOpen) {
            this.isOpen = isOpen;
        }

        public int getImgType() {
            return imgType;
        }

        public void setImgType(int imgType) {
            this.imgType = imgType;
        }

        public boolean isIsAttention() {
            return isAttention;
        }

        public void setIsAttention(boolean isAttention) {
            this.isAttention = isAttention;
        }

        public boolean isIsCollect() {
            return isCollect;
        }

        public void setIsCollect(boolean isCollect) {
            this.isCollect = isCollect;
        }

        public boolean isIsClick() {
            return isClick;
        }

        public void setIsClick(boolean isClick) {
            this.isClick = isClick;
        }

        public int getClickNum() {
            return clickNum;
        }

        public void setClickNum(int clickNum) {
            this.clickNum = clickNum;
        }

        public UserInfoBean getUserInfo() {
            return userInfo;
        }

        public void setUserInfo(UserInfoBean userInfo) {
            this.userInfo = userInfo;
        }

        public int getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(int commentCount) {
            this.commentCount = commentCount;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public List<TopicsBean> getTopics() {
            return topics;
        }

        public void setTopics(List<TopicsBean> topics) {
            this.topics = topics;
        }

        public static class UserInfoBean {
            /**
             * id : 1031
             * phoneNum : null
             * nickName : 李四
             * headImg : http://img.rianbow.cn/202012091309325288188.jpg
             * explains : null
             * birthday : null
             * userType : null
             * userRole : T
             * isSingle : null
             * lng : 115.54894616
             * lat : 116.151641641
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

        public static class TopicsBean {
            /**
             * id : 48
             * topicTitle : 热门话题1
             * topicNum : 1
             * topicQuality : null
             * topicViewNum : 0
             */

            private int id;
            private String topicTitle;
            private int topicNum;
            private Object topicQuality;
            private int topicViewNum;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTopicTitle() {
                return topicTitle;
            }

            public void setTopicTitle(String topicTitle) {
                this.topicTitle = topicTitle;
            }

            public int getTopicNum() {
                return topicNum;
            }

            public void setTopicNum(int topicNum) {
                this.topicNum = topicNum;
            }

            public Object getTopicQuality() {
                return topicQuality;
            }

            public void setTopicQuality(Object topicQuality) {
                this.topicQuality = topicQuality;
            }

            public int getTopicViewNum() {
                return topicViewNum;
            }

            public void setTopicViewNum(int topicViewNum) {
                this.topicViewNum = topicViewNum;
            }
        }
    }
}
