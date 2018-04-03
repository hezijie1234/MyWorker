package com.gongyou.worker.utils;

import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/4/18.
 */

public class TimeDown extends CountDownTimer {

    protected TextView tv;
    private String endText;

    /**
     * @param millisInFuture    剩余时间
     * @param countDownInterval 间隔
     * @param tv                显示位置
     * @param endText           最终显示
     */
    public TimeDown(long millisInFuture, long countDownInterval, TextView tv, String endText) {
        super(millisInFuture, countDownInterval);
        this.tv = tv;
        this.endText = endText;
    }

    /**
     * 控制每次间隔的显示
     *
     * @param millisUntilFinished 单次间隔结果
     */
    @Override
    public void onTick(long millisUntilFinished) {
        tv.setText(millisUntilFinished / 1000 + "");
    }

    @Override
    public void onFinish() {
        tv.setEnabled(true);
        tv.setText(endText);
        tv.setClickable(true);
    }
}
