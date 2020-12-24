package com.YiDian.RainBow.main.fragment.find.bean;

import java.util.List;

public class UserMySeeBean {

    /**
     * msg : 查询成功
     * type : OK
     * object : [{"id":null,"userId":1030,"likeType":1,"createTime":"2020-12-24 14:00:07","nickName":"重复认同感很牛逼","headImg":"http://img.rianbow.cn/202012171106214632768.jpg","explains":"喔、卟説·~伱\u2018*卟懂\u2019\u2018！","isFans":0,"buserId":1066},{"id":null,"userId":1030,"likeType":1,"createTime":"2020-12-24 13:59:36","nickName":"色弱GV","headImg":"http://img.rianbow.cn/202012091311039645114.jpg","explains":"喔、卟説·~伱\u2018*卟懂\u2019\u2018！","isFans":0,"buserId":1062},{"id":null,"userId":1030,"likeType":1,"createTime":"2020-12-24 13:58:49","nickName":"11","headImg":"http://img.rianbow.cn/20201209120030244035.jpg","explains":"喔、卟説·~伱\u2018*卟懂\u2019\u2018！","isFans":0,"buserId":1059},{"id":null,"userId":1030,"likeType":1,"createTime":"2020-12-24 13:55:29","nickName":"搜嘎","headImg":"http://img.rianbow.cn/202012091202481289490.jpg","explains":"喔、卟説·~伱\u2018*卟懂\u2019\u2018！","isFans":0,"buserId":1046},{"id":null,"userId":1030,"likeType":1,"createTime":"2020-12-24 13:55:18","nickName":"北方","headImg":"http://img.rianbow.cn/202012091312202833963.jpg","explains":"喔、卟説·~伱\u2018*卟懂\u2019\u2018！","isFans":0,"buserId":1038},{"id":null,"userId":1030,"likeType":1,"createTime":"2020-12-24 11:07:13","nickName":"hhhaaa","headImg":"http://img.rianbow.cn/20201209130924997312.jpg","explains":"喔、卟説·~伱\u2018*卟懂\u2019\u2018！","isFans":0,"buserId":1032}]
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
         * id : null
         * userId : 1030
         * likeType : 1
         * createTime : 2020-12-24 14:00:07
         * nickName : 重复认同感很牛逼
         * headImg : http://img.rianbow.cn/202012171106214632768.jpg
         * explains : 喔、卟説·~伱‘*卟懂’‘！
         * isFans : 0
         * buserId : 1066
         */

        private Object id;
        private int userId;
        private int likeType;
        private String createTime;
        private String nickName;
        private String headImg;
        private String explains;
        private int isFans;
        private int buserId;

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

        public int getLikeType() {
            return likeType;
        }

        public void setLikeType(int likeType) {
            this.likeType = likeType;
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

        public String getExplains() {
            return explains;
        }

        public void setExplains(String explains) {
            this.explains = explains;
        }

        public int getIsFans() {
            return isFans;
        }

        public void setIsFans(int isFans) {
            this.isFans = isFans;
        }

        public int getBuserId() {
            return buserId;
        }

        public void setBuserId(int buserId) {
            this.buserId = buserId;
        }
    }
}
