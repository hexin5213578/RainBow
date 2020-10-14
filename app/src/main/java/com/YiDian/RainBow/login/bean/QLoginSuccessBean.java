package com.YiDian.RainBow.login.bean;

public class QLoginSuccessBean {

    /**
     * ret : 0
     * openid : 1495F2E42C4B30DEB48E49C18F1592C0
     * access_token : FE3B82F4A692923BFD8A854CDB46AB07
     * pay_token : 285241BA82A201521BF13039E8D4FDA8
     * expires_in : 7776000
     * code :
     * proxy_code :
     * proxy_expires_in : 0
     * pf : desktop_m_qq-10000144-android-2002-
     * pfkey : 7889430aae4528e57c72e76620a1f54c
     * msg :
     * login_cost : 26
     * query_authority_cost : -1781714020
     * authority_cost : 0
     * expires_time : 1610444904023
     */

    private int ret;
    private String openid;
    private String access_token;
    private String pay_token;
    private int expires_in;
    private String code;
    private String proxy_code;
    private int proxy_expires_in;
    private String pf;
    private String pfkey;
    private String msg;
    private int login_cost;
    private int query_authority_cost;
    private int authority_cost;
    private long expires_time;

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getPay_token() {
        return pay_token;
    }

    public void setPay_token(String pay_token) {
        this.pay_token = pay_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getProxy_code() {
        return proxy_code;
    }

    public void setProxy_code(String proxy_code) {
        this.proxy_code = proxy_code;
    }

    public int getProxy_expires_in() {
        return proxy_expires_in;
    }

    public void setProxy_expires_in(int proxy_expires_in) {
        this.proxy_expires_in = proxy_expires_in;
    }

    public String getPf() {
        return pf;
    }

    public void setPf(String pf) {
        this.pf = pf;
    }

    public String getPfkey() {
        return pfkey;
    }

    public void setPfkey(String pfkey) {
        this.pfkey = pfkey;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getLogin_cost() {
        return login_cost;
    }

    public void setLogin_cost(int login_cost) {
        this.login_cost = login_cost;
    }

    public int getQuery_authority_cost() {
        return query_authority_cost;
    }

    public void setQuery_authority_cost(int query_authority_cost) {
        this.query_authority_cost = query_authority_cost;
    }

    public int getAuthority_cost() {
        return authority_cost;
    }

    public void setAuthority_cost(int authority_cost) {
        this.authority_cost = authority_cost;
    }

    public long getExpires_time() {
        return expires_time;
    }

    public void setExpires_time(long expires_time) {
        this.expires_time = expires_time;
    }
}
