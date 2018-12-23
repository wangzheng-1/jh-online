package com.xcompany.jhonline.module.me.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xcompany.jhonline.R;
import com.xcompany.jhonline.model.base.Model;
import com.xcompany.jhonline.model.me.MeCollectBean;
import com.xcompany.jhonline.widget.StarBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MeCollectAdapter extends RecyclerView.Adapter {

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


    public MeCollectAdapter(Context context, List<Model> mdatas) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.mDatas = mdatas;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.activity_me_collect_list_item, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        final MeCollectBean bean = (MeCollectBean)mDatas.get(position);
        final ViewHolder holder = (ViewHolder) viewHolder;
        holder.constructionTeamName.setText(bean.getDetailName());

        holder.starBar.setStarMark(new Random().nextInt(6));
        Random random = new Random();
        int i = random.nextInt(2);
        if (i == 0) {
            holder.image.setImageResource(R.drawable.yes);
        } else {
            holder.image.setImageResource(R.drawable.no);
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
        @BindView(R.id.constructionTeamName)
        TextView constructionTeamName;
        @BindView(R.id.mainAcceptOptionText)
        TextView mainAcceptOptionText;
        @BindView(R.id.isReadyCapitalText)
        TextView isReadyCapitalText;
        @BindView(R.id.projectAddText)
        TextView projectAddText;
        @BindView(R.id.publishTimeText)
        TextView publishTimeText;
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
        void onItemClick(int position, Model bean, RecyclerView.ViewHolder holder);
    }

}
