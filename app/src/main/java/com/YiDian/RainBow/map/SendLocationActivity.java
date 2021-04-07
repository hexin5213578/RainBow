package com.YiDian.RainBow.map;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.YiDian.RainBow.R;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMapException;
import com.amap.api.maps2d.AMapUtils;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.NaviPara;
import com.leaf.library.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SendLocationActivity extends AppCompatActivity {
    @BindView(R.id.mapView)
    MapView mapView;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.ll_daohang)
    LinearLayout llDaohang;
    private AMap aMap;
    private Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locationmsgmap);

        ButterKnife.bind(this);

        StatusBarUtil.setTransparentForWindow(SendLocationActivity.this);
        StatusBarUtil.setDarkMode(SendLocationActivity.this);

        mapView.onCreate(savedInstanceState);

        if (aMap == null) {
            aMap = mapView.getMap();
        }
        // 显示实时交通状况
        aMap.setTrafficEnabled(true);
        //地图模式可选类型：MAP_TYPE_NORMAL,MAP_TYPE_SATELLITE,MAP_TYPE_NIGHT
        // 卫星地图模式
        aMap.setMapType(AMap.MAP_TYPE_NORMAL);
        getData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mapView!=null) {
            mapView.onDestroy();

        }
    }

    protected void getData() {
        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        handler = new Handler();

        //获取需要查看的位置信息
        Intent intent = getIntent();
        String address = intent.getStringExtra("address");
        double lat = intent.getDoubleExtra("lat", 0);
        double lon = intent.getDoubleExtra("lon", 0);

        //缩放隐藏
        aMap.getUiSettings().setZoomControlsEnabled(false);
        LatLng latLng = new LatLng(lat, lon);
        //标点
        final Marker marker = aMap.addMarker(new MarkerOptions().position(latLng));
        //视角调整到点的位置
        aMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(lat, lon), 16, 0, 0)));

        //地址赋值
        String[] split = address.split(",");
        tvTitle.setText(split[1]+"");
        tvAddress.setText(split[0]+"");

        llDaohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 构造导航参数
                NaviPara naviPara = new NaviPara();
                // 设置终点位置
                naviPara.setTargetPoint(latLng);
                // 设置导航策略，这里是避免拥堵
                naviPara.setNaviStyle(NaviPara.DRIVING_AVOID_CONGESTION);

                // 调起高德地图导航
                try {
                    AMapUtils.openAMapNavi(naviPara, SendLocationActivity.this);
                } catch (AMapException e) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // 如果没安装会进入异常，调起下载页面
                            AMapUtils.getLatestAMapApp(SendLocationActivity.this);
                        }
                    },2000);
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mapView!=null){
            mapView.onResume();
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        if (mapView!=null) {
            mapView.onPause();
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mapView.onSaveInstanceState(outState);
    }
}
