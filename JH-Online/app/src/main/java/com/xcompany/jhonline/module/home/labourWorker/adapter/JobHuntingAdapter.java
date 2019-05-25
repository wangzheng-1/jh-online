package com.xcompany.jhonline.module.home.labourWorker.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xcompany.jhonline.R;
import com.xcompany.jhonline.app.GlideApp;
import com.xcompany.jhonline.model.home.JobHunting;
import com.xcompany.jhonline.utils.GlideUtil;
import com.xcompany.jhonline.utils.NullCheck;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class JobHuntingAdapter extends RecyclerView.Adapter {
    private List<JobHunting> mDatas = new ArrayList<>();
    public LayoutInflater mInflater;
    public Context context;


    public void addDatas(List<JobHunting> datas) {
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public void setDatas(List<JobHunting> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    public List<JobHunting> getDatas() {
        return mDatas;
    }


    public JobHuntingAdapter(Context context, List<JobHunting> mdatas) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.mDatas = mdatas;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.item_job_hunting, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        JobHunting bean = mDatas.get(position);
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.itemView.setOnClickListener(v -> mListener.onItemClick(position, bean, holder));
        holder.tvName.setText(NullCheck.check(bean.getName()));
        holder.tvCid.setText(NullCheck.check("工种信息：", bean.getCid()));
        holder.tvEntryTime.setText(NullCheck.check("发布时间：", bean.getEntryTime()));
        holder.tvNumber.setText(NullCheck.check("队伍人数：", bean.getNumber()));
        holder.tvContacts.setText(NullCheck.check("地址：", bean.getContacts()));
        if(TextUtils.equals(bean.getSign(),"0")){
            holder.tvTelephone.setText(NullCheck.check(bean.getTelephone()));
        }else {
            holder.tvTelephone.setText("查看电话");
        }
        holder.tvTelephone.setOnClickListener(v -> {
            if (onPhoneClickListener != null)
                onPhoneClickListener.onPhoneClick(position, bean, holder);
        });
        GlideUtil.LoaderImage(context, bean.getImage(), holder.image, true);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.tv_cid)
        TextView tvCid;
        @BindView(R.id.tv_entryTime)
        TextView tvEntryTime;
        @BindView(R.id.tv_number)
        TextView tvNumber;
        @BindView(R.id.tv_contacts)
        TextView tvContacts;
        @BindView(R.id.tv_telephone)
        TextView tvTelephone;

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
        void onItemClick(int position, JobHunting bean, RecyclerView.ViewHolder holder);
    }

    public interface OnPhoneClickListener {
        void onPhoneClick(int position, JobHunting bean, RecyclerView.ViewHolder holder);
    }
}
