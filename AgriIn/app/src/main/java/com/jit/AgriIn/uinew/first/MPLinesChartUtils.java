package com.jit.AgriIn.uinew.first;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.jit.AgriIn.R;
import com.jit.AgriIn.model.response.HisValue;
import com.jit.AgriIn.model.response.OrgResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuanyuan on 2017/7/20.
 */

public class MPLinesChartUtils {
    private Context mContext;
    private LineChart mLineChart;
    private String mChartName = "";

    public MPLinesChartUtils(LineChart LineChart, Context context, List<HisValue> hisValueList, List<OrgResponse> orgResponseList, String typrStr){
        mContext =context;
        mLineChart = LineChart;


        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();


        if (hisValueList.isEmpty()){
            mLineChart.setData(null);
            return;
        }

        //图表描述
        Description description = new Description();
        description.setText(typrStr);//描述内容
        description.setTextColor(0xff000000);//描述字体颜色
        description.setTextSize(16f);//描述字体大小
        description.setTextAlign(Paint.Align.LEFT);//文字左对齐
        description.setPosition(100,100);//设置图表描述
        mLineChart.setDescription(description);

        mLineChart.setDrawGridBackground(false);

//        mLineChart.getDescription().setEnabled(false);  // 是否添加描述
        mLineChart.setNoDataText("正在获取数据。。。");
        mLineChart.setTouchEnabled(true);
        mLineChart.setDragEnabled(true);
        mLineChart.setScaleEnabled(true);
        mLineChart.setPinchZoom(true);
        mLineChart.setBackgroundColor(Color.parseColor("#F8F8FF"));
        MyMarkerView mv = new MyMarkerView(mContext, R.layout.custom_marker_view);
        mv.setChartView(mLineChart); // For bounds control
        mLineChart.setMarker(mv); // Set the marker to the chart

        List<String> times =new ArrayList<>();
        times.clear();
        times = hisValueList.get(0).getTimes();
        //上面右边效果图的部分代码，设置X轴
        for (int i=0;i<hisValueList.size();i++){
            if (times.size() < hisValueList.get(i).getTimes().size() ){
                times = hisValueList.get(i).getTimes();
            }

        }
        MyXFormatter formatter = new MyXFormatter(times);
        XAxis xAxis = mLineChart.getXAxis();
        xAxis.enableGridDashedLine(20f, 5f, 0f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // 设置X轴的位置
        xAxis.setLabelCount(12);
        xAxis.setTextSize(7);
        xAxis.setValueFormatter(formatter);


        for (int i = 0; i < hisValueList.size(); i++) {
            ArrayList<Entry> yVals = new ArrayList<>();


            for (int j = 0; j < hisValueList.get(i).getValues().size(); j++) {
                double val = 0;
                if (hisValueList.get(i).getValues().get(j) != null){
                    val = hisValueList.get(i).getValues().get(j);
                    yVals.add(new Entry(j,(float) val));
                }

            }
            for (int m=0;m<orgResponseList.size();m++){
                if (orgResponseList.get(m).getSnpid() == hisValueList.get(i).getSnpid()){
                    mChartName = orgResponseList.get(m).getName() +"(" + orgResponseList.get(m).getSuffix() + ")";
                }
            }

            LineDataSet set1 = new LineDataSet(yVals, String.valueOf(mChartName));

//            set1.setDrawC(true);  //设置曲线为圆滑的线
            set1.setCubicIntensity(0.2f);
            set1.setDrawFilled(false);  //设置包括的范围区域填充颜色
            set1.setDrawCircles(false);  //设置有圆点
            set1.setLineWidth(2f);    //设置线的宽度
            set1.setCircleSize(5f);   //设置小圆的大小
            int color = (int) -(Math.random() * (16777216 - 1) + 1);;
            set1.setHighLightColor(color);
            set1.setColor(color);    //设置曲线的颜色
            set1.setFillColor(color);
            dataSets.add(set1);
        }
//        data = new LineData(xVals, dataSets);

        // create a data object with the datasets
        LineData data = new LineData(dataSets);

        // set data
        mLineChart.setData(data);
        Legend legend = mLineChart.getLegend();
        legend.setWordWrapEnabled(true);
    }

}
