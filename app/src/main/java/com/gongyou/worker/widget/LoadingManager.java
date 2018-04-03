package com.gongyou.worker.widget;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;

/**
 * @Description: 网络加载中/重新尝试/无数据
 * @Author: LiuXiaoLong
 * @Time: 2018/2/8:11:36
 */

public class LoadingManager {
    public static final int NO_LAYOUT_ID = 0;
    public static int BASE_LOADING_LAYOUT_ID = NO_LAYOUT_ID;
    public static int BASE_RETRY_LAYOUT_ID = NO_LAYOUT_ID;
    public static int BASE_EMPTY_LAYOUT_ID = NO_LAYOUT_ID;

    public LoadingLayout mLoadingAndRetryLayout;


    public LoadingListener DEFAULT_LISTENER = new LoadingListener()
    {
        @Override
        public void setRetryEvent(View retryView)
        {

        }
    };


    public LoadingManager(Object activityOrFragmentOrView, LoadingListener listener)
    {   if (activityOrFragmentOrView==null) return;
        if (listener == null) listener = DEFAULT_LISTENER;

        ViewGroup contentParent = null;
        Context context;
        if (activityOrFragmentOrView instanceof Activity)
        {
            Activity activity = (Activity) activityOrFragmentOrView;
            context = activity;
            contentParent = (ViewGroup) activity.findViewById(android.R.id.content);
        } else if (activityOrFragmentOrView instanceof Fragment)
        {
            Fragment fragment = (Fragment) activityOrFragmentOrView;
            context = fragment.getActivity();
            contentParent = (ViewGroup) (fragment.getView().getParent());
        } else if (activityOrFragmentOrView instanceof View)
        {
            View view = (View) activityOrFragmentOrView;
            contentParent = (ViewGroup) (view.getParent());
            context = view.getContext();
        } else
        {
            throw new IllegalArgumentException("the argument's type must be Fragment or Activity: init(context)");
        }
        int childCount = contentParent.getChildCount();
        //get contentParent
        int index = 0;
        View oldContent;
        if (activityOrFragmentOrView instanceof View)
        {
            oldContent = (View) activityOrFragmentOrView;
            for (int i = 0; i < childCount; i++)
            {
                if (contentParent.getChildAt(i) == oldContent)
                {
                    index = i;
                    break;
                }
            }
        } else
        {
            oldContent = contentParent.getChildAt(0);
        }
        contentParent.removeView(oldContent);
        //setup content layout
        LoadingLayout loadingAndRetryLayout = new LoadingLayout(context);

        ViewGroup.LayoutParams lp = oldContent.getLayoutParams();
        contentParent.addView(loadingAndRetryLayout, index, lp);
        loadingAndRetryLayout.setContentView(oldContent);
        // setup loading,retry,empty layout
        setupLoadingLayout(listener, loadingAndRetryLayout);
        setupRetryLayout(listener, loadingAndRetryLayout);
        setupEmptyLayout(listener, loadingAndRetryLayout);
        //callback
        listener.setRetryEvent(loadingAndRetryLayout.getRetryView());
        listener.setLoadingEvent(loadingAndRetryLayout.getLoadingView());
        listener.setEmptyEvent(loadingAndRetryLayout.getEmptyView());
        mLoadingAndRetryLayout = loadingAndRetryLayout;
        hideViews();
    }

    private void setupEmptyLayout(LoadingListener listener, LoadingLayout loadingAndRetryLayout)
    {
        if (listener.isSetEmptyLayout())
        {
            int layoutId = listener.generateEmptyLayoutId();
            if (layoutId != NO_LAYOUT_ID)
            {
                loadingAndRetryLayout.setEmptyView(layoutId);
            } else
            {
                loadingAndRetryLayout.setEmptyView(listener.generateEmptyLayout());
            }
        } else
        {
            if (BASE_EMPTY_LAYOUT_ID != NO_LAYOUT_ID)
                loadingAndRetryLayout.setEmptyView(BASE_EMPTY_LAYOUT_ID);
        }
    }

    private void setupLoadingLayout(LoadingListener listener, LoadingLayout loadingAndRetryLayout)
    {
        if (listener.isSetLoadingLayout())
        {
            int layoutId = listener.generateLoadingLayoutId();
            if (layoutId != NO_LAYOUT_ID)
            {
                loadingAndRetryLayout.setLoadingView(layoutId);
            } else
            {
                loadingAndRetryLayout.setLoadingView(listener.generateLoadingLayout());
            }
        } else
        {
            if (BASE_LOADING_LAYOUT_ID != NO_LAYOUT_ID)
                loadingAndRetryLayout.setLoadingView(BASE_LOADING_LAYOUT_ID);
        }
    }

    private void setupRetryLayout(LoadingListener listener, LoadingLayout loadingAndRetryLayout)
    {
        if (listener.isSetRetryLayout())
        {
            int layoutId = listener.generateRetryLayoutId();
            if (layoutId != NO_LAYOUT_ID)
            {
                loadingAndRetryLayout.setLoadingView(layoutId);
            } else
            {
                loadingAndRetryLayout.setLoadingView(listener.generateRetryLayout());
            }
        } else
        {
            if (BASE_RETRY_LAYOUT_ID != NO_LAYOUT_ID)
                loadingAndRetryLayout.setRetryView(BASE_RETRY_LAYOUT_ID);
        }
    }

    public static LoadingManager generate(Object activityOrFragment, LoadingListener listener)
    {
        return new LoadingManager(activityOrFragment, listener);
    }

    public void showLoading()
    {
        mLoadingAndRetryLayout.showLoading();
    }

    public void showRetry()
    {
        mLoadingAndRetryLayout.showRetry();
    }

    public void showContent()
    {
        mLoadingAndRetryLayout.showContent();
    }

    public void showEmpty()
    {
        mLoadingAndRetryLayout.showEmpty();
    }

    public void hideViews()
    {
        mLoadingAndRetryLayout.hideViews();
    }
}
