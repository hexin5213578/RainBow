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
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.dynamic.adapter.FriendAdapter;
import com.YiDian.RainBow.dynamic.bean.SaveAiteBean;
import com.YiDian.RainBow.dynamic.bean.SelectFriendOrGroupBean;
import com.YiDian.RainBow.friend.bean.FriendBean;
import com.YiDian.RainBow.utils.NetUtils;
import com.leaf.library.StatusBarUtil;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SelectFriendActivity extends BaseAvtivity implements View.OnClickListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_back)
    LinearLayout ivBack;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;
    @BindView(R.id.rl_top)
    RelativeLayout rlTop;
    @BindView(R.id.rc_friend)
    RecyclerView rcFriend;
    @BindView(R.id.sv)
    SpringView sv;
    String str = "";
    @BindView(R.id.rl_nodata)
    RelativeLayout rlNodata;
    private List<FriendBean.ObjectBean> list;
    private int userid;
    private List<SelectFriendOrGroupBean.ObjectBean.UserListBean> searchlist;

    @Override
    protected int getResId() {
        return R.layout.activity_selectfriend;
    }

    @Override
    protected void getData() {

        userid = Integer.valueOf(Common.getUserId());
        //设置状态栏颜色及字体颜色
        StatusBarUtil.setGradientColor(SelectFriendActivity.this, toolbar);
        StatusBarUtil.setDarkMode(SelectFriendActivity.this);

        getFriend();

        //下拉刷新监听
        sv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                getFriend();

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
        list = new ArrayList<>();
        searchlist = new ArrayList<>();
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    public void getFriend() {
        //获取我的好友列表
        showDialog();
        NetUtils.getInstance().getApis().doGetMyFriend(userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FriendBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(FriendBean friendBean) {
                        hideDialog();
                        list = friendBean.getObject();
                        if (friendBean.getMsg().equals("查询成功")) {
                            if (list != null && list.size() > 0) {
                                sv.setHeader(new AliHeader(SelectFriendActivity.this));


                                sv.setVisibility(View.VISIBLE);
                                tvConfirm.setVisibility(View.VISIBLE);
                                rlNodata.setVisibility(View.GONE);
                                //创建布局管理器
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SelectFriendActivity.this, RecyclerView.VERTICAL, false);
                                rcFriend.setLayoutManager(linearLayoutManager);
                                //创建适配器
                                FriendAdapter friendAdapter = new FriendAdapter(SelectFriendActivity.this, list);
                                //设置适配器
                                rcFriend.setAdapter(friendAdapter);

                            } else {
                                sv.setVisibility(View.GONE);
                                tvConfirm.setVisibility(View.GONE);
                                rlNodata.setVisibility(View.VISIBLE);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideDialog();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void calculationCountAndPrice(String i) {
        if (i.equals("发送个通知")){
            str = "";
            for (FriendBean.ObjectBean bean : list) {
                if (bean.isIscheck()) {
                    str += bean.getNickName() + ",";
                }
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
                if (str.equals("")) {
                    Toast.makeText(this, "请选择要@的好友", Toast.LENGTH_SHORT).show();
                } else {
                    //将str发送到 发布动态页
                    SaveAiteBean saveAiteBean = new SaveAiteBean();
                    saveAiteBean.setString(str);
                    EventBus.getDefault().post(saveAiteBean);

                    finish();
                }
                break;
        }
    }
}
