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
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jit.AgriIn.R;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.model.response.FishPondResponse;
import com.zxl.baselib.bean.response.BaseResponse;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.util.NetworkHelper;
import com.zxl.baselib.util.ui.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class FishPondAddActivity extends BaseActivity<FishPondAddView,FishPondAddPresenter> implements FishPondAddView {

    @BindView(R.id.ivToolbarNavigation)
    ImageView mIvToolbarNavigation;
    @BindView(R.id.vToolbarDivision)
    View mVToolbarDivision;
    @BindView(R.id.tvToolbarTitle)
    TextView mTvToolbarTitle;
    @BindView(R.id.tv_publish_now)
    TextView mTvPublishNow;

    @BindView(R.id.etFishPondName)
    EditText mEtFishPondName;
    @BindView(R.id.etFishPondLength)
    EditText mEtFishPondLength;
    @BindView(R.id.etFishPondWidth)
    EditText mEtFishPondWidth;
    @BindView(R.id.etFishPondDepth)
    EditText mEtFishPondDepth;
    @BindView(R.id.orientation)
    TextView mOrientation;
    @BindView(R.id.etFishPondLongitude)
    EditText mEtFishPondLongitude;
    @BindView(R.id.etFishPondLatitude)
    EditText mEtFishPondLatitude;
    @BindView(R.id.etFishPondAddress)
    EditText mEtFishPondAddress;
    @BindView(R.id.tvFishPondProduct)
    TextView mTvFishPondProduct;

    private LocationManager locationManager;
    private String locationProvider;       //位置提供器
    private ArrayList<String> orientations = new ArrayList();

    @Override
    protected void init() {

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_fish_pond_add;
    }

    @Override
    protected FishPondAddPresenter createPresenter() {
        return new FishPondAddPresenter(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mTvToolbarTitle.setText("添加塘口");
        mTvPublishNow.setVisibility(View.VISIBLE);
        mTvPublishNow.setText(R.string.submit);

        getLocation(this);
    }

    @Override
    protected void initData() {
        orientations.clear();
        orientations.add("东-西");
        orientations.add("南-北");
    }

    @Override
    protected void initListener() {
        mIvToolbarNavigation.setOnClickListener(v -> onBackPressed());

        mTvPublishNow.setOnClickListener(v -> requestSubmit());

        mOrientation.setOnClickListener(v ->
                new MaterialDialog.Builder(this)
                .title("朝向")
                .items(orientations)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        mOrientation.setText(text);
                    }
                })
                .positiveText(android.R.string.cancel)
                .show()
        );
    }
    private void requestSubmit() {
        checkFormatData();
    }
    private void checkFormatData(){

        String pondName = mEtFishPondName.getText().toString();
        if (TextUtils.isEmpty(pondName)) {
            UIUtils.showToast("请填写塘口名称");
            return;
        }

        String length = mEtFishPondLength.getText().toString();
        if (TextUtils.isEmpty(length)) {
            UIUtils.showToast("请填写塘口长度");
            return;
        }

        String width = mEtFishPondWidth.getText().toString();
        if (TextUtils.isEmpty(width)) {
            UIUtils.showToast("请填写塘口长度");
            return;
        }

        String depth = mEtFishPondDepth.getText().toString();
        if (TextUtils.isEmpty(depth)) {
            UIUtils.showToast("请填写塘口长度");
            return;
        }

        String orientation = mOrientation.getText().toString();
        if(TextUtils.isEmpty(orientation)){
            UIUtils.showToast("请填写塘口朝向");
            return;
        }

        String longitude = mEtFishPondLongitude.getText().toString();
        if (TextUtils.isEmpty(longitude)) {
            UIUtils.showToast("请填写塘口经度");
            return;
        }

        String latitude = mEtFishPondLatitude.getText().toString();
        if (TextUtils.isEmpty(latitude)) {
            UIUtils.showToast("请填写塘口纬度");
            return;
        }

        String address = mEtFishPondAddress.getText().toString();
        if(TextUtils.isEmpty(address)){
            UIUtils.showToast("请填写地址");
            return;
        }

        String product = mTvFishPondProduct.getText().toString();
        if (TextUtils.isEmpty(product)){
            UIUtils.showToast("请填写产品");
            return;
        }

        if(!NetworkHelper.isNetworkAvailable(this)){
            UIUtils.showToast(getString(R.string.no_net));
            return;
        }else {
            doFishPondAddCommit(pondName,Double.parseDouble(length),Double.parseDouble(width),Double.parseDouble(depth),orientation,Double.parseDouble(longitude),Double.parseDouble(latitude),address,product);
        }
    }

    private void doFishPondAddCommit(String pondName, double length, double width, double depth, String orientation, double longitude, double latitude, String address,String product) {
        mPresenter.addFishPond(pondName,length,width,depth,orientation,longitude,latitude,address,product);
    }


    @Override
    public void addFishPondSuccess(BaseResponse baseResponse) {
        if (baseResponse != null) {
            mRxManager.post(AppConstant.RX_ADD_POND, baseResponse);
            UIUtils.showToast("添加成功");
            Log.e("test",baseResponse.toString());
        } else {
            UIUtils.showToast("请勿重复添加");
        }

        finish();
    }

    @Override
    public void addFishPondFailure(String error) {
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
        mEtFishPondLongitude.setText(String.valueOf(location.getLongitude()));
        mEtFishPondLatitude.setText(String.valueOf(location.getLatitude()));
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