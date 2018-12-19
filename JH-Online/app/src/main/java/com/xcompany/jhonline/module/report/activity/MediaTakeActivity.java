package com.xcompany.jhonline.module.report.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.cjt2325.cameralibrary.JCameraView;
import com.cjt2325.cameralibrary.lisenter.ErrorLisenter;
import com.cjt2325.cameralibrary.lisenter.JCameraLisenter;
import com.xcompany.jhonline.R;
import com.xcompany.jhonline.model.report.MediaBaseBean;
import com.xcompany.jhonline.model.report.MediaBaseBeanSerial;
import com.xcompany.jhonline.utils.FileUtil;
import com.xcompany.jhonline.utils.T;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.cjt2325.cameralibrary.JCameraView.BUTTON_STATE_BOTH;
import static com.xcompany.jhonline.module.report.activity.PhotoSelectActivity.EXTRA_SELECTED_PHOTOS;


public class MediaTakeActivity extends AppCompatActivity {

    public static final String TAKE_MEDIA_TYPE = "takeMediaType";


    private JCameraView jCameraView;

    String saveMediaPath = Environment.getExternalStorageDirectory().getPath() + File.separator + "JhOnline";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_take);
        setting();
        jCameraView = (JCameraView) findViewById(R.id.jcameraview);

        //设置视频保存路径
        jCameraView.setSaveVideoPath(saveMediaPath);

        int type = getIntent().getIntExtra(TAKE_MEDIA_TYPE,BUTTON_STATE_BOTH);
        //设置只能录像或只能拍照或两种都可以（默认两种都可以）
        jCameraView.setFeatures(type);

        //设置视频质量
        jCameraView.setMediaQuality(JCameraView.MEDIA_QUALITY_MIDDLE);

        //JCameraView监听
        jCameraView.setErrorLisenter(new ErrorLisenter() {
            @Override
            public void onError() {
                T.showToast(MediaTakeActivity.this,"相机打开失败");
                MediaTakeActivity.this.finish();

            }

            @Override
            public void AudioPermissionError() {
                //没有录取权限回调
                T.showToast(MediaTakeActivity.this,"没有打开相机权限");
                MediaTakeActivity.this.finish();

            }
        });

        jCameraView.setJCameraLisenter(new JCameraLisenter() {
            @Override
            public void captureSuccess(Bitmap bitmap) {
                String filepath = getUri(bitmap);
                if (filepath == null){
                    MediaTakeActivity.this.finish();
                }
                else {
                    returnResult(filepath,false);
                }
            }

            @Override
            public void recordSuccess(String url, Bitmap firstFrame) {
                returnResult(url,true);
            }
            @Override
            public void quit() {
                MediaTakeActivity.this.finish();
            }
        });
    }

    private void setting() {
        if (Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        } else {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(option);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        jCameraView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        jCameraView.onPause();
    }

    private String getUri(Bitmap bitmap){
        String filepath =  saveMediaPath + File.separator + System.currentTimeMillis() + ".png";
        boolean success = FileUtil.saveBitmap(bitmap,filepath);
        if(success){
            return filepath;
        }
        return null;
    }
    private void returnResult(String url,boolean isVideo){

        if(url == null){
            MediaTakeActivity.this.finish();
        }
        List<MediaBaseBean> mediaBaseBeanList = new ArrayList<>();
        MediaBaseBean mediaBaseBean = new MediaBaseBean();
        mediaBaseBean.setIsvideo(isVideo);
        mediaBaseBean.setUrl(url);
        mediaBaseBeanList.add(mediaBaseBean);
        MediaBaseBeanSerial mediaBaseBeanSerial = new MediaBaseBeanSerial();
        mediaBaseBeanSerial.setMediaBaseBeanList(mediaBaseBeanList);

        Intent intent1 = new Intent();
        intent1.putExtra(EXTRA_SELECTED_PHOTOS, mediaBaseBeanSerial);
        setResult(Activity.RESULT_OK, intent1);
        finish();
    }

}
