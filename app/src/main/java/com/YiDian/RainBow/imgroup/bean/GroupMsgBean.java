package com.YiDian.RainBow.imgroup.bean;

public class GroupMsgBean {


    /**
     * msg : 查询成功
     * type : OK
     * object : {"id":333338,"jgGroupId":47346902,"groupName":"47346902","groupInfo":"还没有简介，快来设置吧","groupNotice":"是大V","groupImg":"http://img.rianbow.cn/202102061133559362200.png","holderId":1030,"baseMap":"http://img.rianbow.cn/202103091354152978436.jpg","addType":0,"isTop":0,"createTime":"2021-03-01 14:53:15","userNum":29,"groupId":333338,"userId":1030,"groupType":1,"nickName":null,"headImg":null}
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
         * id : 333338
         * jgGroupId : 47346902
         * groupName : 47346902
         * groupInfo : 还没有简介，快来设置吧
         * groupNotice : 是大V
         * groupImg : http://img.rianbow.cn/202102061133559362200.png
         * holderId : 1030
         * baseMap : http://img.rianbow.cn/202103091354152978436.jpg
         * addType : 0
         * isTop : 0
         * createTime : 2021-03-01 14:53:15
         * userNum : 29
         * groupId : 333338
         * userId : 1030
         * groupType : 1
         * nickName : null
         * headImg : null
         */

        private int id;
        private int jgGroupId;
        private String groupName;
        private String groupInfo;
        private String groupNotice;
        private String groupImg;
        private int holderId;
        private String baseMap;
        private int addType;
        private int isTop;
        private String createTime;
        private int userNum;
        private int groupId;
        private int userId;
        private int groupType;
        private Object nickName;
        private Object headImg;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getJgGroupId() {
            return jgGroupId;
        }

        public void setJgGroupId(int jgGroupId) {
            this.jgGroupId = jgGroupId;
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

        public String getGroupNotice() {
            return groupNotice;
        }

        public void setGroupNotice(String groupNotice) {
            this.groupNotice = groupNotice;
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

        public String getBaseMap() {
            return baseMap;
        }

        public void setBaseMap(String baseMap) {
            this.baseMap = baseMap;
        }

        public int getAddType() {
            return addType;
        }

        public void setAddType(int addType) {
            this.addType = addType;
        }

        public int getIsTop() {
            return isTop;
        }

        public void setIsTop(int isTop) {
            this.isTop = isTop;
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

        public int getGroupId() {
            return groupId;
        }

        public void setGroupId(int groupId) {
            this.groupId = groupId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getGroupType() {
            return groupType;
        }

        public void setGroupType(int groupType) {
            this.groupType = groupType;
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
    }
}
