package com.YiDian.RainBow.user;

import android.content.Intent;
import android.util.Log;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.topic.SaveIntentMsgBean;

import java.io.Serializable;

//用户主页  头像点击进入
public class PersonHomeActivity extends BaseAvtivity {
    @Override
    protected int getResId() {
        return R.layout.activity_personhome;
    }

    @Override
    protected void getData() {
        //2标记传入姓名  1标记传入id
        Intent intent =
                getIntent();
        SaveIntentMsgBean msg = (SaveIntentMsgBean) intent.getSerializableExtra("msg");
        int flag = msg.getFlag();
        if(flag==2){
            String name = msg.getMsg();

            Log.d("xxx","传过来的姓名为"+name);

        }else{
            int id = msg.getId();

            Log.d("xxx","传过来的id为"+id);
        }
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }
}
