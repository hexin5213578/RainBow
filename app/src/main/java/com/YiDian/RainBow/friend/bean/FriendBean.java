package com.YiDian.RainBow.friend.bean;

import java.util.List;

public class FriendBean {

    /**
     * msg : 查询成功
     * type : OK
     * object : [{"id":null,"userId":1030,"fansId":1031,"createTime":"2020-12-27 09:45:09","nickName":"李四","headImg":"http://img.rianbow.cn/202012091309325288188.jpg","explains":"得到的","userRole":"T"}]
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
         * fansId : 1031
         * createTime : 2020-12-27 09:45:09
         * nickName : 李四
         * headImg : http://img.rianbow.cn/202012091309325288188.jpg
         * explains : 得到的
         * userRole : T
         */

        private Object id;
        private int userId;
        private int fansId;
        private String createTime;
        private String nickName;
        private String headImg;
        private String explains;
        private String userRole;
        private boolean ischeck;

        public boolean isIscheck() {
            return ischeck;
        }

        public void setIscheck(boolean ischeck) {
            this.ischeck = ischeck;
        }

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

        public int getFansId() {
            return fansId;
        }

        public void setFansId(int fansId) {
            this.fansId = fansId;
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
    }
}
