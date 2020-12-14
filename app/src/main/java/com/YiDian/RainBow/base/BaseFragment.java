package com.YiDian.RainBow.base;

import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.custom.progress.Loading_view;
import com.bumptech.glide.Glide;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @ClassName: BaseFragment
 * @Description: (java类作用描述)
 * @Author: hmy
 */
public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements BaseView {
    private P presenter;
    private Unbinder bind;
    private Loading_view loading_view;
    public boolean mViewInflateFinished;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getContext(),getResId(),null);
        presenter  = initPresenter();
        bind = ButterKnife.bind(this, view);
        getid(view);
        mViewInflateFinished = true;
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getData();
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

    public P getPresenter() {
        return presenter;
    }


    protected abstract void getid(View view);
    protected abstract int getResId();
    protected abstract P initPresenter();
    protected abstract void getData();
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(presenter != null){
            presenter.detachView();
            presenter = null;
        }
        bind.unbind();
    }
    /**
     * fragment 提供的回调，回调当天fragment是否对用用户可见
     * 他是在当这个 fragment 是否对用户的可见发生变化的时候
     * @param isVisibleToUser false对用户不可见， true对用户可见
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        // 如果还没有加载过数据 && 用户切换到了这个fragment
        // 那就开始加载数据
        if (mViewInflateFinished && isVisibleToUser) {
            getData();
        }
    }
    private void doNetWork() {
        if (getUserVisibleHint()) {
            getData();
        }
    }
    //判断网络状态
    public boolean NetWork(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        if(activeNetworkInfo!=null){
            return true;
        }
        return false;
    }
}
