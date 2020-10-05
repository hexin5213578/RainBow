package com.YiDian.RainBow.welcome;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;

public class MainActivity extends BaseAvtivity{

    @Override
    protected int getResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void getData() {

    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }
}