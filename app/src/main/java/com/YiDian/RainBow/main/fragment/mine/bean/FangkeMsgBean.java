package com.YiDian.RainBow.main.fragment.mine.bean;

import java.util.List;

public class FangkeMsgBean {

    /**
     * msg : 查询成功
     * type : OK
     * object : {"meets":[{"id":33,"userId":1035,"beUserId":1030,"accessNum":2,"dayAccessNum":0,"createTime":"2020-12-25 10:55:12","nickName":"获取Q昵称","headImg":"http://img.rianbow.cn/202012091311039645114.jpg","userRole":"BI","isFans":1},{"id":27,"userId":1031,"beUserId":1030,"accessNum":2,"dayAccessNum":0,"createTime":"2020-12-25 10:53:47","nickName":"李老四","headImg":"http://img.rianbow.cn/202012091309129337159.jpg","userRole":"BI","isFans":1},{"id":28,"userId":1032,"beUserId":1030,"accessNum":55,"dayAccessNum":0,"createTime":"2020-12-21 14:31:29","nickName":"hhhaaa李103","headImg":"http://img.rianbow.cn/202101091337175732682.png","userRole":"保密","isFans":0}],"meetToDayNum":0,"meetAllDayNum":212}
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
         * meets : [{"id":33,"userId":1035,"beUserId":1030,"accessNum":2,"dayAccessNum":0,"createTime":"2020-12-25 10:55:12","nickName":"获取Q昵称","headImg":"http://img.rianbow.cn/202012091311039645114.jpg","userRole":"BI","isFans":1},{"id":27,"userId":1031,"beUserId":1030,"accessNum":2,"dayAccessNum":0,"createTime":"2020-12-25 10:53:47","nickName":"李老四","headImg":"http://img.rianbow.cn/202012091309129337159.jpg","userRole":"BI","isFans":1},{"id":28,"userId":1032,"beUserId":1030,"accessNum":55,"dayAccessNum":0,"createTime":"2020-12-21 14:31:29","nickName":"hhhaaa李103","headImg":"http://img.rianbow.cn/202101091337175732682.png","userRole":"保密","isFans":0}]
         * meetToDayNum : 0
         * meetAllDayNum : 212
         */

        private int meetToDayNum;
        private int meetAllDayNum;
        private List<MeetsBean> meets;

        public int getMeetToDayNum() {
            return meetToDayNum;
        }

        public void setMeetToDayNum(int meetToDayNum) {
            this.meetToDayNum = meetToDayNum;
        }

        public int getMeetAllDayNum() {
            return meetAllDayNum;
        }

        public void setMeetAllDayNum(int meetAllDayNum) {
            this.meetAllDayNum = meetAllDayNum;
        }

        public List<MeetsBean> getMeets() {
            return meets;
        }

        public void setMeets(List<MeetsBean> meets) {
            this.meets = meets;
        }

        public static class MeetsBean {
            /**
             * id : 33
             * userId : 1035
             * beUserId : 1030
             * accessNum : 2
             * dayAccessNum : 0
             * createTime : 2020-12-25 10:55:12
             * nickName : 获取Q昵称
             * headImg : http://img.rianbow.cn/202012091311039645114.jpg
             * userRole : BI
             * isFans : 1
             */

            private int id;
            private int userId;
            private int beUserId;
            private int accessNum;
            private int dayAccessNum;
            private String createTime;
            private String nickName;
            private String headImg;
            private String userRole;
            private int isFans;

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

            public int getBeUserId() {
                return beUserId;
            }

            public void setBeUserId(int beUserId) {
                this.beUserId = beUserId;
            }

            public int getAccessNum() {
                return accessNum;
            }

            public void setAccessNum(int accessNum) {
                this.accessNum = accessNum;
            }

            public int getDayAccessNum() {
                return dayAccessNum;
            }

            public void setDayAccessNum(int dayAccessNum) {
                this.dayAccessNum = dayAccessNum;
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

            public String getUserRole() {
                return userRole;
            }

            public void setUserRole(String userRole) {
                this.userRole = userRole;
            }

            public int getIsFans() {
                return isFans;
            }

            public void setIsFans(int isFans) {
                this.isFans = isFans;
            }
        }
    }
}
