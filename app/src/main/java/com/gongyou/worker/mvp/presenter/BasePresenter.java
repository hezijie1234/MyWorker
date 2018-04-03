package com.gongyou.worker.mvp.presenter;



import com.gongyou.worker.mvp.view.BaseView;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by Administrator on 2018/3/13.
 */

public abstract class BasePresenter<V extends BaseView>  {
    public V mView;
    public void attach(V mView){
        this.mView = mView;
    }

    public BasePresenter(){

    }

    public Subscription mSubscription;

    public void detach(){
        if(mSubscription != null && mSubscription.isUnsubscribed()){
            mSubscription.unsubscribe();
        }
    }
}
