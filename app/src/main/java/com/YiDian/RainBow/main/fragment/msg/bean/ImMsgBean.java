package com.YiDian.RainBow.main.fragment.msg.bean;

public class ImMsgBean {

    /**
     * set_from_name : 0
     * d : 0
     * e : 0
     * f : 1608691836842
     * _id : 3
     * content : {"text":"奥术大师阿萨德","booleanExtras":{},"contentType":"text","extras":{},"numExtras":{},"stringExtras":{}}
     * contentType : text
     * createTimeInMillis : 1608691836842
     * create_time : 1608691836
     * direct : send
     * from_appkey : 87ce5706efafab51ddd2be08
     * from_id : 1038
     * from_type : user
     * from_platform : a
     * msg_type : text
     * serverMessageId : 9812468448
     * status : send_success
     * sui_mtime : 0
     * targetInfo : {"address":"","appkey":"87ce5706efafab51ddd2be08","avatar":"qiniu/image/a/1ACC3B74DDE3174F49DDC63C1978F8E3","birthday":"","blacklist":0,"extras":{},"gender":"0","isFriend":0,"mGender":"unknown","mtime":1610088795,"nickname":"何梦洋","noDisturb":0,"memo_others":"","memo_name":"","region":"","signature":"","star":-1,"uid":593215420,"username":"1030"}
     * target_type : single
     * version : 1
     */

    private int set_from_name;
    private int d;
    private int e;
    private long f;
    private int _id;
    private ContentBean content;
    private String contentType;
    private long createTimeInMillis;
    private int create_time;
    private String direct;
    private String from_name;
    private String from_appkey;
    private String from_id;
    private String from_type;
    private String from_platform;
    private String msg_type;
    private long serverMessageId;
    private String status;
    private int sui_mtime;
    private TargetInfoBean targetInfo;
    private String target_type;
    private int version;

    public String getFrom_name() {
        return from_name;
    }

    public void setFrom_name(String from_name) {
        this.from_name = from_name;
    }

    public int getSet_from_name() {
        return set_from_name;
    }

    public void setSet_from_name(int set_from_name) {
        this.set_from_name = set_from_name;
    }

    public int getD() {
        return d;
    }

    public void setD(int d) {
        this.d = d;
    }

    public int getE() {
        return e;
    }

    public void setE(int e) {
        this.e = e;
    }

    public long getF() {
        return f;
    }

    public void setF(long f) {
        this.f = f;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public long getCreateTimeInMillis() {
        return createTimeInMillis;
    }

    public void setCreateTimeInMillis(long createTimeInMillis) {
        this.createTimeInMillis = createTimeInMillis;
    }

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }

    public String getDirect() {
        return direct;
    }

    public void setDirect(String direct) {
        this.direct = direct;
    }

    public String getFrom_appkey() {
        return from_appkey;
    }

    public void setFrom_appkey(String from_appkey) {
        this.from_appkey = from_appkey;
    }

    public String getFrom_id() {
        return from_id;
    }

    public void setFrom_id(String from_id) {
        this.from_id = from_id;
    }

    public String getFrom_type() {
        return from_type;
    }

    public void setFrom_type(String from_type) {
        this.from_type = from_type;
    }

    public String getFrom_platform() {
        return from_platform;
    }

    public void setFrom_platform(String from_platform) {
        this.from_platform = from_platform;
    }

    public String getMsg_type() {
        return msg_type;
    }

    public void setMsg_type(String msg_type) {
        this.msg_type = msg_type;
    }

    public long getServerMessageId() {
        return serverMessageId;
    }

    public void setServerMessageId(long serverMessageId) {
        this.serverMessageId = serverMessageId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getSui_mtime() {
        return sui_mtime;
    }

    public void setSui_mtime(int sui_mtime) {
        this.sui_mtime = sui_mtime;
    }

    public TargetInfoBean getTargetInfo() {
        return targetInfo;
    }

    public void setTargetInfo(TargetInfoBean targetInfo) {
        this.targetInfo = targetInfo;
    }

    public String getTarget_type() {
        return target_type;
    }

    public void setTarget_type(String target_type) {
        this.target_type = target_type;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public static class ContentBean {
        /**
         * text : 奥术大师阿萨德
         * booleanExtras : {}
         * contentType : text
         * extras : {}
         * numExtras : {}
         * stringExtras : {}
         */

        private String text;
        private BooleanExtrasBean booleanExtras;
        private String contentType;
        private ExtrasBean extras;
        private NumExtrasBean numExtras;
        private StringExtrasBean stringExtras;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public BooleanExtrasBean getBooleanExtras() {
            return booleanExtras;
        }

        public void setBooleanExtras(BooleanExtrasBean booleanExtras) {
            this.booleanExtras = booleanExtras;
        }

        public String getContentType() {
            return contentType;
        }

        public void setContentType(String contentType) {
            this.contentType = contentType;
        }

        public ExtrasBean getExtras() {
            return extras;
        }

        public void setExtras(ExtrasBean extras) {
            this.extras = extras;
        }

        public NumExtrasBean getNumExtras() {
            return numExtras;
        }

        public void setNumExtras(NumExtrasBean numExtras) {
            this.numExtras = numExtras;
        }

        public StringExtrasBean getStringExtras() {
            return stringExtras;
        }

        public void setStringExtras(StringExtrasBean stringExtras) {
            this.stringExtras = stringExtras;
        }

        public static class BooleanExtrasBean {
        }

        public static class ExtrasBean {
        }

        public static class NumExtrasBean {
        }

        public static class StringExtrasBean {
        }
    }

    public static class TargetInfoBean {
        /**
         * address :
         * appkey : 87ce5706efafab51ddd2be08
         * avatar : qiniu/image/a/1ACC3B74DDE3174F49DDC63C1978F8E3
         * birthday :
         * blacklist : 0
         * extras : {}
         * gender : 0
         * isFriend : 0
         * mGender : unknown
         * mtime : 1610088795
         * nickname : 何梦洋
         * noDisturb : 0
         * memo_others :
         * memo_name :
         * region :
         * signature :
         * star : -1
         * uid : 593215420
         * username : 1030
         */

        private String address;
        private String appkey;
        private String avatar;
        private String birthday;
        private int blacklist;
        private ExtrasBeanX extras;
        private String gender;
        private int isFriend;
        private String mGender;
        private int mtime;
        private String nickname;
        private int noDisturb;
        private String memo_others;
        private String memo_name;
        private String region;
        private String signature;
        private int star;
        private int uid;
        private String username;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAppkey() {
            return appkey;
        }

        public void setAppkey(String appkey) {
            this.appkey = appkey;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public int getBlacklist() {
            return blacklist;
        }

        public void setBlacklist(int blacklist) {
            this.blacklist = blacklist;
        }

        public ExtrasBeanX getExtras() {
            return extras;
        }

        public void setExtras(ExtrasBeanX extras) {
            this.extras = extras;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public int getIsFriend() {
            return isFriend;
        }

        public void setIsFriend(int isFriend) {
            this.isFriend = isFriend;
        }

        public String getMGender() {
            return mGender;
        }

        public void setMGender(String mGender) {
            this.mGender = mGender;
        }

        public int getMtime() {
            return mtime;
        }

        public void setMtime(int mtime) {
            this.mtime = mtime;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getNoDisturb() {
            return noDisturb;
        }

        public void setNoDisturb(int noDisturb) {
            this.noDisturb = noDisturb;
        }

        public String getMemo_others() {
            return memo_others;
        }

        public void setMemo_others(String memo_others) {
            this.memo_others = memo_others;
        }

        public String getMemo_name() {
            return memo_name;
        }

        public void setMemo_name(String memo_name) {
            this.memo_name = memo_name;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public int getStar() {
            return star;
        }

        public void setStar(int star) {
            this.star = star;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public static class ExtrasBeanX {
        }
    }
}
