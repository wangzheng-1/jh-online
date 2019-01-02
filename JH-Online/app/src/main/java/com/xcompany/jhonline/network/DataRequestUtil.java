package com.xcompany.jhonline.network;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.BodyRequest;
import com.xcompany.jhonline.utils.ReleaseConfig;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 *  网络请求工具类
 */
public class DataRequestUtil {

    /**
     *  执行GET请求
     * @param interfaceName  接口名称
     * @param params  传参
     * @param netCallBack  回调接口
     */
    public static void getRequest(String interfaceName,Map<String,String> params,NetCallBack netCallBack){
        String url = ReleaseConfig.baseUrl() + interfaceName;
        executeRequest(OkGo.post(getRequestUrl(url,params)),netCallBack);
    }

    /**
     *  执行POST请求
     * @param interfaceName  接口名称
     * @param params  传参
     * @param netCallBack  回调接口
     */
    public static void postRequest(String interfaceName,Map<String,String> params,NetCallBack netCallBack){
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
        request.execute(new JsonCallback<ApiResponse<JSONArray>>() {

                    @Override
                    public void onSuccess(Response<ApiResponse<JSONArray>> response) {
                        JSONArray data = response.body().getData();
                        List<JSONObject> dataList = new ArrayList<>();
                        if(data!= null && data.size() > 0) {
                            for(int i = 0;i < data.size(); i++){
                                dataList.add(data.getJSONObject(i));
                            }
                        }
                        netCallBack.done(dataList,dataList.size(),null);
                    }

                    @Override
                    public ApiResponse<JSONArray> convertResponse(okhttp3.Response response) throws Exception {
                        String jsonString = response.body().string();
                        response.close();
                        if (!TextUtils.isEmpty(jsonString)) {
                            Type type = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
                            final ApiResponse<JSONArray> t = new Gson().fromJson(jsonString, type);
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
                    public void onError(Response<ApiResponse<JSONArray>> response) {
                        Exception exception = new Exception(response.getException());
                        netCallBack.done(null,0, exception);
                    }
                });
    }
    /**
     *  拼接get请求的URL
     * @param url 请求前缀
     * @param params 请求参数
     */
    private static String getRequestUrl(String url,Map<String, String> params){
        StringBuffer urlBuffer = new StringBuffer();
        urlBuffer.append(url);
        if(params.size()!=0){
            for(String key : params.keySet()){
                Object value = params.get(key);
                urlBuffer.append("&"+key + "=" +value);
            }
        }
        return urlBuffer.toString();

    }

    /**
     *上传文件
     * @param interfaceName 接口地址
     * @param paramsMap 参数
     * @param netCallBack 回调
     */
    public static <T> void upLoadFile(String interfaceName, Map<String, Object> paramsMap, OKNetCallBack<T> netCallBack) {
        try {

            String requestUrl = ReleaseConfig.baseUrl() + interfaceName;
            MultipartBody.Builder builder = new MultipartBody.Builder();
            //设置类型
            builder.setType(MultipartBody.FORM);
            //追加参数
            for (String key : paramsMap.keySet()) {
                Object object = paramsMap.get(key);
                if (!(object instanceof File)) {
                    builder.addFormDataPart(key, object.toString());
                } else {
                    File file = (File) object;
                    builder.addFormDataPart(key, file.getName(), RequestBody.create(null, file));
                }
            }
            //创建Request
            final Request request = new Request.Builder().url(requestUrl).post(builder.build()).build();
            OkHttpClient okHttpClient = new OkHttpClient();
            //单独设置参数 比如读取超时时间
            final Call call = okHttpClient.newBuilder().writeTimeout(50, TimeUnit.SECONDS).build().newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    netCallBack.done(null,e);
                }
                @Override
                public void onResponse(Call call, okhttp3.Response response) {
                    try{
                        JSONObject jsonObject = JSON.parseObject(response.body().string());
                        if (jsonObject.getIntValue("code") == 555) {//请求成功

                            Type clazz = ((ParameterizedType)netCallBack.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
                            T t = new Gson().fromJson(jsonObject.getString("msg"), clazz);
                            netCallBack.done(t,null);
                        } else {
                            netCallBack.done(null,new Exception(jsonObject.getString("msg")));
                        }
                    }
                    catch (Exception e){
                        netCallBack.done(null,e);
                    }

                }
            });
        } catch (Exception e) {
            netCallBack.done(null,e);
        }
    }

}
