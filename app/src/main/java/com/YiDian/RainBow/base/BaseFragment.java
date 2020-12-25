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
    //加载的试图
    private View mContentView;
    //三个核心变量
    private boolean isUserHint;//用户是否可见
    private boolean isViewInit;//view视图是否加载过
    private boolean isDataLoad;//耗时操作是否加载过

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mContentView == null) {
            mContentView = createView(inflater, container);
        }
        presenter  = initPresenter();

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

    //这个方法优先级很高
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {//用户可见就为true 不可见就是false
        super.setUserVisibleHint(isVisibleToUser);
        this.isUserHint = isVisibleToUser;
        loadData();
        Log.i("baseF","setUserVisibleHint");
    }

    void loadData() {
        //进行优化懒加载的方法
        if (isUserHint && isViewInit && !isDataLoad) {
            getData();
            isDataLoad = true;
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
