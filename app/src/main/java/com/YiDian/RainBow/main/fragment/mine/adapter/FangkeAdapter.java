package com.YiDian.RainBow.main.fragment.mine.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.main.fragment.home.bean.FollowBean;
import com.YiDian.RainBow.main.fragment.mine.bean.FangkeMsgBean;
import com.YiDian.RainBow.topic.SaveIntentMsgBean;
import com.YiDian.RainBow.user.PersonHomeActivity;
import com.YiDian.RainBow.utils.NetUtils;
import com.YiDian.RainBow.utils.StringUtil;
import com.YiDian.RainBow.utils.TimeUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FangkeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final List<FangkeMsgBean.ObjectBean.MeetsBean> list;

    private FangkeMsgBean.ObjectBean.MeetsBean bean;


    public FangkeAdapter(Context context, List<FangkeMsgBean.ObjectBean.MeetsBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_fangke, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        bean = list.get(position);

        Glide.with(context).load(bean.getHeadImg()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(((ViewHolder) holder).ivHeadimg);

        ((ViewHolder) holder).ivHeadimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到用户信息页
                bean = list.get(position);

                Intent intent = new Intent(context, PersonHomeActivity.class);
                SaveIntentMsgBean saveIntentMsgBean = new SaveIntentMsgBean();
                saveIntentMsgBean.setId(bean.getUserId());
                //2标记传入姓名  1标记传入id
                saveIntentMsgBean.setFlag(1);
                intent.putExtra("msg", saveIntentMsgBean);
                context.startActivity(intent);
            }
        });

        ((ViewHolder) holder).tvName.setText(bean.getNickName());

        ((ViewHolder) holder).tvAutograph.setText("共访问了您" + bean.getAccessNum() + "次");
        String userRole = bean.getUserRole();
        if (userRole != null) {
            if (userRole.equals("保密")) {
                ((ViewHolder) holder).tvXingbie.setVisibility(View.GONE);
            } else {
                ((ViewHolder) holder).tvXingbie.setText(userRole);
            }
        } else {
            ((ViewHolder) holder).tvXingbie.setVisibility(View.GONE);
        }


        //设置访问时间
        long l = TimeUtil.TimeToLong(bean.getCreateTime());
        String newChatTime = StringUtil.getNewChatTime(l);
        ((ViewHolder) holder).tvTime.setText(newChatTime);


        int isFans = bean.getIsFans();
        if (isFans == 1) {
            ((ViewHolder) holder).btGuanzhu.setVisibility(View.GONE);
        } else {
            ((ViewHolder) holder).btGuanzhu.setVisibility(View.VISIBLE);
            ((ViewHolder) holder).btGuanzhu.setText("关注");
        }
        ((ViewHolder) holder).btGuanzhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bean = list.get(position);
                ((ViewHolder) holder).btGuanzhu.setEnabled(false);
                //发起关注
                NetUtils.getInstance().getApis()
                        .doFollow(bean.getBeUserId(), bean.getUserId())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<FollowBean>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(FollowBean followBean) {
                                EventBus.getDefault().post("刷新访客页面");
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_headimg)
        ImageView ivHeadimg;
        @BindView(R.id.tv_xingbie)
        TextView tvXingbie;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_autograph)
        TextView tvAutograph;
        @BindView(R.id.bt_guanzhu)
        Button btGuanzhu;
        @BindView(R.id.rl_item)
        RelativeLayout rlItem;
        @BindView(R.id.tv_time)
        TextView tvTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
