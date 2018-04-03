package com.gongyou.worker.activity.first;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.gongyou.worker.R;


import butterknife.BindView;
import butterknife.ButterKnife;

public class CertificationActivity extends AppCompatActivity {
    @BindView(R.id.idcard_btn)
    Button idCardBtn;
    @BindView(R.id.bankcard_btn)
    Button bankCardBtn;
    @BindView(R.id.business_license_btn)
    Button licenseBtn;
    private boolean hasGotToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certification);
        ButterKnife.bind(this);
        initData();
        listener();
    }



    private void initData() {
        initAccessTokenWithAkSk();
    }

    private void initAccessTokenWithAkSk() {
        OCR.getInstance().initAccessToken(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken result) {
                // 调用成功，返回AccessToken对象
                String token = result.getAccessToken();
                hasGotToken = true;
                Log.e("111", "onResult: " + token );
            }
            @Override
            public void onError(OCRError error) {
                // 调用失败，返回OCRError子类SDKError对象
                Log.e("111", "onError: " + error );
            }
        }, getApplicationContext());
    }
    private boolean checkTokenStatus() {
        if (!hasGotToken) {
            Toast.makeText(getApplicationContext(), "token还未成功获取", Toast.LENGTH_LONG).show();
        }
        return hasGotToken;
    }
    private void listener() {
        idCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkTokenStatus()){
                    return;
                }
                Intent intent = new Intent(CertificationActivity.this, IDCardActivity.class);
                startActivity(intent);
            }
        });
        bankCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkTokenStatus()){
                    return;
                }
            }
        });
        licenseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkTokenStatus()){
                    return;
                }
            }
        });

    }
}
