package com.YiDian.RainBow.main.fragment.mine.bean;

import java.util.List;

public class SelectAllDraftsBean {

    /**
     * msg : selectAllDrafts
     * type : OK
     * object : {"total":3,"list":[{"id":99,"userId":1031,"contentInfo":"摸哪具体问#热门话题3# @何梦洋0,何梦洋1, ","contentImg":"http://qjvorl4f2.hn-bkt.clouddn.com/20201125162104979119_1603545284104.jpg,http://qjvorl4f2.hn-bkt.clouddn.com/20201125162108269846_2020-10-27-20-44-21-10.jpg,http://qjvorl4f2.hn-bkt.clouddn.com/202011251621091764843_2020-10-25-22-44-58-52.jpg,http://qjvorl4f2.hn-bkt.clouddn.com/202011251621108604691.jpg","lng":null,"lat":null,"createTime":"2020-11-25 16:23:03","status":0,"viewNum":null,"isOpen":1,"imgType":21,"clickNum":null,"area":""},{"id":100,"userId":1031,"contentInfo":"","contentImg":"http://qjvorl4f2.hn-bkt.clouddn.com/20201125162142222612_1603545284104.jpg,http://qjvorl4f2.hn-bkt.clouddn.com/2020112516214582175.jpg","lng":null,"lat":null,"createTime":"2020-11-25 16:23:38","status":0,"viewNum":null,"isOpen":1,"imgType":2,"clickNum":null,"area":""},{"id":101,"userId":1031,"contentInfo":"","contentImg":"http://qjvorl4f2.hn-bkt.clouddn.com/202011251622099574366.mp4","lng":null,"lat":null,"createTime":"2020-11-25 16:24:01","status":0,"viewNum":null,"isOpen":1,"imgType":3,"clickNum":null,"area":""}],"pageNum":1,"pageSize":15,"size":3,"startRow":1,"endRow":3,"pages":1,"prePage":0,"nextPage":0,"isFirstPage":true,"isLastPage":true,"hasPreviousPage":false,"hasNextPage":false,"navigatePages":8,"navigatepageNums":[1],"navigateFirstPage":1,"navigateLastPage":1}
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
         * total : 3
         * list : [{"id":99,"userId":1031,"contentInfo":"摸哪具体问#热门话题3# @何梦洋0,何梦洋1, ","contentImg":"http://qjvorl4f2.hn-bkt.clouddn.com/20201125162104979119_1603545284104.jpg,http://qjvorl4f2.hn-bkt.clouddn.com/20201125162108269846_2020-10-27-20-44-21-10.jpg,http://qjvorl4f2.hn-bkt.clouddn.com/202011251621091764843_2020-10-25-22-44-58-52.jpg,http://qjvorl4f2.hn-bkt.clouddn.com/202011251621108604691.jpg","lng":null,"lat":null,"createTime":"2020-11-25 16:23:03","status":0,"viewNum":null,"isOpen":1,"imgType":21,"clickNum":null,"area":""},{"id":100,"userId":1031,"contentInfo":"","contentImg":"http://qjvorl4f2.hn-bkt.clouddn.com/20201125162142222612_1603545284104.jpg,http://qjvorl4f2.hn-bkt.clouddn.com/2020112516214582175.jpg","lng":null,"lat":null,"createTime":"2020-11-25 16:23:38","status":0,"viewNum":null,"isOpen":1,"imgType":2,"clickNum":null,"area":""},{"id":101,"userId":1031,"contentInfo":"","contentImg":"http://qjvorl4f2.hn-bkt.clouddn.com/202011251622099574366.mp4","lng":null,"lat":null,"createTime":"2020-11-25 16:24:01","status":0,"viewNum":null,"isOpen":1,"imgType":3,"clickNum":null,"area":""}]
         * pageNum : 1
         * pageSize : 15
         * size : 3
         * startRow : 1
         * endRow : 3
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
             * id : 99
             * userId : 1031
             * contentInfo : 摸哪具体问#热门话题3# @何梦洋0,何梦洋1,
             * contentImg : http://qjvorl4f2.hn-bkt.clouddn.com/20201125162104979119_1603545284104.jpg,http://qjvorl4f2.hn-bkt.clouddn.com/20201125162108269846_2020-10-27-20-44-21-10.jpg,http://qjvorl4f2.hn-bkt.clouddn.com/202011251621091764843_2020-10-25-22-44-58-52.jpg,http://qjvorl4f2.hn-bkt.clouddn.com/202011251621108604691.jpg
             * lng : null
             * lat : null
             * createTime : 2020-11-25 16:23:03
             * status : 0
             * viewNum : null
             * isOpen : 1
             * imgType : 21
             * clickNum : null
             * area :
             */

            private int id;
            private int userId;
            private String contentInfo;
            private String contentImg;
            private Double lng;
            private Double lat;
            private String createTime;
            private int status;
            private Object viewNum;
            private int isOpen;
            private int imgType;
            private Object clickNum;
            private String area;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public String getContentInfo() {
                return contentInfo;
            }

            public void setContentInfo(String contentInfo) {
                this.contentInfo = contentInfo;
            }

            public String getContentImg() {
                return contentImg;
            }

            public void setContentImg(String contentImg) {
                this.contentImg = contentImg;
            }

            public Double getLng() {
                return lng;
            }

            public void setLng(Double lng) {
                this.lng = lng;
            }

            public Double getLat() {
                return lat;
            }

            public void setLat(Double lat) {
                this.lat = lat;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public Object getViewNum() {
                return viewNum;
            }

            public void setViewNum(Object viewNum) {
                this.viewNum = viewNum;
            }

            public int getIsOpen() {
                return isOpen;
            }

            public void setIsOpen(int isOpen) {
                this.isOpen = isOpen;
            }

            public int getImgType() {
                return imgType;
            }

            public void setImgType(int imgType) {
                this.imgType = imgType;
            }

            public Object getClickNum() {
                return clickNum;
            }

            public void setClickNum(Object clickNum) {
                this.clickNum = clickNum;
            }

            public String getArea() {
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }
        }
    }
}
