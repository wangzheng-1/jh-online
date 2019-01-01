package com.xcompany.jhonline.module.publish.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xcompany.jhonline.R;
import com.xcompany.jhonline.model.base.Category;
import com.xcompany.jhonline.model.home.City;
import com.xcompany.jhonline.utils.NullCheck;
import com.xcompany.jhonline.widget.CleanEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xieliang on 2019/1/1 10:14
 */
public class QualityTeamFormActivity extends AppCompatActivity {
    @BindView(R.id.tv_cid)
    TextView tvCid;
    @BindView(R.id.et_name)
    CleanEditText etName;
    @BindView(R.id.tv_inventory)
    TextView tvInventory;
    @BindView(R.id.et_pay)
    CleanEditText etPay;
    @BindView(R.id.tv_service)
    TextView tvService;
    @BindView(R.id.tv_example)
    TextView tvExample;
    @BindView(R.id.et_idea)
    CleanEditText etIdea;
    @BindView(R.id.et_enounce)
    CleanEditText etEnounce;
    @BindView(R.id.tv_native)
    TextView tvNative;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.et_explain)
    EditText etExplain;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.ll_cid)
    LinearLayout llCid;
    private String cid;
    private String inventory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quality_team_form);
        ButterKnife.bind(this);
        Category c1 = (Category) getIntent().getExtras().get("cid");
        cid = c1.getId();
        Category c2 = (Category) getIntent().getExtras().get("inventory");
        inventory = c2.getId();
        tvCid.setText(NullCheck.check("分包承建商信息 匹配优质班组 ", c1.getName()));
        tvInventory.setText(NullCheck.check(c2.getName()));
    }

    @OnClick({R.id.ll_cid, R.id.ll_inventory})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.ll_cid:
                intent = new Intent(this, PublishTypeActivity.class);
                startActivityForResult(intent, 1003);
                break;
            case R.id.ll_inventory:
                intent = new Intent(this, InventoryActivity.class);
                startActivityForResult(intent, 1005);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1003 && resultCode == 1004) {
            Category parent = (Category) data.getExtras().get("cid");
            cid = parent.getId();
            tvCid.setText(NullCheck.check("分包承建商信息 匹配优质班组 ", parent.getName()));

            Category child = (Category) data.getExtras().get("inventory");
            inventory = child.getId();
            tvInventory.setText(NullCheck.check(child.getName()));

        } else if (requestCode == 1005 && resultCode == 1006) {
            Category parent = (Category) data.getExtras().get("cid");
            cid = parent.getId();
            tvCid.setText(NullCheck.check("分包承建商信息 匹配优质班组 ", parent.getName()));

            inventory = data.getStringExtra("id");
            String name = data.getStringExtra("name");
            tvInventory.setText(NullCheck.check(name));
        }
    }
}
