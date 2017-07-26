package com.example.open;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import com.example.open.fragment.*;
import com.example.open.home.HomeFragment;
import com.example.open.user.UserFragment;
import com.example.open.mall.MallFragment;




/*
 *  MainActivity
 *      1.继承Activity,实现监听接口
 *      2.定义成员变量：对应的页面索引及Fragment页面、FragmentManager、导航栏
 *      3.重写的两个方法：
 *        onCreate{setOnTabSelectListener(位于Nav..Bar)、getFragment、addFragment}
 *        onTabSelected{hideAllFragment、showFragment}
 */

public class MainActivity extends AppCompatActivity
    //实现接口用于获取this来判定当前的状态
        implements OnTabSelectListener ,ViewPager.OnPageChangeListener {
    //定义导航栏的页面索引
    private static final int TAB_F1 = 0;
    private static final int TAB_F2 = 1;
    private static final int TAB_F3 = 2;
    private static final int TAB_F4 = 3;
    private static final int TAB_F5 = 4;
    //定义FragmentManager以及Fragment的页面
    private FragmentManager mFragmentManager;
    private Fragment mFragment1;
    private Fragment mFragment2;
    private Fragment mFragment3;
    private Fragment mFragment4;
    private Fragment mFragment5;

    private ViewPager mViewPager;

    private MyFragmentPageAdapter mAdapter;

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
        //隐藏标题，对应的在A~Manifest.xml里设置NoActionBar的主题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }


    private  void  initView(){
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        //通过getFragmentManager获取事务
        FragmentManager fm = getSupportFragmentManager();
        //初始化自定义适配器
        mAdapter =  new MyFragmentPageAdapter(fm);
        //绑定自定义适配器
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(this);
        //将在activity_main中对应的navigator在这里配置
        mNavigator = (NavigationBar)findViewById(R.id.navigator);
        //设置选中事件，this报错是因为没有在这里实现接口，所以this就不是listener
        mNavigator.setOnTabSelectListener(this);
         //动态加载Fragment
        //设置FragmentManager,此处导包对应support.v4
        mFragmentManager = getSupportFragmentManager();
        //通过getFragment()设置mHomepage的Fragment页面
        mFragment1 = getFragment(HomeFragment.class,null);
        //添加mHomePage的事务
        addFragment(mFragment1);

    }


    //【initView】关联Fragment要在activity中显示的位置并提交事务
    private void addFragment(Fragment fragment) {
        //定义事务
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        //添加对应在activity_main的布局，来加载fragment
        transaction.add(R.id.viewpager,fragment);
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
        mViewPager.setCurrentItem(index);
        //隐藏所有的Fragment
        hideAllFragment();
        //根据获取的当前索引进行匹配
        switch (index){
            case TAB_F1:
                showFragment(mFragment1,true); //显示mHomePage
                break;//跳出循环
            case TAB_F2:
                if(null == mFragment2){
                    //若还没有创建mRankPage就通过getFragment获取一个
                    mFragment2 = getFragment(Fragment2.class,null);
                    //添加到Fragment
                    addFragment(mFragment2);
                }else{
                    //如果已经有mFragment2，就显示出来
                    showFragment(mFragment2,true);
                }
                break;
            case TAB_F3://同上
                if(null == mFragment3){
                    mFragment3 = getFragment(Fragment3.class,null);
                    addFragment(mFragment3);
                }else{
                    showFragment(mFragment3,true);
                }
                break;
            case TAB_F4:
                if(null == mFragment4){
                    mFragment4 = getFragment(MallFragment.class,null);
                    addFragment(mFragment4);
                }else{
                    showFragment(mFragment4,true);
                }
                break;
            case TAB_F5:
                if(null == mFragment5){
                    mFragment5 = getFragment(UserFragment.class,null);
                    addFragment(mFragment5);
                }else{
                    showFragment(mFragment5,true);
                }
                break;
        }


    }
    //【onTabSelected】默认不显示所有Fragment
    private void hideAllFragment() {
        //Fragment不为空的时候 ,默认值false，通过showFragment进行判断，隐藏一个fragment
        if(null!=mFragment1)
            showFragment(mFragment1,false);
        if(null!=mFragment4)
            showFragment(mFragment4,false);
        if(null!=mFragment2)
            showFragment(mFragment2,false);
        if(null!=mFragment3)
            showFragment(mFragment3,false);
        if(null!= mFragment5)
            showFragment(mFragment5,false);
    }
    //【onTabSelected】选择要显示的Fragment,
    private void showFragment(Fragment fragment, boolean show) {
       //通过FragmentManager进行Fragment的事务
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        if(show){
            transaction.show(fragment);//显示
        }else {
            transaction.hide(fragment); //隐藏
        }
        //提交事务
        transaction.commit();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
       mNavigator.selectAt(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }



    public class MyFragmentPageAdapter  extends FragmentPagerAdapter {
        private OnTabSelectListener listener;


        public MyFragmentPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 5;
        }


        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return getFragment(HomeFragment.class,null);
                case 1:
                    return getFragment(Fragment2.class,null);
                case 2:
                    return getFragment(Fragment3.class,null);
                case 3:
                    return getFragment(MallFragment.class,null);
                case 4:
                    return getFragment(UserFragment.class,null);
                default:
                    return null;
            }
        }
    }
}


