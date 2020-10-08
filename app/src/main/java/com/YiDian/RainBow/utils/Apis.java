package com.YiDian.RainBow.utils;


import com.YiDian.RainBow.main.fragment.home.bean.NewDynamicBean;

import java.net.URL;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface Apis {
    //最新动态
    @GET("content/getContentByTime")
    Observable<NewDynamicBean> getNewDynamic(@Query("page")int page,@Query("pageSize")int pagesize);
}
