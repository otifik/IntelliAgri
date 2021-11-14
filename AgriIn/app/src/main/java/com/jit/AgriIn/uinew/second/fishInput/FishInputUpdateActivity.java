package com.jit.AgriIn.uinew.second.fishInput;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.jit.AgriIn.R;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.model.bean.FishInputUpdateBean;
import com.jit.AgriIn.model.cache.UserCache;
import com.jit.AgriIn.model.response.FishInputResponse;
import com.jit.AgriIn.model.response.FishPondResponse;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.util.NetworkHelper;
import com.zxl.baselib.util.time.TimeUtil;
import com.zxl.baselib.util.ui.UIUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

public class FishInputUpdateActivity extends BaseActivity<FishInputUpdateView,FishInputUpdatePresenter> implements FishInputUpdateView{

    @BindView(R.id.ivToolbarNavigation)
    ImageView mIvToolbarNavigation;
    @BindView(R.id.vToolbarDivision)
    View mVToolbarDivision;
    @BindView(R.id.tvToolbarTitle)
    TextView mTvToolbarTitle;
    @BindView(R.id.tv_publish_now)
    TextView mTvPublishNow;

    @BindView(R.id.fishType)
    EditText mFishType;
    @BindView(R.id.fishInputAmount)
    EditText mFishInputAmount;
    @BindView(R.id.unit)
    TextView mUnit;
    @BindView(R.id.selectedTime)
    TextView mSelectedTime;
    @BindView(R.id.ponds)
    TextView mPonds;
    @BindView(R.id.batchNumber)
    EditText mBatchNumber;

    private FishInputResponse mFishInputResponse;

    private String type;
    private double amount;
    private String unit;
    private String date;
    private String pond;
    private String batchNumber;

    private int mItemIndex;
    private int mFishInputId;
    private boolean isFromFishInputMain = false;

    private Integer[] isCheckedPonds;
    private List<String> selectedPonds = new ArrayList<>();

    String fishType = "";
    String time = "";

    private ArrayList<String> units =  new ArrayList<>();

    @Override
    protected void init() {
        if (getIntent()!=null){
            mFishInputResponse = (FishInputResponse) getIntent().getExtras().getSerializable(AppConstant.BUNDLE_FISH_INPUT_BEAN);
            type = mFishInputResponse.getType();
            amount = mFishInputResponse.getAmount();
            unit = mFishInputResponse.getUnit();
            date = mFishInputResponse.getDate();
            pond = mFishInputResponse.getPond();
            batchNumber = mFishInputResponse.getBatchNumber();

            mFishInputId = mFishInputResponse.getId();
            mItemIndex = getIntent().getIntExtra(AppConstant.EXTRA_ITEM_INDEX,-1);
            Log.d("test",Integer.toString(mItemIndex));
            Log.d("test",batchNumber);
            isFromFishInputMain = getIntent().getBooleanExtra(AppConstant.EXTRA_IS_FROM_FISH_INPUT_MAIN,false);
        }
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_fish_input_add;
    }

    @Override
    protected FishInputUpdatePresenter createPresenter() {
        return new FishInputUpdatePresenter(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mTvToolbarTitle.setText("修改鱼苗投入信息");
        mTvPublishNow.setVisibility(View.VISIBLE);
        mTvPublishNow.setText(R.string.submit);

        mFishType.setText(type);
        mFishInputAmount.setText(String.valueOf(amount));
        mUnit.setText(unit);
        mSelectedTime.setText(date);
        mPonds.setText(pond);
        mBatchNumber.setText(batchNumber);
    }

    @Override
    protected void initData() {
        units.clear();
        units.add("尾");
        units.add("斤");
        units.add("升");
    }

    @Override
    protected void initListener() {
        mIvToolbarNavigation.setOnClickListener(v -> onBackPressed());


        mTvPublishNow.setOnClickListener(v -> requestSubmit());

        mSelectedTime.setOnClickListener(l -> showCustomDatePicker());

        mPonds.setOnClickListener(v -> mPresenter.getAllUserPonds(UserCache.getUserUsername()));

        mUnit.setOnClickListener(v ->
                new MaterialDialog.Builder(this)
                    .title("请选择单位")
                    .items(units)
                    .itemsCallback(new MaterialDialog.ListCallback() {
                        @Override
                        public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                            mUnit.setText(text);
                        }
                    })
                    .positiveText(android.R.string.cancel)
                    .show());

//        mFishType.setOnFocusChangeListener((v, hasFocus) -> {
//            if(hasFocus){
//
//            }else {
//                fishType = mFishType.getText().toString();
//                String bN = fishType+time;
//                mBatchNumber.setText(bN);
//            }
//        });

        mFishType.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                fishType = s.toString();
                String bN = fishType+time;
                mBatchNumber.setText(bN);
            }
        });

        mSelectedTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                time = s.toString();
                String bN = fishType+time;
                mBatchNumber.setText(bN);
            }
        });

    }

    private void requestSubmit() {
        checkFormatData();
    }

    private void checkFormatData(){
        String type = mFishType.getText().toString();
        if(TextUtils.isEmpty(type)){
            UIUtils.showToast("请填写鱼苗品种");
            return;
        }

        String amount = mFishInputAmount.getText().toString();
        if(TextUtils.isEmpty(amount)){
            UIUtils.showToast("请填写投入量");
        }

        String unit = mUnit.getText().toString();
        if (TextUtils.isEmpty(unit)){
            UIUtils.showToast("请选择单位");
        }

        String date = mSelectedTime.getText().toString();
        if(TextUtils.isEmpty(date)){
            UIUtils.showToast("请填写投入时间");
        }

        String ponds = mPonds.getText().toString();
        if (TextUtils.isEmpty(ponds)){
            UIUtils.showToast("请选择塘口");
        }

        String batchNumber = mBatchNumber.getText().toString();
        if(TextUtils.isEmpty(batchNumber)){
            UIUtils.showToast("请填写批次号");
        }

        if(!NetworkHelper.isNetworkAvailable(this)){
            UIUtils.showToast(getString(R.string.no_net));
            return;
        }else {
            doInputAddCommit(type,Double.parseDouble(amount),unit,date,selectedPonds,batchNumber);
        }
    }

    private void doInputAddCommit(String type,double amount,String unit,String date,List<String> pond,String batchNumber){
        mPresenter.updateFishInput(mFishInputId,type,amount,unit,date,pond,batchNumber);
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


    @Override
    public void updateFishInputSuccess() {
        FishInputUpdateBean fishInputUpdateBean = new FishInputUpdateBean();
        fishInputUpdateBean.setItemPosition(mItemIndex);

        mFishInputResponse.setType(mFishType.getText().toString());
        mFishInputResponse.setAmount(Double.parseDouble(mFishInputAmount.getText().toString()));
        mFishInputResponse.setUnit(mUnit.getText().toString());
        mFishInputResponse.setDate(mSelectedTime.getText().toString());
        mFishInputResponse.setPond(mPonds.getText().toString());
        mFishInputResponse.setBatchNumber(mBatchNumber.getText().toString());

        fishInputUpdateBean.setFishInputResponse(mFishInputResponse);
        mRxManager.post(AppConstant.RX_UPDATE_FISH_INPUT,fishInputUpdateBean);
        UIUtils.showToast("修改成功");
        finish();
    }

    @Override
    public void updateFishInputFailure(String error) {
        UIUtils.showToast(error);
    }

    @Override
    public void getAllUserPondsSuccess(List<FishPondResponse> baseResponseList) {
        ArrayList<String> names = new ArrayList<>();
        for (FishPondResponse fpr: baseResponseList){
            names.add(fpr.getName());
        }
        new MaterialDialog.Builder(this)
                .title("请选择塘口")
                .items(names)
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
                        selectedPonds.clear();
                        for(int i=0;i<isCheckedPonds.length;i++){
                            if(i!=0){
                                sb.append(";");
                            }
                            sb.append(names.get(isCheckedPonds[i]));
                            selectedPonds.add(names.get(isCheckedPonds[i]));
                        }
                        mPonds.setText(sb.toString());
                    }
                })
                .positiveText("确认")
                .show();
    }

    @Override
    public void getAllUserPondsFailure(String error) {
        UIUtils.showToast(error);
    }
}