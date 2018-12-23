package com.xcompany.jhonline.module.home.certificate.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xcompany.jhonline.R;
import com.xcompany.jhonline.model.home.Registration;
import com.xcompany.jhonline.utils.NullCheck;
import com.xcompany.jhonline.widget.MultipleTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RegistrationAdapter extends RecyclerView.Adapter {
    private List<Registration> mDatas = new ArrayList<>();
    public LayoutInflater mInflater;
    public Context context;


    public void addDatas(List<Registration> datas) {
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public void setDatas(List<Registration> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    public List<Registration> getDatas() {
        return mDatas;
    }


    public RegistrationAdapter(Context context, List<Registration> mdatas) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.mDatas = mdatas;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.item_registration, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        Registration bean = mDatas.get(position);
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.tvName.setText(NullCheck.check(bean.getName()));
        holder.tvClassname.setContentText(bean.getClassname());
        holder.tvContacts.setContentText(bean.getContacts());
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
        holder.tvEntryTime.setContentText(bean.getEntryTime());
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
        @BindView(R.id.tv_classname)
        MultipleTextView tvClassname;
        @BindView(R.id.tv_contacts)
        MultipleTextView tvContacts;
        @BindView(R.id.tv_price)
        MultipleTextView tvPrice;
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
        void onItemClick(int position, Registration bean, RecyclerView.ViewHolder holder);
    }

}
