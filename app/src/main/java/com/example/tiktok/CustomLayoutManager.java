package com.example.tiktok;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

public class CustomLayoutManager extends LinearLayoutManager implements RecyclerView.OnChildAttachStateChangeListener {
    private int mDrift;//位移，用来判断移动方向

    private PagerSnapHelper mPagerSnapHelper;
    private OnPageSlideListener mOnPageSlideListener;

    public CustomLayoutManager(Context context) {
        super(context);
    }

    public CustomLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
        //Log.d("videos","CustomLayoutManager1");
        mPagerSnapHelper = new PagerSnapHelper();
    }

    @Override
    public void onAttachedToWindow(RecyclerView view) {
       // Log.d("videos","CustomLayoutManager2");

        view.addOnChildAttachStateChangeListener(this);
        mPagerSnapHelper.attachToRecyclerView(view);
        super.onAttachedToWindow(view);
    }

    //Item添加进来
    @Override
    public void onChildViewAttachedToWindow(@NonNull View view) {
        //Log.d("videos","CustomLayoutManager3");
        //播放视频操作，判断将要播放的是上一个视频，还是下一个视频
        if (mDrift > 0) { //向上
            if (mOnPageSlideListener != null)
                mOnPageSlideListener.onPageSelected(getPosition(view), true);
        } else { //向下
            if (mOnPageSlideListener != null)
                mOnPageSlideListener.onPageSelected(getPosition(view), false);
        }
    }

    //Item移除出去
    @Override
    public void onChildViewDetachedFromWindow(@NonNull View view) {
       // Log.d("videos","CustomLayoutManager4");
        //暂停播放操作
        if (mDrift >= 0) {
            if (mOnPageSlideListener != null)
                mOnPageSlideListener.onPageRelease(true, getPosition(view));
        } else {
            if (mOnPageSlideListener != null)
                mOnPageSlideListener.onPageRelease(false, getPosition(view));
        }
    }

    @Override
    public void onScrollStateChanged(int state) { //滑动状态监听
        switch (state) {
            case RecyclerView.SCROLL_STATE_IDLE:
              //  Log.d("videos","CustomLayoutManager5");
                View view = mPagerSnapHelper.findSnapView(this);
                int position = getPosition(view);
                if (mOnPageSlideListener != null) {
                    mOnPageSlideListener.onPageSelected(position, position == getItemCount() - 1);
                }
                break;
        }
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        this.mDrift = dy;
       // Log.d("videos","CustomLayoutManager6");
        return super.scrollVerticallyBy(dy, recycler, state);
    }

    //接口注入
    public void setOnPageSlideListener(OnPageSlideListener mOnViewPagerListener) {
        Log.d("videos","CustomLayoutManager7");
        this.mOnPageSlideListener = mOnViewPagerListener;
    }
}