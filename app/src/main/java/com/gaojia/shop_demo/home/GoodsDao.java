package com.gaojia.shop_demo.home;
import com.gaojia.shop_demo.goodsload.GoodsLoad;
import java.util.ArrayList;
import java.util.List;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2017/3/29 0029.
 */

public class GoodsDao {
    public List<GoodsInfo> select() {
        List<GoodsInfo> savelist  = new ArrayList<>();
        BmobQuery<GoodsLoad> query = new BmobQuery<GoodsLoad>();
        final String[] name = new String[1];
        final Integer[] price = new Integer[1];
        final String[] describe = new String[1];
        query.addQueryKeys("goodID,goodprice,goodsdescribe");
        query.findObjects(new FindListener<GoodsLoad>() {
            @Override
            public void done(List<GoodsLoad> list, BmobException e) {
                if(e == null){
                    for(GoodsLoad goodsLoad : list){
                         name[0] = goodsLoad.getGoodsID();
                         price[0] = goodsLoad.getGoodsprice();
                         describe[0] = goodsLoad.getGoodsdescribe();
                    }
                }
            }

        });
        GoodsInfo goodsInfo = new GoodsInfo(name[0],describe[0],price[0]);
        savelist.add(goodsInfo);

        return savelist;
    }
}
