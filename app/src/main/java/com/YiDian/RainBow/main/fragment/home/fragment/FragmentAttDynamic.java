package com.YiDian.RainBow.main.fragment.home.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseFragment;
import com.YiDian.RainBow.base.BasePresenter;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

//关注动态
public class FragmentAttDynamic extends BaseFragment {
    @BindView(R.id.no_att_img)
    ImageView noAttImg;
    @BindView(R.id.weiguanzhu)
    TextView weiguanzhu;
    @BindView(R.id.rl_no_att)
    RelativeLayout rlNoAtt;
    @BindView(R.id.bt_go_att)
    Button btGoAtt;

    @Override
    protected void getid(View view) {

    }

    @Override
    protected int getResId() {
        return R.layout.home_home_fragment_attdynamic;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void getData() {
        btGoAtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post("跳转到匹配页");
            }
        });
    }
}
