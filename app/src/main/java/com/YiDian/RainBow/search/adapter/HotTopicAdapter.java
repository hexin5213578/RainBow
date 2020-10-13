package com.YiDian.RainBow.search.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.custom.HalfType;
import com.YiDian.RainBow.search.activity.TopicDetailsActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.YiDian.RainBow.base.Common.getRoundCornerImage;

public class HotTopicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final List<String> list;


    public HotTopicAdapter(Context context, List<String> list) {

        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_hot_topic, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        //设置图片圆角
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.headimg); // 先从资源中把背景图获取出来
        Bitmap roundBitmap = getRoundCornerImage(bitmap, 10, HalfType.ALL); // 将图片的上半部分圆弧化。
        Drawable dw = new BitmapDrawable(context.getResources(), roundBitmap);
        ((ViewHolder) holder).ivHeadimg.setBackgroundDrawable(dw); // 设置背景。API>=16的話，可以直接用setBackground方法

        ((ViewHolder)holder).rlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2020/10/13 0013 传递话题id查询详细话题
                Intent intent = new Intent(context, TopicDetailsActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_headimg)
        ImageView ivHeadimg;
        @BindView(R.id.tv_topic)
        TextView tvTopic;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_num)
        TextView tvNum;
        @BindView(R.id.rl_item)
        RelativeLayout rlItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
