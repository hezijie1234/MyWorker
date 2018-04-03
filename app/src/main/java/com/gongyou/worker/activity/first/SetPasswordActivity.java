package com.gongyou.worker.activity.first;


import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.gongyou.worker.R;
import com.gongyou.worker.SampleApplicationLike;
import com.gongyou.worker.activity.MVPBaseActivity;
import com.gongyou.worker.mvp.bean.second.LoginInfo;
import com.gongyou.worker.mvp.bean.second.StringInfo;
import com.gongyou.worker.mvp.presenter.second.SetPasswordPresenter;
import com.gongyou.worker.mvp.view.second.SetPasswordView;
import com.gongyou.worker.network.RequestCode;
import com.gongyou.worker.utils.SpUtils;

import butterknife.BindView;

public class SetPasswordActivity extends MVPBaseActivity<SetPasswordView,SetPasswordPresenter> implements SetPasswordView {
    private int mType;
    private String mPhoneNum;
    private String mPWD;
    private String mSMSNum;
    @BindView(R.id.et_psd)
    EditText mPassword;
    @BindView(R.id.et_resetpsd)
    EditText mResetPWD;
    @BindView(R.id.btn_psw)
    Button mSendBtn;
    @Override
    protected int initLayoutId() {
        return R.layout.activity_set_password;
    }



    @Override
    protected void initData() {
        Intent intent = getIntent();
        mPhoneNum = intent.getStringExtra("phone");
        mType = intent.getIntExtra("type",0);
        mSMSNum = intent.getStringExtra("smsCode");
    }

    @Override
    protected void addLisenter() {
        super.addLisenter();
        mSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pwd = mPassword.getText().toString().trim();
                mPWD = mResetPWD.getText().toString().trim();
                if (checkPwd(pwd,mPWD)){
                    if (mType == 0){
                        mPresenter.setPassword(mPhoneNum,pwd,mPWD,mSMSNum,"");
                    }else if (mType == 1){
                        mPresenter.resetPassword(mPhoneNum,pwd,mPWD,mSMSNum);
                    }
                }
            }
        });
    }

    private boolean checkPwd(String pwd, String repwd) {
        if (TextUtils.isEmpty(pwd)) {
            SampleApplicationLike.showToast("密码不能为空 !");
            return false;
        }
        if (TextUtils.isEmpty(repwd)) {
            SampleApplicationLike.showToast("请输入确认密码 !");
            return false;
        }
        if (!pwd.equals(repwd)) {
            SampleApplicationLike.showToast("两次输入的密码不相同 !");
            return false;
        }
        return true;
    }

    @Override
    protected SetPasswordPresenter initPresenter() {
        return new SetPasswordPresenter();
    }

    @Override
    public void onError(String msg, RequestCode requestCode) {
        if (!TextUtils.isEmpty(msg)){
            SampleApplicationLike.showToast(msg);
        }
    }

    @Override
    public void registerSetPassword(LoginInfo loginInfo, RequestCode requestCode) {
        SampleApplicationLike.showToast("帐号注册成功 !");
        SpUtils.putString(this, "token", loginInfo.getToken());
        SampleApplicationLike.setToken(loginInfo.getToken());
        Intent intent = new Intent(SetPasswordActivity.this, LoginActivity.class);
        intent.putExtra("phone",mPhoneNum);
        intent.putExtra("password",mPWD);
        startActivity(intent);
    }

    @Override
    public void reSetPassword(StringInfo resetPWDData, RequestCode requestCode) {
        SampleApplicationLike.showToast("密码修改成功 !");
        Intent intent = new Intent(SetPasswordActivity.this, LoginActivity.class);
        Log.e("111", "reSetPassword: "+ mPhoneNum );
        Log.e("111", "reSetPassword: "+ mPWD );
        intent.putExtra("phone",mPhoneNum);
        intent.putExtra("password",mPWD);
        startActivity(intent);
    }

    @Override
    protected void initView() {
        setNeedStatusBar(false);
    }
}
