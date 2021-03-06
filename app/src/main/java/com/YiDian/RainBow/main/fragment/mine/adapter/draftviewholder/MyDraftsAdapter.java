package com.YiDian.RainBow.main.fragment.mine.adapter.draftviewholder;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.main.fragment.home.adapter.NewDynamicAdapter;
import com.YiDian.RainBow.main.fragment.home.bean.CollectDynamicBean;
import com.YiDian.RainBow.main.fragment.mine.adapter.draftviewholder.MydraftViewHolderImg;
import com.YiDian.RainBow.main.fragment.mine.adapter.draftviewholder.MydraftViewHolderText;
import com.YiDian.RainBow.main.fragment.mine.adapter.draftviewholder.MydraftViewHolderTextAndImg;
import com.YiDian.RainBow.main.fragment.mine.adapter.draftviewholder.MydraftViewHolderVideo;
import com.YiDian.RainBow.main.fragment.mine.adapter.draftviewholder.MydraftViewHolderVideoAndText;
import com.YiDian.RainBow.main.fragment.mine.bean.SelectAllDraftsBean;
import com.YiDian.RainBow.topic.SaveIntentMsgBean;
import com.YiDian.RainBow.topic.TopicDetailsActivity;
import com.YiDian.RainBow.user.PersonHomeActivity;
import com.YiDian.RainBow.utils.NetUtils;
import com.YiDian.RainBow.utils.StringUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.YiDian.RainBow.base.Common.decodeUriAsBitmapFromNet;
import static com.YiDian.RainBow.main.fragment.home.adapter.NewDynamicAdapter.TAG;

public class MyDraftsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Activity context;
    private final List<SelectAllDraftsBean.ObjectBean.ListBean> list;


    private PopupWindow mPopupWindow;
    private SelectAllDraftsBean.ObjectBean.ListBean listBean;
    private SelectAllDraftsBean.ObjectBean.ListBean.UserInfoBean userInfo;


    private MydraftViewHolderText mydraftViewHolderText;
    private MydraftViewHolderImg mydraftViewHolderImg;
    private MydraftViewHolderTextAndImg mydraftViewHolderTextAndImg;
    private MydraftViewHolderVideo mydraftViewHolderVideo;
    private MydraftViewHolderVideoAndText mydraftViewHolderVideoAndText;


    public MyDraftsAdapter(Activity context, List<SelectAllDraftsBean.ObjectBean.ListBean> list) {

        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //?????????
        if (viewType == 1) {
            mydraftViewHolderText = MydraftViewHolderText.createViewHolder(context, parent, R.layout.item_drafts_text);
            return mydraftViewHolderText;
        }
        //?????????
        if (viewType == 2) {
            mydraftViewHolderImg = MydraftViewHolderImg.createViewHolder(context, parent, R.layout.item_drafts_img);
            return mydraftViewHolderImg;
        }
        //???????????????
        if (viewType == 21) {
            mydraftViewHolderTextAndImg = MydraftViewHolderTextAndImg.createViewHolder(context, parent, R.layout.item_drafts_text_img);
            return mydraftViewHolderTextAndImg;
        }
        //?????????
        if (viewType == 3) {
            mydraftViewHolderVideo = MydraftViewHolderVideo.createViewHolder(context, parent, R.layout.item_drafts_video);
            return mydraftViewHolderVideo;
        }
        //???????????????
        if (viewType == 31) {
            mydraftViewHolderVideoAndText = MydraftViewHolderVideoAndText.createViewHolder(context, parent, R.layout.item_drafts_video_text);
            return mydraftViewHolderVideoAndText;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof MydraftViewHolderText) {
            mydraftViewHolderText = (MydraftViewHolderText) holder;
            setdate1(mydraftViewHolderText, position);

            //??????????????????
            String contentInfo = listBean.getContentInfo();

            //??????@ ## ???????????????
            if (!contentInfo.equals("") && contentInfo.contains("@") || contentInfo.contains("#")) {
                getWeiBoContent(context, contentInfo, mydraftViewHolderText.tvDynamicText);
            } else {
                mydraftViewHolderText.tvDynamicText.setText(contentInfo);
            }

        } else if (holder instanceof MydraftViewHolderImg) {
            mydraftViewHolderImg = (MydraftViewHolderImg) holder;

            setdate2(mydraftViewHolderImg, position);

            String contentImg = list.get(position).getContentImg();
            String[] split = contentImg.split(",");

            List<String> imglist = new ArrayList<>();

            for (int i = 0; i < split.length; i++) {
                imglist.add(split[i].trim() + "?imageView2/0/format/jpg/w/400");
            }
            mydraftViewHolderImg.rcImage.setIsShowAll(false); //???????????????????????????9???????????????????????????
            mydraftViewHolderImg.rcImage.setSpacing(5); //?????????????????????????????????
            mydraftViewHolderImg.rcImage.setUrlList(imglist); //?????????????????????url
        } else if (holder instanceof MydraftViewHolderTextAndImg) {
            mydraftViewHolderTextAndImg = (MydraftViewHolderTextAndImg) holder;

            setdate3(mydraftViewHolderTextAndImg, position);
            //??????????????????
            String contentInfo = listBean.getContentInfo();

            //??????@ ## ???????????????
            if (!contentInfo.equals("") && contentInfo.contains("@") || contentInfo.contains("#")) {
                getWeiBoContent(context, contentInfo, mydraftViewHolderTextAndImg.tvDynamicText);
            } else {
                mydraftViewHolderTextAndImg.tvDynamicText.setText(contentInfo);
            }
            //????????????
            String contentImg = list.get(position).getContentImg();
            String[] split = contentImg.split(",");
            List<String> img = new ArrayList<>();
            for (int i = 0; i < split.length; i++) {
                img.add(split[i].trim() + "?imageView2/0/format/jpg/w/400");
            }
            mydraftViewHolderTextAndImg.rcImage.setIsShowAll(false); //???????????????????????????9???????????????????????????
            mydraftViewHolderTextAndImg.rcImage.setSpacing(5); //?????????????????????????????????
            mydraftViewHolderTextAndImg.rcImage.setUrlList(img); //?????????????????????url
        } else if (holder instanceof MydraftViewHolderVideo) {
            mydraftViewHolderVideo = (MydraftViewHolderVideo) holder;

            setdate4(mydraftViewHolderVideo, position);
            //??????????????????
            String contentImg = list.get(position).getContentImg();

            Bitmap netVideoBitmap = decodeUriAsBitmapFromNet(contentImg + "?vframe/jpg/offset/1/w/480/h/360");
            //????????????
            mydraftViewHolderVideo.videoPlayer.loadCoverImage(contentImg, netVideoBitmap);

            mydraftViewHolderVideo.videoPlayer.setUpLazy(contentImg, true, null, null, "");

            //??????????????????
            mydraftViewHolderVideo.videoPlayer.setPlayTag(TAG);
            mydraftViewHolderVideo.videoPlayer.setLockLand(true);
            mydraftViewHolderVideo.videoPlayer.setPlayPosition(position);
            //?????????????????????????????????????????????????????????????????????????????????????????? setLockLand ?????????????????? orientationUtils ??????
            mydraftViewHolderVideo.videoPlayer.setAutoFullWithSize(false);
            //?????????????????????????????????
            mydraftViewHolderVideo.videoPlayer.setReleaseWhenLossAudio(false);
            //????????????
            mydraftViewHolderVideo.videoPlayer.setShowFullAnimation(true);
            //????????????????????????
            mydraftViewHolderVideo.videoPlayer.setIsTouchWiget(false);
        } else if (holder instanceof MydraftViewHolderVideoAndText) {
            mydraftViewHolderVideoAndText = (MydraftViewHolderVideoAndText) holder;

            setdate5(mydraftViewHolderVideoAndText, position);
            //????????????
            //??????????????????
            String contentInfo = listBean.getContentInfo();

            //??????@ ## ???????????????
            if (!contentInfo.equals("") && contentInfo.contains("@") || contentInfo.contains("#")) {
                getWeiBoContent(context, contentInfo, mydraftViewHolderVideoAndText.tvDynamicText);
            } else {
                mydraftViewHolderVideoAndText.tvDynamicText.setText(contentInfo);
            }

            //??????????????????
            String contentImg = list.get(position).getContentImg();

            Bitmap netVideoBitmap = decodeUriAsBitmapFromNet(contentImg + "?vframe/jpg/offset/1/w/480/h/360");
            //????????????
            mydraftViewHolderVideoAndText.videoPlayer.loadCoverImage(contentImg, netVideoBitmap);

            //??????????????????
            mydraftViewHolderVideoAndText.videoPlayer.setUpLazy(contentImg, true, null, null, "");

            //??????????????????
            mydraftViewHolderVideoAndText.videoPlayer.setPlayTag(TAG);
            mydraftViewHolderVideoAndText.videoPlayer.setLockLand(true);
            mydraftViewHolderVideoAndText.videoPlayer.setPlayPosition(position);
            //?????????????????????????????????????????????????????????????????????????????????????????? setLockLand ?????????????????? orientationUtils ??????
            mydraftViewHolderVideoAndText.videoPlayer.setAutoFullWithSize(false);
            //?????????????????????????????????
            mydraftViewHolderVideoAndText.videoPlayer.setReleaseWhenLossAudio(false);
            //????????????
            mydraftViewHolderVideoAndText.videoPlayer.setShowFullAnimation(true);
            //????????????????????????
            mydraftViewHolderVideoAndText.videoPlayer.setIsTouchWiget(false);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        int imgType = list.get(position).getImgType();
        //??????????????????????????????
        return imgType;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void showSelect() {
        //?????????????????????
        mPopupWindow = new PopupWindow();
        mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_drafts, null);

        TextView tv_development = view.findViewById(R.id.tv_development);
        TextView tv_delete = view.findViewById(R.id.tv_delete);

        //???????????? ?????????????????????
        tv_development.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                NetUtils.getInstance().getApis()
                        .doUpdateDraft(listBean.getId(), 1)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<CollectDynamicBean>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(CollectDynamicBean collectDynamicBean) {
                                if (collectDynamicBean.getObject().equals("????????????")) {
                                    EventBus.getDefault().post("????????????");
                                    Toast.makeText(context, "????????????", Toast.LENGTH_SHORT).show();
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

        //????????????
        tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                NetUtils.getInstance().getApis()
                        .doDeleteDraft(listBean.getId(), listBean.getUserId())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<CollectDynamicBean>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(CollectDynamicBean collectDynamicBean) {
                                if (collectDynamicBean.getObject().equals("????????????")) {
                                    EventBus.getDefault().post("????????????");
                                    Toast.makeText(context, "????????????", Toast.LENGTH_SHORT).show();
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
        //popwindow????????????
        mPopupWindow.setAnimationStyle(R.style.popwindow_anim_style);
        mPopupWindow.setContentView(view);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setWindowAlpa(false);
            }
        });
        show(view);
    }

    //???????????????
    public void setWindowAlpa(boolean isopen) {
        if (Build.VERSION.SDK_INT < 11) {
            return;
        }
        final Window window = context.getWindow();
        final WindowManager.LayoutParams lp = window.getAttributes();
        window.setFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND, WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        ValueAnimator animator;
        if (isopen) {
            animator = ValueAnimator.ofFloat(1.0f, 0.5f);
        } else {
            animator = ValueAnimator.ofFloat(0.5f, 1.0f);
        }
        animator.setDuration(400);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float alpha = (float) animation.getAnimatedValue();
                lp.alpha = alpha;
                window.setAttributes(lp);
            }
        });
        animator.start();
    }

    /**
     * ??????PopupWindow
     */

    private void show(View v) {
        if (mPopupWindow != null && !mPopupWindow.isShowing()) {
            mPopupWindow.showAtLocation(v, Gravity.CENTER_VERTICAL, 0, 0);
        }
        setWindowAlpa(true);
    }

    /**
     * ??????PopupWindow
     */
    public void dismiss() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }

    @SuppressLint("ResourceAsColor")
    public static Spannable getWeiBoContent(final Context context, String source, TextView tv) {

        SpannableStringBuilder spannable = new SpannableStringBuilder(source);
        // ?????????????????????
        String AT = "@[\\u4e00-\\u9fa5\\w\\-]+";// @???
        String TOPIC = "#([^\\#|.]+)#";// ##??????
        //????????????
        Pattern pattern = Pattern.compile("(" + AT + ")|" + "(" + TOPIC + ")");
        Matcher matcher = pattern.matcher(spannable);

        Log.d("xxx", matcher.groupCount() + "");

        while (matcher.find()) {
            // ??????group???????????????????????????????????????????????????(0???????????????1?????????????????????)
            final String at = matcher.group(1);
            final String topic = matcher.group(2);
            // ??????@??????
            if (at != null) {
                //??????????????????
                int start = matcher.start(1);
                int end = start + at.length();

                tv.setMovementMethod(LinkMovementMethod.getInstance());
                spannable.setSpan(new NewDynamicAdapter.MyClickableSpanAt() {
                    @Override
                    public void onClick(View widget) {
                        //???????????????????????????????????????????????????Toast??????
                        String substring = at.substring(1);

                        Intent intent = new Intent(context, PersonHomeActivity.class);
                        SaveIntentMsgBean saveIntentMsgBean = new SaveIntentMsgBean();
                        saveIntentMsgBean.setMsg(substring);
                        //2??????????????????  1????????????id
                        saveIntentMsgBean.setFlag(2);
                        intent.putExtra("msg", saveIntentMsgBean);
                        context.startActivity(intent);
                    }
                }, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            }

            // ????????????##??????
            if (topic != null) {
                int start = matcher.start(2);
                int end = start + topic.length();

                tv.setMovementMethod(LinkMovementMethod.getInstance());
                spannable.setSpan(new NewDynamicAdapter.MyClickableSpanTopic() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        String substring = topic.substring(1, topic.length() - 1);

                        Intent intent = new Intent(context, TopicDetailsActivity.class);
                        SaveIntentMsgBean saveIntentMsgBean = new SaveIntentMsgBean();
                        saveIntentMsgBean.setMsg(substring);
                        //2?????????????????????  1????????????id
                        saveIntentMsgBean.setFlag(2);
                        intent.putExtra("msg", saveIntentMsgBean);
                        context.startActivity(intent);

                    }
                }, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            }
        }
        tv.setText(spannable);

        return spannable;
    }

    public void setdate1(MydraftViewHolderText holder, int position) {
        listBean = list.get(position);
        userInfo = list.get(position).getUserInfo();

        holder.rlXiala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelect();
            }
        });

        //???????????????
        holder.tvUsername.setText(userInfo.getNickName());
        //????????????
        Glide.with(context).load(userInfo.getHeadImg()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(holder.ivHeadimg);


        int attestation = userInfo.getAttestation();
        //????????????
        if (attestation == 0) {
            holder.ivIsatt.setVisibility(View.GONE);
        } else if (attestation == 1) {
            holder.ivIsatt.setImageResource(R.mipmap.qingtong);
        } else if (attestation == 2) {
            holder.ivIsatt.setImageResource(R.mipmap.baiyin);
        } else if (attestation == 3) {
            holder.ivIsatt.setImageResource(R.mipmap.huangjin);
        } else if (attestation == 4) {
            holder.ivIsatt.setImageResource(R.mipmap.bojin);
        } else if (attestation == 5) {
            holder.ivIsatt.setImageResource(R.mipmap.zuanshi);
        }

        //????????????????????????
        String userRole = userInfo.getUserRole();
        if (userRole != null) {
            if (userRole.equals("??????")) {
                holder.tvAge.setVisibility(View.GONE);
            } else if (userRole.equals("")) {
                holder.tvAge.setVisibility(View.GONE);
            } else {
                //????????????
                holder.tvAge.setText(userInfo.getUserRole());
            }
        } else {
            holder.tvAge.setVisibility(View.GONE);
        }

        //??????????????????
        String createTime = listBean.getCreateTime();
        if (createTime != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);
            try {
                Date parse = sdf.parse(createTime);

                long time = parse.getTime();

                //??????????????????
                long l = System.currentTimeMillis();
                //????????????????????????
                long difference = l - time;

                //????????????12?????? ????????????
                if (difference > 1800000) {
                    String newChatTime = StringUtil.getNewChatTime(time);
                    holder.tvTime.setText(newChatTime);
                }
                if (difference > 1200000 && difference < 1800000) {
                    holder.tvTime.setText("??????????????????");
                }
                if (difference > 600000 && difference < 1200000) {
                    holder.tvTime.setText("20???????????????");
                }
                if (difference > 300000 && difference < 600000) {
                    holder.tvTime.setText("10???????????????");
                }
                if (difference > 240000 && difference < 300000) {
                    holder.tvTime.setText("5???????????????");
                }
                if (difference > 180000 && difference < 240000) {
                    holder.tvTime.setText("4???????????????");
                }
                if (difference > 120000 && difference < 180000) {
                    holder.tvTime.setText("3???????????????");
                }
                if (difference > 60000 && difference < 120000) {
                    holder.tvTime.setText("2???????????????");
                }
                if (difference < 60000) {
                    holder.tvTime.setText("1???????????????");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public void setdate2(MydraftViewHolderImg holder, int position) {
        listBean = list.get(position);
        userInfo = list.get(position).getUserInfo();

        holder.rlXiala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelect();
            }
        });

        //???????????????
        holder.tvUsername.setText(userInfo.getNickName());
        //????????????
        Glide.with(context).load(userInfo.getHeadImg()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(holder.ivHeadimg);


        int attestation = userInfo.getAttestation();
        //????????????
        if (attestation == 0) {
            holder.ivIsatt.setVisibility(View.GONE);
        } else if (attestation == 1) {
            holder.ivIsatt.setImageResource(R.mipmap.qingtong);
        } else if (attestation == 2) {
            holder.ivIsatt.setImageResource(R.mipmap.baiyin);
        } else if (attestation == 3) {
            holder.ivIsatt.setImageResource(R.mipmap.huangjin);
        } else if (attestation == 4) {
            holder.ivIsatt.setImageResource(R.mipmap.bojin);
        } else if (attestation == 5) {
            holder.ivIsatt.setImageResource(R.mipmap.zuanshi);
        }

        //????????????????????????
        String userRole = userInfo.getUserRole();
        if (userRole != null) {
            if (userRole.equals("??????")) {
                holder.tvAge.setVisibility(View.GONE);
            } else if (userRole.equals("")) {
                holder.tvAge.setVisibility(View.GONE);
            } else {
                //????????????
                holder.tvAge.setText(userInfo.getUserRole());
            }
        } else {
            holder.tvAge.setVisibility(View.GONE);
        }

        //??????????????????
        String createTime = listBean.getCreateTime();
        if (createTime != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);
            try {
                Date parse = sdf.parse(createTime);

                long time = parse.getTime();

                //??????????????????
                long l = System.currentTimeMillis();
                //????????????????????????
                long difference = l - time;

                //????????????12?????? ????????????
                if (difference > 1800000) {
                    String newChatTime = StringUtil.getNewChatTime(time);
                    holder.tvTime.setText(newChatTime);
                }
                if (difference > 1200000 && difference < 1800000) {
                    holder.tvTime.setText("??????????????????");
                }
                if (difference > 600000 && difference < 1200000) {
                    holder.tvTime.setText("20???????????????");
                }
                if (difference > 300000 && difference < 600000) {
                    holder.tvTime.setText("10???????????????");
                }
                if (difference > 240000 && difference < 300000) {
                    holder.tvTime.setText("5???????????????");
                }
                if (difference > 180000 && difference < 240000) {
                    holder.tvTime.setText("4???????????????");
                }
                if (difference > 120000 && difference < 180000) {
                    holder.tvTime.setText("3???????????????");
                }
                if (difference > 60000 && difference < 120000) {
                    holder.tvTime.setText("2???????????????");
                }
                if (difference < 60000) {
                    holder.tvTime.setText("1???????????????");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public void setdate3(MydraftViewHolderTextAndImg holder, int position) {
        listBean = list.get(position);
        userInfo = list.get(position).getUserInfo();

        holder.rlXiala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelect();
            }
        });

        //???????????????
        holder.tvUsername.setText(userInfo.getNickName());
        //????????????
        Glide.with(context).load(userInfo.getHeadImg()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(holder.ivHeadimg);


        int attestation = userInfo.getAttestation();
        //????????????
        if (attestation == 0) {
            holder.ivIsatt.setVisibility(View.GONE);
        } else if (attestation == 1) {
            holder.ivIsatt.setImageResource(R.mipmap.qingtong);
        } else if (attestation == 2) {
            holder.ivIsatt.setImageResource(R.mipmap.baiyin);
        } else if (attestation == 3) {
            holder.ivIsatt.setImageResource(R.mipmap.huangjin);
        } else if (attestation == 4) {
            holder.ivIsatt.setImageResource(R.mipmap.bojin);
        } else if (attestation == 5) {
            holder.ivIsatt.setImageResource(R.mipmap.zuanshi);
        }

        //????????????????????????
        String userRole = userInfo.getUserRole();
        if (userRole != null) {
            if (userRole.equals("??????")) {
                holder.tvAge.setVisibility(View.GONE);
            } else if (userRole.equals("")) {
                holder.tvAge.setVisibility(View.GONE);
            } else {
                //????????????
                holder.tvAge.setText(userInfo.getUserRole());
            }
        } else {
            holder.tvAge.setVisibility(View.GONE);
        }

        //??????????????????
        String createTime = listBean.getCreateTime();
        if (createTime != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);
            try {
                Date parse = sdf.parse(createTime);

                long time = parse.getTime();

                //??????????????????
                long l = System.currentTimeMillis();
                //????????????????????????
                long difference = l - time;

                //????????????12?????? ????????????
                if (difference > 1800000) {
                    String newChatTime = StringUtil.getNewChatTime(time);
                    holder.tvTime.setText(newChatTime);
                }
                if (difference > 1200000 && difference < 1800000) {
                    holder.tvTime.setText("??????????????????");
                }
                if (difference > 600000 && difference < 1200000) {
                    holder.tvTime.setText("20???????????????");
                }
                if (difference > 300000 && difference < 600000) {
                    holder.tvTime.setText("10???????????????");
                }
                if (difference > 240000 && difference < 300000) {
                    holder.tvTime.setText("5???????????????");
                }
                if (difference > 180000 && difference < 240000) {
                    holder.tvTime.setText("4???????????????");
                }
                if (difference > 120000 && difference < 180000) {
                    holder.tvTime.setText("3???????????????");
                }
                if (difference > 60000 && difference < 120000) {
                    holder.tvTime.setText("2???????????????");
                }
                if (difference < 60000) {
                    holder.tvTime.setText("1???????????????");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public void setdate4(MydraftViewHolderVideo holder, int position) {
        listBean = list.get(position);
        userInfo = list.get(position).getUserInfo();

        holder.rlXiala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelect();
            }
        });

        //???????????????
        holder.tvUsername.setText(userInfo.getNickName());
        //????????????
        Glide.with(context).load(userInfo.getHeadImg()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(holder.ivHeadimg);


        int attestation = userInfo.getAttestation();
        //????????????
        if (attestation == 0) {
            holder.ivIsatt.setVisibility(View.GONE);
        } else if (attestation == 1) {
            holder.ivIsatt.setImageResource(R.mipmap.qingtong);
        } else if (attestation == 2) {
            holder.ivIsatt.setImageResource(R.mipmap.baiyin);
        } else if (attestation == 3) {
            holder.ivIsatt.setImageResource(R.mipmap.huangjin);
        } else if (attestation == 4) {
            holder.ivIsatt.setImageResource(R.mipmap.bojin);
        } else if (attestation == 5) {
            holder.ivIsatt.setImageResource(R.mipmap.zuanshi);
        }

        //????????????????????????
        String userRole = userInfo.getUserRole();
        if (userRole != null) {
            if (userRole.equals("??????")) {
                holder.tvAge.setVisibility(View.GONE);
            } else if (userRole.equals("")) {
                holder.tvAge.setVisibility(View.GONE);
            } else {
                //????????????
                holder.tvAge.setText(userInfo.getUserRole());
            }
        } else {
            holder.tvAge.setVisibility(View.GONE);
        }

        //??????????????????
        String createTime = listBean.getCreateTime();
        if (createTime != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);
            try {
                Date parse = sdf.parse(createTime);

                long time = parse.getTime();

                //??????????????????
                long l = System.currentTimeMillis();
                //????????????????????????
                long difference = l - time;

                //????????????12?????? ????????????
                if (difference > 1800000) {
                    String newChatTime = StringUtil.getNewChatTime(time);
                    holder.tvTime.setText(newChatTime);
                }
                if (difference > 1200000 && difference < 1800000) {
                    holder.tvTime.setText("??????????????????");
                }
                if (difference > 600000 && difference < 1200000) {
                    holder.tvTime.setText("20???????????????");
                }
                if (difference > 300000 && difference < 600000) {
                    holder.tvTime.setText("10???????????????");
                }
                if (difference > 240000 && difference < 300000) {
                    holder.tvTime.setText("5???????????????");
                }
                if (difference > 180000 && difference < 240000) {
                    holder.tvTime.setText("4???????????????");
                }
                if (difference > 120000 && difference < 180000) {
                    holder.tvTime.setText("3???????????????");
                }
                if (difference > 60000 && difference < 120000) {
                    holder.tvTime.setText("2???????????????");
                }
                if (difference < 60000) {
                    holder.tvTime.setText("1???????????????");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public void setdate5(MydraftViewHolderVideoAndText holder, int position) {
        listBean = list.get(position);
        userInfo = list.get(position).getUserInfo();

        holder.rlXiala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelect();
            }
        });

        //???????????????
        holder.tvUsername.setText(userInfo.getNickName());
        //????????????
        Glide.with(context).load(userInfo.getHeadImg()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(holder.ivHeadimg);


        int attestation = userInfo.getAttestation();
        //????????????
        if (attestation == 0) {
            holder.ivIsatt.setVisibility(View.GONE);
        } else if (attestation == 1) {
            holder.ivIsatt.setImageResource(R.mipmap.qingtong);
        } else if (attestation == 2) {
            holder.ivIsatt.setImageResource(R.mipmap.baiyin);
        } else if (attestation == 3) {
            holder.ivIsatt.setImageResource(R.mipmap.huangjin);
        } else if (attestation == 4) {
            holder.ivIsatt.setImageResource(R.mipmap.bojin);
        } else if (attestation == 5) {
            holder.ivIsatt.setImageResource(R.mipmap.zuanshi);
        }

        //????????????????????????
        String userRole = userInfo.getUserRole();
        if (userRole != null) {
            if (userRole.equals("??????")) {
                holder.tvAge.setVisibility(View.GONE);
            } else if (userRole.equals("")) {
                holder.tvAge.setVisibility(View.GONE);
            } else {
                //????????????
                holder.tvAge.setText(userInfo.getUserRole());
            }
        } else {
            holder.tvAge.setVisibility(View.GONE);
        }

        //??????????????????
        String createTime = listBean.getCreateTime();
        if (createTime != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);
            try {
                Date parse = sdf.parse(createTime);

                long time = parse.getTime();

                //??????????????????
                long l = System.currentTimeMillis();
                //????????????????????????
                long difference = l - time;

                //????????????12?????? ????????????
                if (difference > 1800000) {
                    String newChatTime = StringUtil.getNewChatTime(time);
                    holder.tvTime.setText(newChatTime);
                }
                if (difference > 1200000 && difference < 1800000) {
                    holder.tvTime.setText("??????????????????");
                }
                if (difference > 600000 && difference < 1200000) {
                    holder.tvTime.setText("20???????????????");
                }
                if (difference > 300000 && difference < 600000) {
                    holder.tvTime.setText("10???????????????");
                }
                if (difference > 240000 && difference < 300000) {
                    holder.tvTime.setText("5???????????????");
                }
                if (difference > 180000 && difference < 240000) {
                    holder.tvTime.setText("4???????????????");
                }
                if (difference > 120000 && difference < 180000) {
                    holder.tvTime.setText("3???????????????");
                }
                if (difference > 60000 && difference < 120000) {
                    holder.tvTime.setText("2???????????????");
                }
                if (difference < 60000) {
                    holder.tvTime.setText("1???????????????");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
