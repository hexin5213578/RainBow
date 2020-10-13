package com.YiDian.RainBow.regist.bean;

public class RegistBean {

    /**
     * msg : insertPassword
     * type : OK
     * object : {"id":null,"userId":103,"phoneNum":"15652578310","password":"123456","accountType":1,"weChatOpenId":null,"qqOpenId":null}
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
         * id : null
         * userId : 103
         * phoneNum : 15652578310
         * password : 123456
         * accountType : 1
         * weChatOpenId : null
         * qqOpenId : null
         */

        private Object id;
        private int userId;
        private String phoneNum;
        private String password;
        private int accountType;
        private Object weChatOpenId;
        private Object qqOpenId;

        public Object getId() {
            return id;
        }

        public void setId(Object id) {
            this.id = id;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getPhoneNum() {
            return phoneNum;
        }

        public void setPhoneNum(String phoneNum) {
            this.phoneNum = phoneNum;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public int getAccountType() {
            return accountType;
        }

        public void setAccountType(int accountType) {
            this.accountType = accountType;
        }

        public Object getWeChatOpenId() {
            return weChatOpenId;
        }

        public void setWeChatOpenId(Object weChatOpenId) {
            this.weChatOpenId = weChatOpenId;
        }

        public Object getQqOpenId() {
            return qqOpenId;
        }

        public void setQqOpenId(Object qqOpenId) {
            this.qqOpenId = qqOpenId;
        }
    }
}
