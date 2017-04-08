package com.gaojia.shop_demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.gaojia.shop_demo.base.MyConnectionListener;
import com.gaojia.shop_demo.ec.ECChatActivity;
import com.gaojia.shop_demo.runtimepermissions.PermissionsManager;
import com.gaojia.shop_demo.runtimepermissions.PermissionsResultAction;
import com.gaojia.shop_demo.vip.VIPFragment;
import com.gaojia.shop_demo.base.BaseActivity;
import com.gaojia.shop_demo.base.ShowWhatFragmentListener;
import com.gaojia.shop_demo.base.TestFragment;
import com.gaojia.shop_demo.home.HomeFragment;
import com.gaojia.shop_demo.mine.MineFragment;
import com.hyphenate.EMContactListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseContactListFragment;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.hyphenate.exceptions.HyphenateException;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.socks.library.KLog;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;

public class MainActivity extends BaseActivity implements OnTabSelectListener,ShowWhatFragmentListener {
    @BindView(R.id.bottom_bar)
    BottomBar mBottomBar;
    //我的页面
    private MineFragment mMineFragment;
    //主页面
    private HomeFragment mHomeFragment;
    //通讯录
    private EaseConversationListFragment conversationListFragment;
    //好友列表
    private static EaseContactListFragment contactListFragment;
    private static EMMessageListener emMessageListener;
    private Fragment mCurrentFragment;
    private VIPFragment mVipFragment;

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

        /**
         * 请求所有必要的权限----
         */
        PermissionsManager.getInstance().requestAllManifestPermissionsIfNecessary(this, new PermissionsResultAction() {
            @Override
            public void onGranted() {
//				Toast.makeText(MainActivity.this, "All permissions have been granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDenied(String permission) {
                //Toast.makeText(MainActivity.this, "Permission " + permission + " has been denied", Toast.LENGTH_SHORT).show();
            }
        });
        //注册一个监听连接状态的listener
        EMClient.getInstance().addConnectionListener(new MyConnectionListener(this));
        emMessageListener = new EMMessageListener() {

            @Override
            public void onMessageReceived(List<EMMessage> messages) {
                //收到消息----刷新一下当前页面喽
                conversationListFragment.refresh();
                EMClient.getInstance().chatManager().importMessages(messages);//保存到数据库
            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> messages) {
                //收到透传消息
            }

            @Override
            public void onMessageRead(List<EMMessage> list) {

            }

            @Override
            public void onMessageDelivered(List<EMMessage> list) {

            }


            @Override
            public void onMessageChanged(EMMessage message, Object change) {
                //消息状态变动
            }
        };
        EMClient.getInstance().chatManager().addMessageListener(emMessageListener);

    }

    private void initView() {
        // 可以看一下Fragmentmanager里面是不是已经有了这些Fragment
        retrieveFragment();

        // alt+enter
        // 设置导航选择的监听事件
        mBottomBar.setOnTabSelectListener(this);

        EMClient.getInstance().contactManager().setContactListener(new EMContactListener() {

            @Override
            public void onContactInvited(String username, String reason) {
                //收到好友邀请
            }

            @Override
            public void onFriendRequestAccepted(String s) {

            }

            @Override
            public void onFriendRequestDeclined(String s) {

            }

            @Override
            public void onContactDeleted(String username) {
                KLog.e("好友被删除了" + username);
                //被删除时回调此方法

                new Thread() {//需要在子线程中调用
                    @Override
                    public void run() {
                        //需要设置联系人列表才能启动fragment
                        contactListFragment.setContactsMap(getContact());
                        contactListFragment.refresh();
                    }
                }.start();
            }


            @Override
            public void onContactAdded(String username) {
                //增加了联系人时回调此方法
                KLog.e("添加好友了" + username);

                new Thread() {//需要在子线程中调用
                    @Override
                    public void run() {
                        //需要设置联系人列表才能启动fragment
                        contactListFragment.setContactsMap(getContact());
                        contactListFragment.refresh();
                    }
                }.start();

            }
        });

        contactListFragment = new EaseContactListFragment();
        new Thread() {//需要在子线程中调用
            @Override
            public void run() {
                //需要设置联系人列表才能启动fragment
                contactListFragment.setContactsMap(getContact());
            }
        }.start();

        //设置item点击事件
        contactListFragment.setContactListItemClickListener(new EaseContactListFragment.EaseContactListItemClickListener() {

            @Override
            public void onListItemClicked(EaseUser user) {
                startActivity(new Intent(MainActivity.this, ECChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, user.getUsername()));
            }
        });
        conversationListFragment = new EaseConversationListFragment();
        conversationListFragment.setConversationListItemClickListener(new EaseConversationListFragment.EaseConversationListItemClickListener() {

            @Override
            public void onListItemClicked(EMConversation conversation) {
                //进入聊天页面
                startActivity(new Intent(MainActivity.this, ECChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, conversation.conversationId()));
            }
        });
        getSupportFragmentManager().beginTransaction().add(R.id.layout_container, conversationListFragment).commit();
    }


    private void retrieveFragment() {
        FragmentManager manager = getSupportFragmentManager();
        mMineFragment = (MineFragment) manager.findFragmentByTag(MineFragment.class.getName());
        mHomeFragment = (HomeFragment) manager.findFragmentByTag(HomeFragment.class.getName());
        mVipFragment = (VIPFragment) manager.findFragmentByTag(VIPFragment.class.getName());
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
            case R.id.tab_addressbooks:
                if(contactListFragment == null){
                    contactListFragment = new EaseContactListFragment();
                }
                switchfragment(contactListFragment);
                break;
            case R.id.tab_friend:
                if(conversationListFragment == null){
                    conversationListFragment = new EaseConversationListFragment();
                }
                switchfragment(conversationListFragment);
                break;
            case R.id.tab_king:
                if(mVipFragment == null){
                    mVipFragment = VIPFragment.newInstance();
                }
                switchfragment(mVipFragment);
                break;
        }
    }

    @Override
    public void setTargetFragment(String targetFragment) {
        switch (targetFragment){
            case "MainFragment":
                if(mHomeFragment==null){
                    mHomeFragment = HomeFragment.newInstance();
                }
                switchfragment(mHomeFragment);
                break;
            case "MineFragment":
                if(mMineFragment == null){
                    mMineFragment = MineFragment.newInstance();
                }
                switchfragment(mMineFragment);
        }
    }

    private Map<String, EaseUser> getContact() {
        Map<String, EaseUser> map = new HashMap<>();
        try {
            List<String> userNames = EMClient.getInstance().contactManager().getAllContactsFromServer();
//            KLog.e("......有几个好友:" + userNames.size());
            for (String userId : userNames) {
//                KLog.e("好友列表中有 : " + userId);
                map.put(userId, new EaseUser(userId));
            }
        } catch (HyphenateException e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().chatManager().removeMessageListener(emMessageListener);
    }
}
