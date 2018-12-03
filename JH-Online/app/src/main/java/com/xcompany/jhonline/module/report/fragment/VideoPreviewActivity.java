package com.xcompany.jhonline.module.report.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.xcompany.jhonline.R;
import com.xcompany.jhonline.base.AbstractActivity;
import com.xcompany.jhonline.base.BaseActivity;
import com.xcompany.jhonline.model.report.VideoMsg;

import org.greenrobot.eventbus.EventBus;


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
                EventBus.getDefault().post(new VideoMsg(videopath));
                finish();
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
}
