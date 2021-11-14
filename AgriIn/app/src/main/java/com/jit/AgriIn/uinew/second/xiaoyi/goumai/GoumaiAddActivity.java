package com.jit.AgriIn.uinew.second.xiaoyi.goumai;

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
import com.jit.AgriIn.model.response.IncomeResponse;
import com.jit.AgriIn.uinew.second.guding.GudingAddPresenter;
import com.jit.AgriIn.uinew.second.guding.GudingAddView;
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
public class GoumaiAddActivity extends BaseActivity<GudingAddView,GudingAddPresenter> implements GudingAddView {


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

    @BindView(R.id.tvGoumaiType)
    TextView tvGoumaiType;
    @BindView(R.id.tvGoumaiTotal)
    TextView tvGoumaiTotal;

    @BindView(R.id.tvTimeSelected)
    TextView mTvTimeSelected;
    @BindView(R.id.etDescription)
    EditText mEtRizhiContent;

    @BindView(R.id.ll_count_type)
    LinearLayout mLlCountType;
    @BindView(R.id.tvJilu)
    TextView mTvJilu;
    @BindView(R.id.ll_danjia)
    LinearLayout mLlDanjia;
    @BindView(R.id.ll_shuliang)
    LinearLayout mLlShuliang;
    @BindView(R.id.ll_zonge)
    LinearLayout mLlZonge;

    @BindView(R.id.tvRichangName)
    TextView mTvRichangName;

    @BindView(R.id.etDanjia)
    EditText mEtDanjia;
    @BindView(R.id.etshuliang)
    EditText mEtShuliang;
    @BindView(R.id.tvDanwei)
    TextView mTvDanwei;

    private List<String> unitType = new ArrayList<>();
    private List<String> jisuanType = new ArrayList<>();



    @Override
    protected void init() {
        jisuanType.add("单价");
        jisuanType.add("总额");

        unitType.add("斤");
        unitType.add("袋");
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_guding_add;
    }

    @Override
    protected GudingAddPresenter createPresenter() {
        return new GudingAddPresenter(this);
    }


    @Override
    protected void initView(Bundle savedInstanceState) {

        mLlDanjia.setVisibility(View.GONE);
        mLlShuliang.setVisibility(View.GONE);
        mLlZonge.setVisibility(View.GONE);

        mTvToolbarTitle.setText("添加购买金额");
        mTvPublishNow.setVisibility(View.VISIBLE);
        mTvPublishNow.setText(R.string.submit);


        tvGoumaiType.setText("购买类型");
        tvGoumaiTotal.setText("购买金额");
        mTvGudingType.setHint("请选择购买类型");
        mEtGudingPrice.setHint("请输入购买金额");

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

        mTvGudingType.setOnClickListener(view -> mPresenter.getGoumaiType());

        mTvTimeSelected.setOnClickListener(view -> showCustomDatePicker());

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

        mTvDanwei.setOnClickListener(view -> new MaterialDialog.Builder(mContext)
                .title("单位")
                .items(unitType)
                .itemsCallback((dialog, v, which, text) -> mTvDanwei.setText(text))
                .positiveText(android.R.string.cancel)
                .show());
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
            UIUtils.showToast("请选择购买物品类型");
            return;
        }

        String price = "";
        String gudingPrice = mEtGudingPrice.getText().toString();
        String danjiaPrice = mEtDanjia.getText().toString();
        String shuliang = mEtShuliang.getText().toString();
        String unit = mTvDanwei.getText().toString();
        if (mTvJilu.getText().toString().equals("单价")){
            if (TextUtils.isEmpty(danjiaPrice)){
                UIUtils.showToast("请输入单价");
                return;
            }
            if (TextUtils.isEmpty(shuliang)){
                UIUtils.showToast("请输入数量");
                return;
            }
            if (TextUtils.isEmpty(unit)){
                UIUtils.showToast("请选择单位");
                return;
            }
            price = danjiaPrice;
        }else if (mTvJilu.getText().toString().equals("总额")){
            if (TextUtils.isEmpty(gudingPrice)){
                UIUtils.showToast("请输入购买金额");
                return;
            }
            price = gudingPrice;
        }

        if (price.isEmpty()){
            UIUtils.showToast("请输入购买金额");
            return;
        }


        String time = mTvTimeSelected.getText().toString();
        if (TextUtils.isEmpty(time)){
            UIUtils.showToast("请选择投放时间");
            return;
        }

        String richangName = mTvRichangName.getText().toString();
        if (TextUtils.isEmpty(richangName)){
            UIUtils.showToast("请选择名称");
            return;
        }


        String rizhiContent = mEtRizhiContent.getText().toString();



        if (!NetworkHelper.isNetworkAvailable(this)){
            UIUtils.showToast(getString(R.string.no_net));
            return;
        }else {

            doPondAddCommit(richangName,Integer.valueOf(price),time,rizhiContent,shuliang,unit);
        }
    }


    private void doPondAddCommit(String name, int gudingPrice,String time,String des,String count,String unit) {
        mPresenter.addIncome(1,name,gudingPrice,time,des,count,unit);
    }


    @Override
    public void addGudingSuccess(IncomeResponse incomeResponse) {
        mRxManager.post(AppConstant.RX_ADD_POND,incomeResponse);
        finish();
    }

    @Override
    public void addGudingFailure(String error) {
        UIUtils.showToast(error);
    }

    @Override
    public void getGudingTypeSuccess(String[] gudingType) {
        new MaterialDialog.Builder(this)
                .title("购买物品类型")
                .items(gudingType)
                .itemsCallback((dialog, view, which, text) ->
                {
                    mTvGudingType.setText(text);
                    if (mTvGudingType.getText().toString().equals("饵料")){
                        mPresenter.getErliaoType();
                    }else if (mTvGudingType.getText().toString().equals("水质改良剂")){
                        mPresenter.getMedicineType();
                    }else {
                        mTvRichangName.setText(text);
                    }

                })
                .positiveText(android.R.string.cancel)
                .show();
    }

    @Override
    public void getGudingTypeFailure(String error) {
        UIUtils.showToast(error);
    }

    @Override
    public void getErliaoTypeSuccess(String[] erliaoType) {
        new MaterialDialog.Builder(this)
                .title("物品名称")
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
                .title("物品名称")
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
