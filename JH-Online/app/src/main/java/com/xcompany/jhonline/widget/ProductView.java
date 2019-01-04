package com.xcompany.jhonline.widget;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xcompany.jhonline.R;

/**
 * Created by xieliang on 2019/1/1 23:16
 */
public class ProductView {
    public View mView;
    private Context mContext;
    private LinearLayout llDelete;
    private CleanEditText etClass;
    private LinearLayout llAddImage;
    private ImageView ivImage;
    private ImageView ivDelete;
    private TextView tvUpload;
    private LinearLayout llParent;

    public ProductView(Context context, boolean isDelete) {
        mContext = context;
        mView = View.inflate(context, R.layout.view_renting_product, null);
        llDelete = mView.findViewById(R.id.ll_delete);
        etClass = mView.findViewById(R.id.et_class);
        llAddImage = mView.findViewById(R.id.ll_add_image);
        ivImage = mView.findViewById(R.id.iv_image);
        ivDelete = mView.findViewById(R.id.iv_delete);
        tvUpload = mView.findViewById(R.id.tv_upload);
        llParent = mView.findViewById(R.id.ll_parent);
        TypeGridView gridView = new TypeGridView(mContext, 0, 1);
        llParent.addView(gridView.mView);
        ivDelete.setVisibility(isDelete ? View.VISIBLE : View.GONE);
        ivDelete.setOnClickListener(v -> {
            if (mListener != null) mListener.onDelete();
        });
    }

    public void setOnDeleteListener(OnDeleteListener mListener) {
        this.mListener = mListener;
    }

    private OnDeleteListener mListener;

    public interface OnDeleteListener {
        void onDelete();
    }

    public boolean check() {
        if (TextUtils.isEmpty(etClass.getText().toString())) {
            return false;
        }
        return true;
    }

    public String getEntry() {
        return etClass.getText().toString();
    }

}
