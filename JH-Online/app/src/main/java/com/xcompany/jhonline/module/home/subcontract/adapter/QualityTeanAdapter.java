package com.xcompany.jhonline.module.home.subcontract.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.StackView;
import android.widget.TextView;

import com.xcompany.jhonline.R;
import com.xcompany.jhonline.widget.StarBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;


public class QualityTeanAdapter extends RecyclerView.Adapter {
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


    public QualityTeanAdapter(Context context, List<String> mdatas) {
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
        final String bean = mDatas.get(position);
        final ViewHolder holder = (ViewHolder) viewHolder;
        holder.tvText.setText(bean);
        holder.starBar.setStarMark(new Random().nextInt(6));
        Random random = new Random();
        int i = random.nextInt(2);
        if (i == 0) {
            holder.image.setImageResource(R.drawable.yes);
        } else {
            holder.image.setImageResource(R.drawable.no);
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text)
        TextView tvText;
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
        void onItemClick(int position, String bean, RecyclerView.ViewHolder holder);
    }

}
