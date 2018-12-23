package com.xcompany.jhonline.module.home.equipment.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.xcompany.jhonline.R;
import com.xcompany.jhonline.model.home.RentSeekingDetail;
import com.xcompany.jhonline.network.JHCallback;
import com.xcompany.jhonline.network.JHResponse;
import com.xcompany.jhonline.utils.NullCheck;
import com.xcompany.jhonline.utils.ReleaseConfig;
import com.xcompany.jhonline.utils.T;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xieliang on 2018/12/8 10:15
 */
public class RentSeekingDetailActivity extends AppCompatActivity {
    @BindView(R.id.tv_linkman)
    TextView tvLinkman;
    @BindView(R.id.tv_telephone)
    TextView tvTelephone;
    @BindView(R.id.tv_contacts)
    TextView tvContacts;
    @BindView(R.id.tv_entryTime)
    TextView tvEntryTime;
    @BindView(R.id.tv_explain)
    TextView tvExplain;
    private String id;
    private RentSeekingDetail rentingDetail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_seeking_detail);
        ButterKnife.bind(this);
        id = getIntent().getStringExtra("id");
        getData();
    }

    public void getData() {
        OkGo.<JHResponse<RentSeekingDetail>>post(ReleaseConfig.baseUrl() + "Lease/LeaseDetails")
                .tag(this)
                .params("id", id)
                .execute(new JHCallback<JHResponse<RentSeekingDetail>>() {
                    @Override
                    public void onSuccess(Response<JHResponse<RentSeekingDetail>> response) {
                        rentingDetail = response.body().getMsg();
                        initView();
                    }

                    @Override
                    public void onError(Response<JHResponse<RentSeekingDetail>> response) {
                        T.showToast(RentSeekingDetailActivity.this, response.getException().getMessage());
                    }
                });
    }

    private void initView() {
        tvLinkman.setText(NullCheck.check("联系人：", rentingDetail.getLinkman()));
        tvTelephone.setText(NullCheck.check(rentingDetail.getTelephone()));
        tvContacts.setText(NullCheck.check("地址：", rentingDetail.getContacts()));
        tvEntryTime.setText(NullCheck.check("信息有效期：", rentingDetail.getEntryTime()));
        tvExplain.setText(NullCheck.check(rentingDetail.getExplain()));
    }
}
