/*
 * Copyright (C) 2018 The Jerry xu Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.incoder.mvc;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import org.incoder.mvc.adapter.BaseFragmentPagerAdapter;
import org.incoder.mvc.base.BaseActivity;
import org.incoder.mvc.view.HomeFragment;
import org.incoder.mvc.view.MessageFragment;
import org.incoder.mvc.view.MineFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * MainActivity.
 *
 * @author : Jerry xu
 * @since : 2018/12/3 21:31
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.navigation)
    BottomNavigationView mNavigationView;
    @BindView(R.id.vp_content)
    ViewPager mViewPager;

    private List<Fragment> mFragments;
    private List<String> mTitles;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mFragments.get(0);
                    return true;
                case R.id.navigation_dashboard:
                    mFragments.get(1);
                    return true;
                case R.id.navigation_notifications:
                    mFragments.get(2);
                    return true;
                default:
                    break;
            }
            return false;
        }
    };

    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            mViewPager.setCurrentItem(i);
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        setSupportActionBar(mToolbar);
        initTitles();
        initFragment();
    }

    private void initTitles() {
        mTitles = new ArrayList<>();
        mTitles.add("首页");
        mTitles.add("消息");
        mTitles.add("我的");
    }

    private void initFragment() {
        mFragments = new ArrayList<>();
        mFragments.add(new HomeFragment());
        mFragments.add(new MessageFragment());
        mFragments.add(new MineFragment());

        mViewPager.setAdapter(new BaseFragmentPagerAdapter(getSupportFragmentManager(), mFragments, mTitles));
        mViewPager.setOffscreenPageLimit(mFragments.size());
        mViewPager.addOnPageChangeListener(mOnPageChangeListener);
        mNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public boolean isNeedEventBus() {
        return false;
    }
}
