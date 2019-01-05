package com.xcompany.jhonline.widget;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.xcompany.jhonline.R;
import com.xcompany.jhonline.model.base.Category;
import com.xcompany.jhonline.network.DataRequestUtil;
import com.xcompany.jhonline.network.FileNetCallBack;
import com.xcompany.jhonline.network.JHCallback;
import com.xcompany.jhonline.network.JHResponse;
import com.xcompany.jhonline.utils.GlideUtil;
import com.xcompany.jhonline.utils.ReleaseConfig;
import com.xcompany.jhonline.utils.T;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xieliang on 2019/1/1 23:16
 */
public class SupplierProductView {
    public View mView;
    private Context mContext;
    private LinearLayout llAddImage;
    private ImageView ivImage;
    private ImageView ivDelete;
    private TextView tvUpload;
    private LinearLayout llParent;
    private AutoGridView childGrid;
    private Category curCategory;
    private Category curChild;
    private String imageUrl;
    private SupplierProductView that = this;
    private List<Category> childDatas = new ArrayList<>();
    private MyAdapter adapter;

    public SupplierProductView(Context context, boolean isDelete) {
        mContext = context;
        mView = View.inflate(context, R.layout.view_quality_supplier_product, null);
        llAddImage = mView.findViewById(R.id.ll_add_image);
        ivImage = mView.findViewById(R.id.iv_image);
        ivDelete = mView.findViewById(R.id.iv_delete);
        tvUpload = mView.findViewById(R.id.tv_upload);
        llParent = mView.findViewById(R.id.ll_parent);
        childGrid = mView.findViewById(R.id.child_grid);
        adapter = new MyAdapter();
        childGrid.setAdapter(adapter);
        childGrid.setOnItemClickListener((parent, view, position, id) -> {
            curChild = childDatas.get(position);
            adapter.notifyDataSetChanged();
        });
        TypeGridView gridView = new TypeGridView(mContext, 5, 0, true);
        gridView.setOnItemClickListener(category -> {
            this.curCategory = category;
            getMenuData(category.getId());
        });
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
        void onSelect(SupplierProductView productView);
    }


    public boolean check() {
        return curCategory != null && curChild != null && !TextUtils.isEmpty(imageUrl);
    }

    public Category getCurCategory() {
        return curCategory;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Category getCurChild() {
        return curChild;
    }

    private ProgressDialog dialog = null;

    public void setImageUrl(String imageUrl) {
        dialog = ProgressDialog.show(mContext, "", "正在上传，请稍后", true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        File file = new File(imageUrl);
        Map<String, Object> params = new HashMap<>();
        params.put("type", "Supplier");
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

    private void getMenuData(String pid) {
        curChild = null;
        OkGo.<JHResponse<List<Category>>>post(ReleaseConfig.baseUrl() + "class/classList")
                .tag(this)
                .params("type", 5)
                .params("pid", pid)
                .execute(new JHCallback<JHResponse<List<Category>>>() {
                    @Override
                    public void onSuccess(Response<JHResponse<List<Category>>> response) {
                        childDatas = response.body().getMsg();
                        if (childDatas.size() > 0) curChild = childDatas.get(0);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Response<JHResponse<List<Category>>> response) {

                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        T.showToast(mContext, response.getException().getMessage());
                    }
                });
    }

    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return childDatas.size();
        }

        @Override
        public Object getItem(int position) {
            return childDatas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.view_child_product_item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tvTitle.setText(childDatas.get(position).getName());
            if (curChild != null && TextUtils.equals(curChild.getId(), childDatas.get(position).getId())) {
                holder.tvTitle.setTextColor(mContext.getResources().getColor(R.color.color0072FF));
            } else {
                holder.tvTitle.setTextColor(mContext.getResources().getColor(R.color.color333333));
            }
            return convertView;
        }

    }

    static class ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
