package com.YiDian.RainBow.imgroup.bean;

import java.util.List;

public class GroupMemberTwoBean {

    /**
     * msg : 查询成功
     * type : OK
     * object : [{"id":null,"groupId":333345,"userId":1030,"groupType":1,"isTop":null,"createTime":"2021-03-11 14:16:54","userNum":null,"groupName":null,"groupInfo":null,"groupImg":null,"nickName":"阿杨在此","headImg":"http://img.rianbow.cn/202103061150135187118.jpg"}]
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
         * groupId : 333345
         * userId : 1030
         * groupType : 1
         * isTop : null
         * createTime : 2021-03-11 14:16:54
         * userNum : null
         * groupName : null
         * groupInfo : null
         * groupImg : null
         * nickName : 阿杨在此
         * headImg : http://img.rianbow.cn/202103061150135187118.jpg
         */

        private Object id;
        private int groupId;
        private int userId;
        private int groupType;
        private Object isTop;
        private String createTime;
        private Object userNum;
        private Object groupName;
        private Object groupInfo;
        private Object groupImg;
        private String nickName;
        private String headImg;

        public Object getId() {
            return id;
        }

        public void setId(Object id) {
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

        public Object getIsTop() {
            return isTop;
        }

        public void setIsTop(Object isTop) {
            this.isTop = isTop;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public Object getUserNum() {
            return userNum;
        }

        public void setUserNum(Object userNum) {
            this.userNum = userNum;
        }

        public Object getGroupName() {
            return groupName;
        }

        public void setGroupName(Object groupName) {
            this.groupName = groupName;
        }

        public Object getGroupInfo() {
            return groupInfo;
        }

        public void setGroupInfo(Object groupInfo) {
            this.groupInfo = groupInfo;
        }

        public Object getGroupImg() {
            return groupImg;
        }

        public void setGroupImg(Object groupImg) {
            this.groupImg = groupImg;
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
    }
}
