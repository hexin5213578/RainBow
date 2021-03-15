package com.YiDian.RainBow.main.fragment.find.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.YiDian.RainBow.main.fragment.find.bean.FindUserMsgBean;
import com.YiDian.RainBow.user.bean.UserMsgBean;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Random;

import me.haowen.soulplanet.adapter.PlanetAdapter;
import me.haowen.soulplanet.utils.SizeUtils;
import me.haowen.soulplanet.view.PlanetView;

public class TestAdapter extends PlanetAdapter {

    private List<FindUserMsgBean.ObjectBean> list;
    private FindUserMsgBean.ObjectBean bean;

    public TestAdapter(List<FindUserMsgBean.ObjectBean> list) {

        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public View getView(Context context, int position, ViewGroup parent) {
        PlanetView planetView = new PlanetView(context);


        bean = list.get(position);
        Log.d("xxx","当前下标为"+position);
        planetView.setSign(bean.getNickName());
        Log.d("xxx","当前昵称为"+bean.getNickName());

        int starColor = 0;
        boolean hasShadow = false;

        String str = "";
        Log.d("xxx","当前角色为"+bean.getUserRole());
        if (bean.getUserRole().equals("T")) {


            str = "T";
            starColor = PlanetView.COLOR_T;
        } else if (bean.getUserRole().equals("P")) {


            str = "P";
            starColor = PlanetView.COLOR_P;
        } else if (bean.getUserRole().equals("H")) {


            str = "H";
            starColor = PlanetView.COLOR_H;
        } else if (bean.getUserRole().equals("BI")) {
           // hasShadow = true;
            str = "BI";
            starColor = PlanetView.COLOR_BI;
        } else {
            str = "保密";
            starColor = PlanetView.COLOR_BAOMI;
        }
        planetView.setStarColor(starColor);
        planetView.setHasShadow(hasShadow);
        planetView.setMatch("", str);
        if (hasShadow) {
            planetView.setMatchColor(starColor);
        } else {
            planetView.setMatchColor(starColor);
        }
        int starWidth = SizeUtils.dp2px(context, 50.0f);
        int starHeight = SizeUtils.dp2px(context, 85.0f);
        int starPaddingTop = SizeUtils.dp2px(context, 20.0f);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(starWidth, starHeight);
        planetView.setPadding(0, starPaddingTop, 0, 0);
        planetView.setLayoutParams(layoutParams);
        return planetView;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getPopularity(int position) {
        return position % 10;
    }

    @Override
    public void onThemeColorChanged(View view, int themeColor) {

    }
}