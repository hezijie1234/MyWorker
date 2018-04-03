package com.gongyou.worker.activity.first;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.gongyou.worker.R;
import com.gongyou.worker.SampleApplicationLike;
import com.gongyou.worker.activity.MVPBaseActivity;
import com.gongyou.worker.mvp.bean.second.LoginInfo;
import com.gongyou.worker.mvp.presenter.second.LoginPresenter;
import com.gongyou.worker.mvp.view.second.LoginView;
import com.gongyou.worker.network.RequestCode;
import com.gongyou.worker.utils.Constant;
import com.gongyou.worker.utils.LogUtil;
import com.gongyou.worker.utils.SpUtils;
import com.gongyou.worker.utils.ValueUtil;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.utils.SocializeUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import butterknife.BindView;


public class LoginActivity extends MVPBaseActivity<LoginView,LoginPresenter> implements LoginView {
    @BindView(R.id.username)
    EditText mUserName;
    @BindView(R.id.password)
    EditText mPassword;
    @BindView(R.id.login)
    Button mLoginBtn;
    @BindView(R.id.register)
    TextView mRegister;
    @BindView(R.id.tv_forget)
    TextView mForgetText;
    @BindView(R.id.login_qq)
    ImageView mQQLogin;
    @BindView(R.id.login_weichart)
    ImageView mWeiChatLogin;
    @BindView(R.id.login_sina)
    ImageView mSiNaLogin;
    private ProgressDialog dialog;
    private String mPhoneNum;
    private String mPwd;

    private String nick;
    private String openid;
    private String head_image;
    private String province;
    private String city;
    private String area;
    private String sex;
    private int register_type;
    private String unionid;
    private String pwd;

    @Override
    public void onError(String msg, RequestCode requestCode) {
        if (!TextUtils.isEmpty(msg)){
            SampleApplicationLike.showToast(msg);
        }
    }

    @Override
    public void loginSuccess(LoginInfo data, RequestCode requestCode) {
        switch (requestCode){
            case ox:
                String token = data.getToken();
//        mobile = mUsername.getText().toString().trim();
                if (TextUtils.isEmpty(data.getMobile())) {
                    data.setMobile(mPhoneNum);
                }
                SpUtils.putString(this, "token", token);
                SpUtils.putString(this, "mobile", mPhoneNum);
                SampleApplicationLike.setToken(token);
                SampleApplicationLike.showToast("登录成功 !");
                break;
            case dog:
        }
    }

    @Override
    protected LoginPresenter initPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected int initLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e(TAG, "onNewIntent: 注册或者重新修改密码可以直接登录。" );
        String phone = intent.getStringExtra("phone");
        String password = intent.getStringExtra("password");
        Log.e("111", "onNewIntent: "+ phone );
        Log.e("111", "onNewIntent: "+ password );
        if (checkMobileAndPwd(phone,password)){
            if (TextUtils.isEmpty(SampleApplicationLike.clientid)) {
                SampleApplicationLike.showToast("缺少设备号，请授予权限");
                return;
            }

            mPresenter.login(phone,password, Constant.DEVICE_CODE,1);
        }
    }

    @Override
    protected void addLisenter() {
        super.addLisenter();
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPhoneNum = mUserName.getText().toString().trim();
                mPwd = mPassword.getText().toString().trim();
                if (checkMobileAndPwd(mPhoneNum,mPwd)){
                    if (TextUtils.isEmpty(SampleApplicationLike.clientid)) {
                        SampleApplicationLike.showToast("缺少设备号，请授予权限");
                        return;
                    }
                    mPresenter.login(mPhoneNum,mPwd,SampleApplicationLike.clientid,1);
                }
            }
        });
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                intent.putExtra("type",0);
                startActivity(intent);
            }
        });

        mForgetText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                intent.putExtra("type",1);
                startActivity(intent);
            }
        });

        mQQLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isQQClientAvailable(LoginActivity.this)) {
                    SampleApplicationLike.showToast("未安装QQ");
                    return;
                }
                UMShareAPI.get(LoginActivity.this).getPlatformInfo(LoginActivity.this,
                        SHARE_MEDIA.QQ, authListener);
            }
        });
        mWeiChatLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isWeixinAvilible(LoginActivity.this)) {
                    SampleApplicationLike.showToast("未安装微信");
                    return;
                }
                UMShareAPI.get(LoginActivity.this).getPlatformInfo(LoginActivity.this,
                        SHARE_MEDIA.WEIXIN, authListener);
            }
        });

        mSiNaLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UMShareAPI.get(LoginActivity.this).getPlatformInfo(LoginActivity.this,
                        SHARE_MEDIA.SINA, authListener);
            }
        });

    }
    public  boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 判断qq是否可用
     *
     * @param context
     * @return
     */
    public  boolean isQQClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mobileqq")) {

                    return true;
                }
            }
        }
        return false;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    private boolean checkMobileAndPwd(String mobile, String pwd) {
        if (!ValueUtil.isMobileNO(mobile)) {
            Toast.makeText(this, "请检查您的手机号 !", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(pwd) || pwd.length() < 6) {
            Toast.makeText(this, "密码不少于 6 位 !", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    protected void initData() {
        if (TextUtils.isEmpty(SampleApplicationLike.clientid)) {
            SampleApplicationLike.clientid = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
            LogUtil.e("--------SampleApplicationLike--Settings.Secure.getString----telephonyManager.getDeviceId()---------clientid-----------------" + SampleApplicationLike.clientid);
        }
        dialog = new ProgressDialog(this);
        // 三方获取用户资料时是否每次都要进行授权,设置为每次都要进行授权。
        UMShareConfig config = new UMShareConfig();
        config.isNeedAuthOnGetUserInfo(true);
        UMShareAPI.get(LoginActivity.this).setShareConfig(config);
    }

    UMAuthListener authListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            SocializeUtils.safeShowDialog(dialog);
        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int i, Map<String, String> data) {
            SocializeUtils.safeCloseDialog(dialog);
            String temp = "";
            for (String key : data.keySet()) {
                temp = temp + key + " : " + data.get(key) + "\n";
            }
            LogUtil.d(temp + (new SimpleDateFormat()).format(new Date()) + "--umeng_tool--");
//            String name = platform.name();

            nick = data.get("screen_name");
            unionid = data.get("unionid");
            String accessToken = data.get("accessToken");
            openid = data.get("openid");
            head_image = data.get("iconurl");
            province = data.get("province");
            city = data.get("city");
            area = data.get("province");
            String gender = data.get("gender");
            sex = "男".equals(gender) ? "1" : ("女".equals(gender) ? "2" : "0");
            LogUtil.e("" + platform.name());
            register_type = SHARE_MEDIA.QQ.equals(platform) ? 2 : (SHARE_MEDIA.WEIXIN.equals(platform) ? 3 : (SHARE_MEDIA.SINA.equals(platform) ? 4 : -1));
            LogUtil.d("AAAAAAAAAAAAA", "screen_name-" + nick + "" +
                    "\n-accessToken-" + accessToken +
                    "\n-openid-" + openid +
//                    "\n-head_image-" + head_image +
                    "\nprovince" + province +
                    "\n--sex-" + sex +
                    "\n--register_type--" + register_type);
            if (openid == null) {
                openid = accessToken;
            }
            LogUtil.e(openid);
            if (platform.equals(SHARE_MEDIA.QQ)) {
//                mPresenter.getQQUnionId(accessToken);
            } else if (platform.equals(SHARE_MEDIA.SINA)) {
                unionid = data.get("uid");
                mPresenter.thirdPartyLogin(nick, openid, head_image, province, city, area, sex, register_type, SampleApplicationLike.clientid, 1, unionid);
            } else {
                mPresenter.thirdPartyLogin(nick, openid, head_image, province, city, area, sex, register_type, SampleApplicationLike.clientid, 1, unionid);
            }
        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable t) {
            SocializeUtils.safeCloseDialog(dialog);
            SampleApplicationLike.showToast("失败");
            LogUtil.e(t.getMessage());
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {
            SampleApplicationLike.showToast("取消");
            SocializeUtils.safeCloseDialog(dialog);
        }
    };
}
