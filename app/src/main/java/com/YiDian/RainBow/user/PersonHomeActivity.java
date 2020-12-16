package com.YiDian.RainBow.user;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;

public class PersonHomeActivity extends BaseAvtivity {
    @Override
    protected int getResId() {
        return R.layout.activity_personhome;
    }

    @Override
    protected void getData() {
        //2标记传入姓名  1标记传入id


    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }
}
