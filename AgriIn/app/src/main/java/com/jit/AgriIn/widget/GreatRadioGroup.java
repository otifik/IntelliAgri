package com.jit.AgriIn.widget;

/**
 * @author crazyZhangxl on 2018/10/16.
 * Describe: 很不错的RadioGroup
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

public class GreatRadioGroup extends RadioGroup
{
    // 边缘 分割线颜色
    private int bgColor = Color.parseColor("#00BFFF");
    // 画笔
    private Paint mpaint;
    // 圆弧半径 === 注意这里的半径 应当和xml里设置的左右的corners 呈对应关系
    private int roundWidth;
    // 边缘线宽度
    private int strokeWidth;

    public GreatRadioGroup(Context paramContext)
    {
        super(paramContext);
    }

    public GreatRadioGroup(Context paramContext, AttributeSet paramAttributeSet)
    {
        super(paramContext, paramAttributeSet);
    }

    private void drawDivider(Canvas paramCanvas)
    {
        if (getOrientation() == LinearLayout.HORIZONTAL)
        {
            int j = getChildCount();
            if (j > 1)
            {
                int i = 1;
                while (i < j)
                {
                    int k = getChildAt(i).getLeft();
                    paramCanvas.drawLine(k, 0.0F, k, getMeasuredHeight(), this.mpaint);
                    i += 1;
                }
            }
        }
    }

    private void drawRoundBackGround(Canvas paramCanvas)
    {
        this.roundWidth = ((int)TypedValue.applyDimension(1, 4.0F, getResources().getDisplayMetrics()));
        float f = this.mpaint.getStrokeWidth() - 1.0F;
        // 绘制圆角矩形
        paramCanvas.drawRoundRect(new RectF(
                0.0F + f,
                0.0F + f,
                getMeasuredWidth() - f,
                getMeasuredHeight() - f),
                this.roundWidth,
                this.roundWidth,
                this.mpaint);
    }

    private Path getRoundRectPath()
    {
        this.roundWidth = ((int)TypedValue.applyDimension(1, 4.0F, getResources().getDisplayMetrics()));
        int i = this.roundWidth * 2;
        Path localPath = new Path();
        RectF localRectF = new RectF();
        localPath.moveTo(0.0F, this.roundWidth);
        localRectF.set(0.0F, 0.0F, i, i);
        localPath.arcTo(localRectF, 180.0F, 90.0F);
        localPath.lineTo(getMeasuredWidth() - this.roundWidth, 0.0F);
        localRectF.set(getMeasuredWidth() - i, 0.0F, getMeasuredWidth(), i);
        localPath.arcTo(localRectF, 270.0F, 90.0F);
        localPath.lineTo(getMeasuredWidth(), getMeasuredHeight() - this.roundWidth);
        localRectF.set(getMeasuredWidth() - i, getMeasuredHeight() - i, getMeasuredWidth(), getMeasuredHeight());
        localPath.arcTo(localRectF, 0.0F, 90.0F);
        localPath.lineTo(this.roundWidth, getMeasuredHeight());
        localRectF.set(0.0F, getMeasuredHeight() - i, i, getMeasuredHeight());
        localPath.arcTo(localRectF, 90.0F, 90.0F);
        localPath.close();
        return localPath;
    }

    private void initPaint()
    {
        // 这里要做适配
        this.strokeWidth = ((int) TypedValue.applyDimension(1, 1.0F, getResources().getDisplayMetrics()));
        this.mpaint = new Paint();
        this.mpaint.setStrokeWidth(this.strokeWidth);
        // 设置抗锯齿 优化显示效果
        this.mpaint.setAntiAlias(true);
        // 设置防止抖动
        this.mpaint.setDither(true);
        this.mpaint.setStyle(Paint.Style.STROKE);
        this.mpaint.setColor(this.bgColor);
    }

    @Override
    protected void dispatchDraw(Canvas paramCanvas)
    {
        super.dispatchDraw(paramCanvas);
        if ((getMeasuredHeight() > 0) && (getMeasuredWidth() > 0))
        {
            initPaint();
            drawRoundBackGround(paramCanvas);
            drawDivider(paramCanvas);
        }
    }

    public void setBgColor(int paramInt)
    {
        this.bgColor = paramInt;
    }
}
