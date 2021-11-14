package com.jit.AgriIn.uinew.first;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.github.mikephil.charting.charts.LineChart;
import com.jit.AgriIn.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by yuanyuan on 2017/9/21.
 */

public class HisDataAdapter extends BaseQuickAdapter<HistoryDataInfo, BaseViewHolder> {
    private Context mContext = null;

    public HisDataAdapter(List<HistoryDataInfo> eiArr, Context context) {
        super(R.layout.item_hisdata, eiArr);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, HistoryDataInfo item) {
        helper.setIsRecyclable(false);



        LineChart dataChart = helper.getView(R.id.data_chart);
//        if (item.lineCount==1){ // 每张图一条线
        if (item.chartName.contains("水温")){
            new MPChartUtils(dataChart,mContext,item.xArray,item.y1Array,300.0f,0.0f,"湿度过高","湿度过低",item.chartName,50f,0f,R.color.bg_blue);
        }else if (item.chartName.contains("溶氧")){
            new MPChartUtils(dataChart,mContext,item.xArray,item.y1Array,50f,0f,"温度过高","温度过低",item.chartName,40f,0f,R.color.orange);
        }else if (item.chartName.contains("PH")){
            new MPChartUtils(dataChart,mContext,item.xArray,item.y1Array,50f,10f,"温度过高","温度过低",item.chartName,16f,0f,R.color.app_black);
        }
    }

    public static String getCurrentTimeDate() {
        //"yyyy年MM月dd日   HH:mm:ss"
        SimpleDateFormat formatter   =   new SimpleDateFormat("yyyy-MM-dd");
        Date curDate =  new Date(System.currentTimeMillis());
//获取当前时间
        String str   =   formatter.format(curDate);

        return str;
    }


    public static String getCurrentMiaoTime() {
        //"yyyy年MM月dd日   HH:mm:ss"
        SimpleDateFormat formatter   =   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate =  new Date(System.currentTimeMillis());
//获取当前时间
        String   str   =   formatter.format(curDate);

        return str;
    }

    public static String getEverydayDate(Date date) {
        //"yyyy年MM月dd日   HH:mm:ss"
        SimpleDateFormat formatter   =   new SimpleDateFormat("yyyy-MM-dd");
        String   str   =   formatter.format(date);

        return str;
    }
}
