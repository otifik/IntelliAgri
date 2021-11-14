package com.jit.AgriIn.uinew.first;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.xrecyclerview.XRecyclerView;
import com.github.mikephil.charting.charts.LineChart;
import com.jit.AgriIn.R;
import com.jit.AgriIn.model.response.HisResponse;
import com.jit.AgriIn.model.response.HisValue;
import com.jit.AgriIn.model.response.OrgResponse;
import com.jit.AgriIn.uinew.first.data.query.HisDataPresenter;
import com.jit.AgriIn.uinew.first.data.query.HisDataView;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BaseFragment;
import com.zxl.baselib.util.app.SharePreferenceUtils;
import com.zxl.baselib.util.ui.UIUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import butterknife.BindView;

import static com.jit.AgriIn.uinew.first.HisDataAdapter.getCurrentMiaoTime;
import static com.jit.AgriIn.uinew.first.HisDataAdapter.getCurrentTimeDate;

/**
 * Created by yuanyuan on 2019/3/11.
 */

public class HisDataFragment extends BaseFragment<HisDataView, HisDataPresenter> implements HisDataView {


    @BindView(R.id.rvPond)
    XRecyclerView rvHis;
    @BindView(R.id.tv_starttime)
    TextView tvStartTime;
    private String mStartTime;
    private String mEndTime;
    private Timer mTimer;

    private String mChartName = "";

    private List<HisResponse> hisResponseList = new ArrayList<>();
    private BaseQuickAdapter<HisResponse, BaseViewHolder> mAdapter;
    private List<HisResponse> mHisResponse = new ArrayList<>();
    private List<HisValue> mHisValueList = new ArrayList<>();

    private List<OrgResponse> mOrgResponsee = new ArrayList<>();

    int mCellId;

    @Override
    public void init() {

    }


    public static HisDataFragment newInstance() {

        Bundle args = new Bundle();

        HisDataFragment fragment = new HisDataFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_auto_cruise;
    }

    @Override
    protected HisDataPresenter createPresenter() {
        return new HisDataPresenter((BaseActivity)getActivity());
    }


    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }


    private void initAdapter() {
        rvHis.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        mAdapter = new BaseQuickAdapter<HisResponse, BaseViewHolder>(R.layout.item_hisdata,mHisResponse
        ) {
            @Override
            protected void convert(BaseViewHolder helper, HisResponse item) {
                LineChart dataChart = helper.getView(R.id.data_chart);
                mHisValueList.clear();
                for (int i=0;i<item.getSntvs().size();i++){
                    if (!item.getSntvs().get(i).getTimes().isEmpty()){
                        mHisValueList.add(item.getSntvs().get(i));
                    }
                }
                new MPLinesChartUtils(dataChart,getActivity(),mHisValueList,mOrgResponsee,item.getType());
            }
        };


        rvHis.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                //设置的刷新
//                mPage = 1;
                tvStartTime.setText(getCurrentTimeDate());
                mStartTime = getCurrentTimeDate() + " 00:00:00";
                mEndTime = getCurrentMiaoTime();
                mPresenter.getOrgData(mCellId);
            }

            @Override
            public void onLoadMore() {
                rvHis.noMoreLoading();
            }
        });



        rvHis.setAdapter(mAdapter);
        // 设置空布局
        View emptyView = LayoutInflater.from(getActivity()).inflate(R.layout.empty_pond_main, null, false);
        TextView tvEmptyDes =  emptyView.findViewById(R.id.tvEmptyDes);
        tvEmptyDes.setText("无传感数据");
        mAdapter.setEmptyView(emptyView);
    }


    @Override
    public void initView(View rootView) {
        tvStartTime.setText(getCurrentTimeDate());
        mStartTime = getCurrentTimeDate() + " 00:00:00";
        mEndTime = getCurrentMiaoTime();

        initAdapter();



    }





    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        tvStartTime.setOnClickListener(v -> {
            showCustomDatePicker();
        });
    }

    public void showCustomDatePicker() {
        new MaterialDialog.Builder(getActivity())
                .title("时间选择")
                .customView(R.layout.dialog_datepicker, false)
                .positiveText(android.R.string.ok)
                .negativeText(android.R.string.cancel)
                .onPositive((dialog, which) -> {
                    DatePicker datePicker = (DatePicker) dialog.findViewById(R.id.datePicker);
                    tvStartTime.setText(String.format("%d-%02d-%d", datePicker.getYear(), datePicker.getMonth() + 1, datePicker.getDayOfMonth()));

                    mStartTime = tvStartTime.getText().toString() + " 00:00:00";
                    mEndTime = tvStartTime.getText().toString() + " 23:59:59";
                    mPresenter.getHisData(mCellId,mStartTime,mEndTime);
                })
                .show();


    }



    @Override
    public void getHisDataSuccess(List<HisResponse> hisResponseList) {
        mHisResponse.clear();
        if (hisResponseList == null){
            UIUtils.showToast("设备不在线");
        }else if (hisResponseList.isEmpty()){
            UIUtils.showToast("暂无传感设备");
        } else {
            for (int i=0;i<hisResponseList.size();i++){
                if (!hisResponseList.get(i).getSntvs().isEmpty()){
                    mHisResponse.add(hisResponseList.get(i));
                }
            }
            if (mHisResponse.isEmpty()){
                UIUtils.showToast("暂无历史数据");
                mAdapter.notifyDataSetChanged();
                rvHis.refreshComplete();
                return;
            }
            mAdapter.notifyDataSetChanged();
        }

        // 刷新成功
        rvHis.refreshComplete();
    }

    @Override
    public void getHisDataFailure(String error) {
        UIUtils.showToast(error);
        rvHis.refreshComplete();
    }

    @Override
    public void getOrgDataSuccess(List<OrgResponse> orgResponseList) {
        mOrgResponsee.clear();
        mOrgResponsee.addAll(orgResponseList);
        mPresenter.getHisData(mCellId,mStartTime,mEndTime);
    }

    @Override
    public void getOrgDataFailure(String error) {
        UIUtils.showToast(error);
    }

    @Override
    public void onSupportVisible() {
        mCellId = SharePreferenceUtils.getInstance(getActivity()).getInt("cellId",-100);
        mPresenter.getOrgData(mCellId);
    }

}
