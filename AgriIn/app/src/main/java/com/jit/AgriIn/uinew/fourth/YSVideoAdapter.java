package com.jit.AgriIn.uinew.fourth;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jit.AgriIn.R;
import com.videogo.openapi.bean.EZDeviceInfo;

import java.util.List;

public class YSVideoAdapter extends BaseQuickAdapter<EZDeviceInfo,BaseViewHolder> {


    public YSVideoAdapter(int layoutResId, @Nullable List<EZDeviceInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, EZDeviceInfo item) {
        LinearLayout llItem = helper.getView(R.id.ll_itemvideo);
        View itemIconArea = helper.getView(R.id.item_icon_area);
        ImageView iconIv = helper.getView(R.id.item_icon);
        ImageView playBtn = helper.getView(R.id.item_play_btn);
        ImageView offlineBtn = helper.getView(R.id.item_offline);
        ImageView offlineBgBtn = helper.getView(R.id.offline_bg);
        TextView tvDeviceName = helper.getView(R.id.tvDeviceName);

        helper.addOnClickListener(R.id.item_play_btn);

//        if (!(item.getDeviceSerial().equals("231750010") ||  item.getDeviceSerial().equals("231750199") || item.getDeviceSerial().equals("231750698"))){
//            llItem.setVisibility(View.GONE);
//        }

        if (item != null){
            if (item.getStatus() == 2) {//设备不在线
                offlineBtn.setVisibility(View.VISIBLE);
                offlineBgBtn.setVisibility(View.VISIBLE);
                playBtn.setVisibility(View.GONE);
            }else {
                offlineBtn.setVisibility(View.GONE);
                offlineBgBtn.setVisibility(View.GONE);
                playBtn.setVisibility(View.VISIBLE);
            }
        }


//        Log.e("封面",item.getDeviceCover());
//            if(!TextUtils.isEmpty(imageUrl)) {
//                iconIv.setVisibility(View.VISIBLE);
////                Glide.with(mContext).load(imageUrl).placeholder(R.drawable.device_other).into(viewHolder.iconIv);
//                GlideLoaderUtils.display(mContext, iconIv, imageUrl);
//            }

        tvDeviceName.setText(item.getDeviceName());

    }
}
