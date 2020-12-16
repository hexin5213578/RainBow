package com.YiDian.RainBow.topic;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;

public class TopicDetailsActivity extends BaseAvtivity {
    @Override
    protected int getResId() {
        return R.layout.activity_topic_details;
    }

    @Override
    protected void getData() {
        //2标记传入话题名  1标记传入id

    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }
}
