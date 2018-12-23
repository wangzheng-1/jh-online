package com.xcompany.jhonline.module.home.subcontract.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.xcompany.jhonline.R;
import com.xcompany.jhonline.base.BaseFragment;
import com.xcompany.jhonline.model.base.Category;
import com.xcompany.jhonline.model.home.City;
import com.xcompany.jhonline.model.home.QuailtyTeam;
import com.xcompany.jhonline.module.home.base.CityListActivity;
import com.xcompany.jhonline.module.home.subcontract.activity.QualityTeamDetailActivity;
import com.xcompany.jhonline.module.home.subcontract.adapter.QualityTeamAdapter;
import com.xcompany.jhonline.network.JHCallback;
import com.xcompany.jhonline.network.JHResponse;
import com.xcompany.jhonline.utils.DensityUtils;
import com.xcompany.jhonline.utils.NullCheck;
import com.xcompany.jhonline.utils.ReleaseConfig;
import com.xcompany.jhonline.utils.SharedPreferenceUtil;
import com.xcompany.jhonline.utils.T;
import com.xcompany.jhonline.widget.JHGridView;
import com.xcompany.jhonline.widget.MenuButton;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by xieliang on 2018/11/28 23:02
 */
public class QualityTeamFragment extends BaseFragment {
    @BindView(R.id.recycler_list)
    XRecyclerView mRecyclerView;
    @BindView(R.id.root)
    LinearLayout root;
    @BindView(R.id.menu1)
    MenuButton menu1;
    @BindView(R.id.menu2)
    MenuButton menu2;
    @BindView(R.id.menu3)
    MenuButton menu3;
    @BindView(R.id.menu4)
    MenuButton menu4;
    private QualityTeamAdapter mAdapter;
    private List<QuailtyTeam> mdatas = new ArrayList<>();
    private PopupWindow window;
    private PopupWindow window2;
    private PopupWindow window3;
    private String cityId;
    private LinkedHashMap<String, List<Category>> subcontractCategory = new LinkedHashMap<>();
    List<JHGridView> jhGridViewList = new ArrayList<>();
    private String curCategory = "";
    private String area_id = "";

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_quality_team;
    }

    @Override
    protected void initEventAndData() {
        cityId = SharedPreferenceUtil.getCityId(mContext);
        getMenuData();
        mAdapter = new QualityTeamAdapter(mContext, mdatas);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        View view = View.inflate(mContext, R.layout.view_divide_10, null);
        mRecyclerView.addHeaderView(view);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreEnabled(false);
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                getData();
            }

            @Override
            public void onLoadMore() {
            }
        });
        mAdapter.setOnItemClickListener(((position, bean, holder) -> {
            Intent intent = new Intent(mContext, QualityTeamDetailActivity.class);
            intent.putExtra("id", bean.getId());
            startActivity(intent);
        }));
        getData();
    }

    public void getMenuData() {
        subcontractCategory.clear();
        jhGridViewList.clear();
        OkGo.<JHResponse<List<Category>>>post(ReleaseConfig.baseUrl() + "class/classList")
                .tag(this)
                .params("type", 6)
                .params("pid", 0)
                .execute(new JHCallback<JHResponse<List<Category>>>() {
                    @Override
                    public void onSuccess(Response<JHResponse<List<Category>>> response) {
                        List<Category> categories = response.body().getMsg();
                        for (int i = 0; i < categories.size(); i++) {
                            Category category = categories.get(i);
                            getSubMenuData(category);
                        }
                    }
                });
    }

    public void getSubMenuData(Category category) {
        List<Category> list = new ArrayList<>();
        subcontractCategory.put(category.getName(), list);
        OkGo.<JHResponse<List<Category>>>post(ReleaseConfig.baseUrl() + "class/classList")
                .tag(this)
                .params("type", 6)
                .params("pid", category.getId())
                .execute(new JHCallback<JHResponse<List<Category>>>() {
                    @Override
                    public void onSuccess(Response<JHResponse<List<Category>>> response) {
                        list.addAll(response.body().getMsg());
                    }
                });
    }

    public void getData() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("Cityid", cityId);
        if (!TextUtils.isEmpty(curCategory)) httpParams.put("cid", curCategory);
        if (!TextUtils.isEmpty(area_id)) httpParams.put("area_id", area_id);
        if (!TextUtils.isEmpty(curGrade)) httpParams.put("grade", curGrade);
        if (!TextUtils.isEmpty(curPay)) httpParams.put("pay", curPay);
        OkGo.<JHResponse<List<QuailtyTeam>>>post(ReleaseConfig.baseUrl() + "builder/builderList")
                .tag(this)
                .params(httpParams)
                .execute(new JHCallback<JHResponse<List<QuailtyTeam>>>() {
                    @Override
                    public void onSuccess(Response<JHResponse<List<QuailtyTeam>>> response) {
                        mdatas = response.body().getMsg();
                        mAdapter.setDatas(mdatas);
                        mRecyclerView.refreshComplete();
                    }

                    @Override
                    public void onError(Response<JHResponse<List<QuailtyTeam>>> response) {
                        T.showToast(mContext, response.getException().getMessage());
                    }
                });
    }

    @OnClick({R.id.menu1, R.id.menu2, R.id.menu3, R.id.menu4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.menu1:
                if (window == null) {
                    initPopuptWindow();
                } else if (window.isShowing()) {
                    window.dismiss();
                } else {
                    window.showAtLocation(root, Gravity.LEFT | Gravity.TOP,
                            0, DensityUtils.dp2px(mContext, 134) + DensityUtils.getStatusBarHeight(mContext));
                    menu1.setChecked(true);
                }
                break;
            case R.id.menu2:
                Intent intent = new Intent(mContext, CityListActivity.class);
                startActivityForResult(intent, 1001);
                break;
            case R.id.menu3:
                if (window2 == null) {
                    initPopuptWindow2();
                } else if (window2.isShowing()) {
                    window2.dismiss();
                } else {
                    window2.showAtLocation(root, Gravity.LEFT | Gravity.TOP,
                            0, DensityUtils.dp2px(mContext, 134) + DensityUtils.getStatusBarHeight(mContext));
                    menu3.setChecked(true);
                }
                break;
            case R.id.menu4:
                if (window3 == null) {
                    initPopuptWindow3();
                } else if (window3.isShowing()) {
                    window3.dismiss();
                } else {
                    window3.showAtLocation(root, Gravity.LEFT | Gravity.TOP,
                            0, DensityUtils.dp2px(mContext, 134) + DensityUtils.getStatusBarHeight(mContext));
                    menu4.setChecked(true);
                }
                break;
        }
    }

    public void initPopuptWindow() {
        View view = View.inflate(mContext, R.layout.view_subcontract_menu,
                null);
        LinearLayout llParent = view.findViewById(R.id.ll_parent);
        for (String name : subcontractCategory.keySet()) {
            List<Category> categories = subcontractCategory.get(name);
            View v = View.inflate(mContext, R.layout.view_subcontract_menu_child, null);
            MenuButton button = v.findViewById(R.id.menu_btn);
            button.setTitle(NullCheck.check(name));
            JHGridView gridView = v.findViewById(R.id.grid_view);
            jhGridViewList.add(gridView);
            gridView.setMdatas(categories, curCategory);
            button.setOnClickListener(v1 -> {
                if (gridView.getVisibility() == View.VISIBLE) {
                    gridView.setVisibility(View.GONE);
                    button.setChecked(false);
                } else {
                    gridView.setVisibility(View.VISIBLE);
                    button.setChecked(true);
                }
            });
            gridView.setCheckChangeListener(category -> {
                curCategory = category.getId();
                menu1.setTitle(category.getName());
                for (int i = 0; i < jhGridViewList.size(); i++) {
                    jhGridViewList.get(i).setCurrentId(curCategory);
                }
                getData();
                window.dismiss();
            });
            llParent.addView(v);
        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 0, 200);
        view.setLayoutParams(params);
        LinearLayout layout = new LinearLayout(mContext);
        layout.addView(view);
        layout.setBackgroundColor(Color.argb(120, 0, 0, 0));
        window = new PopupWindow(layout, DensityUtils.getScreenW(mContext), DensityUtils.getScreenHnobar(mContext, -134));
        window.setFocusable(true);
        window.setBackgroundDrawable(new BitmapDrawable());
        window.setOutsideTouchable(true);
        layout.setOnClickListener(v -> window.dismiss());
        window.setOnDismissListener(() -> menu1.setChecked(false));
        window.showAtLocation(root, Gravity.LEFT | Gravity.TOP,
                0, DensityUtils.dp2px(mContext, 134) + DensityUtils.getStatusBarHeight(mContext));
        menu1.setChecked(true);
    }

    private String curGrade = null;

    public void initPopuptWindow2() {
        View view = View.inflate(mContext, R.layout.view_credit_menu,
                null);
        MenuButton unlimited = view.findViewById(R.id.unlimited);
        MenuButton desc = view.findViewById(R.id.desc);
        MenuButton asc = view.findViewById(R.id.asc);
        unlimited.setOnClickListener(v -> {
            unlimited.setChecked(true);
            desc.setChecked(false);
            asc.setChecked(false);
            curGrade = "0";
            getData();
            window2.dismiss();
        });

        desc.setOnClickListener(v -> {
            unlimited.setChecked(false);
            desc.setChecked(true);
            asc.setChecked(false);
            curGrade = "1";
            getData();
            window2.dismiss();
        });

        asc.setOnClickListener(v -> {
            unlimited.setChecked(false);
            desc.setChecked(false);
            asc.setChecked(true);
            curGrade = "2";
            getData();
            window2.dismiss();
        });
        LinearLayout layout = new LinearLayout(mContext);
        layout.addView(view);
        layout.setBackgroundColor(Color.argb(120, 0, 0, 0));
        window2 = new PopupWindow(layout, DensityUtils.getScreenW(mContext), DensityUtils.getScreenHnobar(mContext, -134));
        window2.setFocusable(true);
        window2.setBackgroundDrawable(new BitmapDrawable());
        window2.setOutsideTouchable(true);
        layout.setOnClickListener(v -> window2.dismiss());
        window2.setOnDismissListener(() -> menu3.setChecked(false));
        window2.showAtLocation(root, Gravity.LEFT | Gravity.TOP,
                0, DensityUtils.dp2px(mContext, 134) + DensityUtils.getStatusBarHeight(mContext));
        menu3.setChecked(true);
    }

    private String curPay = null;

    public void initPopuptWindow3() {
        View view = View.inflate(mContext, R.layout.view_advance_fund_menu,
                null);
        MenuButton advance_yes = view.findViewById(R.id.advance_yes);
        MenuButton advance_no = view.findViewById(R.id.advance_no);
        advance_yes.setOnClickListener(v -> {
            advance_yes.setChecked(true);
            advance_no.setChecked(false);
            curPay = "1";
            getData();
            window3.dismiss();
        });

        advance_no.setOnClickListener(v -> {
            advance_yes.setChecked(false);
            advance_no.setChecked(true);
            curPay = "2";
            getData();
            window3.dismiss();
        });
        LinearLayout layout = new LinearLayout(mContext);
        layout.addView(view);
        layout.setBackgroundColor(Color.argb(120, 0, 0, 0));
        window3 = new PopupWindow(layout, DensityUtils.getScreenW(mContext), DensityUtils.getScreenHnobar(mContext, -134));
        window3.setFocusable(true);
        window3.setBackgroundDrawable(new BitmapDrawable());
        window3.setOutsideTouchable(true);
        layout.setOnClickListener(v -> window3.dismiss());
        window3.setOnDismissListener(() -> menu4.setChecked(false));
        window3.showAtLocation(root, Gravity.LEFT | Gravity.TOP,
                0, DensityUtils.dp2px(mContext, 134) + DensityUtils.getStatusBarHeight(mContext));
        menu4.setChecked(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 && resultCode == 1002) {
            City city = (City) data.getExtras().get("city");
            area_id = city.getId();
            menu2.setTitle(city.getName());
            getData();
        }
    }
}
