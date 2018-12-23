package com.xcompany.jhonline.module.home.subcontract.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xcompany.jhonline.R;
import com.xcompany.jhonline.model.home.QuailtyTeam;
import com.xcompany.jhonline.utils.NullCheck;
import com.xcompany.jhonline.widget.StarBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class QualityTeamAdapter extends RecyclerView.Adapter {
    private List<QuailtyTeam> mDatas = new ArrayList<>();
    public LayoutInflater mInflater;
    public Context context;


    public void addDatas(List<QuailtyTeam> datas) {
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public void setDatas(List<QuailtyTeam> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    public List<QuailtyTeam> getDatas() {
        return mDatas;
    }


    public QualityTeamAdapter(Context context, List<QuailtyTeam> mdatas) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.mDatas = mdatas;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.item_quality_team, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        QuailtyTeam bean = mDatas.get(position);
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.tvName.setText(NullCheck.check(bean.getName()));
        holder.tvInventory.setText(NullCheck.check("主要承接单项：", bean.getInventory()));

        if (TextUtils.equals("0.00", bean.getPay())) {
            holder.tvPay.setText("是否垫资：不可垫资");
        } else {
            holder.tvPay.setText("是否垫资：可垫资");
        }
        holder.tvContacts.setText(NullCheck.check("项目地址：", bean.getContacts()));
        holder.tvEntryTime.setText(NullCheck.check("发布时间：", bean.getEntryTime()));

        holder.starBar.setStarMark(bean.getGrade());
        if (TextUtils.equals("0", bean.getAuthentication())) {
            holder.image.setImageResource(R.drawable.yes);
        } else {
            holder.image.setImageResource(R.drawable.no);
        }
        if (mListener != null) {
            holder.itemView.setOnClickListener(v -> mListener.onItemClick(position, bean, holder));
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_inventory)
        TextView tvInventory;
        @BindView(R.id.tv_contacts)
        TextView tvContacts;
        @BindView(R.id.tv_pay)
        TextView tvPay;
        @BindView(R.id.tv_entryTime)
        TextView tvEntryTime;
        @BindView(R.id.starBar)
        StarBar starBar;
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
        void onItemClick(int position, QuailtyTeam bean, RecyclerView.ViewHolder holder);
    }

}
