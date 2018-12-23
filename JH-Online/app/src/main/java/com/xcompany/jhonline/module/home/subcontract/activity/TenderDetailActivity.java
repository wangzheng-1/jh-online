package com.xcompany.jhonline.module.home.subcontract.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.xcompany.jhonline.R;
import com.xcompany.jhonline.model.home.TenderDetail;
import com.xcompany.jhonline.network.JHCallback;
import com.xcompany.jhonline.network.JHResponse;
import com.xcompany.jhonline.utils.NullCheck;
import com.xcompany.jhonline.utils.ReleaseConfig;
import com.xcompany.jhonline.utils.T;
import com.xcompany.jhonline.widget.MultipleTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xieliang on 2018/12/8 11:02
 */
public class TenderDetailActivity extends AppCompatActivity {
    @BindView(R.id.tv_name)
    MultipleTextView tvName;
    @BindView(R.id.tv_company)
    MultipleTextView tvCompany;
    @BindView(R.id.tv_acreage)
    MultipleTextView tvAcreage;
    @BindView(R.id.tv_contacts)
    MultipleTextView tvContacts;
    @BindView(R.id.tv_inventory)
    MultipleTextView tvInventory;
    @BindView(R.id.tv_entryTime)
    MultipleTextView tvEntryTime;
    @BindView(R.id.tv_linkman)
    MultipleTextView tvLinkman;
    @BindView(R.id.tv_telephone)
    TextView tvTelephone;
    @BindView(R.id.tv_email)
    MultipleTextView tvEmail;
    @BindView(R.id.tv_explain)
    TextView tvExplain;
    private String id;
    private TenderDetail detail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tender_detail);
        ButterKnife.bind(this);
        id = getIntent().getStringExtra("id");
        getData();
    }

    public void getData() {
        OkGo.<JHResponse<TenderDetail>>post(ReleaseConfig.baseUrl() + "builder/tenderingDetails")
                .tag(this)
                .params("id", id)
                .execute(new JHCallback<JHResponse<TenderDetail>>() {
                    @Override
                    public void onSuccess(Response<JHResponse<TenderDetail>> response) {
                        detail = response.body().getMsg();
                        initView();
                    }

                    @Override
                    public void onError(Response<JHResponse<TenderDetail>> response) {
                        T.showToast(TenderDetailActivity.this, response.getException().getMessage());
                    }
                });
    }

    private void initView() {
        tvName.setContentText(detail.getName());
        tvCompany.setContentText(detail.getCompany());
        tvAcreage.setContentText(detail.getAcreage());
        tvContacts.setContentText(detail.getContacts());
        tvInventory.setContentText(detail.getInventory());
        tvEntryTime.setContentText(detail.getEntryTime());
        tvLinkman.setContentText(detail.getLinkman());
        tvTelephone.setText(NullCheck.check(detail.getTelephone()));
        tvEmail.setContentText(detail.getEmail());
        tvExplain.setText(detail.getExplain());
    }

}
