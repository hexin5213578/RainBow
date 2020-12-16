package com.YiDian.RainBow.main.fragment.home.bean;

import java.util.List;

public class SecondCommentBean {

    /**
     * msg : getSonCommentById获取成功!
     * type : OK
     * object : {"total":2,"list":[{"id":3,"userid":1030,"commentinfo":"阿斯蒂芬","lng":null,"lat":null,"commenttype":1,"fatherid":1,"createtime":null,"clickNum":null,"children":[{"id":5,"userid":1031,"commentinfo":"安抚如果很舒服","lng":null,"lat":null,"commenttype":1,"fatherid":3,"createtime":null,"clickNum":null,"children":[],"userInfo":{"id":1031,"phoneNum":null,"nickName":"李四","headImg":"http://img.rianbow.cn/202012091309325288188.jpg","explains":null,"birthday":null,"userType":null,"userRole":"T","isSingle":null,"lng":115.54894616,"lat":116.151641641,"ratio":null,"attestation":1,"createTime":null,"countNum":null,"distance":null,"distancing":null,"age":null,"invitationCode":null}}],"userInfo":{"id":1030,"phoneNum":null,"nickName":"何梦阳","headImg":"http://img.rianbow.cn/202012091309129337159.jpg","explains":null,"birthday":null,"userType":null,"userRole":"保密","isSingle":null,"lng":115.298833,"lat":34.097004,"ratio":null,"attestation":1,"createTime":null,"countNum":null,"distance":null,"distancing":null,"age":null,"invitationCode":null}},{"id":4,"userid":1031,"commentinfo":"熬过防守打法","lng":null,"lat":null,"commenttype":1,"fatherid":1,"createtime":null,"clickNum":null,"children":[{"id":6,"userid":1031,"commentinfo":"就闹好舒服","lng":null,"lat":null,"commenttype":1,"fatherid":4,"createtime":null,"clickNum":null,"children":[],"userInfo":{"id":1031,"phoneNum":null,"nickName":"李四","headImg":"http://img.rianbow.cn/202012091309325288188.jpg","explains":null,"birthday":null,"userType":null,"userRole":"T","isSingle":null,"lng":115.54894616,"lat":116.151641641,"ratio":null,"attestation":1,"createTime":null,"countNum":null,"distance":null,"distancing":null,"age":null,"invitationCode":null}}],"userInfo":{"id":1031,"phoneNum":null,"nickName":"李四","headImg":"http://img.rianbow.cn/202012091309325288188.jpg","explains":null,"birthday":null,"userType":null,"userRole":"T","isSingle":null,"lng":115.54894616,"lat":116.151641641,"ratio":null,"attestation":1,"createTime":null,"countNum":null,"distance":null,"distancing":null,"age":null,"invitationCode":null}}],"pageNum":1,"pageSize":2,"size":2,"startRow":0,"endRow":1,"pages":1,"prePage":0,"nextPage":0,"isFirstPage":true,"isLastPage":true,"hasPreviousPage":false,"hasNextPage":false,"navigatePages":8,"navigatepageNums":[1],"navigateFirstPage":1,"navigateLastPage":1}
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
         * total : 2
         * list : [{"id":3,"userid":1030,"commentinfo":"阿斯蒂芬","lng":null,"lat":null,"commenttype":1,"fatherid":1,"createtime":null,"clickNum":null,"children":[{"id":5,"userid":1031,"commentinfo":"安抚如果很舒服","lng":null,"lat":null,"commenttype":1,"fatherid":3,"createtime":null,"clickNum":null,"children":[],"userInfo":{"id":1031,"phoneNum":null,"nickName":"李四","headImg":"http://img.rianbow.cn/202012091309325288188.jpg","explains":null,"birthday":null,"userType":null,"userRole":"T","isSingle":null,"lng":115.54894616,"lat":116.151641641,"ratio":null,"attestation":1,"createTime":null,"countNum":null,"distance":null,"distancing":null,"age":null,"invitationCode":null}}],"userInfo":{"id":1030,"phoneNum":null,"nickName":"何梦阳","headImg":"http://img.rianbow.cn/202012091309129337159.jpg","explains":null,"birthday":null,"userType":null,"userRole":"保密","isSingle":null,"lng":115.298833,"lat":34.097004,"ratio":null,"attestation":1,"createTime":null,"countNum":null,"distance":null,"distancing":null,"age":null,"invitationCode":null}},{"id":4,"userid":1031,"commentinfo":"熬过防守打法","lng":null,"lat":null,"commenttype":1,"fatherid":1,"createtime":null,"clickNum":null,"children":[{"id":6,"userid":1031,"commentinfo":"就闹好舒服","lng":null,"lat":null,"commenttype":1,"fatherid":4,"createtime":null,"clickNum":null,"children":[],"userInfo":{"id":1031,"phoneNum":null,"nickName":"李四","headImg":"http://img.rianbow.cn/202012091309325288188.jpg","explains":null,"birthday":null,"userType":null,"userRole":"T","isSingle":null,"lng":115.54894616,"lat":116.151641641,"ratio":null,"attestation":1,"createTime":null,"countNum":null,"distance":null,"distancing":null,"age":null,"invitationCode":null}}],"userInfo":{"id":1031,"phoneNum":null,"nickName":"李四","headImg":"http://img.rianbow.cn/202012091309325288188.jpg","explains":null,"birthday":null,"userType":null,"userRole":"T","isSingle":null,"lng":115.54894616,"lat":116.151641641,"ratio":null,"attestation":1,"createTime":null,"countNum":null,"distance":null,"distancing":null,"age":null,"invitationCode":null}}]
         * pageNum : 1
         * pageSize : 2
         * size : 2
         * startRow : 0
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
             * id : 3
             * userid : 1030
             * commentinfo : 阿斯蒂芬
             * lng : null
             * lat : null
             * commenttype : 1
             * fatherid : 1
             * createtime : null
             * clickNum : null
             * children : [{"id":5,"userid":1031,"commentinfo":"安抚如果很舒服","lng":null,"lat":null,"commenttype":1,"fatherid":3,"createtime":null,"clickNum":null,"children":[],"userInfo":{"id":1031,"phoneNum":null,"nickName":"李四","headImg":"http://img.rianbow.cn/202012091309325288188.jpg","explains":null,"birthday":null,"userType":null,"userRole":"T","isSingle":null,"lng":115.54894616,"lat":116.151641641,"ratio":null,"attestation":1,"createTime":null,"countNum":null,"distance":null,"distancing":null,"age":null,"invitationCode":null}}]
             * userInfo : {"id":1030,"phoneNum":null,"nickName":"何梦阳","headImg":"http://img.rianbow.cn/202012091309129337159.jpg","explains":null,"birthday":null,"userType":null,"userRole":"保密","isSingle":null,"lng":115.298833,"lat":34.097004,"ratio":null,"attestation":1,"createTime":null,"countNum":null,"distance":null,"distancing":null,"age":null,"invitationCode":null}
             */

            private int id;
            private int userid;
            private String commentinfo;
            private Object lng;
            private Object lat;
            private int commenttype;
            private int fatherid;
            private Object createtime;
            private Object clickNum;
            private UserInfoBean userInfo;
            private List<ChildrenBean> children;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getUserid() {
                return userid;
            }

            public void setUserid(int userid) {
                this.userid = userid;
            }

            public String getCommentinfo() {
                return commentinfo;
            }

            public void setCommentinfo(String commentinfo) {
                this.commentinfo = commentinfo;
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

            public int getCommenttype() {
                return commenttype;
            }

            public void setCommenttype(int commenttype) {
                this.commenttype = commenttype;
            }

            public int getFatherid() {
                return fatherid;
            }

            public void setFatherid(int fatherid) {
                this.fatherid = fatherid;
            }

            public Object getCreatetime() {
                return createtime;
            }

            public void setCreatetime(Object createtime) {
                this.createtime = createtime;
            }

            public Object getClickNum() {
                return clickNum;
            }

            public void setClickNum(Object clickNum) {
                this.clickNum = clickNum;
            }

            public UserInfoBean getUserInfo() {
                return userInfo;
            }

            public void setUserInfo(UserInfoBean userInfo) {
                this.userInfo = userInfo;
            }

            public List<ChildrenBean> getChildren() {
                return children;
            }

            public void setChildren(List<ChildrenBean> children) {
                this.children = children;
            }

            public static class UserInfoBean {
                /**
                 * id : 1030
                 * phoneNum : null
                 * nickName : 何梦阳
                 * headImg : http://img.rianbow.cn/202012091309129337159.jpg
                 * explains : null
                 * birthday : null
                 * userType : null
                 * userRole : 保密
                 * isSingle : null
                 * lng : 115.298833
                 * lat : 34.097004
                 * ratio : null
                 * attestation : 1
                 * createTime : null
                 * countNum : null
                 * distance : null
                 * distancing : null
                 * age : null
                 * invitationCode : null
                 */

                private int id;
                private Object phoneNum;
                private String nickName;
                private String headImg;
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

            public static class ChildrenBean {
                /**
                 * id : 5
                 * userid : 1031
                 * commentinfo : 安抚如果很舒服
                 * lng : null
                 * lat : null
                 * commenttype : 1
                 * fatherid : 3
                 * createtime : null
                 * clickNum : null
                 * children : []
                 * userInfo : {"id":1031,"phoneNum":null,"nickName":"李四","headImg":"http://img.rianbow.cn/202012091309325288188.jpg","explains":null,"birthday":null,"userType":null,"userRole":"T","isSingle":null,"lng":115.54894616,"lat":116.151641641,"ratio":null,"attestation":1,"createTime":null,"countNum":null,"distance":null,"distancing":null,"age":null,"invitationCode":null}
                 */

                private int id;
                private int userid;
                private String commentinfo;
                private Object lng;
                private Object lat;
                private int commenttype;
                private int fatherid;
                private Object createtime;
                private Object clickNum;
                private UserInfoBeanX userInfo;
                private List<?> children;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getUserid() {
                    return userid;
                }

                public void setUserid(int userid) {
                    this.userid = userid;
                }

                public String getCommentinfo() {
                    return commentinfo;
                }

                public void setCommentinfo(String commentinfo) {
                    this.commentinfo = commentinfo;
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

                public int getCommenttype() {
                    return commenttype;
                }

                public void setCommenttype(int commenttype) {
                    this.commenttype = commenttype;
                }

                public int getFatherid() {
                    return fatherid;
                }

                public void setFatherid(int fatherid) {
                    this.fatherid = fatherid;
                }

                public Object getCreatetime() {
                    return createtime;
                }

                public void setCreatetime(Object createtime) {
                    this.createtime = createtime;
                }

                public Object getClickNum() {
                    return clickNum;
                }

                public void setClickNum(Object clickNum) {
                    this.clickNum = clickNum;
                }

                public UserInfoBeanX getUserInfo() {
                    return userInfo;
                }

                public void setUserInfo(UserInfoBeanX userInfo) {
                    this.userInfo = userInfo;
                }

                public List<?> getChildren() {
                    return children;
                }

                public void setChildren(List<?> children) {
                    this.children = children;
                }

                public static class UserInfoBeanX {
                    /**
                     * id : 1031
                     * phoneNum : null
                     * nickName : 李四
                     * headImg : http://img.rianbow.cn/202012091309325288188.jpg
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
                     * age : null
                     * invitationCode : null
                     */

                    private int id;
                    private Object phoneNum;
                    private String nickName;
                    private String headImg;
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
    }
}
