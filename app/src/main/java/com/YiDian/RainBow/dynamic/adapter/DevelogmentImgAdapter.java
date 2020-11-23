package com.YiDian.RainBow.dynamic.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.custom.image.CustomRoundAngleImageView;
import com.YiDian.RainBow.dynamic.activity.DevelopmentDynamicActivity;
import com.YiDian.RainBow.main.fragment.home.activity.NewDynamicImage;
import com.bumptech.glide.Glide;
import com.dmcbig.mediapicker.PickerActivity;
import com.dmcbig.mediapicker.PickerConfig;
import com.dmcbig.mediapicker.entity.Media;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DevelogmentImgAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final DevelopmentDynamicActivity dynamicActivity;
    private final ArrayList<Media> list;

    public DevelogmentImgAdapter(DevelopmentDynamicActivity dynamicActivity, ArrayList<Media> list) {

        this.dynamicActivity = dynamicActivity;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1) {
            View view = View.inflate(dynamicActivity, R.layout.item_develogmentimg, null);
            return new ViewHolder(view);
        } else if (viewType == 2) {
            View view = View.inflate(dynamicActivity, R.layout.item_develoment_nothing, null);
            return new ViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String imgurl
                = list.get(position).path;
        if (!imgurl.equals("123")) {

            Glide.with(dynamicActivity).load(list.get(position).path).into(((ViewHolder) holder).ivImage);

            ((ViewHolder) holder).ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list.remove(position);
                    EventBus.getDefault().post(position);
                    if (list.size() == 0) {
                        EventBus.getDefault().post("展示选择框");
                    }
                }
            });
            //单击预览
            ((ViewHolder) holder).ivImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<String> ImaUrl = new ArrayList<>();

                        for (int i =0;i<list.size()-1;i++){
                            ImaUrl.add(list.get(i).path);
                        }

                    Intent intent = new Intent(dynamicActivity, NewDynamicImage.class);
                    intent.putExtra("index", position);
                    intent.putStringArrayListExtra("img", (ArrayList<String>) ImaUrl);
                    dynamicActivity.startActivity(intent);
                }
            });
        } else {
            ((ViewHolder)holder).rlSelectedimg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i =0;i<list.size();i++){
                        Media media = list.get(list.size() - 1);
                        String path = media.path;
                        if(path.equals("123")){
                            list.remove(media);
                        }
                    }
                    //测试图片选择
                    //装被选中的文件
                    Intent intent = new Intent(dynamicActivity, PickerActivity.class);
                    intent.putExtra(PickerConfig.SELECT_MODE, PickerConfig.PICKER_IMAGE);//设置选择类型，默认是图片和视频可一起选择(非必填参数)
                    long maxSize = 10485760L;//long long long long类型
                    intent.putExtra(PickerConfig.MAX_SELECT_SIZE, maxSize);
                    intent.putExtra(PickerConfig.MAX_SELECT_COUNT, 9); //最大选择数量，默认40（非必填参数）
                    ArrayList<Media> defaultSelect = list;//可以设置默认选中的照片，比如把select刚刚选择的list设置成默认的。
                    intent.putExtra(PickerConfig.DEFAULT_SELECTED_LIST, defaultSelect); //可以设置默认选中的照片(非必填参数)
                    dynamicActivity.startActivityForResult(intent, 201);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        String s = list.get(position).path;
        if (s.equals("123")) {
            return 2;
        }
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_image)
        CustomRoundAngleImageView ivImage;
        @BindView(R.id.iv_delete)
        ImageView ivDelete;
        @BindView(R.id.rl_selectedimg)
        RelativeLayout rlSelectedimg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
