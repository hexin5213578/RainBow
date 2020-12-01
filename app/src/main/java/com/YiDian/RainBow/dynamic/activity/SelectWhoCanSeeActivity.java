package com.YiDian.RainBow.dynamic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.dynamic.bean.SaveWhoCanseeBean;
import com.leaf.library.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectWhoCanSeeActivity extends BaseAvtivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_back)
    LinearLayout ivBack;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;
    @BindView(R.id.rl_top)
    RelativeLayout rlTop;
    @BindView(R.id.rb1)
    RadioButton rb1;
    @BindView(R.id.rb2)
    RadioButton rb2;
    @BindView(R.id.rb3)
    RadioButton rb3;
    @BindView(R.id.rb4)
    RadioButton rb4;
    String str = "";
    @Override
    protected int getResId() {
        return R.layout.activity_select_who_cansee;
    }


    @Override
    protected void getData() {
        //设置状态栏颜色 跟状态栏字体颜色
        StatusBarUtil.setGradientColor(SelectWhoCanSeeActivity.this, toolbar);
        StatusBarUtil.setDarkMode(SelectWhoCanSeeActivity.this);

        //先获取当前的谁可以看
        Intent intent = getIntent();
        String msg = intent.getStringExtra("msg");

        if(msg.equals(rb1.getText().toString())){
            rb1.setChecked(true);
        }
        if(msg.equals(rb2.getText().toString())){
            rb2.setChecked(true);
        }
        if(msg.equals(rb3.getText().toString())){
            rb3.setChecked(true);
        }
        if(msg.equals(rb4.getText().toString())){
            rb4.setChecked(true);
        }

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rb1.isChecked()){
                   str = rb1.getText().toString();
                }
                if (rb2.isChecked()){
                    str = rb2.getText().toString();
                }
                if (rb3.isChecked()){
                    str = rb3.getText().toString();
                }
                if (rb4.isChecked()){
                    str = rb4.getText().toString();
                }
                Toast.makeText(SelectWhoCanSeeActivity.this, ""+str, Toast.LENGTH_SHORT).show();

                //将选中的结果发送到发布动态页
                SaveWhoCanseeBean saveWhoCanseeBean = new SaveWhoCanseeBean();
                saveWhoCanseeBean.setStr(str);
                EventBus.getDefault().post(saveWhoCanseeBean);

                //发送成功后关闭页面
                finish();
            }
        });
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

}
