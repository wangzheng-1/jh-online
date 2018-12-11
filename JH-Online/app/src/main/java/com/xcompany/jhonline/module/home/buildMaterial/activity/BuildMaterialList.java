package com.xcompany.jhonline.module.home.buildMaterial.activity;

import android.support.v4.app.Fragment;

import com.xcompany.jhonline.module.home.base.BaseFragmentListActivity;
import com.xcompany.jhonline.module.home.buildMaterial.fragment.PurchaseFragment;
import com.xcompany.jhonline.module.home.buildMaterial.fragment.QualitySupplierFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xieliang on 2018/11/26 22:57
 */
public class BuildMaterialList extends BaseFragmentListActivity {
    @Override
    protected List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new QualitySupplierFragment());
        fragments.add(new PurchaseFragment());
        return fragments;
    }

    @Override
    protected String[] getLabels() {
        return new String[]{"优质供应商", "正在采购"};
    }

    @Override
    protected String getPageTitle() {
        return "建材产品";
    }
}
