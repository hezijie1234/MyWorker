package com.gongyou.worker.mvp.presenter.second;


import com.gongyou.worker.SampleApplicationLike;
import com.gongyou.worker.mvp.bean.ResponseInfo;
import com.gongyou.worker.mvp.bean.second.FindInfo;
import com.gongyou.worker.mvp.bean.second.MsgViewInfo;
import com.gongyou.worker.mvp.model.BaseModel;
import com.gongyou.worker.mvp.presenter.BasePresenter;
import com.gongyou.worker.mvp.view.second.MainActivityView;
import com.gongyou.worker.network.RequestCode;
import com.gongyou.worker.utils.LogUtil;

import java.util.List;

import rx.Observable;
import rx.Subscription;

/**
 * Created by Administrator on 2018/3/23.
 */

public class MainActivityPresenter extends BasePresenter<MainActivityView> {

    public void getUserData(final String token, final double mLongitude, final double mLatitude) {
        mSubscription = new BaseModel<List<FindInfo>, MainActivityView>(mView) {
            @Override
            protected Observable<ResponseInfo<List<FindInfo>>> getObservable(RequestCode requestCode) {
                LogUtil.e("mLongitude="+mLongitude+"               mLatitude------"+mLatitude);
                return mResponseInfoAPI.getUserData(mLongitude,mLatitude);
            }

            @Override
            protected void onNext(List<FindInfo> data, RequestCode requestCode) {
                mView.loadInfoSuccess(data,requestCode);
            }
        }.doSubscribeAPI(RequestCode.rat);
    }

    public Subscription refreshMsgView() {
        mSubscription = new BaseModel<MsgViewInfo, MainActivityView>(mView) {
            @Override
            protected Observable<ResponseInfo<MsgViewInfo>> getObservable(RequestCode requestCode) {
                return mResponseInfoAPI.refreshMsgView(SampleApplicationLike.getToken());
            }

            @Override
            protected void onNext(MsgViewInfo data, RequestCode requestCode) {
                mView.refreshMsgView(data,requestCode);
            }
        }.doSubscribeAPI(RequestCode.ox);
        return mSubscription;
    }

}
