package com.gongyou.worker.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.bumptech.glide.Glide;
import com.gongyou.worker.R;
import com.gongyou.worker.utils.state;
import com.gongyou.worker.widget.LoadingDialog;
import com.gongyou.worker.widget.LoadingListener;
import com.gongyou.worker.widget.LoadingManager;

import butterknife.ButterKnife;


/**
 * Author: zlc
 * Date: 2017/5/11.
 */

public abstract class BaseActivity extends CommonBaseActivity {

    public FrameLayout getContainer() {
        return mContainer;
    }

    private FrameLayout mContainer;
    private View mNone;
    private View mError;
    private View mLoad;
    protected View mRootView;
    private state mState = state.complete;
    private ImageView mLoagGif;
    private LoadingDialog mDialog;

    public void setNeedStatusBar(boolean needStatusBar) {
        this.needStatusBar = needStatusBar;
    }

    private boolean needStatusBar = true;
    public LoadingManager mLoadingManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_base_view);
        mContainer = (FrameLayout) findViewById(R.id.container);
        LayoutInflater inflater = LayoutInflater.from(this);
        mRootView = inflater.inflate(initLayoutId(), null);
        ButterKnife.bind(this, mRootView);
        mContainer.addView(mRootView);
//        setStatusBar();

        mNone = inflater.inflate(R.layout.view_empty, null);
        mError = inflater.inflate(R.layout.view_error, null);
        mLoad = inflater.inflate(R.layout.view_loading, null);
        mContainer.addView(mNone);
        mContainer.addView(mError);
        mContainer.addView(mLoad);
        mNone.setVisibility(View.GONE);
        mError.setVisibility(View.GONE);
        mLoad.setVisibility(View.GONE);
        mDialog=new LoadingDialog(this,"加载中...");
//        RelativeLayout windowsManager = (RelativeLayout) mInflater.inflate(
//                R.layout.windows_manager, null);//为整个activity的布局
//        windowsManager .addView(fakeTitleContainer);

        initView();
        /*---------要求子类布局中必须有一个id是status_bar用于占去状态栏的位置-------
        ---------如果不需要请在initView()方法中调用setNeedStatusBar(false)--------*/
        if (needStatusBar)
            setStatusBarHight((LinearLayout) mRootView.findViewById(R.id.status_bar));
        initData();
        addLisenter();
        initLoadingManger();


        mLoagGif = (ImageView) mLoad.findViewById(R.id.load_gif);
    }

    protected abstract int initLayoutId();

    protected void addLisenter() {

    }

    protected abstract void initData();

    protected abstract void initView();

    public void initLoadingManger() {
        mLoadingManager = LoadingManager.generate(initLoadview(), new LoadingListener() {
            @Override
            public void setRetryEvent(View retryView) {
                retryRefreashTView(retryView);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        MyApp.getRefWatcher().watch(this);
    }

    public void setState(state state) {
        mState = state;
        refreshState();
    }

    public state getState() {
        return mState;
    }

    private void refreshState() {
        switch (mState) {
            case loading:
                mLoad.setVisibility(View.VISIBLE);
                Glide.with(this).load(R.drawable.loading).into(mLoagGif);
                break;
            case error:
                mLoagGif.setImageDrawable(null);
                mLoad.setVisibility(View.GONE);
                mError.setVisibility(View.VISIBLE);
                break;
            case empty:
                mLoagGif.setImageDrawable(null);
                mLoad.setVisibility(View.GONE);
                mError.setVisibility(View.GONE);
                mNone.setVisibility(View.VISIBLE);
                break;
            case complete:
                mLoagGif.setImageDrawable(null);
                mLoad.setVisibility(View.GONE);
                mError.setVisibility(View.GONE);
                mNone.setVisibility(View.GONE);
                mRootView.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     *子类重写可以添加占位图
     */
    public Object initLoadview() {
        return null;
    }
    /**
     *子类重写可以响应占位图点击事件
     */
    public void retryRefreashTView(View retryView) {
    }

    /**
     * showLoading(显示加载进度)
     */
    public void showLoading() {
        if (mDialog != null) {
            mDialog.show();
        }
    }

    /**
     * showLoading(显示加载进度)
     */
    public void showLoading(String msg) {
        if (mDialog != null) {
            mDialog.setMessage(msg);
            mDialog.show();
        }
    }

    /**
     * dismissLoading(取消加载进度)
     */
    public void hideLoading() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }
}
