package com.zxl.baselib.widget.fab;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

import com.zxl.baselib.baserx.RxBusHelper;
import com.zxl.baselib.commom.BaseAppConst;

/**
 * @author zxl on 2018/8/23.
 *         discription: 自定义FloatActionBar的Behavior
 *
 */

public class ScrollFABBehavior extends FloatingActionButton.Behavior {

    public ScrollFABBehavior(Context context, AttributeSet attrs) {
        super();
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull FloatingActionButton child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL ||
                super.onStartNestedScroll(coordinatorLayout,
                child,
                directTargetChild,
                target,
                axes,
                type);
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout,
                               @NonNull FloatingActionButton child,
                               @NonNull View target,
                               int dxConsumed,
                               int dyConsumed,
                               int dxUnconsumed,
                               int dyUnconsumed,
                               int type) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
        // 如果悬浮按钮可见,并且是上滑,那么隐藏
        if (dyConsumed > 0 && child.getVisibility() == View.VISIBLE) {
            child.hide();
            // 发送给菜单通知 ---
            RxBusHelper.getInstance().post(BaseAppConst.MENU_SHOW_HIDE,false);
        } else if (dyConsumed < 0 && child.getVisibility() != View.VISIBLE) {
            // 如果悬浮按钮不可见而且是下滑
            RxBusHelper.getInstance().post(BaseAppConst.MENU_SHOW_HIDE,true);
            child.show();
        }
    }
}
