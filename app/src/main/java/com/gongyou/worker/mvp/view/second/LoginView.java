package com.gongyou.worker.mvp.view.second;




import com.gongyou.worker.mvp.bean.second.LoginInfo;
import com.gongyou.worker.mvp.view.BaseView;
import com.gongyou.worker.network.RequestCode;

import java.util.List;

/**
 * 作    者: ZhangLC
 * 创建时间: 2017/6/19.
 * 说    明:
 */

public interface LoginView extends BaseView {
    void loginSuccess(LoginInfo data, RequestCode requestCode);

//    void setWorkTypeData(List<WorkType> data, RequestCode requestCode);
//
//    void setQQLoginCallBack(QQUnionId qqUnionInfo);
}
