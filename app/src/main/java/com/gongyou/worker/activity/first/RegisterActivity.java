package com.gongyou.worker.activity.first;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.gongyou.worker.R;
import com.gongyou.worker.SampleApplicationLike;
import com.gongyou.worker.activity.MVPBaseActivity;
import com.gongyou.worker.mvp.bean.second.SendSMSInfo;
import com.gongyou.worker.mvp.bean.second.VerifyCode;
import com.gongyou.worker.mvp.presenter.second.RegisterPresenter;
import com.gongyou.worker.mvp.view.second.RegisterView;
import com.gongyou.worker.network.RequestCode;
import com.gongyou.worker.utils.Code;
import com.gongyou.worker.utils.TimeDown;
import com.gongyou.worker.utils.ValueUtil;

import butterknife.BindView;

public class RegisterActivity extends MVPBaseActivity<RegisterView,RegisterPresenter> implements RegisterView {
    @BindView(R.id.et_phone)
    EditText mPhoneNum;
    @BindView(R.id.et_ivCode)
    EditText mImageVerify;
    @BindView(R.id.et_smscode)
    EditText mVerifyNum;
    @BindView(R.id.rl_nextIv)
    RelativeLayout mNextImage;
    private int mType;
    @BindView(R.id.yanzhengma_img)
    ImageView mVerifyImage;
    @BindView(R.id.send_code)
    TextView mSendVerifyCode;
    @BindView(R.id.btn_toregister)
    Button mRegisterBtn;
    @BindView(R.id.ll_login)
    LinearLayout mLoginBtn;
    private TimeDown mSendCode;
    private String phoneNum;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        mType = intent.getIntExtra("type", 0);
        mPresenter.setImageCode(SampleApplicationLike.clientid);
    }

    @Override
    protected RegisterPresenter initPresenter() {
        return new RegisterPresenter();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void addLisenter() {
        super.addLisenter();
        mNextImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.setImageCode(SampleApplicationLike.clientid);
            }
        });

        mSendVerifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNum = mPhoneNum.getText().toString().trim();
                String verifyCode = mImageVerify.getText().toString().trim();
                if (!ValueUtil.isMobileNO(phoneNum)) {
                    SampleApplicationLike.showToast("请输入手机号码 !");
                    return;
                }
                if (TextUtils.isEmpty(verifyCode) || verifyCode.length() != 4) {
                    SampleApplicationLike.showToast("请填写图形验证");
                    return;
                }
                Log.e(TAG, "onClick: " + SampleApplicationLike.clientid );
                mPresenter.getSmsCode(phoneNum,verifyCode,mType,SampleApplicationLike.clientid);
                mSendVerifyCode.setText("");
                mSendVerifyCode.setEnabled(false);
                mSendCode = new TimeDown(60 * 1000, 1000, mSendVerifyCode, "重新获取");
                mSendCode.start();
            }
        });

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("111", "onClick: 确定按钮被点击了" );
                String smsCode = mVerifyNum.getText().toString().trim();
                Log.e(TAG, "onClick: "+smsCode + "phone:" + phoneNum );
                if (TextUtils.isEmpty(smsCode) || TextUtils.isEmpty(phoneNum)){
                    SampleApplicationLike.showToast("信息缺乏，无法提交！");
                    return;
                }
                Intent intent = new Intent(RegisterActivity.this, SetPasswordActivity.class);
                intent.putExtra("type",mType);
                intent.putExtra("smsCode",smsCode);
                intent.putExtra("phone",phoneNum);
                startActivity(intent);
            }
        });
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSendCode != null){
            mSendCode.cancel();
            mSendCode = null;
        }
    }

    @Override
    public void onError(String msg, RequestCode requestCode) {
        switch (requestCode) {
            case rat:
                SampleApplicationLike.showToast("获取图片验证码失败 !");
                break;
            case ox:
                SampleApplicationLike.showToast("发送失败");
                break;
        }
        SampleApplicationLike.showToast(msg);
    }

    @Override
    public void setImageCode(VerifyCode data, RequestCode requestCode) {
        mVerifyImage.setImageBitmap(Code.getInstance().createBitmap(data.getVerify_code()));
    }

    @Override
    public void verifySuccess(SendSMSInfo info, RequestCode requestCode) {
        SampleApplicationLike.showToast("发送成功");
    }
}
