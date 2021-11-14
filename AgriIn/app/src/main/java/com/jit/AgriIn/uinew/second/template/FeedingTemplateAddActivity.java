package com.jit.AgriIn.uinew.second.template;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.jit.AgriIn.R;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.model.cache.UserCache;
import com.jit.AgriIn.model.response.FeedingTemplateResponse;
import com.jit.AgriIn.model.response.FishInputResponse;
import com.jit.AgriIn.model.response.FishPondResponse;
import com.jit.AgriIn.model.response.InputResponse;
import com.zxl.baselib.bean.response.BaseResponse;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.util.NetworkHelper;
import com.zxl.baselib.util.ui.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class FeedingTemplateAddActivity extends BaseActivity<FeedingTemplateAddView,FeedingTemplateAddPresenter> implements FeedingTemplateAddView {

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
    private List<String> input = new ArrayList<>();

    private Integer[] isCheckedTimes;
    private Integer[] isCheckedInputs;

    @Override
    protected void init() {

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_feeding_template_add;
    }

    @Override
    protected FeedingTemplateAddPresenter createPresenter() {
        return new FeedingTemplateAddPresenter(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mTvToolbarTitle.setText("添加模板");
        mTvPublishNow.setVisibility(View.VISIBLE);
        mTvPublishNow.setText(R.string.submit);
    }

    @Override
    protected void initData() {
        units.clear();
        units.add("千克");
        units.add("斤");

        times.clear();
        times.add("早上");
        times.add("上午");
        times.add("中午");
        times.add("下午");
        times.add("傍晚");
        times.add("晚上");

        input.clear();
        input.add("玉米");
        input.add("金坷垃");
    }

    @Override
    protected void initListener() {
        mIvToolbarNavigation.setOnClickListener(v -> onBackPressed());

        mTvPublishNow.setOnClickListener(v -> requestSubmit());

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

        mInputTime.setOnClickListener(v ->
                new MaterialDialog.Builder(this)
                .title("选择投喂时间")
                .items(times)
                .itemsCallbackMultiChoice(null, new MaterialDialog.ListCallbackMultiChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                        isCheckedTimes = which;
                        return true;
                    }
                })
                .alwaysCallMultiChoiceCallback()
                .positiveText("确认")
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

//        mPond.setOnClickListener(v -> mPresenter.getAllUserPonds(UserCache.getUserUsername(),0));

        mInput.setOnClickListener(v -> mPresenter.getAllUserInputs(UserCache.getUserUsername()));

        mBatchNumber.setOnClickListener(v -> mPresenter.getAllUserFishInput(UserCache.getUserUsername()));

    }

    private void requestSubmit(){
        checkFormatData();
    }

    private void checkFormatData() {

        String name = mTemplateName.getText().toString();
        if (TextUtils.isEmpty(name)){
            UIUtils.showToast("请填写模板名称");
            return;
        }

        String batchNumber = mBatchNumber.getText().toString();
        if (TextUtils.isEmpty(batchNumber)){
            UIUtils.showToast("请选择批次");
            return;
        }

        String pond = mPond.getText().toString();

        String food = mInput.getText().toString();
        if (TextUtils.isEmpty(food)){
           UIUtils.showToast("请选择饲料");
           return;
        }

        String amount = mInputNumber.getText().toString();
        if (TextUtils.isEmpty(amount)){
            UIUtils.showToast("请填写投入量");
            return;
        }
        
        String unit = mUnit.getText().toString();
        if (TextUtils.isEmpty(unit)){
            UIUtils.showToast("请选择单位");
            return;
        }

        String time = mInputTime.getText().toString();
        if (TextUtils.isEmpty(time)){
            UIUtils.showToast("请选择投喂时间");
            return;
        }

        String remarks = mRemarks.getText().toString();

        if(!NetworkHelper.isNetworkAvailable(this)){
            UIUtils.showToast(getString(R.string.no_net));
            return;
        }else {
            doFeedingTemplateCommit(name,batchNumber,pond,food,Double.parseDouble(amount),unit,time,remarks);
        }
    }

    private void doFeedingTemplateCommit(String name,String batchNumber,String pond,String food,double amount,String unit,String time,String remarks){
        mPresenter.addFeedingTemplate(name,batchNumber,pond,food,amount,unit,time,remarks);
    }

    @Override
    public void addFeedingTemplateSuccess(BaseResponse baseResponse) {
        if (baseResponse != null) {
            mRxManager.post(AppConstant.RX_ADD_FEEDING_TEMPLATE, baseResponse);
            UIUtils.showToast("添加成功");
            Log.e("test",baseResponse.toString());
        } else {
            UIUtils.showToast("请勿重复添加");
        }

        finish();
    }

    @Override
    public void addFeedingTemplateFailure(String error) {
        UIUtils.showToast(error);
    }

    @Override
    public void getAllUserFishInputSuccess(List<FishInputResponse> fishInputResponseList) {
        ArrayList<String> fishInput = new ArrayList<>();

        for(FishInputResponse fir: fishInputResponseList){
            fishInput.add(fir.getBatchNumber());
        }

        new MaterialDialog.Builder(this)
                .title("选择批次号")
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

//    @Override
//    public void getAllUserPondSuccess(List<FishPondResponse> fishPondResponseList) {
//
//    }
//
//    @Override
//    public void getAllUserPondFailure(String error) {
//        UIUtils.showToast(error);
//    }

    @Override
    public void getAllUserInputSuccess(List<InputResponse> inputResponseList) {

        ArrayList<String> inputs = new ArrayList<>();

        for (InputResponse ir: inputResponseList){
            if(ir.getType().equals("饲料")){
                inputs.add(ir.getName());
            }
        }

        new MaterialDialog.Builder(this)
                .title("选择投入品")
                .items(inputs)
                .itemsCallbackMultiChoice(null, new MaterialDialog.ListCallbackMultiChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                        isCheckedInputs = which;
                        return true;
                    }
                })
                .alwaysCallMultiChoiceCallback()
                .positiveText("确认")
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