package com.YiDian.RainBow.main.fragment.mine.bean;

import java.util.List;

public class ConsumeRecordBean {


    /**
     * msg : 查询成功
     * type : OK
     * object : {"pageInfo":{"total":384,"list":
     * [{"id":771,"userId":1030,"goldNum":3000,"recordContent":"给北方发送了礼物","recordType":2,"createTime":"2021-01-18 10:43:51"},
     * {"id":769,"userId":1030,"goldNum":500,"recordContent":"给北方发送了礼物","recordType":2,"createTime":"2021-01-18 10:42:28"},
     * {"id":767,"userId":1030,"goldNum":288888,"recordContent":"给北方发送了礼物","recordType":2,"createTime":"2021-01-16 17:23:12"},
     * {"id":761,"userId":1030,"goldNum":288888,"recordContent":"给北方发送了礼物","recordType":2,"createTime":"2021-01-16 17:23:08"},
     * {"id":763,"userId":1030,"goldNum":288888,"recordContent":"给北方发送了礼物","recordType":2,"createTime":"2021-01-16 17:23:08"},
     * {"id":765,"userId":1030,"goldNum":288888,"recordContent":"给北方发送了礼物","recordType":2,"createTime":"2021-01-16 17:23:08"},
     * {"id":753,"userId":1030,"goldNum":288888,"recordContent":"给北方发送了礼物","recordType":2,"createTime":"2021-01-16 17:23:07"},
     * {"id":755,"userId":1030,"goldNum":288888,"recordContent":"给北方发送了礼物","recordType":2,"createTime":"2021-01-16 17:23:07"},
     * {"id":757,"userId":1030,"goldNum":288888,"recordContent":"给北方发送了礼物","recordType":2,"createTime":"2021-01-16 17:23:07"},
     * {"id":759,"userId":1030,"goldNum":288888,"recordContent":"给北方发送了礼物","recordType":2,"createTime":"2021-01-16 17:23:07"},
     * {"id":745,"userId":1030,"goldNum":288888,"recordContent":"给北方发送了礼物","recordType":2,"createTime":"2021-01-16 17:23:06"},
     * {"id":747,"userId":1030,"goldNum":288888,"recordContent":"给北方发送了礼物","recordType":2,"createTime":"2021-01-16 17:23:06"},
     * {"id":749,"userId":1030,"goldNum":288888,"recordContent":"给北方发送了礼物","recordType":2,"createTime":"2021-01-16 17:23:06"},
     * {"id":751,"userId":1030,"goldNum":288888,"recordContent":"给北方发送了礼物","recordType":2,"createTime":"2021-01-16 17:23:06"},
     * {"id":733,"userId":1030,"goldNum":288888,"recordContent":"给北方发送了礼物","recordType":2,"createTime":"2021-01-16 17:23:05"}],
     * "pageNum":1,"pageSize":15,"size":15,"startRow":1,"endRow":15,"pages":26,"prePage":0,"nextPage":2,"isFirstPage":true,"
     * isLastPage":false,"hasPreviousPage":false,"hasNextPage":true,"navigatePages":8,"navigatepageNums":[1,2,3,4,5,6,7,8],
     * "navigateFirstPage":1,"navigateLastPage":8},"spendingGoldNum":61355326,"incomeGoldNum":2000,"goldNum":
     * {"id":6,"userId":1030,"goldNum":39513204,"usableNum":0,"goldUsable":1000000000,"goldAll":1039513204}}
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
         * pageInfo : {"total":384,"list":
         * [{"id":771,"userId":1030,"goldNum":3000,"recordContent":"给北方发送了礼物","recordType":2,"createTime":"2021-01-18 10:43:51"},
         * {"id":769,"userId":1030,"goldNum":500,"recordContent":"给北方发送了礼物","recordType":2,"createTime":"2021-01-18 10:42:28"},
         * {"id":767,"userId":1030,"goldNum":288888,"recordContent":"给北方发送了礼物","recordType":2,"createTime":"2021-01-16 17:23:12"},
         * {"id":761,"userId":1030,"goldNum":288888,"recordContent":"给北方发送了礼物","recordType":2,"createTime":"2021-01-16 17:23:08"},
         * {"id":763,"userId":1030,"goldNum":288888,"recordContent":"给北方发送了礼物","recordType":2,"createTime":"2021-01-16 17:23:08"},
         * {"id":765,"userId":1030,"goldNum":288888,"recordContent":"给北方发送了礼物","recordType":2,"createTime":"2021-01-16 17:23:08"},
         * {"id":753,"userId":1030,"goldNum":288888,"recordContent":"给北方发送了礼物","recordType":2,"createTime":"2021-01-16 17:23:07"},
         * {"id":755,"userId":1030,"goldNum":288888,"recordContent":"给北方发送了礼物","recordType":2,"createTime":"2021-01-16 17:23:07"},
         * {"id":757,"userId":1030,"goldNum":288888,"recordContent":"给北方发送了礼物","recordType":2,"createTime":"2021-01-16 17:23:07"},
         * {"id":759,"userId":1030,"goldNum":288888,"recordContent":"给北方发送了礼物","recordType":2,"createTime":"2021-01-16 17:23:07"},
         * {"id":745,"userId":1030,"goldNum":288888,"recordContent":"给北方发送了礼物","recordType":2,"createTime":"2021-01-16 17:23:06"},
         * {"id":747,"userId":1030,"goldNum":288888,"recordContent":"给北方发送了礼物","recordType":2,"createTime":"2021-01-16 17:23:06"},
         * {"id":749,"userId":1030,"goldNum":288888,"recordContent":"给北方发送了礼物","recordType":2,"createTime":"2021-01-16 17:23:06"},
         * {"id":751,"userId":1030,"goldNum":288888,"recordContent":"给北方发送了礼物","recordType":2,"createTime":"2021-01-16 17:23:06"},
         * {"id":733,"userId":1030,"goldNum":288888,"recordContent":"给北方发送了礼物","recordType":2,"createTime":"2021-01-16 17:23:05"}],
         * "pageNum":1,"pageSize":15,"size":15,"startRow":1,"endRow":15,"pages":26,"prePage":0,"nextPage":2,"isFirstPage":true,"isLastPage":false,"hasPreviousPage":false,"hasNextPage":true,"navigatePages":8,"navigatepageNums":[1,2,3,4,5,6,7,8],"navigateFirstPage":1,"navigateLastPage":8}
         * spendingGoldNum : 61355326
         * incomeGoldNum : 2000
         * goldNum : {"id":6,"userId":1030,"goldNum":39513204,"usableNum":0,"goldUsable":1000000000,"goldAll":1039513204}
         */

        private PageInfoBean pageInfo;
        private int spendingGoldNum;
        private int incomeGoldNum;
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

        public void setSpendingGoldNum(int spendingGoldNum) {
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
             * total : 384
             * list : [{"id":771,"userId":1030,"goldNum":3000,"recordContent":"给北方发送了礼物","recordType":2,"createTime":"2021-01-18 10:43:51"},{"id":769,"userId":1030,"goldNum":500,"recordContent":"给北方发送了礼物","recordType":2,"createTime":"2021-01-18 10:42:28"},{"id":767,"userId":1030,"goldNum":288888,"recordContent":"给北方发送了礼物","recordType":2,"createTime":"2021-01-16 17:23:12"},{"id":761,"userId":1030,"goldNum":288888,"recordContent":"给北方发送了礼物","recordType":2,"createTime":"2021-01-16 17:23:08"},{"id":763,"userId":1030,"goldNum":288888,"recordContent":"给北方发送了礼物","recordType":2,"createTime":"2021-01-16 17:23:08"},{"id":765,"userId":1030,"goldNum":288888,"recordContent":"给北方发送了礼物","recordType":2,"createTime":"2021-01-16 17:23:08"},{"id":753,"userId":1030,"goldNum":288888,"recordContent":"给北方发送了礼物","recordType":2,"createTime":"2021-01-16 17:23:07"},{"id":755,"userId":1030,"goldNum":288888,"recordContent":"给北方发送了礼物","recordType":2,"createTime":"2021-01-16 17:23:07"},{"id":757,"userId":1030,"goldNum":288888,"recordContent":"给北方发送了礼物","recordType":2,"createTime":"2021-01-16 17:23:07"},{"id":759,"userId":1030,"goldNum":288888,"recordContent":"给北方发送了礼物","recordType":2,"createTime":"2021-01-16 17:23:07"},{"id":745,"userId":1030,"goldNum":288888,"recordContent":"给北方发送了礼物","recordType":2,"createTime":"2021-01-16 17:23:06"},{"id":747,"userId":1030,"goldNum":288888,"recordContent":"给北方发送了礼物","recordType":2,"createTime":"2021-01-16 17:23:06"},{"id":749,"userId":1030,"goldNum":288888,"recordContent":"给北方发送了礼物","recordType":2,"createTime":"2021-01-16 17:23:06"},{"id":751,"userId":1030,"goldNum":288888,"recordContent":"给北方发送了礼物","recordType":2,"createTime":"2021-01-16 17:23:06"},{"id":733,"userId":1030,"goldNum":288888,"recordContent":"给北方发送了礼物","recordType":2,"createTime":"2021-01-16 17:23:05"}]
             * pageNum : 1
             * pageSize : 15
             * size : 15
             * startRow : 1
             * endRow : 15
             * pages : 26
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
                 * id : 771
                 * userId : 1030
                 * goldNum : 3000
                 * recordContent : 给北方发送了礼物
                 * recordType : 2
                 * createTime : 2021-01-18 10:43:51
                 */

                private int id;
                private int userId;
                private int goldNum;
                private String recordContent;
                private int recordType;
                private String createTime;

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

                public int getGoldNum() {
                    return goldNum;
                }

                public void setGoldNum(int goldNum) {
                    this.goldNum = goldNum;
                }

                public String getRecordContent() {
                    return recordContent;
                }

                public void setRecordContent(String recordContent) {
                    this.recordContent = recordContent;
                }

                public int getRecordType() {
                    return recordType;
                }

                public void setRecordType(int recordType) {
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
             * goldNum : 39513204
             * usableNum : 0
             * goldUsable : 1000000000
             * goldAll : 1039513204
             */

            private int id;
            private int userId;
            private int goldNum;
            private int usableNum;
            private int goldUsable;
            private int goldAll;

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

            public int getGoldNum() {
                return goldNum;
            }

            public void setGoldNum(int goldNum) {
                this.goldNum = goldNum;
            }

            public int getUsableNum() {
                return usableNum;
            }

            public void setUsableNum(int usableNum) {
                this.usableNum = usableNum;
            }

            public int getGoldUsable() {
                return goldUsable;
            }

            public void setGoldUsable(int goldUsable) {
                this.goldUsable = goldUsable;
            }

            public int getGoldAll() {
                return goldAll;
            }

            public void setGoldAll(int goldAll) {
                this.goldAll = goldAll;
            }
        }
    }
}
