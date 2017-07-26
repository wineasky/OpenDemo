package com.example.open.home;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.example.open.OnTabSelectListener;
import com.example.open.R;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class HomeFragment extends Fragment implements  View.OnClickListener,OnTabSelectListener,ViewPager.OnPageChangeListener {
   private HomeNavigationBar1 mNavigationBar;
   private ViewPager mViewPager;
    @Override
    public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate
                (R.layout.fragment_home,container,false);
        mNavigationBar = (HomeNavigationBar1)v.findViewById(R.id.home_tab_bar);
        mNavigationBar.setOnTabSelectListener(this);


        mViewPager = (ViewPager) v.findViewById(R.id.view_pager);
        mViewPager.setAdapter(new FeedAdapter());
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.addOnPageChangeListener(this);

        v.findViewById(R.id.btn_search).setOnClickListener(this);
        v.findViewById(R.id.btn_msg).setOnClickListener(this);

        return v;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
         mNavigationBar.setIndexAt(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }



    @Override
    public void onTabSelected(int index) {
        mViewPager.setCurrentItem(index);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != mViewPager)mViewPager.removeOnPageChangeListener(this );
    }

    @Override
    public void onClick(View v) {

    }

    private class FeedAdapter extends PagerAdapter {


        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(MATCH_PARENT,MATCH_PARENT);
            FeedListView listView = new FeedListView(getContext());
            container.addView(listView,lp);
            return listView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }
    }


}