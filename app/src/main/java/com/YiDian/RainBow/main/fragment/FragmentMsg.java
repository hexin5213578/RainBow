package com.YiDian.RainBow.main.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseFragment;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.friend.activity.FriendsActivity;
import com.YiDian.RainBow.main.bean.NoticeCountBean;
import com.YiDian.RainBow.main.fragment.msg.activity.FriendImActivity;
import com.YiDian.RainBow.main.fragment.msg.adapter.MsgRecordingAdapter;
import com.YiDian.RainBow.notice.ClickNoticeActivity;
import com.YiDian.RainBow.notice.CommentNoticeActivity;
import com.YiDian.RainBow.notice.FriendNoticeActivity;
import com.YiDian.RainBow.notice.SystemNoticeActivity;
import com.YiDian.RainBow.utils.NetUtils;
import com.leaf.library.StatusBarUtil;
import com.liaoinstan.springview.widget.SpringView;
import com.yanzhenjie.recyclerview.swipe.SwipeItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.Conversation;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FragmentMsg extends BaseFragment implements View.OnClickListener {
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
    @BindView(R.id.rl_nodata)
    RelativeLayout rlNodata;
    private MsgRecordingAdapter msgRecordingAdapter;
    private Intent intent;
    private int userid;
    private boolean firstInit = false;
    private List<Conversation> conversationList;

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

    Handler handler = new Handler() {//实例化一个Handler，并复写handlerMessage方法
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {//这句就不做说明了，都能看懂
                case 1:
                    //实例化时一个TimerTask实力类，调用构造函数，并执行run方法，测试用，看方法是否定时执行
                    getNoticeCount();
                    break;
            }
            super.handleMessage(msg);
        }

    };
    @Override
    protected void getData() {
        //设置状态栏颜色与字体颜色
        StatusBarUtil.setGradientColor(getActivity(), toolbar);
        StatusBarUtil.setDarkMode(getActivity());

        //设置点击事件监听
        ivMyBuddy.setOnClickListener(this);
        rlSystem.setOnClickListener(this);
        rlFriend.setOnClickListener(this);
        rlComment.setOnClickListener(this);
        rlClick.setOnClickListener(this);

        Timer timer = new Timer();//实例化一个定时器
        timer.schedule(timerTask, 0, 1000 * 60);

        firstInit  = true;
        //获取当前登录的用户
        userid = Integer.valueOf(Common.getUserId());

        //申请开启麦克风权限
        if (Build.VERSION.SDK_INT >= 23) {
            int request = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO);
            //缺少权限，进行权限申请
            if (request != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.RECORD_AUDIO}, 100);
                return;//
            }
        }
        rcMsgRecording.setAdapter(null);
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

        //设置侧滑菜单的删除事件
        rcMsgRecording.setSwipeMenuItemClickListener(new SwipeMenuItemClickListener() {
            @Override
            public void onItemClick(SwipeMenuBridge menuBridge) {
                menuBridge.closeMenu();

                int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。0是左，右是1，暂时没有用到
                int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
                int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。
                // TODO: 2021/1/8 `0008  调用删除单个回话并清空聊天记录
                Conversation conversation = conversationList.get(adapterPosition);
                if (conversation.getType().name().equals("group")){

                    conversation.resetUnreadCount();

                    boolean b = JMessageClient.deleteGroupConversation(Long.parseLong(conversation.getTargetId()));
                    if (b){
                        Log.d("xxx","删除成功");
                    }else{
                        Log.d("xxx","删除失败");
                    }

                }else{
                    String targetId = conversation.getTargetId();

                    //删除单个聊天对象
                    JMessageClient.deleteSingleConversation(targetId,Common.get_JG());
                    conversationList.remove(adapterPosition);

                    EventBus.getDefault().post("收到了信息");
                }
                getImList();
            }
        });
        getImList();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMsg(String str){
        if (str.equals("收到了信息")){
            getImList();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getReLoad(String str){
        if (str.equals("重新获取消息")){
            getImList();
        }
    }

    public void getImList() {
        //聊天记录
        conversationList = JMessageClient.getConversationList();

        List<Conversation> newconverList = new ArrayList<>();

        if (conversationList !=null && conversationList.size() > 0) {
            rlNodata.setVisibility(View.GONE);
            sv.setVisibility(View.VISIBLE);

            for (int i =0;i<conversationList.size();i++){
                String name = conversationList.get(i).getType().name();
                if (name.equals("single")){
                    newconverList.add(conversationList.get(i));
                }
            }
            if (newconverList!=null && newconverList.size()>0){
                Log.d("xxx", newconverList.size() + "");

                //创建recycleView管理器
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);

                rcMsgRecording.setLayoutManager(linearLayoutManager);
                //创建适配器
                msgRecordingAdapter = new MsgRecordingAdapter(getContext());
                msgRecordingAdapter.setData(newconverList);
                //设置适配器
                rcMsgRecording.setAdapter(msgRecordingAdapter);
            }else{
                rlNodata.setVisibility(View.VISIBLE);
                sv.setVisibility(View.GONE);
            }
        }else{
            rlNodata.setVisibility(View.VISIBLE);
            sv.setVisibility(View.GONE);
        }

    }
    //实例TimerTask,在run函数中向hangdler发送消息
    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }
    };


    @Override
    public void onResume() {
        super.onResume();
        Log.d("xxx","重新进入了");
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getStr(String str) {
        if (str.equals("重新计算数量")) {
            getNoticeCount();
        }
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

                        firstInit = true;

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
                intent.putExtra("flag",1);
                startActivity(intent);
                break;
            //系统通知
            case R.id.rl_system:
                intent = new Intent(getContext(), SystemNoticeActivity.class);
                startActivity(intent);
                break;
            //添加好友通知
            case R.id.rl_friend:
                intent = new Intent(getContext(), FriendNoticeActivity.class);
                startActivity(intent);
                break;
            //评论通知
            case R.id.rl_comment:
                intent = new Intent(getContext(), CommentNoticeActivity.class);
                startActivity(intent);
                break;
            //点赞通知
            case R.id.rl_click:
                intent = new Intent(getContext(), ClickNoticeActivity.class);
                startActivity(intent);
                break;
        }
    }
}
