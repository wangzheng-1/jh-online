package com.xcompany.jhonline.module.home.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xcompany.jhonline.R;
import com.xcompany.jhonline.model.home.Hiring;
import com.xcompany.jhonline.utils.NullCheck;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HomeListAdapter extends BaseAdapter {
    private List<Hiring> mdatas;
    private Context context;

    public HomeListAdapter(Context context, List<Hiring> mdatas) {
        this.mdatas = mdatas;
        this.context = context;
    }

    public List<Hiring> getMdatas() {
        return mdatas;
    }

    public void setMdatas(List<Hiring> mdatas) {
        this.mdatas = mdatas;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mdatas != null ? mdatas.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return mdatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_home, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Hiring bean = mdatas.get(position);
        holder.name.setText(NullCheck.check(bean.getName()));
        holder.cid.setText(NullCheck.check("劳务工种：", bean.getCid()));
        holder.contacts.setText(NullCheck.check("项目地址：", bean.getContacts()));
        holder.entryTime.setText(NullCheck.check("发布时间：", bean.getEntryTime()));
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.cid)
        TextView cid;
        @BindView(R.id.contacts)
        TextView contacts;
        @BindView(R.id.entryTime)
        TextView entryTime;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
