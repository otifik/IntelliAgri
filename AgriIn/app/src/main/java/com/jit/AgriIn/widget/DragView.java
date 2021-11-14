package com.jit.AgriIn.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * @author zxl on 2018/9/19.
 *         discription: 自定义横向的药箱View
 */

public class DragView extends View {

    /**
     * 边缘线画笔
     */
    private Paint mBoardPaint;

    /**
     * 边缘线的宽度
     */
    private float mBoardWidth = 5f;
    /**
     * 边缘线条的颜色
     */
    private int mBoardColor = Color.BLACK;

    /**
     * 填充画笔
     */
    private Paint mSolidPaint;

    /**
     * 边缘矩形
     */
    private RectF mBoardRect;

    /**
     * 填充矩形
     */
    private Rect mSolidRect;


    /**
     * 填充线条的颜色
     */
    private int mSolidColor = Color.RED;
    private float mDragWidth = 50f;
    private float mDragHeight = 40f;
    private float mRadius = 5f;
    /**
     * 填充的百分比 ---
     */
    private float mFillValue = 0f;
    private float mRealHeight;

    public DragView(Context context) {
        this(context,null);
    }

    public DragView(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public DragView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mBoardRect = new RectF();
        float halfWidth = mBoardPaint.getStrokeWidth() / 2;
        int totalHeight = getMeasuredHeight();
        int totalWidth = getMeasuredWidth();

        mBoardRect.left = halfWidth;
        mBoardRect.top = halfWidth;
        mBoardRect.right = totalWidth - halfWidth;
        mBoardRect.bottom = totalHeight - halfWidth;
        mRealHeight = totalHeight - 4 * halfWidth;

        mSolidRect = new Rect();
        mSolidRect.left = (int)(halfWidth * 2);
        mSolidRect.right = (int)(totalWidth - halfWidth * 2);
        mSolidRect.bottom = (int)(totalHeight - halfWidth * 2);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int sizeWidth  = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeWidth  = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        if (modeWidth == MeasureSpec.AT_MOST || modeWidth == MeasureSpec.UNSPECIFIED) {
            sizeWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    mDragWidth,
                    getContext().getResources()
                            .getDisplayMetrics());
            sizeWidth += getPaddingLeft() + getPaddingRight();
        }
        if (modeHeight == MeasureSpec.AT_MOST || modeHeight == MeasureSpec.UNSPECIFIED) {
            sizeHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    mDragHeight,
                    getContext().getResources()
                            .getDisplayMetrics());
            sizeHeight += getPaddingBottom() + getPaddingTop();
        }

        setMeasuredDimension(sizeWidth, sizeHeight);
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        mBoardPaint = new Paint();
        mBoardPaint.setStyle(Paint.Style.STROKE);
        mBoardPaint.setStrokeWidth(mBoardWidth);
        mBoardPaint.setColor(mBoardColor);
        mBoardPaint.setAntiAlias(true);

        mSolidPaint = new Paint();
        mSolidPaint.setStyle(Paint.Style.FILL);
        mSolidPaint.setColor(mSolidColor);
        mSolidPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRoundRect(mBoardRect,mRadius,mRadius,mBoardPaint);
        mSolidRect.top = mSolidRect.bottom - (int)(mRealHeight * mFillValue);
        canvas.drawRect(mSolidRect,mSolidPaint);
    }

    public void setValue(float progress){
        if (progress > 1){
            return;
        }
        mFillValue = progress;
        invalidate();
    }

}
