package com.xcompany.jhonline.app;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.SPCookieStore;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.socks.library.KLog;
import com.xcompany.jhonline.model.base.CityService;

import java.net.URLDecoder;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;


public class JhApplication extends Application {

    private static JhApplication instance;

    private static CityService cityService = new CityService();

    public static synchronized JhApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        KLog.init(true, "JHOnline");//开发阶段打印日志，正式环境请关闭！
        initOkGo();
    }


    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    private void initOkGo() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(message -> {
            KLog.i(URLDecoder.decode(message));
        });
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(logging);
        //全局的读取超时时间
        builder.readTimeout(30000, TimeUnit.MILLISECONDS);
        //全局的写入超时时间
        builder.writeTimeout(30000, TimeUnit.MILLISECONDS);
        //全局的连接超时时间
        builder.connectTimeout(30000, TimeUnit.MILLISECONDS);
        //使用sp保持cookie，如果cookie不过期，则一直有效
        builder.cookieJar(new CookieJarImpl(new SPCookieStore(this)));

        HttpHeaders headers = new HttpHeaders();
        HttpParams params = new HttpParams();
        OkGo.getInstance().init(this)
                .setOkHttpClient(builder.build())
                .setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(3)                               //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
                .addCommonHeaders(headers)                      //全局公共头
                .addCommonParams(params);                       //全局公共参数
    }

    public CityService getCityService() {
        return cityService;
    }

    public void setCityService(CityService cityService) {
        JhApplication.cityService = cityService;
    }
}
