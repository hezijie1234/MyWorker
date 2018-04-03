package com.gongyou.worker.mvp.presenter.first;



import com.gongyou.worker.SampleApplicationLike;
import com.gongyou.worker.mvp.bean.ResponseInfo;
import com.gongyou.worker.mvp.bean.first.ListFilterInfo;
import com.gongyou.worker.mvp.model.BaseModel;
import com.gongyou.worker.mvp.presenter.BasePresenter;

import com.gongyou.worker.mvp.view.first.ListFilterView;
import com.gongyou.worker.network.RequestCode;
import com.gongyou.worker.utils.Constant;

import rx.Observable;
import rx.Subscription;

/**
 * Created by Administrator on 2018/3/13.
 */

public class ListFilterPresenter extends BasePresenter<ListFilterView> {

    private int page = 1;

    public Subscription getListFilterData(final String token, final double lng, final double lat, final int min_wage, final int max_wage, final String profession_id, RequestCode code){
        final long startTime = System.currentTimeMillis();
        mSubscription = new BaseModel<ListFilterInfo,ListFilterView>(mView){

            @Override
            protected Observable<ResponseInfo<ListFilterInfo>> getObservable(RequestCode requestCode) {
                switch (requestCode) {
                    case first:
                    case refresh:
                        page = 1;
                        break;
                    case more:
                        page++;
                        break;
                }
                return mResponseInfoAPI.getListFilterData(token, lng, lat, page, profession_id,min_wage,max_wage);
            }

            @Override
            protected void onNext(final ListFilterInfo data, final RequestCode requestCode) {
                long dTime = System.currentTimeMillis() - startTime;
                if (dTime < Constant.Net_Time) {
                    SampleApplicationLike.getHandler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mView.setListFilterData(data, requestCode);
                        }
                    }, Constant.Net_Time - dTime);
                } else {
                    mView.setListFilterData(data, requestCode);
                }
            }
        }.doSubscribeAPI(code);
        return mSubscription;
    }

}
