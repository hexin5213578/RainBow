package com.YiDian.RainBow.friend.bean;

import java.util.List;

public class InitGroupBean  {

    /**
     * msg : 查询成功
     * type : OK
     * object : {"groupChuangJian":[{"id":222222,"jgGroupId":1000,"groupName":"来玩吧","groupInfo":"进来皆亲人","groupImg":"http://img.rianbow.cn/202101091337175732682.png","holderId":100025,"baseMap":null,"addType":null,"createTime":"2021-01-25 11:01:37","userNum":1,"userId":null,"isTop":0}],"groupTuiJian":[{"id":212121,"jgGroupId":1002,"groupName":"百宝箱","groupInfo":"当事人挺好","groupImg":"http://img.rianbow.cn/88fb997520210128010037.jpg","holderId":100110,"baseMap":"http://img.rianbow.cn/88fb997520210128010037.jpg","addType":0,"createTime":"2021-01-26 09:02:15","userNum":1,"userId":null,"isTop":null}],"groupJiaRu":[{"id":null,"groupId":333333,"userId":100025,"groupType":2,"isTop":0,"createTime":"2021-01-26 11:01:37","userNum":2,"groupName":"七匹狼","groupInfo":"发的规划已经","groupImg":"http://img.rianbow.cn/d680d40f20210125110119.jpg, http://img.rianbow.cn/11a07f6320210125110119.jpg, http://img.rianbow.cn/087b7fc520210125110119.jpg, http://img.rianbow.cn/cb1fcb9520210125110119.jpg","nickName":null,"headImg":null}]}
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
        private List<GroupChuangJianBean> groupChuangJian;
        private List<GroupTuiJianBean> groupTuiJian;
        private List<GroupJiaRuBean> groupJiaRu;

        public List<GroupChuangJianBean> getGroupChuangJian() {
            return groupChuangJian;
        }

        public void setGroupChuangJian(List<GroupChuangJianBean> groupChuangJian) {
            this.groupChuangJian = groupChuangJian;
        }

        public List<GroupTuiJianBean> getGroupTuiJian() {
            return groupTuiJian;
        }

        public void setGroupTuiJian(List<GroupTuiJianBean> groupTuiJian) {
            this.groupTuiJian = groupTuiJian;
        }

        public List<GroupJiaRuBean> getGroupJiaRu() {
            return groupJiaRu;
        }

        public void setGroupJiaRu(List<GroupJiaRuBean> groupJiaRu) {
            this.groupJiaRu = groupJiaRu;
        }

        public static class GroupChuangJianBean {
            /**
             * id : 222222
             * jgGroupId : 1000
             * groupName : 来玩吧
             * groupInfo : 进来皆亲人
             * groupImg : http://img.rianbow.cn/202101091337175732682.png
             * holderId : 100025
             * baseMap : null
             * addType : null
             * createTime : 2021-01-25 11:01:37
             * userNum : 1
             * userId : null
             * isTop : 0
             */

            private int id;
            private int jgGroupId;
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

        public static class GroupTuiJianBean {
            /**
             * id : 212121
             * jgGroupId : 1002
             * groupName : 百宝箱
             * groupInfo : 当事人挺好
             * groupImg : http://img.rianbow.cn/88fb997520210128010037.jpg
             * holderId : 100110
             * baseMap : http://img.rianbow.cn/88fb997520210128010037.jpg
             * addType : 0
             * createTime : 2021-01-26 09:02:15
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

        public static class GroupJiaRuBean {
            /**
             * id : null
             * groupId : 333333
             * userId : 100025
             * groupType : 2
             * isTop : 0
             * createTime : 2021-01-26 11:01:37
             * userNum : 2
             * groupName : 七匹狼
             * groupInfo : 发的规划已经
             * groupImg : http://img.rianbow.cn/d680d40f20210125110119.jpg, http://img.rianbow.cn/11a07f6320210125110119.jpg, http://img.rianbow.cn/087b7fc520210125110119.jpg, http://img.rianbow.cn/cb1fcb9520210125110119.jpg
             * nickName : null
             * headImg : null
             */

            private Object id;
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
}
