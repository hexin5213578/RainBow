package com.YiDian.RainBow.notice.bean;

import java.util.List;

public class CommentNoticeBean {

    /**
     * msg : 查询成功
     * type : OK
     * object : [{"id":411,"msgType":6,"msgUserId":1030,"userMsgId":1031,"msgContent":"李四回复了你的评论","msgFatherId":384,"createTime":"2020-12-21 15:55:40","nickName":"李四","headImg":"http://img.rianbow.cn/202012091309325288188.jpg","contentInfo":"指的是符合个百分点收入高回报儿童","contentId":236,"commentInfo":"测试回复2222","userType":null,"isRead":2},{"id":410,"msgType":6,"msgUserId":1030,"userMsgId":1031,"msgContent":"李四回复了你的评论","msgFatherId":383,"createTime":"2020-12-21 15:05:10","nickName":"李四","headImg":"http://img.rianbow.cn/202012091309325288188.jpg","contentInfo":"指的是符合个百分点收入高回报儿童","contentId":236,"commentInfo":"测试回复1111","userType":null,"isRead":2},{"id":413,"msgType":5,"msgUserId":1030,"userMsgId":1031,"msgContent":"李四评论了你的动态","msgFatherId":382,"createTime":"2020-12-21 14:55:40","nickName":"李四","headImg":"http://img.rianbow.cn/202012091309325288188.jpg","contentInfo":"李露露激动 葵花","contentId":233,"commentInfo":"测试2222","userType":null,"isRead":2},{"id":412,"msgType":5,"msgUserId":1030,"userMsgId":1031,"msgContent":"李四评论了你的动态","msgFatherId":381,"createTime":"2020-12-21 14:53:23","nickName":"李四","headImg":"http://img.rianbow.cn/202012091309325288188.jpg","contentInfo":"李露露激动 葵花","contentId":233,"commentInfo":"测试1111","userType":null,"isRead":2}]
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
         * id : 411
         * msgType : 6
         * msgUserId : 1030
         * userMsgId : 1031
         * msgContent : 李四回复了你的评论
         * msgFatherId : 384
         * createTime : 2020-12-21 15:55:40
         * nickName : 李四
         * headImg : http://img.rianbow.cn/202012091309325288188.jpg
         * contentInfo : 指的是符合个百分点收入高回报儿童
         * contentId : 236
         * commentInfo : 测试回复2222
         * userType : null
         * isRead : 2
         */

        private int id;
        private int msgType;
        private int msgUserId;
        private int userMsgId;
        private String msgContent;
        private int msgFatherId;
        private String createTime;
        private String nickName;
        private String headImg;
        private String contentInfo;
        private int contentId;
        private int firstComment;
        private String commentInfo;
        private Object userType;
        private int isRead;

        public int getFirstComment() {
            return firstComment;
        }

        public void setFirstComment(int firstComment) {
            this.firstComment = firstComment;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getMsgType() {
            return msgType;
        }

        public void setMsgType(int msgType) {
            this.msgType = msgType;
        }

        public int getMsgUserId() {
            return msgUserId;
        }

        public void setMsgUserId(int msgUserId) {
            this.msgUserId = msgUserId;
        }

        public int getUserMsgId() {
            return userMsgId;
        }

        public void setUserMsgId(int userMsgId) {
            this.userMsgId = userMsgId;
        }

        public String getMsgContent() {
            return msgContent;
        }

        public void setMsgContent(String msgContent) {
            this.msgContent = msgContent;
        }

        public int getMsgFatherId() {
            return msgFatherId;
        }

        public void setMsgFatherId(int msgFatherId) {
            this.msgFatherId = msgFatherId;
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

        public String getContentInfo() {
            return contentInfo;
        }

        public void setContentInfo(String contentInfo) {
            this.contentInfo = contentInfo;
        }

        public int getContentId() {
            return contentId;
        }

        public void setContentId(int contentId) {
            this.contentId = contentId;
        }

        public String getCommentInfo() {
            return commentInfo;
        }

        public void setCommentInfo(String commentInfo) {
            this.commentInfo = commentInfo;
        }

        public Object getUserType() {
            return userType;
        }

        public void setUserType(Object userType) {
            this.userType = userType;
        }

        public int getIsRead() {
            return isRead;
        }

        public void setIsRead(int isRead) {
            this.isRead = isRead;
        }
    }
}
