package com.YiDian.RainBow.notice;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.custom.customDialog.CustomDialogCancleFollow;
import com.YiDian.RainBow.custom.customDialog.CustomDialogCleanNotice;
import com.YiDian.RainBow.main.fragment.home.bean.FollowBean;
import com.YiDian.RainBow.notice.adapter.SystemNoticeAdapter;
import com.YiDian.RainBow.notice.bean.CleanNoticeBean;
import com.YiDian.RainBow.notice.bean.NoticeMsgBean;
import com.YiDian.RainBow.utils.NetUtils;
import com.leaf.library.StatusBarUtil;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

//系统通知
public class SystemNoticeActivity extends BaseAvtivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_back)
    LinearLayout ivBack;
    @BindView(R.id.ll_clear)
    LinearLayout llclear;
    @BindView(R.id.rl_top)
    RelativeLayout rlTop;
    @BindView(R.id.rc_notice)
    RecyclerView rcNotice;
    @BindView(R.id.sv)
    SpringView sv;
    @BindView(R.id.rl_nodata)
    RelativeLayout rlNodata;
    private int userid;
    int page = 1;
    int size = 15;

    private List<NoticeMsgBean.ObjectBean> allList;

    @Override
    protected int getResId() {
        return R.layout.activity_systemnotice;
    }

    @Override
    protected void getData() {
        //状态栏背景 及字体颜色
        StatusBarUtil.setGradientColor(SystemNoticeActivity.this,toolbar);
        StatusBarUtil.setDarkMode(SystemNoticeActivity.this);

        allList = new ArrayList<>();
        //获取当前登录的userid
        userid = Integer.valueOf(Common.getUserId());
        //获取数据
        getSystemNotice(page,size);

        sv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page++;
                        getSystemNotice(page,size);
                        sv.onFinishFreshAndLoad();
                    }
                },1000);
            }

            @Override
            public void onLoadmore() {

            }
        });
        //清空通知
        llclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialogCleanNotice.Builder builder = new CustomDialogCleanNotice.Builder(SystemNoticeActivity.this);
                builder.setMessage("确定清空所有系统通知嘛?").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                            NetUtils.getInstance().getApis()
                                    .doDeleteNotice(userid,0)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Observer<CleanNoticeBean>() {
                                        @Override
                                        public void onSubscribe(Disposable d) {

                                        }

                                        @Override
                                        public void onNext(CleanNoticeBean cleanNoticeBean) {
                                            //清除成功重新加载数据
                                            allList.clear();
                                            getSystemNotice(page,size);
                                            dialog.dismiss();
                                            llclear.setEnabled(false);
                                        }

                                        @Override
                                        public void onError(Throwable e) {

                                        }
                                        @Override
                                        public void onComplete() {

                                        }
                                    });
                        }
                });
                builder.setNegativeButton("取消",
                        new android.content.DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.create().show();
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                EventBus.getDefault().post("重新计算数量");
            }
        });
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    //获取系统通知
    public void getSystemNotice(int page, int size) {
        NetUtils.getInstance().getApis()
                .doGetSystemNoticeMsg(userid, page, size)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NoticeMsgBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(NoticeMsgBean noticeMsgBean) {
                        List<NoticeMsgBean.ObjectBean> list =
                                noticeMsgBean.getObject();
                        if(list.size()>0 && list!=null){
                            llclear.setEnabled(true);

                            sv.setVisibility(View.VISIBLE);
                            rlNodata.setVisibility(View.GONE);

                            allList.addAll(list);

                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SystemNoticeActivity.this, RecyclerView.VERTICAL, true);
                            linearLayoutManager.scrollToPositionWithOffset(0,0);

                            rcNotice.setLayoutManager(linearLayoutManager);
                            SystemNoticeAdapter systemNoticeAdapter = new SystemNoticeAdapter(SystemNoticeActivity.this, allList);

                            rcNotice.setAdapter(systemNoticeAdapter);
                        }else{
                            if(allList.size()>0 && allList!=null){
                                llclear.setEnabled(true);

                                Toast.makeText(SystemNoticeActivity.this, "没有更多通知了", Toast.LENGTH_SHORT).show();
                            }else{
                                llclear.setEnabled(false);
                                sv.setVisibility(View.GONE);
                                rlNodata.setVisibility(View.VISIBLE);
                            }
                        }
                        if(list.size()>14){
                            sv.setHeader(new AliFooter(SystemNoticeActivity.this));
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
