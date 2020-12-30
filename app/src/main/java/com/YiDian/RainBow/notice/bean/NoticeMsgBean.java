package com.YiDian.RainBow.notice.bean;

import java.util.List;

public class NoticeMsgBean {

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
         * id : 115
         * msgType : 0
         * msgUserId : 1030
         * userMsgId : 1030
         * msgContent : 测试
         * msgFatherId : null
         * createTime : 2020-12-28 17:31:57
         * nickName : 何梦洋
         * headImg : http://img.rianbow.cn/202012091309129337159.jpg
         * contentInfo : null
         * contentId : null
         * commentInfo : null
         * userType : null
         * isRead : 1
         */

        private int id;
        private int msgType;
        private int msgUserId;
        private int userMsgId;
        private String msgContent;
        private Object msgFatherId;
        private String createTime;
        private String nickName;
        private String headImg;
        private Object contentInfo;
        private Object contentId;
        private Object commentInfo;
        private Object userType;
        private int isRead;

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

        public Object getMsgFatherId() {
            return msgFatherId;
        }

        public void setMsgFatherId(Object msgFatherId) {
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

        public Object getContentInfo() {
            return contentInfo;
        }

        public void setContentInfo(Object contentInfo) {
            this.contentInfo = contentInfo;
        }

        public Object getContentId() {
            return contentId;
        }

        public void setContentId(Object contentId) {
            this.contentId = contentId;
        }

        public Object getCommentInfo() {
            return commentInfo;
        }

        public void setCommentInfo(Object commentInfo) {
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
