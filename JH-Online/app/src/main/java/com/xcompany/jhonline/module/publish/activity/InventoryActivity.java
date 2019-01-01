package com.xcompany.jhonline.module.publish.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.xcompany.jhonline.R;
import com.xcompany.jhonline.model.base.Category;
import com.xcompany.jhonline.network.JHCallback;
import com.xcompany.jhonline.network.JHResponse;
import com.xcompany.jhonline.utils.NullCheck;
import com.xcompany.jhonline.utils.ReleaseConfig;
import com.xcompany.jhonline.utils.T;
import com.xcompany.jhonline.widget.JHGridView2;
import com.xcompany.jhonline.widget.MenuButton;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xieliang on 2019/1/1 11:56
 */
public class InventoryActivity extends AppCompatActivity {
    @BindView(R.id.ll_root)
    LinearLayout llRoot;
    private LinkedHashMap<Category, List<Category>> subcontractCategory = new LinkedHashMap<>();
    private int flag = 0;
    List<JHGridView2> jhGridViewList = new ArrayList<>();
    private Category parent;
    private List<String> ids = new ArrayList<>();
    private List<String> names = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        ButterKnife.bind(this);
        getMenuData();
    }

    private void getMenuData() {
        OkGo.<JHResponse<List<Category>>>post(ReleaseConfig.baseUrl() + "class/classList")
                .tag(this)
                .params("type", 6)
                .params("pid", 0)
                .execute(new JHCallback<JHResponse<List<Category>>>() {
                    @Override
                    public void onSuccess(Response<JHResponse<List<Category>>> response) {
                        List<Category> categories = response.body().getMsg();
                        flag = categories.size();
                        for (int i = 0; i < categories.size(); i++) {
                            Category category = categories.get(i);
                            getSubMenuData(category);
                        }
                    }
                });
    }

    private void getSubMenuData(Category category) {
        List<Category> list = new ArrayList<>();
        subcontractCategory.put(category, list);
        OkGo.<JHResponse<List<Category>>>post(ReleaseConfig.baseUrl() + "class/classList")
                .tag(this)
                .params("type", 6)
                .params("pid", category.getId())
                .execute(new JHCallback<JHResponse<List<Category>>>() {
                    @Override
                    public void onSuccess(Response<JHResponse<List<Category>>> response) {
                        flag--;
                        list.addAll(response.body().getMsg());
                        if (flag == 0) {
                            initView();
                        }
                    }
                });
    }

    private void initView() {
        for (Category category : subcontractCategory.keySet()) {
            List<Category> categories = subcontractCategory.get(category);
            View v = View.inflate(this, R.layout.view_subcontract_menu_child2, null);
            MenuButton button = v.findViewById(R.id.menu_btn);
            button.setTitle(NullCheck.check(category.getName()));
            JHGridView2 gridView = v.findViewById(R.id.grid_view);
            jhGridViewList.add(gridView);
            gridView.setMdatas(categories);
            button.setOnClickListener(v1 -> {
                if (gridView.getVisibility() == View.VISIBLE) {
                    gridView.setVisibility(View.GONE);
                    button.setChecked(false);
                } else {
                    gridView.setVisibility(View.VISIBLE);
                    button.setChecked(true);
                }
            });
            gridView.setCheckChangeListener(child -> {
                if (parent != category) {
                    parent = category;
                    ids.clear();
                    names.clear();
                }
                if (ids.contains(child.getId())) {
                    ids.remove(child.getId());
                    names.remove(child.getName());
                } else {
                    if (ids.size() > 2) {
                        T.showToast(this, "最多选中3个2级类目");
                        return;
                    } else {
                        ids.add(child.getId());
                        names.add(child.getName());
                    }
                }

                for (int i = 0; i < jhGridViewList.size(); i++) {
                    jhGridViewList.get(i).setIds(ids);
                }
            });
            llRoot.addView(v);
        }
    }


    @OnClick({R.id.tv_submit, R.id.rl_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_submit:
                if (ids.size() == 0 || ids.size() != names.size()) {
                    return;
                }
                String id = "";
                String name = "";
                for (int i = 0; i < ids.size(); i++) {
                    if (i != 0) {
                        id += ",";
                        name += ",";
                    }
                    id += ids.get(i);
                    name += names.get(i);
                }
                Intent intent = new Intent();
                intent.putExtra("id", id);
                intent.putExtra("name", name);
                intent.putExtra("cid", parent);
                setResult(1006,intent);
                finish();
                break;
            case R.id.rl_submit:
                break;
        }
    }
}
