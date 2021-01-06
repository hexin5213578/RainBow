package com.YiDian.RainBow.setup.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.custom.customDialog.CustomDialogCleanNotice;
import com.YiDian.RainBow.friend.adapter.ContactAdapter;
import com.YiDian.RainBow.notice.FriendNoticeActivity;
import com.YiDian.RainBow.notice.bean.CleanNoticeBean;
import com.YiDian.RainBow.setup.bean.BlackListBean;
import com.YiDian.RainBow.setup.bean.InsertRealBean;
import com.YiDian.RainBow.utils.NetUtils;
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

public class BlackListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final List<BlackListBean.ObjectBean.ListBean> list;
    private BlackListBean.ObjectBean.ListBean listBean;


    public BlackListAdapter(Context context, List<BlackListBean.ObjectBean.ListBean> list) {

        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_blackfriend, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        listBean = list.get(position);
        //设置头像
        Glide.with(context).load(listBean.getHeadImg()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(((ViewHolder) holder).ivHeadimg);
        //设置名字
        ((ViewHolder)holder).tvName.setText(listBean.getNickName());

        ((ViewHolder)holder).tvYichu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listBean = list.get(position);
                CustomDialogCleanNotice.Builder builder = new CustomDialogCleanNotice.Builder(context);
                builder.setMessage("确定将他移出黑名单嘛?").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        NetUtils.getInstance().getApis()
                                .doDeleteBlackFriend(listBean.getUserId(),listBean.getBeUserId())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<InsertRealBean>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onNext(InsertRealBean insertRealBean) {
                                        dialog.dismiss();
                                        if (insertRealBean.getMsg().equals("移出成功")){
                                            EventBus.getDefault().post("刷新界面");
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
                });
                builder.setNegativeButton("取消",
                        new android.content.DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.create().show();
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
        @BindView(R.id.tv_yichu)
        TextView tvYichu;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
