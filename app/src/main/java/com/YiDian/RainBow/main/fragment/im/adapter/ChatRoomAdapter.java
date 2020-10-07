package com.YiDian.RainBow.main.fragment.im.adapter;

import android.content.Context;
import android.content.res.Resources;
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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.YiDian.RainBow.base.Common.getRoundCornerImage;

public class ChatRoomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final List<String> list;

    public ChatRoomAdapter(Context context, List<String> list) {

        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_chatroom, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),R.mipmap.lovelygirl); // 先从资源中把背景图获取出来
        Bitmap roundBitmap = getRoundCornerImage(bitmap, 30, HalfType.ALL); // 将图片的上半部分圆弧化。
        Drawable dw = new BitmapDrawable(context.getResources(), roundBitmap);
        ((ViewHolder) holder).llBg.setBackgroundDrawable(dw); // 设置背景。API>=16的話，可以直接用setBackground方法

    }

    //设置圆角图片
/*    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg); // 先从资源中把背景图获取出来
    Bitmap roundBitmap = getRoundCornerImage(bitmap, 30, HalfType.TOP); // 将图片的上半部分圆弧化。
    ImageView image = (ImageView) findViewById(R.id.image);
    Drawable dw = new BitmapDrawable(getResources(),roundBitmap);
        image.setBackgroundDrawable(dw); // 设置背景。API>=16的話，可以直接用setBackground方法*/


    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_reason)
        TextView tvReason;
        @BindView(R.id.tv_num)
        TextView tvNum;
        @BindView(R.id.ll_bg)
        RelativeLayout llBg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
