package com.xcompany.jhonline.widget;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xcompany.jhonline.R;

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
    }

    public void setOnDeleteListener(OnDeleteListener mListener) {
        this.mListener = mListener;
    }

    private OnDeleteListener mListener;

    public interface OnDeleteListener {
        void onDelete();
    }
}
