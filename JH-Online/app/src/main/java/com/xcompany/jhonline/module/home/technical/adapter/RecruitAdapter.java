package com.xcompany.jhonline.module.home.technical.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        holder.tvTelephone.setText(NullCheck.check(bean.getTelephone()));
        holder.itemView.setOnClickListener(v -> mListener.onItemClick(position, bean, holder));
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
        void onItemClick(int position, Recruit bean, RecyclerView.ViewHolder holder);
    }

}
