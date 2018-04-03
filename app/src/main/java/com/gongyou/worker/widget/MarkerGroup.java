package com.gongyou.worker.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.Gallery;
import android.widget.ImageView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.gongyou.worker.R;
import com.gongyou.worker.utils.DensityUtils;
import com.gongyou.worker.utils.LogUtil;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by zlc on 2017/3/2.
 * <p>
 * 自定义控件,类似RadioGroup
 */

public class MarkerGroup implements AMap.OnMarkerClickListener {


    private Context context;
    private AMap mAMap;
    private int pisition = -1;
    private static Map<Integer, MarkerButton> mMarkers = new HashMap<>();

    private OnMarkerCheckedLisenter mOnMarkerCheckedLisenter;

    public int count = 0;

    public void setChecked(int position) {
//        LogUtil.e("mMarkers.size()------------" + mMarkers.size());
        MarkerButton markerButton = mMarkers.get(position);
        if (markerButton != null) {
            LatLng latLng = markerButton.mMarker.getPosition();
//            LogUtil.e("mMarker.id------------" + markerButton.id);
            //本就是ViewPager界面切换操作去改变Marker的显示,因此不让Marker的变化回调ViewPager
            onMarkerChecked(latLng, false);
        }else {
//            LogUtil.e("---------之前有一个Marker加载失败--------position------"+position);
        }
    }

    public interface OnMarkerCheckedLisenter {
        void onMarkerChecked(int pisition);
    }

    public void setOnMarkerCheckedLisenter(OnMarkerCheckedLisenter lisenter) {
        mOnMarkerCheckedLisenter = lisenter;
    }

    public MarkerGroup(AMap aMap, Context context) {
        mAMap = aMap;
        this.context = context;
        mAMap.setOnMarkerClickListener(this);

    }

    /**
     * 生成一个带点击切换功能的Marker
     *
     * @param latLng Marker坐标
     * @param url    Marker图片的网络路径
     */
    public void addMarkerButton(final LatLng latLng, final String url, int pisition) {
//        final ImageView viewm = getView();
        final ImageView viewm = new ImageView(context);
//        LogUtil.d("mViews.size()", mViews.size() + "--------addMarkerButton-------");
        viewm.setScaleType(ImageView.ScaleType.FIT_START);
        viewm.setBackgroundResource(R.drawable.mapimage);
//        final int id = pisition++;
        viewm.setTag(pisition);
        Glide.with(context).load(url)
                .asBitmap()
                .transform(new GlideCircleTransform(context))
                .into(new SimpleTarget<Bitmap>() {

                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        viewm.setImageBitmap(resource);
                        MarkerOptions options = new MarkerOptions();
                        options.position(latLng);
                        viewm.setLayoutParams(new Gallery.LayoutParams(DensityUtils.dp2px(context, 40),
                                DensityUtils.dp2px(context, 40)));
                        int px = DensityUtils.dp2px(context, 2.6f);
//                        viewm.setPadding(px, DensityUtils.dp2px(context, 4), px, 0);
                        viewm.setPadding(px * 2, px, px * 2, 0);
                        options.icon(BitmapDescriptorFactory.fromView(viewm));
                        Log.d("onResourceReady", "----------");
                        Marker marker = mAMap.addMarker(options);
                        int finalId = (int) viewm.getTag();
                        MarkerButton markerButton = new MarkerButton(marker, latLng, url, finalId);
                        mMarkers.put(finalId, markerButton);
                        count--;
//                        LogUtil.d("onResourceReady-----------" + count);

                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        super.onLoadFailed(e, errorDrawable);
//                        LogUtil.d("onLoadFailed------------" + count);
                        int finalId = (int) viewm.getTag();
                        mMarkers.put(finalId, null);  //保证集合长度

                        count--;
                    }

                    @Override
                    public void onStart() {

                        super.onStart();
                        LogUtil.d("onStart----------------" + count);
                        count++;

                    }
                });
    }


//    public void addMarkerButton(final LatLng latLng, final String url, final int position) {
//        final ImageView viewm = new ImageView(context);
//        viewm.setScaleType(ImageView.ScaleType.FIT_START);
//        viewm.setBackgroundResource(R.drawable.mapimage);
//        viewm.setTag(position);
//        Glide.with(context).load(url)
//                .asBitmap()
//                .transform(new GlideCircleTransform(context))
//                .into(new SimpleTarget<Bitmap>() {
//
//                    @Override
//                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                        viewm.setImageBitmap(resource);
//                        MarkerOptions options = new MarkerOptions();
//                        options.position(latLng);
//                        viewm.setLayoutParams(new Gallery.LayoutParams(DensityUtils.dp2px(context, 40),
//                                DensityUtils.dp2px(context, 40)));
//                        int px = DensityUtils.dp2px(context, 2.6f);
//                        viewm.setPadding(px * 2, px, px * 2, 0);
//                        options.icon(BitmapDescriptorFactory.fromView(viewm));
//                        Log.d("onResourceReady", "----------");
//                        Marker marker = mAMap.addMarker(options);
//                        int finalId = (int) viewm.getTag();
//                        MarkerButton markerButton = new MarkerButton(marker, latLng, url, finalId);
//                        mMarkers.put(position, markerButton);
//                        count--;
//                        LogUtil.d("onResourceReady-----------" + count);
//
//                    }
//
//                    @Override
//                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
//                        super.onLoadFailed(e, errorDrawable);
//                        LogUtil.d("onLoadFailed------------" + count);
//
//                        count--;
//                    }
//
//                    @Override
//                    public void onStart() {
//
//                        super.onStart();
//                        LogUtil.d("onStart----------------" + count);
//                        count++;
//
//                    }
//                });
//    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        LatLng latLng = marker.getPosition();
        onMarkerChecked(latLng, true);
        return true;
    }

    /**
     * 图片放大缩小的关键方法,指定一个title即可
     */
    private void
    onMarkerChecked(LatLng latLng, boolean b) {
        if (b) {
            //回调部分
            Set<Integer> integers = mMarkers.keySet();
//            for (MarkerButton button : mMarkers) {
//                if (button.mLatLng.equals(latLng) && mOnMarkerCheckedLisenter != null) {
//                    LogUtil.i("有相同的坐标----------------------------!");
//                    mOnMarkerCheckedLisenter.onMarkerChecked(button.id);
//                    break;
//                }
//                LogUtil.v(button.mLatLng.toString());
//            }
            for (int i : integers) {
                if (mMarkers.get(i).mLatLng.equals(latLng) && mOnMarkerCheckedLisenter != null) {
//                    LogUtil.i("有相同的坐标----------------------------!");
                    mOnMarkerCheckedLisenter.onMarkerChecked(mMarkers.get(i).id);
                    break;
                }
            }
//            LogUtil.d(latLng.toString() + "-----------------------------");
        }
        count++;
        changeMarkerCheck(latLng);
        count--;
//        LogUtil.d("count", count + "----------onMarkerChecked-----------" + count);
    }

    private void changeMarkerCheck(LatLng latLng) {
        Set<Integer> integers = mMarkers.keySet();
        for (int i : integers) {
            MarkerButton button = mMarkers.get(i);
            if (button.checked) {
                String url = button.mUrl;
                click(button, url, false);
                button.checked = false;
//                LogUtil.d("--缩小marker----------------------------" + button.id);
                break;
            }
        }
        //两次循环避免小图标压在大图标上的情况
        for (int i : integers) {
            MarkerButton button = mMarkers.get(i);
            if (button.mMarker.getPosition().equals(latLng)) {
                if (!button.checked) {
                    String url = button.mUrl;
                    click(button, url, true);
                    button.checked = true;
//                    LogUtil.d("--放大marker----------------------------" + button.id);
                    break;
                }
            }
        }
    }


    private void click(final MarkerButton markerBean, String url, final boolean flag) {
//        final ImageView viewm = getView(markerBean.s_id);
        final ImageView viewm = new ImageView(context);
        viewm.setScaleType(ImageView.ScaleType.FIT_START);
        Glide.with(context).load(url)
                .asBitmap()
                .transform(new GlideCircleTransform(context))
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                        MarkerOptions options = markerBean.mMarker.getOptions();
                        viewm.setImageBitmap(resource);
                        markerBean.mMarker.remove();
                        viewm.setImageBitmap(resource);
                        MarkerOptions options = new MarkerOptions();
                        options.position(markerBean.mLatLng);
                        if (flag) {
                            viewm.setLayoutParams(new Gallery.LayoutParams(DensityUtils.dp2px(context, 60),
                                    DensityUtils.dp2px(context, 60)));
                            int px = DensityUtils.dp2px(context, 5);
                            viewm.setPadding(px * 2, DensityUtils.dp2px(context, 6), px * 2, 0);
                            viewm.setBackgroundResource(R.drawable.bjgmapimage);
                        } else {
                            viewm.setLayoutParams(new Gallery.LayoutParams(DensityUtils.dp2px(context, 40),
                                    DensityUtils.dp2px(context, 40)));
                            int px = DensityUtils.dp2px(context, 2.6f);
                            viewm.setPadding(px * 2, px, px * 2, 0);
                            viewm.setBackgroundResource(R.drawable.mapimage);
                        }
                        options.icon(BitmapDescriptorFactory.fromView(viewm));
                        Marker marker = mAMap.addMarker(options);
                        markerBean.setMarker(marker);
                    }
                });
    }

    /**
     * 移除所以Marker
     */
    public void removeMarkers() {
        Set<Integer> integers = mMarkers.keySet();
        for (int i : integers) {
            mMarkers.get(i).mMarker.remove();
        }
        mMarkers.clear();
        pisition = 0;
        counts = 0;
    }
    int counts = 0;
}
