package com.YiDian.RainBow.setup;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.base.BaseAvtivity;
import com.YiDian.RainBow.base.BasePresenter;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.Circle;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SetupActivity extends AppCompatActivity {
    @BindView(R.id.map)
    MapView map;
    //初始化地图控制器对象
    AMap aMap;
    private Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        ButterKnife.bind(this);

        map.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = map.getMap();
        }
        aMap.setTrafficEnabled(true);// 显示实时交通状况
        //地图模式可选类型：MAP_TYPE_NORMAL,MAP_TYPE_SATELLITE,MAP_TYPE_NIGHT
        aMap.setMapType(AMap.MAP_TYPE_NORMAL);// 卫星地图模式

        //默认位置
        LatLng lat_default = new LatLng(29.56471,106.55073);//构造一个位置
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lat_default,9));

        //地圖縮放

        //画圆
        LatLng latLng = new LatLng(39.984059,116.307771);
        Circle circle = aMap.addCircle(new CircleOptions().
                center(latLng).
                radius(2000).
                fillColor(Color.argb(40,11,164,233)).
                strokeColor(Color.argb(0,11,164,233)).
                strokeWidth(15));


        //画点
        LatLng latLng1 = new LatLng(39.906901,116.397972);

        MarkerOptions markerOptions = new MarkerOptions()
                .title("何梦洋在北京奥术大师多阿萨德").snippet("DefaultMarker")
                .position(latLng1)
                .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(),R.mipmap.fire)));
        marker = aMap.addMarker(markerOptions);

        LatLng latLng2 = new LatLng(39.806901,116.297972);
        marker = aMap.addMarker(new MarkerOptions().position(latLng2).setFlat(false).title("1231231").snippet("DefaultMarker"));
    }
}
