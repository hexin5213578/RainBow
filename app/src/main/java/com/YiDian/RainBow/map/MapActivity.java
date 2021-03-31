package com.YiDian.RainBow.map;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.YiDian.RainBow.R;
import com.YiDian.RainBow.map.adapter.MapApapter;
import com.YiDian.RainBow.map.bean.SaveNearMessageBean;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.leaf.library.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MapActivity extends Activity implements View.OnClickListener, LocationSource, AMapLocationListener, GeocodeSearch.OnGeocodeSearchListener, PoiSearch.OnPoiSearchListener {

    AMap aMap;
    OnLocationChangedListener mListener;
    AMapLocationClient mlocationClient;
    AMapLocationClientOption mLocationOption;
    MyLocationStyle myLocationStyle;
    @BindView(R.id.mapView)
    MapView mapView;
    @BindView(R.id.rc_nearplace)
    RecyclerView rcNearplace;
    @BindView(R.id.tv_back)
    TextView tvBack;
    @BindView(R.id.tv_send)
    TextView tvSend;
    private GeocodeSearch geocoderSearch;
    private String str = "";
    private MapApapter mapApapter;
    private PoiItem poiItem;
    private SaveNearMessageBean saveNearMessageBean;

    int selectPostion = 0;

    private List<SaveNearMessageBean> locationMsglist;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_map);
        //获取定位权限
        Request();

        StatusBarUtil.setTransparentForWindow(MapActivity.this);
        StatusBarUtil.setDarkMode(MapActivity.this);

        ButterKnife.bind(this);

        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mapView.onCreate(savedInstanceState);
        //初始化地图控制器对象
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        // 设置定位监听
        aMap.setLocationSource(this);
        // 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.setMyLocationEnabled(true);
        // 设置定位的类型为定位模式，有定位、跟随或地图根据面向方向旋转几种
        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);

        //初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle = new MyLocationStyle();
        //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.interval(10000);

        //设置定位蓝点的Style
        aMap.setMyLocationStyle(myLocationStyle);
        //aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
        // 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        aMap.setMyLocationEnabled(true);

        mapApapter = new MapApapter();
        mapApapter.setContext(MapActivity.this);

        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("xxx","当前选中下标为"+selectPostion);
                EventBus.getDefault().post(locationMsglist.get(selectPostion));
                //获取到选中的条目信息
                finish();
            }
        });
        geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(this);
        aMap.setOnMyLocationChangeListener(new AMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                //獲取經度  緯度
                LatLonPoint latLonPoint = new LatLonPoint(location.getLatitude(), location.getLongitude());

                RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 0, GeocodeSearch.AMAP);
                geocoderSearch.getFromLocationAsyn(query);

                //地圖縮放
                aMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(location.getLatitude(), location.getLongitude()), 15, 0, 0)));
            }
        });
    }
    /**
     * 安卓10.0定位权限
     */
    public void Request() {
        if (Build.VERSION.SDK_INT >= 23) {
            int request = ContextCompat.checkSelfPermission(MapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
            //缺少权限，进行权限申请
            if (request != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(MapActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
                return;//
            } else {
            }
        } else {

        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        //mapView.onDestroy();
        if (null != mlocationClient) {
            mlocationClient.onDestroy();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mlocationClient == null) {
            //初始化定位
            mlocationClient = new AMapLocationClient(this);
            //初始化定位参数
            mLocationOption = new AMapLocationClientOption();
            //设置定位回调监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mLocationOption.setInterval(50000);
            mLocationOption.setOnceLocation(true);
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();//启动定位
        }
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        //定位回调
        if (mListener != null && aMapLocation != null) {
            if (aMapLocation != null
                    && aMapLocation.getErrorCode() == 0) {
                // 显示系统小蓝点
                mListener.onLocationChanged(aMapLocation);

                //获取当前定位结果来源，如网络定位结果，详见定位类型表
                aMapLocation.getLocationType();
                //获取纬度
                double latitude = aMapLocation.getLatitude();
                //获取经度
                double longitude = aMapLocation.getLongitude();

                aMapLocation.getAccuracy();//获取精度信息

                //查询关键字
                PoiSearch.Query query = new PoiSearch.Query("", "", "");
                query.setPageSize(30);
                PoiSearch search = new PoiSearch(this, query);
                search.setBound(new PoiSearch.SearchBound(new LatLonPoint(latitude, longitude), 1000));
                search.setOnPoiSearchListener(this);
                search.searchPOIAsyn();

            } else {
                String errText = "定位失败," + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
            }
        }
    }

    //定位当前位置
    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        RegeocodeAddress regeocodeAddress = regeocodeResult.getRegeocodeAddress();

        String building = regeocodeAddress.getFormatAddress();
        String substring = building.substring(9);
        str = substring;

    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }


    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        ArrayList<PoiItem> pois = poiResult.getPois();

        locationMsglist = new ArrayList<>();

        poiItem = pois.get(0);
        saveNearMessageBean = new SaveNearMessageBean();
        saveNearMessageBean.setIsselect(true);
        saveNearMessageBean.setTitle(poiItem.getTitle());
        saveNearMessageBean.setShengfen(poiItem.getProvinceName());
        saveNearMessageBean.setShiqu(poiItem.getCityName());
        saveNearMessageBean.setXian(poiItem.getAdName());
        saveNearMessageBean.setAddress(poiItem.getSnippet());
        saveNearMessageBean.setLocataion(poiItem.getLatLonPoint());
        locationMsglist.add(saveNearMessageBean);

        for (int j=1; j<=pois.size()-1;j++){
            poiItem = pois.get(j);

            saveNearMessageBean = new SaveNearMessageBean();
            saveNearMessageBean.setIsselect(false);
            saveNearMessageBean.setTitle(poiItem.getTitle());
            saveNearMessageBean.setShengfen(poiItem.getProvinceName());
            saveNearMessageBean.setShiqu(poiItem.getCityName());
            saveNearMessageBean.setXian(poiItem.getAdName());
            saveNearMessageBean.setAddress(poiItem.getSnippet());
            saveNearMessageBean.setLocataion(poiItem.getLatLonPoint());
            locationMsglist.add(saveNearMessageBean);
        }


        mapApapter.setData(locationMsglist);

        LinearLayoutManager manager = new LinearLayoutManager(MapActivity.this, RecyclerView.VERTICAL, false);
        rcNearplace.setLayoutManager(manager);
        rcNearplace.setAdapter(mapApapter);
        mapApapter.setOnItemListener(new MapApapter.OnItemListener() {
            @Override
            public void onClick(MapApapter.ViewHolder holder, int position) {
                selectPostion = position;
                mapApapter.setDefSelect(position);
                if(locationMsglist.get(position).isIsselect()) {
                    //选中状态点击之后改为未选中
                    locationMsglist.get(position).setIsselect(true);
                    mapApapter.notifyDataSetChanged();
                }else {
                    //未选中状态点击之后给为选中
                    locationMsglist.get(position).setIsselect(true);
                    mapApapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

}