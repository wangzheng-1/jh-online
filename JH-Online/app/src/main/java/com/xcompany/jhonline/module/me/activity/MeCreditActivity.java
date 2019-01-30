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

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.xcompany.jhonline.R;
import com.xcompany.jhonline.base.BaseActivity;
import com.xcompany.jhonline.model.base.Model;
import com.xcompany.jhonline.model.me.IntegralEnum;
import com.xcompany.jhonline.model.me.MeCreditDetailBean;
import com.xcompany.jhonline.module.me.fragment.MeFragment;
import com.xcompany.jhonline.network.JHCallback;
import com.xcompany.jhonline.network.JHResponse;
import com.xcompany.jhonline.network.UserService;
import com.xcompany.jhonline.utils.ReleaseConfig;
import com.xcompany.jhonline.utils.T;

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

    List<MeCreditDetailBean> meCreditDetailBeans;

    private Integer totalIntegral = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_credit);
        ButterKnife.bind(this);
        initAdapter();
        getCreditData();

    }

    private void initAdapter() {
        meCreditDetailBeans = new ArrayList<>();
        adapter = new MyAdapter(this, meCreditDetailBeans);
        creditDetailList.setAdapter(adapter);
    }
    private void getCreditData(){
        OkGo.<JHResponse<List<MeCreditDetailBean>>>post(ReleaseConfig.baseUrl() + "User/signList")
                .tag(this)
                .params("uid",UserService.getInstance().getUid())
                .execute(new JHCallback<JHResponse<List<MeCreditDetailBean>>>() {
                    @Override
                    public void onSuccess(Response<JHResponse<List<MeCreditDetailBean>>> response) {

                        List<MeCreditDetailBean> meCreditDetailBeanList = response.body().getMsg();
                        meCreditDetailBeans.addAll(meCreditDetailBeanList);
                        adapter.notifyDataSetChanged();

                        if(meCreditDetailBeanList != null && meCreditDetailBeanList.size() > 0){
                            for(MeCreditDetailBean meCreditDetailBean : meCreditDetailBeanList){
                                totalIntegral += meCreditDetailBean.getSign();

                            }
                        }
                        creditText.setText("" + totalIntegral);

                    }

                    @Override
                    public void onError(Response<JHResponse<List<MeCreditDetailBean>>> response) {
//                        T.showToast(MeCreditActivity.this, response.getException().getMessage());

                    }
                });
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
            IntegralEnum integralEnum = IntegralEnum.getIntegralEnum(meCreditDetailBean.getOptxt());
            if(integralEnum == null){
                viewHolder.creditDetailTitle.setText("其他");
            }
            else {
                viewHolder.creditDetailTitle.setText(integralEnum.getName());
            }
            viewHolder.creditDetailNum.setText(meCreditDetailBean.getSign() + "");
            return convertView;

        }

        class ViewHolder {
            TextView creditDetailTitle;
            TextView creditDetailNum;

        }
    }
}
