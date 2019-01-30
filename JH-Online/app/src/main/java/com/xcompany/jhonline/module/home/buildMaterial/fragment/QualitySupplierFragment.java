package com.xcompany.jhonline.module.home.buildMaterial.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.xcompany.jhonline.R;
import com.xcompany.jhonline.base.BaseFragment;
import com.xcompany.jhonline.model.home.QualitySupplier;
import com.xcompany.jhonline.module.home.buildMaterial.activity.QualitySupplierDetailActivity;
import com.xcompany.jhonline.module.home.buildMaterial.adapter.QualitySupplierAapter;
import com.xcompany.jhonline.network.JHCallback;
import com.xcompany.jhonline.network.JHResponse;
import com.xcompany.jhonline.utils.ReleaseConfig;
import com.xcompany.jhonline.utils.SharedPreferenceUtil;
import com.xcompany.jhonline.utils.T;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by xieliang on 2018/12/5 22:45
 */
public class QualitySupplierFragment extends BaseFragment {
    @BindView(R.id.recycler_list)
    XRecyclerView mRecyclerView;
    @BindView(R.id.et_name)
    EditText etName;
    private QualitySupplierAapter mAdapter;
    private List<QualitySupplier> mdatas = new ArrayList<>();
    private String cityId;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_quality_supplier;
    }

    @Override
    protected void initEventAndData() {
        cityId = SharedPreferenceUtil.getCityId(mContext);
        mAdapter = new QualitySupplierAapter(mContext, mdatas);
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
        mAdapter.setOnItemClickListener((position, bean, holder) -> {
            Intent intent = new Intent(mContext, QualitySupplierDetailActivity.class);
            intent.putExtra("id", bean.getId());
            startActivity(intent);
        });
        etName.setOnKeyListener(((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                getData();
            }
            return false;
        }));
        getData();
    }

    public void getData() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("Cityid", cityId);
        String trim = etName.getText().toString().trim();
        if (!TextUtils.isEmpty(trim)) {
            httpParams.put("name", trim);
        }
        OkGo.<JHResponse<List<QualitySupplier>>>post(ReleaseConfig.baseUrl() + "Supplier/supplierList")
                .tag(this)
                .params(httpParams)
                .execute(new JHCallback<JHResponse<List<QualitySupplier>>>() {
                    @Override
                    public void onSuccess(Response<JHResponse<List<QualitySupplier>>> response) {
                        mdatas = response.body().getMsg();
                        mAdapter.setDatas(mdatas);
                        mRecyclerView.refreshComplete();
                    }

                    @Override
                    public void onError(Response<JHResponse<List<QualitySupplier>>> response) {
//                        T.showToast(mContext, response.getException().getMessage());
                    }
                });
    }
}
