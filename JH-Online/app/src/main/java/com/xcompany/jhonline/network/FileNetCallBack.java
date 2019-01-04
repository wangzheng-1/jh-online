package com.xcompany.jhonline.network;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public abstract class FileNetCallBack<T> implements Callback {

    @Override
    public void onFailure(Call call, IOException e) {
        done(null,e);
    }
    @Override
    public void onResponse(Call call, Response response) throws IOException {

        String jsonString = response.body().string();
        response.close();
        if (!TextUtils.isEmpty(jsonString)) {
            Type type = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            try {
                T t = new Gson().fromJson(jsonString, type);
                if (t instanceof JHResponse) {
                    if (((JHResponse) t).getCode() == 555) {//请求成功
                        done(t,null) ;
                    } else {
                        done(null,new IllegalStateException(((JHResponse) t).getMsg().toString())) ;
                        }
                } else {
                    done(null,new IllegalStateException("服务器返回数据格式异常!"));
                }
            } catch (JsonSyntaxException e) {
                done(null,e);
                }
        } else {
            done(null,new IllegalStateException("服务器返回为空!"));
        }
    }

    public abstract void done(T t, Exception e);

}
