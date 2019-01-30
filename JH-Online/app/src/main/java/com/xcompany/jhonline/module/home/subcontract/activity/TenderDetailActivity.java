package com.xcompany.jhonline.module.home.subcontract.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.xcompany.jhonline.R;
import com.xcompany.jhonline.model.home.TenderDetail;
import com.xcompany.jhonline.network.JHCallback;
import com.xcompany.jhonline.network.JHResponse;
import com.xcompany.jhonline.network.UserService;
import com.xcompany.jhonline.utils.DetailCommonUtils;
import com.xcompany.jhonline.utils.NullCheck;
import com.xcompany.jhonline.utils.ReleaseConfig;
import com.xcompany.jhonline.utils.T;
import com.xcompany.jhonline.widget.MultipleTextView;
import com.xcompany.jhonline.widget.ProjectToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @BindView(R.id.toolbar)
    ProjectToolbar toolbar;
    private String id;
    private TenderDetail detail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tender_detail);
        ButterKnife.bind(this);
        id = getIntent().getStringExtra("id");
        DetailCommonUtils.checkStatus(this);
        DetailCommonUtils.saveAndUnSave(this, id, "1");
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
//                        T.showToast(TenderDetailActivity.this, response.getException().getMessage());
                    }
                });
    }

    private void initView() {
        if(TextUtils.equals(detail.getIssue(),"0")){
            toolbar.setToolbar_right("取消收藏");
        }else {
            toolbar.setToolbar_right("收藏");
        }
        tvName.setContentText(detail.getName());
        tvCompany.setContentText(detail.getCompany());
        tvAcreage.setContentText(detail.getAcreage());
        tvContacts.setContentText(detail.getContacts());
        tvInventory.setContentText(detail.getInventory());
        tvEntryTime.setContentText(detail.getEntryTime());
        tvLinkman.setContentText(detail.getLinkman());
        if(TextUtils.equals(detail.getSign(),"0")){
            tvTelephone.setText(NullCheck.check(detail.getTelephone()));
        }else {
            tvTelephone.setText("查看电话");
        }
        tvEmail.setContentText(detail.getEmail());
        tvExplain.setText(detail.getExplain());
    }
    @OnClick({R.id.tv_telephone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_telephone:
                if(TextUtils.equals(detail.getSign(),"0")){
                    String telephone = detail.getTelephone();
                    if (!TextUtils.isEmpty(telephone)) {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + telephone));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("提示");
                    builder.setMessage("查看电话需要消耗5积分");
                    builder.setNegativeButton("取消", null);
                    builder.setPositiveButton("确定", ((dialog, which) -> {
                        OkGo.<JHResponse<String>>post(ReleaseConfig.baseUrl() + "User/consume")
                                .params("uid", UserService.getInstance().getUid())
                                .params("fid", id)
                                .params("type", "1")
                                .execute(new JHCallback<JHResponse<String>>() {
                                    @Override
                                    public void onSuccess(Response<JHResponse<String>> response) {
                                        detail.setSign("0");
                                        tvTelephone.setText(NullCheck.check(detail.getTelephone()));
                                        String telephone = detail.getTelephone();
                                        if (!TextUtils.isEmpty(telephone)) {
                                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + telephone));
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                        }
                                    }

                                    @Override
                                    public void onError(Response<JHResponse<String>> response) {
                                        T.showToast(TenderDetailActivity.this, "积分不足！");
                                    }
                                });
                    }));
                    builder.show();
                }
                break;
        }
    }

}
