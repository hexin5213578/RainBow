package com.YiDian.RainBow.main.fragment.find.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseFragment;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.custom.loading.CustomDialog;
import com.YiDian.RainBow.main.fragment.find.adapter.TestAdapter;
import com.YiDian.RainBow.main.fragment.find.bean.SaveFilterBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import me.haowen.soulplanet.view.SoulPlanetsView;

//匹配界面
public class Fragmentmatch extends BaseFragment {

    @BindView(R.id.soulPlanetView)
    SoulPlanetsView soulPlanetView;
    private CustomDialog dialog1;
    private int userid;

    @Override
    protected void getid(View view) {

    }

    @Override
    protected int getResId() {
        return R.layout.home_find_fragment_match;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void getData() {
        //从网络获取数据表后将其中的列表存放在这个链表中
        dialog1 = new CustomDialog(getContext(), "正在加载...");
        userid = Integer.valueOf(Common.getUserId());

        TestAdapter testAdapter = new TestAdapter();
        soulPlanetView.setAdapter(testAdapter);

        //条目点击
        soulPlanetView.setOnTagClickListener(new SoulPlanetsView.OnTagClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, int position) {
                Toast.makeText(getContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMsg(SaveFilterBean saveFilterBean) {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

}
