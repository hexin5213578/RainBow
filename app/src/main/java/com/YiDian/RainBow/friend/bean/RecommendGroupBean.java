package com.YiDian.RainBow.friend.bean;

import java.util.List;

public class RecommendGroupBean {

    /**
     * msg : 查询成功
     * type : OK
     * object : [{"id":8848570,"jgGroupId":0,"groupName":"哈哈哈哈","groupInfo":"签名","groupImg":"http://img.rianbow.cn/20201209103443580181.jpg","holderId":1035,"baseMap":"http://img.rianbow.cn/20201209103519075753.jpg","addType":0,"createTime":"2020-12-10 14:09:48","userNum":1,"userId":null,"isTop":null},{"id":8848581,"jgGroupId":0,"groupName":"闽宁习性自信","groupInfo":"还没有简介，快来设置吧","groupImg":"http://img.rianbow.cn/20201209103443580181.jpg","holderId":1030,"baseMap":"http://img.rianbow.cn/20201209103443580181.jpg","addType":0,"createTime":"2021-01-14 20:04:23","userNum":1,"userId":null,"isTop":null},{"id":3221121,"jgGroupId":0,"groupName":"寡不敌众喷空","groupInfo":"欢迎新人入群","groupImg":"http://img.rianbow.cn/20201209103443580181.jpg","holderId":11,"baseMap":"http://img.rianbow.cn/20201209103519075753.jpg","addType":0,"createTime":"2019-01-15","userNum":4,"userId":null,"isTop":null},{"id":11,"jgGroupId":0,"groupName":"哈哈群","groupInfo":"第三方肉疙瘩","groupImg":"http://img.rianbow.cn/20201209103443580181.jpg","holderId":1031,"baseMap":"http://img.rianbow.cn/20201209103519075753.jpg","addType":0,"createTime":"2021-01-14 17:57:57","userNum":2,"userId":null,"isTop":null},{"id":8848562,"jgGroupId":0,"groupName":"互动群103","groupInfo":"howareyougroup","groupImg":"http://img.rianbow.cn/20201209103443580181.jpg","holderId":11,"baseMap":"http://img.rianbow.cn/20201209103519075753.jpg","addType":0,"createTime":"2020-11-25","userNum":3,"userId":null,"isTop":null},{"id":2555536,"jgGroupId":0,"groupName":"柘城七匹狼103喷空","groupInfo":"七兄弟，合力断金","groupImg":"http://img.rianbow.cn/20201209103443580181.jpg","holderId":11,"baseMap":"http://img.rianbow.cn/20201209103519075753.jpg","addType":0,"createTime":"2019-10-20","userNum":5,"userId":null,"isTop":null},{"id":8848582,"jgGroupId":0,"groupName":"我哭摸鱼哦得了大家","groupInfo":"还没有简介，快来设置吧","groupImg":"http://img.rianbow.cn/20201209103443580181.jpg","holderId":1030,"baseMap":"http://img.rianbow.cn/20201209103443580181.jpg","addType":0,"createTime":"2021-01-14 20:18:36","userNum":1,"userId":null,"isTop":null},{"id":8848580,"jgGroupId":0,"groupName":"哇哈哈","groupInfo":"还没有简介，快来设置吧","groupImg":"http://img.rianbow.cn/20201209103443580181.jpg","holderId":1032,"baseMap":"http://img.rianbow.cn/20201209103443580181.jpg","addType":0,"createTime":"2021-01-14 18:52:25","userNum":2,"userId":null,"isTop":null}]
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
         * id : 8848570
         * jgGroupId : 0
         * groupName : 哈哈哈哈
         * groupInfo : 签名
         * groupImg : http://img.rianbow.cn/20201209103443580181.jpg
         * holderId : 1035
         * baseMap : http://img.rianbow.cn/20201209103519075753.jpg
         * addType : 0
         * createTime : 2020-12-10 14:09:48
         * userNum : 1
         * userId : null
         * isTop : null
         */

        private int id;
        private int jgGroupId;
        private String groupName;
        private String groupInfo;
        private String groupImg;
        private int holderId;
        private String baseMap;
        private int addType;
        private String createTime;
        private int userNum;
        private Object userId;
        private Object isTop;

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

        public Object getIsTop() {
            return isTop;
        }

        public void setIsTop(Object isTop) {
            this.isTop = isTop;
        }
    }
}
