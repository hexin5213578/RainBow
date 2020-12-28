package com.YiDian.RainBow.friend.bean;

import java.util.List;

public class MyfollowBean {

    /**
     * msg : 查询成功
     * type : OK
     * object : {"total":52,"list":[{"id":null,"userId":1059,"fansId":1030,"createTime":"2020-12-27 11:20:52","nickName":"11","headImg":"http://img.rianbow.cn/20201209120030244035.jpg","explains":"是DFVB","userRole":"T","isAttention":0},{"id":null,"userId":1106,"fansId":1030,"createTime":"2020-12-27 13:20:13","nickName":"wan码子38","headImg":"http://img.rianbow.cn/202012091309129337159.jpg","explains":"在萨尔电饭锅","userRole":"H","isAttention":1},{"id":null,"userId":1109,"fansId":1030,"createTime":"2020-12-27 13:20:32","nickName":"wan码子41","headImg":"http://img.rianbow.cn/202012091309129337159.jpg","explains":"喔、卟説·~伱\u2018*卟懂\u2019\u2018！","userRole":"H","isAttention":1},{"id":null,"userId":1033,"fansId":1030,"createTime":"2020-12-27 09:45:09","nickName":"万古神帝","headImg":"http://img.rianbow.cn/202012091309452565249.jpg","explains":"房地产给他以秩序","userRole":"保密","isAttention":1},{"id":null,"userId":1039,"fansId":1030,"createTime":"2020-12-27 13:14:52","nickName":"东方人工","headImg":"http://img.rianbow.cn/20201209131203454171.jpg","explains":"晚上忍痛割爱发布v","userRole":"保密","isAttention":1},{"id":null,"userId":1093,"fansId":1030,"createTime":"2020-12-27 13:19:36","nickName":"元白","headImg":"http://img.rianbow.cn/202012091309129337159.jpg","explains":"爱的色放日本","userRole":"H","isAttention":1},{"id":null,"userId":1092,"fansId":1030,"createTime":"2020-12-27 13:19:34","nickName":"勇男","headImg":"http://img.rianbow.cn/202012091309129337159.jpg","explains":"早点睡方便","userRole":"H","isAttention":1},{"id":null,"userId":1038,"fansId":1030,"createTime":"2020-12-27 13:14:48","nickName":"北方","headImg":"http://img.rianbow.cn/202012091312202833963.jpg","explains":"都发给你","userRole":"T","isAttention":1},{"id":null,"userId":1097,"fansId":1030,"createTime":"2020-12-27 13:19:45","nickName":"博康","headImg":"http://img.rianbow.cn/202012091309129337159.jpg","explains":"谁让他忽然二维数组","userRole":"H","isAttention":1},{"id":null,"userId":1098,"fansId":1030,"createTime":"2020-12-27 13:19:47","nickName":"和同","headImg":"http://img.rianbow.cn/202012091309129337159.jpg","explains":"等同于今年体弱多病","userRole":"H","isAttention":1},{"id":null,"userId":1079,"fansId":1030,"createTime":"2020-12-27 13:18:59","nickName":"嘉慕","headImg":"http://img.rianbow.cn/202012091309129337159.jpg","explains":"对方提供意见和地方","userRole":"H","isAttention":1},{"id":null,"userId":1081,"fansId":1030,"createTime":"2020-12-27 13:19:09","nickName":"天成","headImg":"http://img.rianbow.cn/202012091309129337159.jpg","explains":"统一缴纳话费第三个","userRole":"H","isAttention":1},{"id":null,"userId":1082,"fansId":1030,"createTime":"2020-12-27 13:19:11","nickName":"天空","headImg":"http://img.rianbow.cn/202012091309129337159.jpg","explains":"当事人同意福建和内容","userRole":"H","isAttention":1},{"id":null,"userId":1087,"fansId":1030,"createTime":"2020-12-27 13:19:22","nickName":"天逸","headImg":"http://img.rianbow.cn/202012091309129337159.jpg","explains":"大繁荣高回报大幅v","userRole":"H","isAttention":1},{"id":null,"userId":1108,"fansId":1030,"createTime":"2020-12-27 13:20:30","nickName":"奇正","headImg":"http://img.rianbow.cn/202012091309129337159.jpg","explains":"幸福感不","userRole":"H","isAttention":1}],"pageNum":1,"pageSize":15,"size":15,"startRow":1,"endRow":15,"pages":4,"prePage":0,"nextPage":2,"isFirstPage":true,"isLastPage":false,"hasPreviousPage":false,"hasNextPage":true,"navigatePages":8,"navigatepageNums":[1,2,3,4],"navigateFirstPage":1,"navigateLastPage":4}
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
         * total : 52
         * list : [{"id":null,"userId":1059,"fansId":1030,"createTime":"2020-12-27 11:20:52","nickName":"11","headImg":"http://img.rianbow.cn/20201209120030244035.jpg","explains":"是DFVB","userRole":"T","isAttention":0},{"id":null,"userId":1106,"fansId":1030,"createTime":"2020-12-27 13:20:13","nickName":"wan码子38","headImg":"http://img.rianbow.cn/202012091309129337159.jpg","explains":"在萨尔电饭锅","userRole":"H","isAttention":1},{"id":null,"userId":1109,"fansId":1030,"createTime":"2020-12-27 13:20:32","nickName":"wan码子41","headImg":"http://img.rianbow.cn/202012091309129337159.jpg","explains":"喔、卟説·~伱\u2018*卟懂\u2019\u2018！","userRole":"H","isAttention":1},{"id":null,"userId":1033,"fansId":1030,"createTime":"2020-12-27 09:45:09","nickName":"万古神帝","headImg":"http://img.rianbow.cn/202012091309452565249.jpg","explains":"房地产给他以秩序","userRole":"保密","isAttention":1},{"id":null,"userId":1039,"fansId":1030,"createTime":"2020-12-27 13:14:52","nickName":"东方人工","headImg":"http://img.rianbow.cn/20201209131203454171.jpg","explains":"晚上忍痛割爱发布v","userRole":"保密","isAttention":1},{"id":null,"userId":1093,"fansId":1030,"createTime":"2020-12-27 13:19:36","nickName":"元白","headImg":"http://img.rianbow.cn/202012091309129337159.jpg","explains":"爱的色放日本","userRole":"H","isAttention":1},{"id":null,"userId":1092,"fansId":1030,"createTime":"2020-12-27 13:19:34","nickName":"勇男","headImg":"http://img.rianbow.cn/202012091309129337159.jpg","explains":"早点睡方便","userRole":"H","isAttention":1},{"id":null,"userId":1038,"fansId":1030,"createTime":"2020-12-27 13:14:48","nickName":"北方","headImg":"http://img.rianbow.cn/202012091312202833963.jpg","explains":"都发给你","userRole":"T","isAttention":1},{"id":null,"userId":1097,"fansId":1030,"createTime":"2020-12-27 13:19:45","nickName":"博康","headImg":"http://img.rianbow.cn/202012091309129337159.jpg","explains":"谁让他忽然二维数组","userRole":"H","isAttention":1},{"id":null,"userId":1098,"fansId":1030,"createTime":"2020-12-27 13:19:47","nickName":"和同","headImg":"http://img.rianbow.cn/202012091309129337159.jpg","explains":"等同于今年体弱多病","userRole":"H","isAttention":1},{"id":null,"userId":1079,"fansId":1030,"createTime":"2020-12-27 13:18:59","nickName":"嘉慕","headImg":"http://img.rianbow.cn/202012091309129337159.jpg","explains":"对方提供意见和地方","userRole":"H","isAttention":1},{"id":null,"userId":1081,"fansId":1030,"createTime":"2020-12-27 13:19:09","nickName":"天成","headImg":"http://img.rianbow.cn/202012091309129337159.jpg","explains":"统一缴纳话费第三个","userRole":"H","isAttention":1},{"id":null,"userId":1082,"fansId":1030,"createTime":"2020-12-27 13:19:11","nickName":"天空","headImg":"http://img.rianbow.cn/202012091309129337159.jpg","explains":"当事人同意福建和内容","userRole":"H","isAttention":1},{"id":null,"userId":1087,"fansId":1030,"createTime":"2020-12-27 13:19:22","nickName":"天逸","headImg":"http://img.rianbow.cn/202012091309129337159.jpg","explains":"大繁荣高回报大幅v","userRole":"H","isAttention":1},{"id":null,"userId":1108,"fansId":1030,"createTime":"2020-12-27 13:20:30","nickName":"奇正","headImg":"http://img.rianbow.cn/202012091309129337159.jpg","explains":"幸福感不","userRole":"H","isAttention":1}]
         * pageNum : 1
         * pageSize : 15
         * size : 15
         * startRow : 1
         * endRow : 15
         * pages : 4
         * prePage : 0
         * nextPage : 2
         * isFirstPage : true
         * isLastPage : false
         * hasPreviousPage : false
         * hasNextPage : true
         * navigatePages : 8
         * navigatepageNums : [1,2,3,4]
         * navigateFirstPage : 1
         * navigateLastPage : 4
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
             * id : null
             * userId : 1059
             * fansId : 1030
             * createTime : 2020-12-27 11:20:52
             * nickName : 11
             * headImg : http://img.rianbow.cn/20201209120030244035.jpg
             * explains : 是DFVB
             * userRole : T
             * isAttention : 0
             */

            private Object id;
            private int userId;
            private int fansId;
            private String createTime;
            private String nickName;
            private String headImg;
            private String explains;
            private String userRole;
            private int isAttention;

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

            public int getIsAttention() {
                return isAttention;
            }

            public void setIsAttention(int isAttention) {
                this.isAttention = isAttention;
            }
        }
    }
}
