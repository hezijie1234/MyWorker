package com.gongyou.worker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.gongyou.worker.mvp.bean.first.ListFilterInfo;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListFilterInfo.NormalBean info = new ListFilterInfo.NormalBean();
        info.a_id = 3;
        Toast.makeText(SampleApplicationLike.getContext(), "" + info
                .a_id, Toast.LENGTH_SHORT).show();
    }
}
