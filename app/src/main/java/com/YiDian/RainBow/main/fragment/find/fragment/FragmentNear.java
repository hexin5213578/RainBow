package com.YiDian.RainBow.main.fragment.find.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseFragment;
import com.YiDian.RainBow.base.BasePresenter;
import com.YiDian.RainBow.base.Common;
import com.YiDian.RainBow.custom.loading.CustomDialog;
import com.YiDian.RainBow.main.fragment.find.adapter.NearPersonAdapter;
import com.YiDian.RainBow.main.fragment.find.bean.NearPersonBean;
import com.YiDian.RainBow.utils.NetUtils;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

// εη° - ιθΏ
public class FragmentNear extends Fragment implements AMapLocationListener {
    @BindView(R.id.rc_near_person)
    RecyclerView rcNearPerson;
    @BindView(R.id.sv)
    SpringView sv;
    @BindView(R.id.rl_nodata)
    RelativeLayout rlNodata;
    private int userid;
    //ε£°ζmlocationClientε―Ήθ±‘
    AMapLocationClient mlocationClient;
    //ε£°ζmLocationOptionε―Ήθ±‘
    AMapLocationClientOption mLocationOption = null;
    private double latitude;
    private double longitude;
    int page = 1;
    int size = 15;
    private List<NearPersonBean.ObjectBean.ListBean> alllist;
    //ε θ½½ηθ―εΎ
    private View mContentView;
    //δΈδΈͺζ ΈεΏει
    private boolean isUserHint;//η¨ζ·ζ―ε¦ε―θ§
    private boolean isViewInit;//viewθ§εΎζ―ε¦ε θ½½θΏ
    private boolean isDataLoad;//θζΆζδ½ζ―ε¦ε θ½½θΏ
    private Unbinder bind;
    private CustomDialog dialog1;
    private CustomDialog dialog2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mContentView == null) {
            mContentView = createView(inflater, container);
        }

        bind = ButterKnife.bind(this, mContentView);

        return mContentView;
    }

    public View createView(LayoutInflater inflater, ViewGroup container) {
        View view = null;
        if (getResId() != 0) {
            view = inflater.inflate(getResId(), container, false);
        } else {
            throw new IllegalStateException("this layout id is null");
        }
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i("baseF","onViewCreated");

        if (!isViewInit) {
            getid(mContentView);
        }
        isViewInit = true;
        loadData();
    }
    protected void getid(View view) {

    }

    protected int getResId() {
        return R.layout.home_find_fragment_near;
    }

    protected void getData() {
        userid = Integer.valueOf(Common.getUserId());
        alllist = new ArrayList<>();

        dialog1 = new CustomDialog(getContext(),"ζ­£ε¨θ·εδ½η½?δΏ‘ζ―...");
        dialog2 = new CustomDialog(getContext(),"ζ­£ε¨ε θ½½...");

        //θΏε₯ε?δ½ ε?δ½ζεθ·εη¬¬δΈζ¬‘ζ°ζ?
        doLocation();
        sv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                int page = 1;
                alllist.clear();
                getNearPerson(page,size);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        sv.onFinishFreshAndLoad();
                    }
                },1000);
            }

            @Override
            public void onLoadmore() {
                page++;
                getNearPerson(page,size);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        sv.onFinishFreshAndLoad();
                    }
                },1000);
            }
        });


    }
    //θΏδΈͺζΉζ³δΌεηΊ§εΎι«
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {//η¨ζ·ε―θ§ε°±δΈΊtrue δΈε―θ§ε°±ζ―false
        super.setUserVisibleHint(isVisibleToUser);
        this.isUserHint = isVisibleToUser;
        loadData();
    }

    void loadData() {
        //θΏθ‘δΌεζε θ½½ηζΉζ³
        if (isUserHint && isViewInit) {
            getData();
            isDataLoad = true;
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
            bind.unbind();
    }

    public void doLocation() {
        mlocationClient = new AMapLocationClient(getContext());
        //εε§εε?δ½εζ°
        mLocationOption = new AMapLocationClientOption();
        //θ?Ύη½?ε?δ½ηε¬
        mlocationClient.setLocationListener(this);
        //θ?Ύη½?ε?δ½ζ¨‘εΌδΈΊι«η²ΎεΊ¦ζ¨‘εΌοΌBattery_SavingδΈΊδ½εθζ¨‘εΌοΌDevice_Sensorsζ―δ»θ?Ύε€ζ¨‘εΌ
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //θ?Ύη½?ε?δ½ι΄ι,εδ½ζ―«η§,ι»θ?€δΈΊ2000ms
        mLocationOption.setOnceLocation(true);
        //θ?Ύη½?ε?δ½εζ°
        mlocationClient.setLocationOption(mLocationOption);
        // ζ­€ζΉζ³δΈΊζ―ιεΊε?ζΆι΄δΌεθ΅·δΈζ¬‘ε?δ½θ―·ζ±οΌδΈΊδΊεε°η΅ιζΆθζη½η»ζ΅ιζΆθοΌ
        // ζ³¨ζθ?Ύη½?ειηε?δ½ζΆι΄ηι΄ιοΌζε°ι΄ιζ―ζδΈΊ1000msοΌοΌεΉΆδΈε¨ειζΆι΄θ°η¨stopLocation()ζΉζ³ζ₯εζΆε?δ½θ―·ζ±
        // ε¨ε?δ½η»ζεοΌε¨ειηηε½ε¨ζθ°η¨onDestroy()ζΉζ³
        // ε¨εζ¬‘ε?δ½ζε΅δΈοΌε?δ½ζ θ?ΊζεδΈε¦οΌι½ζ ιθ°η¨stopLocation()ζΉζ³η§»ι€θ―·ζ±οΌε?δ½sdkει¨δΌη§»ι€
        //ε―ε¨ε?δ½
        mlocationClient.startLocation();
        dialog1.show();
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //ε?δ½ζεεθ°δΏ‘ζ―οΌθ?Ύη½?ηΈε³ζΆζ―
                aMapLocation.getLocationType();//θ·εε½εε?δ½η»ζζ₯ζΊοΌε¦η½η»ε?δ½η»ζοΌθ―¦θ§ε?δ½η±»εθ‘¨
                //θ·εηΊ¬εΊ¦
                latitude = aMapLocation.getLatitude();
                //θ·εη»εΊ¦
                longitude = aMapLocation.getLongitude();
                aMapLocation.getAccuracy();//θ·εη²ΎεΊ¦δΏ‘ζ―
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(aMapLocation.getTime());
                df.format(date);//ε?δ½ζΆι΄

                Log.d("xxx", "ε?δ½ζε");
                alllist.clear();
                dialog1.dismiss();
                page = 1;
                getNearPerson(page,size);
            } else {
                dialog1.dismiss();
                Toast.makeText(getContext(), "θ·εδ½η½?δΏ‘ζ―ε€±θ΄₯", Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (aMapLocation.getErrorCode()==12){
                            Request();
                        }
                    }
                },1000);

                //ζΎη€Ίιθ――δΏ‘ζ―ErrCodeζ―ιθ――η οΌerrInfoζ―ιθ――δΏ‘ζ―οΌθ―¦θ§ιθ――η θ‘¨γ
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }
    }
    //ε?ε10.0ε?δ½ζι
    public void Request() {
        if (Build.VERSION.SDK_INT >= 23) {
            int request = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION);
            if (request != PackageManager.PERMISSION_GRANTED)//ηΌΊε°ζιοΌθΏθ‘ζιη³θ―·
            {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
                return;//
            } else {

            }
        } else {

        }
    }
    //εζ° requestCodeζ―ζδ»¬ε¨η³θ―·ζιηζΆεδ½Ώη¨ηε―δΈηη³θ―·η 
    //String[] permissionεζ―ζιεθ‘¨οΌδΈθ¬η¨δΈε°
    //int[] grantResults ζ―η¨ζ·ηζδ½εεΊοΌεε«θΏζιζ―ε€θ―·ζ±ζε
    //η±δΊε¨ζιη³θ―·ηζΆεοΌζδ»¬ε°±η³θ―·δΊδΈδΈͺζιοΌζδ»₯ζ­€ε€ηζ°η»ηιΏεΊ¦ι½ζ―1
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(getContext(), "ζιη³θ―·ε€±θ΄₯οΌη¨ζ·ζη»ζι", Toast.LENGTH_SHORT).show();
            }
        }
    }
    //θ·εζ°ζ?
    public void getNearPerson(int page,int size){
        dialog2.show();
        NetUtils.getInstance().getApis()
                .doGetNearPerson(userid,longitude,latitude,page,size)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NearPersonBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(NearPersonBean nearPersonBean) {
                        dialog2.dismiss();

                        List<NearPersonBean.ObjectBean.ListBean> list = nearPersonBean.getObject().getList();
                        if(list.size()>0 && list!=null){

                            sv.setVisibility(View.VISIBLE);
                            rlNodata.setVisibility(View.GONE);

                            sv.setHeader(new AliHeader(getContext()));

                            alllist.addAll(list);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                            rcNearPerson.setLayoutManager(linearLayoutManager);
                            NearPersonAdapter nearPersonAdapter = new NearPersonAdapter(getContext(), alllist);
                            rcNearPerson.setAdapter(nearPersonAdapter);
                        }else{
                            if(alllist.size()>0 && alllist!=null){
                                Toast.makeText(getContext(), "ζ²‘ζζ΄ε€εε?ΉδΊ", Toast.LENGTH_SHORT).show();
                            }else{
                                sv.setVisibility(View.GONE);
                                rlNodata.setVisibility(View.VISIBLE);
                            }
                        }
                        if (list.size()>8){
                            sv.setFooter(new AliFooter(getContext()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        dialog2.dismiss();
                        Toast.makeText(getContext(), "ζ°ζ?θ―·ζ±ε€±θ΄₯", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
