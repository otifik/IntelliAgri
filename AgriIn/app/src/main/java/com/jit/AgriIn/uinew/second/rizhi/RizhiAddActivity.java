package com.jit.AgriIn.uinew.second.rizhi;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jit.AgriIn.R;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.model.cache.UserCache;
import com.jit.AgriIn.model.response.RizhiResponse;
import com.jit.AgriIn.uinew.role_admin.CellChooseActivity;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.util.NetworkHelper;
import com.zxl.baselib.util.time.TimeUtil;
import com.zxl.baselib.util.ui.UIUtils;

import java.util.Date;

import butterknife.BindView;

/**
 * @author zxl on 2018/08/29.
 *         discription: 新增塘口配置
 */
public class RizhiAddActivity extends BaseActivity<RizhiAddView,RizhiAddPresenter> implements RizhiAddView {


    @BindView(R.id.ivToolbarNavigation)
    ImageView mIvToolbarNavigation;
    @BindView(R.id.vToolbarDivision)
    View mVToolbarDivision;
    @BindView(R.id.tvToolbarTitle)
    TextView mTvToolbarTitle;
    @BindView(R.id.tv_publish_now)
    TextView mTvPublishNow;
    @BindView(R.id.ll_pond)
    LinearLayout mLlPond;
    @BindView(R.id.tvGudingType)
    TextView mTvGudingType;
    @BindView(R.id.etDescription)
    EditText mEtRizhiContent;
    @BindView(R.id.tvTimeSelected)
    TextView mTvTimeSelected;

    @BindView(R.id.tvCellId)
    TextView mTvCellId;
    int mCellId;

    @Override
    protected void init() {

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_rizhi_add;
    }

    @Override
    protected RizhiAddPresenter createPresenter() {
        return new RizhiAddPresenter(this);
    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        mTvToolbarTitle.setText("添加种养日志");
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


        mTvPublishNow.setOnClickListener(v -> requestSubmit());

        mTvGudingType.setOnClickListener(view -> mPresenter.getObserveType());

        mTvTimeSelected.setOnClickListener(view -> showCustomDatePicker());

        mTvCellId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, CellChooseActivity.class);
                intent.putExtra("username", UserCache.getUserUsername());
                startActivityForResult(intent, 1);//带请求码打开activity (请求码自定 这里设为1
            }
        });
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

        String gudingType = mTvGudingType.getText().toString();
        if (TextUtils.isEmpty(gudingType)){
            UIUtils.showToast("请选择巡视事件类型");
            return;
        }

        String rizhiContent = mEtRizhiContent.getText().toString();
//        if (TextUtils.isEmpty(rizhiContent)){
//            UIUtils.showToast("请输入事件详细内容");
//            return;
//        }

        String time = mTvTimeSelected.getText().toString();
        if (TextUtils.isEmpty(time)){
            UIUtils.showToast("请选择投放时间");
            return;
        }



        if (!NetworkHelper.isNetworkAvailable(this)){
            UIUtils.showToast(getString(R.string.no_net));
            return;
        }else {
            doPondAddCommit(gudingType,rizhiContent,time,Integer.valueOf(cellId));
        }
    }


    private void doPondAddCommit(String rizhiName, String rizhiContent,String time,int mCellId) {
        mPresenter.addRizhi(rizhiName,rizhiContent,time,mCellId);
    }

    @Override
    public void addRizhiSuccess(RizhiResponse rizhiResponse) {
        mRxManager.post(AppConstant.RX_ADD_POND,rizhiResponse);
        finish();
    }

    @Override
    public void addRizhiFailure(String error) {
        UIUtils.showToast(error);
    }

    @Override
    public void getRizhiTypeSuccess(String[] gudingType) {
        new MaterialDialog.Builder(this)
                .title("巡视事件类型")
                .items(gudingType)
                .itemsCallback((dialog, view, which, text) -> mTvGudingType.setText(text))
                .positiveText(android.R.string.cancel)
                .show();
    }

    @Override
    public void getRizhiTypeFailure(String error) {
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
                    mTvTimeSelected.setText(String.format("%d-%02d-%d", datePicker.getYear(), datePicker.getMonth() + 1, datePicker.getDayOfMonth())
                            + TimeUtil.date2String
                            (new Date(System.currentTimeMillis()), " HH:mm:ss"));
                })
                .show();
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
