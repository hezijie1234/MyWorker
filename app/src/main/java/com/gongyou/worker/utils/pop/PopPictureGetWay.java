package com.gongyou.worker.utils.pop;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.baidu.ocr.ui.camera.CameraActivity;
import com.gongyou.worker.R;
import com.gongyou.worker.SampleApplicationLike;
import com.gongyou.worker.utils.FileUtil;
import com.gongyou.worker.utils.ImageFileUtils;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static android.view.Gravity.BOTTOM;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static com.gongyou.worker.utils.pop.PopPictureGetWay.PhotoState.SYSTEMCAMERA;


/**
 * Created by Poison
 * Created on 2018/1/11.
 */

public class PopPictureGetWay {
    private Activity activity;
    private View parent;
    //表示跳转的是自定义相机还是系统相机，true为自定义相机，false为系统相机
    private boolean isSelfCamera;
    PhotoState mPhotostate;
    public enum PhotoState{
        CUSTOMCAMERA,
        SYSTEMCAMERA,
        IDCARDFRONT,
        IDCARDBACK,
        BUSINESSLICENSE
    }
    public PopPictureGetWay(Activity activity, View parent) {
        this.activity = activity;
        this.parent = parent;
        mPhotostate=SYSTEMCAMERA;
    }

    public PopPictureGetWay(Activity activity, View parent, PhotoState photostate) {
        this.activity = activity;
        this.parent = parent;
        mPhotostate = photostate;
    }

    private PopupWindow popupWindow;

    public PopupWindow createPop(final int requestCode) {
        popupWindow = new PopupWindow(activity);
        View view = View.inflate(activity, R.layout.pop_pic_getway, null);
        popupWindow.setWidth(MATCH_PARENT);
        popupWindow.setHeight(WRAP_CONTENT);
        popupWindow.setContentView(view);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindow.setFocusable(false);
        popupWindow.showAtLocation(this.parent, BOTTOM, 0, 0);
        final Window window = activity.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.alpha = 0.5f;
        window.setAttributes(layoutParams);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams layoutParams1 = window.getAttributes();
                layoutParams1.alpha = 1f;
                window.setAttributes(layoutParams1);
            }
        });
        TextView takePhoto = (TextView) view.findViewById(R.id.tv_take_photo);
        TextView album = (TextView) view.findViewById(R.id.tv_open_album);
        TextView cancel = (TextView) view.findViewById(R.id.tv_cancel);
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //拍照
//            if (isSelfCamera) {
//                activity.startActivityForResult(new Intent(MyApp.getContext(), TakePhotoActivity.class), requestCode);
//            } else {
//                openCamera(requestCode);
//            }
                switch (mPhotostate){
                    case IDCARDFRONT: //身份证正面
                        Intent intent = new Intent(activity, CameraActivity.class);
                        intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                                FileUtil.getSaveFile(SampleApplicationLike.getContext()).getAbsolutePath());
                        intent.putExtra(CameraActivity.KEY_NATIVE_ENABLE,
                                true);
                        // KEY_NATIVE_MANUAL设置了之后CameraActivity中不再自动初始化和释放模型
                        // 请手动使用CameraNativeHelper初始化和释放模型
                        // 推荐这样做，可以避免一些activity切换导致的不必要的异常
                        intent.putExtra(CameraActivity.KEY_NATIVE_MANUAL,
                                true);
                        intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_FRONT);
                        activity.startActivityForResult(intent, requestCode);
                        break;
                    case IDCARDBACK:  //身份证反面
                        Intent intent1 = new Intent(activity, CameraActivity.class);
                        intent1.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                                FileUtil.getSaveFile(SampleApplicationLike.getContext()).getAbsolutePath());
                        intent1.putExtra(CameraActivity.KEY_NATIVE_ENABLE,
                                true);
                        // KEY_NATIVE_MANUAL设置了之后CameraActivity中不再自动初始化和释放模型
                        // 请手动使用CameraNativeHelper初始化和释放模型
                        // 推荐这样做，可以避免一些activity切换导致的不必要的异常
                        intent1.putExtra(CameraActivity.KEY_NATIVE_MANUAL,
                                true);
                        intent1.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_BACK);
                        activity.startActivityForResult(intent1, requestCode);
                        break;
                    case SYSTEMCAMERA: //系统拍照
                        openCamera(requestCode);
                        break;
                    case BUSINESSLICENSE: //营业执照
                        openCamera(requestCode);
                        break;
                }
                popupWindow.dismiss();
            }
        });

        album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开相册
//            openCamera(requestCode);
                gallery(requestCode);
                popupWindow.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (onWayChoose != null) {
//                onWayChoose.doCancel();
//            }
                    popupWindow.dismiss();
            }
        });
        return popupWindow;
    }

    public void imageResult(int requestCode, Intent data, final ImageView imageView, final OnImageCompress compress) {
        this.onImageCompress = compress;
        if (data != null) {    //相册获取
            Uri uri = data.getData();
            String fileName = ImageFileUtils.getRealFilePath(activity, uri);
            File file = new File(fileName);
            Luban.with(activity).load(file).setCompressListener(new OnCompressListener() {
                @Override
                public void onStart() {

                }

                @Override
                public void onSuccess(File file) {
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getPath(), null);
                    imageView.setImageBitmap(bitmap);
                    onImageCompress.compressSuccess(file);
                }

                @Override
                public void onError(Throwable e) {
                    onImageCompress = compress;
                    onImageCompress.compressFail(e);

                }
            }).launch();

        } else {             //普通拍照
            Luban.with(activity).load(mPhotoFile).setCompressListener(new OnCompressListener() {
                @Override
                public void onStart() {

                }

                @Override
                public void onSuccess(File file) {
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
                    imageView.setImageBitmap(bitmap);
                    onImageCompress.compressSuccess(file);
                }

                @Override
                public void onError(Throwable e) {
                    onImageCompress = compress;
                    onImageCompress.compressFail(e);
                }
            }).launch();

        }

//        switch (requestCode) {
//            case CARD_UPLOAD:
//                if (data!=null){    //相册获取
//
//                }else {             //拍照
//
//                }
//                break;
//            case OPEN_ALLOW_UPLOAD:
//
//                break;
//        }
    }


    public interface OnImageCompress {
        void compressSuccess(File file);

        void compressFail(Throwable e);
    }

    public OnImageCompress onImageCompress;

//    public void setOnImageCompress(OnImageCompress l) {
//        this.onImageCompress = l;
//    }


    public static boolean flag = false;        //拍照false,相机true.
    public String mPhotoPath;
    public File mPhotoFile;

    // 拍照后存储并显示图片
    private void openCamera(int requestCode) {
        flag = true;
        try {
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");//开始拍照
            mPhotoPath = getSDPath() + "/" + getPhotoFileName();//设置图片文件路径，getSDPath()和getPhotoFileName()具体实现在下面
            mPhotoFile = new File(mPhotoPath);
            if (!mPhotoFile.exists()) {
                mPhotoFile.createNewFile();//创建新文件
            }
            Uri photoUri = Uri.fromFile(mPhotoFile); // 传递路径
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);// 更改系统默认存储路径
            activity.startActivityForResult(intent, requestCode);//跳转界面传回拍照所得数据
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".jpg";
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


    public void gallery(int requestCode) {
        // 激活系统图库，选择一张图片
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
//        intent.putExtra("viewId",viewId);
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_GALLERY
        activity.startActivityForResult(intent, requestCode);
    }


    private String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }
}
