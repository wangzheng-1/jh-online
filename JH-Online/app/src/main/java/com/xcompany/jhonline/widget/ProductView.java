package com.xcompany.jhonline.widget;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xcompany.jhonline.R;
import com.xcompany.jhonline.model.base.Category;
import com.xcompany.jhonline.network.DataRequestUtil;
import com.xcompany.jhonline.network.FileNetCallBack;
import com.xcompany.jhonline.network.JHResponse;
import com.xcompany.jhonline.utils.GlideUtil;
import com.xcompany.jhonline.utils.T;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xieliang on 2019/1/1 23:16
 */
public class ProductView {
    public View mView;
    private Context mContext;
    private LinearLayout llDelete;
    private CleanEditText etTitle;
    private LinearLayout llAddImage;
    private ImageView ivImage;
    private ImageView ivDelete;
    private TextView tvUpload;
    private LinearLayout llParent;
    private Category curCategory;
    private String imageUrl;
    private ProductView that = this;

    public ProductView(Context context, boolean isDelete) {
        mContext = context;
        mView = View.inflate(context, R.layout.view_renting_product, null);
        llDelete = mView.findViewById(R.id.ll_delete);
        etTitle = mView.findViewById(R.id.et_title);
        llAddImage = mView.findViewById(R.id.ll_add_image);
        ivImage = mView.findViewById(R.id.iv_image);
        ivDelete = mView.findViewById(R.id.iv_delete);
        tvUpload = mView.findViewById(R.id.tv_upload);
        llParent = mView.findViewById(R.id.ll_parent);
        TypeGridView gridView = new TypeGridView(mContext, 0, 1,true);
        gridView.setOnItemClickListener(category -> this.curCategory = category);
        llParent.addView(gridView.mView);
        ivDelete.setVisibility(isDelete ? View.VISIBLE : View.GONE);
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
        void onSelect(ProductView productView);
    }


    public boolean check() {
        if (curCategory==null) {
            return false;
        }
        if (TextUtils.isEmpty(imageUrl)) {
            return false;
        }
        return true;
    }

    public Category getCurCategory() {
        return curCategory;
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
        params.put("type", "Lease");
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
