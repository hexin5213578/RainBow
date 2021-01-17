package com.YiDian.RainBow.main.fragment.mine.bean;

import java.util.List;

public class SigninMsgBean {

    /**
     * msg : 数据加载成功
     * type : OK
     * object : [{"id":84,"userId":1030,"weeks":1,"isSign":0,"createTime":null,"award":5},{"id":85,"userId":1030,"weeks":2,"isSign":0,"createTime":null,"award":5},{"id":86,"userId":1030,"weeks":3,"isSign":0,"createTime":null,"award":5},{"id":87,"userId":1030,"weeks":4,"isSign":0,"createTime":null,"award":5},{"id":88,"userId":1030,"weeks":5,"isSign":0,"createTime":null,"award":5},{"id":89,"userId":1030,"weeks":6,"isSign":1,"createTime":"2021-01-16T16:00:00.000+0000","award":5},{"id":90,"userId":1030,"weeks":7,"isSign":0,"createTime":null,"award":5}]
     */

    private String msg;
    private String type;
    private List<ObjectBean> object;

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

    public List<ObjectBean> getObject() {
        return object;
    }

    public void setObject(List<ObjectBean> object) {
        this.object = object;
    }

    public static class ObjectBean {
        /**
         * id : 84
         * userId : 1030
         * weeks : 1
         * isSign : 0
         * createTime : null
         * award : 5
         */

        private int id;
        private int userId;
        private int weeks;
        private int isSign;
        private Object createTime;
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

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
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
