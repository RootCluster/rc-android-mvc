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
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import org.incoder.mvc.adapter.BaseFragmentPagerAdapter;
import org.incoder.mvc.view.HomeFragment;
import org.incoder.mvc.view.MessageFragment;
import org.incoder.mvc.view.MineFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * MainActivity.
 *
 * @author : Jerry xu
 * @since : 2018/12/3 21:31
 */
public class MainActivity extends AppCompatActivity {

    private List<Fragment> mFragments;
    private List<String> mTitles;
    private ViewPager viewPager;
    private BottomNavigationView navigation;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.vp_content);
        navigation = findViewById(R.id.navigation);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
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

        viewPager.setAdapter(new BaseFragmentPagerAdapter(getSupportFragmentManager(), mFragments, mTitles));
        viewPager.setOffscreenPageLimit(mFragments.size());
        viewPager.addOnPageChangeListener(mOnPageChangeListener);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }


    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            viewPager.setCurrentItem(i);
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

}
