package com.jit.AgriIn.uinew.second.guding;

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
import com.jit.AgriIn.model.bean.GudingUpdateBean;
import com.jit.AgriIn.model.response.IncomeResponse;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.util.NetworkHelper;
import com.zxl.baselib.util.time.TimeUtil;
import com.zxl.baselib.util.ui.UIUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

/**
 * @author zxl on 2018/08/29.
 *         discription: 新增塘口配置
 */
public class GudingUpdateActivity extends BaseActivity<GudingUpdateView,GudingUpdatePresenter> implements GudingUpdateView {


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
    @BindView(R.id.etGudingPrice)
    EditText mEtGudingPrice;
    @BindView(R.id.etDanjia)
    EditText mEtDanjia;

    @BindView(R.id.tvTimeSelected)
    TextView mTvTimeSelected;
    @BindView(R.id.etDescription)
    EditText mEtRizhiContent;

    @BindView(R.id.ll_miao)
    LinearLayout mLlMiao;
    @BindView(R.id.tvbreedType)
    TextView mTvBreedType;
    @BindView(R.id.llCount)
    LinearLayout mLlCount;
    @BindView(R.id.etCount)
    EditText mEtCount;
    @BindView(R.id.tvUnit)
    TextView mTvUnit;

    @BindView(R.id.ll_count_type)
    LinearLayout mLlCountType;
    @BindView(R.id.ll_danjia)
    LinearLayout mLlDanjia;
    @BindView(R.id.ll_shuliang)
    LinearLayout mLlShuliang;
    @BindView(R.id.ll_name)
    LinearLayout mLlName;

    @BindView(R.id.tvJilu)
    TextView mTvJilu;
    @BindView(R.id.ll_zonge)
    LinearLayout mLlZonge;

    private List<String> breedList = new ArrayList<>();
    private List<String> unitType = new ArrayList<>();

    private IncomeResponse mIncomeResponse;
    private String mGudingName;
    private int mGudingPrice;
    private String mGudingRemark;
    private String mRecordTime;
    private int mItemIndex;
    private int mGudingId;
    private boolean isFromPondMain = false;

    private List<String> jisuanType = new ArrayList<>();

    @Override
    protected void init() {
        jisuanType.add("单价");
        jisuanType.add("总额");
        breedList.add("鱼苗");
        breedList.add("虾苗");
        breedList.add("蟹苗");

        unitType.add("斤");
        unitType.add("只");

        if (getIntent() != null){
            mIncomeResponse = (IncomeResponse) getIntent().getExtras().getSerializable(AppConstant.BUNDLE_POND_BEAN);
            mGudingName = mIncomeResponse.getName();
            mGudingPrice = mIncomeResponse.getTotal();
            mGudingId = mIncomeResponse.getId();
            if (mIncomeResponse.getRemark() != null){
                mGudingRemark = mIncomeResponse.getRemark();
            }
            mRecordTime = mIncomeResponse.getSysTime();
            mItemIndex = getIntent().getIntExtra(AppConstant.EXTRA_ITEM_INDEX,-1);
            isFromPondMain = getIntent().getBooleanExtra(AppConstant.EXTRA_IS_FROM_POND_MAIN,false);
        }
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_guding_add;
    }

    @Override
    protected GudingUpdatePresenter createPresenter() {
        return new GudingUpdatePresenter(this);
    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        mLlCountType.setVisibility(View.GONE);
        mLlDanjia.setVisibility(View.GONE);
        mLlShuliang.setVisibility(View.GONE);
        mLlName.setVisibility(View.GONE);

        mTvToolbarTitle.setText("修改固定成本");
        mTvPublishNow.setVisibility(View.VISIBLE);
        mTvPublishNow.setText(R.string.submit);

        // 初始化时间
//        mTvTimeSelected.setText(TimeUtil.date2String
//                (new Date(System.currentTimeMillis()), "yyyy-MM-dd HH:mm:ss"));

        mTvGudingType.setText(mGudingName);
        if (mGudingRemark != null){
            mEtRizhiContent.setText(mGudingRemark);
        }
        mTvTimeSelected.setText(mRecordTime);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        mIvToolbarNavigation.setOnClickListener(v -> onBackPressed());


        mTvPublishNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestSubmit();
            }
        });

        mTvGudingType.setOnClickListener(view -> mPresenter.getGudingType());

        mTvTimeSelected.setOnClickListener(view -> showCustomDatePicker());

        mTvBreedType.setOnClickListener(view -> new MaterialDialog.Builder(mContext)
                .title("种苗类型")
                .items(breedList)
                .itemsCallback((dialog, v, which, text) -> mTvBreedType.setText(text))
                .positiveText(android.R.string.cancel)
                .show());

        mTvUnit.setOnClickListener(view -> new MaterialDialog.Builder(mContext)
                .title("单位")
                .items(unitType)
                .itemsCallback((dialog, v, which, text) -> mTvUnit.setText(text))
                .positiveText(android.R.string.cancel)
                .show());


        mLlCountType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(mContext)
                        .title("记录方式")
                        .items(jisuanType)
                        .itemsCallback((dialog, v, which, text) -> {
                            mTvJilu.setText(text);
                            if (mTvJilu.getText().toString().equals("单价")){
                                mLlDanjia.setVisibility(View.VISIBLE);
                                mLlShuliang.setVisibility(View.VISIBLE);
                                mLlZonge.setVisibility(View.GONE);
                            }else if (mTvJilu.getText().toString().equals("总额")){
                                mLlDanjia.setVisibility(View.GONE);
                                mLlShuliang.setVisibility(View.GONE);
                                mLlZonge.setVisibility(View.VISIBLE);
                            }
                        })
                        .positiveText(android.R.string.cancel)
                        .show();
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
        String gudingType = mTvGudingType.getText().toString();
        if (TextUtils.isEmpty(gudingType)){
            UIUtils.showToast("请选择固定投入类型");
            return;
        }

        String breedType = mTvBreedType.getText().toString();
        String count = mEtCount.getText().toString();
        String unit = mTvUnit.getText().toString();
        String danjia = mEtDanjia.getText().toString();

        String gudingPrice = mEtGudingPrice.getText().toString();
        if (gudingType.equals("苗种")){
            if (TextUtils.isEmpty(breedType)){
                UIUtils.showToast("请选择苗种类型");
                return;
            }
            if (TextUtils.isEmpty(count)){
                UIUtils.showToast("请填写数量");
                return;
            }
            if (TextUtils.isEmpty(unit)){
                UIUtils.showToast("请选择单位");
                return;
            }
            if (TextUtils.isEmpty(danjia)){
                UIUtils.showToast("请填写单价");
                return;
            }
        }else{
            if(mTvJilu.getText().toString().equals("单价")){
                if (TextUtils.isEmpty(count)){
                    UIUtils.showToast("请填写数量");
                    return;
                }
                if (TextUtils.isEmpty(unit)){
                    UIUtils.showToast("请选择单位");
                    return;
                }
                if (TextUtils.isEmpty(danjia)){
                    UIUtils.showToast("请填写单价");
                    return;
                }
            }else if (mTvJilu.getText().toString().equals("总额")){
                if (TextUtils.isEmpty(gudingPrice)){
                    UIUtils.showToast("请输入投入品金额");
                    return;
                }
            }
        }

        String time = mTvTimeSelected.getText().toString();
        if (TextUtils.isEmpty(time)){
            UIUtils.showToast("请选择投放时间");
            return;
        }



        String rizhiContent = mEtRizhiContent.getText().toString();


        if (!NetworkHelper.isNetworkAvailable(this)){
            UIUtils.showToast(getString(R.string.no_net));
            return;
        }else {
            if (gudingType.equals("苗种")){
                doPondAddCommit(breedType,Integer.valueOf(danjia),time,rizhiContent,count,unit);
            }else {
                doPondAddCommit(gudingType,Integer.valueOf(gudingPrice),time,rizhiContent,count,unit);
            }
        }
    }


    private void doPondAddCommit(String gudingName, int gudingPrice,String time,String rizhiContent,String count,String unit) {
        mPresenter.updateIncome(0,gudingName,gudingPrice,time,rizhiContent,count,unit,mGudingId);
    }



    @Override
    public void updateGudingSuccess(IncomeResponse incomeResponse) {
            GudingUpdateBean gudingUpdateBean = new GudingUpdateBean();
            gudingUpdateBean.setItemPosition(mItemIndex);
            gudingUpdateBean.setIncomeResponse(incomeResponse);
            mRxManager.post(AppConstant.RX_UPDATE_POND, gudingUpdateBean);
            finish();
    }

    @Override
    public void updateGudingFailure(String error) {
        UIUtils.showToast(error);
    }

    @Override
    public void getGudingTypeSuccess(String[] gudingType) {
        //点击回调
        new MaterialDialog.Builder(this)
                .title("固定投入类型")
                .items(gudingType)
                .itemsCallback((dialog, itemView, position, text) -> {
                    mTvGudingType.setText(text);
                    if (mTvGudingType.getText().equals("苗种")){
                        mLlMiao.setVisibility(View.VISIBLE);


                        mLlCountType.setVisibility(View.GONE);
                        mLlDanjia.setVisibility(View.VISIBLE);
                        mLlShuliang.setVisibility(View.VISIBLE);
                        mLlZonge.setVisibility(View.GONE);
                    }else {
                        mLlMiao.setVisibility(View.GONE);

                        mLlCountType.setVisibility(View.GONE);
                        mTvBreedType.setText("");
                        mEtCount.setText("");
                        mTvUnit.setText("");
                    }
                })
                .positiveText(android.R.string.cancel)
                .show();
    }

    @Override
    public void getGudingTypeFailure(String error) {
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
}
