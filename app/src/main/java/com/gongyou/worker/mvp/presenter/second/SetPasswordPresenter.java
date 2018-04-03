package com.gongyou.worker.mvp.presenter.second;


import com.gongyou.worker.mvp.bean.ResponseInfo;
import com.gongyou.worker.mvp.bean.second.LoginInfo;
import com.gongyou.worker.mvp.bean.second.StringInfo;
import com.gongyou.worker.mvp.model.BaseModel;
import com.gongyou.worker.mvp.presenter.BasePresenter;
import com.gongyou.worker.mvp.view.second.SetPasswordView;
import com.gongyou.worker.network.RequestCode;

import rx.Observable;
import rx.Subscription;

/**
 * Created by Administrator on 2018/3/16.
 */

public class SetPasswordPresenter extends BasePresenter<SetPasswordView> {

    public Subscription setPassword(final String phone,final String pwd,final String rePwd,final String smsCode,final String o){
        mSubscription = new BaseModel<LoginInfo,SetPasswordView>(mView){

            @Override
            protected Observable<ResponseInfo<LoginInfo>> getObservable(RequestCode requestCode) {
                return mResponseInfoAPI.mobileRegist(phone,pwd,rePwd,smsCode,o,1);
            }

            @Override
            protected void onNext(LoginInfo data, RequestCode requestCode) {
                mView.registerSetPassword(data,requestCode);
            }
        }.doSubscribeAPI(RequestCode.rat);
        return mSubscription;
    }

    public Subscription resetPassword(final String phone ,final String pwd,final String repwd,final String smsCode){
        mSubscription = new BaseModel<StringInfo,SetPasswordView>(mView){
            @Override
            protected Observable<ResponseInfo<StringInfo>> getObservable(RequestCode requestCode) {
                return mResponseInfoAPI.reSetPassword(phone,pwd,repwd,smsCode);
            }

            @Override
            protected void onNext(StringInfo stringInfo, RequestCode requestCode) {
                mView.reSetPassword(stringInfo,requestCode);
            }
        }.doSubscribeAPI(RequestCode.ox);
        return mSubscription;
    }
}
