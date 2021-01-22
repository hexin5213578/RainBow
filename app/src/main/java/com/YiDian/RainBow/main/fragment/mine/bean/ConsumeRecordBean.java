package com.YiDian.RainBow.main.fragment.mine.bean;

import java.util.List;

public class ConsumeRecordBean {


    /**
     * msg : 查询成功
     * type : OK
     * object : {"pageInfo":{"total":396,"list":[{"id":784,"userId":1030,"goldNum":13,"recordContent":"此次签到或补签奖励","recordType":1,"createTime":"2021-01-21 13:02:46"},{"id":785,"userId":1030,"goldNum":4,"recordContent":"本次补签所花费金币","recordType":2,"createTime":"2021-01-21 13:02:46"},{"id":782,"userId":1030,"goldNum":11,"recordContent":"此次签到或补签奖励","recordType":1,"createTime":"2021-01-21 10:53:37"},{"id":783,"userId":1030,"goldNum":3,"recordContent":"本次补签所花费金币","recordType":2,"createTime":"2021-01-21 10:53:37"},{"id":780,"userId":1030,"goldNum":9,"recordContent":"此次签到或补签奖励","recordType":1,"createTime":"2021-01-21 10:53:31"},{"id":781,"userId":1030,"goldNum":2,"recordContent":"本次补签所花费金币","recordType":2,"createTime":"2021-01-21 10:53:31"},{"id":779,"userId":1030,"goldNum":5,"recordContent":"此次签到或补签奖励","recordType":1,"createTime":"2021-01-21 10:53:23"},{"id":778,"userId":1030,"goldNum":19,"recordContent":"此次签到或补签奖励","recordType":1,"createTime":"2021-01-21 09:38:48"},{"id":774,"userId":1030,"goldNum":10,"recordContent":"您和您的情侣都已经签到,可以去任务中领取哦","recordType":1,"createTime":"2021-01-20 17:02:23"},{"id":775,"userId":1030,"goldNum":10,"recordContent":"实名认证完成获取金币10个","recordType":1,"createTime":"2021-01-20 17:02:23"}],"pageNum":1,"pageSize":10,"size":10,"startRow":1,"endRow":10,"pages":40,"prePage":0,"nextPage":2,"isFirstPage":true,"isLastPage":false,"hasPreviousPage":false,"hasNextPage":true,"navigatePages":8,"navigatepageNums":[1,2,3,4,5,6,7,8],"navigateFirstPage":1,"navigateLastPage":8},"spendingGoldNum":61355335,"incomeGoldNum":2097,"goldNum":{"id":6,"userId":1030,"goldNum":39513272,"usableNum":0,"goldUsable":1000000000,"goldAll":1039513272}}
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
         * pageInfo : {"total":396,"list":[{"id":784,"userId":1030,"goldNum":13,"recordContent":"此次签到或补签奖励","recordType":1,"createTime":"2021-01-21 13:02:46"},{"id":785,"userId":1030,"goldNum":4,"recordContent":"本次补签所花费金币","recordType":2,"createTime":"2021-01-21 13:02:46"},{"id":782,"userId":1030,"goldNum":11,"recordContent":"此次签到或补签奖励","recordType":1,"createTime":"2021-01-21 10:53:37"},{"id":783,"userId":1030,"goldNum":3,"recordContent":"本次补签所花费金币","recordType":2,"createTime":"2021-01-21 10:53:37"},{"id":780,"userId":1030,"goldNum":9,"recordContent":"此次签到或补签奖励","recordType":1,"createTime":"2021-01-21 10:53:31"},{"id":781,"userId":1030,"goldNum":2,"recordContent":"本次补签所花费金币","recordType":2,"createTime":"2021-01-21 10:53:31"},{"id":779,"userId":1030,"goldNum":5,"recordContent":"此次签到或补签奖励","recordType":1,"createTime":"2021-01-21 10:53:23"},{"id":778,"userId":1030,"goldNum":19,"recordContent":"此次签到或补签奖励","recordType":1,"createTime":"2021-01-21 09:38:48"},{"id":774,"userId":1030,"goldNum":10,"recordContent":"您和您的情侣都已经签到,可以去任务中领取哦","recordType":1,"createTime":"2021-01-20 17:02:23"},{"id":775,"userId":1030,"goldNum":10,"recordContent":"实名认证完成获取金币10个","recordType":1,"createTime":"2021-01-20 17:02:23"}],"pageNum":1,"pageSize":10,"size":10,"startRow":1,"endRow":10,"pages":40,"prePage":0,"nextPage":2,"isFirstPage":true,"isLastPage":false,"hasPreviousPage":false,"hasNextPage":true,"navigatePages":8,"navigatepageNums":[1,2,3,4,5,6,7,8],"navigateFirstPage":1,"navigateLastPage":8}
         * spendingGoldNum : 61355335
         * incomeGoldNum : 2097
         * goldNum : {"id":6,"userId":1030,"goldNum":39513272,"usableNum":0,"goldUsable":1000000000,"goldAll":1039513272}
         */

        private PageInfoBean pageInfo;
        private Integer spendingGoldNum;
        private Integer incomeGoldNum;
        private GoldNumBean goldNum;

        public PageInfoBean getPageInfo() {
            return pageInfo;
        }

        public void setPageInfo(PageInfoBean pageInfo) {
            this.pageInfo = pageInfo;
        }

        public int getSpendingGoldNum() {
            return spendingGoldNum;
        }

        public void setSpendingGoldNum(Integer spendingGoldNum) {
            this.spendingGoldNum = spendingGoldNum;
        }

        public int getIncomeGoldNum() {
            return incomeGoldNum;
        }

        public void setIncomeGoldNum(int incomeGoldNum) {
            this.incomeGoldNum = incomeGoldNum;
        }

        public GoldNumBean getGoldNum() {
            return goldNum;
        }

        public void setGoldNum(GoldNumBean goldNum) {
            this.goldNum = goldNum;
        }

        public static class PageInfoBean {
            /**
             * total : 396
             * list : [{"id":784,"userId":1030,"goldNum":13,"recordContent":"此次签到或补签奖励","recordType":1,"createTime":"2021-01-21 13:02:46"},{"id":785,"userId":1030,"goldNum":4,"recordContent":"本次补签所花费金币","recordType":2,"createTime":"2021-01-21 13:02:46"},{"id":782,"userId":1030,"goldNum":11,"recordContent":"此次签到或补签奖励","recordType":1,"createTime":"2021-01-21 10:53:37"},{"id":783,"userId":1030,"goldNum":3,"recordContent":"本次补签所花费金币","recordType":2,"createTime":"2021-01-21 10:53:37"},{"id":780,"userId":1030,"goldNum":9,"recordContent":"此次签到或补签奖励","recordType":1,"createTime":"2021-01-21 10:53:31"},{"id":781,"userId":1030,"goldNum":2,"recordContent":"本次补签所花费金币","recordType":2,"createTime":"2021-01-21 10:53:31"},{"id":779,"userId":1030,"goldNum":5,"recordContent":"此次签到或补签奖励","recordType":1,"createTime":"2021-01-21 10:53:23"},{"id":778,"userId":1030,"goldNum":19,"recordContent":"此次签到或补签奖励","recordType":1,"createTime":"2021-01-21 09:38:48"},{"id":774,"userId":1030,"goldNum":10,"recordContent":"您和您的情侣都已经签到,可以去任务中领取哦","recordType":1,"createTime":"2021-01-20 17:02:23"},{"id":775,"userId":1030,"goldNum":10,"recordContent":"实名认证完成获取金币10个","recordType":1,"createTime":"2021-01-20 17:02:23"}]
             * pageNum : 1
             * pageSize : 10
             * size : 10
             * startRow : 1
             * endRow : 10
             * pages : 40
             * prePage : 0
             * nextPage : 2
             * isFirstPage : true
             * isLastPage : false
             * hasPreviousPage : false
             * hasNextPage : true
             * navigatePages : 8
             * navigatepageNums : [1,2,3,4,5,6,7,8]
             * navigateFirstPage : 1
             * navigateLastPage : 8
             */

            private Integer total;
            private Integer pageNum;
            private Integer pageSize;
            private Integer size;
            private Integer startRow;
            private Integer endRow;
            private Integer pages;
            private Integer prePage;
            private Integer nextPage;
            private boolean isFirstPage;
            private boolean isLastPage;
            private boolean hasPreviousPage;
            private boolean hasNextPage;
            private Integer navigatePages;
            private Integer navigateFirstPage;
            private Integer navigateLastPage;
            private List<ListBean> list;
            private List<Integer> navigatepageNums;

            public int getTotal() {
                return total;
            }

            public void setTotal(Integer total) {
                this.total = total;
            }

            public int getPageNum() {
                return pageNum;
            }

            public void setPageNum(Integer pageNum) {
                this.pageNum = pageNum;
            }

            public int getPageSize() {
                return pageSize;
            }

            public void setPageSize(Integer pageSize) {
                this.pageSize = pageSize;
            }

            public Integer getSize() {
                return size;
            }

            public void setSize(Integer size) {
                this.size = size;
            }

            public Integer getStartRow() {
                return startRow;
            }

            public void setStartRow(Integer startRow) {
                this.startRow = startRow;
            }

            public Integer getEndRow() {
                return endRow;
            }

            public void setEndRow(Integer endRow) {
                this.endRow = endRow;
            }

            public Integer getPages() {
                return pages;
            }

            public void setPages(Integer pages) {
                this.pages = pages;
            }

            public Integer getPrePage() {
                return prePage;
            }

            public void setPrePage(Integer prePage) {
                this.prePage = prePage;
            }

            public Integer getNextPage() {
                return nextPage;
            }

            public void setNextPage(Integer nextPage) {
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

            public Integer getNavigatePages() {
                return navigatePages;
            }

            public void setNavigatePages(Integer navigatePages) {
                this.navigatePages = navigatePages;
            }

            public Integer getNavigateFirstPage() {
                return navigateFirstPage;
            }

            public void setNavigateFirstPage(Integer navigateFirstPage) {
                this.navigateFirstPage = navigateFirstPage;
            }

            public Integer getNavigateLastPage() {
                return navigateLastPage;
            }

            public void setNavigateLastPage(Integer navigateLastPage) {
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
                 * id : 784
                 * userId : 1030
                 * goldNum : 13
                 * recordContent : 此次签到或补签奖励
                 * recordType : 1
                 * createTime : 2021-01-21 13:02:46
                 */

                private Integer id;
                private Integer userId;
                private Integer goldNum;
                private String recordContent;
                private Integer recordType;
                private String createTime;

                public Integer getId() {
                    return id;
                }

                public void setId(Integer id) {
                    this.id = id;
                }

                public Integer getUserId() {
                    return userId;
                }

                public void setUserId(Integer userId) {
                    this.userId = userId;
                }

                public Integer getGoldNum() {
                    return goldNum;
                }

                public void setGoldNum(Integer goldNum) {
                    this.goldNum = goldNum;
                }

                public String getRecordContent() {
                    return recordContent;
                }

                public void setRecordContent(String recordContent) {
                    this.recordContent = recordContent;
                }

                public Integer getRecordType() {
                    return recordType;
                }

                public void setRecordType(Integer recordType) {
                    this.recordType = recordType;
                }

                public String getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(String createTime) {
                    this.createTime = createTime;
                }
            }
        }

        public static class GoldNumBean {
            /**
             * id : 6
             * userId : 1030
             * goldNum : 39513272
             * usableNum : 0
             * goldUsable : 1000000000
             * goldAll : 1039513272
             */

            private Integer id;
            private Integer userId;
            private Integer goldNum;
            private Integer usableNum;
            private Integer goldUsable;
            private Integer goldAll;

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public Integer getUserId() {
                return userId;
            }

            public void setUserId(Integer userId) {
                this.userId = userId;
            }

            public Integer getGoldNum() {
                return goldNum;
            }

            public void setGoldNum(Integer goldNum) {
                this.goldNum = goldNum;
            }

            public Integer getUsableNum() {
                return usableNum;
            }

            public void setUsableNum(Integer usableNum) {
                this.usableNum = usableNum;
            }

            public Integer getGoldUsable() {
                return goldUsable;
            }

            public void setGoldUsable(Integer goldUsable) {
                this.goldUsable = goldUsable;
            }

            public Integer getGoldAll() {
                return goldAll;
            }

            public void setGoldAll(Integer goldAll) {
                this.goldAll = goldAll;
            }
        }
    }
}
