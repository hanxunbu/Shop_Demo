package com.gaojia.shop_demo.ec;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gaojia.shop_demo.R;
import com.gaojia.shop_demo.base.BaseActivity;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

/**
 * Created by Administrator on 2017/3/30.
 */
public class ECloginActivity extends BaseActivity {

    // username 输入框
    private EditText mUsernameEdit;
    // 密码输入框
    private EditText mPasswordEdit;
    // 注册按钮
    private Button mSignUpBtn;
    // 登录按钮
    private Button mSignInBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_ec);
        init();
    }

    private void init() {
        mUsernameEdit = (EditText) findViewById(R.id.ec_edit_username);
        mPasswordEdit = (EditText) findViewById(R.id.ec_edit_password);

        mSignUpBtn = (Button) findViewById(R.id.ec_btn_sign_up);
        mSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        mSignInBtn = (Button) findViewById(R.id.ec_btn_sign_in);
        mSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signin();
            }
        });
    }

    private void signup(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String username = mUsernameEdit.getText().toString().trim();
                String password = mPasswordEdit.getText().toString().trim();
                try {
                    EMClient.getInstance().createAccount(username, password);
                    Log.i("fjw","注册成功");
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    Log.e("fjw","注册失败" + e.getErrorCode() + "," + e.getMessage() );
                }
            }
        }).start();
    }

    private void signin(){
        String username = mUsernameEdit.getText().toString().trim();
        String password = mPasswordEdit.getText().toString().trim();
        EMClient.getInstance().login(username, password, new EMCallBack() {
            @Override
            public void onSuccess() {
                startActivity(new Intent(ECloginActivity.this,ECMainActivity.class));
                finish();
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(ECloginActivity.this, "登录失败", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }


}

