package com.xcompany.jhonline.module.home.equipment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xcompany.jhonline.R;
import com.xcompany.jhonline.model.home.RentSeeking;
import com.xcompany.jhonline.utils.NullCheck;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RentSeekingAdapter extends RecyclerView.Adapter {
    private List<RentSeeking> mDatas = new ArrayList<>();
    public LayoutInflater mInflater;
    public Context context;


    public void addDatas(List<RentSeeking> datas) {
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public void setDatas(List<RentSeeking> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    public List<RentSeeking> getDatas() {
        return mDatas;
    }


    public RentSeekingAdapter(Context context, List<RentSeeking> mdatas) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.mDatas = mdatas;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.item_rent_seeking, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        RentSeeking bean = mDatas.get(position);
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.tvName.setText(NullCheck.check(bean.getName()));
        holder.tvContacts.setText(NullCheck.check("地址：", bean.getContacts()));
        holder.tvEntryTime.setText(NullCheck.check("发布时间：", bean.getEntryTime()));
        String authentication = bean.getAuthentication();
        if (TextUtils.equals("0", authentication)) {
            holder.image.setImageResource(R.drawable.yes);
        } else {
            holder.image.setImageResource(R.drawable.no);
        }
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
        @BindView(R.id.tv_contacts)
        TextView tvContacts;
        @BindView(R.id.tv_entryTime)
        TextView tvEntryTime;
        @BindView(R.id.image)
        ImageView image;

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
        void onItemClick(int position, RentSeeking bean, RecyclerView.ViewHolder holder);
    }

}
