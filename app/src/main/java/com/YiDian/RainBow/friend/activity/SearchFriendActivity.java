package com.YiDian.RainBow.friend.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
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
import com.YiDian.RainBow.dynamic.bean.SelectFriendOrGroupBean;
import com.YiDian.RainBow.friend.adapter.SearchFriendAdapter;
import com.YiDian.RainBow.friend.adapter.SearchGroupAdapter;
import com.YiDian.RainBow.utils.KeyBoardUtils;
import com.YiDian.RainBow.utils.NetUtils;
import com.leaf.library.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SearchFriendActivity extends BaseAvtivity implements View.OnClickListener {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_back)
    LinearLayout ivBack;
    @BindView(R.id.ll_seemore1)
    LinearLayout llSeemore1;
    @BindView(R.id.rc_searchfriend)
    RecyclerView rcSearchfriend;
    @BindView(R.id.ll_seemore2)
    LinearLayout llSeemore2;
    @BindView(R.id.rc_searchgroup)
    RecyclerView rcSearchgroup;
    @BindView(R.id.et_text)
    EditText etText;
    @BindView(R.id.ll_shuru)
    LinearLayout llShuru;
    @BindView(R.id.ll_search)
    LinearLayout llSearch;
    @BindView(R.id.rl_nodata)
    RelativeLayout rlNodata;
    @BindView(R.id.l1)
    RelativeLayout l1;
    @BindView(R.id.l2)
    RelativeLayout l2;
    @BindView(R.id.rl_middle)
    RelativeLayout rlMiddle;
    private int userid;
    private List<SelectFriendOrGroupBean.ObjectBean.GroupListBean> groupList;
    private List<SelectFriendOrGroupBean.ObjectBean.UserListBean> userList;
    private String s;
    private Intent intent;

    @Override
    protected int getResId() {
        return R.layout.activity_search_friend;
    }

    @Override
    protected void getData() {
        StatusBarUtil.setGradientColor(SearchFriendActivity.this, toolbar);
        StatusBarUtil.setDarkMode(SearchFriendActivity.this);

        userid = Integer.valueOf(Common.getUserId());

        ivBack.setOnClickListener(this);
        llSearch.setOnClickListener(this);
        llShuru.setOnClickListener(this);
        llSeemore1.setOnClickListener(this);
        llSeemore2.setOnClickListener(this);


        KeyBoardUtils.openKeyBoard(etText);
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_search:
                s = etText.getText().toString();
                if (!TextUtils.isEmpty(s)) {
                    getSearchDate(s);
                } else {
                    Toast.makeText(this, "请先输入搜索内容", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.ll_shuru:
                KeyBoardUtils.openKeyBoard(etText);
                break;
            case R.id.ll_seemore1:
                intent = new Intent(SearchFriendActivity.this,MoreUserActivity.class);
                intent.putExtra("list", (Serializable) userList);
                startActivity(intent);
                break;
            case R.id.ll_seemore2:
                intent = new Intent(SearchFriendActivity.this,MoreGroupActivity.class);
                intent.putExtra("list", (Serializable) groupList);
                startActivity(intent);
                break;
        }
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
    public void getString(String str){
        if (str.equals("搜索好友刷新界面")){
            getSearchDate(s);
        }
    }
    //获取查询的数据
    public void getSearchDate(String str) {
        NetUtils.getInstance().getApis()
                .doGetAllUserAndGroup(str, userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SelectFriendOrGroupBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SelectFriendOrGroupBean selectFriendOrGroupBean) {
                        groupList = selectFriendOrGroupBean.getObject().getGroupList();
                        userList = selectFriendOrGroupBean.getObject().getUserList();

                        KeyBoardUtils.closeKeyboard(SearchFriendActivity.this);

                        if (userList != null && userList.size() > 0) {
                            rlMiddle.setVisibility(View.VISIBLE);

                            l1.setVisibility(View.VISIBLE);
                            rcSearchfriend.setVisibility(View.VISIBLE);
                            rlNodata.setVisibility(View.GONE);

                            List<SelectFriendOrGroupBean.ObjectBean.UserListBean> list = new ArrayList<>();

                            if (userList.size() > 2) {
                                llSeemore1.setVisibility(View.VISIBLE);

                                //长度大于2只传前两个
                                for (int i =0;i<2;i++){
                                    list.add(userList.get(i));
                                }
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SearchFriendActivity.this, RecyclerView.VERTICAL, false);
                                rcSearchfriend.setLayoutManager(linearLayoutManager);

                                SearchFriendAdapter searchFriendAdapter = new SearchFriendAdapter(SearchFriendActivity.this, list);
                                rcSearchfriend.setAdapter(searchFriendAdapter);
                            } else {
                                llSeemore1.setVisibility(View.GONE);

                                if (userList.size()<=2 && userList.size()>0){
                                    //长度在0与2中间传全部
                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SearchFriendActivity.this, RecyclerView.VERTICAL, false);
                                    rcSearchfriend.setLayoutManager(linearLayoutManager);

                                    SearchFriendAdapter searchFriendAdapter = new SearchFriendAdapter(SearchFriendActivity.this, userList);
                                    rcSearchfriend.setAdapter(searchFriendAdapter);
                                }else{
                                    llSeemore1.setVisibility(View.GONE);
                                }
                            }

                        } else {
                            l1.setVisibility(View.GONE);
                            rcSearchfriend.setVisibility(View.GONE);
                        }
                        if (groupList != null && groupList.size() > 0) {
                            rlMiddle.setVisibility(View.VISIBLE);

                            l2.setVisibility(View.VISIBLE);
                            rlNodata.setVisibility(View.GONE);
                            rcSearchgroup.setVisibility(View.VISIBLE);

                            List<SelectFriendOrGroupBean.ObjectBean.GroupListBean> list = new ArrayList<>();

                            if (groupList.size() > 2) {
                                llSeemore2.setVisibility(View.VISIBLE);

                                for (int i =0;i<2;i++){
                                    list.add(groupList.get(i));
                                }
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SearchFriendActivity.this, RecyclerView.VERTICAL, false);
                                rcSearchgroup.setLayoutManager(linearLayoutManager);

                                SearchGroupAdapter searchGroupAdapter = new SearchGroupAdapter(SearchFriendActivity.this, list);
                                rcSearchgroup.setAdapter(searchGroupAdapter);
                            } else {
                                llSeemore2.setVisibility(View.GONE);

                                if (groupList.size()>0 && groupList.size()<2){
                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SearchFriendActivity.this, RecyclerView.VERTICAL, false);
                                    rcSearchgroup.setLayoutManager(linearLayoutManager);

                                    SearchGroupAdapter searchGroupAdapter = new SearchGroupAdapter(SearchFriendActivity.this, groupList);
                                    rcSearchgroup.setAdapter(searchGroupAdapter);
                                }else{
                                    llSeemore2.setVisibility(View.GONE);
                                }
                            }

                        } else {
                            l2.setVisibility(View.GONE);
                            rcSearchgroup.setVisibility(View.GONE);
                        }
                        if (userList!=null && groupList!=null && userList.size() == 0  && groupList.size() == 0) {
                            rlMiddle.setVisibility(View.GONE);
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