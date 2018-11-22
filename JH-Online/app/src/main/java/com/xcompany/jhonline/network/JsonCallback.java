package com.xcompany.jhonline.network;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.lzy.okgo.callback.AbsCallback;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.util.concurrent.TimeoutException;

import okhttp3.Response;

/**
 * Created by xieliang on 2018/11/22 22:54
 */
public abstract class JsonCallback<T> extends AbsCallback<T> {
    @Override
    public T convertResponse(Response response) throws Exception {
        String jsonString = response.body().string();
        response.close();
        if (!TextUtils.isEmpty(jsonString)) {
            Type type = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            final T t = new Gson().fromJson(jsonString, type);
            if (t instanceof ApiResponse) {
                if (((ApiResponse) t).getMsg() == null) ((ApiResponse) t).setMsg("");
                if (((ApiResponse) t).getCode() == 0) {//请求成功
                    return t;
                } else {
                    throw new IllegalStateException(((ApiResponse) t).getMsg());
                }
            } else {
                throw new IllegalStateException("服务器返回数据格式异常!");
            }
        } else {
            throw new IllegalStateException("服务器返回为空!");
        }
    }

    @Override
    public void onError(com.lzy.okgo.model.Response<T> response) {
        Throwable e = response.getException();
        if (e instanceof TimeoutException) {
            System.out.println("网络连接失败！");
        } else if (e instanceof ConnectException) {
            System.out.println("网络连接失败！");
        } else {
            System.out.println(e.getMessage());
        }
    }
}
