package com.YiDian.RainBow.dynamic.activity;

import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.dynamic.adapter.FriendAdapter;
import com.YiDian.RainBow.dynamic.bean.SaveAiteBean;
import com.YiDian.RainBow.dynamic.bean.SaveTestBean;
import com.leaf.library.StatusBarUtil;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SelectFriendActivity extends BaseAvtivity implements View.OnClickListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_back)
    LinearLayout ivBack;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;
    @BindView(R.id.rl_top)
    RelativeLayout rlTop;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.ll_search)
    LinearLayout llSearch;
    @BindView(R.id.rc_friend)
    RecyclerView rcFriend;
    @BindView(R.id.sv)
    SpringView sv;
    @BindView(R.id.et_text)
    EditText etText;
    String str = "";
    private List<SaveTestBean.ResultBean> list;

    @Override
    protected int getResId() {
        return R.layout.activity_selectfriend;
    }

    @Override
    protected void getData() {
        //设置状态栏颜色及字体颜色
        StatusBarUtil.setGradientColor(SelectFriendActivity.this, toolbar);
        StatusBarUtil.setDarkMode(SelectFriendActivity.this);
        sv.setHeader(new AliHeader(SelectFriendActivity.this));
        //下拉刷新监听
        sv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        sv.onFinishFreshAndLoad();
                    }
                }, 1000);
            }

            @Override
            public void onLoadmore() {

            }
        });
        ivBack.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);
        tvSearch.setOnClickListener(this);
        list = new ArrayList<>();

        for (int i =0;i<10;i++){
            SaveTestBean.ResultBean resultBean = new SaveTestBean.ResultBean();
            resultBean.setName("何梦洋"+i);
            resultBean.setIscheck(false);
            list.add(resultBean);
        }

        //创建布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcFriend.setLayoutManager(linearLayoutManager);
        //创建适配器
        FriendAdapter friendAdapter = new FriendAdapter(this, list);
        //设置适配器
        rcFriend.setAdapter(friendAdapter);
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void calculationCountAndPrice(String i) {
        str = "";
        for (SaveTestBean.ResultBean bean : list) {
            if (bean.isIscheck()){
                str+=bean.getName()+",";
            }
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_confirm:
                if(str.equals("")){
                    Toast.makeText(this, "请选择要@的好友", Toast.LENGTH_SHORT).show();
                }else{
                    //将str发送到 发布动态页
                    SaveAiteBean saveAiteBean = new SaveAiteBean();
                    saveAiteBean.setString(str);
                    EventBus.getDefault().post(saveAiteBean);

                    finish();
                }
                break;
            case R.id.tv_search:
                String name = etText.getText().toString();
                //调用搜索功能


                //展示搜索到的内容
                break;
        }
    }
}
