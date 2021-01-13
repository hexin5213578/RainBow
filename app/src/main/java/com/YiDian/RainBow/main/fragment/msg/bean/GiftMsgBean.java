package com.YiDian.RainBow.main.fragment.msg.bean;

import java.util.List;

public class GiftMsgBean {

    /**
     * msg : 查询成功
     * type : OK
     * object : [{"id":47,"goldNum":500,"giftImg":"http://img.rianbow.cn/20210113103522202909.png","giftName":"玫瑰"},{"id":54,"goldNum":800,"giftImg":"http://img.rianbow.cn/202101131038593516397.png","giftName":"亲吻"},{"id":20,"goldNum":999,"giftImg":"http://img.rianbow.cn/202101130953576327300.png","giftName":"666"},{"id":26,"goldNum":1000,"giftImg":"http://img.rianbow.cn/202101131002366467456.png","giftName":"比心"},{"id":32,"goldNum":1000,"giftImg":"http://img.rianbow.cn/202101131010371231621.png","giftName":"点赞"},{"id":42,"goldNum":1000,"giftImg":"http://img.rianbow.cn/202101131030294186221.png","giftName":"蓝色妖姬"},{"id":50,"goldNum":1500,"giftImg":"http://img.rianbow.cn/202101131037006776021.png","giftName":"魔力可乐"},{"id":46,"goldNum":1888,"giftImg":"http://img.rianbow.cn/202101131035034579336.png","giftName":"猫爪"},{"id":66,"goldNum":1888,"giftImg":"http://img.rianbow.cn/202101131047227215596.png","giftName":"炸弹"},{"id":33,"goldNum":2000,"giftImg":"http://img.rianbow.cn/202101131010567139867.png","giftName":"风车"},{"id":39,"goldNum":2000,"giftImg":"http://img.rianbow.cn/20210113102150362385.png","giftName":"火腿"},{"id":53,"goldNum":2000,"giftImg":"http://img.rianbow.cn/202101131038266222506.png","giftName":"啤酒"},{"id":58,"goldNum":2000,"giftImg":"http://img.rianbow.cn/202101131041021659355.png","giftName":"甜甜圈"},{"id":68,"goldNum":2100,"giftImg":"http://img.rianbow.cn/202101131048204154764.png","giftName":"真爱钻戒"},{"id":21,"goldNum":2999,"giftImg":"http://img.rianbow.cn/202101130955243318957.png","giftName":"Diss"},{"id":36,"goldNum":3000,"giftImg":"http://img.rianbow.cn/202101131015127405088.png","giftName":"皇冠"},{"id":41,"goldNum":3000,"giftImg":"http://img.rianbow.cn/202101131029331877836.png","giftName":"金币"},{"id":48,"goldNum":3000,"giftImg":"http://img.rianbow.cn/202101131036024315910.png","giftName":"米老鼠"},{"id":49,"goldNum":3000,"giftImg":"http://img.rianbow.cn/202101131036376666372.png","giftName":"魔法棒"},{"id":27,"goldNum":3399,"giftImg":"http://img.rianbow.cn/202101131003175587787.png","giftName":"棒棒糖"},{"id":56,"goldNum":5200,"giftImg":"http://img.rianbow.cn/202101131040035846520.png","giftName":"丘比特之箭"},{"id":23,"goldNum":5210,"giftImg":"http://img.rianbow.cn/20210113095857537766.png","giftName":"爱的信封"},{"id":44,"goldNum":5210,"giftImg":"http://img.rianbow.cn/202101131032547358679.png","giftName":"恋爱白鸽"},{"id":61,"goldNum":6000,"giftImg":"http://img.rianbow.cn/202101131043206933509.png","giftName":"小可爱"},{"id":34,"goldNum":6888,"giftImg":"http://img.rianbow.cn/202101131011408461945.png","giftName":"鼓掌"},{"id":37,"goldNum":7000,"giftImg":"http://img.rianbow.cn/20210113102014502499.png","giftName":"活力胶囊"},{"id":28,"goldNum":8000,"giftImg":"http://img.rianbow.cn/202101131004014558933.png","giftName":"爱心气球"},{"id":64,"goldNum":8888,"giftImg":"http://img.rianbow.cn/202101131046132852607.png","giftName":"荧光球"},{"id":25,"goldNum":9999,"giftImg":"http://img.rianbow.cn/20210113100131256968.png","giftName":"彩虹礼炮"},{"id":65,"goldNum":9999,"giftImg":"http://img.rianbow.cn/202101131046543058765.png","giftName":"永恒钻石"},{"id":31,"goldNum":10000,"giftImg":"http://img.rianbow.cn/20210113100927322294.png","giftName":"蛋糕"},{"id":67,"goldNum":15000,"giftImg":"http://img.rianbow.cn/202101131047547168879.png","giftName":"真爱巧克力"},{"id":45,"goldNum":21000,"giftImg":"http://img.rianbow.cn/202101131034184297824.png","giftName":"琉璃钻戒"},{"id":35,"goldNum":28888,"giftImg":"http://img.rianbow.cn/202101131014125072206.png","giftName":"海洋之星"},{"id":55,"goldNum":30000,"giftImg":"http://img.rianbow.cn/202101131039232798848.png","giftName":"氢气球"},{"id":60,"goldNum":40000,"giftImg":"http://img.rianbow.cn/20210113104301198883.png","giftName":"小火箭"},{"id":52,"goldNum":55555,"giftImg":"http://img.rianbow.cn/202101131037506104842.png","giftName":"跑车"},{"id":30,"goldNum":66666,"giftImg":"http://img.rianbow.cn/202101131008257925435.png","giftName":"大锦鲤"},{"id":38,"goldNum":88888,"giftImg":"http://img.rianbow.cn/202101131020561772790.png","giftName":"火箭"},{"id":63,"goldNum":99999,"giftImg":"http://img.rianbow.cn/20210113104527685516.png","giftName":"星空探探"},{"id":69,"goldNum":158888,"giftImg":"http://img.rianbow.cn/202101131049033184294.png","giftName":"直升机"},{"id":43,"goldNum":199999,"giftImg":"http://img.rianbow.cn/202101131030586169130.png","giftName":"浪漫游轮"},{"id":29,"goldNum":288888,"giftImg":"http://img.rianbow.cn/202101131005225008181.png","giftName":"穿云箭"}]
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
         * id : 47
         * goldNum : 500
         * giftImg : http://img.rianbow.cn/20210113103522202909.png
         * giftName : 玫瑰
         */

        private int id;
        private int goldNum;
        private String giftImg;
        private String giftName;
        private boolean isSelected;

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getGoldNum() {
            return goldNum;
        }

        public void setGoldNum(int goldNum) {
            this.goldNum = goldNum;
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
    }
}
