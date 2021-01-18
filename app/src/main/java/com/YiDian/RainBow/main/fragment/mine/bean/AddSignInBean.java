package com.YiDian.RainBow.main.fragment.mine.bean;

public class AddSignInBean {


    /**
     * msg : addSign
     * type : OK
     * object : {"签到信息":"补签成功","totalAward":5,"todayAward":5,"continuousAward":0,"continuousDays":1}
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
         * 签到信息 : 补签成功
         * totalAward : 5
         * todayAward : 5
         * continuousAward : 0
         * continuousDays : 1
         */

        private String 签到信息;
        private int totalAward;
        private int todayAward;
        private int continuousAward;
        private int continuousDays;

        public String get签到信息() {
            return 签到信息;
        }

        public void set签到信息(String 签到信息) {
            this.签到信息 = 签到信息;
        }

        public int getTotalAward() {
            return totalAward;
        }

        public void setTotalAward(int totalAward) {
            this.totalAward = totalAward;
        }

        public int getTodayAward() {
            return todayAward;
        }

        public void setTodayAward(int todayAward) {
            this.todayAward = todayAward;
        }

        public int getContinuousAward() {
            return continuousAward;
        }

        public void setContinuousAward(int continuousAward) {
            this.continuousAward = continuousAward;
        }

        public int getContinuousDays() {
            return continuousDays;
        }

        public void setContinuousDays(int continuousDays) {
            this.continuousDays = continuousDays;
        }
    }
}
