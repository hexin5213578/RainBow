package com.YiDian.RainBow.music.bean;

import java.util.List;

public class MusicDetailsBean {

    /**
     * data : [{"id":1832582017,"url":"http://m701.music.126.net/20210401110446/5ad2bc23fd0f767b1fed534f08422f4d/jdymusic/obj/wo3DlMOGwrbDjj7DisKw/8075830270/291e/1a2e/bcfe/06337524abb93c16570e33ed75732ce1.mp3","br":128002,"size":3360813,"md5":"06337524abb93c16570e33ed75732ce1","code":200,"expi":1200,"type":"mp3","gain":0,"fee":8,"uf":null,"payed":0,"flag":64,"canExtend":false,"freeTrialInfo":null,"level":null,"encodeType":null,"freeTrialPrivilege":{"resConsumable":false,"userConsumable":false},"freeTimeTrialPrivilege":{"resConsumable":false,"userConsumable":false,"type":0,"remainTime":0},"urlSource":0}]
     * code : 200
     */

    private int code;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1832582017
         * url : http://m701.music.126.net/20210401110446/5ad2bc23fd0f767b1fed534f08422f4d/jdymusic/obj/wo3DlMOGwrbDjj7DisKw/8075830270/291e/1a2e/bcfe/06337524abb93c16570e33ed75732ce1.mp3
         * br : 128002
         * size : 3360813
         * md5 : 06337524abb93c16570e33ed75732ce1
         * code : 200
         * expi : 1200
         * type : mp3
         * gain : 0
         * fee : 8
         * uf : null
         * payed : 0
         * flag : 64
         * canExtend : false
         * freeTrialInfo : null
         * level : null
         * encodeType : null
         * freeTrialPrivilege : {"resConsumable":false,"userConsumable":false}
         * freeTimeTrialPrivilege : {"resConsumable":false,"userConsumable":false,"type":0,"remainTime":0}
         * urlSource : 0
         */

        private int id;
        private String url;
        private int br;
        private int size;
        private String md5;
        private int code;
        private int expi;
        private String type;
        private int gain;
        private int fee;
        private Object uf;
        private int payed;
        private int flag;
        private boolean canExtend;
        private Object freeTrialInfo;
        private Object level;
        private Object encodeType;
        private FreeTrialPrivilegeBean freeTrialPrivilege;
        private FreeTimeTrialPrivilegeBean freeTimeTrialPrivilege;
        private int urlSource;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getBr() {
            return br;
        }

        public void setBr(int br) {
            this.br = br;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public String getMd5() {
            return md5;
        }

        public void setMd5(String md5) {
            this.md5 = md5;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public int getExpi() {
            return expi;
        }

        public void setExpi(int expi) {
            this.expi = expi;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getGain() {
            return gain;
        }

        public void setGain(int gain) {
            this.gain = gain;
        }

        public int getFee() {
            return fee;
        }

        public void setFee(int fee) {
            this.fee = fee;
        }

        public Object getUf() {
            return uf;
        }

        public void setUf(Object uf) {
            this.uf = uf;
        }

        public int getPayed() {
            return payed;
        }

        public void setPayed(int payed) {
            this.payed = payed;
        }

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

        public boolean isCanExtend() {
            return canExtend;
        }

        public void setCanExtend(boolean canExtend) {
            this.canExtend = canExtend;
        }

        public Object getFreeTrialInfo() {
            return freeTrialInfo;
        }

        public void setFreeTrialInfo(Object freeTrialInfo) {
            this.freeTrialInfo = freeTrialInfo;
        }

        public Object getLevel() {
            return level;
        }

        public void setLevel(Object level) {
            this.level = level;
        }

        public Object getEncodeType() {
            return encodeType;
        }

        public void setEncodeType(Object encodeType) {
            this.encodeType = encodeType;
        }

        public FreeTrialPrivilegeBean getFreeTrialPrivilege() {
            return freeTrialPrivilege;
        }

        public void setFreeTrialPrivilege(FreeTrialPrivilegeBean freeTrialPrivilege) {
            this.freeTrialPrivilege = freeTrialPrivilege;
        }

        public FreeTimeTrialPrivilegeBean getFreeTimeTrialPrivilege() {
            return freeTimeTrialPrivilege;
        }

        public void setFreeTimeTrialPrivilege(FreeTimeTrialPrivilegeBean freeTimeTrialPrivilege) {
            this.freeTimeTrialPrivilege = freeTimeTrialPrivilege;
        }

        public int getUrlSource() {
            return urlSource;
        }

        public void setUrlSource(int urlSource) {
            this.urlSource = urlSource;
        }

        public static class FreeTrialPrivilegeBean {
            /**
             * resConsumable : false
             * userConsumable : false
             */

            private boolean resConsumable;
            private boolean userConsumable;

            public boolean isResConsumable() {
                return resConsumable;
            }

            public void setResConsumable(boolean resConsumable) {
                this.resConsumable = resConsumable;
            }

            public boolean isUserConsumable() {
                return userConsumable;
            }

            public void setUserConsumable(boolean userConsumable) {
                this.userConsumable = userConsumable;
            }
        }

        public static class FreeTimeTrialPrivilegeBean {
            /**
             * resConsumable : false
             * userConsumable : false
             * type : 0
             * remainTime : 0
             */

            private boolean resConsumable;
            private boolean userConsumable;
            private int type;
            private int remainTime;

            public boolean isResConsumable() {
                return resConsumable;
            }

            public void setResConsumable(boolean resConsumable) {
                this.resConsumable = resConsumable;
            }

            public boolean isUserConsumable() {
                return userConsumable;
            }

            public void setUserConsumable(boolean userConsumable) {
                this.userConsumable = userConsumable;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getRemainTime() {
                return remainTime;
            }

            public void setRemainTime(int remainTime) {
                this.remainTime = remainTime;
            }
        }
    }
}
