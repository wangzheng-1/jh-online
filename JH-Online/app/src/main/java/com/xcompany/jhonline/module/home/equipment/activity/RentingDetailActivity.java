package com.xcompany.jhonline.module.home.equipment.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.xcompany.jhonline.R;
import com.xcompany.jhonline.app.GlideApp;
import com.xcompany.jhonline.model.home.RentingDetail;
import com.xcompany.jhonline.network.JHCallback;
import com.xcompany.jhonline.network.JHResponse;
import com.xcompany.jhonline.network.UserService;
import com.xcompany.jhonline.utils.DetailCommonUtils;
import com.xcompany.jhonline.utils.NullCheck;
import com.xcompany.jhonline.utils.ReleaseConfig;
import com.xcompany.jhonline.utils.T;
import com.xcompany.jhonline.widget.ProjectToolbar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xieliang on 2018/12/8 10:15
 */
public class RentingDetailActivity extends AppCompatActivity {
    @BindView(R.id.tv_classname)
    TextView tvClassname;
    @BindView(R.id.tv_entryTime)
    TextView tvEntryTime;
    @BindView(R.id.tv_contacts)
    TextView tvContacts;
    @BindView(R.id.tv_linkman)
    TextView tvLinkman;
    @BindView(R.id.tv_telephone)
    TextView tvTelephone;
    @BindView(R.id.tv_explain)
    TextView tvExplain;
    @BindView(R.id.image1)
    ImageView image1;
    @BindView(R.id.tv_image1)
    TextView tvImage1;
    @BindView(R.id.ll_image1)
    LinearLayout llImage1;
    @BindView(R.id.image2)
    ImageView image2;
    @BindView(R.id.tv_image2)
    TextView tvImage2;
    @BindView(R.id.ll_image2)
    LinearLayout llImage2;
    @BindView(R.id.image3)
    ImageView image3;
    @BindView(R.id.tv_image3)
    TextView tvImage3;
    @BindView(R.id.ll_image3)
    LinearLayout llImage3;
    @BindView(R.id.toolbar)
    ProjectToolbar toolbar;
    private String id;
    private RentingDetail rentingDetail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renting_detail);
        ButterKnife.bind(this);
        id = getIntent().getStringExtra("id");
        DetailCommonUtils.checkStatus(this);
        DetailCommonUtils.saveAndUnSave(this, id, "2");
        getData();
    }

    public void getData() {
        OkGo.<JHResponse<RentingDetail>>post(ReleaseConfig.baseUrl() + "Lease/LeaseDetails")
                .tag(this)
                .params("id", id)
                .execute(new JHCallback<JHResponse<RentingDetail>>() {
                    @Override
                    public void onSuccess(Response<JHResponse<RentingDetail>> response) {
                        rentingDetail = response.body().getMsg();
                        initView();
                    }

                    @Override
                    public void onError(Response<JHResponse<RentingDetail>> response) {
//                        T.showToast(RentingDetailActivity.this, response.getException().getMessage());
                    }
                });
    }

    private void initView() {
        if(TextUtils.equals(rentingDetail.getIssue(),"0")){
            toolbar.setToolbar_right("取消收藏");
        }else {
            toolbar.setToolbar_right("收藏");
        }
        tvClassname.setText(NullCheck.check("商户出租产品：", rentingDetail.getClassname()));
        tvEntryTime.setText(NullCheck.check("商户入驻时间：", rentingDetail.getEntryTime()));
        tvContacts.setText(NullCheck.check("地址：", rentingDetail.getContacts()));
        tvLinkman.setText(NullCheck.check("联系人：", rentingDetail.getLinkman()));
        if(TextUtils.equals(rentingDetail.getSign(),"0")){
            tvTelephone.setText(NullCheck.check(rentingDetail.getTelephone()));
        }else {
            tvTelephone.setText("查看电话");
        }
        tvExplain.setText(NullCheck.check(rentingDetail.getExplain()));
        List<RentingDetail.CaseBean> caseX = rentingDetail.getCaseX();
        if (caseX.size() == 0) {
            llImage1.setVisibility(View.INVISIBLE);
            llImage2.setVisibility(View.INVISIBLE);
            llImage3.setVisibility(View.INVISIBLE);
        } else if (caseX.size() == 1) {
            llImage1.setVisibility(View.VISIBLE);
            llImage2.setVisibility(View.INVISIBLE);
            llImage3.setVisibility(View.INVISIBLE);
            GlideApp.with(this).load(caseX.get(0).getImager())
                    .centerCrop()
                    .into(image1);
            tvImage1.setText(NullCheck.check(caseX.get(0).getCid()));
        } else if (caseX.size() == 2) {
            llImage1.setVisibility(View.VISIBLE);
            llImage2.setVisibility(View.VISIBLE);
            llImage3.setVisibility(View.INVISIBLE);
            GlideApp.with(this).load(caseX.get(0).getImager())
                    .centerCrop()
                    .into(image1);
            GlideApp.with(this).load(caseX.get(1).getImager())
                    .centerCrop()
                    .into(image2);
            tvImage1.setText(NullCheck.check(caseX.get(0).getCid()));
            tvImage2.setText(NullCheck.check(caseX.get(1).getCid()));
        } else {
            llImage1.setVisibility(View.VISIBLE);
            llImage2.setVisibility(View.VISIBLE);
            llImage3.setVisibility(View.VISIBLE);
            GlideApp.with(this).load(caseX.get(0).getImager())
                    .centerCrop()
                    .into(image1);
            GlideApp.with(this).load(caseX.get(1).getImager())
                    .centerCrop()
                    .into(image2);
            GlideApp.with(this).load(caseX.get(2).getImager())
                    .centerCrop()
                    .into(image3);
            tvImage1.setText(NullCheck.check(caseX.get(0).getCid()));
            tvImage2.setText(NullCheck.check(caseX.get(1).getCid()));
            tvImage3.setText(NullCheck.check(caseX.get(2).getCid()));
        }
    }
    @OnClick({R.id.tv_telephone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_telephone:
                if(TextUtils.equals(rentingDetail.getSign(),"0")){
                    String telephone = rentingDetail.getTelephone();
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
                                .params("type", "2")
                                .execute(new JHCallback<JHResponse<String>>() {
                                    @Override
                                    public void onSuccess(Response<JHResponse<String>> response) {
                                        rentingDetail.setSign("0");
                                        tvTelephone.setText(NullCheck.check(rentingDetail.getTelephone()));
                                        String telephone = rentingDetail.getTelephone();
                                        if (!TextUtils.isEmpty(telephone)) {
                                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + telephone));
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                        }
                                    }

                                    @Override
                                    public void onError(Response<JHResponse<String>> response) {
                                        T.showToast(RentingDetailActivity.this, "积分不足！");
                                    }
                                });
                    }));
                    builder.show();
                }
                break;
        }
    }

}
