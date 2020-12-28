package com.YiDian.RainBow.friend.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseFragment;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.custom.friend.DividerItemDecoration;
import com.YiDian.RainBow.custom.friend.LetterView;
import com.YiDian.RainBow.custom.progress.Loading_view;
import com.YiDian.RainBow.friend.adapter.ContactAdapter;
import com.YiDian.RainBow.friend.bean.FriendBean;
import com.YiDian.RainBow.main.fragment.home.adapter.NewDynamicAdapter;
import com.YiDian.RainBow.main.fragment.home.bean.NewDynamicBean;
import com.YiDian.RainBow.utils.NetUtils;
import com.YiDian.RainBow.utils.SPUtil;
import com.google.gson.Gson;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FragmentFriend extends Fragment {
    @BindView(R.id.contact_list)
    RecyclerView contactList;
    @BindView(R.id.letter_view)
    LetterView letterView;
    @BindView(R.id.rl_top)
    RelativeLayout rlTop;
    @BindView(R.id.rl_nodata)
    RelativeLayout rlNodata;
    private ContactAdapter adapter;
    private int userid;
    private LinearLayoutManager layoutManager;
    File f = new File(
            "/data/data/com.YiDian.RainBow/shared_prefs/friend.xml");
    private List<FriendBean.ObjectBean> spList;
    //加载的试图
    private View mContentView;
    //三个核心变量
    private boolean isUserHint;//用户是否可见
    private boolean isViewInit;//view视图是否加载过
    private boolean isDataLoad;//耗时操作是否加载过
    private Unbinder bind;
    private Loading_view loading_view;

    protected void getid(View view) {

    }

    protected int getResId() {
        return R.layout.fragment_friend;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mContentView == null) {
            mContentView = createView(inflater, container);
        }

        bind = ButterKnife.bind(this, mContentView);

        return mContentView;
    }
    public View createView(LayoutInflater inflater, ViewGroup container) {
        View view = null;
        if (getResId() != 0) {
            view = inflater.inflate(getResId(), container, false);
        } else {
            throw new IllegalStateException("this layout id is null");
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i("baseF","onViewCreated");

        if (!isViewInit) {
            getid(mContentView);
        }
        isViewInit = true;
        loadData();
    }

    //这个方法优先级很高
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {//用户可见就为true 不可见就是false
        super.setUserVisibleHint(isVisibleToUser);
        this.isUserHint = isVisibleToUser;
        loadData();
    }

    void loadData() {
        //进行优化懒加载的方法
        if (isUserHint && isViewInit) {
            getData();
            isDataLoad = true;
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
    protected void getData() {
        userid = Integer.valueOf(Common.getUserId());

        //进入先展示缓存
        Gson gson = new Gson();
        spList = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            String json = SPUtil.getInstance().getData(getContext(), SPUtil.JSON_Friend, "json" + i);
            FriendBean.ObjectBean listBean = gson.fromJson(json, FriendBean.ObjectBean.class);
            if(listBean!=null){
                spList.add(listBean);
            }
        }
        if(f.exists()){
            if(spList.size()>0 && spList !=null){

                rlTop.setVisibility(View.VISIBLE);
                rlNodata.setVisibility(View.GONE);
                letterView.setVisibility(View.VISIBLE);
                layoutManager = new LinearLayoutManager(getContext());
                adapter = new ContactAdapter(getContext(),spList);
                contactList.setLayoutManager(layoutManager);
                contactList.setAdapter(adapter);
            }
        }

        //调用查找我的全部好友
        getMyFriend();
        letterView.setCharacterListener(new LetterView.CharacterClickListener() {
            @Override
            public void clickCharacter(String character) {
                layoutManager.scrollToPositionWithOffset(adapter.getScrollPosition(character), 0);
            }
        });
    }
    // 展示loading圈
    public void showDialog() {
        if(loading_view==null){
            loading_view = new Loading_view(getContext(), R.style.CustomDialog);
        }
        loading_view.show();
    }
    //  隐藏loading圈
    public void hideDialog() {
        if (loading_view != null && loading_view.isShowing()) {
            loading_view.dismiss();
        }
    }
    //查找我的好友
    public void getMyFriend() {
        showDialog();
        NetUtils.getInstance().getApis()
                .doGetMyFriend(userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FriendBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(FriendBean friendBean) {
                        List<FriendBean.ObjectBean> list =
                                friendBean.getObject();
                        if (list.size() > 0 && list != null) {
                            hideDialog();
                            //存本地缓存
                            for (int i = 1; i <= list.size(); i++) {
                                FriendBean.ObjectBean objectBean = list.get(i - 1);
                                Gson gson = new Gson();
                                String json1 = gson.toJson(objectBean);
                                SPUtil.getInstance().saveData(getContext(), SPUtil.JSON_Friend, "json"+i, json1);
                            }
                            letterView.setVisibility(View.VISIBLE);
                            rlTop.setVisibility(View.VISIBLE);
                            rlNodata.setVisibility(View.GONE);
                            layoutManager = new LinearLayoutManager(getContext());
                            adapter = new ContactAdapter(getContext(), list);

                            contactList.setLayoutManager(layoutManager);
                            contactList.setAdapter(adapter);

                        } else {
                            rlNodata.setVisibility(View.VISIBLE);
                            rlTop.setVisibility(View.GONE);
                            letterView.setVisibility(View.GONE);

                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        hideDialog();
                        Toast.makeText(getContext(), "请求失败", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onComplete() {

                    }
                });
    }
}
