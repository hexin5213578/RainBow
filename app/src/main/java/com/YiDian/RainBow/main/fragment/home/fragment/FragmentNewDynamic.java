package com.YiDian.RainBow.main.fragment.home.fragment;

import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseFragment;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.main.fragment.home.bean.NewDynamicBean;
import com.YiDian.RainBow.utils.NetUtils;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.container.MeituanFooter;
import com.liaoinstan.springview.container.MeituanHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.List;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

//最新动态

public class FragmentNewDynamic extends BaseFragment {
    @BindView(R.id.rc_newDynamic)
    RecyclerView rcNewDynamic;
    @BindView(R.id.sv)
    SpringView sv;

    @Override
    protected void getid(View view) {

    }

    @Override
    protected int getResId() {
        return R.layout.home_home_fragment_newdynamic;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void getData() {
        getNew(1,5);

        sv.setHeader(new AliHeader(getContext()));
        sv.setFooter(new AliFooter(getContext()));

        //下拉刷新下拉加载
        sv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sv.onFinishFreshAndLoad();

                    }
                }, 1000);
            }
            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sv.onFinishFreshAndLoad();

                    }
                }, 1000);
            }
        });

    }
    public void getNew(int page,int count){
        //获取最新动态
        NetUtils.getInstance().getApis().getNewDynamic(page,count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewDynamicBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(NewDynamicBean newDynamicBean) {
                        NewDynamicBean.ObjectBean object = newDynamicBean.getObject();
                        List<NewDynamicBean.ObjectBean.ListBean> list = object.getList();

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("hmy",e.getMessage()+"");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
