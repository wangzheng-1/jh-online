package com.xcompany.jhonline.module.report.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xcompany.jhonline.R;
import com.xcompany.jhonline.model.report.Comment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentAdapter extends BaseAdapter {

    List<Comment> commentList;

    Context context;

    public CommentAdapter(Context context, List<Comment> commentList) {
        this.context = context;
        this.commentList = commentList;
    }

    @Override
    public int getCount() {
        return commentList.size();
    }

    @Override
    public Object getItem(int position) {
        return commentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final MyViewHolder myViewHolder;
        final Comment comment = commentList.get(position);
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.fragment_report_comment_item, null);
            myViewHolder = new MyViewHolder(convertView);
            convertView.setTag(myViewHolder);
        } else {
            myViewHolder = (MyViewHolder) convertView.getTag();
        }
        myViewHolder.commentPersonName.setText(comment.getUid()+":");
        myViewHolder.commentContent.setText(comment.getBusiness());
        return convertView;
    }

    class MyViewHolder {
        @BindView(R.id.commentPersonName)
        TextView commentPersonName;
        @BindView(R.id.commentContent)
        TextView commentContent;

        MyViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
