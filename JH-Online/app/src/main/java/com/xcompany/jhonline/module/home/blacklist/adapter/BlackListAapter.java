package com.xcompany.jhonline.module.home.blacklist.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xcompany.jhonline.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class BlackListAapter extends RecyclerView.Adapter {
    private List<String> mDatas = new ArrayList<>();
    public LayoutInflater mInflater;
    public Context context;


    public void addDatas(List<String> datas) {
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public void setDatas(List<String> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    public List<String> getDatas() {
        return mDatas;
    }


    public BlackListAapter(Context context, List<String> mdatas) {
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
        final String bean = mDatas.get(position);
        final ViewHolder holder = (ViewHolder) viewHolder;
        holder.tvText.setText(bean);
        holder.itemView.setOnClickListener(v -> mListener.onItemClick(position, bean, holder));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text)
        TextView tvText;

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
        void onItemClick(int position, String bean, RecyclerView.ViewHolder holder);
    }

}
