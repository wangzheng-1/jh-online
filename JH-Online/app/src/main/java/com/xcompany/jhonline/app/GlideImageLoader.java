package com.xcompany.jhonline.app;

import android.content.Context;
import android.widget.ImageView;

import com.xcompany.jhonline.utils.ReleaseConfig;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by xieliang on 2018/12/15 12:10
 */
public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        String url = ReleaseConfig.baseUrl().replace("/Api/", "") + path;
        GlideApp.with(context).load(url)
                .centerCrop()
                .into(imageView);
    }
}
