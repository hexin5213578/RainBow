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
import com.YiDian.RainBow.custom.loading.CustomDialog;
import com.YiDian.RainBow.main.fragment.mine.adapter.draftviewholder.MyDraftsAdapter;
import com.YiDian.RainBow.main.fragment.mine.bean.SelectAllDraftsBean;
import com.YiDian.RainBow.utils.NetUtils;
import com.YiDian.RainBow.utils.SPUtil;
import com.google.gson.Gson;
import com.leaf.library.StatusBarUtil;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.shuyu.gsyvideoplayer.GSYVideoManager;

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

//我的草稿箱
public class MydraftActivity extends BaseAvtivity implements View.OnClickListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_back)
    LinearLayout ivBack;
    @BindView(R.id.rl_top)
    RelativeLayout rlTop;
    @BindView(R.id.rc_mydraft_development)
    RecyclerView rcMydraftDevelopment;
    @BindView(R.id.sv)
    SpringView sv;
    @BindView(R.id.rl_nodata)
    RelativeLayout ivNodata;
    int userid ;
    List<SelectAllDraftsBean.ObjectBean.ListBean> AllList = new ArrayList<>();
    int page = 1;
    int pagesize = 5;
    File f = new File(
            "/data/data/com.YiDian.RainBow/shared_prefs/drafts.xml");
    private CustomDialog dialog;

    @Override
    protected int getResId() {
        return R.layout.activity_my_draft;
    }

    @Override
    protected void getData() {
        //设置状态栏背景及字体颜色
        StatusBarUtil.setGradientColor(this,toolbar);
        StatusBarUtil.setDarkMode(this);

        dialog = new CustomDialog(this, "正在加载...");

        Gson gson = new Gson();
        List<SelectAllDraftsBean.ObjectBean.ListBean> SpList = new ArrayList<>();

        //直接取消动画
        RecyclerView.ItemAnimator animator = rcMydraftDevelopment.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }
        rcMydraftDevelopment.setItemAnimator(null);
        rcMydraftDevelopment.setHasFixedSize(true);
        rcMydraftDevelopment.setNestedScrollingEnabled(false);
        rcMydraftDevelopment.setFocusableInTouchMode(false);
        rcMydraftDevelopment.requestFocus();
        for (int i = 1; i < 6; i++) {
            String json = SPUtil.getInstance().getData(MydraftActivity.this, SPUtil.JSOn_drafts, "json" + i);
            SelectAllDraftsBean.ObjectBean.ListBean listBean = gson.fromJson(json, SelectAllDraftsBean.ObjectBean.ListBean.class);

            if(listBean!=null){
                SpList.add(listBean);
            }
        }
        if(f.exists()){
            if(SpList.size()>0 && SpList!=null){
                sv.setVisibility(View.VISIBLE);
                ivNodata.setVisibility(View.GONE);

                sv.setHeader(new AliHeader(MydraftActivity.this));
                //创建最新动态适配器
                //创建适配器
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MydraftActivity.this, RecyclerView.VERTICAL, false);
                rcMydraftDevelopment.setLayoutManager(linearLayoutManager);

                MyDraftsAdapter myDraftsAdapter = new MyDraftsAdapter(MydraftActivity.this,SpList);
                rcMydraftDevelopment.setAdapter(myDraftsAdapter);
            }else{
                sv.setVisibility(View.GONE);
                ivNodata.setVisibility(View.VISIBLE);
            }
        }

        //获取当前用户ID
        userid = Integer.parseInt(Common.getUserId());
        //绑定单击事件
        ivBack.setOnClickListener(this);

        //首次获取数据
        getData(page,pagesize);

        sv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                AllList.clear();
                page = 1;
                getData(page,pagesize);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sv.onFinishFreshAndLoad();
                        GSYVideoManager.releaseAllVideos();

                    }
                }, 1000);
            }

            @Override
            public void onLoadmore() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page++;
                        getData(page,pagesize);
                        sv.onFinishFreshAndLoad();
                        GSYVideoManager.releaseAllVideos();

                    }
                }, 1000);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        GSYVideoManager.releaseAllVideos();
        if(EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getStr(String str){
        if(str.equals("刷新界面")){
            AllList.clear();
            getData(1,pagesize);
        }
    }
    @Override
    protected BasePresenter initPresenter() {
        return null;
    }
    public void getData(int page,int size){
        dialog.show();
        NetUtils.getInstance().getApis()
                .doGetAllDraftsBy(userid,page,size)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SelectAllDraftsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SelectAllDraftsBean selectAllDraftsBean) {
                        dialog.dismiss();
                        List<SelectAllDraftsBean.ObjectBean.ListBean> list = selectAllDraftsBean.getObject().getList();
                        if(list.size()>0 && list!=null){
                            AllList.addAll(list);

                            sv.setHeader(new AliHeader(MydraftActivity.this));

                            //存五条数据
                            for (int i = 1; i <= list.size(); i++) {
                                SelectAllDraftsBean.ObjectBean.ListBean listBean = list.get(i-1);
                                Gson gson = new Gson();
                                String json1 = gson.toJson(listBean);
                                SPUtil.getInstance().saveData(MydraftActivity.this, SPUtil.JSOn_drafts, "json"+i, json1);
                            }

                            //隐藏缺省图 展示列表
                            ivNodata.setVisibility(View.GONE);
                            sv.setVisibility(View.VISIBLE);

                            //创建适配器
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MydraftActivity.this, RecyclerView.VERTICAL, false);
                            rcMydraftDevelopment.setLayoutManager(linearLayoutManager);

                            MyDraftsAdapter myDraftsAdapter = new MyDraftsAdapter(MydraftActivity.this,AllList);

                            myDraftsAdapter.setHasStableIds(true);

                            rcMydraftDevelopment.setAdapter(myDraftsAdapter);

                            //集合长度大于14 可以加载更多内容
                            if(list.size()>4){
                                sv.setFooter(new AliFooter(MydraftActivity.this));
                            }
                        }else{
                            if(AllList.size()>0 && AllList!=null){
                                //创建适配器
                                Toast.makeText(MydraftActivity.this, "没有更多内容了", Toast.LENGTH_SHORT).show();
                            }else{
                                sv.setVisibility(View.GONE);
                                ivNodata.setVisibility(View.VISIBLE);
                            }
                        }

                    }
                    @Override
                    public void onError(Throwable e) {
                        dialog.dismiss();
                        Toast.makeText(MydraftActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                //退出当前界面
                finish();
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        GSYVideoManager.onResume();
    }
}
