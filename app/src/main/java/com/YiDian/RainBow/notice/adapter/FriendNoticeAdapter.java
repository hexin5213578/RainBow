package com.YiDian.RainBow.notice.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.custom.customDialog.CustomDialogCancleFollow;
import com.YiDian.RainBow.main.fragment.home.bean.FollowBean;
import com.YiDian.RainBow.notice.bean.FriendNoticeBean;
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

public class FriendNoticeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final List<FriendNoticeBean.ObjectBean.ListBean> list;
    private int userid;
    private FriendNoticeBean.ObjectBean.ListBean listBean;
    private int userType;


    public FriendNoticeAdapter(Context context, List<FriendNoticeBean.ObjectBean.ListBean> list) {

        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_friendnotice, null);
        return new ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        userid = Integer.valueOf(Common.getUserId());
        listBean = list.get(position);
        //加载头像
        Glide.with(context).load(listBean.getHeadImg()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(((ViewHolder)holder).ivHeadimg);
        //设置名字
        ((ViewHolder)holder).tvName.setText(listBean.getNickName());
        //设置时间
        String createTime = listBean.getCreateTime();
        long l = TimeUtil.TimeToLong(createTime);
        String newChatTime = StringUtil.getNewChatTime(l);

        //设置时间
        ((ViewHolder)holder).tvTime.setText(newChatTime);

        //跳转到用户主页
        ((ViewHolder)holder).ivHeadimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listBean = list.get(position);

                Intent intent = new Intent(context, PersonHomeActivity.class);
                SaveIntentMsgBean saveIntentMsgBean = new SaveIntentMsgBean();
                saveIntentMsgBean.setId(listBean.getUserMsgId());
                //2标记传入姓名  1标记传入id
                saveIntentMsgBean.setFlag(1);
                intent.putExtra("msg",saveIntentMsgBean);
                context.startActivity(intent);
            }
        });
        ((ViewHolder)holder).tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listBean = list.get(position);

                Intent intent = new Intent(context, PersonHomeActivity.class);
                SaveIntentMsgBean saveIntentMsgBean = new SaveIntentMsgBean();
                saveIntentMsgBean.setId(listBean.getUserMsgId());
                //2标记传入姓名  1标记传入id
                saveIntentMsgBean.setFlag(1);
                intent.putExtra("msg",saveIntentMsgBean);
                context.startActivity(intent);
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
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_time)
        TextView tvTime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
