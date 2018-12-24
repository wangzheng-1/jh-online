package com.xcompany.jhonline.module.publish.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xcompany.jhonline.R;
import com.xcompany.jhonline.model.base.Model;
import com.xcompany.jhonline.model.publish.PublishItemBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PublishAdapter extends RecyclerView.Adapter {


    private List<Model> mDatas = new ArrayList<>();
    public LayoutInflater mInflater;
    public Context context;


    public void addDatas(List<Model> datas) {
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public void setDatas(List<Model> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    public List<Model> getDatas() {
        return mDatas;
    }


    public PublishAdapter(Context context, List<Model> mdatas) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.mDatas = mdatas;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.activity_publish_list_item, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        final PublishItemBean bean = (PublishItemBean) mDatas.get(position);
        final ViewHolder holder = (ViewHolder) viewHolder;

        Random random = new Random();
        int i = random.nextInt(2);
        if (i == 0) {
            holder.image.setImageResource(R.mipmap.publish_check_pass);
        } else {
            holder.image.setImageResource(R.mipmap.publish_check_no_pass);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(position, bean, holder);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.publishTypeText)
        TextView publishTypeText;
        @BindView(R.id.publishTitleText)
        TextView publishTitleText;
        @BindView(R.id.linkmanText)
        TextView linkmanText;
        @BindView(R.id.publishTimeText)
        TextView publishTimeText;
        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.isTakeTopImage)
        LinearLayout isTakeTopImage;
        @BindView(R.id.takeDetailText)
        TextView takeDetailText;
        @BindView(R.id.takeTopText)
        TextView takeTopText;

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
        void onItemClick(int position, Model bean, RecyclerView.ViewHolder holder);
    }

}
