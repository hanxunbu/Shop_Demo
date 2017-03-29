package com.gaojia.shop_demo.home;

/**
 * Created by Administrator on 2017/3/29 0029.
 */

public class GoodsInfo {
    String name;
    String describe;
    int price;

    public GoodsInfo(String name, String describe, int price) {
        this.name = name;
        this.describe = describe;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
