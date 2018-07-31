package com.example.a83776.demo.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.a83776.demo.R;
import com.example.a83776.demo.model.bean.Live;
import com.example.a83776.demo.ui.activity.LiveDetailActivity;
import com.example.a83776.demo.ui.adapter.LiveAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * description:
 * author: GaoJie
 * created at: 2018/6/27 15:53
 */
public class LiveFragment2 extends SupportFragment {
    private static final String ARG_LIVES = "arg_lives";
    @BindView(R.id.recycleView)
    RecyclerView mRecycleView;
    ArrayList<Live> msubLives = new ArrayList<>();
    private LiveAdapter mAdapter;

    public static LiveFragment2 newInstance(ArrayList<Live> subLives) {
        LiveFragment2 fragment2 = new LiveFragment2();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(ARG_LIVES, subLives);
        fragment2.setArguments(bundle);
        return fragment2;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           msubLives= getArguments().getParcelableArrayList(ARG_LIVES);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_live2, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        mRecycleView.setLayoutManager(layoutManager);
        bindData();
    }

    private void bindData() {
        mAdapter = new LiveAdapter(this,msubLives);
        mRecycleView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(mOnItemClickListener);
    }
    private AdapterView.OnItemClickListener mOnItemClickListener=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Live live = msubLives.get(position);
            if (live != null) {
                startActivity(new Intent(getActivity(),LiveDetailActivity.class)
                .putExtra(LiveDetailActivity.ARG_COURSE_ID,live.getClassId())
                .putExtra(LiveDetailActivity.ARG_ERP_CLASS_ID,live.getErpClassId()));
            }
        }
    };

    public void update(ArrayList<Live> subChannels) {
        if (msubLives != null) {
            if (msubLives .size()>0) {
                msubLives.clear();
            }
            msubLives.addAll(subChannels);
        }
        mAdapter.notifyDataSetChanged();
    }
}
