package com.YiDian.RainBow.topic;

import java.util.List;

public class TopicBean {

    /**
     * msg : getContentByTopicTitle
     * type : OK
     * object : {"total":4,"list":[{"id":209,"userId":1031,"contentInfo":"魔法精灵噢噢噢哦哦墨子#热门话题3#","contentImg":"","lng":116.483038,"lat":39.990633,"createTime":"2020-12-16 09:38:37","status":1,"viewNum":null,"isOpen":1,"imgType":1,"isAttention":true,"isCollect":true,"isClick":false,"clickNum":3,"topics":[{"id":46,"topicTitle":"热门话题3","topicNum":2,"topicQuality":null,"topicViewNum":0}],"userInfo":{"id":1031,"phoneNum":"17698567777","nickName":"李老四","headImg":"http://img.rianbow.cn/202012091309129337159.jpg","backImg":"http://img.rianbow.cn/202012211434078716682.jpg","explains":"我李老四就是牛批冲天","birthday":null,"userType":1,"userRole":"BI","isSingle":1,"loveState":1,"lng":116.37927521679686,"lat":40.21186066153374,"ratio":0.5,"attestation":0,"love_state":null,"createTime":"2020-12-11 15:46:34","isFans":null,"countNum":null,"distance":null,"distancing":null,"age":null,"invitationCode":null},"commentCount":2,"distance":663689.6273859362},{"id":214,"userId":1031,"contentInfo":"#热门话题1##热门话题2##热门话题3#我呢路哦哦马路","contentImg":"","lng":115.298766,"lat":34.097041,"createTime":"2020-12-16 09:39:52","status":1,"viewNum":null,"isOpen":1,"imgType":1,"isAttention":true,"isCollect":false,"isClick":false,"clickNum":2,"topics":[{"id":48,"topicTitle":"热门话题1","topicNum":1,"topicQuality":null,"topicViewNum":0},{"id":45,"topicTitle":"热门话题2","topicNum":4,"topicQuality":null,"topicViewNum":0},{"id":46,"topicTitle":"热门话题3","topicNum":3,"topicQuality":null,"topicViewNum":0}],"userInfo":{"id":1031,"phoneNum":"17698567777","nickName":"李老四","headImg":"http://img.rianbow.cn/202012091309129337159.jpg","backImg":"http://img.rianbow.cn/202012211434078716682.jpg","explains":"我李老四就是牛批冲天","birthday":null,"userType":1,"userRole":"BI","isSingle":1,"loveState":1,"lng":116.37927521679686,"lat":40.21186066153374,"ratio":0.5,"attestation":0,"love_state":null,"createTime":"2020-12-11 15:46:34","isFans":null,"countNum":null,"distance":null,"distancing":null,"age":null,"invitationCode":null},"commentCount":0,"distance":5.921379241480882},{"id":235,"userId":1030,"contentInfo":"#热门话题2##热门话题3##热门话题4#@何梦洋4 @何梦洋3 哦门口哦门口","contentImg":"","lng":null,"lat":null,"createTime":"2020-12-20 16:21:06","status":1,"viewNum":null,"isOpen":1,"imgType":1,"isAttention":false,"isCollect":false,"isClick":false,"clickNum":1,"topics":[{"id":45,"topicTitle":"热门话题2","topicNum":9,"topicQuality":null,"topicViewNum":0},{"id":46,"topicTitle":"热门话题3","topicNum":4,"topicQuality":null,"topicViewNum":0},{"id":49,"topicTitle":"热门话题4","topicNum":1,"topicQuality":null,"topicViewNum":0}],"userInfo":{"id":1030,"phoneNum":"15652578310","nickName":"阿杨在此","headImg":"http://img.rianbow.cn/27c1105b20210117192835.jpg","backImg":"http://img.rianbow.cn/5672bdaf20210123100303.jpg","explains":"我记录55路","birthday":"2005-08-21","userType":1,"userRole":"H","isSingle":2,"loveState":1,"lng":115.298824,"lat":34.097064,"ratio":0.5,"attestation":5,"love_state":null,"createTime":"2021-01-11 15:46:34","isFans":null,"countNum":null,"distance":null,"distancing":null,"age":null,"invitationCode":null},"commentCount":1,"distance":null},{"id":290,"userId":1030,"contentInfo":"hi撸他略略略#热门话题3#","contentImg":"","lng":null,"lat":null,"createTime":"2021-01-23 12:59:17","status":1,"viewNum":null,"isOpen":1,"imgType":1,"isAttention":false,"isCollect":false,"isClick":false,"clickNum":0,"topics":[{"id":46,"topicTitle":"热门话题3","topicNum":5,"topicQuality":null,"topicViewNum":0}],"userInfo":{"id":1030,"phoneNum":"15652578310","nickName":"阿杨在此","headImg":"http://img.rianbow.cn/27c1105b20210117192835.jpg","backImg":"http://img.rianbow.cn/5672bdaf20210123100303.jpg","explains":"我记录55路","birthday":"2005-08-21","userType":1,"userRole":"H","isSingle":2,"loveState":1,"lng":115.298824,"lat":34.097064,"ratio":0.5,"attestation":5,"love_state":null,"createTime":"2021-01-11 15:46:34","isFans":null,"countNum":null,"distance":null,"distancing":null,"age":null,"invitationCode":null},"commentCount":0,"distance":null}],"pageNum":1,"pageSize":4,"size":4,"startRow":0,"endRow":3,"pages":1,"prePage":0,"nextPage":0,"isFirstPage":true,"isLastPage":true,"hasPreviousPage":false,"hasNextPage":false,"navigatePages":8,"navigatepageNums":[1],"navigateFirstPage":1,"navigateLastPage":1}
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
         * total : 4
         * list : [{"id":209,"userId":1031,"contentInfo":"魔法精灵噢噢噢哦哦墨子#热门话题3#","contentImg":"","lng":116.483038,"lat":39.990633,"createTime":"2020-12-16 09:38:37","status":1,"viewNum":null,"isOpen":1,"imgType":1,"isAttention":true,"isCollect":true,"isClick":false,"clickNum":3,"topics":[{"id":46,"topicTitle":"热门话题3","topicNum":2,"topicQuality":null,"topicViewNum":0}],"userInfo":{"id":1031,"phoneNum":"17698567777","nickName":"李老四","headImg":"http://img.rianbow.cn/202012091309129337159.jpg","backImg":"http://img.rianbow.cn/202012211434078716682.jpg","explains":"我李老四就是牛批冲天","birthday":null,"userType":1,"userRole":"BI","isSingle":1,"loveState":1,"lng":116.37927521679686,"lat":40.21186066153374,"ratio":0.5,"attestation":0,"love_state":null,"createTime":"2020-12-11 15:46:34","isFans":null,"countNum":null,"distance":null,"distancing":null,"age":null,"invitationCode":null},"commentCount":2,"distance":663689.6273859362},{"id":214,"userId":1031,"contentInfo":"#热门话题1##热门话题2##热门话题3#我呢路哦哦马路","contentImg":"","lng":115.298766,"lat":34.097041,"createTime":"2020-12-16 09:39:52","status":1,"viewNum":null,"isOpen":1,"imgType":1,"isAttention":true,"isCollect":false,"isClick":false,"clickNum":2,"topics":[{"id":48,"topicTitle":"热门话题1","topicNum":1,"topicQuality":null,"topicViewNum":0},{"id":45,"topicTitle":"热门话题2","topicNum":4,"topicQuality":null,"topicViewNum":0},{"id":46,"topicTitle":"热门话题3","topicNum":3,"topicQuality":null,"topicViewNum":0}],"userInfo":{"id":1031,"phoneNum":"17698567777","nickName":"李老四","headImg":"http://img.rianbow.cn/202012091309129337159.jpg","backImg":"http://img.rianbow.cn/202012211434078716682.jpg","explains":"我李老四就是牛批冲天","birthday":null,"userType":1,"userRole":"BI","isSingle":1,"loveState":1,"lng":116.37927521679686,"lat":40.21186066153374,"ratio":0.5,"attestation":0,"love_state":null,"createTime":"2020-12-11 15:46:34","isFans":null,"countNum":null,"distance":null,"distancing":null,"age":null,"invitationCode":null},"commentCount":0,"distance":5.921379241480882},{"id":235,"userId":1030,"contentInfo":"#热门话题2##热门话题3##热门话题4#@何梦洋4 @何梦洋3 哦门口哦门口","contentImg":"","lng":null,"lat":null,"createTime":"2020-12-20 16:21:06","status":1,"viewNum":null,"isOpen":1,"imgType":1,"isAttention":false,"isCollect":false,"isClick":false,"clickNum":1,"topics":[{"id":45,"topicTitle":"热门话题2","topicNum":9,"topicQuality":null,"topicViewNum":0},{"id":46,"topicTitle":"热门话题3","topicNum":4,"topicQuality":null,"topicViewNum":0},{"id":49,"topicTitle":"热门话题4","topicNum":1,"topicQuality":null,"topicViewNum":0}],"userInfo":{"id":1030,"phoneNum":"15652578310","nickName":"阿杨在此","headImg":"http://img.rianbow.cn/27c1105b20210117192835.jpg","backImg":"http://img.rianbow.cn/5672bdaf20210123100303.jpg","explains":"我记录55路","birthday":"2005-08-21","userType":1,"userRole":"H","isSingle":2,"loveState":1,"lng":115.298824,"lat":34.097064,"ratio":0.5,"attestation":5,"love_state":null,"createTime":"2021-01-11 15:46:34","isFans":null,"countNum":null,"distance":null,"distancing":null,"age":null,"invitationCode":null},"commentCount":1,"distance":null},{"id":290,"userId":1030,"contentInfo":"hi撸他略略略#热门话题3#","contentImg":"","lng":null,"lat":null,"createTime":"2021-01-23 12:59:17","status":1,"viewNum":null,"isOpen":1,"imgType":1,"isAttention":false,"isCollect":false,"isClick":false,"clickNum":0,"topics":[{"id":46,"topicTitle":"热门话题3","topicNum":5,"topicQuality":null,"topicViewNum":0}],"userInfo":{"id":1030,"phoneNum":"15652578310","nickName":"阿杨在此","headImg":"http://img.rianbow.cn/27c1105b20210117192835.jpg","backImg":"http://img.rianbow.cn/5672bdaf20210123100303.jpg","explains":"我记录55路","birthday":"2005-08-21","userType":1,"userRole":"H","isSingle":2,"loveState":1,"lng":115.298824,"lat":34.097064,"ratio":0.5,"attestation":5,"love_state":null,"createTime":"2021-01-11 15:46:34","isFans":null,"countNum":null,"distance":null,"distancing":null,"age":null,"invitationCode":null},"commentCount":0,"distance":null}]
         * pageNum : 1
         * pageSize : 4
         * size : 4
         * startRow : 0
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
             * id : 209
             * userId : 1031
             * contentInfo : 魔法精灵噢噢噢哦哦墨子#热门话题3#
             * contentImg :
             * lng : 116.483038
             * lat : 39.990633
             * createTime : 2020-12-16 09:38:37
             * status : 1
             * viewNum : null
             * isOpen : 1
             * imgType : 1
             * isAttention : true
             * isCollect : true
             * isClick : false
             * clickNum : 3
             * topics : [{"id":46,"topicTitle":"热门话题3","topicNum":2,"topicQuality":null,"topicViewNum":0}]
             * userInfo : {"id":1031,"phoneNum":"17698567777","nickName":"李老四","headImg":"http://img.rianbow.cn/202012091309129337159.jpg","backImg":"http://img.rianbow.cn/202012211434078716682.jpg","explains":"我李老四就是牛批冲天","birthday":null,"userType":1,"userRole":"BI","isSingle":1,"loveState":1,"lng":116.37927521679686,"lat":40.21186066153374,"ratio":0.5,"attestation":0,"love_state":null,"createTime":"2020-12-11 15:46:34","isFans":null,"countNum":null,"distance":null,"distancing":null,"age":null,"invitationCode":null}
             * commentCount : 2
             * distance : 663689.6273859362
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
            private int isOpen;
            private int imgType;
            private Boolean isAttention;
            private boolean isCollect;
            private boolean isClick;
            private int clickNum;
            private UserInfoBean userInfo;
            private int commentCount;
            private String distance;
            private List<TopicsBean> topics;

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

            public Boolean isIsAttention() {
                return isAttention;
            }

            public void setIsAttention(Boolean isAttention) {
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

            public List<TopicsBean> getTopics() {
                return topics;
            }

            public void setTopics(List<TopicsBean> topics) {
                this.topics = topics;
            }

            public static class UserInfoBean {
                /**
                 * id : 1031
                 * phoneNum : 17698567777
                 * nickName : 李老四
                 * headImg : http://img.rianbow.cn/202012091309129337159.jpg
                 * backImg : http://img.rianbow.cn/202012211434078716682.jpg
                 * explains : 我李老四就是牛批冲天
                 * birthday : null
                 * userType : 1
                 * userRole : BI
                 * isSingle : 1
                 * loveState : 1
                 * lng : 116.37927521679686
                 * lat : 40.21186066153374
                 * ratio : 0.5
                 * attestation : 0
                 * love_state : null
                 * createTime : 2020-12-11 15:46:34
                 * isFans : null
                 * countNum : null
                 * distance : null
                 * distancing : null
                 * age : null
                 * invitationCode : null
                 */

                private int id;
                private String phoneNum;
                private String nickName;
                private String headImg;
                private String backImg;
                private String explains;
                private Object birthday;
                private int userType;
                private String userRole;
                private int isSingle;
                private int loveState;
                private double lng;
                private double lat;
                private double ratio;
                private int attestation;
                private Object love_state;
                private String createTime;
                private Object isFans;
                private Object countNum;
                private Object distance;
                private Object distancing;
                private Object age;
                private Object invitationCode;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getPhoneNum() {
                    return phoneNum;
                }

                public void setPhoneNum(String phoneNum) {
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

                public String getBackImg() {
                    return backImg;
                }

                public void setBackImg(String backImg) {
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

                public int getUserType() {
                    return userType;
                }

                public void setUserType(int userType) {
                    this.userType = userType;
                }

                public String getUserRole() {
                    return userRole;
                }

                public void setUserRole(String userRole) {
                    this.userRole = userRole;
                }

                public int getIsSingle() {
                    return isSingle;
                }

                public void setIsSingle(int isSingle) {
                    this.isSingle = isSingle;
                }

                public int getLoveState() {
                    return loveState;
                }

                public void setLoveState(int loveState) {
                    this.loveState = loveState;
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

                public double getRatio() {
                    return ratio;
                }

                public void setRatio(double ratio) {
                    this.ratio = ratio;
                }

                public int getAttestation() {
                    return attestation;
                }

                public void setAttestation(int attestation) {
                    this.attestation = attestation;
                }

                public Object getLove_state() {
                    return love_state;
                }

                public void setLove_state(Object love_state) {
                    this.love_state = love_state;
                }

                public String getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(String createTime) {
                    this.createTime = createTime;
                }

                public Object getIsFans() {
                    return isFans;
                }

                public void setIsFans(Object isFans) {
                    this.isFans = isFans;
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

            public static class TopicsBean {
                /**
                 * id : 46
                 * topicTitle : 热门话题3
                 * topicNum : 2
                 * topicQuality : null
                 * topicViewNum : 0
                 */

                private int id;
                private String topicTitle;
                private int topicNum;
                private Object topicQuality;
                private int topicViewNum;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getTopicTitle() {
                    return topicTitle;
                }

                public void setTopicTitle(String topicTitle) {
                    this.topicTitle = topicTitle;
                }

                public int getTopicNum() {
                    return topicNum;
                }

                public void setTopicNum(int topicNum) {
                    this.topicNum = topicNum;
                }

                public Object getTopicQuality() {
                    return topicQuality;
                }

                public void setTopicQuality(Object topicQuality) {
                    this.topicQuality = topicQuality;
                }

                public int getTopicViewNum() {
                    return topicViewNum;
                }

                public void setTopicViewNum(int topicViewNum) {
                    this.topicViewNum = topicViewNum;
                }
            }
        }
    }
}
