package com.xcompany.jhonline.module.home.professionalSkills.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xcompany.jhonline.R;
import com.xcompany.jhonline.model.home.ProfessionalSkills;
import com.xcompany.jhonline.utils.NullCheck;
import com.xcompany.jhonline.widget.MultipleTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ProfessionalSkillsAdapter extends RecyclerView.Adapter {
    private List<ProfessionalSkills> mDatas = new ArrayList<>();
    public LayoutInflater mInflater;
    public Context context;


    public void addDatas(List<ProfessionalSkills> datas) {
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public void setDatas(List<ProfessionalSkills> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    public List<ProfessionalSkills> getDatas() {
        return mDatas;
    }


    public ProfessionalSkillsAdapter(Context context, List<ProfessionalSkills> mdatas) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.mDatas = mdatas;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.item_professional_skills, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        ProfessionalSkills bean = mDatas.get(position);
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.tvClassname.setText(NullCheck.check(bean.getClassname()));
        holder.tvCity.setContentText(NullCheck.check(bean.getCity()) + NullCheck.check(bean.getArea()));
        if (mListener != null)
            holder.itemView.setOnClickListener(v -> mListener.onItemClick(position, bean, holder));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_classname)
        TextView tvClassname;
        @BindView(R.id.tv_city)
        MultipleTextView tvCity;

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
        void onItemClick(int position, ProfessionalSkills bean, RecyclerView.ViewHolder holder);
    }

}
