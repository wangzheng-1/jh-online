package com.xcompany.jhonline.module.home.technical.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xcompany.jhonline.R;
import com.xcompany.jhonline.model.home.Application;
import com.xcompany.jhonline.utils.NullCheck;
import com.xcompany.jhonline.widget.MultipleTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ApplicationAdapter extends RecyclerView.Adapter {
    private List<Application> mDatas = new ArrayList<>();
    public LayoutInflater mInflater;
    public Context context;


    public void addDatas(List<Application> datas) {
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public void setDatas(List<Application> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    public List<Application> getDatas() {
        return mDatas;
    }


    public ApplicationAdapter(Context context, List<Application> mdatas) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.mDatas = mdatas;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.item_application, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        Application bean = mDatas.get(position);
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.tvName.setContentText(bean.getName());
        holder.tvEducation.setContentText(bean.getEducation());
        holder.tvDuration.setContentText(bean.getDuration());
        holder.tvCid.setContentText(bean.getCid());
        holder.tvCity.setContentText(bean.getCity());
        String price = bean.getPrice();
        try {
            float aFloat = Float.parseFloat(price);
            if (aFloat == 0) {
                holder.tvPrice.setContentText("面议");
            } else {
                holder.tvPrice.setContentText(price);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        holder.tvProject.setText(bean.getProject().toString());
        holder.itemView.setOnClickListener(v -> mListener.onItemClick(position, bean, holder));
        if(TextUtils.equals(bean.getSign(),"0")){
            holder.tvTelephone.setText(NullCheck.check(bean.getTelephone()));
        }else {
            holder.tvTelephone.setText("查看电话");
        }
        holder.tvTelephone.setOnClickListener(v -> {
            if (onPhoneClickListener != null)
                onPhoneClickListener.onPhoneClick(position, bean, holder);
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        MultipleTextView tvName;
        @BindView(R.id.tv_education)
        MultipleTextView tvEducation;
        @BindView(R.id.tv_duration)
        MultipleTextView tvDuration;
        @BindView(R.id.tv_cid)
        MultipleTextView tvCid;
        @BindView(R.id.tv_city)
        MultipleTextView tvCity;
        @BindView(R.id.tv_price)
        MultipleTextView tvPrice;
        @BindView(R.id.tv_project)
        TextView tvProject;
        @BindView(R.id.tv_telephone)
        TextView tvTelephone;
        @BindView(R.id.tv_save)
        TextView tvSave;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    public void setOnItemClickListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }

    public void setOnPhoneClickListener(OnPhoneClickListener onPhoneClickListener) {
        this.onPhoneClickListener = onPhoneClickListener;
    }

    private OnItemClickListener mListener;
    private OnPhoneClickListener onPhoneClickListener;


    public interface OnItemClickListener {
        void onItemClick(int position, Application bean, RecyclerView.ViewHolder holder);
    }

    public interface OnPhoneClickListener {
        void onPhoneClick(int position, Application bean, RecyclerView.ViewHolder holder);
    }
}
