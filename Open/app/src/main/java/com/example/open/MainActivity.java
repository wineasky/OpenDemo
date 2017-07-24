package com.example.open;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import com.example.open.fragment.*;

/**
 * MainActivity
 *      1.继承Activity,实现监听接口
 *      2.定义成员变量：对应的页面索引及Fragment页面、FragmentManager、导航栏
 *      3.重写的两个方法：
 *        onCreate{setOnTabSelectListener(位于Nav..Bar)、getFragment、addFragment}
 *        onTabSelected{hideAllFragment、showFragment}
 */
public class MainActivity extends AppCompatActivity
    //实现接口用于获取this来判定当前的状态
        implements OnTabSelectListener {
    //定义导航栏的页面索引
    private static final int TAB_HOME = 0;
    private static final int TAB_RANK = 1;
    private static final int TAB_CICLE = 2;
    private static final int TAB_ALBUM = 3;
    private static final int TAB_USER = 4;
    //定义FragmentManager以及Fragment的页面
    private FragmentManager mFragmentManager;
    private Fragment mHomePage;
    private Fragment mRankPage;
    private Fragment mCiclePage;
    private Fragment mAlbumPage;
    private Fragment mUserPage;

    //定义导航栏对象
    private NavigationBar mNavigator;

    /**
     *  onCreate:
     *       1.创建Activity实例并设置要显示的布局
     *       2.配置导航栏：找到导航栏&监听点击事件
     *       3.加载Fragment页面：a.获取管理器
     *                          b.设置Fragment页面&提交事务到Activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
       // requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       //将在activity_main中对应的navigator在这里配置
        mNavigator = (NavigationBar)findViewById(R.id.navigator);
        //设置选中事件，this报错是因为没有在这里实现接口，所以this就不是listener
        mNavigator.setOnTabSelectListener(this);
        //动态加载Fragment
        //设置FragmentManager,此处导包对应support.v4
        mFragmentManager = getSupportFragmentManager();
        //通过getFragment()设置mHomepage的Fragment页面
        mHomePage = getFragment(HomeFragment.class,null);
        //添加mHomePage的事务
        addFragment(mHomePage);
    }
    //【onCreate】关联Fragment要在activity中显示的位置并提交事务
    private void addFragment(Fragment fragment) {
        //定义事务
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        //添加对应在activity_main的布局，来加载fragment
        transaction.add(R.id.fragment_container,fragment);
        //提交fragment的事务到Activity
        transaction.commit();
    }
    //【onCreate】获取Fragment
    private Fragment getFragment(Class clazz, Bundle args) {
        //通过instantiate()实例化Fragment,填写clazz的名字
         return  Fragment.instantiate(this,clazz.getName(), null);
    }


    @Override//重写标签选中的方法
    public void onTabSelected(int index) {
        //隐藏所有的Fragment
        hideAllFragment();
        //根据获取的当前索引进行匹配
        switch (index){
            case TAB_HOME:
                showFragment(mHomePage,true); //显示mHomePage
                break;//跳出循环
            case TAB_RANK:
                if(null == mRankPage){
                    //若还没有创建mRankPage就通过getFragment获取一个
                    mRankPage = getFragment(RankFragment.class,null);
                    //添加到Fragment
                    addFragment(mRankPage);
                }else{
                    //如果已经有mRankPage，就显示出来
                    showFragment(mRankPage,true);
                }
                break;
            case TAB_CICLE://同上
                if(null == mCiclePage){
                    mCiclePage = getFragment(AlbumFragment.class,null);
                    addFragment(mCiclePage);
                }else{
                    showFragment(mCiclePage,true);
                }
                break;
            case TAB_ALBUM:
                if(null == mAlbumPage){
                    mAlbumPage = getFragment(PhotoFragment.class,null);
                    addFragment(mAlbumPage);
                }else{
                    showFragment(mAlbumPage,true);
                }
                break;
            case TAB_USER:
                if(null == mUserPage){
                    mUserPage = getFragment(UserFragment.class,null);
                    addFragment(mUserPage);
                }else{
                    showFragment(mUserPage,true);
                }
                break;
        }


    }
    //【onTabSelected】默认不显示所有Fragment
    private void hideAllFragment() {
        //Fragment不为空的时候 ,默认值false，通过showFragment进行判断，隐藏一个fragment
        if(null!=mHomePage)
            showFragment(mHomePage,false);
        if(null!=mAlbumPage)
            showFragment(mAlbumPage,false);
        if(null!=mRankPage)
            showFragment(mRankPage,false);
        if(null!=mCiclePage)
            showFragment(mCiclePage,false);
        if(null!=mUserPage)
            showFragment(mUserPage,false);
    }
    //【onTabSelected】选择要显示的Fragment,
    private void showFragment(Fragment fragment, boolean show) {
       //通过FragmentManager进行Fragment的事务
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        if(show){ /**为什么这个show是true*/
            transaction.show(fragment);//显示
        }else {
            transaction.hide(fragment); //隐藏
        }
        //提交事务
        transaction.commit();
    }
}
