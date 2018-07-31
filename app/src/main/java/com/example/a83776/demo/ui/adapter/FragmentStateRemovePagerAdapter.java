package com.example.a83776.demo.ui.adapter;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * description:
 * author: GaoJie
 * created at: 2018/6/25 16:37
 */
public abstract class FragmentStateRemovePagerAdapter extends FragmentStatePagerAdapter {
    private FragmentTransaction mFragmentTransaction = null;
    private FragmentManager mFragmentManager;
    private Fragment mCurrentPrimaryItem = null;
    private ArrayList<Fragment.SavedState> mSavedStates = new ArrayList<>();
    private int mViewGroup;

    public FragmentStateRemovePagerAdapter(FragmentManager fm) {
        super(fm);
        mFragmentManager = fm;
    }

    @Override
    public abstract Fragment getItem(int position);


    @Override
    public void startUpdate(ViewGroup container) {
        mViewGroup = container.getId();
        if (mViewGroup == View.NO_ID) {
            throw new IllegalStateException("ViewPager with adapter " + this
                    + " requires a view id");
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (mFragmentTransaction == null) {
            mFragmentTransaction = mFragmentManager.beginTransaction();
        }
        long itemId = getItemId(position);
        //Do we already have  this fragment?
        String name = makeFragmentName(container.getId(), itemId);
        Fragment fragment = mFragmentManager.findFragmentByTag(name);
        if (fragment != null) {
            mFragmentTransaction.attach(fragment);
        } else {
            fragment = getItem(position);
            if (mSavedStates.size() > position) {
                Fragment.SavedState fss = mSavedStates.get(position);
                if (fss != null) {
                    fragment.setInitialSavedState(fss);
                }
            }
            mFragmentTransaction.add(container.getId(), fragment, makeFragmentName(container.getId(), itemId));
        }
        if (fragment != mCurrentPrimaryItem) {
            fragment.setMenuVisibility(false);
            fragment.setUserVisibleHint(false);
        }
        return fragment;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        Fragment fragment = (Fragment) object;
        if (fragment != mCurrentPrimaryItem) {
            if (mCurrentPrimaryItem != null) {
                mCurrentPrimaryItem.setMenuVisibility(false);
                mCurrentPrimaryItem.setUserVisibleHint(false);
            }
            if (fragment != null) {
                fragment.setMenuVisibility(true);
                fragment.setUserVisibleHint(true);
            }
            mCurrentPrimaryItem = fragment;
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (mFragmentTransaction == null) {
            mFragmentTransaction = mFragmentManager.beginTransaction();
        }
        mFragmentTransaction.detach(((Fragment) object));
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((Fragment) object).getView();
    }

    /**
     * return a unique identifier for the item at the given position
     * @param position
     * @return
     */
    public long getItemId(int position) {
        return position;
    }

    private static String makeFragmentName(int viewId, long id) {
        return "android:switcher:" + viewId + ":" + id;
    }


    @Override
    public void finishUpdate(ViewGroup container) {
        if (mFragmentTransaction != null) {
            mFragmentTransaction.commitNowAllowingStateLoss();
            mFragmentTransaction = null;
        }
    }

    @Override
    public Parcelable saveState() {
        Bundle state = null;
        if (mSavedStates.size() > 0) {
            state = new Bundle();
            Fragment.SavedState[] fss = new Fragment.SavedState[mSavedStates.size()];
            mSavedStates.toArray(fss);
            state.putParcelableArray("states", fss);
        }
        int count = getCount();
        for (int position = 0; position < getCount(); position++) {
            long itemId = getItemId(position);
            String name = makeFragmentName(mViewGroup, itemId);
            Fragment fragment = mFragmentManager.findFragmentByTag(name);
            if (fragment!= null && fragment.isAdded()) {
                if (state == null) {
                    state = new Bundle();
                }
                String key = "f" + itemId;
                mFragmentManager.putFragment(state, key, fragment);
            }
        }
        return state;
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
        if (state != null) {
            Bundle bundle = (Bundle)state;
            bundle.setClassLoader(loader);
            Parcelable[] fss = bundle.getParcelableArray("states");
            mSavedStates.clear();
            if (fss != null) {
                for (int i=0; i<fss.length; i++) {
                    mSavedStates.add((Fragment.SavedState)fss[i]);
                }
            }
            Iterable<String> keys = bundle.keySet();
            for (String key: keys) {
                if (key.startsWith("f")) {
                    Fragment f = mFragmentManager.getFragment(bundle, key);
                    if (f != null) {
                        f.setMenuVisibility(false);
                    } else {
                    }
                }
            }
        }
    }
    public  void removeAll() {
        if (mFragmentTransaction == null) {
          mFragmentTransaction= mFragmentManager.beginTransaction();
        }
        int count = getCount();
        for (int position = 0; position < count; position++) {
            long itemId = getItemId(position);
            String name = makeFragmentName(mViewGroup, itemId);
            Fragment fragment = mFragmentManager.findFragmentByTag(name);
            if (fragment == null) {
                continue;
            }
            while (mSavedStates.size() <=getCount()) {
                mSavedStates.add(null);
            }
            mSavedStates.set(position, fragment.isAdded() ? mFragmentManager.saveFragmentInstanceState(fragment) : null);
            mFragmentTransaction.remove(fragment);
        }
        mFragmentTransaction.commitAllowingStateLoss();
        mFragmentTransaction = null;
    }
}
