package com.example.a83776.demo.ui.adapter;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a83776.demo.R;
import com.example.a83776.demo.component.ImageLoader;
import com.example.a83776.demo.model.bean.Live;
import com.example.a83776.demo.model.bean.LiveTime;
import com.example.a83776.demo.util.DateTimeFormatUtil;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * description:
 * author: GaoJie
 * created at: 2018/6/27 16:07
 */
public class LiveAdapter extends RecyclerView.Adapter<LiveAdapter.VHolder> {

    Drawable greenbackground;
    private List<Live> comments;
    private Fragment mFragment;
    private int heigth;
    public LiveAdapter( Fragment fragment,List<Live> comments) {
        this.comments = comments;
        this.mFragment = fragment;
        greenbackground = fragment.getResources().getDrawable(R.drawable.ic_trapezoidal);
        greenbackground.setColorFilter(Color.parseColor("#5CC147"), PorterDuff.Mode.SRC_ATOP);
    }

    @Override

    public VHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_live, parent, false);
        heigth = (parent.getHeight() - 3 * 30) / 2;
        return new VHolder(view);
    }

    @Override
    public void onBindViewHolder(VHolder holder, final int position) {
        View itemView = holder.itemView;
        RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) itemView.getLayoutParams();
        lp.height = heigth;
        itemView.setLayoutParams(lp);
        Live live = comments.get(position);
        if (live.getLiveType() == 1) {//直播状态 0：未直播，1：正在直播
            holder.mLlState.setBackgroundDrawable(greenbackground);
            holder.mLlState.setVisibility(View.VISIBLE);
        } else {
            holder.mLlState.setVisibility(View.GONE);
        }
        ImageLoader.load(mFragment,live.getPicture(),holder.mImage,R.drawable.icon_h_default);
        String text = live.getClassName() + "--" + live.getMarketCourseName();
        holder.mTitle.setText(text);
        text = getTimeStr(live);
        holder.mTime.setText(text);
        if (live.getBaseBuyerNum() > 10000) {
            text = "1万+人购买";
        } else {
            text = live.getBaseBuyerNum() + "人购买";
        }
        holder.mCount.setText(text);
        if (position == 3 || position == 4 || position == 5) {
            holder.itemView.setTag(R.integer.tag_view_position);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(null,v,position,getItemId(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (comments != null&&comments.size()>0) {
            return comments.size();
        }
        return 0;
    }

    /**
     * // 直播页面的展示规则<br>
     * //1.正在直播的优先显示<br>
     * //2.当课程（正在直播中和没有正在直播）未结束时，展示最近课次的时间（以当前时间为准，之后未上课的最近课程）<br>
     * //3.当课程已结束，显示课程段时间;都是已结束的课程，按开播时间降序排列<br>
     * //4.不做登陆和未登陆的区分<br>
     *
     * @param live
     * @return
     */
    public String getTimeStr(Live live) {
        if (live.getTimeList() == null || live.getTimeList().isEmpty()) {
            return "";
        }
        //先将时间升序排列
        Collections.sort(live.getTimeList(), new Comparator<LiveTime>() {
            @Override
            public int compare(LiveTime o1, LiveTime o2) {
                return o1.getStartTimeStr().compareTo(o2.getStartTimeStr());
            }
        });
        List<LiveTime> times = live.getTimeList();
        Date date1 = null;
        Date date2 = null;
        long duration = 0;
        if (live.getLiveType() == 1) {//直播状态 0：未在直播，1：正在直播
            LiveTime lastestTime = times.get(0);
            for (int i = 0; i < times.size(); i++) {
                if (times.get(i).getZhiBoZhuangTai() == 1) {
                    duration = DateTimeFormatUtil.timeBetween(times.get(i).getStartTimeStr(), times.get(i).getEndTimeStr(), DateTimeFormatUtil.YYYY_MM_DD_HH_MM_SS);
                    lastestTime = times.get(i);
                    break;
                }
            }
            return "最近课程：今天" + lastestTime.getTime() + "" + duration + "分钟";
        } else {
            date2 = DateTimeFormatUtil.string2date(DateTimeFormatUtil.getNowYearMonthDay(), DateTimeFormatUtil.YYYY_MM_DD_HH_MM_SS);
            if (live.getClassScheduleType() == 1) {//有未上完的课
                int count = 0;
                for (int i = 0; i < times.size(); i++) {
                    count = i;
                    date1 = DateTimeFormatUtil.string2date(times.get(i).getEndTimeStr(), DateTimeFormatUtil.YYYY_MM_DD_HH_MM_SS);
                    if (!date1.before(date2)) {
                        duration = DateTimeFormatUtil.timeBetween(times.get(i).getStartTimeStr(), times.get(i).getEndTimeStr(), DateTimeFormatUtil.YYYY_MM_DD_HH_MM_SS);
                        break;
                    }
                }
                return "最近课程：" + times.get(count).getStartTime() + "" + times.get(count).getTime() + "" + duration + "分钟";
            } else if (live.getClassScheduleType() == 0) {//全部已经上完
                LiveTime first = times.get(0);
                LiveTime last = times.get(times.size() - 1);
                return first.getStartTime() + "-" + last.getStartTime() + "" + times.size() + "节课";
            }
            return "";
        }
    }

    static class VHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        ImageView mImage;
        @BindView(R.id.tmp_image)
        ImageView mTmpImage;
        @BindView(R.id.state)
        TextView mState;
        @BindView(R.id.ll_state)
        LinearLayout mLlState;
        @BindView(R.id.title)
        TextView mTitle;
        @BindView(R.id.main)
        FrameLayout mMain;
        @BindView(R.id.time)
        TextView mTime;
        @BindView(R.id.count)
        TextView mCount;

        public VHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private AdapterView.OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
}
