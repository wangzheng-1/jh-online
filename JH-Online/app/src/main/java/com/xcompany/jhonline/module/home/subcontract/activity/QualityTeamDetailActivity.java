package com.xcompany.jhonline.module.home.subcontract.activity;

import android.content.DialogInterface;
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
import com.xcompany.jhonline.model.home.QualityTeamDetail;
import com.xcompany.jhonline.network.JHCallback;
import com.xcompany.jhonline.network.JHResponse;
import com.xcompany.jhonline.network.UserService;
import com.xcompany.jhonline.utils.DetailCommonUtils;
import com.xcompany.jhonline.utils.GlideUtil;
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
public class QualityTeamDetailActivity extends AppCompatActivity {
    @BindView(R.id.tv_inventory)
    TextView tvInventory;
    @BindView(R.id.tv_idea)
    TextView tvIdea;
    @BindView(R.id.tv_enounce)
    TextView tvEnounce;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_service)
    TextView tvService;
    @BindView(R.id.tv_pay)
    TextView tvPay;
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
    @BindView(R.id.tv_image_remark)
    TextView tvImageRemark;
    @BindView(R.id.toolbar)
    ProjectToolbar toolbar;
    private String id;
    private QualityTeamDetail qualityTeamDetail;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quality_team_detail);
        ButterKnife.bind(this);
        id = getIntent().getStringExtra("id");
        DetailCommonUtils.checkStatus(this);
        DetailCommonUtils.saveAndUnSave(this, id, "0");
        getData();

    }

    public void getData() {
        OkGo.<JHResponse<QualityTeamDetail>>post(ReleaseConfig.baseUrl() + "builder/builderDetails")
                .tag(this)
                .params("id", id)
                .execute(new JHCallback<JHResponse<QualityTeamDetail>>() {
                    @Override
                    public void onSuccess(Response<JHResponse<QualityTeamDetail>> response) {
                        qualityTeamDetail = response.body().getMsg();
                        initView();
                    }

                    @Override
                    public void onError(Response<JHResponse<QualityTeamDetail>> response) {
                        T.showToast(QualityTeamDetailActivity.this, response.getException().getMessage());
                    }
                });
    }

    private void initView() {
        if(TextUtils.equals(qualityTeamDetail.getIssue(),"0")){
            toolbar.setToolbar_right("取消收藏");
        }else {
            toolbar.setToolbar_right("收藏");
        }
        tvInventory.setText(NullCheck.check("主要承建单项：", qualityTeamDetail.getInventory()));
        tvIdea.setText(NullCheck.check("经营理念：", qualityTeamDetail.getIdea()));
        tvEnounce.setText(NullCheck.check("诚信宣言：", qualityTeamDetail.getEnounce()));
        tvAddress.setText(NullCheck.check("籍贯：", qualityTeamDetail.getContacts()));
        tvService.setText(NullCheck.check("意向服务范围：", qualityTeamDetail.getService()));
        if (TextUtils.equals("0.00", qualityTeamDetail.getPay())) {
            tvPay.setText("是否垫资：不可垫资");
        } else {
            tvPay.setText("是否垫资：可垫资");
        }
        tvContacts.setText(NullCheck.check("常驻地：", qualityTeamDetail.getContacts()));
        tvLinkman.setText(NullCheck.check("联系人：", qualityTeamDetail.getLinkman()));
        if(TextUtils.equals(qualityTeamDetail.getSign(),"0")){
            tvTelephone.setText(NullCheck.check(qualityTeamDetail.getTelephone()));
        }else {
            tvTelephone.setText("查看电话");
        }
        tvExplain.setText(NullCheck.check(qualityTeamDetail.getExplain()));
        List<QualityTeamDetail.CaseBean> list = qualityTeamDetail.getCaseX();
        if (list.size() == 0) {
            llImage1.setVisibility(View.INVISIBLE);
            llImage2.setVisibility(View.INVISIBLE);
            llImage3.setVisibility(View.INVISIBLE);
        } else if (list.size() == 1) {
            llImage1.setVisibility(View.VISIBLE);
            llImage2.setVisibility(View.INVISIBLE);
            llImage3.setVisibility(View.INVISIBLE);
            GlideUtil.LoaderImage(this, list.get(0).getImager(), image1, true);
            tvImage1.setText(NullCheck.check(list.get(0).getEntry()));
            llImage1.setOnClickListener(v -> tvImageRemark.setText(NullCheck.check(list.get(0).getEntry())));
            tvImageRemark.setText(NullCheck.check(list.get(0).getEntry()));
        } else if (list.size() == 2) {
            llImage1.setVisibility(View.VISIBLE);
            llImage2.setVisibility(View.VISIBLE);
            llImage3.setVisibility(View.INVISIBLE);
            GlideUtil.LoaderImage(this, list.get(0).getImager(), image1, true);
            GlideUtil.LoaderImage(this, list.get(1).getImager(), image2, true);
            tvImage1.setText(NullCheck.check(list.get(0).getEntry()));
            tvImage2.setText(NullCheck.check(list.get(1).getEntry()));
            llImage1.setOnClickListener(v -> tvImageRemark.setText(NullCheck.check(list.get(0).getEntry())));
            llImage2.setOnClickListener(v -> tvImageRemark.setText(NullCheck.check(list.get(1).getEntry())));
            tvImageRemark.setText(NullCheck.check(list.get(0).getEntry()));
        } else {
            llImage1.setVisibility(View.VISIBLE);
            llImage2.setVisibility(View.VISIBLE);
            llImage3.setVisibility(View.VISIBLE);
            GlideUtil.LoaderImage(this, list.get(0).getImager(), image1, true);
            GlideUtil.LoaderImage(this, list.get(1).getImager(), image2, true);
            GlideUtil.LoaderImage(this, list.get(2).getImager(), image3, true);
            tvImage1.setText(NullCheck.check(list.get(0).getEntry()));
            tvImage2.setText(NullCheck.check(list.get(1).getEntry()));
            tvImage3.setText(NullCheck.check(list.get(2).getEntry()));
            llImage1.setOnClickListener(v -> tvImageRemark.setText(NullCheck.check(list.get(0).getEntry())));
            llImage2.setOnClickListener(v -> tvImageRemark.setText(NullCheck.check(list.get(1).getEntry())));
            llImage3.setOnClickListener(v -> tvImageRemark.setText(NullCheck.check(list.get(2).getEntry())));
            tvImageRemark.setText(NullCheck.check(list.get(0).getEntry()));
        }
    }

    @OnClick({R.id.tv_telephone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_telephone:
                if(TextUtils.equals(qualityTeamDetail.getSign(),"0")){
                    String telephone = qualityTeamDetail.getTelephone();
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
                                .params("type", "0")
                                .execute(new JHCallback<JHResponse<String>>() {
                                    @Override
                                    public void onSuccess(Response<JHResponse<String>> response) {
                                        qualityTeamDetail.setSign("0");
                                        tvTelephone.setText(NullCheck.check(qualityTeamDetail.getTelephone()));
                                        String telephone = qualityTeamDetail.getTelephone();
                                        if (!TextUtils.isEmpty(telephone)) {
                                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + telephone));
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                        }
                                    }

                                    @Override
                                    public void onError(Response<JHResponse<String>> response) {
                                        T.showToast(QualityTeamDetailActivity.this, "积分不足！");
                                    }
                                });
                    }));
                    builder.show();
                }
                break;
        }
    }
}
