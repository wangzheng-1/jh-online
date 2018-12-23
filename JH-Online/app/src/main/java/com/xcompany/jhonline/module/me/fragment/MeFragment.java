package com.xcompany.jhonline.module.me.fragment;

import android.content.Intent;
import android.support.design.widget.BottomSheetDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xcompany.jhonline.R;
import com.xcompany.jhonline.base.BaseFragment;
import com.xcompany.jhonline.module.me.activity.MeCollectListActivity;
import com.xcompany.jhonline.module.me.activity.MeCreditActivity;
import com.xcompany.jhonline.module.me.activity.MeFollowListActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by xieliang on 2018/11/21 11:47
 */
public class MeFragment extends BaseFragment {
    @BindView(R.id.homeMeText)
    TextView homeMeText;
    @BindView(R.id.collectBtn)
    ImageView collectBtn;
    @BindView(R.id.meHeadIcon)
    ImageView meHeadIcon;
    @BindView(R.id.nickNameText)
    TextView nickNameText;
    @BindView(R.id.contactText)
    TextView contactText;
    @BindView(R.id.signBtn)
    TextView signBtn;
    @BindView(R.id.mineIntegralLayout)
    LinearLayout mineIntegralLayout;
    @BindView(R.id.mineIntegralText)
    TextView mineIntegralText;
    @BindView(R.id.mineCollectLayout)
    LinearLayout mineCollectLayout;
    @BindView(R.id.mineCollectText)
    TextView mineCollectText;
    @BindView(R.id.mineFellowLayout)
    LinearLayout mineFellowLayout;
    @BindView(R.id.mineFellowText)
    TextView mineFellowText;
    @BindView(R.id.meWantEnterBtn)
    LinearLayout meWantEnterBtn;
    @BindView(R.id.bussinessCoporLayout)
    LinearLayout bussinessCoporLayout;
    @BindView(R.id.aboutUsLayout)
    LinearLayout aboutUsLayout;
    @BindView(R.id.userGuideLayout)
    LinearLayout userGuideLayout;
    @BindView(R.id.meLoginOutLayout)
    LinearLayout meLoginOutLayout;
    Unbinder unbinder;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    protected void initEventAndData() {
        unbinder = ButterKnife.bind(this, mView);
        collectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheetDialog();
            }
        });
        initListener();


    }
    private void initListener(){
        mineIntegralLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MeFragment.this.getContext(),MeCreditActivity.class);
                startActivity(intent);
            }
        });
        mineCollectLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MeFragment.this.getContext(),MeCollectListActivity.class);
                startActivity(intent);
            }
        });
        mineFellowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MeFragment.this.getContext(),MeFollowListActivity.class);
                startActivity(intent);
            }
        });
    }


    BottomSheetDialog bottomSheet;
    private void initBottomSheet(){
        if(bottomSheet == null){
            bottomSheet = new BottomSheetDialog(this.getContext());//实例化BottomSheetDialog
            bottomSheet.setCancelable(true);//设置点击外部是否可以取消
            View bottomReportMenu = this.getLayoutInflater().inflate(R.layout.fragment_me_share_bottom_menu,null);
            bottomReportMenu.findViewById(R.id.shareFriendLayout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(bottomSheet != null && bottomSheet.isShowing()){
                        bottomSheet.dismiss();
                    }
                    //todo  分享到微信好友

                }
            });
            bottomReportMenu.findViewById(R.id.shareMomentLayout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(bottomSheet != null && bottomSheet.isShowing()){
                        bottomSheet.dismiss();
                    }
                    //todo 分享到朋友圈
                }
            });
            bottomReportMenu.findViewById(R.id.cancelText).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(bottomSheet != null && bottomSheet.isShowing()){
                        bottomSheet.dismiss();
                    }
                }
            });
            bottomSheet.setContentView(bottomReportMenu);
        }
    }
    private void showBottomSheetDialog(){
        if(bottomSheet == null){
            initBottomSheet();
        }
        if(bottomSheet.isShowing()){
            bottomSheet.dismiss();
        }
        else {
            bottomSheet.show();
        }
    }



}
