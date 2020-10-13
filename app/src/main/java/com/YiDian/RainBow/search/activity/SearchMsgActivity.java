package com.YiDian.RainBow.search.activity;

import android.util.Log;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;

//搜索信息展示页
public class SearchMsgActivity extends BaseAvtivity {
    @Override
    protected int getResId() {
        return R.layout.activity_searchmsg;
    }

    @Override
    protected void getData() {
      /*  JMessageClient.register("zhangsan", "111111", new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                Log.d("hmy","i为:"+i+"  s为:"+s);
            }
        });*/
      JMessageClient.login("zhangsan", "111111", new BasicCallback() {
          @Override
          public void gotResult(int i, String s) {
              Log.d("hmy","i为:"+i+"  s为:"+s);
          }
      });
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }
}
