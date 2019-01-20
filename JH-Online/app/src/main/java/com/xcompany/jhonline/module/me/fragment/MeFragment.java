package com.xcompany.jhonline.module.me.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.xcompany.jhonline.R;
import com.xcompany.jhonline.base.BaseFragment;
import com.xcompany.jhonline.model.me.MeAll;
import com.xcompany.jhonline.module.login.LoginActivity;
import com.xcompany.jhonline.module.me.activity.AboutUsActivity;
import com.xcompany.jhonline.module.me.activity.BuinessCooperateActivity;
import com.xcompany.jhonline.module.me.activity.MeCollectListActivity;
import com.xcompany.jhonline.module.me.activity.MeCreditActivity;
import com.xcompany.jhonline.module.me.activity.MeFollowListActivity;
import com.xcompany.jhonline.module.me.activity.UserGuideActivity;
import com.xcompany.jhonline.module.publish.activity.PublishTypeActivity;
import com.xcompany.jhonline.network.JHCallback;
import com.xcompany.jhonline.network.JHResponse;
import com.xcompany.jhonline.network.UserService;
import com.xcompany.jhonline.utils.ReleaseConfig;
import com.xcompany.jhonline.utils.StringUtil;
import com.xcompany.jhonline.utils.T;

import butterknife.BindView;

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
//    @BindView(R.id.nickNameText)
//    TextView nickNameText;
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


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    protected void initEventAndData() {

        contactText = mView.findViewById(R.id.contactText);
        collectBtn = mView.findViewById(R.id.collectBtn);

        contactText.setText(UserService.getInstance().getMobile());

        collectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheetDialog();
            }
        });
        initListener();
        getData();

    }

    private void getData(){
        OkGo.<JHResponse<MeAll>>post(ReleaseConfig.baseUrl() + "User/census")
                .tag(this)
                .params("uid",UserService.getInstance().getUid())
                .execute(new JHCallback<JHResponse<MeAll>>() {
                    @Override
                    public void onSuccess(Response<JHResponse<MeAll>> response) {
                        MeAll result = response.body().getMsg();
                        if(result != null){
                            mineIntegralText.setText(result.getIntegral());
                            mineCollectText.setText(result.getIssue());
                            mineFellowText.setText(result.getGive());
                            if(!StringUtil.isEmpty(result.getSign())){
                                signBtn.setText("已签到");
                                signBtn.setEnabled(false);
                            }
                        }

                    }

                    @Override
                    public void onError(Response<JHResponse<MeAll>> response) {
                        T.showToast(MeFragment.this.getActivity(), response.getException().getMessage());
                    }
                });
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
        bussinessCoporLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MeFragment.this.getContext(),BuinessCooperateActivity.class);
                startActivity(intent);
            }
        });
        aboutUsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MeFragment.this.getContext(),AboutUsActivity.class);
                startActivity(intent);
            }
        });
        userGuideLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MeFragment.this.getContext(),UserGuideActivity.class);
                startActivity(intent);
            }
        });
        meLoginOutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showExitDialog();
            }
        });
        signBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sign();
            }
        });
        meWantEnterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MeFragment.this.getContext(),PublishTypeActivity.class);
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

    AlertDialog exitDialog;
    private void showExitDialog(){
        if(exitDialog == null){
            AlertDialog.Builder builder = new AlertDialog.Builder(MeFragment.this.getContext());
            View view = LayoutInflater.from(MeFragment.this.getContext()).inflate(R.layout.fragment_me_exit_layout,null);  //关键语句1，你自定义的layout，所有layout哟
            view.findViewById(R.id.cancelText).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(exitDialog != null && exitDialog.isShowing()){
                        exitDialog.dismiss();
                    }
                }
            });
            view.findViewById(R.id.confirmText).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(exitDialog != null && exitDialog.isShowing()){
                        exitDialog.dismiss();
                    }
                    exit();
                }
            });
            builder.setView(view);
            exitDialog = builder.create();
            exitDialog.setCancelable(false);
        }
        exitDialog.show();
    }
    private void exit(){
        UserService.getInstance().clear();
        Intent intent = new Intent(MeFragment.this.getContext(),LoginActivity.class);
        startActivity(intent);
        MeFragment.this.getActivity().finish();
    }

    private void sign(){
        signBtn.setEnabled(false);
        OkGo.<JHResponse<String>>post(ReleaseConfig.baseUrl() + "User/sign")
                .tag(this)
                .params("optxt","0")
                .params("uid",UserService.getInstance().getUid())
                .execute(new JHCallback<JHResponse<String>>() {
                    @Override
                    public void onSuccess(Response<JHResponse<String>> response) {
                        String result = response.body().getMsg();
                        signBtn.setText("已签到");
                        signBtn.setEnabled(false);
                    }

                    @Override
                    public void onError(Response<JHResponse<String>> response) {
                        T.showToast(MeFragment.this.getActivity(), "签到失败，请稍后重试");
                        signBtn.setEnabled(true);
                    }
                });
    }

}
