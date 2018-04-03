package com.gongyou.worker.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.gongyou.worker.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.view.Gravity.BOTTOM;

/**
 * Author: TFJ
 * Date: 2017/7/12.
 */

public class ImageFileUtils {

    public Activity mActivity;
    public View layout;

    public PopupWindow popupWindow;

    public ImageFileUtils(Activity activity, View layout) {
        mActivity = activity;
        this.layout = layout;
    }

    /**
     *
     * @param requestCode   请求码
     * @param viewId         被点击控件id
     */
    public void showPopupWindow(final int requestCode, final int viewId) {
        popupWindow = new PopupWindow(mActivity);
        View view = LayoutInflater.from(mActivity).inflate(R.layout.pop_take_pic, null);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(view);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(layout, BOTTOM, 0, 0);
//        LogUtil.i("AuthenticationActivity", "弹窗···" + popupWindow.isShowing());
        // 设置背景颜色变暗
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = 0.3f;
        mActivity.getWindow().setAttributes(lp);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
                lp.alpha = 1f;
                mActivity.getWindow().setAttributes(lp);
            }
        });
        TextView tvPhone = ((TextView) view.findViewById(R.id.tv_phone));
        TextView tvCarmer = ((TextView) view.findViewById(R.id.tv_carmer));
        TextView tvCancell = ((TextView) view.findViewById(R.id.tv_cancell));
        tvCarmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera_2(requestCode, viewId);
                popupWindow.dismiss();
            }
        });
        tvPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gallery(requestCode,  viewId);
                popupWindow.dismiss();
            }
        });
        tvCancell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }


    /**
     * 通过 uri seletion选择来获取图片的真实uri
     *
     * @param uri
     * @param seletion
     * @return
     */
    public String getImagePath(Uri uri, String seletion) {
        String path = null;
        if (uri != null) {
            Cursor cursor = mActivity.getContentResolver().query(uri, null, seletion, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                }
                cursor.close();
            }
        }
        return path;
    }

    public static String getRealFilePath(final Context context, final Uri uri ) {
        if ( null == uri ) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if ( scheme == null )
            data = uri.getPath();
        else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
            data = uri.getPath();
        } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
            Cursor cursor = context.getContentResolver().query( uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null );
            if ( null != cursor ) {
                if ( cursor.moveToFirst() ) {
                    int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
                    if ( index > -1 ) {
                        data = cursor.getString( index );
                    }
                }
                cursor.close();
            }
        }
        return data;
    }


    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".jpg";
    }


    /*
     * 从相册获取
     */
    public static boolean flag = false;        //拍照false,相机true.

    public void gallery(int requestCode, int viewId) {
        // 激活系统图库，选择一张图片
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
//        intent.putExtra("viewId",viewId);
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_GALLERY
        mActivity.startActivityForResult(intent, requestCode);
    }

    public String mPhotoPath;
    public File mPhotoFile;

    // 拍照后存储并显示图片
    private void openCamera_2(int requestCode, int viewId) {
        flag = true;
        try {
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");//开始拍照
            mPhotoPath = getSDPath() + "/" + getPhotoFileName();//设置图片文件路径，getSDPath()和getPhotoFileName()具体实现在下面

            mPhotoFile = new File(mPhotoPath);
            if (!mPhotoFile.exists()) {
                mPhotoFile.createNewFile();//创建新文件
            }
//            intent.putExtra(MediaStore.EXTRA_OUTPUT,//Intent有了图片的信息
//                    Uri.fromFile(mPhotoFile));


            Uri photoUri = Uri.fromFile(mPhotoFile); // 传递路径
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);// 更改系统默认存储路径
//            intent.putExtra("viewId",viewId);
            mActivity.startActivityForResult(intent, requestCode);//跳转界面传回拍照所得数据
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED);   //判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        return sdDir.toString();

    }
}
