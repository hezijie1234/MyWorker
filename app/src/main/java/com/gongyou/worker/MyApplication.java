package com.gongyou.worker;

import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * Created by Administrator on 2017-07-17.
 */

public class MyApplication extends TinkerApplication {
    public MyApplication() {
        super(ShareConstants.TINKER_ENABLE_ALL, "com.gongyou.worker.SampleApplicationLike",
                "com.tencent.tinker.loader.TinkerLoader", false);
    }
}
