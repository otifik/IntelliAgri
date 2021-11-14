package com.jit.AgriIn.ui.activity.config;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jit.AgriIn.R;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.model.bean.JsonBean;
import com.jit.AgriIn.model.response.PondMainResponse;
import com.jit.AgriIn.ui.presenter.config.PondAddAtPresenter;
import com.jit.AgriIn.ui.view.config.PondAddAtView;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.util.NetworkHelper;
import com.zxl.baselib.util.ui.UIUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * @author zxl on 2018/08/29.
 *         discription: 新增生产单元配置
 */
public class PondAddActivity extends BaseActivity<PondAddAtView,PondAddAtPresenter> implements PondAddAtView {


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
    @BindView(R.id.etPondName)
    EditText mEtPondName;
    @BindView(R.id.etPondLength)
    EditText mEtPondLength;
    @BindView(R.id.etPondWidth)
    EditText mEtPondWidth;
    @BindView(R.id.etPondDepth)
    EditText mEtPondDepth;
    @BindView(R.id.etMaxPondDepth)
    EditText mEtMaxPondDepth;
    @BindView(R.id.tvPondAddress)
    TextView mTvPondAddress;
    @BindView(R.id.llPondAddress)
    LinearLayout mLlPondAddress;
    @BindView(R.id.etDetailAddress)
    EditText mEtDetailAddress;
    @BindView(R.id.tvInputDirection)
    TextView mTvInputDirection;
    @BindView(R.id.llPondDirection)
    LinearLayout mLlPondDirection;
    @BindView(R.id.tvPondLatitude)
    TextView mTvPondLatitude;
    @BindView(R.id.llPondLatitude)
    LinearLayout mLlPondLatitude;
    private List<String> directionList = new ArrayList<>();
    private String mProvince;
    private String mCity;
    private String mDistrict;
    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private boolean isLoaded = false;

    private float eSLongitude = AppConstant.LATITUDE_DEFAULT;
    private float eSLatitude = AppConstant.LATITUDE_DEFAULT;
    private float eNLongitude = AppConstant.LATITUDE_DEFAULT;
    private float eNLatitude = AppConstant.LATITUDE_DEFAULT;
    private float wSLongitude = AppConstant.LATITUDE_DEFAULT;
    private float wSLatitude = AppConstant.LATITUDE_DEFAULT;
    private float wNLongitude = AppConstant.LATITUDE_DEFAULT;
    private float wNLatitude = AppConstant.LATITUDE_DEFAULT;
    @Override
    protected void init() {
        directionList = Arrays.asList(AppConstant.DIRECTION_ARRAYS);
                /* 很好使用了rxjava来进行加载了*/

/*
            暂时不包含省市区-----
            Observable.create(this::initJsonData).observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> isLoaded = aBoolean);*/
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_pond_info_add;
    }

    @Override
    protected PondAddAtPresenter createPresenter() {
        return new PondAddAtPresenter(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mTvToolbarTitle.setText(R.string.title_add_pond_config);
        mTvPublishNow.setVisibility(View.VISIBLE);
        mTvPublishNow.setText(R.string.submit);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        mIvToolbarNavigation.setOnClickListener(v -> onBackPressed());

        /*跳转向设置各个方向角的经纬度 带返回*/
        mLlPondLatitude.setOnClickListener(v -> jumpToActivityForResult(LatitudesActivity.class,AppConstant.RECODE_ADD_LATITUDE));

/*        mLlPondAddress.setOnClickListener((View v) -> {
            if (isLoaded) {
                showPickerView();
            } else {
                UIUtils.showToast(getString(R.string.error_for_load_city));
            }
        });*/

        mLlPondDirection.setOnClickListener(v -> directionDialogShow());

        mTvPublishNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestSubmit();
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
        String pondName = mEtPondName.getText().toString();
        if (TextUtils.isEmpty(pondName)){
            UIUtils.showToast(getString(R.string.input_pond_name));
            return;
        }

        String pondLength = mEtPondLength.getText().toString();
        if (TextUtils.isEmpty(pondLength)){
            UIUtils.showToast(getString(R.string.input_pond_length));
            return;
        }

        String pondWidth = mEtPondWidth.getText().toString();
        if (TextUtils.isEmpty(pondWidth)){
            UIUtils.showToast(getString(R.string.input_pond_width));
            return;
        }

/*
        String pondAddress = mTvPondAddress.getText().toString();
        if (TextUtils.isEmpty(pondAddress)){
            UIUtils.showToast(getString(R.string.input_pond_address));
            return;
        }
        */

        String pondDetailAddress = mEtDetailAddress.getText().toString();
        if (TextUtils.isEmpty(pondDetailAddress)){
            UIUtils.showToast(getString(R.string.input_pond_detail_address));
            return;
        }

        String pondDirection = mTvInputDirection.getText().toString();
        if (TextUtils.isEmpty(pondDirection)){
            UIUtils.showToast(getString(R.string.choose_pond_direction));
            return;
        }

        String pondDepth = mEtPondDepth.getText().toString();
        if (TextUtils.isEmpty(pondDepth)){
            UIUtils.showToast(getString(R.string.input_pond_depth));
            return;
        }

        String pondMaxDepth = mEtMaxPondDepth.getText().toString();
        if (TextUtils.isEmpty(pondMaxDepth)){
            UIUtils.showToast(getString(R.string.input_pond_max_depth));
            return;
        }

        String tvLatitude = mTvPondLatitude.getText().toString();
        if (TextUtils.isEmpty(tvLatitude)){
            UIUtils.showToast(getString(R.string.input_pond_attitude));
            return;
        }

        if (!NetworkHelper.isNetworkAvailable(this)){
            UIUtils.showToast(getString(R.string.no_net));
            return;
        }else {
            doPondAddCommit(pondName,Float.parseFloat(pondWidth),Float.parseFloat(pondLength),Float.parseFloat(pondDepth),
                    Float.parseFloat(pondMaxDepth),pondDetailAddress,pondDirection);
        }
    }


    private void doPondAddCommit(String pondName, float width, float length, float depth, float maxDepth, String pondDetailAddress,String pondDirection) {
        mPresenter.addPond(eNLatitude,eSLatitude,wNLatitude,wSLatitude,
                eNLongitude,eSLongitude,wNLongitude,wSLongitude,
                width,length,depth,maxDepth,pondDetailAddress,pondName,pondDirection
                );
    }


    public void directionDialogShow() {
        new MaterialDialog.Builder(this)
                .title(R.string.pond_direction)
                .items(directionList)
                .itemsCallback((dialog, view, which, text) -> mTvInputDirection.setText(text))
                .positiveText(android.R.string.cancel)
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            switch (requestCode){
                case AppConstant.RECODE_ADD_LATITUDE:
                    eSLongitude =  data.getFloatExtra(AppConstant.DirectionExtra.LONG_E_S.name(),
                            AppConstant.LATITUDE_DEFAULT);
                    eNLongitude = data.getFloatExtra(AppConstant.DirectionExtra.LONG_E_N.name(),AppConstant.LATITUDE_DEFAULT);
                    wNLongitude = data.getFloatExtra(AppConstant.DirectionExtra.LONG_W_N.name(),AppConstant.LATITUDE_DEFAULT);
                    wSLongitude = data.getFloatExtra(AppConstant.DirectionExtra.LONG_W_S.name(),AppConstant.LATITUDE_DEFAULT);
                    eSLatitude = data.getFloatExtra(AppConstant.DirectionExtra.LAT_E_S.name(),AppConstant.LATITUDE_DEFAULT);
                    eNLatitude = data.getFloatExtra(AppConstant.DirectionExtra.LAT_E_N.name(),AppConstant.LATITUDE_DEFAULT);
                    wSLatitude = data.getFloatExtra(AppConstant.DirectionExtra.LAT_W_S.name(),AppConstant.LATITUDE_DEFAULT);
                    wNLatitude = data.getFloatExtra(AppConstant.DirectionExtra.LAT_W_N.name(),AppConstant.LATITUDE_DEFAULT);
                    mTvPondLatitude.setText(getString(R.string.latitude_format_total,
                            String.valueOf(eNLongitude), String.valueOf(eNLatitude),
                            String.valueOf(eSLongitude),String.valueOf(eSLatitude),
                            String.valueOf(wSLongitude),String.valueOf(wSLatitude),
                            String.valueOf(wNLongitude),String.valueOf(wNLatitude)));
                    break;
                default:
                    break;
            }
        }
    }

/*    private void showPickerView() {
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(mContext, (options1, options2, options3, v) -> {
            //返回的分别是三个级别的选中位置
            mProvince = options1Items.get(options1).getPickerViewText();
            mCity = options2Items.get(options1).get(options2);
            mDistrict = options3Items.get(options1).get(options2).get(options3);
            mTvPondAddress.setText(getString(R.string.str_format_city_bond, mProvince, mCity, mDistrict));
        })
                .setTitleText(getString(R.string.str_pk_title_city))
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .setOutSideCancelable(false)// default is true
                .build();
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }
    private void initJsonData(ObservableEmitter<Boolean> emitter) {//解析数据

        *//**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * *//*
        String JsonData = new GetJsonDataUtil().getJson(mContext, "province.json");//获取assets目录下的json文件数据

        ArrayList<JsonBean> jsonBean = parseData(JsonData, emitter);//用Gson 转成实体

        *//**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         *//*
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> mProvince_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市

                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {

                    for (int d = 0; d < jsonBean.get(i).getCityList().get(c).getArea().size(); d++) {//该城市对应地区所有数据
                        String AreaName = jsonBean.get(i).getCityList().get(c).getArea().get(d);

                        City_AreaList.add(AreaName);//添加该城市所有地区数据
                    }
                }
                mProvince_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            *//**
             * 添加城市数据
             *//*
            options2Items.add(CityList);

            *//**
             * 添加地区数据
             *//*
            options3Items.add(mProvince_AreaList);
        }

        emitter.onNext(true);
        emitter.onComplete();

    }
    public ArrayList<JsonBean> parseData(String result, ObservableEmitter<Boolean> emitter) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            emitter.onNext(false);
            emitter.onComplete();
        }
        return detail;
    }*/
    

    @Override
    public void addPondSuccess(PondMainResponse response) {
        mRxManager.post(AppConstant.RX_ADD_POND,response);
        finish();
    }

    @Override
    public void addPondFailure(String error) {
        UIUtils.showToast(error);
    }
}
