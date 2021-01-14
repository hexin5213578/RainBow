package com.YiDian.RainBow.imgroup.bean;

import java.util.List;

public class MyCreateGroupMsgBean {

    /**
     * msg : 查询成功
     * type : OK
     * object : [{"id":null,"groupId":3221121,"userId":1030,"groupType":2,"isTop":1,"createTime":"2020-13-09 10:15:58","userNum":3,"groupName":"寡不敌众喷空","groupInfo":"欢迎新人入群","groupImg":"http://img.rianbow.cn/20201209103443580181.jpg","nickName":null,"headImg":null},{"id":null,"groupId":11,"userId":1030,"groupType":2,"isTop":0,"createTime":"2020-12-10 14:09:48","userNum":2,"groupName":"哈哈群","groupInfo":"第三方肉疙瘩","groupImg":"http://img.rianbow.cn/20201209103443580181.jpg","nickName":null,"headImg":null}]
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
         * groupId : 3221121
         * userId : 1030
         * groupType : 2
         * isTop : 1
         * createTime : 2020-13-09 10:15:58
         * userNum : 3
         * groupName : 寡不敌众喷空
         * groupInfo : 欢迎新人入群
         * groupImg : http://img.rianbow.cn/20201209103443580181.jpg
         * nickName : null
         * headImg : null
         */

        private int id;
        private int groupId;
        private int userId;
        private int groupType;
        private int isTop;
        private String createTime;
        private int userNum;
        private String groupName;
        private String groupInfo;
        private String groupImg;
        private Object nickName;
        private Object headImg;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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
