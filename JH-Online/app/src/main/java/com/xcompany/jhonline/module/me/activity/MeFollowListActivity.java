package com.xcompany.jhonline.module.me.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xcompany.jhonline.R;
import com.xcompany.jhonline.base.XListViewActivity;
import com.xcompany.jhonline.model.base.Model;
import com.xcompany.jhonline.model.report.Moment;
import com.xcompany.jhonline.module.report.activity.PhotoSelectActivity;
import com.xcompany.jhonline.widget.MyBGANinePhotoLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPreviewActivity;
import cn.bingoogolapple.photopicker.imageloader.BGARVOnScrollListener;
import cn.bingoogolapple.photopicker.widget.BGAImageView;
import cn.jzvd.JZVideoPlayerStandard;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MeFollowListActivity extends XListViewActivity implements EasyPermissions.PermissionCallbacks,MyBGANinePhotoLayout.Delegate {

    private static final int PRC_PHOTO_PREVIEW = 1;

    private static final int RC_ADD_MOMENT = 1;
    private static final int MEDIA_SELECT = 2;

    private MomentAdapter meFollowAdapter;

    private static final String EXTRA_MOMENT = "EXTRA_MOMENT";


    @Override
    public void onAppCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_me_follow_list);
        xRecyclerView = this.findViewById(R.id.followListView);
    }

    @Override
    public void getDataItems(int start, int limit, Callback callback) {
        List<Model> moments = new ArrayList<>();
        moments.add(new Moment("1张网络图片", "http://gslb.miaopai.com/stream/ed5HCfnhovu3tyIQAiv60Q__.mp4"));
        moments.add(new Moment("2张网络图片", new ArrayList<>(Arrays.asList("http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered2.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered3.png"))));
        moments.add(new Moment("9张网络图片", new ArrayList<>(Arrays.asList("http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered11.png",
                "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered12.png",
                "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered13.png",
                "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered14.png",
                "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered15.png",
                "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered16.png",
                "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered17.png",
                "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered18.png",
                "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered19.png"))));
        moments.add(new Moment("5张网络图片", new ArrayList<>(Arrays.asList("http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered11.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered12.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered13.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered14.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered15.png"))));
        moments.add(new Moment("3张网络图片", new ArrayList<>(Arrays.asList("http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered4.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered5.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered6.png"))));
        moments.add(new Moment("8张网络图片", new ArrayList<>(Arrays.asList("http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered11.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered12.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered13.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered14.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered15.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered16.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered17.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered18.png"))));
        moments.add(new Moment("4张网络图片", new ArrayList<>(Arrays.asList("http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered7.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered8.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered9.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered10.png"))));
        moments.add(new Moment("2张网络图片", new ArrayList<>(Arrays.asList("http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered2.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered3.png"))));
        moments.add(new Moment("3张网络图片", new ArrayList<>(Arrays.asList("http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered4.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered5.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered6.png"))));
        moments.add(new Moment("4张网络图片", new ArrayList<>(Arrays.asList("http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered7.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered8.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered9.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered10.png"))));
        moments.add(new Moment("9张网络图片", new ArrayList<>(Arrays.asList("http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered11.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered12.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered13.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered14.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered15.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered16.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered17.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered18.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered19.png"))));
        moments.add(new Moment("1张网络图片", new ArrayList<>(Arrays.asList("http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered1.png"))));
        moments.add(new Moment("5张网络图片", new ArrayList<>(Arrays.asList("http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered11.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered12.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered13.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered14.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered15.png"))));
        moments.add(new Moment("6张网络图片", new ArrayList<>(Arrays.asList("http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered11.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered12.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered13.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered14.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered15.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered16.png"))));
        moments.add(new Moment("7张网络图片", new ArrayList<>(Arrays.asList("http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered11.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered12.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered13.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered14.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered15.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered16.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered17.png"))));
        moments.add(new Moment("8张网络图片", new ArrayList<>(Arrays.asList("http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered11.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered12.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered13.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered14.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered15.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered16.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered17.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered18.png"))));

        callback.setDataItems(moments);

    }

    // 初始化适配器
    public void initAdapter() {

        meFollowAdapter = new MomentAdapter(xRecyclerView);
        xRecyclerView.addOnScrollListener(new BGARVOnScrollListener(this));
        xRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        xRecyclerView.setAdapter(meFollowAdapter);
        xRecyclerView.setLoadingListener(this);
        meFollowAdapter.setData(SourceDateList);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == RC_ADD_MOMENT) {
            meFollowAdapter.addFirstItem((Moment) data.getParcelableExtra(EXTRA_MOMENT));
            xRecyclerView.smoothScrollToPosition(0);
        }

        if(resultCode== Activity.RESULT_OK && requestCode == MEDIA_SELECT){
            List<String> list = (List<String>) data.getSerializableExtra(PhotoSelectActivity.DATA);
        }
    }


    private class MomentAdapter extends BGARecyclerViewAdapter<Model> {

        public MomentAdapter(RecyclerView recyclerView) {
            super(recyclerView, R.layout.fragment_report_item);
        }

        @Override
        protected void fillData(BGAViewHolderHelper helper, int position, Model model) {

            Moment moment = (Moment) model;
            BGAImageView reportHeadImage = helper.getView(R.id.reportHeadImage);
            TextView userNameText = helper.getView(R.id.userNameText);
            TextView mineFellowText = helper.getView(R.id.mineFellowText);
            TextView reportContentText = helper.getView(R.id.reportContentText);
            MyBGANinePhotoLayout reportNineImage = helper.getView(R.id.reportNineImage);

            TextView thumbText = helper.getView(R.id.thumbText);
            TextView commentText = helper.getView(R.id.commentText);
            TextView commentListView = helper.getView(R.id.commentListView);

            if (TextUtils.isEmpty(moment.content)) {
                reportContentText.setVisibility(View.GONE);
            } else {
                reportContentText.setVisibility(View.VISIBLE);
                reportContentText.setText(moment.content);
            }

            JZVideoPlayerStandard reportNineVideo = helper.getView(R.id.reportNineVideo);
            if(TextUtils.isEmpty(moment.videoUrl)){
                reportNineVideo.setVisibility(View.GONE);
            }
            else{
                reportNineVideo.setVisibility(View.VISIBLE);
                reportNineVideo.setUp("http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4"
                        , JZVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "嫂子闭眼睛");
            }

            if(moment.photos != null && moment.photos.size()>0 ){
                reportNineImage.setDelegate(MeFollowListActivity.this);
                reportNineImage.setData(moment.photos);
                reportNineImage.setVisibility(View.VISIBLE);
            }
            else {
                reportNineImage.setVisibility(View.GONE);
            }


        }
    }
    private MyBGANinePhotoLayout mCurrentClickNpl;

    @Override
    public void onClickNinePhotoItem(MyBGANinePhotoLayout ninePhotoLayout, View view, int position, String model, List<String> models) {
        mCurrentClickNpl = ninePhotoLayout;
        photoPreviewWrapper();
    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (requestCode == PRC_PHOTO_PREVIEW) {
            Toast.makeText(MeFollowListActivity.this, "您拒绝了「图片预览」所需要的相关权限!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 图片预览，兼容6.0动态权限
     */
    @AfterPermissionGranted(PRC_PHOTO_PREVIEW)
    private void photoPreviewWrapper() {
        if (mCurrentClickNpl == null) {
            return;
        }

        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(MeFollowListActivity.this, perms)) {
            File downloadDir = new File(Environment.getExternalStorageDirectory(), "BGAPhotoPickerDownload");
            BGAPhotoPreviewActivity.IntentBuilder photoPreviewIntentBuilder = new BGAPhotoPreviewActivity.IntentBuilder(this)
                    .saveImgDir(downloadDir); // 保存图片的目录，如果传 null，则没有保存图片功能

            if (mCurrentClickNpl.getItemCount() == 1) {
                // 预览单张图片
                photoPreviewIntentBuilder.previewPhoto(mCurrentClickNpl.getCurrentClickItem());
            } else if (mCurrentClickNpl.getItemCount() > 1) {
                // 预览多张图片
                photoPreviewIntentBuilder.previewPhotos(mCurrentClickNpl.getData())
                        .currentPosition(mCurrentClickNpl.getCurrentClickItemPosition()); // 当前预览图片的索引
            }
            startActivity(photoPreviewIntentBuilder.build());
        } else {
            EasyPermissions.requestPermissions(this, "图片预览需要以下权限:\n\n1.访问设备上的照片", PRC_PHOTO_PREVIEW, perms);
        }
    }
}
