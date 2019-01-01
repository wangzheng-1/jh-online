package com.xcompany.jhonline.network;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lzy.okgo.callback.AbsCallback;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.util.concurrent.TimeoutException;

import okhttp3.Response;

/**
 * Created by xieliang on 2018/11/22 22:54
 */
public abstract class JHCallback<T> extends AbsCallback<T> {
    @Override
    public T convertResponse(Response response) throws Exception {
        String jsonString = response.body().string();
        response.close();
        if (!TextUtils.isEmpty(jsonString)) {
            Type type = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            try {
                T t = new Gson().fromJson(jsonString, type);
                if (t instanceof JHResponse) {
                    if (((JHResponse) t).getCode() == 666) {//请求成功
                        return t;
                    } else {
                        throw new IllegalStateException(((JHResponse) t).getMsg().toString());
                    }
                } else {
                    throw new IllegalStateException("服务器返回数据格式异常!");
                }
            } catch (JsonSyntaxException e) {
                throw new IllegalStateException(e.getMessage());
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
