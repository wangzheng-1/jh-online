package com.xcompany.jhonline.module.me.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.xcompany.jhonline.R;
import com.xcompany.jhonline.app.GlideApp;
import com.xcompany.jhonline.base.BaseActivity;
import com.xcompany.jhonline.model.me.PersonCenterBean;
import com.xcompany.jhonline.network.JHCallback;
import com.xcompany.jhonline.network.JHResponse;
import com.xcompany.jhonline.utils.ReleaseConfig;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserGuideActivity extends BaseActivity {


    @BindView(R.id.backHomeLayout)
    LinearLayout backHomeLayout;
    @BindView(R.id.reportTitleText)
    TextView reportTitleText;
    @BindView(R.id.titleLayout)
    RelativeLayout titleLayout;
    @BindView(R.id.cooperateImage)
    ImageView cooperateImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_guide);
        ButterKnife.bind(this);
        getData();
    }

    @OnClick(R.id.backHomeLayout)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backHomeLayout:
                this.finish();
                onBackPressed();
                break;
        }
    }
    private void getData(){
        Map<String,String> params = new HashMap<>();
        params.put("id","6");
        params.put("port","5");
        OkGo.<JHResponse<PersonCenterBean>>post(ReleaseConfig.baseUrl() + "index/Carousel")
                .tag(this)
                .params(params)
                .execute(new JHCallback<JHResponse<PersonCenterBean>>() {
                    @Override
                    public void onSuccess(Response<JHResponse<PersonCenterBean>> response) {
                        Message message = new Message();
                        message.what = 1;
                        message.obj =  response.body().getMsg();
                        handler.sendMessage(message);
                    }

                    @Override
                    public void onError(Response<JHResponse<PersonCenterBean>> response) {

                   }
                });
    }
    private BitmapFactory.Options getBitmapOption(int inSampleSize) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPurgeable = true;
        options.inSampleSize = inSampleSize;

        return options;
    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int msgWhat = msg.what;
            switch (msgWhat){
                case 1:
                    PersonCenterBean personCenterBean = (PersonCenterBean) msg.obj;
                    if(personCenterBean != null && personCenterBean.getImages()!= null
                            && personCenterBean.getImages().size() >=3){
                        String imageUrl = personCenterBean.getImages().get(2);
                        GlideApp.with(UserGuideActivity.this).load(ReleaseConfig.getInstance().getDisplayBaseUrl() +  imageUrl)
                                .downloadOnly(new SimpleTarget<File>() {

                                    @Override
                                    public void onResourceReady(@NonNull File resource, @Nullable Transition<? super File> transition) {
                                        Bitmap bitmap= BitmapFactory.decodeFile(resource.getAbsolutePath(),getBitmapOption(1));
                                        // 显示处理好的Bitmap图片
                                        cooperateImage.setImageBitmap(bitmap);
                                    }
                                });

                    }
                    break;
                default:
                    break;
            }
        }
    };
}
