package com.YiDian.RainBow.main.fragment.find.bean;

import java.util.List;

public class NearPersonBean {

    /**
     * msg : 查询成功
     * type : OK
     * object : {"total":12,"list":[{"id":1060,"phoneNum":null,"nickName":"头像测试","headImg":"http://img.rianbow.cn/202012091203537752626.jpg","backImg":null,"explains":"喔、卟説·~伱\u2018*卟懂\u2019\u2018！","birthday":null,"userType":null,"userRole":"保密","isSingle":null,"lng":null,"lat":null,"ratio":null,"attestation":null,"createTime":null,"isFans":0,"countNum":null,"distance":14958,"distancing":null,"age":null,"invitationCode":null},{"id":11,"phoneNum":null,"nickName":"赵柳","headImg":"http://img.rianbow.cn/202012091309042277711.jpg","backImg":null,"explains":"老顽童","birthday":null,"userType":null,"userRole":"保密","isSingle":null,"lng":null,"lat":null,"ratio":null,"attestation":null,"createTime":null,"isFans":0,"countNum":null,"distance":15586,"distancing":null,"age":null,"invitationCode":null},{"id":2,"phoneNum":null,"nickName":"张三","headImg":"http://img.rianbow.cn/202012091308475961960.jpg","backImg":null,"explains":"勇敢的去爱","birthday":null,"userType":null,"userRole":"T","isSingle":null,"lng":null,"lat":null,"ratio":null,"attestation":null,"createTime":null,"isFans":1,"countNum":null,"distance":15963,"distancing":null,"age":null,"invitationCode":null},{"id":1059,"phoneNum":null,"nickName":"11","headImg":"http://img.rianbow.cn/20201209120030244035.jpg","backImg":null,"explains":"喔、卟説·~伱\u2018*卟懂\u2019\u2018！","birthday":null,"userType":null,"userRole":"T","isSingle":null,"lng":null,"lat":null,"ratio":null,"attestation":null,"createTime":null,"isFans":0,"countNum":null,"distance":18858,"distancing":null,"age":null,"invitationCode":null},{"id":1058,"phoneNum":null,"nickName":"kk","headImg":"http://img.rianbow.cn/202012091200596933259.jpg","backImg":null,"explains":"喔、卟説·~伱\u2018*卟懂\u2019\u2018！","birthday":null,"userType":null,"userRole":"T","isSingle":null,"lng":null,"lat":null,"ratio":null,"attestation":null,"createTime":null,"isFans":0,"countNum":null,"distance":27561,"distancing":null,"age":null,"invitationCode":null},{"id":1065,"phoneNum":null,"nickName":"但如果你不","headImg":"http://img.rianbow.cn/202012091311039645114.jpg","backImg":null,"explains":"喔、卟説·~伱\u2018*卟懂\u2019\u2018！","birthday":null,"userType":null,"userRole":"BI","isSingle":null,"lng":null,"lat":null,"ratio":null,"attestation":null,"createTime":null,"isFans":0,"countNum":null,"distance":29003,"distancing":null,"age":null,"invitationCode":null},{"id":1057,"phoneNum":null,"nickName":"hh","headImg":"http://img.rianbow.cn/202012091201129544124.jpg","backImg":null,"explains":"喔、卟説·~伱\u2018*卟懂\u2019\u2018！","birthday":null,"userType":null,"userRole":"P","isSingle":null,"lng":null,"lat":null,"ratio":null,"attestation":null,"createTime":null,"isFans":0,"countNum":null,"distance":30474,"distancing":null,"age":null,"invitationCode":null},{"id":1037,"phoneNum":null,"nickName":"获取QQQQ昵称","headImg":"http://img.rianbow.cn/202012091312357284973.jpg","backImg":null,"explains":"喔、卟説·~伱\u2018*卟懂\u2019\u2018！","birthday":null,"userType":null,"userRole":"BI","isSingle":null,"lng":null,"lat":null,"ratio":null,"attestation":null,"createTime":null,"isFans":0,"countNum":null,"distance":34403,"distancing":null,"age":null,"invitationCode":null},{"id":1031,"phoneNum":null,"nickName":"李四","headImg":"http://img.rianbow.cn/202012091309325288188.jpg","backImg":null,"explains":"得到的","birthday":null,"userType":null,"userRole":"T","isSingle":null,"lng":null,"lat":null,"ratio":null,"attestation":null,"createTime":null,"isFans":1,"countNum":null,"distance":37108,"distancing":null,"age":null,"invitationCode":null},{"id":1056,"phoneNum":null,"nickName":"广告","headImg":"http://img.rianbow.cn/202012091201239282597.jpg","backImg":null,"explains":"喔、卟説·~伱\u2018*卟懂\u2019\u2018！","birthday":null,"userType":null,"userRole":"P","isSingle":null,"lng":null,"lat":null,"ratio":null,"attestation":null,"createTime":null,"isFans":0,"countNum":null,"distance":41152,"distancing":null,"age":null,"invitationCode":null},{"id":1062,"phoneNum":null,"nickName":"色弱GV","headImg":"http://img.rianbow.cn/202012091311039645114.jpg","backImg":null,"explains":"喔、卟説·~伱\u2018*卟懂\u2019\u2018！","birthday":null,"userType":null,"userRole":"BI","isSingle":null,"lng":null,"lat":null,"ratio":null,"attestation":null,"createTime":null,"isFans":0,"countNum":null,"distance":47978,"distancing":null,"age":null,"invitationCode":null},{"id":1032,"phoneNum":null,"nickName":"hhhaaa","headImg":"http://img.rianbow.cn/20201209130924997312.jpg","backImg":null,"explains":"喔、卟説·~伱\u2018*卟懂\u2019\u2018！","birthday":null,"userType":null,"userRole":"保密","isSingle":null,"lng":null,"lat":null,"ratio":null,"attestation":null,"createTime":null,"isFans":0,"countNum":null,"distance":49334,"distancing":null,"age":null,"invitationCode":null}],"pageNum":1,"pageSize":15,"size":12,"startRow":1,"endRow":12,"pages":1,"prePage":0,"nextPage":0,"isFirstPage":true,"isLastPage":true,"hasPreviousPage":false,"hasNextPage":false,"navigatePages":8,"navigatepageNums":[1],"navigateFirstPage":1,"navigateLastPage":1}
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
         * total : 12
         * list : [{"id":1060,"phoneNum":null,"nickName":"头像测试","headImg":"http://img.rianbow.cn/202012091203537752626.jpg","backImg":null,"explains":"喔、卟説·~伱\u2018*卟懂\u2019\u2018！","birthday":null,"userType":null,"userRole":"保密","isSingle":null,"lng":null,"lat":null,"ratio":null,"attestation":null,"createTime":null,"isFans":0,"countNum":null,"distance":14958,"distancing":null,"age":null,"invitationCode":null},{"id":11,"phoneNum":null,"nickName":"赵柳","headImg":"http://img.rianbow.cn/202012091309042277711.jpg","backImg":null,"explains":"老顽童","birthday":null,"userType":null,"userRole":"保密","isSingle":null,"lng":null,"lat":null,"ratio":null,"attestation":null,"createTime":null,"isFans":0,"countNum":null,"distance":15586,"distancing":null,"age":null,"invitationCode":null},{"id":2,"phoneNum":null,"nickName":"张三","headImg":"http://img.rianbow.cn/202012091308475961960.jpg","backImg":null,"explains":"勇敢的去爱","birthday":null,"userType":null,"userRole":"T","isSingle":null,"lng":null,"lat":null,"ratio":null,"attestation":null,"createTime":null,"isFans":1,"countNum":null,"distance":15963,"distancing":null,"age":null,"invitationCode":null},{"id":1059,"phoneNum":null,"nickName":"11","headImg":"http://img.rianbow.cn/20201209120030244035.jpg","backImg":null,"explains":"喔、卟説·~伱\u2018*卟懂\u2019\u2018！","birthday":null,"userType":null,"userRole":"T","isSingle":null,"lng":null,"lat":null,"ratio":null,"attestation":null,"createTime":null,"isFans":0,"countNum":null,"distance":18858,"distancing":null,"age":null,"invitationCode":null},{"id":1058,"phoneNum":null,"nickName":"kk","headImg":"http://img.rianbow.cn/202012091200596933259.jpg","backImg":null,"explains":"喔、卟説·~伱\u2018*卟懂\u2019\u2018！","birthday":null,"userType":null,"userRole":"T","isSingle":null,"lng":null,"lat":null,"ratio":null,"attestation":null,"createTime":null,"isFans":0,"countNum":null,"distance":27561,"distancing":null,"age":null,"invitationCode":null},{"id":1065,"phoneNum":null,"nickName":"但如果你不","headImg":"http://img.rianbow.cn/202012091311039645114.jpg","backImg":null,"explains":"喔、卟説·~伱\u2018*卟懂\u2019\u2018！","birthday":null,"userType":null,"userRole":"BI","isSingle":null,"lng":null,"lat":null,"ratio":null,"attestation":null,"createTime":null,"isFans":0,"countNum":null,"distance":29003,"distancing":null,"age":null,"invitationCode":null},{"id":1057,"phoneNum":null,"nickName":"hh","headImg":"http://img.rianbow.cn/202012091201129544124.jpg","backImg":null,"explains":"喔、卟説·~伱\u2018*卟懂\u2019\u2018！","birthday":null,"userType":null,"userRole":"P","isSingle":null,"lng":null,"lat":null,"ratio":null,"attestation":null,"createTime":null,"isFans":0,"countNum":null,"distance":30474,"distancing":null,"age":null,"invitationCode":null},{"id":1037,"phoneNum":null,"nickName":"获取QQQQ昵称","headImg":"http://img.rianbow.cn/202012091312357284973.jpg","backImg":null,"explains":"喔、卟説·~伱\u2018*卟懂\u2019\u2018！","birthday":null,"userType":null,"userRole":"BI","isSingle":null,"lng":null,"lat":null,"ratio":null,"attestation":null,"createTime":null,"isFans":0,"countNum":null,"distance":34403,"distancing":null,"age":null,"invitationCode":null},{"id":1031,"phoneNum":null,"nickName":"李四","headImg":"http://img.rianbow.cn/202012091309325288188.jpg","backImg":null,"explains":"得到的","birthday":null,"userType":null,"userRole":"T","isSingle":null,"lng":null,"lat":null,"ratio":null,"attestation":null,"createTime":null,"isFans":1,"countNum":null,"distance":37108,"distancing":null,"age":null,"invitationCode":null},{"id":1056,"phoneNum":null,"nickName":"广告","headImg":"http://img.rianbow.cn/202012091201239282597.jpg","backImg":null,"explains":"喔、卟説·~伱\u2018*卟懂\u2019\u2018！","birthday":null,"userType":null,"userRole":"P","isSingle":null,"lng":null,"lat":null,"ratio":null,"attestation":null,"createTime":null,"isFans":0,"countNum":null,"distance":41152,"distancing":null,"age":null,"invitationCode":null},{"id":1062,"phoneNum":null,"nickName":"色弱GV","headImg":"http://img.rianbow.cn/202012091311039645114.jpg","backImg":null,"explains":"喔、卟説·~伱\u2018*卟懂\u2019\u2018！","birthday":null,"userType":null,"userRole":"BI","isSingle":null,"lng":null,"lat":null,"ratio":null,"attestation":null,"createTime":null,"isFans":0,"countNum":null,"distance":47978,"distancing":null,"age":null,"invitationCode":null},{"id":1032,"phoneNum":null,"nickName":"hhhaaa","headImg":"http://img.rianbow.cn/20201209130924997312.jpg","backImg":null,"explains":"喔、卟説·~伱\u2018*卟懂\u2019\u2018！","birthday":null,"userType":null,"userRole":"保密","isSingle":null,"lng":null,"lat":null,"ratio":null,"attestation":null,"createTime":null,"isFans":0,"countNum":null,"distance":49334,"distancing":null,"age":null,"invitationCode":null}]
         * pageNum : 1
         * pageSize : 15
         * size : 12
         * startRow : 1
         * endRow : 12
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
             * id : 1060
             * phoneNum : null
             * nickName : 头像测试
             * headImg : http://img.rianbow.cn/202012091203537752626.jpg
             * backImg : null
             * explains : 喔、卟説·~伱‘*卟懂’‘！
             * birthday : null
             * userType : null
             * userRole : 保密
             * isSingle : null
             * lng : null
             * lat : null
             * ratio : null
             * attestation : null
             * createTime : null
             * isFans : 0
             * countNum : null
             * distance : 14958
             * distancing : null
             * age : null
             * invitationCode : null
             */

            private int id;
            private Object phoneNum;
            private String nickName;
            private String headImg;
            private Object backImg;
            private String explains;
            private Object birthday;
            private Object userType;
            private String userRole;
            private Object isSingle;
            private Object lng;
            private Object lat;
            private Object ratio;
            private Object attestation;
            private Object createTime;
            private int isFans;
            private Object countNum;
            private int distance;
            private Object distancing;
            private Object age;
            private Object invitationCode;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public Object getPhoneNum() {
                return phoneNum;
            }

            public void setPhoneNum(Object phoneNum) {
                this.phoneNum = phoneNum;
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

            public Object getBackImg() {
                return backImg;
            }

            public void setBackImg(Object backImg) {
                this.backImg = backImg;
            }

            public String getExplains() {
                return explains;
            }

            public void setExplains(String explains) {
                this.explains = explains;
            }

            public Object getBirthday() {
                return birthday;
            }

            public void setBirthday(Object birthday) {
                this.birthday = birthday;
            }

            public Object getUserType() {
                return userType;
            }

            public void setUserType(Object userType) {
                this.userType = userType;
            }

            public String getUserRole() {
                return userRole;
            }

            public void setUserRole(String userRole) {
                this.userRole = userRole;
            }

            public Object getIsSingle() {
                return isSingle;
            }

            public void setIsSingle(Object isSingle) {
                this.isSingle = isSingle;
            }

            public Object getLng() {
                return lng;
            }

            public void setLng(Object lng) {
                this.lng = lng;
            }

            public Object getLat() {
                return lat;
            }

            public void setLat(Object lat) {
                this.lat = lat;
            }

            public Object getRatio() {
                return ratio;
            }

            public void setRatio(Object ratio) {
                this.ratio = ratio;
            }

            public Object getAttestation() {
                return attestation;
            }

            public void setAttestation(Object attestation) {
                this.attestation = attestation;
            }

            public Object getCreateTime() {
                return createTime;
            }

            public void setCreateTime(Object createTime) {
                this.createTime = createTime;
            }

            public int getIsFans() {
                return isFans;
            }

            public void setIsFans(int isFans) {
                this.isFans = isFans;
            }

            public Object getCountNum() {
                return countNum;
            }

            public void setCountNum(Object countNum) {
                this.countNum = countNum;
            }

            public int getDistance() {
                return distance;
            }

            public void setDistance(int distance) {
                this.distance = distance;
            }

            public Object getDistancing() {
                return distancing;
            }

            public void setDistancing(Object distancing) {
                this.distancing = distancing;
            }

            public Object getAge() {
                return age;
            }

            public void setAge(Object age) {
                this.age = age;
            }

            public Object getInvitationCode() {
                return invitationCode;
            }

            public void setInvitationCode(Object invitationCode) {
                this.invitationCode = invitationCode;
            }
        }
    }
}
