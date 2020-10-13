package com.YiDian.RainBow.main.fragment.find.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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
import com.YiDian.RainBow.custom.drawable.ShadowDrawable;

public class CardsDataAdapter extends ArrayAdapter<String> {

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

        RelativeLayout rlAllCard = view.findViewById(R.id.rl_allcard);
        RelativeLayout rlCardBottom = view.findViewById(R.id.rl_card_bottom);
        ImageView ivImg = view.findViewById(R.id.iv_img);
        TextView tvdistance = view.findViewById(R.id.tv_distance);
        TextView username = view.findViewById(R.id.tv_username);
        TextView xingbie = view.findViewById(R.id.tv_xingbie);
        RecyclerView rcHobby = view.findViewById(R.id.rc_hobby);
        TextView tv_qianming = view.findViewById(R.id.tv_qianming);

        // 实例：设置背景为颜色为#3D5AFE，圆角为8dp, 阴影颜色为#66000000，宽度为10dp的背景
        ShadowDrawable.setShadowDrawable(rlAllCard, Color.parseColor("#ffffff"), 10,
                Color.parseColor("#66000000"), 1, 0, 0);
        username.setText(getItem(position));

        return view;

    }

}
