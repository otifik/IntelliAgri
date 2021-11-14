package com.jit.AgriIn.uinew.second.reagent;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.jit.AgriIn.R;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.model.bean.InputUpdateBean;
import com.jit.AgriIn.model.bean.ReagentInputUpdateBean;
import com.jit.AgriIn.model.response.ReagentInputResponse;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.util.NetworkHelper;
import com.zxl.baselib.util.ui.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ReagentInputUpdateActivity extends BaseActivity<ReagentInputUpdateView,ReagentInputUpdatePresenter> implements ReagentInputUpdateView {

    @BindView(R.id.ivToolbarNavigation)
    ImageView mIvToolbarNavigation;
    @BindView(R.id.vToolbarDivision)
    View mVToolbarDivision;
    @BindView(R.id.tvToolbarTitle)
    TextView mTvToolbarTitle;
    @BindView(R.id.tv_publish_now)
    TextView mTvPublishNow;

    @BindView(R.id.batchNumber)
    TextView mBatchNumber;
    @BindView(R.id.ponds)
    TextView mPonds;
    @BindView(R.id.reagent)
    TextView mReagent;
    @BindView(R.id.amount)
    EditText mAmount;
    @BindView(R.id.unit)
    TextView mUnit;
    @BindView(R.id.selectedTime)
    TextView mSelectedTime;
    @BindView(R.id.remarks)
    EditText mRemarks;

    private ReagentInputResponse mReagentInputResponse;

    private String batchNumber;
    private String pond;
    private String reagent;
    private double amount;
    private String unit;
    private String time;
    private String remarks;

    private int mItemIndex;
    private int mReagentInputId;
    private boolean isFromReagentInputMain = false;

    private List<String> units = new ArrayList<>();
    private List<String> times = new ArrayList<>();
    private List<String> batch = new ArrayList<>();
    private List<String> ponds = new ArrayList<>();
    private Integer[] isCheckedPonds;
    private List<String> reagents = new ArrayList<>();
    private Integer[] isCheckedReagents;

    @Override
    protected void init() {
        if (getIntent()!=null){
            mReagentInputResponse = (ReagentInputResponse) getIntent().getExtras().getSerializable(AppConstant.BUNDLE_REAGENT_INPUT_BEAN);

            batchNumber = mReagentInputResponse.getBatchNumber();
            pond = mReagentInputResponse.getPond();
            reagent = mReagentInputResponse.getReagent();
            amount = mReagentInputResponse.getAmount();
            unit = mReagentInputResponse.getUnit();
            time = mReagentInputResponse.getTime();
            remarks = mReagentInputResponse.getRemarks();

            mReagentInputId = mReagentInputResponse.getId();
            mItemIndex = getIntent().getIntExtra(AppConstant.EXTRA_ITEM_INDEX,-1);
            isFromReagentInputMain = getIntent().getBooleanExtra(AppConstant.EXTRA_IS_FROM_REAGENT_INPUT_MAIN,false);
        }
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_reagent_add;
    }

    @Override
    protected ReagentInputUpdatePresenter createPresenter() {
        return new ReagentInputUpdatePresenter(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mTvToolbarTitle.setText("修改投喂调水剂信息");
        mTvPublishNow.setVisibility(View.VISIBLE);
        mTvPublishNow.setText(R.string.submit);

        mBatchNumber.setText(batchNumber);
        mPonds.setText(pond);
        mReagent.setText(reagent);
        mAmount.setText(String.valueOf(amount));
        mUnit.setText(unit);
        mSelectedTime.setText(time);
        mReagent.setText(reagent);
        mRemarks.setText(remarks);

    }

    @Override
    protected void initData() {
        units.clear();
        units.add("毫升");
        units.add("升");
        units.add("克");
        units.add("千克");

        times.clear();
        times.add("早上");
        times.add("上午");
        times.add("中午");
        times.add("下午");
        times.add("傍晚");

        batch.clear();
        batch.add("鲈鱼20211022");
        batch.add("桂鱼20211022");

        ponds.clear();
        ponds.add("塘口A");
        ponds.add("塘口B");

        reagents.clear();
        reagents.add("消毒剂");
        reagents.add("碘水");
    }

    @Override
    protected void initListener() {
        mIvToolbarNavigation.setOnClickListener(v -> onBackPressed());

        mTvPublishNow.setOnClickListener(v -> requestSubmit());

        mReagent.setOnClickListener(v ->
                new MaterialDialog.Builder(this)
                        .title("请选择调水剂")
                        .items(reagents)
                        .itemsCallbackMultiChoice(null, new MaterialDialog.ListCallbackMultiChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                                isCheckedReagents = which;
                                return true;
                            }
                        })
                        .alwaysCallMultiChoiceCallback()
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                StringBuilder sb = new StringBuilder();
                                for(int i=0;i<isCheckedReagents.length;i++){
                                    if(i!=0){
                                        sb.append(";");
                                    }
                                    sb.append(reagents.get(isCheckedReagents[i]));
                                }
                                mReagent.setText(sb.toString());
                            }
                        })
                        .positiveText("确认")
                        .show()
        );

        mPonds.setOnClickListener(v ->
                new MaterialDialog.Builder(this)
                        .title("请选择塘口")
                        .items(ponds)
                        .itemsCallbackMultiChoice(null, new MaterialDialog.ListCallbackMultiChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                                isCheckedPonds = which;
                                return true;
                            }
                        })
                        .alwaysCallMultiChoiceCallback()
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                StringBuilder sb = new StringBuilder();
                                for(int i=0;i<isCheckedPonds.length;i++){
                                    if(i!=0){
                                        sb.append(";");
                                    }
                                    sb.append(ponds.get(isCheckedPonds[i]));
                                }
                                mPonds.setText(sb.toString());
                            }
                        })
                        .positiveText("确认")
                        .show()
        );

        mUnit.setOnClickListener(v ->
                new MaterialDialog.Builder(this)
                        .title("选择单位")
                        .items(units)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                mUnit.setText(text);
                            }
                        })
                        .positiveText(android.R.string.cancel)
                        .show()
        );

        mSelectedTime.setOnClickListener(v ->
                new MaterialDialog.Builder(this)
                        .title("选择投喂时间")
                        .items(times)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                mSelectedTime.setText(text);
                            }
                        })
                        .positiveText(android.R.string.cancel)
                        .show()
        );

        mBatchNumber.setOnClickListener(v ->
                new MaterialDialog.Builder(this)
                        .title("选择批次号")
                        .items(batch)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                mBatchNumber.setText(text);
                            }
                        })
                        .positiveText(android.R.string.cancel)
                        .show()
        );

        mSelectedTime.setOnClickListener(l -> showCustomDatePicker());
    }

    private void requestSubmit() {
        checkFormatData();
    }

    private void checkFormatData() {

        String batchNumber = mBatchNumber.getText().toString();
//        if (TextUtils.isEmpty(batchNumber)){
//            UIUtils.showToast("请选择批次号");
//            return;
//        }

        String ponds = mPonds.getText().toString();
        if (TextUtils.isEmpty(ponds)){
            UIUtils.showToast("请选择塘口");
            return;
        }

        String reagent = mReagent.getText().toString();
        if (TextUtils.isEmpty(reagent)){
            UIUtils.showToast("请选择调水剂");
            return;
        }

        String amount = mAmount.getText().toString();
        if (TextUtils.isEmpty(amount)){
            UIUtils.showToast("请输入投入量");
            return;
        }

        String unit = mUnit.getText().toString();
        if (TextUtils.isEmpty(unit)){
            UIUtils.showToast("请选择单位");
            return;
        }

        String time = mSelectedTime.getText().toString();
        if (TextUtils.isEmpty(time)){
            UIUtils.showToast("请选择时间");
            return;
        }

        String remarks = mRemarks.getText().toString();

        if(!NetworkHelper.isNetworkAvailable(this)){
            UIUtils.showToast(getString(R.string.no_net));
            return;
        }else {
            doReagentInputAddCommit(mReagentInputId,batchNumber,ponds,reagent,Double.parseDouble(amount),unit,time,remarks);
        }
    }

    private void doReagentInputAddCommit(int id,String batchNumber,String pond,String reagent,double amount,String unit,String time,String remarks) {
        mPresenter.updateReagentInput(id,batchNumber, pond, reagent, amount, unit, time, remarks);
    }

    @Override
    public void updateReagentInputSuccess() {
        ReagentInputUpdateBean reagentInputUpdateBean = new ReagentInputUpdateBean();
        reagentInputUpdateBean.setItemPosition(mItemIndex);

        mReagentInputResponse.setBatchNumber(mBatchNumber.getText().toString());
        mReagentInputResponse.setPond(mPonds.getText().toString());
        mReagentInputResponse.setReagent(mReagent.getText().toString());
        mReagentInputResponse.setAmount(Double.parseDouble(mAmount.getText().toString()));
        mReagentInputResponse.setUnit(mUnit.getText().toString());
        mReagentInputResponse.setTime(mSelectedTime.getText().toString());
        mReagentInputResponse.setRemarks(mRemarks.getText().toString());

        reagentInputUpdateBean.setReagentInputResponse(mReagentInputResponse);
        mRxManager.post(AppConstant.RX_UPDATE_REAGENT_INPUT,reagentInputUpdateBean);
        finish();
        UIUtils.showToast("修改成功");
    }

    @Override
    public void updateReagentInputFailure(String error) {
        UIUtils.showToast(error);
    }

    public void showCustomDatePicker() {
        new MaterialDialog.Builder(this)
                .title("时间选择")
                .customView(R.layout.dialog_datepicker, false)
                .positiveText(android.R.string.ok)
                .negativeText(android.R.string.cancel)
                .onPositive((dialog, which) -> {
                    DatePicker datePicker = (DatePicker) dialog.findViewById(R.id.datePicker);
                    datePicker.setBackgroundColor(1);
                    mSelectedTime.setText(String.format("%d-%02d-%d", datePicker.getYear(), datePicker.getMonth() + 1, datePicker.getDayOfMonth()));
                })
                .show();
    }
}