package com.xcompany.jhonline.module.home.certificate.acitivity;

import android.support.v4.app.Fragment;

import com.xcompany.jhonline.module.home.base.BaseFragmentListActivity;
import com.xcompany.jhonline.module.home.certificate.fragment.QualificationHandleFragment;
import com.xcompany.jhonline.module.home.certificate.fragment.RegistrationFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xieliang on 2018/11/26 22:57
 */
public class CertificateList extends BaseFragmentListActivity {
    @Override
    protected List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new RegistrationFragment());
        fragments.add(new QualificationHandleFragment());
        return fragments;
    }

    @Override
    protected String[] getLabels() {
        return new String[]{"证照挂靠", "资质办理"};
    }

    @Override
    protected String getPageTitle() {
        return "证照资质";
    }
}
