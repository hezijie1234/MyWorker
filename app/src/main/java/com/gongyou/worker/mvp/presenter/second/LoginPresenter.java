package com.gongyou.worker.mvp.presenter.second;



import com.gongyou.worker.mvp.bean.ResponseInfo;
import com.gongyou.worker.mvp.bean.second.LoginInfo;
import com.gongyou.worker.mvp.model.BaseModel;
import com.gongyou.worker.mvp.presenter.BasePresenter;
import com.gongyou.worker.mvp.view.second.LoginView;
import com.gongyou.worker.network.RequestCode;

import rx.Observable;
import rx.Subscription;

/**
 * Created by Administrator on 2018/3/14.
 */

public class LoginPresenter extends BasePresenter<LoginView> {

    public Subscription login(final String mobile, final String pwd, final String clientid, final int i){
        mSubscription = new BaseModel<LoginInfo,LoginView>(mView){

            @Override
            protected Observable<ResponseInfo<LoginInfo>> getObservable(RequestCode requestCode) {

                return mResponseInfoAPI.login(mobile,pwd,clientid,i);
            }

            @Override
            protected void onNext(LoginInfo data, RequestCode requestCode) {
                mView.loginSuccess(data,requestCode);
            }
        }.doSubscribeAPI(RequestCode.ox);
        return mSubscription;
    }

    public Subscription thirdPartyLogin(final String nick, final String openid, final String head_image, final String province, final String city,
                                        final String area, final String sex, final int register_type, final String clientid, final int i, final String unionid) {
        mSubscription = new BaseModel<LoginInfo, LoginView>(mView) {
            @Override
            protected Observable<ResponseInfo<LoginInfo>> getObservable(RequestCode requestCode) {
                return mResponseInfoAPI.thirdPartyLogin(nick, openid, head_image, province, city, area, sex, register_type, clientid, i,unionid);
            }

            @Override
            protected void onNext(LoginInfo data, RequestCode requestCode) {
                mView.loginSuccess(data, requestCode);
            }
        }.doSubscribeAPI(RequestCode.ox);
        return mSubscription;
    }
}
