package com.YiDian.RainBow.main.fragment.home.bean;

import java.util.List;

public class NewDynamicBean {

    /**
     * msg : getContentsByTime
     * type : OK
     * object : {"total":6,"list":[{"id":15,"userId":101,"contentInfo":"从大V那个iwerhgwerfowqpedoqwpe","contentImg":"https://media.w3.org/2010/05/sintel/trailer.mp4","lng":333,"lat":333,"createTime":"2020-09-28 11:15:49","status":1,"viewNum":null,"clickNum":null,"isOpen":1,"imgType":3},{"id":14,"userId":101,"contentInfo":"你是否加班收费金额花白头发淘宝网","contentImg":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1602150723010&di=0d0bc7a61eeaed1dd3b5eb3fa2c58700&imgtype=0&src=http%3A%2F%2Fa0.att.hudong.com%2F70%2F91%2F01300000261284122542917592865.jpg","lng":222,"lat":222,"createTime":"2020-09-28 10:21:08","status":1,"viewNum":null,"clickNum":null,"isOpen":1,"imgType":2},{"id":13,"userId":101,"contentInfo":"当要DAO层的方法参数是一个数组时，mapper.xml的parameter则为数组的类型，然后使用<foreach>标签进行遍历，collection属性值为\"array\"。  例如：public void deletes(Integer[] ids);(DAO层方法)    则对应的:\r\n\r\n<delete id=\"deleteBrands\" parameterType=\"Integer\">","contentImg":"","lng":1111,"lat":1111,"createTime":"2020-09-28 10:20:41","status":1,"viewNum":null,"clickNum":null,"isOpen":1,"imgType":1},{"id":19,"userId":101,"contentInfo":"how are you !","contentImg":"http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4","lng":114,"lat":444,"createTime":null,"status":1,"viewNum":null,"clickNum":null,"isOpen":1,"imgType":3},{"id":20,"userId":101,"contentInfo":"I am fine,thanks and you!","contentImg":" http://www.w3school.com.cn/example/html5/mov_bbb.mp4","lng":555,"lat":555,"createTime":null,"status":1,"viewNum":null,"clickNum":null,"isOpen":1,"imgType":3},{"id":21,"userId":101,"contentInfo":"Is fine day today.1!","contentImg":"https://media.w3.org/2010/05/sintel/trailer.mp4","lng":222,"lat":111,"createTime":null,"status":1,"viewNum":null,"clickNum":null,"isOpen":1,"imgType":3}],"pageNum":1,"pageSize":10,"size":6,"startRow":1,"endRow":6,"pages":1,"prePage":0,"nextPage":0,"isFirstPage":true,"isLastPage":true,"hasPreviousPage":false,"hasNextPage":false,"navigatePages":8,"navigatepageNums":[1],"navigateFirstPage":1,"navigateLastPage":1}
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
         * total : 6
         * list : [{"id":15,"userId":101,"contentInfo":"从大V那个iwerhgwerfowqpedoqwpe","contentImg":"https://media.w3.org/2010/05/sintel/trailer.mp4","lng":333,"lat":333,"createTime":"2020-09-28 11:15:49","status":1,"viewNum":null,"clickNum":null,"isOpen":1,"imgType":3},{"id":14,"userId":101,"contentInfo":"你是否加班收费金额花白头发淘宝网","contentImg":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1602150723010&di=0d0bc7a61eeaed1dd3b5eb3fa2c58700&imgtype=0&src=http%3A%2F%2Fa0.att.hudong.com%2F70%2F91%2F01300000261284122542917592865.jpg","lng":222,"lat":222,"createTime":"2020-09-28 10:21:08","status":1,"viewNum":null,"clickNum":null,"isOpen":1,"imgType":2},{"id":13,"userId":101,"contentInfo":"当要DAO层的方法参数是一个数组时，mapper.xml的parameter则为数组的类型，然后使用<foreach>标签进行遍历，collection属性值为\"array\"。  例如：public void deletes(Integer[] ids);(DAO层方法)    则对应的:\r\n\r\n<delete id=\"deleteBrands\" parameterType=\"Integer\">","contentImg":"","lng":1111,"lat":1111,"createTime":"2020-09-28 10:20:41","status":1,"viewNum":null,"clickNum":null,"isOpen":1,"imgType":1},{"id":19,"userId":101,"contentInfo":"how are you !","contentImg":"http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4","lng":114,"lat":444,"createTime":null,"status":1,"viewNum":null,"clickNum":null,"isOpen":1,"imgType":3},{"id":20,"userId":101,"contentInfo":"I am fine,thanks and you!","contentImg":" http://www.w3school.com.cn/example/html5/mov_bbb.mp4","lng":555,"lat":555,"createTime":null,"status":1,"viewNum":null,"clickNum":null,"isOpen":1,"imgType":3},{"id":21,"userId":101,"contentInfo":"Is fine day today.1!","contentImg":"https://media.w3.org/2010/05/sintel/trailer.mp4","lng":222,"lat":111,"createTime":null,"status":1,"viewNum":null,"clickNum":null,"isOpen":1,"imgType":3}]
         * pageNum : 1
         * pageSize : 10
         * size : 6
         * startRow : 1
         * endRow : 6
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
             * id : 15
             * userId : 101
             * contentInfo : 从大V那个iwerhgwerfowqpedoqwpe
             * contentImg : https://media.w3.org/2010/05/sintel/trailer.mp4
             * lng : 333.0
             * lat : 333.0
             * createTime : 2020-09-28 11:15:49
             * status : 1
             * viewNum : null
             * clickNum : null
             * isOpen : 1
             * imgType : 3
             */

            private int id;
            private int userId;
            private String contentInfo;
            private String contentImg;
            private double lng;
            private double lat;
            private String createTime;
            private int status;
            private Object viewNum;
            private Object clickNum;
            private int isOpen;
            private int imgType;

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

            public double getLng() {
                return lng;
            }

            public void setLng(double lng) {
                this.lng = lng;
            }

            public double getLat() {
                return lat;
            }

            public void setLat(double lat) {
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

            public Object getClickNum() {
                return clickNum;
            }

            public void setClickNum(Object clickNum) {
                this.clickNum = clickNum;
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
        }
    }
}
