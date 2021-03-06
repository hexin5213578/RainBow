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
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.custom.customDialog.CustomDialogCleanNotice;
import com.YiDian.RainBow.custom.loading.CustomDialog;
import com.YiDian.RainBow.notice.adapter.ClickNoticeAdapter;
import com.YiDian.RainBow.notice.bean.CleanNoticeBean;
import com.YiDian.RainBow.notice.bean.ClickNoticeBean;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ClickNoticeActivity extends BaseAvtivity implements View.OnClickListener {
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
    private int userid;
    int page = 1;
    int size = 15;
    private List<ClickNoticeBean.ObjectBean> allList;
    private ClickNoticeAdapter clickNoticeAdapter;
    private CustomDialog dialog;

    @Override
    protected int getResId() {
        return R.layout.activity_clicknotice;
    }

    @Override
    protected void getData() {
        //??????????????? ???????????????
        StatusBarUtil.setGradientColor(ClickNoticeActivity.this,toolbar);
        StatusBarUtil.setDarkMode(ClickNoticeActivity.this);

        //??????????????????
        RecyclerView.ItemAnimator animator = rcNotice.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }

        ivBack.setOnClickListener(this);
        llClear.setOnClickListener(this);
        dialog = new CustomDialog(this, "????????????...");


        allList = new ArrayList<>();
        //?????????????????????userid
        userid = Integer.valueOf(Common.getUserId());
        //????????????
        getNotice(page,size);


        sv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                allList.clear();
                page=  1;
                getNotice(page,size);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        sv.onFinishFreshAndLoad();
                    }
                },1000);
            }

            @Override
            public void onLoadmore() {
                page++;
                getNotice(page,size);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        sv.onFinishFreshAndLoad();
                    }
                },1000);
            }
        });
        rcNotice.setAdapter(null);
        //??????????????????
        rcNotice.setSwipeMenuCreator(new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(ClickNoticeActivity.this)
                        .setBackground(R.color.red)
                        .setImage(R.mipmap.delete)
                        .setHeight(ViewGroup.LayoutParams.MATCH_PARENT)//????????????????????????match_parent????????????item????????????
                        .setWidth(150);//?????????
                swipeRightMenu.addMenuItem(deleteItem);//?????????????????????
            }
        });

        //?????????????????????????????????
        rcNotice.setSwipeMenuItemClickListener(new SwipeMenuItemClickListener() {
            @Override
            public void onItemClick(SwipeMenuBridge menuBridge) {
                menuBridge.closeMenu();
                int direction = menuBridge.getDirection(); // ???????????????????????????0???????????????1?????????????????????
                int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView???Item???position???
                int menuPosition = menuBridge.getPosition(); // ?????????RecyclerView???Item??????Position???

                int msgType = allList.get(adapterPosition).getMsgType();
                if (msgType==3){
                    //??????????????????
                    NetUtils.getInstance().getApis()
                            .doDeleteOneNotice(allList.get(adapterPosition).getId(), 3)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<CleanNoticeBean>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(CleanNoticeBean cleanNoticeBean) {
                                    if (cleanNoticeBean.getMsg().equals("????????????")) {
                                        allList.remove(adapterPosition);

                                        clickNoticeAdapter.notifyDataSetChanged();
                                    }
                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                }else if(msgType==4){
                    //??????????????????
                    NetUtils.getInstance().getApis()
                            .doDeleteOneNotice(allList.get(adapterPosition).getId(), 4)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<CleanNoticeBean>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(CleanNoticeBean cleanNoticeBean) {
                                    if (cleanNoticeBean.getMsg().equals("????????????")) {
                                        allList.remove(adapterPosition);

                                        clickNoticeAdapter.notifyDataSetChanged();
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

        });
    }
    //????????????????????????
    public void getNotice(int page,int size){
        dialog.show();
        NetUtils.getInstance().getApis()
                .doGetClickNotice(userid,page,size)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ClickNoticeBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ClickNoticeBean clickNoticeBean) {
                        dialog.dismiss();
                        List<ClickNoticeBean.ObjectBean> list = clickNoticeBean.getObject();
                        if (list.size()>0 && list!=null){
                            llClear.setEnabled(true);
                            allList.addAll(list);

                            sv.setVisibility(View.VISIBLE);
                            rlNodata.setVisibility(View.GONE);

                            sv.setHeader(new AliHeader(ClickNoticeActivity.this));

                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ClickNoticeActivity.this,RecyclerView.VERTICAL,false);
                            rcNotice.setLayoutManager(linearLayoutManager);

                            clickNoticeAdapter = new ClickNoticeAdapter(ClickNoticeActivity.this, allList);
                            rcNotice.setAdapter(clickNoticeAdapter);
                        }else{
                            if (allList.size()>0 && allList!=null){
                                llClear.setEnabled(true);
                                Toast.makeText(ClickNoticeActivity.this, "?????????????????????", Toast.LENGTH_SHORT).show();
                            }else{
                                llClear.setEnabled(false);
                                sv.setVisibility(View.GONE);
                                rlNodata.setVisibility(View.VISIBLE);
                            }
                        }
                        if(list.size()>14){
                            sv.setFooter(new AliFooter(ClickNoticeActivity.this));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        dialog.dismiss();
                        Toast.makeText(ClickNoticeActivity.this, "??????????????????", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                EventBus.getDefault().post("??????????????????");
                break;
            case R.id.ll_clear:
                CustomDialogCleanNotice.Builder builder = new CustomDialogCleanNotice.Builder(ClickNoticeActivity.this);
                builder.setMessage("??????????????????????????????????").setPositiveButton("??????", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        NetUtils.getInstance().getApis()
                                .doDeleteNotice(userid,3)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<CleanNoticeBean>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onNext(CleanNoticeBean cleanNoticeBean) {
                                        //??????????????????????????????
                                        allList.clear();
                                        getNotice(page,size);
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
                builder.setNegativeButton("??????",
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
