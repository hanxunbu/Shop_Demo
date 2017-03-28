package com.gaojia.shop_demo.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;


import com.gaojia.shop_demo.R;
import com.gaojia.shop_demo.base.BaseActivity;
import com.gaojia.shop_demo.base.ShowWhatFragmentListener;

import cn.bmob.v3.Bmob;

/**
 * Created by Administrator on 2017/3/23 0023.
 */

public class UserActivity extends BaseActivity implements ShowWhatFragmentListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Bmob.initialize(this, "64ff02a0268ea3a576ec974dbebb3226");
        showLoginFragment();
    }

    @Override
    public void setTargetFragment(String targetFragment) {
        switch (targetFragment) {
            case "LoginFragment":
                showLoginFragment();
                break;
            case "RegisterFragment":
                showRegisterFragment();
                break;
        }
    }
    private void showLoginFragment() {
        LoginFragment loginFragment = new LoginFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.loginfragment, loginFragment);
        ft.commit();
    }

    private void showRegisterFragment(){
        RegisterFragment registerFragment = new RegisterFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.loginfragment,registerFragment);
        ft.commit();
    }
}
