package com.gaojia.shop_demo.user;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/3/27 0027.
 */

public class UserLogin extends BmobObject {
    private String UserName;
    private String Password;
    private String UserEmail;

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
