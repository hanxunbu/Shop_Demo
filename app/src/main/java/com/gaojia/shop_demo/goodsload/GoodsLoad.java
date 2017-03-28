package com.gaojia.shop_demo.goodsload;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2017/3/27 0027.
 */

public class GoodsLoad extends BmobObject {
    private String goodsID;
    private Integer goodsprice;
    private String goodsdescribe;
    private BmobFile goodsphoto;
    private String goodstype;

    public String getGoodstype() {
        return goodstype;
    }

    public void setGoodstype(String goodstype) {
        this.goodstype = goodstype;
    }

    public String getGoodsID() {
        return goodsID;
    }

    public void setGoodsID(String goodsID) {
        this.goodsID = goodsID;
    }

    public Integer getGoodsprice() {
        return goodsprice;
    }

    public void setGoodsprice(Integer goodsprice) {
        this.goodsprice = goodsprice;
    }

    public String getGoodsdescribe() {
        return goodsdescribe;
    }

    public void setGoodsdescribe(String goodsdescribe) {
        this.goodsdescribe = goodsdescribe;
    }

    public BmobFile getGoodsphoto() {
        return goodsphoto;
    }

    public void setGoodsphoto(BmobFile goodsphoto) {
        this.goodsphoto = goodsphoto;
    }
}
