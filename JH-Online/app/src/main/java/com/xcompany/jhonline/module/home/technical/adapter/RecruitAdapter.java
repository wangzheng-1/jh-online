package com.xcompany.jhonline.module.home.technical.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xcompany.jhonline.R;
import com.xcompany.jhonline.model.home.Recruit;
import com.xcompany.jhonline.utils.NullCheck;
import com.xcompany.jhonline.widget.MultipleTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecruitAdapter extends RecyclerView.Adapter {
    private List<Recruit> mDatas = new ArrayList<>();
    public LayoutInflater mInflater;
    public Context context;


    public void addDatas(List<Recruit> datas) {
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public void setDatas(List<Recruit> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    public List<Recruit> getDatas() {
        return mDatas;
    }


    public RecruitAdapter(Context context, List<Recruit> mdatas) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.mDatas = mdatas;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.item_recruit, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        Recruit bean = mDatas.get(position);
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.tvTitle.setText(NullCheck.check(bean.getTitle()));
        holder.tvNumber.setContentText(bean.getNumber());
        holder.tvName.setContentText(bean.getName());
        holder.tvCid.setContentText(bean.getCid());
        holder.tvCity.setContentText(NullCheck.check(bean.getArea() + " " + NullCheck.check(bean.getCity())));
        holder.tvEntryTime.setContentText(bean.getEntryTime());
        holder.tvLinkman.setContentText(bean.getLinkman());
        holder.itemView.setOnClickListener(v -> mListener.onItemClick(position, bean, holder));
        if (TextUtils.equals(bean.getSign(), "0")) {
            holder.tvTelephone.setText(NullCheck.check(bean.getTelephone()));
        } else {
            holder.tvTelephone.setText("查看电话");
        }
        if (TextUtils.equals(bean.getIssue(), "0")) {
            holder.tvSave.setText("取消收藏");
        } else {
            holder.tvSave.setText("收藏");
        }
        holder.llTelephone.setOnClickListener(v -> {
            if (onPhoneClickListener != null)
                onPhoneClickListener.onPhoneClick(position, bean, holder);
        });
        holder.llSave.setOnClickListener(v -> {
            if (onSaveClickListener != null) {
                onSaveClickListener.onSaveClick(position, bean, holder);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_number)
        MultipleTextView tvNumber;
        @BindView(R.id.tv_name)
        MultipleTextView tvName;
        @BindView(R.id.tv_cid)
        MultipleTextView tvCid;
        @BindView(R.id.tv_city)
        MultipleTextView tvCity;
        @BindView(R.id.tv_entryTime)
        MultipleTextView tvEntryTime;
        @BindView(R.id.tv_linkman)
        MultipleTextView tvLinkman;
        @BindView(R.id.tv_telephone)
        TextView tvTelephone;
        @BindView(R.id.tv_save)
        TextView tvSave;
        @BindView(R.id.ll_telephone)
        LinearLayout llTelephone;
        @BindView(R.id.ll_save)
        LinearLayout llSave;

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

    public void setOnSaveClickListener(OnSaveClickListener onSaveClickListener) {
        this.onSaveClickListener = onSaveClickListener;
    }

    private OnItemClickListener mListener;
    private OnPhoneClickListener onPhoneClickListener;
    private OnSaveClickListener onSaveClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position, Recruit bean, RecyclerView.ViewHolder holder);
    }

    public interface OnPhoneClickListener {
        void onPhoneClick(int position, Recruit bean, RecyclerView.ViewHolder holder);
    }

    public interface OnSaveClickListener {
        void onSaveClick(int position, Recruit bean, RecyclerView.ViewHolder holder);
    }
}
