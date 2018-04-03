package com.gongyou.worker.widget;

import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.gongyou.worker.utils.LogUtil;


/**
 * Created by zlc on 2017/2/27.
 * 增加Marker功能
 */

public class MarkerButton {

    public int id;
    public Marker mMarker;
    public LatLng mLatLng;
    public String mUrl;

    public MarkerButton(Marker marker, LatLng latLng, String url, int id) {
        this.mMarker = marker;
        this.mLatLng = latLng;
        this.mUrl = url;
        this.id = id;
        LogUtil.w("MarkerGroup"+"--------id="+id);
    }
    public boolean checked = false;

    public void setMarker(Marker marker) {
        mMarker = marker;
    }
}
