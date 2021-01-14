package com.YiDian.RainBow.main.fragment.msg.bean;

public class GlodNumBean {

    /**
     * msg : 查询成功
     * type : OK
     * object : {"id":0,"userId":0,"goldNum":500,"usableNum":0,"goldUsable":4000,"goldAll":4500}
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
         * id : 0
         * userId : 0
         * goldNum : 500
         * usableNum : 0
         * goldUsable : 4000
         * goldAll : 4500
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
