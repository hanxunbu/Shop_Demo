package com.gaojia.shop_demo;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;


import com.gaojia.shop_demo.base.BaseActivity;
import com.gaojia.shop_demo.base.TestFragment;
import com.gaojia.shop_demo.category.CategoryFragment;
import com.gaojia.shop_demo.find.FindFragment;
import com.gaojia.shop_demo.home.HomeFragment;
import com.gaojia.shop_demo.mine.MineFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;

public class MainActivity extends BaseActivity implements OnTabSelectListener {
    @BindView(R.id.bottom_bar)
    BottomBar mBottomBar;
    private MineFragment mMineFragment;
    private HomeFragment mHomeFragment;
    private CategoryFragment mCategoryFragment;
    private FindFragment mFindFragment;
    private Fragment mCurrentFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();

        //第一：默认初始化
//        Bmob.initialize(this, "Your Application ID");

//        第二：自v3.4.7版本开始,设置BmobConfig,允许设置请求超时时间、文件分片上传时每片的大小、文件的过期时间(单位为秒)，
        BmobConfig config =new BmobConfig.Builder(this)
                //设置appkey
                .setApplicationId("64ff02a0268ea3a576ec974dbebb3226")
                //请求超时时间（单位为秒）：默认15s
                .setConnectTimeout(30)
                //文件分片上传时每片的大小（单位字节），默认512*1024
                .setUploadBlockSize(1024*1024)
                //文件的过期时间(单位为秒)：默认1800s
                .setFileExpiration(2500)
                .build();
        Bmob.initialize(config);
    }

    private void initView() {
        // 可以看一下Fragmentmanager里面是不是已经有了这些Fragment
        retrieveFragment();

        // alt+enter
        // 设置导航选择的监听事件
        mBottomBar.setOnTabSelectListener(this);
    }

    private void retrieveFragment() {
        FragmentManager manager = getSupportFragmentManager();
        mMineFragment = (MineFragment) manager.findFragmentByTag(MineFragment.class.getName());
        mHomeFragment = (HomeFragment) manager.findFragmentByTag(HomeFragment.class.getName());
        mCategoryFragment = (CategoryFragment) manager.findFragmentByTag(CategoryFragment.class.getName());
        mFindFragment = (FindFragment) manager.findFragmentByTag(FindFragment.class.getName());
    }

    private void switchfragment(Fragment target) {
        // add show hide的方式

        if (mCurrentFragment==target) return;

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (mCurrentFragment!=null){
            transaction.hide(mCurrentFragment);
        }
        if (target.isAdded()){
            // 如果已经添加到FragmentManager里面，就展示
            transaction.show(target);
        }else {
            // 为了方便找到Fragment，我们是可以设置Tag
            String tag;
            if (target instanceof TestFragment){
                tag = ((TestFragment)target).getArgumentText();
            }else {

                // 把类名作为tag
                tag = target.getClass().getName();
            }

            // 添加Fragment并设置Tag
            transaction.add(R.id.layout_container,target,tag);
        }

        transaction.commit();
        mCurrentFragment=target;
    }


    @Override
    public void onTabSelected(@IdRes int tabId) {
        switch (tabId){
            case R.id.tab_home:
                if(mHomeFragment==null){
                    mHomeFragment = HomeFragment.newInstance();
                }
                switchfragment(mHomeFragment);
                break;
            case R.id.tab_mine:
                if (mMineFragment==null){
                    mMineFragment = MineFragment.newInstance();
                }
                // 切换Fragment
                switchfragment(mMineFragment);
                break;
            case R.id.tab_category:
                if(mCategoryFragment==null){
                    mCategoryFragment = CategoryFragment.newInstance();
                }
                switchfragment(mCategoryFragment);
                break;
            case R.id.tab_find:
                if(mFindFragment==null){
                    mFindFragment = FindFragment.newInstance();
                }
                switchfragment(mFindFragment);
                break;
        }
    }
}
