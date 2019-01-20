package com.xcompany.jhonline.utils;

import android.app.Activity;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.xcompany.jhonline.R;
import com.xcompany.jhonline.network.JHCallback;
import com.xcompany.jhonline.network.JHResponse;
import com.xcompany.jhonline.network.UserService;
import com.xcompany.jhonline.widget.ProjectToolbar;

/**
 * Created by xieliang on 2019/1/19 22:11
 */
public class DetailCommonUtils {

    public static void checkStatus(Activity activity) {
        LinearLayout llTop = activity.findViewById(R.id.ll_top);
        ImageView topImage = activity.findViewById(R.id.top_image);
        TextView topFix = activity.findViewById(R.id.top_fix);

        String status = activity.getIntent().getExtras().getString("status");
        if(status==null){
            llTop.setVisibility(View.GONE);
        }else if(TextUtils.equals("status","0")){
            //审核通过
            llTop.setVisibility(View.VISIBLE);
            llTop.setBackgroundColor(Color.parseColor("#00BD24"));
            topImage.setImageResource(R.drawable.shtg);
            topFix.setVisibility(View.VISIBLE);
        }else if(TextUtils.equals("status","2")){
            //审核中
            llTop.setVisibility(View.VISIBLE);
            llTop.setBackgroundColor(Color.parseColor("#FFB400"));
            topImage.setImageResource(R.drawable.shz);
            topFix.setVisibility(View.GONE);
        }else {
            //审核不通过
            llTop.setVisibility(View.VISIBLE);
            llTop.setBackgroundColor(Color.parseColor("#FF4500"));
            topImage.setImageResource(R.drawable.shbtg);
            topFix.setVisibility(View.GONE);
        }
    }

    public static void saveAndUnSave(Activity activity, String id, String type) {
        if (TextUtils.isEmpty(id) || TextUtils.isEmpty(type)) return;
        ProjectToolbar toolbar = activity.findViewById(R.id.toolbar);
        toolbar.setOnRightClickListener(v -> {
            String toolbar_right = toolbar.getToolbar_right();
            if (TextUtils.equals("收藏", toolbar_right)) {
                //收藏
                OkGo.<JHResponse<String>>post(ReleaseConfig.baseUrl() + "User/collect")
                        .tag(activity)
                        .params("uid", UserService.getInstance().getUid())
                        .params("fid", id)
                        .params("type", type)
                        .execute(new JHCallback<JHResponse<String>>() {
                            @Override
                            public void onSuccess(Response<JHResponse<String>> response) {
                                T.showToast(activity, "收藏成功");
                                toolbar.setToolbar_right("取消收藏");
                            }

                            @Override
                            public void onError(Response<JHResponse<String>> response) {
                                T.showToast(activity, response.getException().getMessage());
                            }
                        });
            } else {
                //取消收藏
                OkGo.<JHResponse<String>>post(ReleaseConfig.baseUrl() + "User/colldelete")
                        .tag(activity)
                        .params("id", id)
                        .execute(new JHCallback<JHResponse<String>>() {
                            @Override
                            public void onSuccess(Response<JHResponse<String>> response) {
                                T.showToast(activity, "取消收藏成功");
                                toolbar.setToolbar_right("收藏");
                            }

                            @Override
                            public void onError(Response<JHResponse<String>> response) {
                                T.showToast(activity, response.getException().getMessage());
                            }
                        });
            }
        });
    }
}
