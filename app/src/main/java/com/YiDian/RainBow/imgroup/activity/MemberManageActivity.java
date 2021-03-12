package com.YiDian.RainBow.imgroup.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.custom.loading.CustomDialog;
import com.YiDian.RainBow.imgroup.adapter.GroupMemberTwoAdapter;
import com.YiDian.RainBow.imgroup.bean.GroupMemberTwoBean;
import com.YiDian.RainBow.utils.NetUtils;
import com.leaf.library.StatusBarUtil;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 成员管理页
 *
 * @author hmy
 */
public class MemberManageActivity extends BaseAvtivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_back)
    LinearLayout ivBack;
    @BindView(R.id.rc_member)
    RecyclerView rcMember;
    @BindView(R.id.sv)
    SpringView sv;
    private int id;
    private int jgid;
    private CustomDialog dialog;

    @Override
    protected int getResId() {
        return R.layout.activity_member_manager;
    }

    @Override
    protected void getData() {
        //设置导航栏颜色与字体颜色
        StatusBarUtil.setGradientColor(MemberManageActivity.this, toolbar);
        StatusBarUtil.setDarkMode(MemberManageActivity.this);


        dialog = new CustomDialog(MemberManageActivity.this, "正在加载...");


        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        jgid = intent.getIntExtra("jgid", 0);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getMember(id);

        sv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                getMember(id);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        sv.onFinishFreshAndLoad();
                    }
                },1000);
            }

            @Override
            public void onLoadmore() {

            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getStr(String str) {
        if (str.equals("删除成功重新请求数据")) {
            getMember(id);
        }
    }

    public void getMember(int id) {
        dialog.show();
        //获取成员信息
        NetUtils.getInstance().getApis().doGetGroupMembertwo(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GroupMemberTwoBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull GroupMemberTwoBean groupMemberTwoBean) {
                        dialog.dismiss();

                        //展示下拉刷新
                        sv.setHeader(new AliHeader(MemberManageActivity.this));

                        List<GroupMemberTwoBean.ObjectBean> list =
                                groupMemberTwoBean.getObject();

                        //创建布局管理器
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MemberManageActivity.this, RecyclerView.VERTICAL, false);
                        rcMember.setLayoutManager(linearLayoutManager);
                        //创建适配器
                        GroupMemberTwoAdapter groupMemberTwoAdapter = new GroupMemberTwoAdapter(MemberManageActivity.this, list, jgid);
                        rcMember.setAdapter(groupMemberTwoAdapter);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        dialog.dismiss();
                        Toast.makeText(MemberManageActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
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

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
