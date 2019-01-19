package com.xcompany.jhonline.network;

import android.content.Context;
import android.content.SharedPreferences;

import com.xcompany.jhonline.app.JhApplication;

public class UserService {

	private static UserService singleInstance = null;
	public static UserService getInstance(){
		if(singleInstance!=null){
			return singleInstance;
		}
		synchronized (UserService.class) {
			if(singleInstance!=null){
				return singleInstance;
			}
			singleInstance = new UserService();
			singleInstance.init();
		}
		return singleInstance;
	}

	//用户ID
	private String uid;
	//验证吗
	private String code;
	//手机号
	private String mobile;
	public boolean isLogin(){
		if(uid==null||"".equalsIgnoreCase(uid)){
			return false;
		}
		return true;
	}

	public void clear() {
		uid = null;
		code = null;
		mobile = null;

		SharedPreferences preference = JhApplication.getInstance().getSharedPreferences("user_config", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preference.edit();
		editor.clear();
		editor.apply();
	}

	public void save(){
		SharedPreferences preference = JhApplication.getInstance().getSharedPreferences("user_config", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preference.edit();
		editor.putString("uid", uid);
		editor.putString("code", code);
		editor.putString("mobile", mobile);
		editor.apply();
	}

	private void init(){
		SharedPreferences preference = JhApplication.getInstance().getSharedPreferences("user_config", Context.MODE_PRIVATE);
		uid = preference.getString("uid", null);
		code = preference.getString("code", null);
		mobile = preference.getString("mobile", null);

	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}
