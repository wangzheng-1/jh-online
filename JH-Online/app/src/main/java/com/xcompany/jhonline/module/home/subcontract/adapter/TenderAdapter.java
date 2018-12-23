package com.xcompany.jhonline.module.home.subcontract.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xcompany.jhonline.R;
import com.xcompany.jhonline.model.home.Tender;
import com.xcompany.jhonline.widget.MultipleTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TenderAdapter extends RecyclerView.Adapter {
    private List<Tender> mDatas = new ArrayList<>();
    public LayoutInflater mInflater;
    public Context context;


    public void addDatas(List<Tender> datas) {
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public void setDatas(List<Tender> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    public List<Tender> getDatas() {
        return mDatas;
    }


    public TenderAdapter(Context context, List<Tender> mdatas) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.mDatas = mdatas;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.item_tender, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        Tender bean = mDatas.get(position);
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.tvName.setText(bean.getName());
        holder.tvInventory.setContentText(bean.getInventory());
        holder.tvCompany.setContentText(bean.getCompany());
        holder.tvEntryTime.setText(bean.getEntryTime());
//        GlideApp.with(context).load(bean.getImage())
//                .centerCrop()
//                .into(holder.image);
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
        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.tv_inventory)
        MultipleTextView tvInventory;
        @BindView(R.id.tv_company)
        MultipleTextView tvCompany;
        @BindView(R.id.tv_entryTime)
        MultipleTextView tvEntryTime;

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
        void onItemClick(int position, Tender bean, RecyclerView.ViewHolder holder);
    }

}
