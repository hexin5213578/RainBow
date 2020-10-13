package com.YiDian.RainBow.utils;


import com.YiDian.RainBow.login.bean.LoginBean;
import com.YiDian.RainBow.main.fragment.home.bean.NewDynamicBean;
import com.YiDian.RainBow.regist.bean.RegistBean;
import com.YiDian.RainBow.setpwd.bean.GetPhoneCodeBean;

import java.net.URL;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface Apis {
    //最新动态
    @GET("content/getContentByTime")
    Observable<NewDynamicBean> getNewDynamic(@Query("page")int page,@Query("pageSize")int pagesize);

    //发送验证码
    @GET("user/sendSms")
    Observable<GetPhoneCodeBean> getPhoneCode(@Query("phoneNum")String phone);

    //注册
    @GET("user/register")
    Observable<RegistBean> doRegist(@Query("phoneNum")String phone,@Query("password")String pwd);

    //密码登录
    @GET("user/userLogin")
    Observable<LoginBean> doPwdLogin(@Query("phoneNum")String phone,@Query("password")String pwd,@Query("accountType") int type,@Query("lng")double lng,@Query("lat")double lat);

    //QQ登录
    @GET("user/userLogin")
    Observable<LoginBean> doQqLogin(@Query("accountType") int type,@Query("qqOpenID")String qqId,@Query("lng")double lng,@Query("lat")double lat);

    //微信登录
    @GET("user/userLogin")
    Observable<LoginBean> doWechatLogin(@Query("accountType") int type,@Query("weChatOpenId")String wechatId,@Query("lng")double lng,@Query("lat")double lat);

}
