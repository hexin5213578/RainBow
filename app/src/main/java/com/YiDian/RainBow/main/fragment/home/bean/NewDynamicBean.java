package com.YiDian.RainBow.main.fragment.home.bean;

import java.util.List;

public class NewDynamicBean {

    /**
     * msg : getContentsByTime
     * type : OK
     * object : {"total":3,"list":[{"id":185,"userId":1031,"contentInfo":"","contentImg":"http://img.rianbow.cn/26f1fb1f20201214101225.jpg, http://img.rianbow.cn/80aaadcf20201214101225.jpg, http://img.rianbow.cn/496b81be20201214101225.jpg, http://img.rianbow.cn/9c6b630420201214101225.jpg, http://img.rianbow.cn/a677503e20201214101225.jpg, http://img.rianbow.cn/3e07843620201214101225.jpg, http://img.rianbow.cn/093e523320201214101225.jpg, http://img.rianbow.cn/ccff07ca20201214101225.jpg, http://img.rianbow.cn/020a256620201214101225.jpg","lng":null,"lat":null,"createTime":"2020-12-14 10:12:27","status":1,"viewNum":null,"isOpen":1,"imgType":2,"isAttention":false,"isCollect":false,"isClick":false,"clickNum":0,"topics":[],"userInfo":{"id":1031,"phoneNum":null,"nickName":"李四","headImg":"http://img.rianbow.cn/202012091309325288188.jpg","age":null,"explains":null,"birthday":null,"userType":null,"userRole":"T","isSingle":null,"lng":115.54894616,"lat":116.151641641,"ratio":null,"attestation":1,"createTime":null,"countNum":null,"distance":null,"distancing":null,"invitationCode":null},"commentCount":0,"distance":null},{"id":184,"userId":1031,"contentInfo":"我还没额头哦星眸","contentImg":"http://img.rianbow.cn/26f1fb1f20201214100307.jpg, http://img.rianbow.cn/80aaadcf20201214100307.jpg, http://img.rianbow.cn/3e07843620201214100307.jpg, http://img.rianbow.cn/ccff07ca20201214100307.jpg, http://img.rianbow.cn/3aeca1ff20201214100307.jpg","lng":null,"lat":null,"createTime":"2020-12-14 10:03:05","status":1,"viewNum":null,"isOpen":1,"imgType":21,"isAttention":false,"isCollect":false,"isClick":false,"clickNum":0,"topics":[],"userInfo":{"id":1031,"phoneNum":null,"nickName":"李四","headImg":"http://img.rianbow.cn/202012091309325288188.jpg","age":null,"explains":null,"birthday":null,"userType":null,"userRole":"T","isSingle":null,"lng":115.54894616,"lat":116.151641641,"ratio":null,"attestation":1,"createTime":null,"countNum":null,"distance":null,"distancing":null,"invitationCode":null},"commentCount":0,"distance":null},{"id":182,"userId":1030,"contentInfo":"爱上一个不服老司机良好","contentImg":"http://img.rianbow.cn/202012140954477472859.jpg,http://img.rianbow.cn/202012140954477473803.jpg,http://img.rianbow.cn/202012140954477479967.jpg","lng":123,"lat":123,"createTime":"2020-12-14 09:54:52","status":1,"viewNum":null,"isOpen":1,"imgType":21,"isAttention":false,"isCollect":false,"isClick":false,"clickNum":0,"topics":[],"userInfo":{"id":1030,"phoneNum":null,"nickName":"何梦阳","headImg":"http://img.rianbow.cn/202012091309129337159.jpg","age":null,"explains":null,"birthday":null,"userType":null,"userRole":"保密","isSingle":null,"lng":115.298833,"lat":34.097004,"ratio":null,"attestation":1,"createTime":null,"countNum":null,"distance":null,"distancing":null,"invitationCode":null},"commentCount":0,"distance":9859639.71113922}],"pageNum":1,"pageSize":3,"size":3,"startRow":0,"endRow":2,"pages":1,"prePage":0,"nextPage":0,"isFirstPage":true,"isLastPage":true,"hasPreviousPage":false,"hasNextPage":false,"navigatePages":8,"navigatepageNums":[1],"navigateFirstPage":1,"navigateLastPage":1}
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
         * list : [{"id":185,"userId":1031,"contentInfo":"","contentImg":"http://img.rianbow.cn/26f1fb1f20201214101225.jpg, http://img.rianbow.cn/80aaadcf20201214101225.jpg, http://img.rianbow.cn/496b81be20201214101225.jpg, http://img.rianbow.cn/9c6b630420201214101225.jpg, http://img.rianbow.cn/a677503e20201214101225.jpg, http://img.rianbow.cn/3e07843620201214101225.jpg, http://img.rianbow.cn/093e523320201214101225.jpg, http://img.rianbow.cn/ccff07ca20201214101225.jpg, http://img.rianbow.cn/020a256620201214101225.jpg","lng":null,"lat":null,"createTime":"2020-12-14 10:12:27","status":1,"viewNum":null,"isOpen":1,"imgType":2,"isAttention":false,"isCollect":false,"isClick":false,"clickNum":0,"topics":[],"userInfo":{"id":1031,"phoneNum":null,"nickName":"李四","headImg":"http://img.rianbow.cn/202012091309325288188.jpg","age":null,"explains":null,"birthday":null,"userType":null,"userRole":"T","isSingle":null,"lng":115.54894616,"lat":116.151641641,"ratio":null,"attestation":1,"createTime":null,"countNum":null,"distance":null,"distancing":null,"invitationCode":null},"commentCount":0,"distance":null},{"id":184,"userId":1031,"contentInfo":"我还没额头哦星眸","contentImg":"http://img.rianbow.cn/26f1fb1f20201214100307.jpg, http://img.rianbow.cn/80aaadcf20201214100307.jpg, http://img.rianbow.cn/3e07843620201214100307.jpg, http://img.rianbow.cn/ccff07ca20201214100307.jpg, http://img.rianbow.cn/3aeca1ff20201214100307.jpg","lng":null,"lat":null,"createTime":"2020-12-14 10:03:05","status":1,"viewNum":null,"isOpen":1,"imgType":21,"isAttention":false,"isCollect":false,"isClick":false,"clickNum":0,"topics":[],"userInfo":{"id":1031,"phoneNum":null,"nickName":"李四","headImg":"http://img.rianbow.cn/202012091309325288188.jpg","age":null,"explains":null,"birthday":null,"userType":null,"userRole":"T","isSingle":null,"lng":115.54894616,"lat":116.151641641,"ratio":null,"attestation":1,"createTime":null,"countNum":null,"distance":null,"distancing":null,"invitationCode":null},"commentCount":0,"distance":null},{"id":182,"userId":1030,"contentInfo":"爱上一个不服老司机良好","contentImg":"http://img.rianbow.cn/202012140954477472859.jpg,http://img.rianbow.cn/202012140954477473803.jpg,http://img.rianbow.cn/202012140954477479967.jpg","lng":123,"lat":123,"createTime":"2020-12-14 09:54:52","status":1,"viewNum":null,"isOpen":1,"imgType":21,"isAttention":false,"isCollect":false,"isClick":false,"clickNum":0,"topics":[],"userInfo":{"id":1030,"phoneNum":null,"nickName":"何梦阳","headImg":"http://img.rianbow.cn/202012091309129337159.jpg","age":null,"explains":null,"birthday":null,"userType":null,"userRole":"保密","isSingle":null,"lng":115.298833,"lat":34.097004,"ratio":null,"attestation":1,"createTime":null,"countNum":null,"distance":null,"distancing":null,"invitationCode":null},"commentCount":0,"distance":9859639.71113922}]
         * pageNum : 1
         * pageSize : 3
         * size : 3
         * startRow : 0
         * endRow : 2
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
             * id : 185
             * userId : 1031
             * contentInfo :
             * contentImg : http://img.rianbow.cn/26f1fb1f20201214101225.jpg, http://img.rianbow.cn/80aaadcf20201214101225.jpg, http://img.rianbow.cn/496b81be20201214101225.jpg, http://img.rianbow.cn/9c6b630420201214101225.jpg, http://img.rianbow.cn/a677503e20201214101225.jpg, http://img.rianbow.cn/3e07843620201214101225.jpg, http://img.rianbow.cn/093e523320201214101225.jpg, http://img.rianbow.cn/ccff07ca20201214101225.jpg, http://img.rianbow.cn/020a256620201214101225.jpg
             * lng : null
             * lat : null
             * createTime : 2020-12-14 10:12:27
             * status : 1
             * viewNum : null
             * isOpen : 1
             * imgType : 2
             * isAttention : false
             * isCollect : false
             * isClick : false
             * clickNum : 0
             * topics : []
             * userInfo : {"id":1031,"phoneNum":null,"nickName":"李四","headImg":"http://img.rianbow.cn/202012091309325288188.jpg","age":null,"explains":null,"birthday":null,"userType":null,"userRole":"T","isSingle":null,"lng":115.54894616,"lat":116.151641641,"ratio":null,"attestation":1,"createTime":null,"countNum":null,"distance":null,"distancing":null,"invitationCode":null}
             * commentCount : 0
             * distance : null
             */

            private int id;
            private int userId;
            private String contentInfo;
            private String contentImg;
            private Object lng;
            private Object lat;
            private String createTime;
            private int status;
            private Object viewNum;
            private int isOpen;
            private int imgType;
            private boolean isAttention;
            private boolean isCollect;
            private boolean isClick;
            private int clickNum;
            private UserInfoBean userInfo;
            private int commentCount;
            private String distance;
            private List<?> topics;

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

            public boolean isIsAttention() {
                return isAttention;
            }

            public void setIsAttention(boolean isAttention) {
                this.isAttention = isAttention;
            }

            public boolean isIsCollect() {
                return isCollect;
            }

            public void setIsCollect(boolean isCollect) {
                this.isCollect = isCollect;
            }

            public boolean isIsClick() {
                return isClick;
            }

            public void setIsClick(boolean isClick) {
                this.isClick = isClick;
            }

            public int getClickNum() {
                return clickNum;
            }

            public void setClickNum(int clickNum) {
                this.clickNum = clickNum;
            }

            public UserInfoBean getUserInfo() {
                return userInfo;
            }

            public void setUserInfo(UserInfoBean userInfo) {
                this.userInfo = userInfo;
            }

            public int getCommentCount() {
                return commentCount;
            }

            public void setCommentCount(int commentCount) {
                this.commentCount = commentCount;
            }

            public String getDistance() {
                return distance;
            }

            public void setDistance(String distance) {
                this.distance = distance;
            }

            public List<?> getTopics() {
                return topics;
            }

            public void setTopics(List<?> topics) {
                this.topics = topics;
            }

            public static class UserInfoBean {
                /**
                 * id : 1031
                 * phoneNum : null
                 * nickName : 李四
                 * headImg : http://img.rianbow.cn/202012091309325288188.jpg
                 * age : null
                 * explains : null
                 * birthday : null
                 * userType : null
                 * userRole : T
                 * isSingle : null
                 * lng : 115.54894616
                 * lat : 116.151641641
                 * ratio : null
                 * attestation : 1
                 * createTime : null
                 * countNum : null
                 * distance : null
                 * distancing : null
                 * invitationCode : null
                 */

                private int id;
                private Object phoneNum;
                private String nickName;
                private String headImg;
                private Object age;
                private Object explains;
                private Object birthday;
                private Object userType;
                private String userRole;
                private Object isSingle;
                private double lng;
                private double lat;
                private Object ratio;
                private int attestation;
                private Object createTime;
                private Object countNum;
                private Object distance;
                private Object distancing;
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

                public Object getAge() {
                    return age;
                }

                public void setAge(Object age) {
                    this.age = age;
                }

                public Object getExplains() {
                    return explains;
                }

                public void setExplains(Object explains) {
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

                public Object getRatio() {
                    return ratio;
                }

                public void setRatio(Object ratio) {
                    this.ratio = ratio;
                }

                public int getAttestation() {
                    return attestation;
                }

                public void setAttestation(int attestation) {
                    this.attestation = attestation;
                }

                public Object getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(Object createTime) {
                    this.createTime = createTime;
                }

                public Object getCountNum() {
                    return countNum;
                }

                public void setCountNum(Object countNum) {
                    this.countNum = countNum;
                }

                public Object getDistance() {
                    return distance;
                }

                public void setDistance(Object distance) {
                    this.distance = distance;
                }

                public Object getDistancing() {
                    return distancing;
                }

                public void setDistancing(Object distancing) {
                    this.distancing = distancing;
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
}
