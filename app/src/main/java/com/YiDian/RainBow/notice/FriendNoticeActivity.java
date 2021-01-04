package com.YiDian.RainBow.notice;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
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
import com.YiDian.RainBow.custom.customDialog.CustomDialogCleanNotice;
import com.YiDian.RainBow.notice.adapter.FriendNoticeAdapter;
import com.YiDian.RainBow.notice.bean.CleanNoticeBean;
import com.YiDian.RainBow.notice.bean.FriendNoticeBean;
import com.YiDian.RainBow.utils.NetUtils;
import com.leaf.library.StatusBarUtil;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

//粉丝通知
public class FriendNoticeActivity extends BaseAvtivity implements View.OnClickListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_back)
    LinearLayout ivBack;
    @BindView(R.id.ll_clear)
    LinearLayout llClear;
    @BindView(R.id.rl_nodata)
    RelativeLayout rlNodata;
    @BindView(R.id.rc_notice)
    SwipeMenuRecyclerView rcNotice;
    @BindView(R.id.sv)
    SpringView sv;
    List<FriendNoticeBean.ObjectBean.ListBean> allList;
    private int userid;
    int page = 1;
    int size = 15;
    private FriendNoticeAdapter friendNoticeAdapter;

    @Override
    protected int getResId() {
        return R.layout.activity_friendnotice;
    }

    @Override
    protected void getData() {
        //设置状态栏背景及字体颜色
        StatusBarUtil.setGradientColor(FriendNoticeActivity.this, toolbar);
        StatusBarUtil.setDarkMode(FriendNoticeActivity.this);
        ivBack.setOnClickListener(this);
        llClear.setOnClickListener(this);

        userid = Integer.valueOf(Common.getUserId());

        allList = new ArrayList<>();

        getNotice(page, size);

        sv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        allList.clear();
                        page = 1;
                        getNotice(page, size);
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
                        getNotice(page, size);
                        sv.onFinishFreshAndLoad();
                    }
                }, 1000);
            }
        });
        rcNotice.setAdapter(null);

        //设置侧滑菜单
        rcNotice.setSwipeMenuCreator(new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(FriendNoticeActivity.this)
                        .setBackground(R.color.red)
                        .setImage(R.mipmap.delete)
                        .setHeight(ViewGroup.LayoutParams.MATCH_PARENT)//设置高，这里使用match_parent，就是与item的高相同
                        .setWidth(150);//设置宽
                swipeRightMenu.addMenuItem(deleteItem);//设置右边的侧滑
            }
        });

        //设置侧滑菜单的点击事件
        rcNotice.setSwipeMenuItemClickListener(new SwipeMenuItemClickListener() {
            @Override
            public void onItemClick(SwipeMenuBridge menuBridge) {
                menuBridge.closeMenu();
                int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。0是左，右是1，暂时没有用到
                int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
                int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。

                //删除单个通知
                NetUtils.getInstance().getApis()
                        .doDeleteOneNotice(allList.get(adapterPosition).getId(), 2)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<CleanNoticeBean>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(CleanNoticeBean cleanNoticeBean) {
                                if (cleanNoticeBean.getMsg().equals("删除成功")) {
                                    allList.remove(adapterPosition);

                                    friendNoticeAdapter.notifyDataSetChanged();
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
        });
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    //获取数据
    public void getNotice(int page, int size) {
        showDialog();
        NetUtils.getInstance().getApis()
                .doGetFriendNoticeMsg(userid, page, size)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FriendNoticeBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(FriendNoticeBean friendNoticeBean) {
                        hideDialog();
                        List<FriendNoticeBean.ObjectBean.ListBean> list = friendNoticeBean.getObject().getList();
                        if (list.size() > 0 && list != null) {
                            sv.setHeader(new AliHeader(FriendNoticeActivity.this));
                            allList.addAll(list);

                            llClear.setEnabled(true);

                            sv.setVisibility(View.VISIBLE);
                            rlNodata.setVisibility(View.GONE);

                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(FriendNoticeActivity.this, RecyclerView.VERTICAL, false);

                            rcNotice.setLayoutManager(linearLayoutManager);
                            //创建适配器
                            friendNoticeAdapter = new FriendNoticeAdapter(FriendNoticeActivity.this, allList);


                            rcNotice.setAdapter(friendNoticeAdapter);

                        } else {
                            if (allList.size() > 0) {
                                llClear.setEnabled(true);
                                Toast.makeText(FriendNoticeActivity.this, "没有更多内容了", Toast.LENGTH_SHORT).show();
                            } else {
                                llClear.setEnabled(false);
                                sv.setVisibility(View.GONE);
                                rlNodata.setVisibility(View.VISIBLE);
                            }
                        }
                        if (list.size() > 14) {
                            sv.setFooter(new AliFooter(FriendNoticeActivity.this));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideDialog();
                        Toast.makeText(FriendNoticeActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
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
                finish();
                EventBus.getDefault().post("重新计算数量");
                break;
            case R.id.ll_clear:
                CustomDialogCleanNotice.Builder builder = new CustomDialogCleanNotice.Builder(FriendNoticeActivity.this);
                builder.setMessage("确定清空所有粉丝通知嘛?").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        NetUtils.getInstance().getApis()
                                .doDeleteNotice(userid, 2)
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
                                        getNotice(page, size);
                                        dialog.dismiss();
                                        llClear.setEnabled(false);
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
                break;
        }
    }
}
