package com.YiDian.RainBow.imgroup.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.imgroup.adapter.GroupMemberAdapter;
import com.YiDian.RainBow.imgroup.adapter.GroupMemberDetailsAdapter;
import com.YiDian.RainBow.imgroup.bean.GroupMemberBean;
import com.YiDian.RainBow.imgroup.bean.GroupMemberTwoBean;
import com.YiDian.RainBow.utils.NetUtils;
import com.leaf.library.StatusBarUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class GroupMemberDetailsActivity extends BaseAvtivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_back)
    LinearLayout ivBack;
    @BindView(R.id.rc_member)
    RecyclerView rcMember;

    @Override
    protected int getResId() {
        return R.layout.acitivity_group_member;
    }

    @Override
    protected void getData() {
        StatusBarUtil.setGradientColor(GroupMemberDetailsActivity.this,toolbar);
        StatusBarUtil.setDarkMode(GroupMemberDetailsActivity.this);

        Intent intent = getIntent();
        int groupid = intent.getIntExtra("groupid", 0);

        getGroupMember(groupid);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
    //获取群成员
    public void getGroupMember(int groupid){
        NetUtils.getInstance().getApis()
                .doGetGroupMembertwo(groupid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GroupMemberTwoBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull GroupMemberTwoBean bean) {
                        if (bean.getType().equals("OK")){
                            List<GroupMemberTwoBean.ObjectBean> list = bean.getObject();
                            if (list!=null && list.size()>0){

                                GridLayoutManager gridLayoutManager = new GridLayoutManager(GroupMemberDetailsActivity.this, 7);
                                rcMember.setLayoutManager(gridLayoutManager);

                                GroupMemberDetailsAdapter adapter = new GroupMemberDetailsAdapter(GroupMemberDetailsActivity.this);
                                //设置数据源
                                adapter.setData(list);

                                rcMember.setAdapter(adapter);
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

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
}
