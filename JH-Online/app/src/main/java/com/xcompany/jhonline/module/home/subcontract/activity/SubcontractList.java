package com.xcompany.jhonline.module.home.subcontract.activity;

import android.support.v4.app.Fragment;

import com.xcompany.jhonline.module.home.base.BaseFragmentListActivity;
import com.xcompany.jhonline.module.home.subcontract.fragment.QualityTeamFragment;
import com.xcompany.jhonline.module.home.subcontract.fragment.TenderFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xieliang on 2018/11/26 22:57
 */
public class SubcontractList extends BaseFragmentListActivity {

    @Override
    protected List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new QualityTeamFragment());
        fragments.add(new TenderFragment());
        return fragments;
    }

    @Override
    protected String[] getLabels() {
        return new String[]{"匹配优质班组", "正在招标项目"};
    }

    @Override
    protected String getPageTitle() {
        return "分包单位";
    }
}
