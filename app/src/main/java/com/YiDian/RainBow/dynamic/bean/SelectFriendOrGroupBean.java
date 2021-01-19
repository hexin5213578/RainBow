package com.YiDian.RainBow.dynamic.bean;

import java.util.List;

public class SelectFriendOrGroupBean {

    /**
     * msg : 查询成功
     * type : OK
     * object : {"userList":[{"id":null,"userId":1030,"fansId":1031,"createTime":"2021-01-04 10:56:18","nickName":"李老四","headImg":"http://img.rianbow.cn/202012091309129337159.jpg","explains":"我李老四就是牛批冲天","userRole":"BI","isAttention":null},{"id":null,"userId":1030,"fansId":1038,"createTime":"2021-01-04 10:56:57","nickName":"北方","headImg":"http://img.rianbow.cn/202012091312202833963.jpg","explains":"都发给你","userRole":"T","isAttention":null},{"id":null,"userId":1030,"fansId":1032,"createTime":"2021-01-04 10:56:22","nickName":"hhhaaa李103","headImg":"http://img.rianbow.cn/202101091337175732682.png","explains":"的身姿发布的","userRole":"保密","isAttention":null},{"id":null,"userId":1030,"fansId":1036,"createTime":"2021-01-04 10:56:49","nickName":"获取QQ昵称103","headImg":"http://img.rianbow.cn/20201209131251122971.jpg","explains":"㘝养护科","userRole":"BI","isAttention":null},{"id":null,"userId":1030,"fansId":1039,"createTime":"2021-01-04 10:57:02","nickName":"东方人工","headImg":"http://img.rianbow.cn/20201209131203454171.jpg","explains":"晚上忍痛割爱发布v","userRole":"保密","isAttention":null},{"id":null,"userId":1030,"fansId":1075,"createTime":"2021-01-04 10:57:40","nickName":"弘方103","headImg":"http://img.rianbow.cn/202012091309129337159.jpg","explains":"招多少人发个红包中大润发","userRole":"H","isAttention":null},{"id":null,"userId":1030,"fansId":1033,"createTime":"2021-01-04 10:56:24","nickName":"万古神帝李103","headImg":"http://img.rianbow.cn/202012091309452565249.jpg","explains":"房地产给他以秩序","userRole":"保密","isAttention":null},{"id":null,"userId":1030,"fansId":1037,"createTime":"2021-01-04 10:56:53","nickName":"获取QQQQ昵称","headImg":"http://img.rianbow.cn/202012091312357284973.jpg","explains":"ty8uikfg","userRole":"BI","isAttention":null}],"groupList":[{"id":12,"jgGroupId":0,"groupName":"万古神帝103","groupInfo":"欢迎来加入我们","groupImg":"http://img.rianbow.cn/20201209103443580181.jpg","holderId":1030,"baseMap":"http://img.rianbow.cn/20201209103519075753.jpg","addType":1,"createTime":"2020-12-05 17:11:21","userNum":null,"userId":1030,"isTop":null}]}
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
        private List<UserListBean> userList;
        private List<GroupListBean> groupList;

        public List<UserListBean> getUserList() {
            return userList;
        }

        public void setUserList(List<UserListBean> userList) {
            this.userList = userList;
        }

        public List<GroupListBean> getGroupList() {
            return groupList;
        }

        public void setGroupList(List<GroupListBean> groupList) {
            this.groupList = groupList;
        }

        public static class UserListBean {
            /**
             * id : null
             * userId : 1030
             * fansId : 1031
             * createTime : 2021-01-04 10:56:18
             * nickName : 李老四
             * headImg : http://img.rianbow.cn/202012091309129337159.jpg
             * explains : 我李老四就是牛批冲天
             * userRole : BI
             * isAttention : null
             */

            private Object id;
            private int userId;
            private int fansId;
            private String createTime;
            private String nickName;
            private String headImg;
            private String explains;
            private String userRole;
            private Object isAttention;
            private boolean isCheck;

            public boolean isCheck() {
                return isCheck;
            }

            public void setisCheck(boolean check) {
                isCheck = check;
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

            public Object getIsAttention() {
                return isAttention;
            }

            public void setIsAttention(Object isAttention) {
                this.isAttention = isAttention;
            }
        }

        public static class GroupListBean {
            /**
             * id : 12
             * jgGroupId : 0
             * groupName : 万古神帝103
             * groupInfo : 欢迎来加入我们
             * groupImg : http://img.rianbow.cn/20201209103443580181.jpg
             * holderId : 1030
             * baseMap : http://img.rianbow.cn/20201209103519075753.jpg
             * addType : 1
             * createTime : 2020-12-05 17:11:21
             * userNum : null
             * userId : 1030
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
            private Object userNum;
            private int userId;
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

            public Object getUserNum() {
                return userNum;
            }

            public void setUserNum(Object userNum) {
                this.userNum = userNum;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
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
}
