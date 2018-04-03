package com.gongyou.worker.mvp.view.second;


import com.gongyou.worker.mvp.bean.second.SendSMSInfo;
import com.gongyou.worker.mvp.bean.second.VerifyCode;
import com.gongyou.worker.mvp.view.BaseView;
import com.gongyou.worker.network.RequestCode;

/**
 * Created by Administrator on 2018/3/15.
 */

public interface RegisterView extends BaseView {
    void setImageCode(VerifyCode data, RequestCode requestCode);
    void verifySuccess(SendSMSInfo info, RequestCode requestCode);

}
