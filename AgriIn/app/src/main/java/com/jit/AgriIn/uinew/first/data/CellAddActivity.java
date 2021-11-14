package com.jit.AgriIn.uinew.first.data;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jit.AgriIn.R;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.model.cache.UserCache;
import com.jit.AgriIn.model.response.CellProTypeResponse;
import com.jit.AgriIn.model.response.CellResponse;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.util.NetworkHelper;
import com.zxl.baselib.util.ui.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author zxl on 2018/08/29.
 *         discription: 新增生产单元配置
 */
public class CellAddActivity extends BaseActivity<CellAddView, CellAddPresenter> implements CellAddView {


    @BindView(R.id.ivToolbarNavigation)
    ImageView mIvToolbarNavigation;
    @BindView(R.id.vToolbarDivision)
    View mVToolbarDivision;
    @BindView(R.id.tvToolbarTitle)
    TextView mTvToolbarTitle;
    @BindView(R.id.tv_publish_now)
    TextView mTvPublishNow;


    @BindView(R.id.etName)
    EditText mEtName;
    @BindView(R.id.etLength)
    EditText mEtLength;
    @BindView(R.id.etWidth)
    EditText mEtWidth;
    @BindView(R.id.etLongitude)
    EditText mEtLongitude;
    @BindView(R.id.etLatitude)
    EditText mEtLatitude;

    @BindView(R.id.tvCellProType)
    TextView mTvCellProType;
    @BindView(R.id.tvCellPro)
    TextView mTvCellPro;


    private List<String> mProTypeList = new ArrayList<>();
    int mPos = 0;


    private LocationManager locationManager;
    private String locationProvider;       //位置提供器

    @Override
    protected void init() {

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_cell_add;
    }

    @Override
    protected CellAddPresenter createPresenter() {
        return new CellAddPresenter(this);
    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        mTvToolbarTitle.setText("添加生产单元");
        mTvPublishNow.setVisibility(View.VISIBLE);
        mTvPublishNow.setText(R.string.submit);

        getLocation(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        mIvToolbarNavigation.setOnClickListener(v -> onBackPressed());


        mTvPublishNow.setOnClickListener(v -> requestSubmit());

        mTvCellProType.setOnClickListener(view -> mPresenter.getCellProType());


//        mTvCellPro.setOnClickListener(view -> mPresenter.getCellPro());
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

        String name = mEtName.getText().toString();
        if (TextUtils.isEmpty(name)) {
            UIUtils.showToast("请填写生产单元名称");
            return;
        }

        String length = mEtLength.getText().toString();
        if (TextUtils.isEmpty(length)) {
            UIUtils.showToast("请填写生产单元长度");
            return;
        }

        String width = mEtWidth.getText().toString();
        if (TextUtils.isEmpty(width)) {
            UIUtils.showToast("请填写生产单元宽度");
            return;
        }

        String longitude = mEtLongitude.getText().toString();
        if (TextUtils.isEmpty(longitude)) {
            UIUtils.showToast("请填写生产单元经度");
            return;
        }

        String latitude = mEtLatitude.getText().toString();
        if (TextUtils.isEmpty(latitude)) {
            UIUtils.showToast("请填写生产单元纬度");
            return;
        }

        String protype = mTvCellProType.getText().toString();
        if (TextUtils.isEmpty(protype)) {
            UIUtils.showToast("请选择生产单元类别");
            return;
        }

        String pro = mTvCellPro.getText().toString();
        if (TextUtils.isEmpty(pro)) {
            UIUtils.showToast("请选择产品名称");
            return;
        }


        if (!NetworkHelper.isNetworkAvailable(this)) {
            UIUtils.showToast(getString(R.string.no_net));
            return;
        } else {
            doPondAddCommit(Double.valueOf(length), Double.valueOf(width), Double.valueOf(longitude), Double.valueOf(latitude), protype, pro, name);
        }
    }


    private void doPondAddCommit(double length, double width, double longitude, double latitude, String product, String pro, String cellname) {
        mPresenter.addCell(length, width, longitude, latitude, product, pro, cellname, UserCache.getUserUsername());
    }


    @Override
    public void addCellSuccess(CellResponse cellResponse) {
        if (cellResponse != null) {
            mRxManager.post(AppConstant.RX_ADD_POND, cellResponse);
        } else {
            UIUtils.showToast("请勿重复添加");
        }

        finish();
    }

    @Override
    public void addCellFailure(String error) {
        UIUtils.showToast(error);
    }

    @Override
    public void getCellProTypeSuccess(List<CellProTypeResponse> cellProTypeResponseList) {
        mProTypeList.clear();
        for (int i = 0; i < cellProTypeResponseList.size(); i++) {
            mProTypeList.add(cellProTypeResponseList.get(i).getCelltype());
        }


        new MaterialDialog.Builder(this)
                .title("生产单元类型")
                .items(mProTypeList)
                .itemsCallback(new MaterialDialog.ListCallback() { //点击回调
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        mPos = position;
                        mTvCellProType.setText(text);
                        mPresenter.getCellPro(mProTypeList.get(position));
                    }
                })
                .positiveText(android.R.string.cancel)
                .show();
    }


    @Override
    public void getCellProTypeFailure(String error) {
        UIUtils.showToast(error);
    }

    @Override
    public void getCellProSuccess(String[] cellProList) {
        new MaterialDialog.Builder(this)
                .title("产品名称")
                .items(cellProList)
                .itemsCallback((dialog, view, which, text) -> mTvCellPro.setText(text))
                .positiveText(android.R.string.cancel)
                .show();
    }

    @Override
    public void getCellProFailure(String error) {
        UIUtils.showToast(error);
    }

    private void getLocation(Context context) {
        //1.获取位置管理器
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        //2.获取位置提供器，GPS或是NetWork
        List<String> providers = locationManager.getProviders(true);
        if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
            //如果是网络定位
            locationProvider = LocationManager.NETWORK_PROVIDER;
        } else if (providers.contains(LocationManager.GPS_PROVIDER)) {
            //如果是GPS定位
            locationProvider = LocationManager.GPS_PROVIDER;
        } else {
            Toast.makeText(this, "没有可用的位置提供器", Toast.LENGTH_SHORT).show();
            return;
        }

        //3.获取上次的位置，一般第一次运行，此值为null
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            UIUtils.showToast("请打开定位功能");
            return;
        }
        Location location = locationManager.getLastKnownLocation(locationProvider);
        if (location!=null){
            showLocation(location);
        }else{
            // 监视地理位置变化，第二个和第三个参数分别为更新的最短时间minTime和最短距离minDistace
            locationManager.requestLocationUpdates(locationProvider, 0, 0,mListener);
        }
    }


    private void showLocation(Location location){
//        String address = "纬度："+location.getLatitude()+"经度："+location.getLongitude();
//        mLocation.setText(address);
        mEtLongitude.setText(String.valueOf(location.getLongitude()));
        mEtLatitude.setText(String.valueOf(location.getLatitude()));
    }

    LocationListener mListener = new LocationListener() {
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
        @Override
        public void onProviderEnabled(String provider) {
        }
        @Override
        public void onProviderDisabled(String provider) {
        }
        // 如果位置发生变化，重新显示
        @Override
        public void onLocationChanged(Location location) {
            showLocation(location);
        }
    };
}
