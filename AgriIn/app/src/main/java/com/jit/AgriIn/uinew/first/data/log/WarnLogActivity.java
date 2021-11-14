package com.jit.AgriIn.uinew.first.data.log;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.xrecyclerview.XRecyclerView;
import com.jit.AgriIn.R;
import com.jit.AgriIn.model.response.WarnLogResponse;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.util.ui.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.jit.AgriIn.uinew.first.HisDataAdapter.getCurrentMiaoTime;
import static com.jit.AgriIn.uinew.first.HisDataAdapter.getCurrentTimeDate;

public class WarnLogActivity extends BaseActivity<WarnLogView,WarnLogPresenter> implements WarnLogView{
    @BindView(R.id.rlDateSel)
    RelativeLayout rlDateSel;
    @BindView(R.id.ivToolbarNavigation)
    ImageView ivToolbarNavigation;
    @BindView(R.id.vToolbarDivision)
    View vToolbarDivision;
    @BindView(R.id.ibAddMenu)
    ImageButton ibAddMenu;
    @BindView(R.id.tvToolbarTitle)
    TextView tvToolbarTitle;
    @BindView(R.id.rvPond)
    XRecyclerView rvPond;

    @BindView(R.id.tv_starttime)
    TextView tvStartTime;
    private String mStartTime;
    private String mEndTime;

    int mPosition = 0;
    int mCellId;
    private boolean isChecked = false;

    private BaseQuickAdapter<WarnLogResponse, BaseViewHolder> mPondMainAdapter;
    private List<WarnLogResponse> mEquipResponses = new ArrayList<>();
    private WarnLogResponse mEquipResponse = new WarnLogResponse();
    @Override
    protected void init() {

        if (getIntent() != null){
            mCellId = getIntent().getIntExtra("cellId",-100);
        }
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_guding;
    }

    @Override
    protected WarnLogPresenter createPresenter() {
        return new WarnLogPresenter(this);
    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        rlDateSel.setVisibility(View.VISIBLE);

        ibAddMenu.setVisibility(View.VISIBLE);
        tvToolbarTitle.setText("预警日志列表");

        tvStartTime.setText(getCurrentTimeDate());
        mStartTime = getCurrentTimeDate() + " 00:00:00";
        mEndTime = getCurrentMiaoTime();

        setAdapter();

        mPresenter.getWarnLog(mCellId,mStartTime,mEndTime);
    }

    private void setAdapter() {
        rvPond.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mPondMainAdapter = new BaseQuickAdapter<WarnLogResponse, BaseViewHolder>(R.layout.item_warnlog,mEquipResponses
        ) {
            @Override
            protected void convert(BaseViewHolder helper, WarnLogResponse item) {
                if (item.getParam() != null){
                    helper.setText(R.id.tvType,"(" + item.getParam() +")");
                }

                if (item.isIsdown()){
                    helper.setText(R.id.tvUpOrDown,"下限预警");
                }else{
                    helper.setText(R.id.tvUpOrDown,"上限预警");
                }

                if (item.getSta() == 0){
                    helper.setText(R.id.tvSta,"否");
                }else if (item.getSta() == 1){
                    helper.setText(R.id.tvSta,"是");
                }

                helper.setText(R.id.tvTime,item.getTime());
                helper.setText(R.id.tvValue,item.getValue());
            }
        };


        rvPond.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                //设置的刷新
//                mPage = 1;
                tvStartTime.setText(getCurrentTimeDate());
                mStartTime = getCurrentTimeDate() + " 00:00:00";
                mEndTime = getCurrentMiaoTime();
                mPresenter.getWarnLog(mCellId,mStartTime,mEndTime);
            }

            @Override
            public void onLoadMore() {
                rvPond.noMoreLoading();
            }
        });



        rvPond.setAdapter(mPondMainAdapter);
        // 设置空布局
        View emptyView = LayoutInflater.from(this).inflate(R.layout.empty_pond_main, null, false);
        TextView tvEmptyDes =  emptyView.findViewById(R.id.tvEmptyDes);
        tvEmptyDes.setText("当日无日志输出");
        mPondMainAdapter.setEmptyView(emptyView);

    }





    @Override
    protected void initData() {
//        mPage = 1;
//        mPresenter.getCellEquips(mCellId);
        ibAddMenu.setVisibility(View.GONE);
    }

    @Override
    protected void initListener() {
        ivToolbarNavigation.setOnClickListener(view -> onBackPressed());

        tvStartTime.setOnClickListener(v -> {
            showCustomDatePicker();
        });
    }

    public void showCustomDatePicker() {
        new MaterialDialog.Builder(this)
                .title("时间选择")
                .customView(R.layout.dialog_datepicker, false)
                .positiveText(android.R.string.ok)
                .negativeText(android.R.string.cancel)
                .onPositive((dialog, which) -> {
                    DatePicker datePicker = (DatePicker) dialog.findViewById(R.id.datePicker);
                    tvStartTime.setText(String.format("%d-%02d-%d", datePicker.getYear(), datePicker.getMonth() + 1, datePicker.getDayOfMonth()));

                    mStartTime = tvStartTime.getText().toString() + " 00:00:00";
                    mEndTime = tvStartTime.getText().toString() + " 23:59:59";
                    mPresenter.getWarnLog(mCellId,mStartTime,mEndTime);
                })
                .show();


    }



    @Override
    public void getWarnLogSuccess(List<WarnLogResponse> equipResponseList) {
        mEquipResponses.clear();
        mEquipResponses.addAll(equipResponseList);
        mPondMainAdapter.notifyDataSetChanged();
        // 刷新成功
        rvPond.refreshComplete();
    }

    @Override
    public void getWarnLogFailure(String error) {
        UIUtils.showToast(error);
        rvPond.refreshComplete();
    }
}
