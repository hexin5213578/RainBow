package com.YiDian.RainBow.user;

import android.content.Intent;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.topic.SaveIntentMsgBean;

import java.io.Serializable;

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


        }else{
            int id = msg.getId();


        }
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }
}
