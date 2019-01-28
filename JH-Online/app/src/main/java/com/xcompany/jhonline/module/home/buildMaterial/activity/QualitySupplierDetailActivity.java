package com.xcompany.jhonline.module.home.buildMaterial.activity;

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
import com.xcompany.jhonline.model.home.QualitySupplierDetail;
import com.xcompany.jhonline.module.home.subcontract.activity.QualityTeamDetailActivity;
import com.xcompany.jhonline.network.JHCallback;
import com.xcompany.jhonline.network.JHResponse;
import com.xcompany.jhonline.network.UserService;
import com.xcompany.jhonline.utils.DetailCommonUtils;
import com.xcompany.jhonline.utils.NullCheck;
import com.xcompany.jhonline.utils.ReleaseConfig;
import com.xcompany.jhonline.utils.T;
import com.xcompany.jhonline.widget.MultipleTextView;
import com.xcompany.jhonline.widget.ProjectToolbar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xieliang on 2018/12/8 10:15
 */
public class QualitySupplierDetailActivity extends AppCompatActivity {
    @BindView(R.id.tv_classname)
    MultipleTextView tvClassname;
    @BindView(R.id.tv_entryTime)
    MultipleTextView tvEntryTime;
    @BindView(R.id.tv_contacts)
    MultipleTextView tvContacts;
    @BindView(R.id.tv_linkman)
    MultipleTextView tvLinkman;
    @BindView(R.id.tv_telephone)
    TextView tvTelephone;
    @BindView(R.id.tv_delivery)
    MultipleTextView tvDelivery;
    @BindView(R.id.tv_door)
    MultipleTextView tvDoor;
    @BindView(R.id.tv_loan)
    MultipleTextView tvLoan;
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
    @BindView(R.id.top_image)
    ImageView topImage;
    @BindView(R.id.top_fix)
    TextView topFix;
    @BindView(R.id.ll_top)
    LinearLayout llTop;
    private String id;
    private QualitySupplierDetail detail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quality_supplier_detail);
        ButterKnife.bind(this);
        id = getIntent().getStringExtra("id");
        DetailCommonUtils.checkStatus(this);
        DetailCommonUtils.saveAndUnSave(this, id, "4");
        getData();
    }

    public void getData() {
        OkGo.<JHResponse<QualitySupplierDetail>>post(ReleaseConfig.baseUrl() + "Supplier/supplierDetails")
                .tag(this)
                .params("id", id)
                .execute(new JHCallback<JHResponse<QualitySupplierDetail>>() {
                    @Override
                    public void onSuccess(Response<JHResponse<QualitySupplierDetail>> response) {
                        detail = response.body().getMsg();
                        initView();
                    }

                    @Override
                    public void onError(Response<JHResponse<QualitySupplierDetail>> response) {
                        T.showToast(QualitySupplierDetailActivity.this, response.getException().getMessage());
                    }
                });
    }

    private void initView() {
        if(TextUtils.equals(detail.getIssue(),"0")){
            toolbar.setToolbar_right("取消收藏");
        }else {
            toolbar.setToolbar_right("收藏");
        }
        tvClassname.setContentText(detail.getClassname());
        tvEntryTime.setContentText(detail.getEntryTime());
        tvContacts.setContentText(detail.getContacts());
        tvLinkman.setContentText(detail.getLinkman());
        if(TextUtils.equals(detail.getSign(),"0")){
            tvTelephone.setText(NullCheck.check(detail.getTelephone()));
        }else {
            tvTelephone.setText("查看电话");
        }
        if (TextUtils.equals("0", detail.getDelivery())) {
            tvDelivery.setContentText("是");
        } else if (TextUtils.equals("1", detail.getDelivery())) {
            tvDelivery.setContentText("否");
        }
        if (TextUtils.equals("0", detail.getDoor())) {
            tvDoor.setContentText("是");
        } else if (TextUtils.equals("1", detail.getDoor())) {
            tvDoor.setContentText("否");
        } else if (TextUtils.equals("2", detail.getDoor())) {
            tvDoor.setContentText("看量需求");
        }
        if (TextUtils.equals("0", detail.getLoan())) {
            tvLoan.setContentText("是");
        } else if (TextUtils.equals("1", detail.getLoan())) {
            tvLoan.setContentText("否");
        } else if (TextUtils.equals("2", detail.getLoan())) {
            tvLoan.setContentText("看量另议");
        }
        tvExplain.setText(NullCheck.check(detail.getExplain()));
        List<QualitySupplierDetail.CaseBean> list = detail.getCaseX();
        if (list.size() == 0) {
            llImage1.setVisibility(View.INVISIBLE);
            llImage2.setVisibility(View.INVISIBLE);
            llImage3.setVisibility(View.INVISIBLE);
        } else if (list.size() == 1) {
            llImage1.setVisibility(View.VISIBLE);
            llImage2.setVisibility(View.INVISIBLE);
            llImage3.setVisibility(View.INVISIBLE);
            GlideApp.with(this).load(list.get(0).getImager())
                    .centerCrop()
                    .into(image1);
            tvImage1.setText(NullCheck.check(list.get(0).getCid()));
        } else if (list.size() == 2) {
            llImage1.setVisibility(View.VISIBLE);
            llImage2.setVisibility(View.VISIBLE);
            llImage3.setVisibility(View.INVISIBLE);
            GlideApp.with(this).load(list.get(0).getImager())
                    .centerCrop()
                    .into(image1);
            GlideApp.with(this).load(list.get(1).getImager())
                    .centerCrop()
                    .into(image2);
            tvImage1.setText(NullCheck.check(list.get(0).getCid()));
            tvImage2.setText(NullCheck.check(list.get(1).getCid()));
        } else {
            llImage1.setVisibility(View.VISIBLE);
            llImage2.setVisibility(View.VISIBLE);
            llImage3.setVisibility(View.VISIBLE);
            GlideApp.with(this).load(list.get(0).getImager())
                    .centerCrop()
                    .into(image1);
            GlideApp.with(this).load(list.get(1).getImager())
                    .centerCrop()
                    .into(image2);
            GlideApp.with(this).load(list.get(2).getImager())
                    .centerCrop()
                    .into(image3);
            tvImage1.setText(NullCheck.check(list.get(0).getCid()));
            tvImage2.setText(NullCheck.check(list.get(1).getCid()));
            tvImage3.setText(NullCheck.check(list.get(2).getCid()));
        }
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
                    builder.setMessage("查看电话需要消耗30积分");
                    builder.setNegativeButton("取消", null);
                    builder.setPositiveButton("确定", ((dialog, which) -> {
                        OkGo.<JHResponse<String>>post(ReleaseConfig.baseUrl() + "User/consume")
                                .params("uid", UserService.getInstance().getUid())
                                .params("fid", id)
                                .params("type", "4")
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
                                        T.showToast(QualitySupplierDetailActivity.this, response.getException().getMessage());
                                    }
                                });
                    }));
                    builder.show();
                }
                break;
        }
    }
}
