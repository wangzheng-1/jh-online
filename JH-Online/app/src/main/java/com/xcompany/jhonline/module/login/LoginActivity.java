package com.xcompany.jhonline.module.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xcompany.jhonline.R;
import com.xcompany.jhonline.base.BaseActivity;
import com.xcompany.jhonline.module.system.activity.MainActivity;
import com.xcompany.jhonline.network.LoginRequestUtil;
import com.xcompany.jhonline.network.LoginResponse;
import com.xcompany.jhonline.network.NetCallBack;
import com.xcompany.jhonline.network.UserService;
import com.xcompany.jhonline.utils.T;
import com.xcompany.jhonline.widget.CleanEditText;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.titleLayout)
    LinearLayout titleLayout;
    @BindView(R.id.logoLayout)
    LinearLayout logoLayout;
    @BindView(R.id.mobileEdit)
    CleanEditText mobileEdit;
    @BindView(R.id.mobileLayout)
    LinearLayout mobileLayout;
    @BindView(R.id.identifyEdit)
    CleanEditText identifyEdit;
    @BindView(R.id.sendIdentifyText)
    TextView sendIdentifyText;
    @BindView(R.id.identifyLayout)
    LinearLayout identifyLayout;
    @BindView(R.id.loginFormLayout)
    LinearLayout loginFormLayout;
    @BindView(R.id.loginText)
    TextView loginText;
    @BindView(R.id.userProtocolLayout)
    LinearLayout userProtocolLayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initListener();
    }

    private void initListener() {
        //用户名输入框回车时间监听
        mobileEdit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    identifyEdit.setFocusable(true);
                    identifyEdit.requestFocus();
                    return true;
                }
                return false;
            }
        });
        mobileEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mobileEdit.hasFoucs) {
                    mobileEdit.setClearIconVisible(s.length() > 0);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() >0 && identifyEdit.getText() != null && identifyEdit.getText().length() >0){
                    setLoginTextEnable(true);
                }
                else {
                    setLoginTextEnable(false);
                }
            }
        });{

        }
        identifyEdit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    loginText.setEnabled(false);
                    login();
                    return true;
                }
                return false;
            }
        });
        identifyEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (identifyEdit.hasFoucs) {
                    identifyEdit.setClearIconVisible(s.length() > 0);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() >0 && mobileEdit.getText() != null && mobileEdit.getText().length() >0){
                    setLoginTextEnable(true);
                }
                else {
                    setLoginTextEnable(false);
                }
            }
        });
        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLoginTextEnable(false);
                login();
            }
        });
    }

    private Long getIdentifyCodeTime;  //获取验证码开始时间
    private LoginResponse loginResponse; //获取到的验证码信息
    private String mobile;   //获取验证码时的手机号

    @OnClick({R.id.sendIdentifyText, R.id.loginText, R.id.userProtocolLayout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sendIdentifyText:
                if(mobileEdit.getText().toString().isEmpty()){
                    T.showToast(LoginActivity.this,"请输入正确的手机号或验证码");
                    return;
                }
                sendIdentifyText.setEnabled(false);
                sendIdentifyText.setTextColor(getResources().getColor(R.color.text_light_gray));
                recLen = 60;
                timer = new Timer();
                task = new IdentifyTimerTask();
                timer.schedule(task, 1000, 1000);
                sendIdentifyCode();
                break;
            case R.id.loginText:

                break;
            case R.id.userProtocolLayout:
                break;
        }
    }
    class IdentifyTimerTask extends TimerTask {
        @Override
        public void run() {
            recLen --;
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }
    }
    private int recLen = 60;
    Timer timer = new Timer();
    IdentifyTimerTask task = new IdentifyTimerTask();

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what) {
                case 1:
                    sendIdentifyText.setText(recLen + "秒后重新获取");
                    if (recLen <= 0) {
                        timer.cancel();
                        sendIdentifyText.setTextColor(getResources().getColor(R.color.text_blue));
                        sendIdentifyText.setText("发送");
                        sendIdentifyText.setEnabled(true);
                    }
            }
        }
    };

    private void setLoginTextEnable(boolean enable){
        loginText.setEnabled(enable);
        if(enable){
            loginText.setBackground(getResources().getDrawable(R.drawable.background_rectangle_all_corner_blue));
        }
        else {
            loginText.setBackground(getResources().getDrawable(R.drawable.background_rectangle_all_corner_gray));
        }
    }
    private void login(){

        long loginTime = System.currentTimeMillis();
        if(loginResponse == null || loginResponse.getCode() == null  //未获取验证码
                || getIdentifyCodeTime == null   //未获取验证吗
                || mobile.isEmpty()   //未获取验证吗
                || ((loginTime - getIdentifyCodeTime) >(10*60*1000)) //超过10分钟
                || identifyEdit.getText().toString().isEmpty()  // 未输入验证码
                || !identifyEdit.getText().toString().equals(loginResponse.getCode()) //验证码与所发验证码不相同
                || mobileEdit.getText().toString().isEmpty()  //  未输入手机号码
                || !mobile.equals(mobileEdit.getText().toString())  //发送验证码的手机与当前输入手机号不匹配
                ) {
            T.showToast(LoginActivity.this,"请输入正确的手机号或验证码");
            return;
        }
        else {
            UserService userService = UserService.getInstance();
            userService.setCode(loginResponse.getCode());
            userService.setUid(loginResponse.getUid());
            userService.setMobile(mobile);
            userService.save();
            Intent intent = new Intent();
            intent.setClass(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            LoginActivity.this.finish();
        }
    }

    /**
     * 获取验证码
     */
    private void sendIdentifyCode(){
        Map param = new HashMap();
        param.put("type","0");
        param.put("port","3");
        param.put("telephone",mobileEdit.getText().toString());
        String interfaceName = "SendSms/code";
        getIdentifyCodeTime = System.currentTimeMillis();
        mobile = mobileEdit.getText().toString();
        loginResponse = null;
        LoginRequestUtil.loginRequest(interfaceName, param, new NetCallBack<LoginResponse>() {
            @Override
            public void done(List<LoginResponse> list, int count, Exception e) {
                if(e != null){
                    T.showToast(LoginActivity.this,e.getMessage());
                    getIdentifyCodeTime = null;
                }
                timer.cancel();
                sendIdentifyText.setTextColor(getResources().getColor(R.color.text_blue));
                sendIdentifyText.setText("发送");
                sendIdentifyText.setEnabled(true);
                loginResponse = list.get(0);
            }
        });
    }

}
