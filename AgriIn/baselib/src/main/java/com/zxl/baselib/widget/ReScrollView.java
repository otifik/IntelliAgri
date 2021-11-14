package com.zxl.baselib.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

import com.zxl.baselib.util.LoggerUtils;

import java.util.logging.Logger;

/**
 * @author zxl on 2018/8/27.
 *         discription: 有弹性的ScrollView 实现下拉弹回和上拉弹回
 *         这里大概是能够看懂的
 *         思路的话, 将原始的整个内容布局,拷贝一份到矩形中;
 *         手指按下时,判断是否可上下滑--
 *         手指移动时,通过手指滑动的距离,执行一个比例去将偏移值累加上原始宽高再做摆放;
 *         手指抬起时,通过当前距离与原始值做差使用动画进行还原为初始状态
 *
 */

public class ReScrollView extends ScrollView {
    private static final String TAG = "ElasticScrollView";

    /**
     *  移动因子 是一个百分比,比如手指移动了100px,那么View就移动50px
     *  目的是达到一个延迟的效果
     */
    private static final float MOVE_FACTOR = 0.5F;
    /**
     * 松开手指后, 界面回到正常位置需要的动画时间
     */
    private static final int ANIM_TIME = 300;
    /**
     *  scrollView的唯一子View
     */
    private View contentView;
    /**
     * 手指摁下的Y值 用于计算
     */
    private float startY;
    /**
     * 用于记录正常的布局位置
     */
    private Rect originalRect = new Rect();
    /**
     * 手指按下时记录是否可以继续下拉
     */
    private boolean canPullDown = false;
    /**
     * 手指按下时记录是否可以继续上拉
     */
    private boolean canPullUp = false;
    /**
     * 在手指滑动过程中记录是否移动了布局
     */
    private boolean isMoved = false;

    private ScrollViewListener mListener;


    public ReScrollView(Context context) {
        super(context);
    }

    public ReScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 初始化完成后即构造方法后调动该方法----
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (this.getChildCount() > 0) {
            this.contentView = this.getChildAt(0);
        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if(this.contentView != null) {
            //ScrollView中的唯一子控件的位置信息,
            // 这个位置信息在整个控件的生命周期中保持不变,直接设置 ---
            // 记录下原始的矩形的长宽高
            this.originalRect.set(this.contentView.getLeft(),
                    this.contentView.getTop(),
                    this.contentView.getRight(),
                    this.contentView.getBottom());
        }
    }

    /**
     * 在触摸事件中,处理上拉和下拉的逻辑-----
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(this.contentView == null) {
            return super.dispatchTouchEvent(ev);
        } else {
            int action = ev.getAction();

            switch(action) {
                case MotionEvent.ACTION_DOWN:
                    this.canPullDown = this.isCanPullDown();
                    this.canPullUp = this.isCanPullUp();
                    this.startY = ev.getY();
                    break;
                case MotionEvent.ACTION_UP:
                    //抬起的时候 返回
                    if(this.isMoved) {
                        TranslateAnimation anim = new TranslateAnimation(
                                0.0F,
                                0.0F,
                                (float)this.contentView.getTop(),
                                (float)this.originalRect.top);
                        anim.setDuration(ANIM_TIME);
                        this.contentView.startAnimation(anim);
                        //  返回到原始布局----
                        this.contentView.layout(this.originalRect.left,
                                this.originalRect.top,
                                this.originalRect.right,
                                this.originalRect.bottom);
                        this.canPullDown = false;
                        this.canPullUp = false;
                        this.isMoved = false;
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    if(!this.canPullDown && !this.canPullUp) {
                        this.startY = ev.getY();
                        this.canPullDown = this.isCanPullDown();
                        this.canPullUp = this.isCanPullUp();
                    } else {
                        float nowY = ev.getY();
                        // 偏移的距离
                        int deltaY = (int)(nowY - this.startY);
                        boolean shouldMove = this.canPullDown && deltaY > 0
                                || this.canPullUp && deltaY < 0
                                || this.canPullUp && this.canPullDown;
                        if(shouldMove) {
                            int offset = (int)((float)deltaY * MOVE_FACTOR);
                            this.contentView.layout(this.originalRect.left,
                                    this.originalRect.top + offset,
                                    this.originalRect.right,
                                    this.originalRect.bottom + offset);
                            this.isMoved = true;
                        }
                    }
                default:
                    break;
            }

            return super.dispatchTouchEvent(ev);
        }
    }

    /**
     * 判断是否滚到顶部
     * @return
     */
    private boolean isCanPullDown() {
        LoggerUtils.logE("scroll","contentView.getHeight = "+this.contentView.getHeight()
        + "    this.getHeight() = "+this.getHeight()
        + "    this.getScrollY = "+ this.getScrollY());
        return this.getScrollY() == 0 ||
                this.contentView.getHeight() < this.getHeight() + this.getScrollY();
    }

    /**
     * 判断是否滚到底部
     * @return
     */
    private boolean isCanPullUp() {
        return this.contentView.getHeight() <= this.getHeight() + this.getScrollY();
    }




    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        if(this.mListener != null) {
            this.mListener.onScrollChanged(l, t, oldl, oldt);
        }
        super.onScrollChanged(l, t, oldl, oldt);
    }

    public void setScrollViewListener(ScrollViewListener paramScrollViewListener)
    {
        this.mListener = paramScrollViewListener;
    }

    public interface ScrollViewListener
    {
        void onScrollChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
    }

    /*
    *
    * 设置滑动监听
    *     根据 paramInt2 的大小进行透明度的变化即可
    * */
}