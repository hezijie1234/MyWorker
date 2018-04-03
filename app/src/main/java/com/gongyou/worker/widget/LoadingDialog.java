package com.gongyou.worker.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gongyou.worker.R;


/**
 * @Description: 网络加载对话框
 * @Author: LiuXiaoLong
 * @Time: 2018/1/23:15:27
 */

public class LoadingDialog {
    public Dialog mDialog;
    private ProgressBar mLoading;
    private TextView mTvMessage;
    public LoadingDialog(Context context, String message) {
        View view = LayoutInflater.from(context).inflate(R.layout.netloading_dialog, null);
        mLoading=(ProgressBar)view.findViewById(R.id.progressBar);
        mTvMessage=(TextView)view.findViewById(R.id.tv_message);
        mTvMessage.setText(message);

        mDialog = new Dialog(context, R.style.LoadingDialog);
        mDialog.setContentView(view);
        mDialog.setCanceledOnTouchOutside(false);
    }

    /**
     * 设置展示内容
     * @param hint
     */
    public void setMessage(String hint) {
        mTvMessage.setText(hint);
    }

    /**
     * 展示
     */
    public void show() {
        if (!mDialog.isShowing()) {
            mDialog.show();
        }
    }

    /**
     * 取消
     */
    public void dismiss() {
        if (mDialog == null)
            return;
        if (mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }
}
