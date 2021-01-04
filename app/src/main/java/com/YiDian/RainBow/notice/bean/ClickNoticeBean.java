package com.YiDian.RainBow.notice.bean;

import java.util.List;

public class ClickNoticeBean {

    /**
     * msg : 查询成功
     * type : OK
     * object : [{"id":515,"msgType":4,"msgUserId":1031,"userMsgId":1030,"msgContent":"何梦洋赞了你的动态","msgFatherId":695,"createTime":"2021-01-04 10:51:05","nickName":"何梦洋","headImg":"http://img.rianbow.cn/202012091309129337159.jpg","contentInfo":"我牛逼","contentId":238,"firstComment":0,"commentInfo":null,"userType":null,"isRead":2},{"id":489,"msgType":3,"msgUserId":1031,"userMsgId":1030,"msgContent":"何梦洋赞了你的评论","msgFatherId":668,"createTime":"2020-12-26 15:29:19","nickName":"何梦洋","headImg":"http://img.rianbow.cn/202012091309129337159.jpg","contentInfo":null,"contentId":236,"firstComment":377,"commentInfo":"测试回复2222","userType":null,"isRead":2},{"id":484,"msgType":4,"msgUserId":1031,"userMsgId":1030,"msgContent":"何梦洋赞了你的动态","msgFatherId":667,"createTime":"2020-12-26 15:26:15","nickName":"何梦洋","headImg":"http://img.rianbow.cn/202012091309129337159.jpg","contentInfo":"啊是皮肤好几篇","contentId":239,"firstComment":0,"commentInfo":null,"userType":null,"isRead":2},{"id":488,"msgType":4,"msgUserId":1031,"userMsgId":1030,"msgContent":"何梦洋赞了你的动态","msgFatherId":667,"createTime":"2020-12-26 15:26:15","nickName":"何梦洋","headImg":"http://img.rianbow.cn/202012091309129337159.jpg","contentInfo":"啊是皮肤好几篇","contentId":239,"firstComment":0,"commentInfo":null,"userType":null,"isRead":2}]
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
         * id : 515
         * msgType : 4
         * msgUserId : 1031
         * userMsgId : 1030
         * msgContent : 何梦洋赞了你的动态
         * msgFatherId : 695
         * createTime : 2021-01-04 10:51:05
         * nickName : 何梦洋
         * headImg : http://img.rianbow.cn/202012091309129337159.jpg
         * contentInfo : 我牛逼
         * contentId : 238
         * firstComment : 0
         * commentInfo : null
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

        public int getFirstComment() {
            return firstComment;
        }

        public void setFirstComment(int firstComment) {
            this.firstComment = firstComment;
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
