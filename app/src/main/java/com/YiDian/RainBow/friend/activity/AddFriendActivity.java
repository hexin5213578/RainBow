package com.YiDian.RainBow.friend.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.custom.zbar.CaptureActivity;
import com.YiDian.RainBow.friend.adapter.RecommendAdapter;
import com.YiDian.RainBow.friend.adapter.RecommendUserAdapter;
import com.YiDian.RainBow.friend.bean.RecommendGroupBean;
import com.YiDian.RainBow.friend.bean.RecommendUserBean;
import com.YiDian.RainBow.topic.SaveIntentMsgBean;
import com.YiDian.RainBow.user.PersonHomeActivity;
import com.YiDian.RainBow.utils.NetUtils;
import com.leaf.library.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

//添加好友及群组
public class AddFriendActivity extends BaseAvtivity implements View.OnClickListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_back)
    LinearLayout ivBack;
    @BindView(R.id.ll_tosearch)
    LinearLayout llTosearch;
    @BindView(R.id.rc_recommendfriend)
    RecyclerView rcRecommendfriend;
    @BindView(R.id.rc_recommendgroup)
    RecyclerView rcRecommendgroup;
    @BindView(R.id.rl_saoyisao)
    RelativeLayout rlSaoyisao;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;
    private int userid;
    private Intent intent;

    @Override
    protected int getResId() {
        return R.layout.activity_addfriend;
    }

    @Override
    protected void getData() {
        StatusBarUtil.setGradientColor(AddFriendActivity.this, toolbar);
        StatusBarUtil.setDarkMode(AddFriendActivity.this);

        userid = Integer.valueOf(Common.getUserId());


        //获取数据
        getRecommendFriend();
        getRecommendGroup();

        ivBack.setOnClickListener(this);
        llTosearch.setOnClickListener(this);
        rlSaoyisao.setOnClickListener(this);
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getString(String str) {
        if (str.equals("刷新推荐好友")) {
            getRecommendFriend();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    //获取推荐好友
    public void getRecommendFriend() {
        NetUtils.getInstance()
                .getApis()
                .dogetRecommendFriend(userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RecommendUserBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(RecommendUserBean recommendUserBean) {
                        List<RecommendUserBean.ObjectBean> list = recommendUserBean.getObject();
                        if (list != null && list.size() > 0) {
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AddFriendActivity.this, RecyclerView.VERTICAL, false);
                            rcRecommendfriend.setLayoutManager(linearLayoutManager);

                            RecommendUserAdapter adapter = new RecommendUserAdapter(AddFriendActivity.this, list);
                            rcRecommendfriend.setAdapter(adapter);
                        }else{
                            rcRecommendfriend.setVisibility(View.GONE);
                            tv1.setVisibility(View.GONE);
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

    //获取推荐群组
    public void getRecommendGroup() {
        NetUtils.getInstance()
                .getApis()
                .dogetRecommendGroup(userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RecommendGroupBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(RecommendGroupBean recommendGroupBean) {
                        List<RecommendGroupBean.ObjectBean> list = recommendGroupBean.getObject();
                        if (list != null && list.size() > 0) {
                            //创建布局管理器
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AddFriendActivity.this, RecyclerView.VERTICAL, false);
                            rcRecommendgroup.setLayoutManager(linearLayoutManager);

                            RecommendAdapter adapter = new RecommendAdapter(AddFriendActivity.this, list);
                            rcRecommendgroup.setAdapter(adapter);
                        }else{
                            rcRecommendgroup.setVisibility(View.GONE);
                            tv2.setVisibility(View.GONE);
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
            case R.id.iv_back:
                //返回
                finish();
                break;
            case R.id.ll_tosearch:
                //跳转到搜索好友页
                intent = new Intent(AddFriendActivity.this, SearchFriendActivity.class);
                startActivity(intent);
                break;
            //扫一扫添加好友
            case R.id.rl_saoyisao:
                //扫描二维码
                intent = new Intent(AddFriendActivity.this, CaptureActivity.class);
                startActivityForResult(intent, 100);
                break;
        }
    }

    // TODO: 2020/12/25 0025 二维码识别回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("xxx", +requestCode + "   " + resultCode);
        Log.e("xxx", "添加好友扫一扫识别成功");
        // 扫描二维码回传
        if (resultCode == RESULT_OK) {
            if (data != null) {
                //获取扫描结果
                Bundle bundle = data.getExtras();
                String result = bundle.getString(CaptureActivity.EXTRA_STRING);

                //判断信息是否属于彩虹 属于彩虹+id格式 跳转到扫描成功页 通过ID查询用户信息
                if (result.contains("彩虹App内用户")) {
                    String substring = result.substring(8);

                    Log.e("xxx", substring);

                    //跳转到用户信息页
                    Intent intent = new Intent(AddFriendActivity.this, PersonHomeActivity.class);
                    SaveIntentMsgBean saveIntentMsgBean = new SaveIntentMsgBean();
                    saveIntentMsgBean.setId(Integer.parseInt(substring));
                    //2标记传入姓名  1标记传入id
                    saveIntentMsgBean.setFlag(1);
                    intent.putExtra("msg", saveIntentMsgBean);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "请扫描用户的二维码", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
