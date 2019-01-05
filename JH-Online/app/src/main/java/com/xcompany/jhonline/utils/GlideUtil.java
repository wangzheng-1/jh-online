package com.xcompany.jhonline.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.xcompany.jhonline.R;
import com.xcompany.jhonline.app.GlideApp;

/**
 * Created by xieliang on 2019/1/4 19:47
 */
public class GlideUtil {
    public static void LoaderImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView, boolean fullPath) {
        if (!fullPath) url = ReleaseConfig.baseUrl().replace("/Api/", "") + url;
        GlideApp.with(context)
                .load(url)
                .centerCrop()
                .error(R.drawable.nopic)    //错误加载
                .placeholder(R.drawable.nopic)   //加载图
                .into(imageView);
    }


}
