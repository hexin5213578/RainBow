package com.YiDian.RainBow.main.fragment;

import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseFragment;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.main.fragment.msg.adapter.MsgRecordingAdapter;
import com.leaf.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class FragmentMsg extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_my_buddy)
    ImageView ivMyBuddy;
    @BindView(R.id.iv_notice)
    ImageView ivNotice;
    @BindView(R.id.iv_buddy)
    ImageView ivBuddy;
    @BindView(R.id.iv_comment)
    ImageView ivComment;
    @BindView(R.id.iv_like)
    ImageView ivLike;
    @BindView(R.id.rc_msg_recording)
    RecyclerView rcMsgRecording;

    @Override
    protected void getid(View view) {

    }

    @Override
    protected int getResId() {
        return R.layout.home_fragment_msg;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void getData() {
        //设置状态栏颜色与字体颜色

        StatusBarUtil.setGradientColor(getActivity(), toolbar);
        StatusBarUtil.setDarkMode(getActivity());

        //设置点击事件监听
        ivMyBuddy.setOnClickListener(this);
        ivNotice.setOnClickListener(this);
        ivBuddy.setOnClickListener(this);
        ivComment.setOnClickListener(this);
        ivLike.setOnClickListener(this);


        //测试聊天记录
        List<String> list = new ArrayList<>();
        list.add("何梦洋");
        //创建recycleView管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rcMsgRecording.setLayoutManager(linearLayoutManager);
        //创建适配器
        MsgRecordingAdapter msgRecordingAdapter = new MsgRecordingAdapter(getContext(), list);
        rcMsgRecording.setAdapter(msgRecordingAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //我的好友
            case R.id.iv_my_buddy:

                break;
                //系统通知
            case R.id.iv_notice:

                break;
                //添加好友
            case R.id.iv_buddy:

                break;
                //评论
            case R.id.iv_comment:

                break;
                //点赞
            case R.id.iv_like:

                break;
        }
    }
}
