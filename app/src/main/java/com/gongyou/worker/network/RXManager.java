package com.gongyou.worker.network;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Author: zlc
 * Date: 2017/5/11.
 */

public class RXManager {

    private static RXManager rxManager;

    private RXManager() {
    }

    public static RXManager getInstance() {
        if (rxManager == null) {
            synchronized (RXManager.class) {
                if (rxManager == null) {
                    rxManager = new RXManager();
                }
            }
        }
        return rxManager;
    }
                //订阅                    //观察                   //用户
    public <T> Subscription doSubscribe(Observable<T> observable, Subscriber<T> subscriber) {
        return observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
