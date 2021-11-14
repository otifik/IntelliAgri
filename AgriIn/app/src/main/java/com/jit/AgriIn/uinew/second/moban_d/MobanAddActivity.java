package com.jit.AgriIn.uinew.second.moban_d;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jit.AgriIn.R;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.model.response.TemplateResponse;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.util.NetworkHelper;
import com.zxl.baselib.util.time.DataHelper;
import com.zxl.baselib.util.ui.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author zxl on 2018/08/29.
 *         discription: 新增塘口配置
 */
public class MobanAddActivity extends BaseActivity<MobanAddView,MobanAddPresenter> implements MobanAddView {


    @BindView(R.id.ivToolbarNavigation)
    ImageView mIvToolbarNavigation;
    @BindView(R.id.vToolbarDivision)
    View mVToolbarDivision;
    @BindView(R.id.tvToolbarTitle)
    TextView mTvToolbarTitle;
    @BindView(R.id.tv_publish_now)
    TextView mTvPublishNow;
    @BindView(R.id.tvErliaoName)
    TextView mTvErliaoName;
    @BindView(R.id.tvStartDate)
    TextView mTvStartDate;
    @BindView(R.id.tvEndDate)
    TextView mTvEndDate;
    @BindView(R.id.tvFeedTime)
    TextView mTvFeedTime;
    @BindView(R.id.etTotal)
    EditText mEtTotal;

    private List<String> richangTypeList = new ArrayList<>();

    @Override
    protected void init() {

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_moban_add;
    }

    @Override
    protected MobanAddPresenter createPresenter() {
        return new MobanAddPresenter(this);
    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        mTvToolbarTitle.setText("添加日常投入模板");
        mTvPublishNow.setVisibility(View.VISIBLE);
        mTvPublishNow.setText(R.string.submit);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        mIvToolbarNavigation.setOnClickListener(v -> onBackPressed());


        mTvErliaoName.setOnClickListener(view -> mPresenter.getErliaoType());


        mTvPublishNow.setOnClickListener(v -> requestSubmit());

        mTvStartDate.setOnClickListener(view -> showStartDatePicker());
        mTvEndDate.setOnClickListener(view -> showEndDatePicker());
        mTvFeedTime.setOnClickListener(view -> showCustomTimePicker());
    }



    public void showStartDatePicker() {
        new MaterialDialog.Builder(this)
                .title("时间选择")
                .customView(R.layout.dialog_datepicker, false)
                .positiveText(android.R.string.ok)
                .negativeText(android.R.string.cancel)
                .onPositive((dialog, which) -> {
                    DatePicker datePicker = (DatePicker) dialog.findViewById(R.id.datePicker);
                    mTvStartDate.setText(String.format("%d-%02d-%02d", datePicker.getYear(), datePicker.getMonth() + 1, datePicker.getDayOfMonth()));
                })
                .show();
    }


    public void showEndDatePicker() {
        new MaterialDialog.Builder(this)
                .title("时间选择")
                .customView(R.layout.dialog_datepicker, false)
                .positiveText(android.R.string.ok)
                .negativeText(android.R.string.cancel)
                .onPositive((dialog, which) -> {
                    DatePicker datePicker = (DatePicker) dialog.findViewById(R.id.datePicker);
                    mTvEndDate.setText(String.format("%d-%02d-%02d", datePicker.getYear(), datePicker.getMonth() + 1, datePicker.getDayOfMonth()));
                })
                .show();
    }

    public void showCustomTimePicker() {
        new MaterialDialog.Builder(this)
                .title("时间选择")
                .customView(R.layout.dialog_timepicker, false)
                .positiveText(android.R.string.ok)
                .negativeText(android.R.string.cancel)
                .onPositive((dialog, which) -> {
                    TimePicker timePicker = (TimePicker) dialog.findViewById(R.id.timePicker);
                    mTvFeedTime.setText(String.format("%02d:%02d", timePicker.getHour(), timePicker.getMinute()));
                })
                .show();
    }

    /**
     * 进行数据的提交
     */
    private void requestSubmit() {
        checkFormatData();
    }

    /**
     * 检查数据格式执行判空操作
     */
    private void checkFormatData() {
        String erliaoName = mTvErliaoName.getText().toString();
        if (TextUtils.isEmpty(erliaoName)){
            UIUtils.showToast("请选择投入饵料类型");
            return;
        }

        String startDate = mTvStartDate.getText().toString();
        if (TextUtils.isEmpty(startDate)){
            UIUtils.showToast("请选择开始投放日期");
            return;
        }

        String endDate = mTvEndDate.getText().toString();
        if (TextUtils.isEmpty(startDate)){
            UIUtils.showToast("请选择结束投放日期");
            return;
        }

        String feedTime = mTvFeedTime.getText().toString();
        if (TextUtils.isEmpty(startDate)){
            UIUtils.showToast("请选每日投喂时间");
            return;
        }

        String total = mEtTotal.getText().toString();
        if (TextUtils.isEmpty(total)){
            UIUtils.showToast("请输入投喂量");
            return;
        }

        if (!(DataHelper.compareTime("yyyy-MM-dd",startDate,endDate) == -1)){
            UIUtils.showToast("请选择合适的日期区间");
            return;
        }


        if (!NetworkHelper.isNetworkAvailable(this)){
            UIUtils.showToast(getString(R.string.no_net));
            return;
        }else {
            doPondAddCommit(erliaoName,startDate,endDate,feedTime,Integer.valueOf(total));
        }
    }


    private void doPondAddCommit(String erliaoName, String startDate,String endDate,String feedTime,int total) {
        mPresenter.addMoban(erliaoName,startDate,endDate,feedTime,total);
    }


    @Override
    public void addMobanSuccess(TemplateResponse templateResponse) {
        mRxManager.post(AppConstant.RX_ADD_POND,templateResponse);
        finish();
    }

    @Override
    public void addMobanFailure(String error) {
        UIUtils.showToast(error);
    }

    @Override
    public void getErliaoTypeSuccess(String[] erliaoType) {
        new MaterialDialog.Builder(this)
                .title("投喂饵料类型")
                .items(erliaoType)
                .itemsCallback((dialog, view, which, text) -> mTvErliaoName.setText(text))
                .positiveText(android.R.string.cancel)
                .show();
    }

    @Override
    public void getErliaoTypeFailure(String error) {
        UIUtils.showToast(error);
    }
}
