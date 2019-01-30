package com.xcompany.jhonline.module.home.blacklist.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.xcompany.jhonline.R;
import com.xcompany.jhonline.app.GlideApp;
import com.xcompany.jhonline.model.home.BlackDetail;
import com.xcompany.jhonline.network.JHCallback;
import com.xcompany.jhonline.network.JHResponse;
import com.xcompany.jhonline.utils.NullCheck;
import com.xcompany.jhonline.utils.ReleaseConfig;
import com.xcompany.jhonline.utils.T;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xieliang on 2018/12/8 10:15
 */
public class BlackListDetailActivity extends AppCompatActivity {
    @BindView(R.id.tv_explain)
    TextView tvExplain;
    @BindView(R.id.image1)
    ImageView image1;
    @BindView(R.id.image2)
    ImageView image2;
    @BindView(R.id.image3)
    ImageView image3;
    private String id;
    private BlackDetail siteMatchingDetail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_black_list_detail);
        ButterKnife.bind(this);
        id = getIntent().getStringExtra("id");
        getData();
    }

    public void getData() {
        OkGo.<JHResponse<BlackDetail>>post(ReleaseConfig.baseUrl() + "Black/blackDetails")
                .tag(this)
                .params("id", id)
                .execute(new JHCallback<JHResponse<BlackDetail>>() {
                    @Override
                    public void onSuccess(Response<JHResponse<BlackDetail>> response) {
                        siteMatchingDetail = response.body().getMsg();
                        initView();
                    }

                    @Override
                    public void onError(Response<JHResponse<BlackDetail>> response) {
//                        T.showToast(BlackListDetailActivity.this, response.getException().getMessage());
                    }
                });
    }

    private void initView() {
        tvExplain.setText(NullCheck.check(siteMatchingDetail.getExplain()));
        // 设计图上应该是最多展示3个图片的，但接口返回的不是数组。。而且和其他模块不一样不是直接可以使用的图片地址，暂时保留多图原代码逻辑
        String image = siteMatchingDetail.getImage();
        List<String> list = new ArrayList<>();
        list.add(ReleaseConfig.baseUrl().replace("/Api/", "") + image);
        if (list.size() == 0) {
            image1.setVisibility(View.INVISIBLE);
            image2.setVisibility(View.INVISIBLE);
            image3.setVisibility(View.INVISIBLE);
        } else if (list.size() == 1) {
            image1.setVisibility(View.VISIBLE);
            image2.setVisibility(View.INVISIBLE);
            image3.setVisibility(View.INVISIBLE);
            GlideApp.with(this).load(list.get(0))
                    .centerCrop()
                    .into(image1);
        } else if (list.size() == 2) {
            image1.setVisibility(View.VISIBLE);
            image2.setVisibility(View.VISIBLE);
            image3.setVisibility(View.INVISIBLE);
            GlideApp.with(this).load(list.get(0))
                    .centerCrop()
                    .into(image1);
            GlideApp.with(this).load(list.get(1))
                    .centerCrop()
                    .into(image2);
        } else {
            image1.setVisibility(View.VISIBLE);
            image2.setVisibility(View.VISIBLE);
            image3.setVisibility(View.VISIBLE);
            GlideApp.with(this).load(list.get(0))
                    .centerCrop()
                    .into(image1);
            GlideApp.with(this).load(list.get(1))
                    .centerCrop()
                    .into(image2);
            GlideApp.with(this).load(list.get(2))
                    .centerCrop()
                    .into(image2);
        }
    }

}
