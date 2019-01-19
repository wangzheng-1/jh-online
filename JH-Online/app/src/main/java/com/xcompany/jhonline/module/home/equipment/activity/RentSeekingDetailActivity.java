package com.xcompany.jhonline.module.home.equipment.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.xcompany.jhonline.R;
import com.xcompany.jhonline.model.home.RentSeekingDetail;
import com.xcompany.jhonline.network.JHCallback;
import com.xcompany.jhonline.network.JHResponse;
import com.xcompany.jhonline.utils.DetailCommonUtils;
import com.xcompany.jhonline.utils.NullCheck;
import com.xcompany.jhonline.utils.ReleaseConfig;
import com.xcompany.jhonline.utils.T;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
        DetailCommonUtils.checkStatus(this);
        DetailCommonUtils.saveAndUnSave(this, id, "3");
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
    @OnClick({R.id.tv_telephone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_telephone:
                String telephone = rentingDetail.getTelephone();
                if (!TextUtils.isEmpty(telephone)) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + telephone));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                break;
        }
    }
}
