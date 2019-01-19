package com.xcompany.jhonline.module.report.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.xcompany.jhonline.R;
import com.xcompany.jhonline.base.ListBaseFragment;
import com.xcompany.jhonline.model.base.Model;
import com.xcompany.jhonline.model.report.Comment;
import com.xcompany.jhonline.model.report.Fellow;
import com.xcompany.jhonline.model.report.Moment;
import com.xcompany.jhonline.module.report.activity.PhotoSelectActivity;
import com.xcompany.jhonline.module.report.activity.ReportAddActivity;
import com.xcompany.jhonline.module.report.adapter.CommentAdapter;
import com.xcompany.jhonline.network.JHCallback;
import com.xcompany.jhonline.network.JHResponse;
import com.xcompany.jhonline.network.UserService;
import com.xcompany.jhonline.utils.ReleaseConfig;
import com.xcompany.jhonline.utils.StringUtil;
import com.xcompany.jhonline.utils.T;
import com.xcompany.jhonline.widget.MyBGANinePhotoLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPreviewActivity;
import cn.bingoogolapple.photopicker.imageloader.BGARVOnScrollListener;
import cn.bingoogolapple.photopicker.widget.BGAImageView;
import cn.jzvd.JZVideoPlayerStandard;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by xieliang on 2018/11/21 11:47
 */
public class ReportFragment extends ListBaseFragment implements EasyPermissions.PermissionCallbacks, MyBGANinePhotoLayout.Delegate {


    private static final int PRC_PHOTO_PREVIEW = 1;

    private static final int RC_ADD_MOMENT = 1;
    private static final int MEDIA_SELECT = 2;

    public static final String SELECT_TYPE = "type";

    public static final int REPORT_TAKE_REQUEST = 1;
    public static final int REPORT_SELECT_ALBUM_REQUEST = 2;
    @BindView(R.id.meShareText)
    TextView meShareText;
    @BindView(R.id.reportTitleText)
    TextView reportTitleText;
    @BindView(R.id.meReportTitleText)
    TextView meReportTitleText;

    private MyBGANinePhotoLayout mCurrentClickNpl;
    private MomentAdapter mMomentAdapter;
    private static final String EXTRA_MOMENT = "EXTRA_MOMENT";


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_report;
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    public void getDataItems(int page, Callback callback) {
        OkGo.<JHResponse<List<Moment>>>post(ReleaseConfig.baseUrl() + "Forum/appList?p=" + page)
                .tag(this)
                .params("uid", UserService.getInstance().getUid())
                .execute(new JHCallback<JHResponse<List<Moment>>>() {
                    @Override
                    public void onSuccess(Response<JHResponse<List<Moment>>> response) {
                        List<Moment> resultList = response.body().getMsg();
                        callback.setDataItems(resultList);
                    }

                    @Override
                    public void onError(Response<JHResponse<List<Moment>>> response) {
                        T.showToast(ReportFragment.this.getActivity(), response.getException().getMessage());
                        xRecyclerView.refreshComplete();
                    }
                });

    }

    // 初始化适配器
    protected void initAdapter() {

        xRecyclerView = mView.findViewById(R.id.recyclerList);
        mMomentAdapter = new MomentAdapter(xRecyclerView);
        xRecyclerView.addOnScrollListener(new BGARVOnScrollListener(this.getActivity()));
        xRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        xRecyclerView.setAdapter(mMomentAdapter);
        xRecyclerView.setLoadingListener(this);
        mMomentAdapter.setData(SourceDateList);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == RC_ADD_MOMENT) {
            mMomentAdapter.addFirstItem((Moment) data.getParcelableExtra(EXTRA_MOMENT));
            xRecyclerView.smoothScrollToPosition(0);
        }

        if (resultCode == Activity.RESULT_OK && requestCode == MEDIA_SELECT) {
            List<String> list = (List<String>) data.getSerializableExtra(PhotoSelectActivity.DATA);
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
        if (EasyPermissions.hasPermissions(this.getContext(), perms)) {
            File downloadDir = new File(Environment.getExternalStorageDirectory(), "BGAPhotoPickerDownload");
            BGAPhotoPreviewActivity.IntentBuilder photoPreviewIntentBuilder = new BGAPhotoPreviewActivity.IntentBuilder(this.getContext())
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
        if (requestCode == PRC_PHOTO_PREVIEW) {
            Toast.makeText(this.getContext(), "您拒绝了「图片预览」所需要的相关权限!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClickNinePhotoItem(MyBGANinePhotoLayout ninePhotoLayout, View view, int position, String model, List<String> models) {
        mCurrentClickNpl = ninePhotoLayout;
        photoPreviewWrapper();
    }

    @OnClick(R.id.meReportTitleText)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.meReportTitleText:
                showBottomSheetDialog();
                break;
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
            LinearLayout commentText = helper.getView(R.id.commentText);
            ListView commentListView = helper.getView(R.id.commentListView);

            if (TextUtils.isEmpty(moment.getBusiness())) {
                reportContentText.setVisibility(View.GONE);
            } else {
                reportContentText.setVisibility(View.VISIBLE);
                reportContentText.setText(moment.getBusiness());
            }

            JZVideoPlayerStandard reportNineVideo = helper.getView(R.id.reportNineVideo);
            if (!TextUtils.isEmpty(moment.getExt())
                    && ("mp4".equalsIgnoreCase(moment.getExt()))
                    && moment.getChoosefile() != null && moment.getChoosefile().size() > 0
                    ) {
                reportNineVideo.setVisibility(View.VISIBLE);
                reportNineVideo.setUp(ReleaseConfig.getInstance().getDisplayBaseUrl() + moment.getChoosefile().get(0)
                        , JZVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "");
            } else {
                reportNineVideo.setVisibility(View.GONE);
            }

            if (!TextUtils.isEmpty(moment.getExt())
                    && moment.getChoosefile() != null && moment.getChoosefile().size() > 0
                    && ("png".equalsIgnoreCase(moment.getExt()) || "bmp".equals(moment.getExt()) || "jpg".equalsIgnoreCase(moment.getExt()) || "jpeg".equalsIgnoreCase(moment.getExt()))) {
                reportNineImage.setVisibility(View.VISIBLE);
                reportNineImage.setDelegate(ReportFragment.this);
                ArrayList<String> imageList = new ArrayList<>();
                for (String imageUrl : moment.getChoosefile()) {
                    imageList.add(ReleaseConfig.getInstance().getDisplayBaseUrl() + imageUrl);
                }
                reportNineImage.setData(imageList);
            } else {
                reportNineImage.setVisibility(View.GONE);
            }
            thumbText.setText(moment.getGive());
            //关注
            Fellow fellow = moment.getGivelist();
            //已经关注或点赞
            if (fellow != null) {
                mineFellowText.setText("已关注");
                mineFellowText.setCompoundDrawables(null, null, null, null);
                mineFellowText.setPadding(0, 0, 0, 0);
                thumbText.setBackground(getResources().getDrawable(R.drawable.background_frame_all_corner_blue));
                thumbText.setTextColor(getResources().getColor(R.color.text_blue));

                Drawable unThumpDrawable = getResources().getDrawable(R.mipmap.click_thumbed);
                unThumpDrawable.setBounds(0, 0, unThumpDrawable.getMinimumWidth(), unThumpDrawable.getMinimumHeight());  // left, top, right, bottom
                thumbText.setCompoundDrawables(unThumpDrawable, null, null, null);


            } else {
                mineFellowText.setText("关注");

                Drawable unFellowDrawable = getResources().getDrawable(R.mipmap.unfollow_btn);
                unFellowDrawable.setBounds(0, 0, unFellowDrawable.getMinimumWidth(), unFellowDrawable.getMinimumHeight());  // left, top, right, bottom
                mineFellowText.setCompoundDrawables(unFellowDrawable, null, null, null);

                thumbText.setBackground(getResources().getDrawable(R.drawable.background_frame_all_corner_gray));
                thumbText.setTextColor(getResources().getColor(R.color.text_light_gray));
                Drawable thumpDrawable = getResources().getDrawable(R.mipmap.click_unthunmb);
                thumpDrawable.setBounds(0, 0, thumpDrawable.getMinimumWidth(), thumpDrawable.getMinimumHeight());  // left, top, right, bottom
                thumbText.setCompoundDrawables(thumpDrawable, null, null, null);

            }

            mineFellowText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (fellow != null) {
                        //取消关注
                        unFellow(moment.getGivelist().getId());
                    } else {
                        fellow(moment.getId());
                    }
                }
            });

            thumbText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (fellow != null) {
                        //取消关注
//                        unFellow(moment.getGivelist().get(0).getId());
                    } else {
                        thumpUp(moment.getId());
                    }
                }
            });
            //评论
            List<Comment> commentList = moment.getMake();
            if (commentList != null && commentList.size() > 0) {
                CommentAdapter commentAdapter = new CommentAdapter(ReportFragment.this.getContext(), commentList);
                commentListView.setAdapter(commentAdapter);
            } else {
                commentListView.setVisibility(View.GONE);
            }

            commentText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showBottomCommentSheetDialog(moment.getId());
                }
            });
        }
    }

    BottomSheetDialog bottomSheet;

    private void initBottomSheet() {
        if (bottomSheet == null) {
            bottomSheet = new BottomSheetDialog(this.getContext());//实例化BottomSheetDialog
            bottomSheet.setCancelable(true);//设置点击外部是否可以取消
            View bottomReportMenu = this.getLayoutInflater().inflate(R.layout.fragment_report_bottom_pop_menu, null);
            bottomReportMenu.findViewById(R.id.takePhotoLayout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (bottomSheet != null && bottomSheet.isShowing()) {
                        bottomSheet.dismiss();
                    }
                    Intent intent = new Intent(mContext, ReportAddActivity.class);
                    intent.putExtra(SELECT_TYPE, REPORT_TAKE_REQUEST);
                    startActivity(intent);
                }
            });
            bottomReportMenu.findViewById(R.id.selectAlbumLayout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (bottomSheet != null && bottomSheet.isShowing()) {
                        bottomSheet.dismiss();
                    }
                    Intent intent = new Intent(mContext, ReportAddActivity.class);
                    intent.putExtra(SELECT_TYPE, REPORT_SELECT_ALBUM_REQUEST);
                    startActivity(intent);
                }
            });
            bottomReportMenu.findViewById(R.id.cancelText).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (bottomSheet != null && bottomSheet.isShowing()) {
                        bottomSheet.dismiss();
                    }
                }
            });
            bottomSheet.setContentView(bottomReportMenu);
        }
    }

    private void showBottomSheetDialog() {
        if (bottomSheet == null) {
            initBottomSheet();
        }
        if (bottomSheet.isShowing()) {
            bottomSheet.dismiss();
        } else {
            bottomSheet.show();
        }
    }

    /**
     * 关注爆料
     *
     * @param id 爆料ID
     */
    private void fellow(String id) {
        OkGo.<JHResponse<String>>post(ReleaseConfig.baseUrl() + "Forum/forumFollow")
                .tag(this)
                .params("fid", id)
                .params("uid", UserService.getInstance().getUid())
                .params("port", "3")
                .execute(new JHCallback<JHResponse<String>>() {
                    @Override
                    public void onSuccess(Response<JHResponse<String>> response) {
                        T.showToast(ReportFragment.this.getActivity(), "关注成功");
                        xRecyclerView.refresh();
                    }

                    @Override
                    public void onError(Response<JHResponse<String>> response) {
                        T.showToast(ReportFragment.this.getActivity(), response.getException().getMessage());
                    }
                });
    }

    /**
     * 取消关注爆料
     *
     * @param id 爆料ID
     */
    private void unFellow(String id) {
        OkGo.<JHResponse<String>>post(ReleaseConfig.baseUrl() + "Forum/forumCancel")
                .tag(this)
                .params("id", id)
                .execute(new JHCallback<JHResponse<String>>() {
                    @Override
                    public void onSuccess(Response<JHResponse<String>> response) {
                        T.showToast(ReportFragment.this.getActivity(), "取消关注成功");
                        xRecyclerView.refresh();
                    }

                    @Override
                    public void onError(Response<JHResponse<String>> response) {
                        T.showToast(ReportFragment.this.getActivity(), response.getException().getMessage());
                    }
                });
    }

    /**
     * 点赞爆料
     *
     * @param id 爆料ID
     */
    private void thumpUp(String id) {
        OkGo.<JHResponse<String>>post(ReleaseConfig.baseUrl() + "Forum/forumGive")
                .tag(this)
                .params("fid", id)
                .params("uid", UserService.getInstance().getUid())
                .params("port", "3")
                .execute(new JHCallback<JHResponse<String>>() {
                    @Override
                    public void onSuccess(Response<JHResponse<String>> response) {
                        T.showToast(ReportFragment.this.getActivity(), "点赞成功");
                        xRecyclerView.refresh();
                    }

                    @Override
                    public void onError(Response<JHResponse<String>> response) {
                        T.showToast(ReportFragment.this.getActivity(), response.getException().getMessage());
                    }
                });
    }

    /**
     * 取消点赞爆料
     *
     * @param id 爆料ID
     */
    private void cancelThumpUp(String id) {
        OkGo.<JHResponse<String>>post(ReleaseConfig.baseUrl() + "Forum/forumGive")
                .tag(this)
                .params("fid", id)
                .params("uid", UserService.getInstance().getUid())
                .params("port", "3")
                .execute(new JHCallback<JHResponse<String>>() {
                    @Override
                    public void onSuccess(Response<JHResponse<String>> response) {
                        T.showToast(ReportFragment.this.getActivity(), "取消点赞成功");
                        xRecyclerView.refresh();
                    }

                    @Override
                    public void onError(Response<JHResponse<String>> response) {
                        T.showToast(ReportFragment.this.getActivity(), response.getException().getMessage());
                    }
                });
    }


    BottomSheetDialog bottomCommentSheet;

    private void showBottomCommentSheetDialog(String id) {
        if (bottomCommentSheet == null) {
            bottomCommentSheet = new BottomSheetDialog(this.getContext());//实例化BottomSheetDialog
            bottomCommentSheet.setCancelable(true);//设置点击外部是否可以取消
            View bottomReportMenu = this.getLayoutInflater().inflate(R.layout.fragment_report_comment_menu, null);
            bottomReportMenu.findViewById(R.id.cancelText).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (bottomCommentSheet != null && bottomCommentSheet.isShowing()) {
                        bottomCommentSheet.dismiss();
                    }
                }
            });
            bottomReportMenu.findViewById(R.id.confirmText).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    comment(((EditText) bottomReportMenu.findViewById(R.id.commentContent)).getText().toString(), id);
                }
            });
            bottomCommentSheet.setContentView(bottomReportMenu);
        }
        bottomCommentSheet.show();

    }

    private void comment(String content, String id) {
        if (StringUtil.isEmpty(content)) {
            T.showToast(ReportFragment.this.getActivity(), "请输入评论内容");
            return;
        }
        T.showToast(ReportFragment.this.getActivity(), content + "::::::::" + id);
        if (bottomCommentSheet != null && bottomCommentSheet.isShowing()) {
            bottomCommentSheet.dismiss();
        }
        xRecyclerView.refresh();
    }
}
