package com.YiDian.RainBow.topic;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.topic.adapter.TopicAdapter;
import com.YiDian.RainBow.utils.NetUtils;
import com.leaf.library.StatusBarUtil;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.tencent.tauth.Tencent;

import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

//话题详情界面
public class TopicDetailsActivity extends BaseAvtivity implements View.OnClickListener {
    private final String TAG = "xxx";
    @BindView(R.id.iv_back)
    LinearLayout ivBack;
    @BindView(R.id.tv_topic)
    TextView tvTopic;
    @BindView(R.id.tv_llcount)
    TextView tvLlcount;
    @BindView(R.id.iv_caidan)
    ImageView ivCaidan;
    List<TopicBean.ObjectBean.ListBean> alllist;
    @BindView(R.id.rc_list)
    RecyclerView rcList;
    @BindView(R.id.sv)
    SpringView sv;
    @BindView(R.id.ll_caidan)
    LinearLayout llCaidan;

    private Tencent mTencent;
    int page = 1;
    int size = 10;
    private int userid;

    @Override
    protected int getResId() {
        return R.layout.activity_topic_details;
    }

    @Override
    protected void getData() {
        ivBack.setOnClickListener(this);
        StatusBarUtil.setTransparentForWindow(TopicDetailsActivity.this);
        StatusBarUtil.setDarkMode(TopicDetailsActivity.this);

        //2标记传入话题名  1标记传入id
        Intent intent =
                getIntent();
        SaveIntentMsgBean msg = (SaveIntentMsgBean) intent.getSerializableExtra("msg");
        mTencent = Tencent.createInstance("101906973", TopicDetailsActivity.this);

        userid = Integer.valueOf(Common.getUserId());
        alllist = new ArrayList<>();

        int flag = msg.getFlag();
        if (flag == 2) {
            String name = msg.getMsg();
            Log.d("xxx", "传过来的话题名为" + name);
            tvTopic.setText(name);
            refresh(name, page);
            sv.setListener(new SpringView.OnFreshListener() {
                @Override
                public void onRefresh() {
                    alllist.clear();
                    refresh(name, 1);
                }

                @Override
                public void onLoadmore() {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            page++;
                            refresh(name, page);
                            sv.onFinishFreshAndLoad();
                        }
                    }, 2000);

                }
            });

        } else {
            int id = msg.getId();
            Log.d("xxx", "传过来的id为" + id);
        }
    }

    public void refresh(String msg, int page) {
        NetUtils.getInstance().getApis().doGetTopicByKey(userid, msg, page, size).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<TopicBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(TopicBean topicBean) {
                        List<TopicBean.ObjectBean.ListBean> list = topicBean.getObject().getList();
                        tvLlcount.setText("参与数：" + topicBean.getObject().getList().get(0).getTopics().get(0).getTopicNum());
                        sv.setHeader(new AliHeader(TopicDetailsActivity.this));

                        if (list != null && list.size() > 0) {
                            Log.d(TAG, "onNext: 拿到数据");

                            LinearLayoutManager layoutManager =
                                    new LinearLayoutManager(TopicDetailsActivity.this, RecyclerView.VERTICAL, false);
                            alllist.addAll(list);

                            TopicAdapter adapter = new TopicAdapter(TopicDetailsActivity.this, alllist, mTencent);
                            rcList.setLayoutManager(layoutManager);
                            rcList.setAdapter(adapter);
                        }
                        if (list.size()>9){
                            sv.setFooter(new AliFooter(TopicDetailsActivity.this));
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
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_caidan:

                break;

        }

    }
}
