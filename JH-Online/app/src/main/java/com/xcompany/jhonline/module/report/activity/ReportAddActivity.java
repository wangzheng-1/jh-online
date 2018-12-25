package com.xcompany.jhonline.module.report.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xcompany.jhonline.R;
import com.xcompany.jhonline.base.BaseActivity;
import com.xcompany.jhonline.model.report.MediaBaseBean;
import com.xcompany.jhonline.model.report.MediaBaseBeanSerial;
import com.xcompany.jhonline.utils.FileUtil;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import cn.bingoogolapple.photopicker.widget.BGASortableNinePhotoLayout;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static com.cjt2325.cameralibrary.JCameraView.BUTTON_STATE_BOTH;
import static com.cjt2325.cameralibrary.JCameraView.BUTTON_STATE_ONLY_CAPTURE;
import static com.xcompany.jhonline.module.report.activity.MediaTakeActivity.TAKE_MEDIA_TYPE;
import static com.xcompany.jhonline.module.report.activity.PhotoSelectActivity.EXTRA_SELECTED_PHOTOS;
import static com.xcompany.jhonline.module.report.activity.PhotoSelectActivity.IMAGE_NUM;
import static com.xcompany.jhonline.module.report.activity.PhotoSelectActivity.SELECT_MEDIA_TYPE;
import static com.xcompany.jhonline.module.report.fragment.ReportFragment.SELECT_TYPE;
import static com.xcompany.jhonline.module.report.activity.ImagePreviewActivity.INIT_SELECT_POSITION;

public class ReportAddActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks, BGASortableNinePhotoLayout.Delegate {


    private static final int PRC_PHOTO_PICKER = 1;

    /**
     * 选择照片视频
     */
    private static final int RC_CHOOSE_PHOTO = 1;

    /**
     * 选择照片预览
     */
    private static final int RC_PHOTO_PREVIEW = 2;

    /**
     * 选择拍摄
     */
    private static final int RC_MEDIA_TAKE = 3;

    /**
     * 选择拍摄
     */
    private static final int RC_VIDEO_PREVIEW = 4;


    private static final String EXTRA_MOMENT = "EXTRA_MOMENT";
    @BindView(R.id.backHomeLayout)
    LinearLayout backHomeLayout;
    @BindView(R.id.mineReportContent)
    EditText mineReportContent;
    @BindView(R.id.hintContentLayout)
    TextView hintContentLayout;
    @BindView(R.id.snpl_moment_add_photos)
    BGASortableNinePhotoLayout mPhotosSnpl;
    @BindView(R.id.videoAddLayout)
    FrameLayout videoAddLayout;
    @BindView(R.id.videoAddImage)
    ImageView videoAddImage;
    @BindView(R.id.videoPlayImage)
    ImageView videoPlayImage;

    private int selectMediaType = 0; //选择流媒体类型  0，默认都可选 1、照片 2、视频


    private int fromSelectType = 1;  // 1 拍摄  2，选择相册


    private  MediaBaseBean videoBaseBean = null; //所拍的视频。


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_add);
        ButterKnife.bind(this);
        fromSelectType = getIntent().getIntExtra(SELECT_TYPE,1);
        mPhotosSnpl = findViewById(R.id.snpl_moment_add_photos);
        initNineSelectView();
        initLayoutBySelectType();
        enterSelectMediaOrTake();
    }

    /**
     * 根据用户所选为拍摄还是选择相册进入不通的页面
     */
    private void enterSelectMediaOrTake(){
        if(fromSelectType == 1){
            Intent intent = new Intent(ReportAddActivity.this, MediaTakeActivity.class);
            intent.putExtra(TAKE_MEDIA_TYPE,BUTTON_STATE_BOTH);
            startActivityForResult(intent,RC_MEDIA_TAKE);
        }
        else {
            Intent intent = new Intent(ReportAddActivity.this, PhotoSelectActivity.class);
            intent.putExtra(SELECT_MEDIA_TYPE,0);
            intent.putExtra(IMAGE_NUM,mPhotosSnpl.getMaxItemCount() - mPhotosSnpl.getItemCount());
            startActivityForResult(intent,RC_CHOOSE_PHOTO);
        }
    }

    /**
     * 根据当前选择流媒体的类型显示不同控件
     */
    private void initLayoutBySelectType() {
        //根据悬着类型加载不同的控件
        if (selectMediaType == 0 || selectMediaType == 1) {
            mPhotosSnpl.setVisibility(View.VISIBLE);
            videoAddLayout.setVisibility(View.GONE);
        }
        else {
            mPhotosSnpl.setVisibility(View.GONE);
            videoAddLayout.setVisibility(View.VISIBLE);
        }
    }

    private void initNineSelectView() {
        //可编辑
        mPhotosSnpl.setEditable(true);
        //显示加号
        mPhotosSnpl.setPlusEnable(true);
        //可拖拽排序
        mPhotosSnpl.setSortable(true);
        // 设置拖拽排序控件的代理
        mPhotosSnpl.setDelegate(this);
        //最多可选9张照片
        mPhotosSnpl.setMaxItemCount(9);
    }

    @Override
    public void onClickAddNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, ArrayList<String> models) {

        showBottomSheetDialog(sortableNinePhotoLayout);
    }

    @Override
    public void onClickDeleteNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        mPhotosSnpl.removeItem(position);
    }

    //照片预览
    @Override
    public void onClickNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        Intent intent = new Intent(ReportAddActivity.this, ImagePreviewActivity.class);
        intent.putExtra(INIT_SELECT_POSITION,position);
        intent.putExtra(EXTRA_SELECTED_PHOTOS,(Serializable) models);
        startActivityForResult(intent,RC_PHOTO_PREVIEW);
    }

    @Override
    public void onNinePhotoItemExchanged(BGASortableNinePhotoLayout sortableNinePhotoLayout, int fromPosition, int toPosition, ArrayList<String> models) {
        Toast.makeText(this, "排序发生变化", Toast.LENGTH_SHORT).show();
    }

    @AfterPermissionGranted(PRC_PHOTO_PICKER)
    private void choicePhotoWrapper() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话就没有拍照功能
            File takePhotoDir = new File(Environment.getExternalStorageDirectory(), "BGAPhotoPickerTakePhoto");

            Intent photoPickerIntent = new BGAPhotoPickerActivity.IntentBuilder(this)
                    .cameraFileDir(takePhotoDir) // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话则不开启图库里的拍照功能
                    .maxChooseCount(mPhotosSnpl.getMaxItemCount() - mPhotosSnpl.getItemCount()) // 图片选择张数的最大值
                    .selectedPhotos(null) // 当前已选中的图片路径集合
                    .pauseOnScroll(false) // 滚动列表时是否暂停加载图片
                    .build();
            startActivityForResult(photoPickerIntent, RC_CHOOSE_PHOTO);
        } else {
            EasyPermissions.requestPermissions(this, "图片选择需要以下权限:\n\n1.访问设备上的照片\n\n2.拍照", PRC_PHOTO_PICKER, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (requestCode == PRC_PHOTO_PICKER) {
            Toast.makeText(this, "您拒绝了「图片选择」所需要的相关权限!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //选择视频图片页面返回
        if (resultCode == RESULT_OK && requestCode == RC_CHOOSE_PHOTO) {
            handleMediaSelectAndPreview(data);
        }
        else if (resultCode == RESULT_OK && requestCode == RC_PHOTO_PREVIEW) {
            MediaBaseBeanSerial result = (MediaBaseBeanSerial)data.getSerializableExtra(EXTRA_SELECTED_PHOTOS);
            List<MediaBaseBean> mediaBaseBeanList = result.getMediaBaseBeanList();
            ArrayList<String> list = new ArrayList();
            if(mediaBaseBeanList != null || mediaBaseBeanList.size() > 0){
                for(MediaBaseBean mediaBaseBean : mediaBaseBeanList){
                    list.add(mediaBaseBean.getUrl());
                }
            }
            mPhotosSnpl.setData(list);
        }
        else if (resultCode == RESULT_OK && requestCode == RC_MEDIA_TAKE) {
            handleMediaSelectAndPreview(data);

        }
        else if (resultCode == RESULT_OK && requestCode == RC_VIDEO_PREVIEW) {
            handleMediaSelectAndPreview(data);
        }
    }

    /**
     * 视频预览  视频图片选择 拍摄返回时处理
     */
    private void handleMediaSelectAndPreview(Intent data){
        videoBaseBean = null;
        MediaBaseBeanSerial result = (MediaBaseBeanSerial)data.getSerializableExtra(EXTRA_SELECTED_PHOTOS);
        List<MediaBaseBean> mediaBaseBeanList = result.getMediaBaseBeanList();
        //选择的视频
        if(mediaBaseBeanList != null && mediaBaseBeanList.size() == 1 && mediaBaseBeanList.get(0).isvideo()){
            videoBaseBean = mediaBaseBeanList.get(0);
            mPhotosSnpl.setVisibility(View.GONE);
            videoAddLayout.setVisibility(View.VISIBLE);
            hintContentLayout.setText("最多可选择一个视频");
            Bitmap bitmap = FileUtil.getVideoThumbnail(mediaBaseBeanList.get(0).getUrl());
            if(bitmap != null){
                videoAddImage.setImageBitmap(bitmap);
            }
        }
        else{
            mPhotosSnpl.setVisibility(View.VISIBLE);
            videoAddLayout.setVisibility(View.GONE);
            hintContentLayout.setText("最多可选择9张图片");
            ArrayList<String> list = new ArrayList();
            if(mediaBaseBeanList == null || mediaBaseBeanList.size() == 0){
                return;
            }
            for(MediaBaseBean mediaBaseBean : mediaBaseBeanList){
                list.add(mediaBaseBean.getUrl());
            }
            mPhotosSnpl.addMoreData(list);
        }
    }

    @OnClick({R.id.snpl_moment_add_photos, R.id.videoAddLayout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.videoAddLayout:
                if(videoBaseBean != null){
                    Intent intent = new Intent(ReportAddActivity.this, VideoPreviewActivity.class);
                    intent.putExtra("videopath",videoBaseBean.getUrl());
                    startActivityForResult(intent,RC_VIDEO_PREVIEW);
                }
                break;
        }
    }


    BottomSheetDialog bottomSheet;
    private void initBottomSheet(BGASortableNinePhotoLayout sortableNinePhotoLayout){
        if(bottomSheet == null){
            bottomSheet = new BottomSheetDialog(sortableNinePhotoLayout.getContext());//实例化BottomSheetDialog
            bottomSheet.setCancelable(true);//设置点击外部是否可以取消
            View bottomReportMenu = sortableNinePhotoLayout.inflate(sortableNinePhotoLayout.getContext(), R.layout.fragment_report_bottom_pop_menu,null);
            bottomReportMenu.findViewById(R.id.reportTitleText).setVisibility(View.GONE);
            bottomReportMenu.findViewById(R.id.takePhotoLayout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(bottomSheet != null && bottomSheet.isShowing()){
                        bottomSheet.dismiss();
                    }
                    Intent intent = new Intent(ReportAddActivity.this, MediaTakeActivity.class);
                    if(mPhotosSnpl.getItemCount() >0) {
                        intent.putExtra(TAKE_MEDIA_TYPE, BUTTON_STATE_ONLY_CAPTURE);
                    }
                    else{
                        intent.putExtra(TAKE_MEDIA_TYPE,BUTTON_STATE_BOTH);
                    }
                    startActivityForResult(intent,RC_MEDIA_TAKE);


                }
            });
            bottomReportMenu.findViewById(R.id.selectAlbumLayout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(bottomSheet != null && bottomSheet.isShowing()){
                        bottomSheet.dismiss();
                    }
                    Intent intent = new Intent(ReportAddActivity.this, PhotoSelectActivity.class);
                    if (mPhotosSnpl.getItemCount() >0){
                        intent.putExtra(SELECT_MEDIA_TYPE,1);
                    }
                    else{
                        intent.putExtra(SELECT_MEDIA_TYPE,0);

                    }
                    intent.putExtra(IMAGE_NUM,mPhotosSnpl.getMaxItemCount() - mPhotosSnpl.getItemCount());
                    startActivityForResult(intent,RC_CHOOSE_PHOTO);
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
    private void showBottomSheetDialog(BGASortableNinePhotoLayout sortableNinePhotoLayout){
        if(bottomSheet == null){
            initBottomSheet(sortableNinePhotoLayout);
        }
        if(bottomSheet != null && bottomSheet.isShowing()){
            bottomSheet.dismiss();
        }
        else if(bottomSheet != null) {
            bottomSheet.show();
        }
    }

}


