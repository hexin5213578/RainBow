package com.YiDian.RainBow.music;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.custom.loading.CustomDialog;
import com.YiDian.RainBow.music.adapter.MusicAdapter;
import com.YiDian.RainBow.music.bean.GetMusicBean;
import com.YiDian.RainBow.music.bean.MusicDetailsBean;
import com.YiDian.RainBow.music.bean.SaveSendMusicBean;
import com.YiDian.RainBow.utils.NetUtils;
import com.leaf.library.StatusBarUtil;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SendMusicActivity extends BaseAvtivity {
    String url = "https://autumnfish.cn/song/url";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_back)
    LinearLayout ivBack;
    @BindView(R.id.rc_music)
    RecyclerView rcMusic;
    @BindView(R.id.sv)
    SpringView sv;
    int size = 20;
    int page = 1;
    private List<GetMusicBean.ObjectBean> allList;
    private CustomDialog customDialog;

    @Override
    protected int getResId() {
        return R.layout.activity_sendmusic;
    }

    @Override
    protected void getData() {
        StatusBarUtil.setGradientColor(SendMusicActivity.this, toolbar);
        StatusBarUtil.setDarkMode(SendMusicActivity.this);

        customDialog = new CustomDialog(SendMusicActivity.this, "正在加载音乐...");
        allList = new ArrayList<>();

        getMusic(page,size);
        sv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                allList.clear();
                page = 1;
                getMusic(page,size);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sv.onFinishFreshAndLoad();
                    }
                },1000);
            }

            @Override
            public void onLoadmore() {
                page ++;
                getMusic(page,size);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sv.onFinishFreshAndLoad();
                    }
                },1000);
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 获取推荐音乐列表
     */
    public void getMusic(int page, int size) {
        customDialog.show();
        NetUtils.getInstance().getApis()
                .doGetMusic(page, size)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GetMusicBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull GetMusicBean getMusicMsgBean) {
                        customDialog.dismiss();
                        if (getMusicMsgBean.getMsg().equals("查询成功")){
                            List<GetMusicBean.ObjectBean> list = getMusicMsgBean.getObject();
                            if (list!=null &&list.size()>0){
                                sv.setHeader(new AliHeader(SendMusicActivity.this));
                                sv.setFooter(new AliFooter(SendMusicActivity.this));
                                allList.addAll(list);

                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SendMusicActivity.this, RecyclerView.VERTICAL, false);
                                rcMusic.setLayoutManager(linearLayoutManager);

                                MusicAdapter musicAdapter = new MusicAdapter(SendMusicActivity.this,allList);
                                rcMusic.setAdapter(musicAdapter);
                            }else{
                                Toast.makeText(SendMusicActivity.this, "没有更多内容了", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        customDialog.dismiss();

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMusicMsg(GetMusicBean.ObjectBean bean){
        Log.d("xxx","在音乐列表接收到需要发送的音乐");
        int music_id = bean.getMusic_id();
        String music_auth = bean.getMusic_auth();
        String music_img = bean.getMusic_img();
        String music_name = bean.getMusic_name();

        SaveSendMusicBean saveSendMusicBean = new SaveSendMusicBean();
        saveSendMusicBean.setName(music_name);
        saveSendMusicBean.setAuthor(music_auth);
        saveSendMusicBean.setImg(music_img);
        saveSendMusicBean.setId(music_id);

        NetUtils.getInstance().getApis()
                .doGetMusicDetails(url,music_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MusicDetailsBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull MusicDetailsBean musicDetailsBean) {
                        if (musicDetailsBean.getCode()==200){
                            String url = musicDetailsBean.getData().get(0).getUrl();

                            saveSendMusicBean.setPath(url);
                            EventBus.getDefault().post(saveSendMusicBean);
                            finish();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

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
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }
}
