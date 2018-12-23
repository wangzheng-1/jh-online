package com.xcompany.jhonline.module.home.blacklist.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.xcompany.jhonline.R;
import com.xcompany.jhonline.app.GlideApp;
import com.xcompany.jhonline.model.home.Black;
import com.xcompany.jhonline.widget.MultipleTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class BlackListAapter extends RecyclerView.Adapter {
    private List<Black> mDatas = new ArrayList<>();
    public LayoutInflater mInflater;
    public Context context;


    public void addDatas(List<Black> datas) {
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public void setDatas(List<Black> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    public List<Black> getDatas() {
        return mDatas;
    }


    public BlackListAapter(Context context, List<Black> mdatas) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.mDatas = mdatas;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.item_black_list, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        Black bean = mDatas.get(position);
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.tvName.setContentText(bean.getName());
        holder.tvExplain.setContentText(bean.getExplain());
        holder.tvNumber.setContentText(bean.getNumber());
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
        MultipleTextView tvName;
        @BindView(R.id.tv_explain)
        MultipleTextView tvExplain;
        @BindView(R.id.tv_number)
        MultipleTextView tvNumber;

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
        void onItemClick(int position, Black bean, RecyclerView.ViewHolder holder);
    }

}
