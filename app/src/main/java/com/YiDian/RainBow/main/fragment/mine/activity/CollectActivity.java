package com.YiDian.RainBow.main.fragment.mine.activity;

import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.main.fragment.home.bean.NewDynamicBean;
import com.YiDian.RainBow.main.fragment.mine.adapter.CollectDynamicAdapter;
import com.YiDian.RainBow.utils.NetUtils;
import com.leaf.library.StatusBarUtil;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.tencent.tauth.Tencent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

//我的收藏
public class CollectActivity extends BaseAvtivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_back)
    LinearLayout ivBack;
    @BindView(R.id.rl_top)
    RelativeLayout rlTop;
    @BindView(R.id.rl_nodata)
    RelativeLayout rlNodata;
    @BindView(R.id.rc_mydraft_development)
    RecyclerView rcMydraftDevelopment;
    @BindView(R.id.sv)
    SpringView sv;
    int page = 1;
    int size = 5;
    private int userid;
    private Tencent mTencent;
    private List<NewDynamicBean.ObjectBean.ListBean> allList;
    File f = new File(
            "/data/data/com.YiDian.RainBow/shared_prefs/collectdynamic.xml");
    @Override
    protected int getResId() {
        return R.layout.activity_collect;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void getData() {

        userid = Integer.valueOf(Common.getUserId());

        //腾讯AppId(替换你自己App Id)、上下文
        mTencent = Tencent.createInstance("101906973", CollectActivity.this);



        //直接取消动画
        RecyclerView.ItemAnimator animator = rcMydraftDevelopment.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }
      /*  Gson gson = new Gson();
        List<NewDynamicBean.ObjectBean.ListBean> SpList = new ArrayList<>();

        for (int i = 1; i < 10; i++) {
            String json = SPUtil.getInstance().getData(CollectActivity.this, SPUtil.JSON_COLLECT, "json" + i);
            NewDynamicBean.ObjectBean.ListBean listBean = gson.fromJson(json, NewDynamicBean.ObjectBean.ListBean.class);
            if(listBean!=null){
                SpList.add(listBean);
            }
        }

        if(f.exists()){
            if(SpList.size()>0 && SpList!=null){
                sv.setVisibility(View.VISIBLE);
                rlNodata.setVisibility(View.GONE);

                sv.setHeader(new AliHeader(CollectActivity.this));
                //创建最新动态适配器
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CollectActivity.this, RecyclerView.VERTICAL, false);
                rcMydraftDevelopment.setLayoutManager(linearLayoutManager);

                CollectDynamicAdapter myDynamicAdapter = new CollectDynamicAdapter(CollectActivity.this, SpList, mTencent);
                rcMydraftDevelopment.setAdapter(myDynamicAdapter);
            }
        }*/
        getDynamic(page,size);

        allList = new ArrayList<>();
        //设置背景透明
        StatusBarUtil.setGradientColor(CollectActivity.this, toolbar);
        //设置状态栏字体黑色
        StatusBarUtil.setDarkMode(this);

        sv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                allList.clear();
                page = 1;
                getDynamic(page,size);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        //等待2.5秒后结束刷新
                        sv.onFinishFreshAndLoad();
                    }
                }, 1000);
            }

            @Override
            public void onLoadmore() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page++;
                        getDynamic(page,size);
                        sv.onFinishFreshAndLoad();
                    }
                }, 1000);
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void getDynamic(int page, int size) {
        NetUtils.getInstance().getApis()
                .doGetCollectDynamicById(page, size, userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewDynamicBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(NewDynamicBean newDynamicBean) {
                        List<NewDynamicBean.ObjectBean.ListBean> list = newDynamicBean.getObject().getList();
                        if (list != null && list.size() > 0) {
                            allList.addAll(list);
                            sv.setHeader(new AliHeader(CollectActivity.this));

                            rlNodata.setVisibility(View.GONE);
                            sv.setVisibility(View.VISIBLE);
                            rcMydraftDevelopment.setVisibility(View.VISIBLE);

                            /*//存五条数据
                            for (int i = 1; i <= list.size(); i++) {
                                NewDynamicBean.ObjectBean.ListBean listBean = list.get(i - 1);
                                Gson gson = new Gson();
                                String json1 = gson.toJson(listBean);
                                SPUtil.getInstance().saveData(CollectActivity.this, SPUtil.JSON_COLLECT, "json" + i, json1);
                            }*/


                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CollectActivity.this, RecyclerView.VERTICAL, false);
                            rcMydraftDevelopment.setLayoutManager(linearLayoutManager);

                            CollectDynamicAdapter collectDynamicAdapter = new CollectDynamicAdapter(CollectActivity.this, allList, mTencent);
                            rcMydraftDevelopment.setAdapter(collectDynamicAdapter);
                        }else{
                            if (allList!=null &&allList.size()>0){
                                Toast.makeText(CollectActivity.this, "沒有更多內容了", Toast.LENGTH_SHORT).show();
                            }else{
                                sv.setVisibility(View.GONE);
                                rlNodata.setVisibility(View.VISIBLE);
                            }
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
    @Override
    protected void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getStr(String str){
        if (str.equals("刷新数据")){
            allList.clear();
            getDynamic(1,5);
        }
    }
    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

}
