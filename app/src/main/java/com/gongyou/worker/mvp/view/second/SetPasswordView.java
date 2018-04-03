package com.gongyou.worker.mvp.view.second;


import com.gongyou.worker.mvp.bean.second.LoginInfo;
import com.gongyou.worker.mvp.bean.second.StringInfo;
import com.gongyou.worker.mvp.view.BaseView;
import com.gongyou.worker.network.RequestCode;

/**
 * Created by Administrator on 2018/3/16.
 */

public interface SetPasswordView extends BaseView {

    void registerSetPassword(LoginInfo loginInfo, RequestCode requestCode);

    void reSetPassword(StringInfo resetPWDData, RequestCode requestCode);
}
