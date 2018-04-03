package com.gongyou.worker.activity.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.gongyou.worker.MainActivity;
import com.gongyou.worker.R;
import com.gongyou.worker.utils.SpUtils;
import com.viewpagerindicator.PageIndicator;


/**
 * 作    者: Zhang Longchen
 * 创建时间: 2017/9/14.
 * 说    明:
 */

public class GuideActivity extends AppCompatActivity {

    private long firstClick = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ViewPager viewPager = (ViewPager) findViewById(R.id.vp_ad);
        PageIndicator mIndicator = (PageIndicator) findViewById(R.id.indicator);
        viewPager.setAdapter(new ImageLoopAdapter());
        mIndicator.setViewPager(viewPager);
    }

    private static final long DoubleClick = 1500;

    private int[] arr = {R.drawable.b1, R.drawable.b2, R.drawable.b3};

    private class ImageLoopAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return arr.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = LayoutInflater.from(GuideActivity.this).inflate(R.layout.item_guide, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.iv_image);
            Button button = (Button) view.findViewById(R.id.btn_confirm);
            imageView.setImageResource(arr[position]);
            button.setVisibility(position == 2 ? View.VISIBLE : View.GONE);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {  //1.5秒之内重复点击无效
                    Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                    SpUtils.putBoolean(GuideActivity.this, "isNewStart", false);
                    startActivity(intent);
                    finish();
                }
            });
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
