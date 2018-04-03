package com.gongyou.worker;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.gongyou.worker.activity.MVPBaseActivity;
import com.gongyou.worker.activity.first.LoginActivity;
import com.gongyou.worker.activity.first.PoiKeywordSearchActivity;
import com.gongyou.worker.mvp.bean.second.FindInfo;
import com.gongyou.worker.mvp.bean.second.MsgViewInfo;
import com.gongyou.worker.mvp.presenter.second.MainActivityPresenter;
import com.gongyou.worker.mvp.view.second.MainActivityView;
import com.gongyou.worker.network.Api;
import com.gongyou.worker.network.RequestCode;
import com.gongyou.worker.utils.LogUtil;
import com.gongyou.worker.widget.MarkerGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends MVPBaseActivity<MainActivityView,MainActivityPresenter> implements MainActivityView {
    @BindView(R.id.login)
    Button mLoginBtn;
    @BindView(R.id.main_text)
    TextView mText;
    @BindView(R.id.location)
    TextView mLocationBtn;
    @BindView(R.id.location_text)
    TextView mLocationTxt;
    @BindView(R.id.map_image)
    TextureMapView mMapView;
    private UiSettings mUiSettings;//定义一个UiSettings对象
    private AMap mAMap;
    private MyLocationStyle myLocationStyle;
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    private LocationSource.OnLocationChangedListener mListener;
    private MarkerGroup mGroup;
    private boolean flag = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMapView.onCreate(savedInstanceState);
        Toast.makeText(this, "补丁三", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void initView() {
        super.initView();
        setNeedStatusBar(false);
    }

    @Override
    protected int initLayoutId() {
        return R.layout.activity_main;
    }
    protected void addLisenter() {
        mText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, ListFilterActivity.class));
                startActivity(new Intent(MainActivity.this, PoiKeywordSearchActivity.class));
            }
        });
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        mLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, CertificationActivity.class));
            }
        });
    }

    @Override
    protected void initData() {
        myLocationStyle = new MyLocationStyle();
        myLocationStyle.strokeColor(Color.TRANSPARENT);
        myLocationStyle.radiusFillColor(Color.TRANSPARENT);
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.location_map_gps_locked));
        if (mAMap == null) {
            mAMap = mMapView.getMap();
        }
        mAMap.setLocationSource(new LocationSource() {
            @Override
            public void activate(OnLocationChangedListener onLocationChangedListener) {
                mListener = onLocationChangedListener;
                //初始化client
                locationClient = new AMapLocationClient(SampleApplicationLike.getContext());
                locationOption = getDefaultOption();
                locationClient.setLocationOption(locationOption);
                locationClient.setLocationListener(locationListener);
                //开启定位。
                locationClient.startLocation();
            }

            @Override
            public void deactivate() {
                locationListener = null;
                if (locationClient != null) {
                    locationClient.stopLocation();
                    locationClient.onDestroy();
                }
                locationClient = null;
            }
        });
        mAMap.setMyLocationStyle(myLocationStyle);
        mAMap.setMyLocationEnabled(true);
        mAMap.setMyLocationType(AMap.LOCATION_TYPE_MAP_FOLLOW);
        mUiSettings = mAMap.getUiSettings();//实例化UiSettings类对象
        mUiSettings.setZoomControlsEnabled(false);
        //设置缩放级别为15
        mAMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        mAMap.setMyLocationEnabled(true);// 可触发定位并显示当前位置
        mGroup = new MarkerGroup(mAMap, SampleApplicationLike.getContext());
//        mPresenter.refreshMsgView();
    }
    /**
     * 定位监听
     */
    AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation location) {
            Log.e(TAG, "onLocationChanged: " + location.getErrorCode() + "  location:" + location );

            if (mListener != null && location != null){
                if (location.getErrorCode() == 0){
                    if (!flag && location.getLatitude() != 0){
                        //地图移动到定位所在地。
                        mListener.onLocationChanged(location);
                        SampleApplicationLike.mLatitude = location.getLatitude();
                        SampleApplicationLike.mLongitude = location.getLongitude();
                        //                    mGroup.removeMarkers();
                        Log.e(TAG, "onLocationChanged: " + "即将再次获取数据信息" );
                        mPresenter.getUserData("", SampleApplicationLike.mLongitude, SampleApplicationLike.mLatitude);
                        flag = true;
                    }
                }else {
                    Toast.makeText(MainActivity.this, "定位失败", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };
    private AMapLocationClientOption getDefaultOption(){
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(10000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
        return mOption;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyLocation();
        mMapView.onDestroy();
    }

    private void destroyLocation(){
        if (null != locationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;
        }
    }

    @Override
    protected MainActivityPresenter initPresenter() {
        return new MainActivityPresenter();
    }

    @Override
    public void onError(String msg, RequestCode requestCode) {

    }

    @Override
    public void loadInfoSuccess(List<FindInfo> data, RequestCode requestCode) {
        setMarkerAndVPData(data);
    }

    public void setMarkerAndVPData(List<FindInfo> list) {
        addmarker(list);
        mGroup.setOnMarkerCheckedLisenter(new MarkerGroup.OnMarkerCheckedLisenter() {
            @Override
            public void onMarkerChecked(int position) {
//                mVpFind.setVisibility(View.VISIBLE);
//                mVpFind.setCurrentItem(position, false);
                LogUtil.d("position", position + "------------onMarkerChecked--------------");
                if (mGroup.count == 0) {
                } else {
                    SampleApplicationLike.showToast(mGroup.count + "---数据还没有加载完成 -----");
                }
            }
        });
        LogUtil.d("setMarkerAndVPData", "----------setMarkerAndVPData----------");
        for (LatLng ll : mLatLngs) {
//            LogUtil.d(ll.toString());
        }
//        adapter.notifyDataSetChanged();
    }
    private List<LatLng> mLatLngs = new ArrayList<>();
    /**
     * 根据请求会的业务bean生成Marker图标
     */
    private void addmarker(List<FindInfo> list) {
        for (int i = 0; i < list.size(); i++) {
            FindInfo findInfo = list.get(i);
            double map_lat = Double.parseDouble(findInfo.map_lat);
            double map_lon = Double.parseDouble(findInfo.map_lng);
            LatLng latLng = new LatLng(map_lat, map_lon);
            for (; ; ) {
                if (!mLatLngs.contains(latLng)) {
                    break;
                }
//                LogUtil.d("addmarker", "---------处理经纬度使之唯一---------");
                map_lat += 0.0001;
                latLng = new LatLng(map_lat, map_lon);
            }
//            LogUtil.v("addmarker", "---------latLng---------------x--" + latLng.toString());
            mLatLngs.add(latLng);
            mGroup.addMarkerButton(latLng, Api.IMG_BASE_URL + findInfo.head_image,i);
        }
    }

    @Override
    public void refreshMsgView(MsgViewInfo data, RequestCode requestCode) {

    }
}
