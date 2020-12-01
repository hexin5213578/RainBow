package com.YiDian.RainBow.main.fragment.home.activity.fragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.main.fragment.home.activity.NewDynamicImage;
import com.bumptech.glide.Glide;


import java.util.ArrayList;
import java.util.List;

public class ImageDetailFragment extends Fragment {

    //Fragment中的onAttach方法
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        List<String> img = ((NewDynamicImage) activity).getUrl();
        int index = ((NewDynamicImage) activity).getIndex();
    }


}
