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
import com.YiDian.RainBow.main.fragment.im.adapter.ChatRoomAdapter;
import com.bumptech.glide.Glide;

import static com.YiDian.RainBow.base.Common.getRoundCornerImage;

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
        CustomRoundAngleImageView ivImg = view.findViewById(R.id.iv_img);
        TextView tvdistance = view.findViewById(R.id.tv_distance);
        TextView username = view.findViewById(R.id.tv_username);
        TextView xingbie = view.findViewById(R.id.tv_xingbie);
        RecyclerView rcHobby = view.findViewById(R.id.rc_hobby);
        TextView tv_qianming = view.findViewById(R.id.tv_qianming);


        Glide.with(context).load(R.mipmap.headimg).into(ivImg);
        username.setText(getItem(position));


        return view;

    }

}
