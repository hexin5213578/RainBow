package com.YiDian.RainBow.dynamic.bean;

import java.util.List;

public class HotTopicBean {

    /**
     * msg : findHotTopic执行成功>>查找成功
     * type : OK
     * object : ["热门话题2","热门话题3","送你定你你你","热门话题1","热门话题4","磨具摸摸墨子","奥利给","推特宣布永久移除特朗普账号"]
     */

    private String msg;
    private String type;
    private List<String> object;

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

    public List<String> getObject() {
        return object;
    }

    public void setObject(List<String> object) {
        this.object = object;
    }
}
