/*
 * Copyright © Yan Zhenjie. All Rights Reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gongyou.worker.activity.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.gongyou.worker.MainActivity;
import com.gongyou.worker.R;
import com.gongyou.worker.utils.SpUtils;


/**
 *
 */
public class WelActivity extends AppCompatActivity implements Runnable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wel);

//        AppConfig.getInstance().initialize();
        getWindow().getDecorView().postDelayed(this, 1000);
//        MyApp.getHandler().postDelayed(this, 2000);
    }

    @Override
    public void run() {
        boolean isNew = SpUtils.getBoolean(this, "isNewStart", true);
        Intent intent;
        if (isNew)
            intent = new Intent(WelActivity.this, GuideActivity.class);
        else
            intent = new Intent(WelActivity.this, MainActivity.class);
            startActivity(intent);
           finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return true;
    }

    @Override
    protected void onDestroy() {
        getWindow().getDecorView().removeCallbacks(this);
        super.onDestroy();
    }
}
