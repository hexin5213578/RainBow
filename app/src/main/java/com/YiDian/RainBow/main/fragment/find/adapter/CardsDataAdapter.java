package com.YiDian.RainBow.main.fragment.find.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.custom.HalfType;
import com.YiDian.RainBow.custom.drawable.ShadowDrawable;
import com.YiDian.RainBow.custom.image.CustomRoundAngleImageView;
import com.YiDian.RainBow.main.fragment.find.bean.AllUserInfoBean;
import com.YiDian.RainBow.main.fragment.im.adapter.ChatRoomAdapter;
import com.YiDian.RainBow.utils.StringUtil;
import com.bumptech.glide.Glide;

import static com.YiDian.RainBow.base.Common.getRoundCornerImage;

public class CardsDataAdapter extends ArrayAdapter<AllUserInfoBean.ObjectBean.ListBean> {

    @NonNull
    private final Context context;
    private final int resource;

    public CardsDataAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = View.inflate(context, R.layout.card_layout,null);

        AllUserInfoBean.ObjectBean.ListBean bean = getItem(position);


        CustomRoundAngleImageView ivImg = view.findViewById(R.id.iv_img);
        TextView tvdistance = view.findViewById(R.id.tv_distance);
        TextView username = view.findViewById(R.id.tv_username);
        TextView xingbie = view.findViewById(R.id.tv_xingbie);
        TextView tv_qianming = view.findViewById(R.id.tv_qianming);

        //加载头像
        Glide.with(context).load(bean.getHeadImg()).into(ivImg);
        //设置距离
        int distance = bean.getDistance();
        if(distance<1000){
            tvdistance.setText(distance+"m");
        }else{
            String dis = String.valueOf(distance/1000);
            tvdistance.setText(dis+"Km");
        }
        //设置用户名
        username.setText(bean.getNickName());
        //设置角色
        if(bean.getUserRole().equals("保密")){
            xingbie.setText("密");
        }else{
            xingbie.setText(bean.getUserRole());
        }
        //设置个性签名
        tv_qianming.setText(bean.getExplains());
        return view;

    }

}
