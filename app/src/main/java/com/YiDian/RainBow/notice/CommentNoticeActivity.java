package com.YiDian.RainBow.notice;

import android.content.DialogInterface;
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
import com.YiDian.RainBow.main.fragment.home.adapter.NewDynamicAdapter;
import com.YiDian.RainBow.main.fragment.home.bean.NewDynamicBean;
import com.YiDian.RainBow.notice.adapter.CommentNoticeAdapter;
import com.YiDian.RainBow.notice.adapter.FriendNoticeAdapter;
import com.YiDian.RainBow.notice.bean.CleanNoticeBean;
import com.YiDian.RainBow.notice.bean.CommentNoticeBean;
import com.YiDian.RainBow.utils.NetUtils;
import com.YiDian.RainBow.utils.SPUtil;
import com.google.gson.Gson;
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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

//????????????
public class CommentNoticeActivity extends BaseAvtivity implements View.OnClickListener {
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
    int page = 1;
    int size = 15;
    private int userid;
    private List<CommentNoticeBean.ObjectBean> allList;
    private CommentNoticeAdapter commentNoticeAdapter;
    private LinearLayoutManager linearLayoutManager;
    private CustomDialog dialog;

    @Override
    protected int getResId() {
        return R.layout.activity_commentnotice;
    }

    @Override
    protected void getData() {
        //????????????????????????????????????
        StatusBarUtil.setGradientColor(CommentNoticeActivity.this, toolbar);
        StatusBarUtil.setDarkMode(CommentNoticeActivity.this);
        ivBack.setOnClickListener(this);
        llClear.setOnClickListener(this);
        userid = Integer.valueOf(Common.getUserId());
        allList = new ArrayList<>();

        //??????????????????
        RecyclerView.ItemAnimator animator = rcNotice.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }

        dialog = new CustomDialog(this, "????????????...");
        getNotice(page, size);
        sv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                allList.clear();
                page = 1;
                getNotice(page, size);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        sv.onFinishFreshAndLoad();
                    }
                }, 1000);
            }

            @Override
            public void onLoadmore() {
                page++;
                getNotice(page, size);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        sv.onFinishFreshAndLoad();
                    }
                }, 1000);
            }
        });
        rcNotice.setAdapter(null);
        //??????????????????
        rcNotice.setSwipeMenuCreator(new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(CommentNoticeActivity.this)
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
                if (msgType==5){
                    //??????????????????
                    NetUtils.getInstance().getApis()
                            .doDeleteOneNotice(allList.get(adapterPosition).getId(), 5)
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

                                        commentNoticeAdapter.notifyDataSetChanged();
                                    }
                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                }else if(msgType==6){
                    //??????????????????
                    NetUtils.getInstance().getApis()
                            .doDeleteOneNotice(allList.get(adapterPosition).getId(), 6)
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

                                        commentNoticeAdapter.notifyDataSetChanged();
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
    public void getNotice(int page, int size){
        dialog.show();
        NetUtils.getInstance().getApis()
                .doGetContentNotice(userid,page,size)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommentNoticeBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CommentNoticeBean contentNoticeBean) {
                        dialog.dismiss();
                        List<CommentNoticeBean.ObjectBean> list = contentNoticeBean.getObject();
                        if(list.size()>0 && list!=null){
                            sv.setHeader(new AliHeader(CommentNoticeActivity.this));

                            llClear.setEnabled(true);

                            allList.addAll(list);

                            sv.setVisibility(View.VISIBLE);
                            rlNodata.setVisibility(View.GONE);

                            linearLayoutManager = new LinearLayoutManager(CommentNoticeActivity.this, RecyclerView.VERTICAL, false);
                            rcNotice.setLayoutManager(linearLayoutManager);
                            //???????????????
                            commentNoticeAdapter = new CommentNoticeAdapter(CommentNoticeActivity.this, allList);
                            rcNotice.setAdapter(commentNoticeAdapter);

                        }else{
                            if(allList.size()>0 && allList!=null){
                                llClear.setEnabled(true);

                                Toast.makeText(CommentNoticeActivity.this, "?????????????????????", Toast.LENGTH_SHORT).show();
                            }else{
                                llClear.setEnabled(false);

                                sv.setVisibility(View.GONE);
                                rlNodata.setVisibility(View.VISIBLE);
                            }
                        }
                        if (list.size()>14){
                            sv.setFooter(new AliFooter(CommentNoticeActivity.this));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        dialog.dismiss();

                        Toast.makeText(CommentNoticeActivity.this, "??????????????????", Toast.LENGTH_SHORT).show();
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
                CustomDialogCleanNotice.Builder builder = new CustomDialogCleanNotice.Builder(CommentNoticeActivity.this);
                builder.setMessage("??????????????????????????????????").setPositiveButton("??????", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        NetUtils.getInstance().getApis()
                                .doDeleteNotice(userid,5)
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
