package com.YiDian.RainBow.friend.fragment;

import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseFragment;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.imgroup.adapter.GroupMyCreateAdapter;
import com.YiDian.RainBow.imgroup.adapter.GroupMyJoinAdapter;
import com.YiDian.RainBow.imgroup.bean.MyCreateGroupMsgBean;
import com.YiDian.RainBow.imgroup.bean.MyJoinGroupMsgBean;
import com.YiDian.RainBow.utils.NetUtils;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FragmentGroup extends BaseFragment {
    @BindView(R.id.rl1)
    RelativeLayout rl1;
    @BindView(R.id.rc_mycreate)
    RecyclerView rcMycreate;
    @BindView(R.id.rl2)
    RelativeLayout rl2;
    @BindView(R.id.rc_myjoin)
    RecyclerView rcMyjoin;
    @BindView(R.id.rc_comment)
    RecyclerView rcComment;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.sv)
    SpringView sv;
    private int userid;
    String TAG = "xxx";

    @Override
    protected void getid(View view) {

    }

    @Override
    protected int getResId() {
        return R.layout.fragmentgroup;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void getData() {
        sv.setHeader(new AliHeader(getContext()));
        userid = Integer.valueOf(Common.getUserId());

        //首次进入获取数据
        getMyCreateGroup();
        getMyJoinGroup();

        sv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //刷新重新获取数据
                        getMyCreateGroup();
                        getMyJoinGroup();

                        sv.onFinishFreshAndLoad();
                    }
                },1000);
            }

            @Override
            public void onLoadmore() {

            }
        });
    }

    public void getRecommendGroup() {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getString(String str) {
        if (str.equals("重新获取群组列表")) {
            getMyJoinGroup();
            getMyCreateGroup();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    //获取我创建的群组
    public void getMyCreateGroup() {
        NetUtils.getInstance().getApis()
                .dogetMyJoinGroup(userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MyCreateGroupMsgBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(MyCreateGroupMsgBean myCreateGroupMsgBean) {
                        List<MyCreateGroupMsgBean.ObjectBean> list = myCreateGroupMsgBean.getObject();

                        if (list.size() > 0 && list != null) {
                            rl1.setVisibility(View.VISIBLE);
                            rcMycreate.setVisibility(View.VISIBLE);

                            tv1.setText("我创建的(" + list.size() + ")");

                            //创建布局管理器
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                            rcMycreate.setLayoutManager(linearLayoutManager);

                            GroupMyCreateAdapter myJoinAdapter = new GroupMyCreateAdapter(getContext(), list);
                            rcMycreate.setAdapter(myJoinAdapter);
                        } else {
                            rl1.setVisibility(View.GONE);
                            rcMycreate.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //获取我加入的列表
    public void getMyJoinGroup() {
        NetUtils.getInstance().getApis()
                .dogetMyCreateGroup(userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MyJoinGroupMsgBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(MyJoinGroupMsgBean myJoinGroupMsgBean) {
                        List<MyJoinGroupMsgBean.ObjectBean> list =
                                myJoinGroupMsgBean.getObject();

                        if (list.size() > 0 && list != null) {
                            rl2.setVisibility(View.VISIBLE);
                            rcMyjoin.setVisibility(View.VISIBLE);

                            tv2.setText("我加入的(" + list.size() + ")");

                            //创建布局管理器
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                            rcMyjoin.setLayoutManager(linearLayoutManager);

                            GroupMyJoinAdapter myCreateAdapter = new GroupMyJoinAdapter(getContext(), list);
                            rcMyjoin.setAdapter(myCreateAdapter);

                        } else {
                            rl2.setVisibility(View.GONE);
                            rcMyjoin.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
