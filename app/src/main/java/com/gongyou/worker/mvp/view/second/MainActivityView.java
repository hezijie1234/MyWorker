package com.gongyou.worker.mvp.view.second;



import com.gongyou.worker.mvp.bean.second.FindInfo;
import com.gongyou.worker.mvp.bean.second.MsgViewInfo;
import com.gongyou.worker.mvp.view.BaseView;
import com.gongyou.worker.network.RequestCode;

import java.util.List;

/**
 * 作    者: ZhangLC
 * 创建时间: 2017/5/31.
 */

public interface MainActivityView extends BaseView {
    void loadInfoSuccess(List<FindInfo> data, RequestCode requestCode);

    void refreshMsgView(MsgViewInfo data, RequestCode requestCode);
}
