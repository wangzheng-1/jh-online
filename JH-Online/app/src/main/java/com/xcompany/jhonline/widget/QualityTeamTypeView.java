package com.xcompany.jhonline.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.xcompany.jhonline.R;
import com.xcompany.jhonline.model.base.Category;
import com.xcompany.jhonline.module.publish.activity.QualityTeamFormActivity;
import com.xcompany.jhonline.network.JHCallback;
import com.xcompany.jhonline.network.JHResponse;
import com.xcompany.jhonline.utils.NullCheck;
import com.xcompany.jhonline.utils.ReleaseConfig;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by xieliang on 2018/12/31 10:53
 */
public class QualityTeamTypeView {
    private LinearLayout llRoot;
    public View mView;
    private Context mContext;
    private LinkedHashMap<Category, List<Category>> subcontractCategory = new LinkedHashMap<>();
    private int flag = 0;

    public QualityTeamTypeView(Context context) {
        mContext = context;
        mView = View.inflate(context, R.layout.view_quality_team, null);
        llRoot = mView.findViewById(R.id.ll_root);
        getMenuData();
    }

    private void getMenuData() {
        subcontractCategory.clear();
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
            View v = View.inflate(mContext, R.layout.view_subcontract_menu_child, null);
            MenuButton button = v.findViewById(R.id.menu_btn);
            button.setTitle(NullCheck.check(category.getName()));
            JHGridView gridView = v.findViewById(R.id.grid_view);
            gridView.setMdatas(categories, "");
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
                Intent intent = new Intent(mContext, QualityTeamFormActivity.class);
                intent.putExtra("cid", category);
                intent.putExtra("inventory", child);
                mContext.startActivity(intent);
                ((Activity) mContext).setResult(1004,intent);
                ((Activity) mContext).finish();
            });
            llRoot.addView(v);
        }
    }

}
