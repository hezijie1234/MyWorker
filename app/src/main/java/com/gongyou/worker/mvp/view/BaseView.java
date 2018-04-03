package com.gongyou.worker.mvp.view;


import com.gongyou.worker.network.RequestCode;

/**
 * Created by Administrator on 2018/3/13.
 */

public interface BaseView {

    void NetWorkError(RequestCode requestCode);

    void onError(String msg, RequestCode requestCode);
}
