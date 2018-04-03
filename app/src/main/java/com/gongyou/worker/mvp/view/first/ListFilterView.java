package com.gongyou.worker.mvp.view.first;



import com.gongyou.worker.mvp.bean.first.ListFilterInfo;
import com.gongyou.worker.mvp.view.BaseView;
import com.gongyou.worker.network.RequestCode;

import java.util.List;

/**
 * Created by Administrator on 2018/3/13.
 */

public interface ListFilterView extends BaseView {

    void setListFilterData(ListFilterInfo data, RequestCode requestCode);

}
