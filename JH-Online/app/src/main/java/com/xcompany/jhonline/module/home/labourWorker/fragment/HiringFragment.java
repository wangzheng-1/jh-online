package com.xcompany.jhonline.module.home.labourWorker.fragment;

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
import com.xcompany.jhonline.model.home.Hiring;
import com.xcompany.jhonline.module.home.base.CityListActivity;
import com.xcompany.jhonline.module.home.labourWorker.activity.HiringDetailActivity;
import com.xcompany.jhonline.module.home.labourWorker.adapter.HiringAdapter;
import com.xcompany.jhonline.network.JHCallback;
import com.xcompany.jhonline.network.JHResponse;
import com.xcompany.jhonline.utils.DensityUtils;
import com.xcompany.jhonline.utils.ReleaseConfig;
import com.xcompany.jhonline.utils.SharedPreferenceUtil;
import com.xcompany.jhonline.utils.T;
import com.xcompany.jhonline.widget.JHGridView;
import com.xcompany.jhonline.widget.MenuButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by xieliang on 2018/11/28 23:02
 */
public class HiringFragment extends BaseFragment {
    @BindView(R.id.recycler_list)
    XRecyclerView mRecyclerView;
    @BindView(R.id.root)
    LinearLayout root;
    @BindView(R.id.menu1)
    MenuButton menu1;
    @BindView(R.id.menu2)
    MenuButton menu2;
    private HiringAdapter mAdapter;
    private List<Hiring> mdatas = new ArrayList<>();
    private PopupWindow window;
    private String cityId;
    private List<Category> categories = new ArrayList<>();
    private String curCategory = "";
    private String area_id = "";

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_hiring;
    }

    @Override
    protected void initEventAndData() {
        cityId = SharedPreferenceUtil.getCityId(mContext);
        getMenuData();
        mAdapter = new HiringAdapter(mContext, mdatas);
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
            Intent intent = new Intent(mContext, HiringDetailActivity.class);
            intent.putExtra("id", bean.getId());
            startActivity(intent);
        }));
        getData();
    }

    public void getMenuData() {
        OkGo.<JHResponse<List<Category>>>post(ReleaseConfig.baseUrl() + "class/classList")
                .tag(this)
                .params("type", 2)
                .params("pid", 0)
                .execute(new JHCallback<JHResponse<List<Category>>>() {
                    @Override
                    public void onSuccess(Response<JHResponse<List<Category>>> response) {
                        categories = response.body().getMsg();
                    }
                });
    }

    public void getData() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("Cityid", cityId);
        httpParams.put("type", 0);
        if (!TextUtils.isEmpty(curCategory)) httpParams.put("cid", curCategory);
        if (!TextUtils.isEmpty(area_id)) httpParams.put("area_id", area_id);
        OkGo.<JHResponse<List<Hiring>>>post(ReleaseConfig.baseUrl() + "worker/workerList")
                .tag(this)
                .params(httpParams)
                .execute(new JHCallback<JHResponse<List<Hiring>>>() {
                    @Override
                    public void onSuccess(Response<JHResponse<List<Hiring>>> response) {
                        mdatas = response.body().getMsg();
                        mAdapter.setDatas(mdatas);
                        mRecyclerView.refreshComplete();
                    }

                    @Override
                    public void onError(Response<JHResponse<List<Hiring>>> response) {
//                        T.showToast(mContext, response.getException().getMessage());
                        if(mRecyclerView!=null){
                            mRecyclerView.refreshComplete();
                        }
                    }
                });
    }

    @OnClick({R.id.menu1, R.id.menu2})
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
        }
    }

    public void initPopuptWindow() {
        View view = View.inflate(mContext, R.layout.view_renting_menu,
                null);
        JHGridView grid = view.findViewById(R.id.menu_grid);
        grid.setMdatas(categories, curCategory);
        grid.setCheckChangeListener(category -> {
            curCategory = category.getId();
            menu1.setTitle(category.getName());
            getData();
            window.dismiss();
        });
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
