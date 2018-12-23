package com.xcompany.jhonline.module.home.labourWorker.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xcompany.jhonline.R;
import com.xcompany.jhonline.model.home.Hiring;
import com.xcompany.jhonline.utils.NullCheck;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HiringAdapter extends RecyclerView.Adapter {
    private List<Hiring> mDatas = new ArrayList<>();
    public LayoutInflater mInflater;
    public Context context;


    public void addDatas(List<Hiring> datas) {
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public void setDatas(List<Hiring> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    public List<Hiring> getDatas() {
        return mDatas;
    }


    public HiringAdapter(Context context, List<Hiring> mdatas) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.mDatas = mdatas;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.item_hiring, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        Hiring bean = mDatas.get(position);
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.name.setText(NullCheck.check(bean.getName()));
        holder.cid.setText(NullCheck.check("劳务工种：", bean.getCid()));
        holder.contacts.setText(NullCheck.check("项目地址：", bean.getContacts()));
        holder.entryTime.setText(NullCheck.check("发布时间：", bean.getEntryTime()));
        holder.itemView.setOnClickListener(v -> mListener.onItemClick(position, bean, holder));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.cid)
        TextView cid;
        @BindView(R.id.contacts)
        TextView contacts;
        @BindView(R.id.entryTime)
        TextView entryTime;

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
        void onItemClick(int position, Hiring bean, RecyclerView.ViewHolder holder);
    }

}
