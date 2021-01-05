package com.YiDian.RainBow.setup.bean;

public class GetRealDataBean {

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
         * id : 105
         * userName : 何梦洋
         * idNum : 412724199705166931
         * userId : 1030
         * frontCardImg : 123123123123
         * reverseCardImg : 1231231231
         * auditStatus : 2
         * idDel : 0
         * auditTime : null
         * createTime : 2021-01-05T03:30:51.000+0000
         */

        private int id;
        private String userName;
        private String idNum;
        private int userId;
        private String frontCardImg;
        private String reverseCardImg;
        private int auditStatus;
        private int idDel;
        private Object auditTime;
        private String createTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getIdNum() {
            return idNum;
        }

        public void setIdNum(String idNum) {
            this.idNum = idNum;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getFrontCardImg() {
            return frontCardImg;
        }

        public void setFrontCardImg(String frontCardImg) {
            this.frontCardImg = frontCardImg;
        }

        public String getReverseCardImg() {
            return reverseCardImg;
        }

        public void setReverseCardImg(String reverseCardImg) {
            this.reverseCardImg = reverseCardImg;
        }

        public int getAuditStatus() {
            return auditStatus;
        }

        public void setAuditStatus(int auditStatus) {
            this.auditStatus = auditStatus;
        }

        public int getIdDel() {
            return idDel;
        }

        public void setIdDel(int idDel) {
            this.idDel = idDel;
        }

        public Object getAuditTime() {
            return auditTime;
        }

        public void setAuditTime(Object auditTime) {
            this.auditTime = auditTime;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }
}
