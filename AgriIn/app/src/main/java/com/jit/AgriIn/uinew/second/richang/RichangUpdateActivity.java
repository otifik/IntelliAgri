package com.jit.AgriIn.uinew.second.richang;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jit.AgriIn.R;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.model.bean.RichangUpdateBean;
import com.jit.AgriIn.model.cache.UserCache;
import com.jit.AgriIn.model.response.DailyThrowResponse;
import com.jit.AgriIn.uinew.role_admin.CellChooseActivity;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.util.NetworkHelper;
import com.zxl.baselib.util.time.TimeUtil;
import com.zxl.baselib.util.ui.UIUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

/**
 * @author zxl on 2018/08/29.
 *         discription: 新增塘口配置
 */
public class RichangUpdateActivity extends BaseActivity<RichangUpdateView,RichangUpdatePresenter> implements RichangUpdateView {

    @BindView(R.id.ivToolbarNavigation)
    ImageView mIvToolbarNavigation;
    @BindView(R.id.vToolbarDivision)
    View mVToolbarDivision;
    @BindView(R.id.tvToolbarTitle)
    TextView mTvToolbarTitle;
    @BindView(R.id.tv_publish_now)
    TextView mTvPublishNow;
    @BindView(R.id.tvRichangType)
    TextView mTvRichangType;
    @BindView(R.id.tvTimeSelected)
    TextView mTvTimeSelected;
    @BindView(R.id.etDescription)
    EditText mEtRizhiContent;
    @BindView(R.id.etTotal)
    EditText mEtTotal;
    @BindView(R.id.tvRichangName)
    TextView mTvRichangName;

    @BindView(R.id.tvUnit)
    TextView mTvUnit;

    @BindView(R.id.tvCellId)
    TextView mTvCellId;
    int mCellId;

    private List<String> richangTypeList = new ArrayList<>();
    private List<String> unitType = new ArrayList<>();

    private DailyThrowResponse mDailyThrowResponse;
    private String mRichangType;
    private int mRichangTotal;
    private String mRichangTime;
    private String mRichangDes;

    private int mItemIndex;
    private int mRichangId;
    private boolean isFromPondMain = false;



    @Override
    protected void init() {
        if (getIntent() != null){
            mDailyThrowResponse = (DailyThrowResponse) getIntent().getExtras().getSerializable(AppConstant.BUNDLE_POND_BEAN);
            mRichangType = mDailyThrowResponse.getType();
            mRichangTotal = mDailyThrowResponse.getTotal();
            mRichangTime = mDailyThrowResponse.getSysTime();
            mRichangDes= mDailyThrowResponse.getDescription();
            mRichangId = mDailyThrowResponse.getId();
            mItemIndex = getIntent().getIntExtra(AppConstant.EXTRA_ITEM_INDEX,-1);
            isFromPondMain = getIntent().getBooleanExtra(AppConstant.EXTRA_IS_FROM_POND_MAIN,false);
        }

        richangTypeList = Arrays.asList(AppConstant.RICHANG_TYPE_ARRAYS);
        unitType.add("斤");
        unitType.add("升");
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_richang_add;
    }

    @Override
    protected RichangUpdatePresenter createPresenter() {
        return new RichangUpdatePresenter(this);
    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        mTvToolbarTitle.setText("修改日常投放");
        mTvPublishNow.setVisibility(View.VISIBLE);
        mTvPublishNow.setText(R.string.submit);

        mTvRichangType.setText(mRichangType);
        mEtTotal.setText(String.valueOf(mRichangTotal));
        mTvTimeSelected.setText(mRichangTime);
        mEtRizhiContent.setText(String.valueOf(mRichangDes));
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        mIvToolbarNavigation.setOnClickListener(v -> onBackPressed());


        mTvPublishNow.setOnClickListener(v -> requestSubmit());

        mTvRichangType.setOnClickListener(view -> TypeDialogShow());

        mTvTimeSelected.setOnClickListener(view -> showCustomDatePicker());

        mTvUnit.setOnClickListener(view -> new MaterialDialog.Builder(mContext)
                .title("单位")
                .items(unitType)
                .itemsCallback((dialog, v, which, text) -> mTvUnit.setText(text))
                .positiveText(android.R.string.cancel)
                .show());

        mTvCellId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, CellChooseActivity.class);
                intent.putExtra("username", UserCache.getUserUsername());
                startActivityForResult(intent, 1);//带请求码打开activity (请求码自定 这里设为1
            }
        });
    }

    private void TypeDialogShow() {
        new MaterialDialog.Builder(this)
                .title("日常投入类型")
                .items(richangTypeList)
                .itemsCallback((dialog, view, which, text) -> mTvRichangType.setText(text))
                .positiveText(android.R.string.cancel)
                .show();
    }

    public void showCustomDatePicker() {
        new MaterialDialog.Builder(this)
                .title("时间选择")
                .customView(R.layout.dialog_datepicker, false)
                .positiveText(android.R.string.ok)
                .negativeText(android.R.string.cancel)
                .onPositive((dialog, which) -> {
                    DatePicker datePicker = (DatePicker) dialog.findViewById(R.id.datePicker);
                    mTvTimeSelected.setText(String.format("%d-%02d-%d", datePicker.getYear(), datePicker.getMonth() + 1, datePicker.getDayOfMonth())
                            + TimeUtil.date2String
                            (new Date(System.currentTimeMillis()), " HH:mm:ss"));
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
        String cellId = mTvCellId.getText().toString();
        if (TextUtils.isEmpty(cellId)){
            UIUtils.showToast("请选择绑定塘口");
            return;
        }

        String richangType = mTvRichangType.getText().toString();
        if (TextUtils.isEmpty(richangType)){
            UIUtils.showToast("请选择日常投入类型");
            return;
        }

        String time = mTvTimeSelected.getText().toString();
        if (TextUtils.isEmpty(time)){
            UIUtils.showToast("请选择投放时间");
            return;
        }

        String total = mEtTotal.getText().toString();
        if (TextUtils.isEmpty(total)){
            UIUtils.showToast("请输入投入量");
            return;
        }

        String richangName = mTvRichangName.getText().toString();
        if (TextUtils.isEmpty(richangName)){
            UIUtils.showToast("请选择名称");
            return;
        }

        String unit = mTvUnit.getText().toString();
        if (TextUtils.isEmpty(unit)){
            UIUtils.showToast("请选择单位");
            return;
        }

        String rizhiContent = mEtRizhiContent.getText().toString();



        if (!NetworkHelper.isNetworkAvailable(this)){
            UIUtils.showToast(getString(R.string.no_net));
            return;
        }else {
            doPondAddCommit(richangType,Integer.valueOf(total),time,rizhiContent,richangName,unit,Integer.valueOf(cellId));
        }
    }


    private void doPondAddCommit(String type, int total,String time,String des,String richangName,String unit,int cell){
        mPresenter.updateRichang(type,total,time,des,richangName,unit,cell,mRichangId);
    }



    @Override
    public void updateRichangSuccess(DailyThrowResponse dailyThrowResponse) {
        RichangUpdateBean richangUpdateBean = new RichangUpdateBean();
        richangUpdateBean.setItemPosition(mItemIndex);
        richangUpdateBean.setDailyThrowResponse(dailyThrowResponse);
        mRxManager.post(AppConstant.RX_UPDATE_POND, richangUpdateBean);
        finish();
    }

    @Override
    public void updateRichangFailure(String error) {
        UIUtils.showToast(error);
    }
}
