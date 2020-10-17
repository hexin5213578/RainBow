package com.YiDian.RainBow.main.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseFragment;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.main.activity.MainActivity;
import com.YiDian.RainBow.main.fragment.msg.adapter.MsgRecordingAdapter;
import com.leaf.library.StatusBarUtil;
import com.yanzhenjie.recyclerview.swipe.SwipeItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

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
    SwipeMenuRecyclerView rcMsgRecording;
    private MsgRecordingAdapter msgRecordingAdapter;

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
        list.add("何梦洋");
        list.add("何梦洋");
        list.add("何梦洋");
        list.add("何梦洋");
        list.add("何梦洋");
        list.add("何梦洋");
        list.add("何梦洋");
        //创建recycleView管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rcMsgRecording.setLayoutManager(linearLayoutManager);

        //设置侧滑菜单
        rcMsgRecording.setSwipeMenuCreator(new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(getContext())
                        .setBackground(R.color.red)
                        .setImage(R.mipmap.delete)
                        .setHeight(ViewGroup.LayoutParams.MATCH_PARENT)//设置高，这里使用match_parent，就是与item的高相同
                        .setWidth(150);//设置宽
                swipeRightMenu.addMenuItem(deleteItem);//设置右边的侧滑
            }
        });
        //设置侧滑菜单的点击事件
        rcMsgRecording.setSwipeMenuItemClickListener(new SwipeMenuItemClickListener() {
            @Override
            public void onItemClick(SwipeMenuBridge menuBridge) {
                menuBridge.closeMenu();

                int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。0是左，右是1，暂时没有用到
                int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
                int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。
                Toast.makeText(getContext(), "删除"+adapterPosition, Toast.LENGTH_SHORT).show();

                //删除
                list.remove(adapterPosition);
                msgRecordingAdapter.notifyDataSetChanged();
            }
        });
        //点击事件
        rcMsgRecording.setSwipeItemClickListener(new SwipeItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Toast.makeText(getContext(), "点击了"+position, Toast.LENGTH_SHORT).show();

                //跳转至聊天详情页
            }
        });

        //创建适配器
        msgRecordingAdapter = new MsgRecordingAdapter(getContext(), list);
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
