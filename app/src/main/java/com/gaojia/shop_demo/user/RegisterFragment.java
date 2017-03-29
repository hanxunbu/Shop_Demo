package com.gaojia.shop_demo.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.gaojia.shop_demo.MainActivity;
import com.gaojia.shop_demo.R;
import com.gaojia.shop_demo.base.BaseFragment;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import static android.R.attr.key;
import static android.R.attr.switchMinWidth;
import static android.R.attr.value;

/**
 * Created by Administrator on 2017/3/14 0014.
 */
public class RegisterFragment extends BaseFragment {
    @BindView(R.id.fragment_register_user_et)
    EditText name;
    @BindView(R.id.fragment_register_pwd_et)
    EditText pwd;
    @BindView(R.id.fragment_register_email_et)
    EditText email;
    @BindView(R.id.fragment_register_bt)
    Button register;
    @BindView(R.id.fragment_register_recommend_et)
    EditText recommend;
    @BindView(R.id.fragment_recommend_bt)
    Button chack;
    String uname;
    String upwd;
    String uemail;
    String urecommend;
    int commendCheck = 0;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public static boolean regexUname(String str) {
        Pattern p = Pattern.compile("^[a-zA-Z]\\w{5,24}$");
        Matcher m = p.matcher(str);
        boolean b = m.matches();
        return b;
    }

    public static boolean regexUpwd(String str) {
        Pattern p = Pattern.compile("^[a-zA-Z0-9]\\w{6,24}$");
        Matcher m = p.matcher(str);
        boolean b = m.matches();
        return b;
    }

    public static boolean regexUemail(String str) {
        Pattern p = Pattern.compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
        Matcher m = p.matcher(str);
        boolean b = m.matches();
        return b;
    }


    @OnClick({R.id.fragment_register_bt, R.id.fragment_recommend_bt})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.fragment_register_bt:
                uname = name.getText().toString().trim();
                upwd = pwd.getText().toString().trim();
                uemail = email.getText().toString().trim();
                urecommend = recommend.getText().toString().trim();
                BmobQuery<MyUser> query = new BmobQuery<>();
                query.addWhereEqualTo("username", urecommend);
                query.findObjects(new FindListener<MyUser>() {
                    @Override
                    public void done(List<MyUser> list, BmobException e) {
                        if (list.size() == 1) {
//                            Toast.makeText(getActivity(),"验证成功",Toast.LENGTH_SHORT).show();
                            commendCheck = 1;
                        } else {
//                            Toast.makeText(getActivity(),"验证失败,找不到此好友",Toast.LENGTH_SHORT).show();
                            commendCheck = 0;
                        }
                    }
                });
                switch (commendCheck) {
                    case 0:
                        if (regexUname(uname) == false || TextUtils.isEmpty(uname)) {
                            Toast.makeText(getContext(), "请输入正确的用户名", Toast.LENGTH_SHORT).show();
                        } else if (TextUtils.isEmpty(upwd) || regexUpwd(upwd) == false) {
                            Toast.makeText(getContext(), "请输入正确的密码", Toast.LENGTH_SHORT).show();
                        } else if (TextUtils.isEmpty(uemail) || regexUemail(uemail) == false) {
                            Toast.makeText(getContext(), "请输入正确的邮箱地址", Toast.LENGTH_SHORT).show();
                        } else {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    MyUser register = new MyUser();
                                    register.setUsername(uname);
                                    register.setPassword(upwd);
                                    register.setEmail(uemail);
                                    register.setGold(50);
//注意：不能用save方法进行注册
                                    register.signUp(new SaveListener<MyUser>() {
                                        @Override
                                        public void done(MyUser s, BmobException e) {
                                            if (e == null) {
                                                Toast.makeText(getActivity(), "注册成功", Toast.LENGTH_SHORT).show();
                                                Intent showMain = new Intent(getContext(),MainActivity.class);
                                                startActivity(showMain);
                                            } else {
                                                Toast.makeText(getActivity(), "注册失败", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            }).start();
                        }

                        break;

                    case 1:
                        if (regexUname(uname) == false || TextUtils.isEmpty(uname)) {
                            Toast.makeText(getContext(), "请输入正确的用户名", Toast.LENGTH_SHORT).show();
                        } else if (TextUtils.isEmpty(upwd) || regexUpwd(upwd) == false) {
                            Toast.makeText(getContext(), "请输入正确的密码", Toast.LENGTH_SHORT).show();
                        } else if (TextUtils.isEmpty(uemail) || regexUemail(uemail) == false) {
                            Toast.makeText(getContext(), "请输入正确的邮箱地址", Toast.LENGTH_SHORT).show();
                        } else {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    final MyUser register = new MyUser();
                                    register.setUsername(uname);
                                    register.setPassword(upwd);
                                    register.setEmail(uemail);
                                    register.setGold(100);
                                    //注意：不能用save方法进行注册
                                    register.signUp(new SaveListener<MyUser>() {
                                        @Override
                                        public void done(MyUser s, BmobException e) {
                                            if (e == null) {
                                                Toast.makeText(getActivity(), "注册成功", Toast.LENGTH_SHORT).show();
                                                Intent showMain = new Intent(getContext(),MainActivity.class);
                                                startActivity(showMain);
                                            } else {
                                                Toast.makeText(getActivity(), "注册失败", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            }).start();
                        }
                        break;

                    case R.id.fragment_recommend_bt:
                        urecommend = recommend.getText().toString().trim();
                        BmobQuery<MyUser> query1 = new BmobQuery<>();
                        query1.addWhereEqualTo("username", urecommend);
                        query1.findObjects(new FindListener<MyUser>() {
                            @Override
                            public void done(List<MyUser> list, BmobException e) {
                                if (list.size() == 1) {
                                    Toast.makeText(getActivity(), "验证成功", Toast.LENGTH_SHORT).show();
                                    commendCheck = 1;
                                } else {
                                    Toast.makeText(getActivity(), "验证失败,找不到此好友", Toast.LENGTH_SHORT).show();
                                    commendCheck = 0;

                                }
                            }
                        });
                }
        }
    }
}
