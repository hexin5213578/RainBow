package com.YiDian.RainBow.imgroup.bean;

import java.util.List;

public class ShenHeListBean {

    /**
     * msg : 查询成功
     * type : OK
     * object : {"total":1,"list":[{"id":1780,"msgType":7,"msgUserId":1030,"userMsgId":1031,"msgContent":"很久想要加入你的群聊\u201c阿西吧\u201d，是否同意","msgFatherId":333345,"createTime":"2021-03-15 14:02:55","nickName":"很久","headImg":"http://img.rianbow.cn/d370f2a020210209091947.jpg","contentInfo":null,"contentId":null,"firstComment":null,"secondCommentId":null,"commentInfo":null,"userType":null,"isRead":2}],"pageNum":1,"pageSize":15,"size":1,"startRow":1,"endRow":1,"pages":1,"prePage":0,"nextPage":0,"isFirstPage":true,"isLastPage":true,"hasPreviousPage":false,"hasNextPage":false,"navigatePages":8,"navigatepageNums":[1],"navigateFirstPage":1,"navigateLastPage":1}
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
         * total : 1
         * list : [{"id":1780,"msgType":7,"msgUserId":1030,"userMsgId":1031,"msgContent":"很久想要加入你的群聊\u201c阿西吧\u201d，是否同意","msgFatherId":333345,"createTime":"2021-03-15 14:02:55","nickName":"很久","headImg":"http://img.rianbow.cn/d370f2a020210209091947.jpg","contentInfo":null,"contentId":null,"firstComment":null,"secondCommentId":null,"commentInfo":null,"userType":null,"isRead":2}]
         * pageNum : 1
         * pageSize : 15
         * size : 1
         * startRow : 1
         * endRow : 1
         * pages : 1
         * prePage : 0
         * nextPage : 0
         * isFirstPage : true
         * isLastPage : true
         * hasPreviousPage : false
         * hasNextPage : false
         * navigatePages : 8
         * navigatepageNums : [1]
         * navigateFirstPage : 1
         * navigateLastPage : 1
         */

        private int total;
        private int pageNum;
        private int pageSize;
        private int size;
        private int startRow;
        private int endRow;
        private int pages;
        private int prePage;
        private int nextPage;
        private boolean isFirstPage;
        private boolean isLastPage;
        private boolean hasPreviousPage;
        private boolean hasNextPage;
        private int navigatePages;
        private int navigateFirstPage;
        private int navigateLastPage;
        private List<ListBean> list;
        private List<Integer> navigatepageNums;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPageNum() {
            return pageNum;
        }

        public void setPageNum(int pageNum) {
            this.pageNum = pageNum;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getStartRow() {
            return startRow;
        }

        public void setStartRow(int startRow) {
            this.startRow = startRow;
        }

        public int getEndRow() {
            return endRow;
        }

        public void setEndRow(int endRow) {
            this.endRow = endRow;
        }

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public int getPrePage() {
            return prePage;
        }

        public void setPrePage(int prePage) {
            this.prePage = prePage;
        }

        public int getNextPage() {
            return nextPage;
        }

        public void setNextPage(int nextPage) {
            this.nextPage = nextPage;
        }

        public boolean isIsFirstPage() {
            return isFirstPage;
        }

        public void setIsFirstPage(boolean isFirstPage) {
            this.isFirstPage = isFirstPage;
        }

        public boolean isIsLastPage() {
            return isLastPage;
        }

        public void setIsLastPage(boolean isLastPage) {
            this.isLastPage = isLastPage;
        }

        public boolean isHasPreviousPage() {
            return hasPreviousPage;
        }

        public void setHasPreviousPage(boolean hasPreviousPage) {
            this.hasPreviousPage = hasPreviousPage;
        }

        public boolean isHasNextPage() {
            return hasNextPage;
        }

        public void setHasNextPage(boolean hasNextPage) {
            this.hasNextPage = hasNextPage;
        }

        public int getNavigatePages() {
            return navigatePages;
        }

        public void setNavigatePages(int navigatePages) {
            this.navigatePages = navigatePages;
        }

        public int getNavigateFirstPage() {
            return navigateFirstPage;
        }

        public void setNavigateFirstPage(int navigateFirstPage) {
            this.navigateFirstPage = navigateFirstPage;
        }

        public int getNavigateLastPage() {
            return navigateLastPage;
        }

        public void setNavigateLastPage(int navigateLastPage) {
            this.navigateLastPage = navigateLastPage;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public List<Integer> getNavigatepageNums() {
            return navigatepageNums;
        }

        public void setNavigatepageNums(List<Integer> navigatepageNums) {
            this.navigatepageNums = navigatepageNums;
        }

        public static class ListBean {
            /**
             * id : 1780
             * msgType : 7
             * msgUserId : 1030
             * userMsgId : 1031
             * msgContent : 很久想要加入你的群聊“阿西吧”，是否同意
             * msgFatherId : 333345
             * createTime : 2021-03-15 14:02:55
             * nickName : 很久
             * headImg : http://img.rianbow.cn/d370f2a020210209091947.jpg
             * contentInfo : null
             * contentId : null
             * firstComment : null
             * secondCommentId : null
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
            private Object contentInfo;
            private Object contentId;
            private Object firstComment;
            private Object secondCommentId;
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

            public Object getFirstComment() {
                return firstComment;
            }

            public void setFirstComment(Object firstComment) {
                this.firstComment = firstComment;
            }

            public Object getSecondCommentId() {
                return secondCommentId;
            }

            public void setSecondCommentId(Object secondCommentId) {
                this.secondCommentId = secondCommentId;
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
}
