package com.example.a83776.demo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.a83776.demo.R;
import com.example.a83776.demo.base.BaseFragment;
import com.example.a83776.demo.contract.LiveCourseContract;
import com.example.a83776.demo.model.bean.Live;
import com.example.a83776.demo.presenter.LiveCoursePresenter;
import com.example.a83776.demo.ui.adapter.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * description:
 * author: GaoJie
 * created at: 2018/6/25 15:16
 */
public class LivesFragment extends BaseFragment<LiveCoursePresenter> implements LiveCourseContract.View {
    @BindView(R.id.inner_viewpager)
    ViewPager mInnerViewpager;
    @BindView(R.id.content)
    RelativeLayout mContent;
    private List<Live> mLives;
    private ArrayList<Fragment> mFragments;
    private static final int PAGE_SIZE = 6;//每页显示的数量
    private FragmentPagerAdapter mAdapter;

    public static LivesFragment newInstance() {
        LivesFragment fragment = new LivesFragment();
        return fragment;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_lives;
    }

    @Override
    protected void initEventAndData() {
    }

    public LivesFragment() {
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLives = new ArrayList<>();
        mFragments = new ArrayList<>();
        mAdapter = new FragmentPagerAdapter(getChildFragmentManager(), mFragments);
        mInnerViewpager.setAdapter(mAdapter);
        mInnerViewpager.setOffscreenPageLimit(4);
        mPresenter.liveCourse(0, "HD", null, 0);
    }

    @Override
    public void onLiveCourseResult(int code, String message, List<Live> lives) {
        if (code == 1) {
            FragmentManager fm = getChildFragmentManager();
            if (lives != null && !lives.isEmpty()) {
                ArrayList<Live> subChannels = null;
                int index = mFragments.size();
                for (int i = 0; i < lives.size(); i++) {
                    if (i % PAGE_SIZE == 0) {
                        subChannels = new ArrayList<>();
                    }
                    if (subChannels != null) {
                        subChannels.add(lives.get(i));
                    }
                    if (i % PAGE_SIZE == PAGE_SIZE - 1 || i == lives.size() - 1) {
                        //ToDo 先查找是否有缓存，有缓存则使用缓存并更新界面，没有则创建
                        Fragment fragment = fm.findFragmentByTag(makeFragmentName(mInnerViewpager.getId(), index++));
                        if (fragment == null) {
                            fragment = LiveFragment2.newInstance(subChannels);
                        } else {
                            ((LiveFragment2) fragment).update(subChannels);
                        }
                        mFragments.add(fragment);
                    }
                }
                mLives.addAll(lives);
                mAdapter.notifyDataSetChanged();
            } else {
            }
        }
    }

    private static String makeFragmentName(int viewId, long id) {
        return "android:switcher:" + viewId + ":" + id;
    }
}
