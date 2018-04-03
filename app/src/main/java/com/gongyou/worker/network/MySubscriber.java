package com.gongyou.worker.network;





import com.gongyou.worker.utils.LogUtil;

import rx.Subscriber;

/**
 * by y on 2016/5/6.
 */
public class MySubscriber<T> extends Subscriber<T> {

    @Override
    public void onStart() {
        super.onStart();
        LogUtil.i("onStart被调用了");
    }

    @Override
    public void onCompleted() {
        LogUtil.i("onCompleted被调用了");
    }

    @Override
    public void onError(Throwable e) {
        LogUtil.e("onError被调用了");
        LogUtil.i(e.getMessage());
    }

    @Override
    public void onNext(T t) {
        LogUtil.i("onNext被调用了");
    }


}
