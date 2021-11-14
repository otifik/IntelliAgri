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
 *         discription: 自定义电池View
 */

public class BatteryView extends View {
    private static final String TAG = "BatteryView";
    /**
     * 电池电量填充颜色
     */
    private int mColor = Color.GREEN;
    /**
     * 电池描边颜色
     */
    private int mStrokeColor = Color.BLACK;
    /**
     * 电量头的高度--- px
     */
    private float mHeadHeight = 15f;

    private float mHeadStroke = 4f;
    /**
     * 电池的宽度 dp
     */
    int mBatteryWidth  = 45;
    /**
     * 电池的高度
     */
    int mBatteryHeight = 60;

    /**
     * 电池内边距
     */
    int mBatteryInsideMargin = 0;



    /**
     * 圆角距形的角度
     */
    float mRadius = 5f;
    /**
     *电池容量圆角距形
     */
    private RectF mBoardRF;

    /**
     * 电池头矩形
     */
    private RectF mHeadRF;


    /**
     * 电量矩形 ----- 填充
     *
     * 以下为矩形的上下左右
     * (对于绘制而言也就是 顶部进行改变----)
     *
     * (左右上都是恒定不会进行变化的 ---)
     */
    private Rect mBatteryVolume;
    private int mBLeft;
    private int mBRight;
    private int mBTop;
    private int mBBottom;

    /**
     * 电池剩余量百分比 ----
     */
    private float mVolumePercentage = 0f;

    /**
     * 电池边界描边宽度 ---
     */
    private float mBoardStrokeWidth = 5f;


    private Paint mBorderPaint;
    private Paint mBatteryPaint;
    private Paint mBatteryHeaderPaint;
    /**
     * 电池电量实际高度
     */
    private float mByHeight;

    public BatteryView(Context context) {
        this(context,null);
    }

    public BatteryView(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public BatteryView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    /**
     *  设置画笔
     */
    private void initPaint(){
        //初始画笔
        //外框画笔
        mBorderPaint = new Paint();
        mBorderPaint.setColor(mStrokeColor);
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setStrokeWidth(mBoardStrokeWidth);
        mBorderPaint.setStyle(Paint.Style.STROKE);


        // 电池的填充色  ----
        mBatteryPaint = new Paint();
        mBatteryPaint.setStyle(Paint.Style.FILL);
        // -----
        mBatteryPaint.setColor(mColor);
        mBatteryPaint.setAntiAlias(true);

        // 电池头的填充  ----
        mBatteryHeaderPaint = new Paint();
        mBatteryHeaderPaint.setStyle(Paint.Style.STROKE);
        mBatteryHeaderPaint.setColor(mStrokeColor);
        mBatteryHeaderPaint.setStrokeWidth(mHeadStroke);
        mBatteryHeaderPaint.setAntiAlias(true);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.e(TAG, "onSizeChanged:得到执行啦");
        mBoardRF = new RectF();
        float boardWidth = mBorderPaint.getStrokeWidth();
        // 整体宽度
        int totalWidth = getMeasuredWidth();
        int totalHeight = getMeasuredHeight();

        // 设置电量填充外围矩形
        mBoardRF.left = boardWidth/2;
        // 留给头的有20px
        mBoardRF.top = boardWidth/2 + mHeadHeight;
        mBoardRF.right = totalWidth -  boardWidth/2;
        mBoardRF.bottom = totalHeight - boardWidth/2;
        mByHeight = totalHeight - mHeadHeight - boardWidth*2;
        float headWidth = mBatteryHeaderPaint.getStrokeWidth();
        // 设置电量头矩形
        mHeadRF = new RectF();
        mHeadRF.left = totalWidth * 2 / 9f;
        mHeadRF.right = totalWidth * 7 /9f;
        mHeadRF.top = headWidth/2;
        mHeadRF.bottom = boardWidth/2 + mHeadHeight;

        // 设置电池电量的填充
        mBatteryVolume = new Rect();
        mBLeft = (int)(mBoardRF.left + mBatteryInsideMargin + boardWidth/2);
        mBTop = (int)(mBoardRF.top + mBatteryInsideMargin + boardWidth/2);
        mBRight = (int)(mBoardRF.right - mBatteryInsideMargin - boardWidth/2);
        mBBottom = (int)(mBoardRF.bottom - mBatteryInsideMargin - boardWidth/2);

        mBatteryVolume.left = mBLeft;
        mBatteryVolume.right = mBRight;
        mBatteryVolume.bottom = mBBottom;
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
                    mBatteryWidth,
                    getContext().getResources()
                            .getDisplayMetrics());
            sizeWidth += getPaddingLeft() + getPaddingRight();
        }
        if (modeHeight == MeasureSpec.AT_MOST || modeHeight == MeasureSpec.UNSPECIFIED) {
            sizeHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    mBatteryHeight,
                    getContext().getResources()
                            .getDisplayMetrics());
            sizeHeight += getPaddingBottom() + getPaddingTop();
        }

        setMeasuredDimension(sizeWidth, sizeHeight);
    }

    /**
     * 设置进度的
     * @param percentage
     */
    public void  setValue(float percentage){
        if (percentage > 1f){
            return;
        }
        // ---------- 优化: 根据进度值,设置填充的颜色;

        mVolumePercentage = percentage;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 首先把矩形框给画出来
        canvas.drawRoundRect(mBoardRF, mRadius, mRadius, mBorderPaint);

        // 绘制电池头 ---
        canvas.drawRect(mHeadRF,mBatteryHeaderPaint);

        Log.e(TAG, "onDraw:绘制电量了" );
        mBatteryVolume.top =
                (int)(mBatteryVolume.bottom - mByHeight * mVolumePercentage);
        // 绘制电池的电量填充 ---
        canvas.drawRect(mBatteryVolume,mBatteryPaint);
    }
}
