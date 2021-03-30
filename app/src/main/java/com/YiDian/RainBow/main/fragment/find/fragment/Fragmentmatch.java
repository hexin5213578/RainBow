package com.YiDian.RainBow.main.fragment.find.fragment;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseFragment;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.custom.loading.CustomDialog;
import com.YiDian.RainBow.friend.bean.RecommendUserBean;
import com.YiDian.RainBow.main.fragment.find.adapter.RecommendFriendAdapter;
import com.YiDian.RainBow.main.fragment.find.adapter.TestAdapter;
import com.YiDian.RainBow.main.fragment.find.bean.FindUserMsgBean;
import com.YiDian.RainBow.main.fragment.find.bean.SaveFilterBean;
import com.YiDian.RainBow.topic.SaveIntentMsgBean;
import com.YiDian.RainBow.user.PersonHomeActivity;
import com.YiDian.RainBow.utils.NetUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.haowen.soulplanet.view.SoulPlanetsView;

//大球页面
public class Fragmentmatch extends BaseFragment {

    @BindView(R.id.soulPlanetView)
    SoulPlanetsView soulPlanetView;
    @BindView(R.id.rc_recommend)
    RecyclerView rcRecommend;
    private CustomDialog dialog1;
    private int userid;
    private List<FindUserMsgBean.ObjectBean> list;
    //实例化一个Handler，并复写handlerMessage方法
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {//这句就不做说明了，都能看懂
                case 1:
                    //实例化时一个TimerTask实力类，调用构造函数，并执行run方法，测试用，看方法是否定时执行
                    getRecommendFriend();
                    break;
            }
            super.handleMessage(msg);
        }

    };
    private Timer timer;

    @Override
    protected void getid(View view) {

    }

    @Override
    protected int getResId() {
        return R.layout.home_find_fragment_match;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }
    //实例TimerTask,在run函数中向hangdler发送消息
    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }
    };
    @Override
    protected void getData() {
        //从网络获取数据表后将其中的列表存放在这个链表中
        dialog1 = new CustomDialog(getContext(), "正在加载...");
        userid = Integer.valueOf(Common.getUserId());

        //获取球内用户
        getUserMsg();
        //获取推荐用户
        getRecommendFriend();

        //实例化一个定时器
        timer = new Timer();
        timer.schedule(timerTask, 0, 1000*60);

        //条目点击
        soulPlanetView.setOnTagClickListener(new SoulPlanetsView.OnTagClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, int position) {
                Intent intent = new Intent(getContext(), PersonHomeActivity.class);
                SaveIntentMsgBean saveIntentMsgBean = new SaveIntentMsgBean();
                saveIntentMsgBean.setId(list.get(position).getId());
                //2标记传入姓名  1标记传入id
                saveIntentMsgBean.setFlag(1);
                intent.putExtra("msg", saveIntentMsgBean);
                startActivity(intent);
            }
        });
    }

    /**
     *  获取推荐用户
     */
    public void getRecommendFriend(){
        NetUtils.getInstance().getApis()
                .dogetRecommendFriend(userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RecommendUserBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull RecommendUserBean recommendUserBean) {
                        List<RecommendUserBean.ObjectBean> list = recommendUserBean.getObject();

                        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL,false);
                        rcRecommend.setLayoutManager(gridLayoutManager);

                        RecommendFriendAdapter recommendFriendAdapter = new RecommendFriendAdapter(getContext(), list);
                        rcRecommend.setAdapter(recommendFriendAdapter);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    /**
     * 获取数据
     *
     * @param
     */
    public void getUserMsg() {
        dialog1.show();
        NetUtils.getInstance()
                .getApis().doGetUserMsg(userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FindUserMsgBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull FindUserMsgBean findUserMsgBean) {
                        dialog1.dismiss();
                        list = findUserMsgBean.getObject();

                        TestAdapter testAdapter = new TestAdapter(list);

                        soulPlanetView.setAdapter(testAdapter);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMsg(SaveFilterBean saveFilterBean) {
        String role = saveFilterBean.getRole();
        if (role.equals("全部")) {

            getUserMsg();

        } else {
            NetUtils.getInstance().getApis()
                    .doGetFilterUserMsg(userid, role)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<FindUserMsgBean>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NonNull FindUserMsgBean findUserMsgBean) {
                            list = findUserMsgBean.getObject();

                            TestAdapter testAdapter = new TestAdapter(list);

                            soulPlanetView.setAdapter(testAdapter);
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        timer.cancel();
    }

}
