package com.xcompany.jhonline.module.home.buildMaterial.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.xcompany.jhonline.R;
import com.xcompany.jhonline.app.GlideApp;
import com.xcompany.jhonline.model.home.QualitySupplier;
import com.xcompany.jhonline.widget.MultipleTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class QualitySupplierAapter extends RecyclerView.Adapter {
    private List<QualitySupplier> mDatas = new ArrayList<>();
    public LayoutInflater mInflater;
    public Context context;


    public void addDatas(List<QualitySupplier> datas) {
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public void setDatas(List<QualitySupplier> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    public List<QualitySupplier> getDatas() {
        return mDatas;
    }


    public QualitySupplierAapter(Context context, List<QualitySupplier> mdatas) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.mDatas = mdatas;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.item_quality_supplier, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        QualitySupplier bean = mDatas.get(position);
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.tvName.setContentText(bean.getName());
        holder.tvClassname.setContentText(bean.getClassname());
        if (bean.getShop().size() > 0)
            GlideApp.with(context).load(bean.getShop().get(0))
                    .centerCrop()
                    .into(holder.image);
        if (mListener != null)
            holder.itemView.setOnClickListener(v -> mListener.onItemClick(position, bean, holder));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.tv_name)
        MultipleTextView tvName;
        @BindView(R.id.tv_classname)
        MultipleTextView tvClassname;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    public void setOnItemClickListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }

    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position, QualitySupplier bean, RecyclerView.ViewHolder holder);
    }

}
