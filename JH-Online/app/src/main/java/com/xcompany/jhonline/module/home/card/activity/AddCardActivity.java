package com.xcompany.jhonline.module.home.card.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.xcompany.jhonline.R;
import com.xcompany.jhonline.widget.CleanEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xieliang on 2018/12/13 00:26
 */
public class AddCardActivity extends AppCompatActivity {
    @BindView(R.id.et_name)
    CleanEditText etName;
    @BindView(R.id.submit)
    TextView submit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_add);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.et_name, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_name:
                break;
            case R.id.submit:
                Intent intent = new Intent(this,CardDetailActivity.class);
                startActivity(intent);
                break;
        }
    }
}
