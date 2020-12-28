package com.YiDian.RainBow.main.fragment.find.bean;

import java.util.List;

public class UserMySeeBean {

    /**
     * msg : 查询成功
     * type : OK
     * object : [{"id":null,"userId":1030,"likeType":1,"createTime":"2020-12-27 17:12:47","nickName":"wan码子64","headImg":"http://img.rianbow.cn/202012091309129337159.jpg","explains":"喔、卟説·~伱\u2018*卟懂\u2019\u2018！","userRole":"H","isFans":0,"buserId":1132},{"id":null,"userId":1030,"likeType":1,"createTime":"2020-12-27 17:12:46","nickName":"wan码子63","headImg":"http://img.rianbow.cn/202012091309129337159.jpg","explains":"喔、卟説·~伱\u2018*卟懂\u2019\u2018！","userRole":"H","isFans":0,"buserId":1131},{"id":null,"userId":1030,"likeType":1,"createTime":"2020-12-27 17:12:44","nickName":"wan码子62","headImg":"http://img.rianbow.cn/202012091309129337159.jpg","explains":"喔、卟説·~伱\u2018*卟懂\u2019\u2018！","userRole":"H","isFans":0,"buserId":1130},{"id":null,"userId":1030,"likeType":1,"createTime":"2020-12-27 17:12:42","nickName":"wan码子61","headImg":"http://img.rianbow.cn/202012091309129337159.jpg","explains":"喔、卟説·~伱\u2018*卟懂\u2019\u2018！","userRole":"H","isFans":0,"buserId":1129},{"id":null,"userId":1030,"likeType":1,"createTime":"2020-12-27 17:12:40","nickName":"wan码子60","headImg":"http://img.rianbow.cn/202012091309129337159.jpg","explains":"喔、卟説·~伱\u2018*卟懂\u2019\u2018！","userRole":"H","isFans":0,"buserId":1128},{"id":null,"userId":1030,"likeType":1,"createTime":"2020-12-27 17:12:38","nickName":"wan码子59","headImg":"http://img.rianbow.cn/202012091309129337159.jpg","explains":"喔、卟説·~伱\u2018*卟懂\u2019\u2018！","userRole":"H","isFans":0,"buserId":1127},{"id":null,"userId":1030,"likeType":1,"createTime":"2020-12-27 17:12:37","nickName":"wan码子58","headImg":"http://img.rianbow.cn/202012091309129337159.jpg","explains":"喔、卟説·~伱\u2018*卟懂\u2019\u2018！","userRole":"H","isFans":0,"buserId":1126},{"id":null,"userId":1030,"likeType":1,"createTime":"2020-12-27 17:12:35","nickName":"wan码子57","headImg":"http://img.rianbow.cn/202012091309129337159.jpg","explains":"喔、卟説·~伱\u2018*卟懂\u2019\u2018！","userRole":"H","isFans":0,"buserId":1125},{"id":null,"userId":1030,"likeType":1,"createTime":"2020-12-27 17:10:18","nickName":"wan码子56","headImg":"http://img.rianbow.cn/202012091309129337159.jpg","explains":"喔、卟説·~伱\u2018*卟懂\u2019\u2018！","userRole":"H","isFans":0,"buserId":1124},{"id":null,"userId":1030,"likeType":1,"createTime":"2020-12-27 17:10:17","nickName":"wan码子55","headImg":"http://img.rianbow.cn/202012091309129337159.jpg","explains":"喔、卟説·~伱\u2018*卟懂\u2019\u2018！","userRole":"H","isFans":0,"buserId":1123},{"id":null,"userId":1030,"likeType":1,"createTime":"2020-12-27 17:10:14","nickName":"wan码子54","headImg":"http://img.rianbow.cn/202012091309129337159.jpg","explains":"喔、卟説·~伱\u2018*卟懂\u2019\u2018！","userRole":"H","isFans":0,"buserId":1122},{"id":null,"userId":1030,"likeType":1,"createTime":"2020-12-27 17:10:13","nickName":"wan码子53","headImg":"http://img.rianbow.cn/202012091309129337159.jpg","explains":"喔、卟説·~伱\u2018*卟懂\u2019\u2018！","userRole":"H","isFans":0,"buserId":1121},{"id":null,"userId":1030,"likeType":1,"createTime":"2020-12-27 17:10:09","nickName":"wan码子52","headImg":"http://img.rianbow.cn/202012091309129337159.jpg","explains":"喔、卟説·~伱\u2018*卟懂\u2019\u2018！","userRole":"H","isFans":0,"buserId":1120},{"id":null,"userId":1030,"likeType":1,"createTime":"2020-12-27 17:10:05","nickName":"wan码子51","headImg":"http://img.rianbow.cn/202012091309129337159.jpg","explains":"喔、卟説·~伱\u2018*卟懂\u2019\u2018！","userRole":"H","isFans":0,"buserId":1119},{"id":null,"userId":1030,"likeType":1,"createTime":"2020-12-27 17:10:02","nickName":"wan码子50","headImg":"http://img.rianbow.cn/202012091309129337159.jpg","explains":"喔、卟説·~伱\u2018*卟懂\u2019\u2018！","userRole":"H","isFans":0,"buserId":1118}]
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
         * id : null
         * userId : 1030
         * likeType : 1
         * createTime : 2020-12-27 17:12:47
         * nickName : wan码子64
         * headImg : http://img.rianbow.cn/202012091309129337159.jpg
         * explains : 喔、卟説·~伱‘*卟懂’‘！
         * userRole : H
         * isFans : 0
         * buserId : 1132
         */

        private Object id;
        private int userId;
        private int likeType;
        private String createTime;
        private String nickName;
        private String headImg;
        private String explains;
        private String userRole;
        private int isFans;
        private int buserId;

        public Object getId() {
            return id;
        }

        public void setId(Object id) {
            this.id = id;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getLikeType() {
            return likeType;
        }

        public void setLikeType(int likeType) {
            this.likeType = likeType;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
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

        public String getExplains() {
            return explains;
        }

        public void setExplains(String explains) {
            this.explains = explains;
        }

        public String getUserRole() {
            return userRole;
        }

        public void setUserRole(String userRole) {
            this.userRole = userRole;
        }

        public int getIsFans() {
            return isFans;
        }

        public void setIsFans(int isFans) {
            this.isFans = isFans;
        }

        public int getBuserId() {
            return buserId;
        }

        public void setBuserId(int buserId) {
            this.buserId = buserId;
        }
    }
}
