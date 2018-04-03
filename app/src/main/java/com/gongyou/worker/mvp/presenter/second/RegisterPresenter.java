package com.gongyou.worker.mvp.presenter.second;

import android.util.Log;


import com.gongyou.worker.mvp.bean.ResponseInfo;
import com.gongyou.worker.mvp.bean.second.SendSMSInfo;
import com.gongyou.worker.mvp.bean.second.VerifyCode;
import com.gongyou.worker.mvp.model.BaseModel;
import com.gongyou.worker.mvp.presenter.BasePresenter;
import com.gongyou.worker.mvp.view.second.RegisterView;
import com.gongyou.worker.network.RequestCode;

import rx.Observable;
import rx.Subscription;

/**
 * Created by Administrator on 2018/3/15.
 */

public class RegisterPresenter extends BasePresenter<RegisterView> {
    public Subscription setImageCode(final String clientId){
        mSubscription = new BaseModel<VerifyCode,RegisterView>(mView){

            @Override
            protected Observable<ResponseInfo<VerifyCode>> getObservable(RequestCode requestCode) {

                return mResponseInfoAPI.getImageCode("be3714e1271600076c24bd56aab87242");
            }

            @Override
            protected void onNext(VerifyCode data, RequestCode requestCode) {
                mView.setImageCode(data,requestCode);
            }
        }.doSubscribeAPI(RequestCode.rat);

        return mSubscription;
    }

    public Subscription getSmsCode(final String phoneNum,final String imageCode,final int type,final String clientId){
        mSubscription = new BaseModel<SendSMSInfo,RegisterView>(mView){

            @Override
            protected Observable<ResponseInfo<SendSMSInfo>> getObservable(RequestCode requestCode) {

                return mResponseInfoAPI.sendSMS(phoneNum,imageCode,type,"be3714e1271600076c24bd56aab87242");
            }

            @Override
            protected void onNext(SendSMSInfo data, RequestCode requestCode) {
                mView.verifySuccess(data,requestCode);
            }
        }.doSubscribeAPI(RequestCode.ox);
        return mSubscription;
    }
}
