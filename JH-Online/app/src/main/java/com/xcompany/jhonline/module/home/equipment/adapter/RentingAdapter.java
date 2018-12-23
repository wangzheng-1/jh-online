package com.xcompany.jhonline.module.home.equipment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xcompany.jhonline.R;
import com.xcompany.jhonline.model.home.Renting;
import com.xcompany.jhonline.utils.NullCheck;
import com.xcompany.jhonline.widget.StarBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RentingAdapter extends RecyclerView.Adapter {
    private List<Renting> mDatas = new ArrayList<>();
    public LayoutInflater mInflater;
    public Context context;


    public void addDatas(List<Renting> datas) {
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public void setDatas(List<Renting> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    public List<Renting> getDatas() {
        return mDatas;
    }


    public RentingAdapter(Context context, List<Renting> mdatas) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.mDatas = mdatas;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.item_renting, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        Renting bean = mDatas.get(position);
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.tvName.setText(NullCheck.check(bean.getName()));
        holder.tvCid.setText(NullCheck.check("出租类别：", bean.getCid()));
        holder.tvContacts.setText(NullCheck.check("地址：", bean.getContacts()));
        holder.starBar.setStarMark(bean.getGrade());
        if (mListener != null)
            holder.itemView.setOnClickListener(v -> mListener.onItemClick(position, bean, holder));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_cid)
        TextView tvCid;
        @BindView(R.id.tv_contacts)
        TextView tvContacts;
        @BindView(R.id.starBar)
        StarBar starBar;

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
        void onItemClick(int position, Renting bean, RecyclerView.ViewHolder holder);
    }

}
