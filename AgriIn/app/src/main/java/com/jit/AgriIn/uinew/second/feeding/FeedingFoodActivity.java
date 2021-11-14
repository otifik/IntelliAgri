package com.jit.AgriIn.uinew.second.feeding;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.jit.AgriIn.R;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.model.cache.UserCache;
import com.jit.AgriIn.model.response.FeedingFoodResponse;
import com.jit.AgriIn.model.response.FeedingTemplateResponse;
import com.jit.AgriIn.model.response.FishPondResponse;
import com.jit.AgriIn.model.response.InputResponse;
import com.zxl.baselib.bean.response.BaseResponse;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.util.NetworkHelper;
import com.zxl.baselib.util.ui.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class FeedingFoodActivity extends BaseActivity<FeedingFoodView,FeedingFoodPresenter> implements FeedingFoodView {

    @BindView(R.id.ivToolbarNavigation)
    ImageView mIvToolbarNavigation;
    @BindView(R.id.vToolbarDivision)
    View mVToolbarDivision;
    @BindView(R.id.tvToolbarTitle)
    TextView mTvToolbarTitle;
    @BindView(R.id.tv_publish_now)
    TextView mTvPublishNow;

    @BindView(R.id.template)
    TextView mTemplate;
    @BindView(R.id.batchNumber)
    TextView mBatchNumber;
    @BindView(R.id.pond)
    TextView mPond;
    @BindView(R.id.food)
    TextView mFood;
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

    private Integer[] isCheckedInputs;

    private List<String> inputs = new ArrayList<>();
    private List<String> templates = new ArrayList<>();
    private List<FeedingTemplateResponse> ftrl = new ArrayList<>();

    @Override
    protected void init() {

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_feeding_food;
    }

    @Override
    protected FeedingFoodPresenter createPresenter() {
        return new FeedingFoodPresenter(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mTvToolbarTitle.setText("投喂饲料");
        mTvPublishNow.setVisibility(View.VISIBLE);
        mTvPublishNow.setText(R.string.submit);

    }

    @Override
    protected void initData() {
        units.clear();
        units.add("千克");
        units.add("斤");
        units.add("升");

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

        mTemplate.setOnClickListener(v -> mPresenter.getAllUserFeedingTemplate(UserCache.getUserUsername()));

        mFood.setOnClickListener(v -> mPresenter.getAllUserInputs(UserCache.getUserUsername()));

        mTemplate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String muban = s.toString();
                for (FeedingTemplateResponse ftr: ftrl){
                    if(ftr.getName().equals(muban)){
                        mBatchNumber.setText(ftr.getBatchNumber());
                        mPond.setText(ftr.getPond());
                        mFood.setText(ftr.getFood());
                        mAmount.setText(String.valueOf(ftr.getAmount()));
                        mUnit.setText(ftr.getUnit());
                        break;
                    }
                }
            }
        });
    }

    private void requestSubmit() {
        checkFormatData();
    }

    private void checkFormatData(){

        String template = mTemplate.getText().toString();
        if (TextUtils.isEmpty(template)){
            UIUtils.showToast("请选择模板");
            return;
        }

        String batchNumber = mBatchNumber.getText().toString();
        if (TextUtils.isEmpty(batchNumber)){
            UIUtils.showToast("请选择批次");
            return;
        }

        String pond = mPond.getText().toString();
        if (TextUtils.isEmpty(pond)){
            UIUtils.showToast("请选择塘口");
        }

        String food = mFood.getText().toString();
        if (TextUtils.isEmpty(food)){
            UIUtils.showToast("请选择饲料");
        }

        String amount = mAmount.getText().toString();
        if (TextUtils.isEmpty(amount)){
            UIUtils.showToast("请输入投喂量");
        }

        String unit = mUnit.getText().toString();
        if (TextUtils.isEmpty(unit)){
            UIUtils.showToast("请选择单位");
        }

        String time = mSelectedTime.getText().toString();
        if (TextUtils.isEmpty(time)){
            UIUtils.showToast("请选择投喂时间");
        }

        String remarks = mRemarks.getText().toString();

        if(!NetworkHelper.isNetworkAvailable(this)){
            UIUtils.showToast(getString(R.string.no_net));
            return;
        }else {
            doFeedingFoodCommit(template,batchNumber,pond,food,Double.parseDouble(amount),unit,time,remarks);
        }
    }

    private void doFeedingFoodCommit(String templateName, String batchNumber, String pond, String food, double amount, String unit, String time, String remarks) {
        mPresenter.addFeedingFood(templateName, batchNumber, pond, food, amount, unit, time, remarks);
    }

    @Override
    public void addFeedingFoodSuccess(BaseResponse baseResponse) {
        if (baseResponse != null) {
            mRxManager.post(AppConstant.RX_ADD_FEEDING_FOOD, baseResponse);
            UIUtils.showToast("添加成功");
        } else {
            UIUtils.showToast("请勿重复添加");
        }

        finish();
    }

    @Override
    public void addFeedingFoodFailure(String error) {
        UIUtils.showToast(error);
    }

    @Override
    public void getAllUserFeedingTemplateSuccess(List<FeedingTemplateResponse> feedingTemplateResponseList) {

        ftrl = feedingTemplateResponseList;

        templates.clear();

        for (FeedingTemplateResponse fpr: feedingTemplateResponseList){
            templates.add(fpr.getName());
        }
        new MaterialDialog.Builder(this)
                .title("选择投喂模板")
                .items(templates)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        mTemplate.setText(text);
                    }
                })
                .positiveText(android.R.string.cancel)
                .show();
    }

    @Override
    public void getAllUserFeedingTemplateFailure(String error) {
        UIUtils.showToast(error);
    }

    @Override
    public void getAllUserInputsSuccess(List<InputResponse> inputResponseList) {

        inputs.clear();

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
                        mFood.setText(sb.toString());
                    }
                })
                .show();
    }

    @Override
    public void getAllUserInputsFailure(String error) {
        UIUtils.showToast(error);
    }
}