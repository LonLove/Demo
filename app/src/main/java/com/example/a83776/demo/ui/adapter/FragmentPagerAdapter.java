package com.example.a83776.demo.ui.adapter;

import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

/**
 * description:
 * author: GaoJie
 * created at: 2018/6/27 9:32
 */
public class FragmentPagerAdapter extends FragmentStateRemovePagerAdapter {
    private List<Fragment> mFragments;
    private List<String> mFragmentTitles = new ArrayList<>();
    private List<Integer> mFragmentIcons = new ArrayList<>();

    public FragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        this.mFragments = new ArrayList<>();
    }

    public FragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        if (mFragments != null && mFragments.size() > 0) {
            return mFragments.size();
        }
        return 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitles.get(position);
    }

    public Integer getPageIcon(int position) {
        return mFragmentIcons.get(position);
    }

    public void addFragment(Fragment fragment, String title) {
        mFragments.add(fragment);
        mFragmentTitles.add(title);
    }

    public void addFragment(Fragment fragment, @DrawableRes int resId, String title) {
        mFragments.add(fragment);
        mFragmentTitles.add(title);
        mFragmentIcons.add(resId);
    }

    public void addFragment(Fragment fragment) {
        mFragments.add(fragment);
        notifyDataSetChanged();
    }

    public void addFragment(List<Fragment> fragments) {
        mFragments.addAll(fragments);
        notifyDataSetChanged();
    }
}
