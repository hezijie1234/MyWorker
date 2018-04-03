package com.gongyou.worker.widget;

import android.view.View;

/**
 * @Description:
 * @Author: LiuXiaoLong
 * @Time: 2018/2/8:11:39
 */

public abstract class LoadingListener {
    public abstract void setRetryEvent(View retryView);

    public void setLoadingEvent(View loadingView)
    {
    }

    public void setEmptyEvent(View emptyView)
    {
    }

    public int generateLoadingLayoutId()
    {
        return LoadingManager.NO_LAYOUT_ID;
    }

    public int generateRetryLayoutId()
    {
        return LoadingManager.NO_LAYOUT_ID;
    }

    public int generateEmptyLayoutId()
    {
        return LoadingManager.NO_LAYOUT_ID;
    }

    public View generateLoadingLayout()
    {
        return null;
    }

    public View generateRetryLayout()
    {
        return null;
    }

    public View generateEmptyLayout()
    {
        return null;
    }

    public boolean isSetLoadingLayout()
    {
        if (generateLoadingLayoutId() != LoadingManager.NO_LAYOUT_ID || generateLoadingLayout() != null)
            return true;
        return false;
    }

    public boolean isSetRetryLayout()
    {
        if (generateRetryLayoutId() != LoadingManager.NO_LAYOUT_ID || generateRetryLayout() != null)
            return true;
        return false;
    }

    public boolean isSetEmptyLayout()
    {
        if (generateEmptyLayoutId() != LoadingManager.NO_LAYOUT_ID || generateEmptyLayout() != null)
            return true;
        return false;
    }


}
