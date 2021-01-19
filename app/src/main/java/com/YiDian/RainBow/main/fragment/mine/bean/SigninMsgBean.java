package com.YiDian.RainBow.main.fragment.mine.bean;

import java.util.List;

public class SigninMsgBean {


    /**
     * msg : 数据加载成功
     * type : OK
     * object : {"signInList":[{"id":119,"userId":1030,"weeks":1,"isSign":1,"createTime":"2021-01-17T16:00:00.000+0000","award":5},{"id":120,"userId":1030,"weeks":2,"isSign":1,"createTime":"2021-01-18T16:00:00.000+0000","award":5},{"id":121,"userId":1030,"weeks":3,"isSign":0,"createTime":"2021-01-17T16:00:00.000+0000","award":5},{"id":122,"userId":1030,"weeks":4,"isSign":0,"createTime":"2021-01-17T16:00:00.000+0000","award":5},{"id":123,"userId":1030,"weeks":5,"isSign":0,"createTime":"2021-01-17T16:00:00.000+0000","award":5},{"id":124,"userId":1030,"weeks":6,"isSign":0,"createTime":"2021-01-17T16:00:00.000+0000","award":5},{"id":125,"userId":1030,"weeks":7,"isSign":0,"createTime":"2021-01-17T16:00:00.000+0000","award":5}],"continuousDays":0}
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
         * signInList : [{"id":119,"userId":1030,"weeks":1,"isSign":1,"createTime":"2021-01-17T16:00:00.000+0000","award":5},{"id":120,"userId":1030,"weeks":2,"isSign":1,"createTime":"2021-01-18T16:00:00.000+0000","award":5},{"id":121,"userId":1030,"weeks":3,"isSign":0,"createTime":"2021-01-17T16:00:00.000+0000","award":5},{"id":122,"userId":1030,"weeks":4,"isSign":0,"createTime":"2021-01-17T16:00:00.000+0000","award":5},{"id":123,"userId":1030,"weeks":5,"isSign":0,"createTime":"2021-01-17T16:00:00.000+0000","award":5},{"id":124,"userId":1030,"weeks":6,"isSign":0,"createTime":"2021-01-17T16:00:00.000+0000","award":5},{"id":125,"userId":1030,"weeks":7,"isSign":0,"createTime":"2021-01-17T16:00:00.000+0000","award":5}]
         * continuousDays : 0
         */

        private int continuousDays;
        private List<SignInListBean> signInList;

        public int getContinuousDays() {
            return continuousDays;
        }

        public void setContinuousDays(int continuousDays) {
            this.continuousDays = continuousDays;
        }

        public List<SignInListBean> getSignInList() {
            return signInList;
        }

        public void setSignInList(List<SignInListBean> signInList) {
            this.signInList = signInList;
        }

        public static class SignInListBean {
            /**
             * id : 119
             * userId : 1030
             * weeks : 1
             * isSign : 1
             * createTime : 2021-01-17T16:00:00.000+0000
             * award : 5
             */

            private int id;
            private int userId;
            private int weeks;
            private int isSign;
            private String createTime;
            private int award;

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

            public int getWeeks() {
                return weeks;
            }

            public void setWeeks(int weeks) {
                this.weeks = weeks;
            }

            public int getIsSign() {
                return isSign;
            }

            public void setIsSign(int isSign) {
                this.isSign = isSign;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public int getAward() {
                return award;
            }

            public void setAward(int award) {
                this.award = award;
            }
        }
    }
}
