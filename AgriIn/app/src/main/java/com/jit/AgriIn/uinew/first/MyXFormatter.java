package com.jit.AgriIn.uinew.first;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by yuanyuan on 2017/7/21.
 */

public class MyXFormatter  implements IAxisValueFormatter {

    private List<String> mValues;

    public MyXFormatter(List<String> values) {
        this.mValues = values;
    }
    private static final String TAG = "MyXFormatter";

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        // "value" represents the position of the label on the axis (x or y)
//        Log.e(TAG, "----->getFormattedValue: "+mValues.get((int) value));
        String s = timeStamp2Date(mValues.get((int) value % mValues.size()), "HH:mm");
        return s;
    }



    /**
     6      * 时间戳转换成日期格式字符串
     7      * @param seconds 精确到秒的字符串
     8      * @param formatStr
     9      * @return
     10      */
     public static String timeStamp2Date(String seconds,String format) {
                 if(seconds == null || seconds.isEmpty() || seconds.equals("null")){
                         return "";
                    }
                 if(format == null || format.isEmpty()){
                         format = "yyyy-MM-dd HH:mm:ss";
                    }
                 SimpleDateFormat sdf = new SimpleDateFormat(format);
                return sdf.format(new Date(Long.valueOf(seconds+"000")));
            }
}