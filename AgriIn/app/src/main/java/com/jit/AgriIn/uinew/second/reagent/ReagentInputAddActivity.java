package com.jit.AgriIn.uinew.second.reagent;

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
import com.jit.AgriIn.model.cache.UserCache;
import com.jit.AgriIn.model.response.FishInputResponse;
import com.jit.AgriIn.model.response.FishPondResponse;
import com.jit.AgriIn.model.response.InputResponse;
import com.jit.AgriIn.model.response.ReagentInputResponse;
import com.zxl.baselib.bean.response.BaseResponse;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.util.NetworkHelper;
import com.zxl.baselib.util.time.TimeUtil;
import com.zxl.baselib.util.ui.UIUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

public class ReagentInputAddActivity extends BaseActivity<ReagentInputAddView,ReagentInputAddPresenter> implements ReagentInputAddView {

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

    private List<String> units = new ArrayList<>();
    private List<String> times = new ArrayList<>();
    private List<String> batch = new ArrayList<>();
    private List<String> ponds = new ArrayList<>();
    private Integer[] isCheckedPonds;
    private List<String> reagents = new ArrayList<>();
    private Integer[] isCheckedReagents;

    private List<FishInputResponse> firl = new ArrayList<>();

    private String originText = "";

    private Boolean flag = false;

    @Override
    public void addReagentSuccess(BaseResponse baseResponse) {
        if (baseResponse != null) {
            mRxManager.post(AppConstant.RX_ADD_REAGENT_INPUT, baseResponse);
            UIUtils.showToast("添加成功");
            Log.e("test",baseResponse.toString());
        } else {
            UIUtils.showToast("请勿重复添加");
        }

        finish();
    }

    @Override
    public void addReagentFailure(String error) {
        UIUtils.showToast(error);
    }

    @Override
    public void getAllUserInputsSuccess(List<InputResponse> inputResponseList) {
        reagents.clear();

        for (InputResponse ir: inputResponseList){
            if(ir.getType().equals("调水剂")){
                reagents.add(ir.getName());
            }
        }

        new MaterialDialog.Builder(this)
                .title("选择调水剂")
                .items(reagents)
                .itemsCallbackMultiChoice(null, new MaterialDialog.ListCallbackMultiChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                        isCheckedReagents = which;
                        return true;
                    }
                })
                .alwaysCallMultiChoiceCallback()
                .positiveText("确认")
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
                .show();
    }

    @Override
    public void getAllUserInputsFailure(String error) {
        UIUtils.showToast(error);
    }

    @Override
    public void getAllUserFishInputSuccess(List<FishInputResponse> fishInputResponseList) {

        firl = fishInputResponseList;

        batch.clear();

        batch.add("无");

        for (FishInputResponse fir: fishInputResponseList){
            batch.add(fir.getBatchNumber());
        }

        new MaterialDialog.Builder(this)
                .title("请选择批次号")
                .items(batch)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        mBatchNumber.setText(text);
                    }
                })
                .positiveText(android.R.string.cancel)
                .show();

    }

    @Override
    public void getAllUserFishInputFailure(String error) {
        UIUtils.showToast(error);
    }

    @Override
    public void getAllUserPondsSuccess(List<FishPondResponse> fishPondResponseList) {
        ArrayList<String> ponds = new ArrayList<>();

        for (FishPondResponse fpr: fishPondResponseList){
            ponds.add(fpr.getName());
        }

        new MaterialDialog.Builder(this)
                .title("选择投入品")
                .items(ponds)
                .itemsCallbackMultiChoice(null, new MaterialDialog.ListCallbackMultiChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                        isCheckedPonds = which;
                        return true;
                    }
                })
                .alwaysCallMultiChoiceCallback()
                .positiveText("确认")
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
                .show();
    }

    @Override
    public void getAllUserPondsFailure(String error) {
        UIUtils.showToast(error);
    }

    @Override
    protected void init() {

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_reagent_add;
    }

    @Override
    protected ReagentInputAddPresenter createPresenter() {
        return new ReagentInputAddPresenter(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mTvToolbarTitle.setText("添加调水剂");
        mTvPublishNow.setVisibility(View.VISIBLE);
        mTvPublishNow.setText(R.string.submit);

        mSelectedTime.setText(TimeUtil.date2String
                (new Date(System.currentTimeMillis()), "yyyy-MM-dd HH:mm:ss"));
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

    }

    @Override
    protected void initListener() {
        mIvToolbarNavigation.setOnClickListener(v -> onBackPressed());

        mTvPublishNow.setOnClickListener(v -> requestSubmit());

        mReagent.setOnClickListener(v -> mPresenter.getAllUserInputs(UserCache.getUserUsername()));

        mPonds.setOnClickListener(v -> mPresenter.getAllUserPonds(UserCache.getUserUsername()));

        mBatchNumber.setOnClickListener(v -> mPresenter.getAllUserFishInput(UserCache.getUserUsername()));

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

        mBatchNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                for(FishInputResponse fir: firl){
                    if(fir.getBatchNumber().equals(s.toString())){
                        mPonds.setText(fir.getPond());
                        break;
                    }
                }
            }
        });

        mPonds.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                originText = s.toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (flag){
                    mBatchNumber.setText("无");
                    flag = false;
                }else {
                    flag = true;
                }
            }
        });

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
            doReagentInputAddCommit(batchNumber,ponds,reagent,Double.parseDouble(amount),unit,time,remarks);
        }
    }

    private void doReagentInputAddCommit(String batchNumber,String pond,String reagent,double amount,String unit,String time,String remarks) {
        mPresenter.addReagentInput(batchNumber, pond, reagent, amount, unit, time, remarks);
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