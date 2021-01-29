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
import com.YiDian.RainBow.friend.adapter.RecommendGroupAdapter;
import com.YiDian.RainBow.friend.bean.InitGroupBean;
import com.YiDian.RainBow.friend.bean.RecommendGroupBean;
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
    @BindView(R.id.rl3)
    RelativeLayout rl3;
    @BindView(R.id.rl_nodata)
    RelativeLayout rlNodata;
    private int userid;
    //判断三个列表是否为空
    int flag = 0;
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

        sv.setHeader(new AliHeader(getContext()));

        //首次进入获取数据
        getGroup();

        sv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                //刷新重新获取数据
                getGroup();

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
            userid = Integer.valueOf(Common.getUserId());
            getGroup();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
    public void getGroup(){
        NetUtils.getInstance().getApis().doGetGroup(userid).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<InitGroupBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(InitGroupBean initGroupBean) {
                        List<InitGroupBean.ObjectBean.GroupChuangJianBean> list1 = initGroupBean.getObject().getGroupChuangJian();
                        List<InitGroupBean.ObjectBean.GroupTuiJianBean> list2 = initGroupBean.getObject().getGroupTuiJian();
                        List<InitGroupBean.ObjectBean.GroupJiaRuBean> list3 = initGroupBean.getObject().getGroupJiaRu();
                        if(list1.size()>0||list2.size()>0||list3.size()>0){
                            rlNodata.setVisibility(View.GONE);
                            //显示缺省页
                            if (list1 != null && list1.size() > 0 ) {
                                rl1.setVisibility(View.VISIBLE);
                                rcMycreate.setVisibility(View.VISIBLE);

                                tv1.setText("我创建的(" + list1.size() + ")");

                                //创建布局管理器
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                                rcMycreate.setLayoutManager(linearLayoutManager);

                                GroupMyCreateAdapter myJoinAdapter = new GroupMyCreateAdapter(getContext(), list1);
                                rcMycreate.setAdapter(myJoinAdapter);
                            } else {
                                rl1.setVisibility(View.GONE);
                                rcMycreate.setVisibility(View.GONE);
                            }
                            //我加入的
                            if (list3.size() > 0 && list3 != null) {
                                rl2.setVisibility(View.VISIBLE);
                                rcMyjoin.setVisibility(View.VISIBLE);

                                tv2.setText("我加入的(" + list3.size() + ")");

                                //创建布局管理器
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                                rcMyjoin.setLayoutManager(linearLayoutManager);

                                GroupMyJoinAdapter myCreateAdapter = new GroupMyJoinAdapter(getContext(), list3);
                                rcMyjoin.setAdapter(myCreateAdapter);

                            } else {
                                rl2.setVisibility(View.GONE);
                                rcMyjoin.setVisibility(View.GONE);
                            }
                            //推荐
                            if (list2 != null && list2.size() > 0) {
                                //创建布局管理器
                                rl3.setVisibility(View.VISIBLE);
                                rcComment.setVisibility(View.VISIBLE);

                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                                rcComment.setLayoutManager(linearLayoutManager);

                                RecommendGroupAdapter adapter = new RecommendGroupAdapter(getContext(), list2);
                                rcComment.setAdapter(adapter);
                            } else {
                                rl3.setVisibility(View.GONE);
                                rcComment.setVisibility(View.GONE);
                            }
                        }else{
                            //显示缺省页
                            rlNodata.setVisibility(View.VISIBLE);


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
