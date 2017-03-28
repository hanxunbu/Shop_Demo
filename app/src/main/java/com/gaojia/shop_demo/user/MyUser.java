package com.gaojia.shop_demo.user;

import cn.bmob.v3.BmobUser;

/**
 * Created by Administrator on 2017/3/28 0028.
 */

public class MyUser extends BmobUser {
    private Boolean sex;
    private String nick;
    private Integer age;
    private Integer gold;

    public Integer getGold() {
        return gold;
    }

    public void setGold(Integer gold) {
        this.gold = gold;
    }

    public boolean getSex() {
        return this.sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getNick() {
        return this.nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
