package com.YiDian.RainBow.topic;

import android.content.Intent;
import android.util.Log;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;

//话题详情界面
public class TopicDetailsActivity extends BaseAvtivity {
    @Override
    protected int getResId() {
        return R.layout.activity_topic_details;
    }

    @Override
    protected void getData() {
        //2标记传入话题名  1标记传入id
        Intent intent =
                getIntent();
        SaveIntentMsgBean msg = (SaveIntentMsgBean) intent.getSerializableExtra("msg");
        int flag = msg.getFlag();
        if(flag==2){
            String name = msg.getMsg();

            Log.d("xxx","传过来的话题名为"+name);

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
