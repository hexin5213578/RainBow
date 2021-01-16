package com.YiDian.RainBow.main.fragment.mine.bean;

import java.io.Serializable;
import java.util.List;

public class GiftBean implements Serializable{


    /**
     * msg : 查询成功
     * type : OK
     * object : [{"id":null,"inUserId":null,"outUserId":1030,"giftId":20,"giftNum":null,"createTime":null,"giftImg":"http://img.rianbow.cn/202101130953576327300.png","giftName":"666","nums":59,"allNums":253},{"id":null,"inUserId":null,"outUserId":1030,"giftId":23,"giftNum":null,"createTime":null,"giftImg":"http://img.rianbow.cn/20210113095857537766.png","giftName":"爱的信封","nums":2,"allNums":253},{"id":null,"inUserId":null,"outUserId":1030,"giftId":28,"giftNum":null,"createTime":null,"giftImg":"http://img.rianbow.cn/202101131004014558933.png","giftName":"爱心气球","nums":9,"allNums":253},{"id":null,"inUserId":null,"outUserId":1030,"giftId":29,"giftNum":null,"createTime":null,"giftImg":"http://img.rianbow.cn/202101131005225008181.png","giftName":"穿云箭","nums":86,"allNums":253},{"id":null,"inUserId":null,"outUserId":1030,"giftId":37,"giftNum":null,"createTime":null,"giftImg":"http://img.rianbow.cn/20210113102014502499.png","giftName":"活力胶囊","nums":10,"allNums":253},{"id":null,"inUserId":null,"outUserId":1030,"giftId":43,"giftNum":null,"createTime":null,"giftImg":"http://img.rianbow.cn/202101131030586169130.png","giftName":"浪漫游轮","nums":3,"allNums":253},{"id":null,"inUserId":null,"outUserId":1030,"giftId":46,"giftNum":null,"createTime":null,"giftImg":"http://img.rianbow.cn/202101131035034579336.png","giftName":"猫爪","nums":2,"allNums":253},{"id":null,"inUserId":null,"outUserId":1030,"giftId":47,"giftNum":null,"createTime":null,"giftImg":"http://img.rianbow.cn/20210113103522202909.png","giftName":"玫瑰","nums":38,"allNums":253},{"id":null,"inUserId":null,"outUserId":1030,"giftId":48,"giftNum":null,"createTime":null,"giftImg":"http://img.rianbow.cn/202101131036024315910.png","giftName":"米老鼠","nums":2,"allNums":253},{"id":null,"inUserId":null,"outUserId":1030,"giftId":49,"giftNum":null,"createTime":null,"giftImg":"http://img.rianbow.cn/202101131036376666372.png","giftName":"魔法棒","nums":3,"allNums":253},{"id":null,"inUserId":null,"outUserId":1030,"giftId":50,"giftNum":null,"createTime":null,"giftImg":"http://img.rianbow.cn/202101131037006776021.png","giftName":"魔力可乐","nums":14,"allNums":253},{"id":null,"inUserId":null,"outUserId":1030,"giftId":54,"giftNum":null,"createTime":null,"giftImg":"http://img.rianbow.cn/202101131038593516397.png","giftName":"亲吻","nums":22,"allNums":253},{"id":null,"inUserId":null,"outUserId":1030,"giftId":60,"giftNum":null,"createTime":null,"giftImg":"http://img.rianbow.cn/20210113104301198883.png","giftName":"小火箭","nums":2,"allNums":253},{"id":null,"inUserId":null,"outUserId":1030,"giftId":61,"giftNum":null,"createTime":null,"giftImg":"http://img.rianbow.cn/202101131043206933509.png","giftName":"小可爱","nums":1,"allNums":253}]
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

    public static class ObjectBean implements Serializable {
        /**
         * id : null
         * inUserId : null
         * outUserId : 1030
         * giftId : 20
         * giftNum : null
         * createTime : null
         * giftImg : http://img.rianbow.cn/202101130953576327300.png
         * giftName : 666
         * nums : 59
         * allNums : 253
         */

        private Object id;
        private int inUserId;
        private int outUserId;
        private int giftId;
        private Object giftNum;
        private Object createTime;
        private String giftImg;
        private String giftName;
        private int nums;
        private int allNums;

        public Object getId() {
            return id;
        }

        public void setId(Object id) {
            this.id = id;
        }

        public int getInUserId() {
            return inUserId;
        }

        public void setInUserId(int inUserId) {
            this.inUserId = inUserId;
        }

        public int getOutUserId() {
            return outUserId;
        }

        public void setOutUserId(int outUserId) {
            this.outUserId = outUserId;
        }

        public int getGiftId() {
            return giftId;
        }

        public void setGiftId(int giftId) {
            this.giftId = giftId;
        }

        public Object getGiftNum() {
            return giftNum;
        }

        public void setGiftNum(Object giftNum) {
            this.giftNum = giftNum;
        }

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
            this.createTime = createTime;
        }

        public String getGiftImg() {
            return giftImg;
        }

        public void setGiftImg(String giftImg) {
            this.giftImg = giftImg;
        }

        public String getGiftName() {
            return giftName;
        }

        public void setGiftName(String giftName) {
            this.giftName = giftName;
        }

        public int getNums() {
            return nums;
        }

        public void setNums(int nums) {
            this.nums = nums;
        }

        public int getAllNums() {
            return allNums;
        }

        public void setAllNums(int allNums) {
            this.allNums = allNums;
        }
    }
}
