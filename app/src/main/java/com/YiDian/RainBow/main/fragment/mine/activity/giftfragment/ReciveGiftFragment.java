package com.YiDian.RainBow.main.fragment.mine.activity.giftfragment;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseFragment;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.main.fragment.mine.activity.MyGiftActivity;
import com.YiDian.RainBow.main.fragment.mine.adapter.GiftAdapter;
import com.YiDian.RainBow.main.fragment.mine.bean.GiftBean;
import com.YiDian.RainBow.utils.NetUtils;
import com.YiDian.RainBow.utils.SPUtil;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ReciveGiftFragment extends BaseFragment {
    @BindView(R.id.rc_gift)
    RecyclerView rcGift;
    @BindView(R.id.rl_nodata)
    RelativeLayout rlNodata;
    private int userid;

    @Override
    protected void getid(View view) {

    }

    @Override
    protected int getResId() {
        return R.layout.fragment_recivegift;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void getData() {
        userid = Integer.parseInt(Common.getUserId());

        NetUtils.getInstance().getApis()
                .dogetReciveGift(userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GiftBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GiftBean giftBean) {
                        if (giftBean.getMsg().equals("查询成功")) {
                            List<GiftBean.ObjectBean> list = giftBean.getObject();
                                if (list.size()>0 && list!=null){

                                    rlNodata.setVisibility(View.GONE);
                                    rcGift.setVisibility(View.VISIBLE);

                                    GiftBean.ObjectBean bean = giftBean.getObject().get(0);
                                    SPUtil.getInstance().saveData(getContext(), SPUtil.FILE_NAME, SPUtil.RECIVE_COUNT, String.valueOf(bean.getAllNums()));

                                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 4);
                                    rcGift.setLayoutManager(gridLayoutManager);

                                    //创建适配器
                                    GiftAdapter giftAdapter = new GiftAdapter(getContext(),list);

                                    rcGift.setAdapter(giftAdapter);
                                }else{
                                    rlNodata.setVisibility(View.VISIBLE);
                                    rcGift.setVisibility(View.GONE);
                                    SPUtil.getInstance().saveData(getContext(), SPUtil.FILE_NAME, SPUtil.RECIVE_COUNT, "0");
                                }
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
