package com.gongyou.worker.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.gongyou.worker.mvp.presenter.BasePresenter;
import com.gongyou.worker.mvp.view.BaseView;
import com.gongyou.worker.network.RequestCode;
import com.gongyou.worker.utils.LogUtil;
import com.gongyou.worker.utils.state;


/**
 * Created by Administrator on 2018/3/14.
 */

public abstract class MVPBaseActivity<V,P extends BasePresenter> extends BaseActivity implements BaseView {

    protected P mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        LogUtil.e("-----------onCreate------------"+this.getClass().getSimpleName()+"------------------------");
        mPresenter = initPresenter();
        mPresenter.attach(MVPBaseActivity.this);
        super.onCreate(savedInstanceState);
    }

    protected abstract P initPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detach();
    }

    /**
     * 网络请求错误,未获取到正确格式数据
     */
    @Override
    public void NetWorkError(RequestCode requestCode) {
        LogUtil.e("执行错误---------------------------------------------------");
//        Toast.makeText(MyApp.getContext(), "网络异常 !", Toast.LENGTH_SHORT).show();  //不让这个Toast被覆盖掉
        if (getState() == state.loading) {
            setState(state.error);
        }
    }

    @Override
    protected void initView() {
        //子类如果用注解就用不着
    }
}
