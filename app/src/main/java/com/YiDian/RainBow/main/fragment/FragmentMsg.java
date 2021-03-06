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

    protected void getid(View view) {

    }

    protected int getResId() {
        return R.layout.home_fragment_msg;
    }

    protected BasePresenter initPresenter() {
        return null;
    }

    Handler handler = new Handler() {//???????????????Handler????????????handlerMessage??????
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {//???????????????????????????????????????
                case 1:
                    //??????????????????TimerTask??????????????????????????????????????????run????????????????????????????????????????????????
                    getNoticeCount();
                    break;
            }
            super.handleMessage(msg);
        }

    };
    protected void getData() {
        //????????????????????????????????????
        StatusBarUtil.setGradientColor(getActivity(), toolbar);
        StatusBarUtil.setDarkMode(getActivity());

        //????????????????????????
        ivMyBuddy.setOnClickListener(this);
        rlSystem.setOnClickListener(this);
        rlFriend.setOnClickListener(this);
        rlComment.setOnClickListener(this);
        rlClick.setOnClickListener(this);

        Timer timer = new Timer();//????????????????????????
        timer.schedule(timerTask, 0, 1000 * 60);

        firstInit  = true;
        //???????????????????????????
        userid = Integer.valueOf(Common.getUserId());

        //???????????????????????????
        if (Build.VERSION.SDK_INT >= 23) {
            int request = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO);
            if (request != PackageManager.PERMISSION_GRANTED)//?????????????????????????????????
            {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.RECORD_AUDIO}, 100);
                return;//
            }
        }
        rcMsgRecording.setAdapter(null);
        //??????????????????
        rcMsgRecording.setSwipeMenuCreator(new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(getContext())
                        .setBackground(R.color.red)
                        .setImage(R.mipmap.delete)
                        .setHeight(ViewGroup.LayoutParams.MATCH_PARENT)//????????????????????????match_parent????????????item????????????
                        .setWidth(150);//?????????
                swipeRightMenu.addMenuItem(deleteItem);//?????????????????????
            }
        });

        //?????????????????????????????????
        rcMsgRecording.setSwipeMenuItemClickListener(new SwipeMenuItemClickListener() {
            @Override
            public void onItemClick(SwipeMenuBridge menuBridge) {
                menuBridge.closeMenu();

                int direction = menuBridge.getDirection(); // ???????????????????????????0???????????????1?????????????????????
                int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView???Item???position???
                int menuPosition = menuBridge.getPosition(); // ?????????RecyclerView???Item??????Position???
                // TODO: 2021/1/8 `0008  ?????????????????????????????????????????????
                Conversation conversation = conversationList.get(adapterPosition);
                String targetId = conversation.getTargetId();

                //????????????????????????
                JMessageClient.deleteSingleConversation(targetId,Common.get_JG());
                conversationList.remove(adapterPosition);

                getImList();
            }
        });
        //????????????
        rcMsgRecording.setSwipeItemClickListener(new SwipeItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {

            }
        });


    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMsg(String str){
        if (str.equals("???????????????")){
            getImList();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getReLoad(String str){
        if (str.equals("??????????????????")){
            getImList();
        }
    }

    public void getImList() {
        //????????????
        conversationList = JMessageClient.getConversationList();
        if (conversationList !=null && conversationList.size() > 0) {
            rlNodata.setVisibility(View.GONE);
            sv.setVisibility(View.VISIBLE);
            Log.d("xxx", conversationList.size() + "");

            //??????recycleView?????????
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);

            rcMsgRecording.setLayoutManager(linearLayoutManager);
            //???????????????
            msgRecordingAdapter = new MsgRecordingAdapter(getContext());
            msgRecordingAdapter.setData(conversationList);
            //???????????????
            rcMsgRecording.setAdapter(msgRecordingAdapter);
        }else{
            rlNodata.setVisibility(View.VISIBLE);
            sv.setVisibility(View.GONE);
        }

    }

    TimerTask timerTask = new TimerTask() {//??????TimerTask,???run????????????hangdler????????????

        @Override
        public void run() {
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }
    };

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            Log.d("xxx", "fragmentmsg???setUserVisibleHint");
            if (firstInit) {
                //????????????????????????
                getImList();
                getNoticeCount();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("xxx","???????????????");
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        getImList();
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
        if (str.equals("??????????????????")) {
            getNoticeCount();
        }
    }

    //??????????????????
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

                        //?????????????????????
                        if (Systemcount > 0) {
                            systemcount.setVisibility(View.VISIBLE);
                            systemcount.setText(Systemcount + "");
                        } else {
                            systemcount.setVisibility(View.GONE);
                        }
                        //?????????????????????
                        if (Friendcount > 0) {
                            friendcount.setVisibility(View.VISIBLE);
                            friendcount.setText(Friendcount + "");
                        } else {
                            friendcount.setVisibility(View.GONE);
                        }
                        //?????????????????????
                        if (Commentcount > 0) {
                            commentcount.setVisibility(View.VISIBLE);
                            commentcount.setText(Commentcount + "");
                        } else {
                            commentcount.setVisibility(View.GONE);
                        }
                        //?????????????????????
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
            //????????????
            case R.id.iv_my_buddy:
                //????????????????????????
                intent = new Intent(getContext(), FriendsActivity.class);
                intent.putExtra("flag",1);
                startActivity(intent);
                break;
            //????????????
            case R.id.rl_system:
                intent = new Intent(getContext(), SystemNoticeActivity.class);
                startActivity(intent);
                break;
            //??????????????????
            case R.id.rl_friend:
                intent = new Intent(getContext(), FriendNoticeActivity.class);
                startActivity(intent);
                break;
            //????????????
            case R.id.rl_comment:
                intent = new Intent(getContext(), CommentNoticeActivity.class);
                startActivity(intent);
                break;
            //????????????
            case R.id.rl_click:
                intent = new Intent(getContext(), ClickNoticeActivity.class);
                startActivity(intent);
                break;
        }
    }
}
