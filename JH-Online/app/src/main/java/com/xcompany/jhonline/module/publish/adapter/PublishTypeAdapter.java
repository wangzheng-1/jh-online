package com.xcompany.jhonline.module.publish.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xcompany.jhonline.R;
import com.xcompany.jhonline.model.base.Model;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PublishTypeAdapter extends BaseAdapter implements View.OnClickListener {

    Context context;

    List<Model> models;

    public PublishTypeAdapter(Context context, List<Model> models) {
        this.context = context;
        this.models = models;
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final MyViewHolder myViewHolder;
        final Model model =  models.get(position);
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.activity_publish_select_item, null);
            myViewHolder = new MyViewHolder(convertView);
            convertView.setTag(myViewHolder);
        } else {
            myViewHolder = (MyViewHolder) convertView.getTag();
        }
        myViewHolder.openDetailLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myViewHolder.hideLayout.getVisibility() == View.VISIBLE){
                    myViewHolder.hideLayout.setVisibility(View.GONE);
                }
                else{
                    myViewHolder.hideLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        return convertView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.hide_1:
                Toast.makeText(context, "加密", Toast.LENGTH_SHORT).show();
                break;
            case R.id.hide_2:
                Toast.makeText(context, "解密", Toast.LENGTH_SHORT).show();
                break;
            case R.id.hide_3:
                Toast.makeText(context, "分享", Toast.LENGTH_SHORT).show();
                break;
            case R.id.hide_4:
                Toast.makeText(context, "删除", Toast.LENGTH_SHORT).show();
                break;
            case R.id.hide_5:
                Toast.makeText(context, "属性", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    class MyViewHolder {
        @BindView(R.id.imageView)
        ImageView imageView;
        @BindView(R.id.typeNameText)
        TextView typeNameText;
        @BindView(R.id.openDetailImage)
        ImageView openDetailImage;
        @BindView(R.id.openDetailLayout)
        LinearLayout openDetailLayout;
        @BindView(R.id.hide_1)
        TextView hide1;
        @BindView(R.id.hide_2)
        TextView hide2;
        @BindView(R.id.hide_3)
        TextView hide3;
        @BindView(R.id.hide_4)
        TextView hide4;
        @BindView(R.id.hide_5)
        TextView hide5;
        @BindView(R.id.hideLayout)
        LinearLayout hideLayout;

        public MyViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);

        }
    }
}
