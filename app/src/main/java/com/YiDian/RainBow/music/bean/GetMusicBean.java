package com.YiDian.RainBow.music.bean;

import java.util.List;

public class GetMusicBean {

    /**
     * msg : 查询成功
     * type : FAILED
     * object : [{"id":8,"music_id":1833316854,"music_name":"阳关引","music_img":"http://p1.music.126.net/gklpYDBGYaMTap26RCUAFQ==/109951165844596556.jpg","music_auth":"王晰"},{"id":9,"music_id":1831767639,"music_name":"我能给的幸福","music_img":"http://p1.music.126.net/W5iJ7pbQqn5OpBhtV_6OcA==/109951165827579431.jpg","music_auth":"阿云嘎"},{"id":10,"music_id":1832582017,"music_name":"LMLY","music_img":"http://p1.music.126.net/rHGXqUJsmM6hRiOL3qTdyQ==/109951165834970489.jpg","music_auth":"王嘉尔"},{"id":11,"music_id":1818844870,"music_name":"Bicycle","music_img":"http://p1.music.126.net/84n0y9njKXCygmsVIdr7kg==/109951165709642799.jpg","music_auth":"金请夏"},{"id":12,"music_id":1462342189,"music_name":"我想我不一样","music_img":"http://p1.music.126.net/P6L2FaJJ44nOoT2PkwhBBQ==/109951165835638734.jpg","music_auth":"张紫宁"},{"id":13,"music_id":1826189041,"music_name":"花,太阳,彩虹,你","music_img":"http://p1.music.126.net/6EX0yj-r5GBLzSNU20rrmQ==/109951165791933542.jpg","music_auth":"Mai"},{"id":14,"music_id":1830383343,"music_name":"越人歌","music_img":"http://p1.music.126.net/0QYCXHgm_WUT6Qc5haI7EQ==/109951165813544165.jpg","music_auth":"谭维维"},{"id":15,"music_id":1810021934,"music_name":"红黑","music_img":"http://p1.music.126.net/-EHFGXVwLwy7ra48lDKMfg==/109951165611159240.jpg","music_auth":"蔡文静"},{"id":16,"music_id":1830423322,"music_name":"因一个人而流出一滴泪","music_img":"http://p1.music.126.net/6XXQfU9JR7ZgrcIWbNgKRw==/109951165813944828.jpg","music_auth":"莫文蔚"},{"id":17,"music_id":1830066193,"music_name":"今天的我已经打烊","music_img":"http://p1.music.126.net/WKpANHmD3qE60RX9szS7vQ==/109951165811087237.jpg","music_auth":"NINEONE#"},{"id":18,"music_id":1830664230,"music_name":"White Dress","music_img":"http://p1.music.126.net/hF0SjZKifDLGlc1OS3sj9A==/109951165816364532.jpg","music_auth":"Lana Del Rey"},{"id":19,"music_id":1823175189,"music_name":"M.I.A (feat. Jackson Wang)","music_img":"http://p1.music.126.net/BzGpf6VxpVVXKHWjDPeILA==/109951165778552172.jpg","music_auth":"Afgan"},{"id":20,"music_id":1833633769,"music_name":"超能力","music_img":"http://p1.music.126.net/CaQIm9X9xy9CMhc86e3qaA==/109951165847509270.jpg","music_auth":"G.E.M.邓紫棋"},{"id":21,"music_id":1823305772,"music_name":"Promise","music_img":"http://p1.music.126.net/U_asi9eKVwEy39_uMSi7og==/109951165762388799.jpg","music_auth":"HARIKIRI"},{"id":22,"music_id":1826534578,"music_name":"多多","music_img":"http://p1.music.126.net/c8BxDKnZE6hQdroRSPnkWA==/109951165794509412.jpg","music_auth":"窦靖童"}]
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
         * id : 8
         * music_id : 1833316854
         * music_name : 阳关引
         * music_img : http://p1.music.126.net/gklpYDBGYaMTap26RCUAFQ==/109951165844596556.jpg
         * music_auth : 王晰
         */

        private int id;
        private int music_id;
        private String music_name;
        private String music_img;
        private String music_auth;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getMusic_id() {
            return music_id;
        }

        public void setMusic_id(int music_id) {
            this.music_id = music_id;
        }

        public String getMusic_name() {
            return music_name;
        }

        public void setMusic_name(String music_name) {
            this.music_name = music_name;
        }

        public String getMusic_img() {
            return music_img;
        }

        public void setMusic_img(String music_img) {
            this.music_img = music_img;
        }

        public String getMusic_auth() {
            return music_auth;
        }

        public void setMusic_auth(String music_auth) {
            this.music_auth = music_auth;
        }
    }
}
