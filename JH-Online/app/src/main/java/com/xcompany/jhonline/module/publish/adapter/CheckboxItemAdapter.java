package com.xcompany.jhonline.module.publish.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xcompany.jhonline.R;
import com.xcompany.jhonline.model.base.Model;
import com.xcompany.jhonline.model.publish.CheckboxItemBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CheckboxItemAdapter extends RecyclerView.Adapter  {

    Context context;

    List<Model> models;

    public CheckboxItemAdapter(Context context, List<Model> models) {
        this.context = context;
        this.models = models;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.system_checkbox_item, viewGroup, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        CheckboxItemBean checkboxItemBean =  (CheckboxItemBean)models.get(position);
        final MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        myViewHolder.optionText.setText(checkboxItemBean.getName());
        myViewHolder.optionText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckboxItemBean checkboxItemBean = (CheckboxItemBean)models.get(position);
                if(checkboxItemBean.isCheck()){
                    checkboxItemBean.setCheck(false);
                    myViewHolder.optionText.setBackgroundResource(R.drawable.background_rectangle_all_corner_gray);
                    myViewHolder.optionText.setTextColor(context.getResources().getColor(R.color.text_black));
                }
                else{
                    checkboxItemBean.setCheck(true);
                    myViewHolder.optionText.setBackgroundResource(R.drawable.background_rectangle_all_corner_blue);
                    myViewHolder.optionText.setTextColor(context.getResources().getColor(R.color.text_white));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.optionText)
        TextView optionText;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
