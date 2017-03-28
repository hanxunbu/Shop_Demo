package com.gaojia.shop_demo.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/3/22 0022.
 */

public class BaseActivity extends AppCompatActivity {
    protected ActionBar actionBar;
    protected ImageView back,menu;
    protected TextView title;
    public  void startActivity (Class targetClass){
        Intent intent = new Intent(this,targetClass);
        startActivity(intent);
    }

    public void startActivity(Class targetClass , Bundle bundle){
        Intent intent = new Intent(this,targetClass);
        intent.putExtra("bundle",bundle);
        startActivity(intent);
    }
}
