package com.YiDian.RainBow.friend.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.custom.friend.ContactComparator;
import com.YiDian.RainBow.friend.bean.Contact;
import com.YiDian.RainBow.friend.bean.FriendBean;
import com.YiDian.RainBow.main.fragment.find.adapter.AllLikeAdapter;
import com.YiDian.RainBow.main.fragment.msg.activity.ImActivity;
import com.YiDian.RainBow.topic.SaveIntentMsgBean;
import com.YiDian.RainBow.user.PersonHomeActivity;
import com.YiDian.RainBow.utils.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ContactAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<FriendBean.ObjectBean> mContactNames; // 联系人名称字符串数组
    private List<String> mContactList; // 联系人名称List（转换成拼音）
    private List<Contact> resultList; // 最终结果（包含分组的字母）
    private List<String> characterList; // 字母List
    private FriendBean.ObjectBean objectBean;

    public ContactAdapter(Context context, List<FriendBean.ObjectBean> list) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mContactNames = list;
        handleContact();

    }

    public enum ITEM_TYPE {
        ITEM_TYPE_CHARACTER,
        ITEM_TYPE_CONTACT
    }

    private void handleContact() {
        mContactList = new ArrayList<>();
        Map<String, String> map = new HashMap<>();

        for (int i = 0; i < mContactNames.size(); i++) {
            String pinyin = Utils.getPingYin(mContactNames.get(i).getNickName());
            map.put(pinyin, mContactNames.get(i).getNickName());
            mContactList.add(pinyin);
        }
        Collections.sort(mContactList, new ContactComparator());

        resultList = new ArrayList<>();
        characterList = new ArrayList<>();

        for (int i = 0; i < mContactList.size(); i++) {
            String name = mContactList.get(i);
            String character = (name.charAt(0) + "").toUpperCase(Locale.ENGLISH);
            if (!characterList.contains(character)) {
                if (character.hashCode() >= "A".hashCode() && character.hashCode() <= "Z".hashCode()) { // 是字母
                    characterList.add(character);
                    resultList.add(new Contact(character, ITEM_TYPE.ITEM_TYPE_CHARACTER.ordinal()));
                } else {
                    if (!characterList.contains("#")) {
                        characterList.add("#");
                        resultList.add(new Contact("#", ITEM_TYPE.ITEM_TYPE_CHARACTER.ordinal()));
                    }
                }
            }

            resultList.add(new Contact(map.get(name), ITEM_TYPE.ITEM_TYPE_CONTACT.ordinal()));
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.ITEM_TYPE_CHARACTER.ordinal()) {
            return new CharacterHolder(mLayoutInflater.inflate(R.layout.item_character, parent, false));
        } else {
            return new ContactHolder(mLayoutInflater.inflate(R.layout.item_contact, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CharacterHolder) {
            ((CharacterHolder) holder).mTextView.setText(resultList.get(position).getmName());
        } else if (holder instanceof ContactHolder) {
            ((ContactHolder) holder).tv_name.setText(resultList.get(position).getmName());

            //循环遍历 与用户名一致对应设置信息
            for (int i =0;i<mContactNames.size();i++){
                objectBean = mContactNames.get(i);
                if(resultList.get(position).getmName().equals(objectBean.getNickName())){
                    //设置头像
                    Glide.with(mContext).load(objectBean.getHeadImg()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(((ContactHolder) holder).ivimage);
                    //设置签名
                    ((ContactHolder) holder).tv_autograph.setText(objectBean.getExplains());
                    //设置性别
                    String userRole = objectBean.getUserRole();
                    if(userRole.equals("保密")){
                        ((ContactHolder) holder).tv_xingbie.setVisibility(View.GONE);
                    }else{
                        ((ContactHolder) holder).tv_xingbie.setText(userRole);

                    }
                    // TODO: 2020/12/27 0027 跳转到聊天页
                    ((ContactHolder) holder).rlitem.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext, ImActivity.class);
                            mContext.startActivity(intent);
                        }
                    });
                    int finalI = i;
                    ((ContactHolder) holder).ivimage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            objectBean = mContactNames.get(finalI);

                            Intent intent = new Intent(mContext, PersonHomeActivity.class);
                            SaveIntentMsgBean saveIntentMsgBean = new SaveIntentMsgBean();
                            saveIntentMsgBean.setId(ContactAdapter.this.objectBean.getFansId());
                            //2标记传入姓名  1标记传入id
                            saveIntentMsgBean.setFlag(1);
                            intent.putExtra("msg",saveIntentMsgBean);
                            mContext.startActivity(intent);
                        }
                    });
                }
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return resultList.get(position).getmType();
    }

    @Override
    public int getItemCount() {
        return resultList == null ? 0 : resultList.size();
    }

    public class CharacterHolder extends RecyclerView.ViewHolder {
        TextView mTextView;

        CharacterHolder(View view) {
            super(view);

            mTextView = (TextView) view.findViewById(R.id.character);
        }
    }

    public class ContactHolder extends RecyclerView.ViewHolder {
        ImageView ivimage;
        TextView tv_xingbie;
        TextView tv_name;
        TextView tv_autograph;
        RelativeLayout rlitem;

        ContactHolder(View view) {
            super(view);

            ivimage = view.findViewById(R.id.iv_headimg);
            tv_xingbie = view.findViewById(R.id.tv_xingbie);
            tv_name = view.findViewById(R.id.tv_name);
            tv_autograph = view.findViewById(R.id.tv_autograph);
            rlitem = view.findViewById(R.id.rl_item);
        }
    }

    public int getScrollPosition(String character) {
        if (characterList.contains(character)) {
            for (int i = 0; i < resultList.size(); i++) {
                if (resultList.get(i).getmName().equals(character)) {
                    return i;
                }
            }
        }
        return -1; // -1不会滑动
    }
}
