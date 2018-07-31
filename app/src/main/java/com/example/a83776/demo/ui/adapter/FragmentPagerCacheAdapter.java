package com.example.a83776.demo.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

/**
 * description:
 * author: GaoJie
 * created at: 2018/6/27 9:50
 */
public class FragmentPagerCacheAdapter extends FragmentStateRemovePagerAdapter {
    public static class FragmentIdCache {
        Fragment mFragment;
        long itemIId;

        public FragmentIdCache(Fragment fragment, long itemIId) {
            mFragment = fragment;
            this.itemIId = itemIId;
        }
    }

    private List<FragmentIdCache> mFragmentIdCaches = new ArrayList<>();

    public FragmentPagerCacheAdapter(FragmentManager fm) {
        super(fm);
    }

    public FragmentPagerCacheAdapter(FragmentManager fm, ArrayList<FragmentIdCache> fragmentIdCaches) {
        super(fm);
        this.mFragmentIdCaches = fragmentIdCaches;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentIdCaches.get(position).mFragment;
    }

    @Override
    public int getCount() {
        if (mFragmentIdCaches != null && mFragmentIdCaches.size() > 0) {
            return mFragmentIdCaches.size();
        }
        return 0;
    }

    //自定义Id，不使用缓存Fragment
    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public void addNext(FragmentIdCache fragmentIdCache) {
        mFragmentIdCaches.add(fragmentIdCache);
        notifyDataSetChanged();
    }

    public void addNext(List<FragmentIdCache> fragmentIdCaches) {
        mFragmentIdCaches.addAll(fragmentIdCaches);
        notifyDataSetChanged();
    }
}
