package com.jit.AgriIn.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

/**
 * @author zxl on 2018/9/17.
 *         discription: 饲料箱View 目前由于技术原因只能说绘制二维图片咯
 *  对于水桶只需画一个矩形的容器即可
 */

public class FeedView  extends View{
    private static final String TAG = "FeedView";
    private Paint mBoardPaint;
    private float mStrokeWidth = 5f;
    private int mBoardColor =  Color.BLACK;
    private int mFillColor = Color.RED;
    private Paint mFillPaint;

    private RectF mBoardRect;
    private Rect mFillRect;
    private float mBFeedWidth = 40f;
    private float mFeedHeight = 50f;
    private float mRadius = 5f;
    /**
     * 填充的百分比 ---
     */
    private float mFillValue = 0f;
    private float mRealHeight;

    public FeedView(Context context) {
       this(context,null);
    }

    public FeedView(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public FeedView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        mBoardPaint = new Paint();
        mBoardPaint.setStyle(Paint.Style.STROKE);
        mBoardPaint.setStrokeWidth(mStrokeWidth);
        mBoardPaint.setColor(mBoardColor);
        mBoardPaint.setAntiAlias(true);

        mFillPaint = new Paint();
        mFillPaint.setStyle(Paint.Style.FILL);
        mFillPaint.setColor(mFillColor);
        mFillPaint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int totalHeight = getMeasuredHeight();
        int totalWidth = getMeasuredWidth();
        mBoardRect = new RectF();
        float boardWidthHalf = mBoardPaint.getStrokeWidth()/2;
        mRealHeight = totalHeight - 4 * boardWidthHalf;
        mBoardRect.left = boardWidthHalf;
        mBoardRect.top = boardWidthHalf;
        mBoardRect.right = totalWidth - boardWidthHalf;
        mBoardRect.bottom = totalHeight - boardWidthHalf;

        mFillRect = new Rect();
        mFillRect.left = (int)(boardWidthHalf * 2);
        mFillRect.right = (int)(totalWidth - boardWidthHalf*2);
        mFillRect.bottom = (int)(totalHeight - boardWidthHalf*2);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int sizeWidth  = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeWidth  = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        Log.e(TAG, "onMeasure: " );
        if (modeWidth == MeasureSpec.AT_MOST || modeWidth == MeasureSpec.UNSPECIFIED) {
            sizeWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    mBFeedWidth,
                    getContext().getResources()
                            .getDisplayMetrics());
            sizeWidth += getPaddingLeft() + getPaddingRight();
        }
        if (modeHeight == MeasureSpec.AT_MOST || modeHeight == MeasureSpec.UNSPECIFIED) {
            sizeHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    mFeedHeight,
                    getContext().getResources()
                            .getDisplayMetrics());
            sizeHeight += getPaddingBottom() + getPaddingTop();
        }

        setMeasuredDimension(sizeWidth, sizeHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 绘制边框
        canvas.drawRoundRect(mBoardRect,mRadius,mRadius,mBoardPaint);

        // 绘制填充
        mFillRect.top = mFillRect.bottom - (int)(mRealHeight * mFillValue);
        canvas.drawRect(mFillRect,mFillPaint);
    }

    /**
     * 重新进行绘制即可--
     * @param value
     */
    public void  setValue(float value){
        if (value > 1f){
            return;
        }
        mFillValue = value;
        invalidate();
    }
}
