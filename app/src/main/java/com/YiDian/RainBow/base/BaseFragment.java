package com.YiDian.RainBow.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.YiDian.RainBow.R;
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
    Dialog mLoadingDialog;
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
    public void showDialog() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new Dialog(getActivity());
            mLoadingDialog.setCancelable(false);
            View v = View.inflate(getContext(), R.layout.dialog_loading, null);
            ImageView iv = v.findViewById(R.id.iv_loading);
            Glide.with(App.getContext()).asGif().load(R.mipmap.ic_launcher).into(iv);

            mLoadingDialog.addContentView(v,
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
        }
        mLoadingDialog.show();

    }
    public void hideDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
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
}
