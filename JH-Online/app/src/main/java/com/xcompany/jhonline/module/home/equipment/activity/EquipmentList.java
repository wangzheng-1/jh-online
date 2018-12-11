package com.xcompany.jhonline.module.home.equipment.activity;

import android.support.v4.app.Fragment;

import com.xcompany.jhonline.module.home.base.BaseFragmentListActivity;
import com.xcompany.jhonline.module.home.equipment.fragment.RentSeekingFragment;
import com.xcompany.jhonline.module.home.equipment.fragment.RentingFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xieliang on 2018/11/26 22:57
 */
public class EquipmentList extends BaseFragmentListActivity {
    @Override
    protected List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new RentingFragment());
        fragments.add(new RentSeekingFragment());
        return fragments;
    }

    @Override
    protected String[] getLabels() {
        return new String[]{"正在出租", "正在求租"};
    }

    @Override
    protected String getPageTitle() {
        return "设备器材";
    }
}
