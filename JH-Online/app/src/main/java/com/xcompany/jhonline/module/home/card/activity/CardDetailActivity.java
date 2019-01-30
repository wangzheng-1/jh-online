package com.xcompany.jhonline.module.home.card.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.xcompany.jhonline.R;
import com.xcompany.jhonline.model.home.CardDetail;
import com.xcompany.jhonline.network.JHCallback;
import com.xcompany.jhonline.network.JHResponse;
import com.xcompany.jhonline.network.UserService;
import com.xcompany.jhonline.utils.GlideUtil;
import com.xcompany.jhonline.utils.NullCheck;
import com.xcompany.jhonline.utils.ReleaseConfig;
import com.xcompany.jhonline.utils.T;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xieliang on 2018/12/13 00:26
 */
public class CardDetailActivity extends AppCompatActivity {

    @BindView(R.id.iv_image)
    ImageView ivImage;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_company)
    TextView tvCompany;
    @BindView(R.id.tv_telephone)
    TextView tvTelephone;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_email)
    TextView tvEmail;
    @BindView(R.id.tv_business)
    TextView tvBusiness;
    private CardDetail detail;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_detail);
        ButterKnife.bind(this);
        getData();
    }

    public void getData() {
        OkGo.<JHResponse<CardDetail>>post(ReleaseConfig.baseUrl() + "Forum/cardList")
                .tag(this)
                .params("uid",UserService.getInstance().getUid())
                .execute(new JHCallback<JHResponse<CardDetail>>() {
                    @Override
                    public void onSuccess(Response<JHResponse<CardDetail>> response) {
                        detail = response.body().getMsg();
                        initView();
                    }

                    @Override
                    public void onError(Response<JHResponse<CardDetail>> response) {
//                        T.showToast(CardDetailActivity.this, response.getException().getMessage());
                    }
                });
    }

    private void initView() {
        tvName.setText(NullCheck.check(detail.getName()));
        tvCompany.setText(NullCheck.check(detail.getCompany()));
        tvBusiness.setText(NullCheck.check(detail.getBusiness()));
        tvEmail.setText(NullCheck.check(detail.getEmail()));
        GlideUtil.LoaderImage(this,detail.getImage(),ivImage,false);
        List<String> contacts = detail.getContacts();
        String text ="";
        for (int i = 0;i<contacts.size();i++){
            text+=contacts.get(i);
        }
        tvAddress.setText(text);
    }
}
