package com.xcompany.jhonline.module.home.labourWorker.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.widget.TextView;

import com.xcompany.jhonline.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xieliang on 2018/12/8 10:15
 */
public class HiringDetailActivity extends AppCompatActivity {
    @BindView(R.id.tv_notice)
    TextView tvNotice;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hiring_detail);
        ButterKnife.bind(this);

        Spanned str1 = Html.fromHtml("<strong><font color=#FF4500>" + "温馨提示:" + "</font></strong>进场请先核实对方身份，过往工程，拒绝先打路费现象，谨防受骗");
        tvNotice.setText(str1);
    }
}
