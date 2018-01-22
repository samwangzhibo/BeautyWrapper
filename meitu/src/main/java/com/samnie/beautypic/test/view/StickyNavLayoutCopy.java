package com.samnie.beautypic.test.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import com.samnie.beautypic.R;


public class StickyNavLayoutCopy extends RelativeLayout {

    private final Scroller mScroller;
    private View mTop;
    private View mNav;
    private View mViewPager;

    private int mTopViewHeight;
    //只要不完全隐藏的都是false 改为3中状态 0==完全隐藏,1=完全显示,2=隐藏一半
    private int topStatus = 1;
    private int mTouchSlop;
    private float mLastY;
    private boolean parentInterceptTouch;
    private boolean childInterceptTouch;
    //0 下拉,1 上划
    private int moveStatus = -1;
    private int heightOffset = 0;
    OnScrollChanged listener;
    private boolean scrollOver;

    public interface OnScrollChanged {
        void onScrollChanged(int l, int t, int oldl, int oldt);
    }

    public void setOnScrollChanged(OnScrollChanged l) {
        listener = l;
    }

    public StickyNavLayoutCopy(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public int getTopHeight() {
        return mTopViewHeight;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (listener != null) {
            listener.onScrollChanged(l, t, oldl, oldt);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTop = findViewById(R.id.id_stickynavlayout_topview);
        mTop.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mTop.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
        mNav = findViewById(R.id.tabs);
        View view = findViewById(R.id.pager);
        mViewPager = view;
    }

    @Override
    public void computeScroll() {
//        Log.e("houxiukai", "computeScroll");
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
//            Log.e("houxiukai", "进来了刷新");
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
            scrollOver = true;
//            Log.e("houxiukai", "scrollOver87:" + scrollOver);
//            Log.e("houxiukai", "computeScroll:" + "scrollOver" + true);
        } else {
            scrollOver = false;
        }
        if (topStatus == 0 || topStatus == 1) {
            scrollOver = true;
        }
    }

    //	@Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mTopViewHeight = mTop.getMeasuredHeight() + mTop.getTop() + heightOffset;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec + mTopViewHeight);
        ViewGroup.LayoutParams params = mViewPager.getLayoutParams();
        params.height = getMeasuredHeight() - mNav.getMeasuredHeight() - mTopViewHeight;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        float y = ev.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
//                Log.e("houxiukai", "dispatchTouchEvent-ACTION_DOWN");
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                float dy = y - mLastY;
                getCurrentScrollView();
                /**
                 * 1.手指滑动时:检测下拉or上拉,
                 * if 下拉:
                 *     检测top是否显示or隐藏
                 *              if隐藏:分发事件,父view拦截 ,显示top
                 *              if显示:分发事件,父view不拦截
                 * if 上拉:
                 *      检测top是否显示or隐藏
                 *              if隐藏:分发事件,父view不拦截 ,
                 *              if显示:分发事件,父view拦截,隐藏top
                 */
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    public void reset() {
        topStatus = 1;
        mScroller.startScroll(getScrollX(), getScrollY(), 0, -getScrollY(), 300);
    }

    /**
     *
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();
        float y = ev.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
//                Log.e("houxiukai", "onInterceptTouchEvent-DOWN");
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
//                Log.e("houxiukai", "onInterceptTouchEvent-move");
                float dy = y - mLastY;
                getCurrentScrollView();
                if (Math.abs(dy) > mTouchSlop) {
                    //下拉
                    if (dy > 0) {
                        //顶部完全隐藏时
                        if (topStatus == 0) {
                            parentInterceptTouch = true;
                            childInterceptTouch = false;

//                            Log.e("houxiukai", "onInterceptTouchEvent:" + "下拉_顶部完全隐藏时" + "拦截");
//                            Log.e("houxiukai", "parentInterceptTouch:" + parentInterceptTouch + ":childInterceptTouch:" + parentInterceptTouch +":"+"拦截");
                            MotionEvent ev2 = MotionEvent.obtain(ev);
                            ev2.setAction(MotionEvent.ACTION_CANCEL);
                            mViewPager.dispatchTouchEvent(ev2);
//                            return dispatchTouchEvent(ev2);
                            return true;
                        }
                        //顶部完全显示
                        if (topStatus == 1) {
                            parentInterceptTouch = false;
                            childInterceptTouch = true;
//                            Log.e("houxiukai", "onInterceptTouchEvent:" + "下拉_顶部完全显示" + "不拦截");
//                            Log.e("houxiukai", "parentInterceptTouch:" + parentInterceptTouch + ":childInterceptTouch:" + parentInterceptTouch +":"+"不拦截");
                            return false;
                        }
                        if (topStatus == 2) {
                            parentInterceptTouch = true;
                            childInterceptTouch = false;
//                            Log.e("houxiukai", "onInterceptTouchEvent:" + "下拉_顶部显示一半" + "拦截");
//                            Log.e("houxiukai", "parentInterceptTouch:" + parentInterceptTouch + ":childInterceptTouch:" + parentInterceptTouch + ":" + "拦截");
                            MotionEvent ev2 = MotionEvent.obtain(ev);
                            ev2.setAction(MotionEvent.ACTION_CANCEL);
                            mViewPager.dispatchTouchEvent(ev2);
                            return true;
                        }

                    }
                    //上划
                    if (dy < 0) {
                        //顶部完全隐藏时
                        if (topStatus == 0) {
                            parentInterceptTouch = false;
                            childInterceptTouch = true;
//                            Log.e("houxiukai", "onInterceptTouchEvent:" + "上划_顶部完全隐藏时" + "不拦截");
//                            Log.e("houxiukai", "parentInterceptTouch:" + parentInterceptTouch + ":childInterceptTouch:" + parentInterceptTouch +":"+"不拦截");
                            return false;
                        }
                        //顶部完全显示
                        if (topStatus == 1) {
                            parentInterceptTouch = true;
                            childInterceptTouch = false;
//                            Log.e("houxiukai", "onInterceptTouchEvent:" + "上划_顶部完全显示" + "拦截");
//                            Log.e("houxiukai", "parentInterceptTouch:" + parentInterceptTouch + ":childInterceptTouch:" + parentInterceptTouch + ":" + "拦截");
                            MotionEvent ev2 = MotionEvent.obtain(ev);
                            ev2.setAction(MotionEvent.ACTION_CANCEL);
                            mViewPager.dispatchTouchEvent(ev2);
                            return true;
                        }
                        if (topStatus == 2) {
                            parentInterceptTouch = true;
                            childInterceptTouch = false;
//                            Log.e("houxiukai", "onInterceptTouchEvent" + "上划_顶部显示一半" + "拦截");
//                            Log.e("houxiukai", "parentInterceptTouch:" + parentInterceptTouch + ":childInterceptTouch:" + parentInterceptTouch + ":" + "拦截");
                            MotionEvent ev2 = MotionEvent.obtain(ev);
                            ev2.setAction(MotionEvent.ACTION_CANCEL);
                            mViewPager.dispatchTouchEvent(ev2);
                            return true;
                        }
                    }
                }
                /**
                 * 1.手指滑动时:检测下拉or上拉,
                 * if 下拉:
                 *     检测top是否显示or隐藏
                 *              if隐藏:分发事件,父view拦截 ,显示top
                 *              if显示:分发事件,父view不拦截
                 * if 上拉:
                 *      检测top是否显示or隐藏
                 *              if隐藏:分发事件,父view不拦截 ,
                 *              if显示:分发事件,父view拦截,隐藏top
                 */

//                if (Math.abs(dy) > mTouchSlop) {
////                    if (parentInterceptTouch) {
////                       return true;
////                    }
////                    if (childInterceptTouch) {
////                        return false;
////                    }
////                    mDragging = true;
//
//                    }
//                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (!scrollOver) {
//                    Log.e("houxiukai", "onInterceptTouchEvent-MotionEvent.ACTION_UP" + "拦截");
//                    scrollOver=true;
                    return true;
                } else {
//                    Log.e("houxiukai", "onInterceptTouchEvent-MotionEvent.ACTION_UP" + "else");
                    return false;
                }
//                mDragging = false;
//                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    private void getCurrentScrollView() {
//        int currentItem = mViewPager.getCurrentItem();
//        PagerAdapter a = mViewPager.getAdapter();
//        if (a instanceof FragmentPagerAdapter) {
//            FragmentPagerAdapter fadapter = (FragmentPagerAdapter) a;
//            Fragment item = (Fragment) fadapter.instantiateItem(mViewPager,
//                    currentItem);
//            listview = (ViewGroup) (item.getView()
//                    .findViewById(R.id.live_fragment_list));
//        } else if (a instanceof FragmentStatePagerAdapter) {
//            FragmentStatePagerAdapter fsAdapter = (FragmentStatePagerAdapter) a;
//            Fragment item = (Fragment) fsAdapter.instantiateItem(mViewPager,
//                    currentItem);
//            listview = (ViewGroup) (item.getView()
//                    .findViewById(R.id.live_fragment_list));
//        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float y = event.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
//                Log.e("houxiukai", "onTouchEvent-ACTION_DOWN");
                mLastY = y;
                return true;
            case MotionEvent.ACTION_MOVE:
//                Log.e("houxiukai", "onTouchEvent-ACTION_MOVE");
                float dy = y - mLastY;
                scrollOver = false;
//                Log.e("houxiukai", "scrollOver325:" + scrollOver);
//                Log.e("TAG", "dy = " + dy + " , y = " + y + " , mLastY = " + mLastY);
//                if (!isTopHidden && dy>0) {
//                    event.setAction(MotionEvent.ACTION_CANCEL);
//                    dispatchTouchEvent(event);
//                    return true;
//                }
//                if (!mDragging && Math.abs(dy) > mTouchSlop) {
//                    mDragging = true;
//                }
//                if (mDragging) {
                if (dy > 0) {
                    moveStatus = 0;
                }
                if (dy < 0) {
                    moveStatus = 1;
                }
                scrollBy(0, (int) -dy);
                //

                //顶部完全隐藏,上划
                if (topStatus == 0 && moveStatus == 1) {
//                    Log.e("houxiukai", "顶部完全隐藏,上划,分发事件" + topStatus);
                    event.setAction(MotionEvent.ACTION_DOWN);
                    dispatchTouchEvent(event);
                }
                //顶部完全显示,下拉
                if (topStatus == 1 && moveStatus == 0) {
//                    Log.e("houxiukai", "顶部完全显示,下拉,分发事件" + topStatus);
                    event.setAction(MotionEvent.ACTION_DOWN);
                    dispatchTouchEvent(event);

                }
//                    if (getScrollY() == mTopViewHeight && dy < 0) {
//                        event.setAction(MotionEvent.ACTION_DOWN);
//                        Log.e("houxiukai", "Log.e(\"houxiukai\", \"onTouchEvent-ACTION_DOWN\");");
//                        dispatchTouchEvent(event);
//                        event.setAction(MotionEvent.ACTION_CANCEL);
//                        dispatchTouchEvent(event);
//                        isInControl = false;
//                    }
//                }
                mLastY = y;
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            case MotionEvent.ACTION_UP:
                float dy1 = y - mLastY;
                //下拉
                if (moveStatus == 0) {
//                    Log.e("houxiukai", "getScrollX():" + getScrollX() + ":" + getScrollY() + ":" + 0 + ":" + mTopViewHeight + ":");
                    mScroller.startScroll(getScrollX(), getScrollY(), 0, -getScrollY(), 300);
//                         scrollTo(0, 0);
                }
                //上划
                if (moveStatus == 1) {
//                    Log.e("houxiukai", "getScrollX():" + getScrollX() + ":" + getScrollY() + ":" + 0 + ":" + mTopViewHeight + ":");
                    mScroller.startScroll(getScrollX(), getScrollY(), 0, mTopViewHeight, 300);
//                    scrollTo(0, mTopViewHeight);
                }
                invalidate();
                moveStatus = -1;
                break;
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void scrollTo(int x, int y) {
        if (y <= 10) {
            y = 0;
        }
        if (y >= mTopViewHeight) {
            y = mTopViewHeight;
        }
        if (y != getScrollY()) {
            super.scrollTo(x, y);
        }
        if (getScrollY() == mTopViewHeight) {
//            scrollOver=true;
//            Log.e("houxiukai", "scrollOver406:" + scrollOver);
            topStatus = 0;
        }
        if (getScrollY() == 0) {
//            scrollOver=true;
//            Log.e("houxiukai", "scrollOver411:" + scrollOver);
            topStatus = 1;
        }
        if (getScrollY() > 0 && getScrollY() < mTopViewHeight) {
            scrollOver = false;
//            Log.e("houxiukai", "scrollOver416:" + scrollOver);
            topStatus = 2;
        }
    }

    public void setHeightOffset(int heightOffset) {
        this.heightOffset = heightOffset;
    }
}
