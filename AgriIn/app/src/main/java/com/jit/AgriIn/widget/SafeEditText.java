package com.jit.AgriIn.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author crazyZhangxl on 2018/10/31.
 * Describe: 安全的EditText解决滑动冲突
 */
public class SafeEditText extends android.support.v7.widget.AppCompatEditText {
    private float mStartX;
    private float mStartY;

    public SafeEditText(Context context) {
        super(context);
    }

    public SafeEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mStartX = event.getRawX();
                mStartY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float endX = event.getRawX();
                float endY = event.getRawY();
                if (Math.abs(endY -  mStartY) > Math.abs(endX - mStartX)){
                    getParent().requestDisallowInterceptTouchEvent(true);
                }else {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(event);
    }
}
