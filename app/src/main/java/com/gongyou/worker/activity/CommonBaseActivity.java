package com.gongyou.worker.activity;

import android.annotation.TargetApi;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.gongyou.worker.SampleApplicationLike;
import com.gongyou.worker.utils.DensityUtils;


/**
 * 作    者: ZhangLC
 * 创建时间: 2017/5/31.
 */

public class CommonBaseActivity extends CheckPermissionsActivity {

    protected String TAG = this.getClass().getSimpleName();
    private ProgressBar mProgressBar;

    //    private int mStatusHeight;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //强制所有Activity竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //解决弹出输入框把底部标题栏顶上去的问题
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
//        if ("".equals(SampleApplicationLike.systemModel))
//            SampleApplicationLike.systemModel = SystemUtil.getSystemModel();
//        LogUtil.d("SystemUtil.getSystemModel()-----------------------------------------" + SampleApplicationLike.systemModel);
//        if (SampleApplicationLike.systemModel.startsWith("KIW")) {
//            setStatusBar();
//        } else {
//        }
//        transparencyBar();
        mProgressBar = createProgressBar();

    }

//    /**
//     * 动态的设置状态栏  实现沉浸式状态栏   半透明  华为手机有底部导航栏不能设为全透明
//     */
//    private void setStatusBar() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                // 部分机型的statusbar会有半透明的黑色背景
//                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//                getWindow().setStatusBarColor(Color.TRANSPARENT);// SDK21
//            }
//            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
//            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
////            applySelectedColor();
////            LinearLayout linear_bar = (LinearLayout) findViewById(R.id.status_bar);
////            LinearLayout linear_bar = getMyStatusBar();
////            setStatusBarHight(linear_bar);
//            //获取到状态栏的高度
//            if (SampleApplicationLike.mStatusHeight == 0)
//                SampleApplicationLike.mStatusHeight = getStatusBarHeight();
//        }
//    }

    /**
     * 修改状态栏为全透明
     */
    @TargetApi(19)
    public void transparencyBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
            );
            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            );
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
//            window.setNavigationBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        if (SampleApplicationLike.mStatusHeight == 0)
            SampleApplicationLike.mStatusHeight = getStatusBarHeight();
//        ((ViewGroup)activity.findViewById(android.R.id.content)).getChildAt(0).setFitsSystemWindows(true);//设置跟布局fitsystemwindow=true
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public void setStatusBarHight(LinearLayout linear_bar) {
        linear_bar.setVisibility(View.INVISIBLE);
        linear_bar.setBackgroundColor(Color.TRANSPARENT);
        //动态的设置隐藏布局的高度  ☆☆☆☆☆☆外层布局必须是 LinearLayout   ☆☆☆☆☆☆☆☆☆
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) linear_bar.getLayoutParams();
        params.height = SampleApplicationLike.mStatusHeight;
        linear_bar.setLayoutParams(params);
    }


//    /**
//     * 通过反射的方式获取状态栏高度
//     * @return
//     */
//    private int getStatusBarHeight() {
//        try {
//            Class<?> c = Class.forName("com.android.internal.R$dimen");
//            Object obj = c.newInstance();
//            Field field = c.getField("status_bar_height");
//            int x = Integer.parseInt(field.get(obj).toString());
//            return getResources().getDimensionPixelSize(x);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return 0;
//    }

    private ProgressBar createProgressBar() {
        // activity根部的ViewGroup，其实是一个FrameLayout
        FrameLayout rootContainer = (FrameLayout) findViewById(android.R.id.content);
        // 给progressbar准备一个FrameLayout的LayoutParams
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置对其方式为：屏幕居中对其
//        lp.gravity = Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL;
        lp.gravity = Gravity.CENTER_HORIZONTAL;
        lp.topMargin = DensityUtils.dp2px(this, 150);
        ProgressBar progressBar = new ProgressBar(this);
        progressBar.setVisibility(View.GONE);
        progressBar.setLayoutParams(lp);
        // 将菊花添加到FrameLayout中
        rootContainer.addView(progressBar);
        return progressBar;
    }

    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    protected void onResume() {
        super.onResume();
//        MobclickAgent.onResume(this);
    }
    protected void onPause() {
        super.onPause();
//        MobclickAgent.onPause(this);
    }
}
