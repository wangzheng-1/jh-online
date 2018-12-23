package com.xcompany.jhonline.module.home.certificate.acitivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.xcompany.jhonline.R;
import com.xcompany.jhonline.model.home.QualificationHandleDetail;
import com.xcompany.jhonline.network.JHCallback;
import com.xcompany.jhonline.network.JHResponse;
import com.xcompany.jhonline.utils.NullCheck;
import com.xcompany.jhonline.utils.ReleaseConfig;
import com.xcompany.jhonline.utils.T;
import com.xcompany.jhonline.widget.MultipleTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xieliang on 2018/12/8 10:15
 */
public class QualificationHandleDetailActivity extends AppCompatActivity {
    @BindView(R.id.tv_linkman)
    MultipleTextView tvLinkman;
    @BindView(R.id.tv_contacts)
    MultipleTextView tvContacts;
    @BindView(R.id.tv_telephone)
    TextView tvTelephone;
    @BindView(R.id.tv_explain)
    TextView tvExplain;
    private String id;
    private QualificationHandleDetail detail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qualification_handle_detail);
        ButterKnife.bind(this);
        id = getIntent().getStringExtra("id");
        getData();
    }

    public void getData() {
        OkGo.<JHResponse<QualificationHandleDetail>>post(ReleaseConfig.baseUrl() + "Licence/hireDetails")
                .tag(this)
                .params("id", id)
                .params("type", 1)
                .execute(new JHCallback<JHResponse<QualificationHandleDetail>>() {
                    @Override
                    public void onSuccess(Response<JHResponse<QualificationHandleDetail>> response) {
                        detail = response.body().getMsg();
                        initView();
                    }

                    @Override
                    public void onError(Response<JHResponse<QualificationHandleDetail>> response) {
                        T.showToast(QualificationHandleDetailActivity.this, response.getException().getMessage());
                    }
                });
    }

    private void initView() {
        tvLinkman.setContentText(detail.getLinkman());
        tvContacts.setContentText(detail.getContacts());
        tvTelephone.setText(NullCheck.check(detail.getTelephone()));
        tvExplain.setText(NullCheck.check(detail.getExplain()));
    }
}
