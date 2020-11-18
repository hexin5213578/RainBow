package com.YiDian.RainBow.main.fragment.find.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseFragment;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.main.fragment.find.activity.UserDetailsActivity;
import com.YiDian.RainBow.main.fragment.find.adapter.CardsDataAdapter;
import com.YiDian.RainBow.main.fragment.find.bean.SaveFilterBean;
import com.wenchao.cardstack.CardStack;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;

import butterknife.BindView;

//匹配界面
public class Fragmentmatch extends BaseFragment {
    @BindView(R.id.container)
    CardStack container;
    int index = 0;
    private CardsDataAdapter cardsDataAdapter;

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

        container.setStackMargin(20);

        cardsDataAdapter = new CardsDataAdapter(getContext(), R.layout.card_layout);
        container.setAdapter(cardsDataAdapter);

        for (int i =0;i<20;i++){
            cardsDataAdapter.add("test"+i);
        }

        container.setListener(new CardStack.CardEventListener() {
            @Override
            public boolean swipeEnd(int i, float v) {
                //if "return true" the dismiss animation will be triggered  如果“ return true”，则将触发关闭动画
                //if false, the card will move back to stack  如果“ return false”，则卡将移回堆栈
                //distance is finger swipe distance in dp  距离是手指滑动距离，以dp为单位
                //the direction indicate swipe direction  方向指示滑动方向
                //there are four directions  有四个方向
                //  0  |  1
                // ----------
                //  2  |  3
                return (v>360)? true : false;
            }

            @Override
            public boolean swipeStart(int i, float v) {

                return false;
            }

            @Override
            public boolean swipeContinue(int i, float v, float v1) {

                return false;
            }
            //关闭动画结束时调用此回调。
            @Override
            public void discarded(int i, int i1) {
                Log.e("xxx",i+"   "+i1);

                //滑动到最后几条  重新添加数据
                if (i % 19 == 0){
                    for (int j =0;j<20;j++){
                        cardsDataAdapter.add("test"+j);
                    }
                }
                index = i;

                 //i   下标
                if (i1==1){
                    // TODO: 2020/10/12 0012  右滑喜欢


                }else{
                    // TODO: 2020/10/12 0012  左滑不喜欢
                }
            }
            //当用户点击顶部卡片时调用此回调。
            @Override
            public void topCardTapped() {
                Toast.makeText(getContext(), "点击了"+index, Toast.LENGTH_SHORT).show();
                // TODO: 2020/10/12 0012 传入需要使用的用户信息
                Intent intent = new Intent(getContext(), UserDetailsActivity.class);

                startActivity(intent);
            }
        });
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMsg(SaveFilterBean saveFilterBean){
        String age = saveFilterBean.getAge();
        //距离
        int distance = saveFilterBean.getDistance();
        //是否单身
        String isSingle = saveFilterBean.getIsSingle();
        //角色
        String role = saveFilterBean.getRole();

        Log.e("xxx",age+"   "+distance+"   "+isSingle+"   "+role);

        // TODO: 2020/11/18 0018 通过该筛选信息 查询列表


        // TODO: 2020/11/18 0018 设置给左滑右滑数据源
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("hmy","onViewCreated");
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d("hmy","onAttach");

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.d("hmy","onHiddenChanged");
    }

    @Override
    public void onStart() {
        super.onStart();
        index = 0;
        Log.d("hmy","onStart");
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("hmy","onResume");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("hmy","onStop");
        if(EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("hmy","onDetach");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("hmy","onPause");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("hmy","onDestroy");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("hmy","onDestroyView");
    }
}
