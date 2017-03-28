package com.gaojia.shop_demo.user;

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


import com.gaojia.shop_demo.R;

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
//    Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            if (msg != null) {
//                switch (msg.what) {
//                    case App.FAILURE:
//                        Toast.makeText(getContext(), "请检查网络连接设置", Toast.LENGTH_SHORT).show();
//                        break;
//                    case App.SUCCEED:
//                        LoginInfo info = (LoginInfo) msg.obj;
//                        LoginInfo.LoginData data = info.getData();
//                        if (info.getData() == null) {
//                            Toast.makeText(getContext(), "用户名或密码错误", Toast.LENGTH_SHORT).show();
//                        } else {
//                            switch (data.getResult()) {
//                                case 0:
//                                    Toast.makeText(getContext(), "登录成功", Toast.LENGTH_SHORT).show();
//                                    Intent intent = new Intent();
//                                    intent.putExtra("token", data.getToken());
//                                    intent.putExtra("name", uname);
//                                    getActivity().setResult(App.RESULTCODE, intent);
//                                    getActivity().finish();
//                                    break;
//                                case -1:
//                                    Toast.makeText(getContext(), "用户名或密码错误", Toast.LENGTH_SHORT).show();
//                                    break;
//                                case -2:
//                                    Toast.makeText(getContext(), "限制登陆", Toast.LENGTH_SHORT).show();
//                                    break;
//                                case -3:
//                                    Toast.makeText(getContext(), "限制登陆(异地登陆等异常)", Toast.LENGTH_SHORT).show();
//                                    break;
//                            }
//                        }
//                        break;
//                }
//            }
//        }
//    };


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
                                        Toast.makeText(getContext(),"登录成功:",Toast.LENGTH_SHORT).show();
                                        //通过BmobUser user = BmobUser.getCurrentUser()获取登录成功后的本地用户信息
                                        //如果是自定义用户对象MyUser，可通过MyUser user = BmobUser.getCurrentUser(MyUser.class)获取自定义用户信息
                                    }else{
                                        Toast.makeText(getContext(),"登录失败:",Toast.LENGTH_SHORT).show();
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
