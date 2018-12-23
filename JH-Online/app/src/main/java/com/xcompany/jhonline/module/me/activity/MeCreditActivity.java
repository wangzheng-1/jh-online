package com.xcompany.jhonline.module.me.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xcompany.jhonline.R;
import com.xcompany.jhonline.base.BaseActivity;
import com.xcompany.jhonline.model.base.Model;
import com.xcompany.jhonline.model.me.MeCreditDetailBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MeCreditActivity extends BaseActivity {

    MyAdapter adapter;
    @BindView(R.id.backHomeLayout)
    LinearLayout backHomeLayout;
    @BindView(R.id.reportTitleText)
    TextView reportTitleText;
    @BindView(R.id.titleLayout)
    RelativeLayout titleLayout;
    @BindView(R.id.creditText)
    TextView creditText;
    @BindView(R.id.creditDetailTitle)
    LinearLayout creditDetailTitle;
    @BindView(R.id.creditDetailList)
    ListView creditDetailList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_credit);
        ButterKnife.bind(this);
        initAdapter();
    }

    private void initAdapter() {
        List<MeCreditDetailBean> meCreditDetailBeans = new ArrayList<>();
        meCreditDetailBeans.add(new MeCreditDetailBean("签到",12));
        meCreditDetailBeans.add(new MeCreditDetailBean("赠送",12));
        meCreditDetailBeans.add(new MeCreditDetailBean("查看手机号",26));
        adapter = new MyAdapter(this, meCreditDetailBeans);
        creditDetailList.setAdapter(adapter);
    }

    @OnClick({R.id.backHomeLayout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backHomeLayout:
                onBackPressed();
                break;
        }
    }

    class MyAdapter extends BaseAdapter {
        private List<MeCreditDetailBean> list = null;
        private Context mContext;

        public MyAdapter(Context mContext, List<MeCreditDetailBean> list) {
            this.mContext = mContext;
            this.list = list;
        }

        public void updateListView(List<MeCreditDetailBean> list) {
            this.list = list;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return this.list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder = null;
            MeCreditDetailBean meCreditDetailBean = list.get(position);
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(
                        R.layout.activity_me_credit_list, null,
                        true);
                viewHolder.creditDetailTitle = (TextView) convertView
                        .findViewById(R.id.creditDetailTitle);
                viewHolder.creditDetailNum = (TextView) convertView
                        .findViewById(R.id.creditDetailNum);
                convertView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.creditDetailTitle.setText(meCreditDetailBean.getDetailName());
            viewHolder.creditDetailNum.setText(meCreditDetailBean.getDetailNum() + "");
            return convertView;

        }

        class ViewHolder {
            TextView creditDetailTitle;
            TextView creditDetailNum;

        }
    }
}
