package com.xcompany.jhonline.widget;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xcompany.jhonline.R;
import com.xcompany.jhonline.app.GlideApp;
import com.xcompany.jhonline.module.publish.activity.PublishAptitudeHandleActivity;
import com.xcompany.jhonline.module.report.activity.PhotoSelectActivity;
import com.xcompany.jhonline.network.DataRequestUtil;
import com.xcompany.jhonline.network.FileNetCallBack;
import com.xcompany.jhonline.network.JHResponse;
import com.xcompany.jhonline.utils.GlideUtil;
import com.xcompany.jhonline.utils.ReleaseConfig;
import com.xcompany.jhonline.utils.T;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static com.xcompany.jhonline.module.report.activity.PhotoSelectActivity.IMAGE_NUM;
import static com.xcompany.jhonline.module.report.activity.PhotoSelectActivity.SELECT_MEDIA_TYPE;

/**
 * Created by xieliang on 2019/1/1 23:16
 */
public class CaseView {
    public View mView;
    private Context mContext;
    private LinearLayout llDelete;
    private CleanEditText etEntry;
    private LinearLayout llAddImage;
    private ImageView ivImage;
    private ImageView ivDelete;
    private TextView tvUpload;
    private EditText etIllustrate;
    private String imageUrl;
    private CaseView that = this;

    public CaseView(Context context, boolean isDelete) {
        mContext = context;
        mView = View.inflate(context, R.layout.view_construction_case, null);
        llDelete = mView.findViewById(R.id.ll_delete);
        etEntry = mView.findViewById(R.id.et_entry);
        llAddImage = mView.findViewById(R.id.ll_add_image);
        ivImage = mView.findViewById(R.id.iv_image);
        ivDelete = mView.findViewById(R.id.iv_delete);
        tvUpload = mView.findViewById(R.id.tv_upload);
        etIllustrate = mView.findViewById(R.id.et_illustrate);
        llDelete.setVisibility(isDelete ? View.VISIBLE : View.GONE);
        ivDelete.setOnClickListener(v -> {
            if (mListener != null) mListener.onDelete();
        });
        llAddImage.setOnClickListener(v -> {
            if (imageListener != null) imageListener.onSelect(this);
        });
        tvUpload.setOnClickListener(v -> {
            if (imageListener != null) imageListener.onSelect(this);
        });

    }

    public void setOnDeleteListener(OnDeleteListener mListener) {
        this.mListener = mListener;
    }

    public void setOnImageListener(OnImageListener imageListener) {
        this.imageListener = imageListener;
    }

    private OnDeleteListener mListener;
    private OnImageListener imageListener;

    public interface OnDeleteListener {
        void onDelete();
    }

    public interface OnImageListener {
        void onSelect(CaseView caseView);
    }

    public boolean check() {
        if (TextUtils.isEmpty(etEntry.getText().toString())) {
            return false;
        }
        if (TextUtils.isEmpty(imageUrl)) {
            return false;
        }
        return true;
    }

    public String getEntry() {
        return etEntry.getText().toString();
    }

    public String getIllustrate() {
        return etIllustrate.getText().toString();
    }

    public String getImageUrl() {
        return imageUrl;
    }

    private ProgressDialog dialog = null;

    public void setImageUrl(String imageUrl) {
        dialog = ProgressDialog.show(mContext, "", "正在上传，请稍后", true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        File file = new File(imageUrl);
        Map<String, Object> params = new HashMap<>();
        params.put("type", "Build");
        params.put("file", file);
        DataRequestUtil.upLoadFile("Public/upload", params, new FileNetCallBack<JHResponse<String>>() {
            @Override
            public void done(JHResponse<String> result, Exception e) {
                ((Activity) mContext).runOnUiThread(() -> {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    if (e == null) {
                        Log.i("JHOnline", result.getMsg());
                        that.imageUrl = result.getMsg();
                        llAddImage.setVisibility(View.GONE);
                        ivImage.setVisibility(View.VISIBLE);
                        tvUpload.setVisibility(View.VISIBLE);
                        GlideUtil.LoaderImage(mContext, that.imageUrl, ivImage, false);
                    } else {
                        T.showToast(mContext, e.getMessage());
                    }
                });

            }
        });
    }
}
