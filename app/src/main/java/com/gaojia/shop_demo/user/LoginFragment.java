package com.gaojia.shop_demo.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.gaojia.shop_demo.MainActivity;
import com.gaojia.shop_demo.R;
import com.gaojia.shop_demo.mine.MineFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2017/3/14 0014.
 */
public class LoginFragment extends Fragment {
    @BindView(R.id.fragment_login_login_bt)
            Button login;
    @BindView(R.id.fragment_login_signup_bt)
            Button register;
    @BindView(R.id.fragment_login_name_et)
            EditText name;
    @BindView(R.id.fragment_login_pwd_et)
            EditText pwd;
    String uname;
    String upwd;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
    @OnClick({R.id.fragment_login_login_bt,R.id.fragment_login_signup_bt,R.id.fragment_login_name_et,R.id.fragment_login_pwd_et})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.fragment_login_login_bt:
                uname = name.getText().toString().trim();
                upwd = pwd.getText().toString().trim();
                if(TextUtils.isEmpty(uname)|| TextUtils.isEmpty(upwd)){
                    Toast.makeText(getContext(),"请输入正确用户名和密码",Toast.LENGTH_SHORT).show();
                }else{
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            BmobUser login = new BmobUser();
                            login.setUsername(uname);
                            login.setPassword(upwd);
                            login.login(new SaveListener<BmobUser>() {

                                @Override
                                public void done(BmobUser bmobUser, BmobException e) {
                                    if(e==null){
                                        Toast.makeText(getContext(),"登录成功",Toast.LENGTH_SHORT).show();
                                        //通过BmobUser user = BmobUser.getCurrentUser()获取登录成功后的本地用户信息
                                        //如果是自定义用户对象MyUser，可通过MyUser user = BmobUser.getCurrentUser(MyUser.class)获取自定义用户信息
                                        Intent showMain = new Intent(getContext(),MainActivity.class);
                                        startActivity(showMain);
                                        MineFragment mineFragment = new MineFragment();

                                    }else{
                                        Toast.makeText(getContext(),"登录失败",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }).start();
                }
                break;
            case R.id.fragment_login_signup_bt:
                UserActivity activity = (UserActivity) getActivity();
                activity.setTargetFragment("RegisterFragment");
                break;
        }
    }
}
