package com.YiDian.RainBow.imgroup.bean;

import java.util.List;

public class MyJoinGroupMsgBean {

    /**
     * msg : 查询成功
     * type : OK
     * object : [{"id":null,"groupName":"万古神帝103","groupInfo":"欢迎来加入我们","groupImg":"http://img.rianbow.cn/20201209103443580181.jpg","holderId":1030,"baseMap":null,"addType":null,"createTime":"2020-12-05 17:11:21","userNum":1,"userId":null,"isTop":0}]
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
         * groupName : 万古神帝103
         * groupInfo : 欢迎来加入我们
         * groupImg : http://img.rianbow.cn/20201209103443580181.jpg
         * holderId : 1030
         * baseMap : null
         * addType : null
         * createTime : 2020-12-05 17:11:21
         * userNum : 1
         * userId : null
         * isTop : 0
         */

        private int id;
        private String groupName;
        private String groupInfo;
        private String groupImg;
        private int holderId;
        private Object baseMap;
        private Object addType;
        private String createTime;
        private int userNum;
        private Object userId;
        private int isTop;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGroupName() {
            return groupName;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }

        public String getGroupInfo() {
            return groupInfo;
        }

        public void setGroupInfo(String groupInfo) {
            this.groupInfo = groupInfo;
        }

        public String getGroupImg() {
            return groupImg;
        }

        public void setGroupImg(String groupImg) {
            this.groupImg = groupImg;
        }

        public int getHolderId() {
            return holderId;
        }

        public void setHolderId(int holderId) {
            this.holderId = holderId;
        }

        public Object getBaseMap() {
            return baseMap;
        }

        public void setBaseMap(Object baseMap) {
            this.baseMap = baseMap;
        }

        public Object getAddType() {
            return addType;
        }

        public void setAddType(Object addType) {
            this.addType = addType;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getUserNum() {
            return userNum;
        }

        public void setUserNum(int userNum) {
            this.userNum = userNum;
        }

        public Object getUserId() {
            return userId;
        }

        public void setUserId(Object userId) {
            this.userId = userId;
        }

        public int getIsTop() {
            return isTop;
        }

        public void setIsTop(int isTop) {
            this.isTop = isTop;
        }
    }
}
