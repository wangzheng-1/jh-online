package com.xcompany.jhonline.module.home.certificate.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xcompany.jhonline.R;
import com.xcompany.jhonline.app.GlideApp;
import com.xcompany.jhonline.model.home.QualificationHandle;
import com.xcompany.jhonline.utils.NullCheck;
import com.xcompany.jhonline.widget.MultipleTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class QualificationHandleAapter extends RecyclerView.Adapter {
    private List<QualificationHandle> mDatas = new ArrayList<>();
    public LayoutInflater mInflater;
    public Context context;


    public void addDatas(List<QualificationHandle> datas) {
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public void setDatas(List<QualificationHandle> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    public List<QualificationHandle> getDatas() {
        return mDatas;
    }


    public QualificationHandleAapter(Context context, List<QualificationHandle> mdatas) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.mDatas = mdatas;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.item_qualification_handle, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        QualificationHandle bean = mDatas.get(position);
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.tvName.setText(NullCheck.check(bean.getName()));
        holder.tvLinkman.setContentText(bean.getLinkman());
        holder.tvContacts.setContentText(bean.getContacts());
        GlideApp.with(context).load(bean.getImage())
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
        TextView tvName;
        @BindView(R.id.tv_linkman)
        MultipleTextView tvLinkman;
        @BindView(R.id.tv_contacts)
        MultipleTextView tvContacts;

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
        void onItemClick(int position, QualificationHandle bean, RecyclerView.ViewHolder holder);
    }

}
