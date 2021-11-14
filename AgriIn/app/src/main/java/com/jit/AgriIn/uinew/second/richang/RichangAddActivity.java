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
public class RichangAddActivity extends BaseActivity<RichangAddView,RichangAddPresenter> implements RichangAddView {


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

    @Override
    protected void init() {
        richangTypeList = Arrays.asList(AppConstant.RICHANG_TYPE_ARRAYS);
        unitType.add("斤");
        unitType.add("升");
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_richang_add;
    }

    @Override
    protected RichangAddPresenter createPresenter() {
        return new RichangAddPresenter(this);
    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        mTvToolbarTitle.setText("添加日常投入");
        mTvPublishNow.setVisibility(View.VISIBLE);
        mTvPublishNow.setText(R.string.submit);

        // 初始化时间
        mTvTimeSelected.setText(TimeUtil.date2String
                (new Date(System.currentTimeMillis()), "yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        mIvToolbarNavigation.setOnClickListener(v -> onBackPressed());


        mTvRichangType.setOnClickListener(view -> TypeDialogShow());


        mTvPublishNow.setOnClickListener(v -> requestSubmit());

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
                .itemsCallback((dialog, view, which, text) -> {
                    mTvRichangType.setText(text);
                    if (mTvRichangType.getText().toString().equals("饵料")){
                        mPresenter.getErliaoType();
                    }else if (mTvRichangType.getText().toString().equals("药品")){
                        mPresenter.getMedicineType();
                    }
                })
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
//        if (TextUtils.isEmpty(rizhiContent)){
//            UIUtils.showToast("请输入事件详细内容");
//            return;
//        }



        if (!NetworkHelper.isNetworkAvailable(this)){
            UIUtils.showToast(getString(R.string.no_net));
            return;
        }else {
            doPondAddCommit(richangType,Integer.valueOf(total),time,rizhiContent,richangName,unit,Integer.valueOf(cellId));
        }
    }


    private void doPondAddCommit(String type, int total,String time,String des,String richangName,String unit,int cell) {
        mPresenter.addRichang(type,total,time,des,richangName,unit,cell);
    }

    @Override
    public void addRichangSuccess(DailyThrowResponse dailyThrowResponse) {
        mRxManager.post(AppConstant.RX_ADD_POND,dailyThrowResponse);
        finish();
    }

    @Override
    public void addRichangFailure(String error) {
        UIUtils.showToast(error);
    }

    @Override
    public void getErliaoTypeSuccess(String[] erliaoType) {
        new MaterialDialog.Builder(this)
                .title("投入品名称")
                .items(erliaoType)
                .itemsCallback((dialog, view, which, text) -> {
                    mTvRichangName.setText(text);
                })
                .positiveText(android.R.string.cancel)
                .show();
    }

    @Override
    public void getErliaoTypeFailure(String error) {
        UIUtils.showToast(error);
    }

    @Override
    public void getMedicineTypeSuccess(String[] erliaoType) {
        new MaterialDialog.Builder(this)
                .title("投入品名称")
                .items(erliaoType)
                .itemsCallback((dialog, view, which, text) -> {
                    mTvRichangName.setText(text);
                })
                .positiveText(android.R.string.cancel)
                .show();
    }

    @Override
    public void getMedicineTypeFailure(String error) {
        UIUtils.showToast(error);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){  //根据请求码可处理不同活动返回的数据
            case 1: //返回的请求码
                //操作
                if (data != null){
                    mCellId  = data.getExtras().getInt("cellid");
                    mTvCellId.setText(String.valueOf(mCellId));
                }
                break;
        }
    }
}
