package com.xcompany.jhonline.module.report.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.xcompany.jhonline.R;
import com.xcompany.jhonline.base.BaseActivity;
import com.xcompany.jhonline.model.report.MediaBaseBean;
import com.xcompany.jhonline.model.report.MediaBaseBeanSerial;
import com.xcompany.jhonline.model.report.VideoMsg;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import static com.xcompany.jhonline.module.report.activity.PhotoSelectActivity.EXTRA_SELECTED_PHOTOS;


/**
 * Created by Administrator on 2017/4/14.
 */

public class VideoPreviewActivity extends BaseActivity {

    private RelativeLayout pop;
    private ImageView video_play,video_iv;
    private VideoView videoView;
    private MediaController controller;
    private String videopath;
    private LinearLayout back;
    private TextView tvcancle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videopreview);
        Intent intent = getIntent();
        videopath = intent.getStringExtra("videopath");
        pop = (RelativeLayout) findViewById(R.id.pop);
        video_play = (ImageView) findViewById(R.id.video_play);
        video_iv = (ImageView) findViewById(R.id.video_iv);
        videoView = (VideoView) findViewById(R.id.videoview);
        back= (LinearLayout) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Glide.with(VideoPreviewActivity.this).load(videopath).into(video_iv);
            }
        });
        tvcancle = (TextView) findViewById(R.id.tv_cancel);
        tvcancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheetDialog();
            }
        });
        controller = new MediaController(this);
        videoView.setMediaController(controller);
        videoView.setVideoPath(videopath);
        video_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop.setVisibility(View.GONE);
                videoView.start();
            }
        });
    }

    BottomSheetDialog bottomSheet;
    private void initBottomSheet(){
        if(bottomSheet == null){
            bottomSheet = new BottomSheetDialog(this);//实例化BottomSheetDialog
            bottomSheet.setCancelable(true);//设置点击外部是否可以取消
            View bottomReportMenu = this.getLayoutInflater().inflate(R.layout.activity_video_preview_delete,null);
            //删除确认
            bottomReportMenu.findViewById(R.id.deleteConfirmLayout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(bottomSheet != null && bottomSheet.isShowing()){
                        bottomSheet.dismiss();
                    }
                    MediaBaseBeanSerial mediaBaseBeanSerial = new MediaBaseBeanSerial();
                    List<MediaBaseBean> mediaBaseBeanList = new ArrayList<>();
                    mediaBaseBeanSerial.setMediaBaseBeanList(mediaBaseBeanList);
                    Intent intent1 = new Intent();
                    intent1.putExtra(EXTRA_SELECTED_PHOTOS, mediaBaseBeanSerial);
                    setResult(Activity.RESULT_OK, intent1);
                    finish();
                }
            });
            bottomReportMenu.findViewById(R.id.cancelText).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(bottomSheet != null && bottomSheet.isShowing()){
                        bottomSheet.dismiss();
                    }
                }
            });
            bottomSheet.setContentView(bottomReportMenu);
        }
    }
    private void showBottomSheetDialog(){
        if(bottomSheet == null){
            initBottomSheet();
        }
        if(bottomSheet.isShowing()){
            bottomSheet.dismiss();
        }
        else {
            bottomSheet.show();
        }
    }
}
