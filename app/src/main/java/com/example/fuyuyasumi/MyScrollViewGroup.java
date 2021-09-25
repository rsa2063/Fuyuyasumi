package com.example.fuyuyasumi;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Scroller;

/**
 * Created by zhangbin on 2016/2/16.
 */
public class MyScrollViewGroup extends ViewGroup {
    private int mScreenHeight;
    private Scroller scroller;
    int lastY = 0, mStart, mEnd;

    public MyScrollViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);
        mScreenHeight = dm.heightPixels;
        scroller = new Scroller(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        MarginLayoutParams params = (MarginLayoutParams) getLayoutParams();
        params.height = mScreenHeight * count;
        setLayoutParams(params);
        for (int i = 0; i < count; i++) {
            View childView = getChildAt(i);
            if (childView.getVisibility() != GONE) {
                childView.layout(l, i * mScreenHeight, r, (i + 1) * mScreenHeight);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastY = y;
                //mStart = y;
                mStart = getScrollY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (!scroller.isFinished()) {
                    scroller.abortAnimation();
                }
                int dy = lastY - y;
                if(getScrollY() < 0) {
                    dy = 0;
                    //dy = - getScrollY();
                    //scrollBy(0, - getScrollY());
                }
                if (getScrollY() > getHeight() - mScreenHeight)  {
                    dy = 0;
                    //dy =  (getHeight() - mScreenHeight) - getScrollY();
                }
                scrollBy(0, dy);
                lastY = y;
                break;
            case MotionEvent.ACTION_UP:
                //mEnd = y;
                mEnd = getScrollY();
                int mOffset = mStart - mEnd;
                if (mOffset < 0) {
                    if (-mOffset < mScreenHeight / 3) {
                        scroller.startScroll(0,getScrollY(), 0, mOffset);
                    } else {
                        scroller.startScroll(0, getScrollY(), 0, (mScreenHeight + mOffset));
                    }
                } else {
                    if (mOffset < mScreenHeight / 3) {
                        scroller.startScroll(0,getScrollY(), 0, mOffset);
                    } else {
                        scroller.startScroll(0, getScrollY(), 0, -(mScreenHeight - mOffset));
                    }
                }
                /*int dScrollY = mEnd - mStart;
                if (dScrollY > 0) {
                    if (dScrollY < mScreenHeight / 3) {
                        scroller.startScroll(
                                0, getScrollY(),
                                0, -dScrollY);
                    } else {
                        scroller.startScroll(
                                0, getScrollY(),
                                0, mScreenHeight - dScrollY);
                    }
                } else {
                    if (-dScrollY < mScreenHeight / 3) {
                        scroller.startScroll(
                                0, getScrollY(),
                                0, -dScrollY);
                    } else {
                        scroller.startScroll(
                                0, getScrollY(),
                                0, -mScreenHeight - dScrollY);
                    }
                }*/
                break;
        }
        postInvalidate();
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {
            scrollTo(0, scroller.getCurrY());
            postInvalidate();
        }

    }
}
