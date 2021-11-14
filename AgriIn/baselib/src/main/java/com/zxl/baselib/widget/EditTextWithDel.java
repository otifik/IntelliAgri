package com.zxl.baselib.widget;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.zxl.baselib.R;
import com.zxl.baselib.util.ui.ResHelper;
import com.zxl.baselib.util.ui.UIUtils;
import com.zxl.baselib.util.window.DisplayHelper;


/**
 * Created by 张先磊 on 2018/5/10.
 */

public class EditTextWithDel extends android.support.v7.widget.AppCompatEditText {
    private final static String TAG = "EditTextWithDel";
    private Drawable imgInable;

    private Context mContext;

    private int mTextHeight;
    private int mTextWidth;

    public EditTextWithDel(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public EditTextWithDel(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        init();
    }

    public EditTextWithDel(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        imgInable = mContext.getResources().getDrawable(
                R.drawable.close_pushcha);
        addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                setDrawable();
            }
        });
        setDrawable();
    }

    private void setDrawable() {
        if (length() < 1)
        /**可以在上、下、左、右设置图标，如果不想在某个地方显示，则设置为null。
         * 图标的宽高将会设置为固有宽高，
         * 既自动通过getIntrinsicWidth和getIntrinsicHeight获取**/ {
            setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        } else {
            setCompoundDrawablesWithIntrinsicBounds(null, null, imgInable, null);
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTextWidth = w;
        mTextHeight = h;

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (imgInable != null && event.getAction() == MotionEvent.ACTION_UP) {
            int eventX = (int) event.getRawX();
            int eventY = (int) event.getRawY();
            Rect rect = new Rect();
            getGlobalVisibleRect(rect);
            Log.e(TAG, rect+"");
            rect.left = rect.right - DisplayHelper.dp2Px(25);
            if (eventX < rect.right && eventX > rect.left) {
                setText("");
            }

        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

}
