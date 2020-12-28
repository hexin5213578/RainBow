package com.YiDian.RainBow.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.friend.FriendsActivity;
import com.YiDian.RainBow.main.bean.NoticeCountBean;
import com.YiDian.RainBow.main.fragment.msg.adapter.MsgRecordingAdapter;
import com.YiDian.RainBow.notice.ClickNoticeActivity;
import com.YiDian.RainBow.notice.CommentNoticeActivity;
import com.YiDian.RainBow.notice.FriendNoticeActivity;
import com.YiDian.RainBow.notice.SystemNoticeActivity;
import com.YiDian.RainBow.utils.NetUtils;
import com.leaf.library.StatusBarUtil;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;
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
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FragmentMsg extends Fragment implements View.OnClickListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_my_buddy)
    RelativeLayout ivMyBuddy;
    @BindView(R.id.rc_msg_recording)
    SwipeMenuRecyclerView rcMsgRecording;
    @BindView(R.id.systemcount)
    TextView systemcount;
    @BindView(R.id.rl_system)
    RelativeLayout rlSystem;
    @BindView(R.id.friendcount)
    TextView friendcount;
    @BindView(R.id.rl_friend)
    RelativeLayout rlFriend;
    @BindView(R.id.commentcount)
    TextView commentcount;
    @BindView(R.id.rl_comment)
    RelativeLayout rlComment;
    @BindView(R.id.clickcount)
    TextView clickcount;
    @BindView(R.id.rl_click)
    RelativeLayout rlClick;
    @BindView(R.id.sv)
    SpringView sv;
    private MsgRecordingAdapter msgRecordingAdapter;
    private Intent intent;
    private int userid;
    //加载的试图
    private View mContentView;
    //三个核心变量
    private boolean isUserHint;//用户是否可见
    private boolean isViewInit;//view视图是否加载过
    private boolean isDataLoad;//耗时操作是否加载过
    private Unbinder bind;

    protected void getid(View view) {

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
        Log.i("baseF", "onViewCreated");
        if (!isViewInit) {
            getid(mContentView);
        }
        getData();
        isViewInit = true;
    }

    //这个方法优先级很高
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {//用户可见就为true 不可见就是false
        super.setUserVisibleHint(isVisibleToUser);
        this.isUserHint = isVisibleToUser;
        //对用户可见查找共有多少条通知
        if (isViewInit){
            getNoticeCount();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }

    protected int getResId() {
        return R.layout.home_fragment_msg;
    }

    protected BasePresenter initPresenter() {
        return null;
    }

    protected void getData() {
        //设置状态栏颜色与字体颜色
        StatusBarUtil.setGradientColor(getActivity(), toolbar);
        StatusBarUtil.setDarkMode(getActivity());

        //获取当前登录的用户
        userid = Integer.valueOf(Common.getUserId());

        //设置下拉
        sv.setHeader(new AliHeader(getContext()));
        sv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sv.onFinishFreshAndLoad();
                    }
                },1000);
            }

            @Override
            public void onLoadmore() {

            }
        });
        //设置点击事件监听
        ivMyBuddy.setOnClickListener(this);
        rlSystem.setOnClickListener(this);
        rlFriend.setOnClickListener(this);
        rlComment.setOnClickListener(this);
        rlClick.setOnClickListener(this);

        //测试聊天记录
        List<String> list = new ArrayList<>();
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
                Toast.makeText(getContext(), "删除" + adapterPosition, Toast.LENGTH_SHORT).show();

                //删除
                list.remove(adapterPosition);
                msgRecordingAdapter.notifyDataSetChanged();
            }
        });
        //点击事件
        rcMsgRecording.setSwipeItemClickListener(new SwipeItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Toast.makeText(getContext(), "点击了" + position, Toast.LENGTH_SHORT).show();

                //跳转至聊天详情页
            }
        });

        //创建适配器
        msgRecordingAdapter = new MsgRecordingAdapter(getContext(), list);
        rcMsgRecording.setAdapter(msgRecordingAdapter);
    }

    //获取通知数量
    public void getNoticeCount() {
        NetUtils.getInstance().getApis().doGetNoticeCount(userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NoticeCountBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(NoticeCountBean noticeCountBean) {
                        NoticeCountBean.ObjectBean bean = noticeCountBean.getObject();
                        int Systemcount = bean.getSystemMessAgeNum();
                        int Friendcount = bean.getFansMessAgeNum();
                        int Commentcount = bean.getCommentMessAgeNum();
                        int Clickcount = bean.getClickMessAgeNum();

                        //设置系统通知数
                        if (Systemcount > 0) {
                            systemcount.setVisibility(View.VISIBLE);
                            systemcount.setText(Systemcount + "");
                        } else {
                            systemcount.setVisibility(View.GONE);
                        }
                        //设置粉丝通知数
                        if (Friendcount > 0) {
                            friendcount.setVisibility(View.VISIBLE);
                            friendcount.setText(Friendcount + "");
                        } else {
                            friendcount.setVisibility(View.GONE);
                        }
                        //设置评论通知数
                        if (Commentcount > 0) {
                            commentcount.setVisibility(View.VISIBLE);
                            commentcount.setText(Commentcount + "");
                        } else {
                            commentcount.setVisibility(View.GONE);
                        }
                        //设置点赞通知数
                        if (Clickcount > 0) {
                            clickcount.setVisibility(View.VISIBLE);
                            clickcount.setText(Clickcount + "");
                        } else {
                            clickcount.setVisibility(View.GONE);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //我的好友
            case R.id.iv_my_buddy:
                //跳转到我的好友页
                intent = new Intent(getContext(), FriendsActivity.class);
                startActivity(intent);
                break;
            //系统通知
            case R.id.rl_system:
                intent = new Intent(getContext(), SystemNoticeActivity.class);
                startActivity(intent);
                break;
            //添加好友
            case R.id.rl_friend:
                intent = new Intent(getContext(), FriendNoticeActivity.class);
                startActivity(intent);
                break;
            //评论
            case R.id.rl_comment:
                intent = new Intent(getContext(), CommentNoticeActivity.class);
                startActivity(intent);
                break;
            //点赞
            case R.id.rl_click:
                intent = new Intent(getContext(), ClickNoticeActivity.class);
                startActivity(intent);
                break;
        }
    }
}
