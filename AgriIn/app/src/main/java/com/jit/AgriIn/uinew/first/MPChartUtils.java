package com.jit.AgriIn.uinew.first;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.jit.AgriIn.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuanyuan on 2017/7/20.
 */

public class MPChartUtils {
    private Context mContext;
    private LineChart mLineChart;
    private List<String> time = new ArrayList<>();
    private List<Double> value = new ArrayList<>();
    private float mMax;
    private float mMinum;
    private float mYMax;
    private float mYmin;
    private String mMaxStr;
    private String mMinumStr;
    private String mTypeStr;


    public MPChartUtils(LineChart LineChart, Context context, List<String> mTime, List<Double> mVvalue, float max,
                        float minium, String maxStr, String minumStr,
                        String type, float yMax, float yMin,int color) {
        mContext =context;
        mLineChart = LineChart;
        time = mTime;
        value = mVvalue;
        mMax = max;
        mMinum = minium;
        mMaxStr = maxStr;
        mMinumStr = minumStr;
        mTypeStr = type;
        mYMax = yMax;
        mYmin = yMin;

        initChart(color);
    }

    private void initChart(int color) {
//        mLineChart.setOnChartGestureListener((OnChartGestureListener) mContext);
//        mLineChart.setOnChartValueSelectedListener((OnChartValueSelectedListener) mContext);
        mLineChart.setDrawGridBackground(false);

        mLineChart.getDescription().setEnabled(false);  // 是否添加描述
        mLineChart.setNoDataText("正在获取数据。。。");

        mLineChart.setTouchEnabled(true);
        mLineChart.setDragEnabled(true);
        mLineChart.setScaleEnabled(true);
        mLineChart.setPinchZoom(true);
        mLineChart.setBackgroundColor(Color.parseColor("#F8F8FF"));

        MyMarkerView mv = new MyMarkerView(mContext, R.layout.custom_marker_view);
        mv.setChartView(mLineChart); // For bounds control
        mLineChart.setMarker(mv); // Set the marker to the chart



//        LimitLine llXAxis = new LimitLine(20f, "Index 10");
//        llXAxis.setLineWidth(2f);
//        llXAxis.enableDashedLine(10f, 10f, 0f);
//        llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.LEFT_BOTTOM);
//        llXAxis.setTextSize(10f);


        //上面右边效果图的部分代码，设置X轴
        MyXFormatter formatter = new MyXFormatter(time);

        XAxis xAxis = mLineChart.getXAxis();
        xAxis.enableGridDashedLine(20f, 5f, 0f);

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // 设置X轴的位置
        xAxis.setLabelCount(12);
        xAxis.setTextSize(7);
        xAxis.setValueFormatter(formatter);
//        xAxis.setTypeface(mTf); // 设置字体
//        xAxis.setEnabled(false);
        // 上面第一行代码设置了false,所以下面第一行即使设置为true也不会绘制AxisLine
//        xAxis.setDrawAxisLine(true);

        // 前面xAxis.setEnabled(false);则下面绘制的Grid不会有"竖的线"（与X轴有关）
//        xAxis.setDrawGridLines(true); // 效果如下图

        // x-axis limit line    x轴限定条件



//        Typeface tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        String familyName = "宋体";
        Typeface tf = Typeface.create(familyName, Typeface.NORMAL);

//        LimitLine ll1 = new LimitLine(mMax, mMaxStr);
//        ll1.setLineWidth(2f);
//        ll1.enableDashedLine(10f, 10f, 0f);
//        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
//        ll1.setTextSize(10f);
//        ll1.setTypeface(tf);
//
//        LimitLine ll2 = new LimitLine(mMinum, mMinumStr);
//        ll2.setLineWidth(2f);
//        ll2.setLineColor(Color.RED);
//        ll2.enableDashedLine(10f, 10f, 0f);
//        ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
//        ll2.setTextSize(10f);
//        ll2.setTypeface(tf);


        LimitLine ll3 = new LimitLine(15,"藻类健康区");
        LimitLine ll4 = new LimitLine(12,"藻类健康区");
        if (mTypeStr.contains("溶解氧")){
            ll3.setLineWidth(2f);
            ll3.setLineColor(Color.parseColor("#F4A460"));
            ll3.setTextColor(Color.parseColor("#808080"));
//        ll3.enableDashedLine(10f, 10f, 0f);
            ll3.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
            ll3.setTextSize(10f);
            ll3.setTypeface(tf);


            ll4.setLineWidth(2f);
//        ll4.enableDashedLine(10f, 10f, 0f);
            ll4.setLineColor(Color.parseColor("#F4A460"));
            ll4.setTextColor(Color.parseColor("#808080"));
            ll4.setLabelPosition(LimitLine.LimitLabelPosition.LEFT_TOP);
            ll4.setTextSize(10f);
            ll4.setTypeface(tf);
        }





        YAxis leftAxis = mLineChart.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
//        leftAxis.addLimitLine(ll1);
//        leftAxis.addLimitLine(ll2);
        if (mTypeStr.equals("溶解氧")){
            leftAxis.addLimitLine(ll3);
            leftAxis.addLimitLine(ll4);
        }
        leftAxis.setAxisMaximum(mYMax);
        leftAxis.setAxisMinimum(mYmin);
        //leftAxis.setYOffset(20f);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(false);

        leftAxis.setDrawLimitLinesBehindData(true);

        mLineChart.getAxisRight().setEnabled(false);




        setData(color);
        mLineChart.animateX(500);
        Legend l = mLineChart.getLegend();
        l.setForm(Legend.LegendForm.LINE);
        setAttributes(mLineChart);
    }

    private void setAttributes(LineChart mLineChart) {
        // 是否显示具体点的值
        List<ILineDataSet> sets = mLineChart.getData()
                .getDataSets();

        for (ILineDataSet iSet : sets) {

            LineDataSet set = (LineDataSet) iSet;
            set.setDrawFilled(false);
        }

    }


    private void setData(int color) {

        ArrayList<Entry> values = new ArrayList<Entry>();

        for (int i = 0; i < value.size(); i++) {
            double val = value.get(i);
            values.add(new Entry(i,(float) val));
        }

        LineDataSet set1;

        if (mLineChart.getData() != null &&
                mLineChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet)mLineChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            mLineChart.getData().notifyDataChanged();
            mLineChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values, mTypeStr);

            set1.setAxisDependency(YAxis.AxisDependency.LEFT);
            set1.setColor(ContextCompat.getColor(mContext,color));
//            set1.setCircleColor(Color.WHITE);
            set1.setLineWidth(2.0f);
            set1.setDrawCircles(false);
            set1.setFillAlpha(65);
            set1.setFillColor(ContextCompat.getColor(mContext,color));
//            set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setDrawCircleHole(false);

//            if (Utils.getSDKInt() >= 18) {
//                // fill drawable only supported on api level 18 and above
//                Log.e("comes","here");
//                Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.fade_red);
//                set1.setFillDrawable(drawable);
//            }
//            else {
//                set1.setFillColor(Color.BLACK);
//            }

            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            dataSets.add(set1); // add the datasets

            // create a data object with the datasets
            LineData data = new LineData(dataSets);

            // set data
            mLineChart.setData(data);
        }
    }


}
