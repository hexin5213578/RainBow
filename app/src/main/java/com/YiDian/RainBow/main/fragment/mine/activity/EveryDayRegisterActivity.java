package com.YiDian.RainBow.main.fragment.mine.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.leaf.library.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EveryDayRegisterActivity extends BaseAvtivity implements View.OnClickListener {


    @BindView(R.id.tv_days)
    TextView tvDays;
    @BindView(R.id.tv_zhouyi)
    TextView tvZhouyi;
    @BindView(R.id.tv_zhouyi_count)
    TextView tvZhouyiCount;
    @BindView(R.id.rl_zhouyi)
    RelativeLayout rlZhouyi;
    @BindView(R.id.tv_zhouer)
    TextView tvZhouer;
    @BindView(R.id.tv_zhouer_count)
    TextView tvZhouerCount;
    @BindView(R.id.rl_zhouer)
    RelativeLayout rlZhouer;
    @BindView(R.id.tv_zhousan)
    TextView tvZhousan;
    @BindView(R.id.tv_zhousan_count)
    TextView tvZhousanCount;
    @BindView(R.id.rl_zhousan)
    RelativeLayout rlZhousan;
    @BindView(R.id.tv_zhousi)
    TextView tvZhousi;
    @BindView(R.id.tv_zhousi_count)
    TextView tvZhousiCount;
    @BindView(R.id.rl_zhousi)
    RelativeLayout rlZhousi;
    @BindView(R.id.tv_zhouwu)
    TextView tvZhouwu;
    @BindView(R.id.tv_zhouwu_count)
    TextView tvZhouwuCount;
    @BindView(R.id.rl_zhouwu)
    RelativeLayout rlZhouwu;
    @BindView(R.id.tv_zhouliu)
    TextView tvZhouliu;
    @BindView(R.id.tv_zhouliu_count)
    TextView tvZhouliuCount;
    @BindView(R.id.rl_zhouliu)
    RelativeLayout rlZhouliu;
    @BindView(R.id.tv_zhouri)
    TextView tvZhouri;
    @BindView(R.id.tv_zhouri_count)
    TextView tvZhouriCount;
    @BindView(R.id.rl_zhouri)
    RelativeLayout rlZhouri;
    @BindView(R.id.bt_qiandao)
    Button btQiandao;
    @BindView(R.id.rc_task)
    RecyclerView rcTask;

    /**
     * tv _zhouyi  天数 未签到换成补签
     * rl_zhouyi   每日签到的背景
     * tv_zhouyi count  当天签到金币数
     */
    @Override
    protected int getResId() {
        return R.layout.activity_everydayregister;
    }

    @Override
    protected void getData() {
        //设置状态栏透明
        StatusBarUtil.setTransparentForWindow(EveryDayRegisterActivity.this);


    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
