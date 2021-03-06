package com.jit.AgriIn.uinew.second.template;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.jit.AgriIn.R;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.model.bean.FeedingTemplateUpdateBean;
import com.jit.AgriIn.model.cache.UserCache;
import com.jit.AgriIn.model.response.FeedingTemplateResponse;
import com.jit.AgriIn.model.response.FishInputResponse;
import com.jit.AgriIn.model.response.InputResponse;
import com.jit.AgriIn.uinew.second.feeding.FeedingFoodUpdateView;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.util.NetworkHelper;
import com.zxl.baselib.util.ui.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class FeedingTemplateUpdateActivity extends BaseActivity<FeedingTemplateUpdateView,FeedingTemplateUpdatePresenter> implements FeedingTemplateUpdateView {

    @BindView(R.id.ivToolbarNavigation)
    ImageView mIvToolbarNavigation;
    @BindView(R.id.vToolbarDivision)
    View mVToolbarDivision;
    @BindView(R.id.tvToolbarTitle)
    TextView mTvToolbarTitle;
    @BindView(R.id.tv_publish_now)
    TextView mTvPublishNow;

    @BindView(R.id.templateName)
    EditText mTemplateName;
    @BindView(R.id.batchNumber)
    TextView mBatchNumber;
    @BindView(R.id.pond)
    TextView mPond;
    @BindView(R.id.input)
    TextView mInput;
    @BindView(R.id.inputNumber)
    EditText mInputNumber;
    @BindView(R.id.unit)
    TextView mUnit;
    @BindView(R.id.inputTime)
    TextView mInputTime;
    @BindView(R.id.remarks)
    EditText mRemarks;

    private List<String> units = new ArrayList<>();
    private List<String> times = new ArrayList<>();
    private List<String> batch = new ArrayList<>();
    private List<String> input = new ArrayList<>();

    private Integer[] isCheckedTimes;
    private Integer[] isCheckedInputs;

    private FeedingTemplateResponse mFeedingTemplateResponse;

    private String name;
    private String batchNumber;
    private String pond;
    private String food;
    private double amount;
    private String unit;
    private String time;
    private String remarks;

    private int mItemIndex;
    private int mFeedingTemplateId;
    private boolean isFromFeedingTemplateMain = false;

    @Override
    protected void init() {
        if (getIntent()!=null){
            mFeedingTemplateResponse = (FeedingTemplateResponse) getIntent().getExtras().getSerializable(AppConstant.BUNDLE_FEEDING_TEMPLATE_BEAN);

            name = mFeedingTemplateResponse.getName();
            batchNumber = mFeedingTemplateResponse.getBatchNumber();
            pond = mFeedingTemplateResponse.getPond();
            food = mFeedingTemplateResponse.getFood();
            amount = mFeedingTemplateResponse.getAmount();
            unit = mFeedingTemplateResponse.getUnit();
            time = mFeedingTemplateResponse.getTime();
            remarks = mFeedingTemplateResponse.getRemarks();

            mFeedingTemplateId = mFeedingTemplateResponse.getId();
            mItemIndex = getIntent().getIntExtra(AppConstant.EXTRA_ITEM_INDEX,-1);
            isFromFeedingTemplateMain = getIntent().getBooleanExtra(AppConstant.EXTRA_IS_FROM_FEEDING_TEMPLATE_MAIN,false);
        }
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_feeding_template_add;
    }

    @Override
    protected FeedingTemplateUpdatePresenter createPresenter() {
        return new FeedingTemplateUpdatePresenter(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mTvToolbarTitle.setText("??????????????????");
        mTvPublishNow.setVisibility(View.VISIBLE);
        mTvPublishNow.setText(R.string.submit);

        mTemplateName.setText(name);
        mBatchNumber.setText(batchNumber);
        mPond.setText(pond);
        mInput.setText(food);
        mInputNumber.setText(String.valueOf(amount));
        mUnit.setText(unit);
        mInputTime.setText(time);
        mRemarks.setText(remarks);
    }

    @Override
    protected void initData() {
        units.clear();
        units.add("??????");
        units.add("???");

        times.clear();
        times.add("??????");
        times.add("??????");
        times.add("??????");
        times.add("??????");
        times.add("??????");
        times.add("??????");

        batch.clear();
        batch.add("??????20211022");
        batch.add("??????20211022");

        input.clear();
        input.add("??????");
        input.add("?????????");
    }

    @Override
    protected void initListener() {
        mIvToolbarNavigation.setOnClickListener(v -> onBackPressed());

        mTvPublishNow.setOnClickListener(v -> requestSubmit());

        mUnit.setOnClickListener(v ->
                new MaterialDialog.Builder(this)
                        .title("????????????")
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

        mInputTime.setOnClickListener(v ->
                new MaterialDialog.Builder(this)
                        .title("??????????????????")
                        .items(times)
                        .itemsCallbackMultiChoice(null, new MaterialDialog.ListCallbackMultiChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                                isCheckedTimes = which;
                                return true;
                            }
                        })
                        .alwaysCallMultiChoiceCallback()
                        .positiveText("??????")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                StringBuilder sb = new StringBuilder();
                                for(int i=0;i<isCheckedTimes.length;i++){
                                    if(i!=0){
                                        sb.append(";");
                                    }
                                    sb.append(times.get(isCheckedTimes[i]));
                                }
                                mInputTime.setText(sb.toString());
                            }
                        })
                        .show()
        );

        mInput.setOnClickListener(v -> mPresenter.getAllUserInputs(UserCache.getUserUsername()));

        mBatchNumber.setOnClickListener(v -> mPresenter.getAllUserFishInput(UserCache.getUserUsername()));
    }

    private void requestSubmit(){
        checkFormatData();
    }

    private void checkFormatData() {

        String name = mTemplateName.getText().toString();
        if (TextUtils.isEmpty(name)){
            UIUtils.showToast("?????????????????????");
            return;
        }

        String batchNumber = mBatchNumber.getText().toString();
        if (TextUtils.isEmpty(batchNumber)){
            UIUtils.showToast("???????????????");
            return;
        }

        String pond = mPond.getText().toString();

        String food = mInput.getText().toString();
        if (TextUtils.isEmpty(food)){
            UIUtils.showToast("???????????????");
            return;
        }

        String amount = mInputNumber.getText().toString();
        if (TextUtils.isEmpty(amount)){
            UIUtils.showToast("??????????????????");
            return;
        }

        String unit = mUnit.getText().toString();
        if (TextUtils.isEmpty(unit)){
            UIUtils.showToast("???????????????");
            return;
        }

        String time = mInputTime.getText().toString();
        if (TextUtils.isEmpty(time)){
            UIUtils.showToast("?????????????????????");
            return;
        }

        String remarks = mRemarks.getText().toString();

        if(!NetworkHelper.isNetworkAvailable(this)){
            UIUtils.showToast(getString(R.string.no_net));
            return;
        }else {
            doFeedingTemplateCommit(mFeedingTemplateId,name,batchNumber,pond,food,Double.parseDouble(amount),unit,time,remarks);
        }
    }

    private void doFeedingTemplateCommit(int id,String name,String batchNumber,String pond,String food,double amount,String unit,String time,String remarks){
        mPresenter.updateFeedingTemplate(id, name, batchNumber, pond, food, amount, unit, time, remarks);
    }

    @Override
    public void updateFeedingTemplateSuccess() {
        FeedingTemplateUpdateBean feedingTemplateUpdateBean = new FeedingTemplateUpdateBean();
        feedingTemplateUpdateBean.setItemPosition(mItemIndex);

        mFeedingTemplateResponse.setName(name);
        mFeedingTemplateResponse.setBatchNumber(batchNumber);
        mFeedingTemplateResponse.setPond(pond);
        mFeedingTemplateResponse.setFood(food);
        mFeedingTemplateResponse.setAmount(amount);
        mFeedingTemplateResponse.setUnit(unit);
        mFeedingTemplateResponse.setTime(time);
        mFeedingTemplateResponse.setRemarks(remarks);

        feedingTemplateUpdateBean.setFeedingTemplateResponse(mFeedingTemplateResponse);
        mRxManager.post(AppConstant.RX_UPDATE_FEEDING_TEMPLATE,feedingTemplateUpdateBean);
        UIUtils.showToast("????????????");
        finish();
    }

    @Override
    public void updateFeedingTemplateFailure(String error) {
        UIUtils.showToast(error);
    }

    @Override
    public void getAllUserFishInputSuccess(List<FishInputResponse> fishInputResponseList) {
        ArrayList<String> fishInput = new ArrayList<>();

        for(FishInputResponse fir: fishInputResponseList){
            fishInput.add(fir.getBatchNumber());
        }

        new MaterialDialog.Builder(this)
                .title("???????????????")
                .items(fishInput)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        mBatchNumber.setText(text);
                        for(FishInputResponse fir: fishInputResponseList){
                            if(fir.getBatchNumber().equals(mBatchNumber.getText())){
                                mPond.setText(fir.getPond());
                                break;
                            }
                        }
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
    public void getAllUserInputSuccess(List<InputResponse> inputResponseList) {
        ArrayList<String> inputs = new ArrayList<>();

        for (InputResponse ir: inputResponseList){
            if(ir.getType().equals("??????")){
                inputs.add(ir.getName());
            }
        }

        new MaterialDialog.Builder(this)
                .title("???????????????")
                .items(inputs)
                .itemsCallbackMultiChoice(null, new MaterialDialog.ListCallbackMultiChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                        isCheckedInputs = which;
                        return true;
                    }
                })
                .alwaysCallMultiChoiceCallback()
                .positiveText("??????")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        StringBuilder sb = new StringBuilder();
                        for(int i=0;i<isCheckedInputs.length;i++){
                            if(i!=0){
                                sb.append(";");
                            }
                            sb.append(inputs.get(isCheckedInputs[i]));
                        }
                        mInput.setText(sb.toString());
                    }
                })
                .show();
    }

    @Override
    public void getAllUserInputFailure(String error) {
        UIUtils.showToast(error);
    }
}