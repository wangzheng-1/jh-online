package com.xcompany.jhonline.network;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.BodyRequest;
import com.xcompany.jhonline.utils.ReleaseConfig;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *  网络请求工具类
 */
public class LoginRequestUtil {

    /**
     *  执行POST请求
     * @param interfaceName  接口名称
     * @param params  传参
     * @param netCallBack  回调接口
     */
    public static void loginRequest(String interfaceName,Map<String,String> params,NetCallBack netCallBack){
        String url = ReleaseConfig.baseUrl() + interfaceName;
        HttpParams httpParams = new HttpParams();
        httpParams.put(params);
        executeRequest(OkGo.post(url).params(httpParams),netCallBack);
    }

    /**
     *  执行请求
     * @param request  请求
     * @param netCallBack  回调接口
     */
    private static void executeRequest(BodyRequest request,NetCallBack netCallBack){

        request.execute(new JsonCallback<LoginResponse>() {

            @Override
            public void onSuccess(Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();
                List<LoginResponse> dataList = new ArrayList<>();
                dataList.add(loginResponse);
                netCallBack.done(dataList,dataList.size(),null);
            }

            @Override
            public LoginResponse convertResponse(okhttp3.Response response) throws Exception {
                String jsonString = response.body().string();
                response.close();
                if (!TextUtils.isEmpty(jsonString)) {
                    Type type = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
                    final LoginResponse t = new Gson().fromJson(jsonString, type);
                    if (t instanceof LoginResponse) {
                        //获取验证码失败
                        if (((LoginResponse) t).getMd() == null || t.getMd() != 666) {
                            throw new IllegalStateException("获取验证码失败");
                        }
                        else{
                            return t;
                        }
                    } else {
                        throw new IllegalStateException("服务器返回数据格式异常!");
                    }
                } else {
                    throw new IllegalStateException("服务器返回为空!");
                }
            }

            @Override
            public void onError(Response<LoginResponse> response) {
                Exception exception = new Exception(response.getException());
                netCallBack.done(null,0, exception);
            }
        });
    }
}
