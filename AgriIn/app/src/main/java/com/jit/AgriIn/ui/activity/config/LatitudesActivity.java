package com.jit.AgriIn.ui.activity.config;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.jit.AgriIn.R;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.widget.MyDialog;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BasePresenter;
import com.zxl.baselib.util.ui.UIUtils;

import butterknife.BindView;

/**
 * @author zxl on 2018/08/29.
 *         discription:新增生产单元的经纬度界面
 *         设置 ---- 高德地图 进行经纬度显示 -----
 */
public class LatitudesActivity extends BaseActivity implements AMap.OnMyLocationChangeListener {
    @BindView(R.id.ivToolbarNavigation)
    ImageView mIvToolbarNavigation;
    @BindView(R.id.vToolbarDivision)
    View mVToolbarDivision;
    @BindView(R.id.tvToolbarTitle)
    TextView mTvToolbarTitle;
    @BindView(R.id.tv_publish_now)
    TextView mTvPublishNow;
    @BindView(R.id.tvW_N)
    TextView mTvWN;
    @BindView(R.id.tvE_N)
    TextView mTvEN;
    @BindView(R.id.tvW_S)
    TextView mTvWS;
    @BindView(R.id.tvE_S)
    TextView mTvES;
    @BindView(R.id.mapView)
    MapView mMapView;
    @BindView(R.id.tvShowLat)
    TextView mTvShowLat;
    @BindView(R.id.tvShowLang)
    TextView mTvShowLang;
    @BindView(R.id.location)
    ImageButton mLocation;
    private float eSLongitude = AppConstant.LATITUDE_DEFAULT;
    private float eSLatitude = AppConstant.LATITUDE_DEFAULT;
    private float eNLongitude = AppConstant.LATITUDE_DEFAULT;
    private float eNLatitude = AppConstant.LATITUDE_DEFAULT;
    private float wSLongitude = AppConstant.LATITUDE_DEFAULT;
    private float wSLatitude = AppConstant.LATITUDE_DEFAULT;
    private float wNLongitude = AppConstant.LATITUDE_DEFAULT;
    private float wNLatitude = AppConstant.LATITUDE_DEFAULT;
    private AMap mAMap = null;
    private static final int STROKE_COLOR = Color.argb(0, 0, 0, 0);
    private static final int FILL_COLOR = Color.argb(0, 0, 0, 0);
    private Location myLocation;
    private LatLng carLocationLatlng;
    private boolean mIsFromChange = false;
    private Snackbar mSnackbar;

    @Override
    protected void init() {
        if (getIntent() != null && getIntent().hasExtra(AppConstant.EXTRA_IS_CHANGE_LATITUDES) ){
            eSLongitude =  getIntent().getFloatExtra(AppConstant.DirectionExtra.LONG_E_S.name(),
                    AppConstant.LATITUDE_DEFAULT);
            eNLongitude = getIntent().getFloatExtra(AppConstant.DirectionExtra.LONG_E_N.name(),AppConstant.LATITUDE_DEFAULT);
            wNLongitude = getIntent().getFloatExtra(AppConstant.DirectionExtra.LONG_W_N.name(),AppConstant.LATITUDE_DEFAULT);
            wSLongitude = getIntent().getFloatExtra(AppConstant.DirectionExtra.LONG_W_S.name(),AppConstant.LATITUDE_DEFAULT);
            eSLatitude = getIntent().getFloatExtra(AppConstant.DirectionExtra.LAT_E_S.name(),AppConstant.LATITUDE_DEFAULT);
            eNLatitude = getIntent().getFloatExtra(AppConstant.DirectionExtra.LAT_E_N.name(),AppConstant.LATITUDE_DEFAULT);
            wSLatitude = getIntent().getFloatExtra(AppConstant.DirectionExtra.LAT_W_S.name(),AppConstant.LATITUDE_DEFAULT);
            wNLatitude = getIntent().getFloatExtra(AppConstant.DirectionExtra.LAT_W_N.name(),AppConstant.LATITUDE_DEFAULT);
            mIsFromChange = true;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_latitudes;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mTvToolbarTitle.setText(R.string.set_pond_latitude);
        mTvPublishNow.setVisibility(View.VISIBLE);
        mTvPublishNow.setText(R.string.save);
        initMapAndShow(savedInstanceState);
        showSnack(mTvWN);
        refreshView();
    }

    private void refreshView() {
        if (mIsFromChange){
            mTvEN.setText(getString(R.string.latitude_format, String.valueOf(eNLongitude), String.valueOf(eNLatitude)));
            mTvES.setText(getString(R.string.latitude_format, String.valueOf(eSLongitude), String.valueOf(eSLatitude)));
            mTvWN.setText(getString(R.string.latitude_format,String.valueOf(wNLongitude),String.valueOf(wNLatitude)));
            mTvWS.setText(getString(R.string.latitude_format,String.valueOf(wSLongitude),String.valueOf(wSLatitude)));
        }
    }

    private void initMapAndShow(Bundle savedInstanceState) {
        //创建地图 重要
        mMapView.onCreate(savedInstanceState);
        if (mAMap == null) {
            mAMap = mMapView.getMap();
        }
        // 初始化定点蓝图样式
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        // 设置定位的频率 2秒一次
        myLocationStyle.interval(2000);
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);
        myLocationStyle.strokeColor(STROKE_COLOR);
        myLocationStyle.strokeWidth(0);
        myLocationStyle.radiusFillColor(FILL_COLOR);
        //设置定位蓝点的Style
        mAMap.setMyLocationStyle(myLocationStyle);
        // 显示定点蓝图
        mAMap.setMyLocationEnabled(true);
        // 设置放大缩小的控制是否可见
        mAMap.getUiSettings().setZoomControlsEnabled(false);
        mAMap.setOnMyLocationChangeListener(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        mIvToolbarNavigation.setOnClickListener(v -> onBackPressed());

        // 东南
        mTvEN.setOnClickListener(v -> showLatitudesDialog(AppConstant.PondDirection.E_N));

        // 东北
        mTvES.setOnClickListener(v -> showLatitudesDialog(AppConstant.PondDirection.E_S));

        // 西南
        mTvWN.setOnClickListener(v -> showLatitudesDialog(AppConstant.PondDirection.W_N));

        // 西北
        mTvWS.setOnClickListener(v -> showLatitudesDialog(AppConstant.PondDirection.W_S));

        mTvPublishNow.setOnClickListener(v -> defDataAndReturn());

        mLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myLocation != null) {
                    mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(myLocation.getLatitude(), myLocation.getLongitude()), 14));
                }
            }
        });
    }

    private void showSnack(View view){
        //  这里已经退出了snack 但后面可以考虑在其他应用场景加逻辑
        mSnackbar = Snackbar.make(view, getString(R.string.snack_bar, getString(R.string.click_to_set_lang)), Snackbar.LENGTH_LONG)
                .setAction(R.string.get_it, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //  这里已经退出了snack 但后面可以考虑在其他应用场景加逻辑
                        mSnackbar.dismiss();
                    }
                });
        mSnackbar.show();
    }

    private void defDataAndReturn() {
        if (eNLongitude == AppConstant.LATITUDE_DEFAULT ||
                eSLatitude == AppConstant.LATITUDE_DEFAULT ||
                wNLatitude == AppConstant.LATITUDE_DEFAULT ||
                wSLatitude == AppConstant.LATITUDE_DEFAULT) {
            UIUtils.showToast(getString(R.string.input_bound_pond_four_latitude));
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(AppConstant.DirectionExtra.LAT_E_N.name(), eNLatitude);
        intent.putExtra(AppConstant.DirectionExtra.LAT_E_S.name(), eSLatitude);
        intent.putExtra(AppConstant.DirectionExtra.LAT_W_N.name(), wNLatitude);
        intent.putExtra(AppConstant.DirectionExtra.LAT_W_S.name(), wSLatitude);
        intent.putExtra(AppConstant.DirectionExtra.LONG_E_S.name(), eSLongitude);
        intent.putExtra(AppConstant.DirectionExtra.LONG_E_N.name(), eNLongitude);
        intent.putExtra(AppConstant.DirectionExtra.LONG_W_S.name(), wSLongitude);
        intent.putExtra(AppConstant.DirectionExtra.LONG_W_N.name(), wNLongitude);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void showLatitudesDialog(AppConstant.PondDirection pondDirection) {
        float longtitude = AppConstant.LATITUDE_DEFAULT;
        float latitude = AppConstant.LATITUDE_DEFAULT;
        switch (pondDirection) {
            case E_N:
                longtitude = eNLongitude;
                latitude = eNLatitude;
                break;
            case E_S:
                longtitude = eSLongitude;
                latitude = eSLatitude;
                break;
            case W_N:
                longtitude = wNLongitude;
                latitude = wNLatitude;
                break;
            case W_S:
                longtitude = wSLongitude;
                latitude = wSLatitude;
                break;
            default:
                break;
        }
        View latitudeView = LayoutInflater.from(this).inflate(R.layout.dialog_insert_latitudes, null);
        MyDialog latitudeDialog = new MyDialog(this, R.style.MyDialog);
        Window window = latitudeDialog.getWindow();
        window.setGravity(Gravity.CENTER);
        window.setWindowAnimations(R.style.BottomDialog);
        window.getDecorView().setPadding(50, 0, 50, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        latitudeDialog.setContentView(latitudeView);
        TextView tvTitle = latitudeView.findViewById(R.id.tvTitle);
        LinearLayout llLongtitude = latitudeView.findViewById(R.id.llLongitude);
        EditText etLongtitude = latitudeView.findViewById(R.id.etLongitude);
        LinearLayout llLatitude = latitudeView.findViewById(R.id.llLatitude);
        EditText etLatitude = latitudeView.findViewById(R.id.etLatitude);
        TextView tvCancel = latitudeView.findViewById(R.id.tvCancel);
        TextView tvEnsure = latitudeView.findViewById(R.id.tvEnsure);
        tvTitle.setText(pondDirection.name);
        etLongtitude.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                llLongtitude.setBackgroundResource(R.drawable.bg_latitude_selector_bg);
            }
        });
        etLatitude.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                llLatitude.setBackgroundResource(R.drawable.bg_latitude_selector_bg);
            }
        });
        etLongtitude.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                llLongtitude.setActivated(true);
                llLatitude.setActivated(false);
            }
        });
        etLatitude.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                llLongtitude.setActivated(false);
                llLatitude.setActivated(true);
            }
        });
        etLatitude.setOnClickListener(v -> {
            etLongtitude.clearFocus();
            etLatitude.setFocusableInTouchMode(true);
            etLatitude.requestFocus();
        });

        etLongtitude.setOnClickListener(v -> {
            etLatitude.clearFocus();
            etLongtitude.setFocusableInTouchMode(true);
            etLongtitude.requestFocus();
        });
        if (longtitude != AppConstant.LATITUDE_DEFAULT) {
            String s1 = String.valueOf(longtitude);
            etLongtitude.setText(s1);
            etLongtitude.setSelection(s1.length());
        }

        if (latitude != AppConstant.LATITUDE_DEFAULT) {
            String s2 = String.valueOf(latitude);
            etLatitude.setText(s2);
            etLatitude.setText(s2);
        }
        tvCancel.setOnClickListener(v -> latitudeDialog.dismiss());
        tvEnsure.setOnClickListener(v -> {
            String latitude1 = etLatitude.getText().toString().trim();
            String longtitude1 = etLongtitude.getText().toString().trim();
            if (TextUtils.isEmpty(latitude1) || TextUtils.isEmpty(longtitude1)) {
                UIUtils.showToast(getString(R.string.tips_this_corner_not_set_ok));
                return;
            }
            recallText(latitude1, longtitude1, pondDirection);
            latitudeDialog.dismiss();
        });
        latitudeDialog.show();

    }

    /**
     * 文本设置的回调
     *
     * @param latitude      纬度
     * @param longtitude    经度
     * @param pondDirection 方向
     */
    private void recallText(String latitude, String longtitude, AppConstant.PondDirection pondDirection) {
        float d1 = Float.parseFloat(longtitude);
        float d2 = Float.parseFloat(latitude);
        switch (pondDirection) {
            case E_N:
                eNLongitude = d1;
                eNLatitude = d2;
                mTvEN.setText(getString(R.string.latitude_format, longtitude, latitude));
                break;
            case E_S:
                eSLongitude = d1;
                eSLatitude = d2;
                mTvES.setText(getString(R.string.latitude_format, longtitude, latitude));
                break;
            case W_N:
                wNLongitude = d1;
                wNLatitude = d2;
                mTvWN.setText(getString(R.string.latitude_format, longtitude, latitude));
                break;
            case W_S:
                wSLongitude = d1;
                wSLatitude = d2;
                mTvWS.setText(getString(R.string.latitude_format, longtitude, latitude));
                break;
            default:
                break;
        }

    }

    @Override
    public void onMyLocationChange(Location location) {
        if (location  != null) {
            // 我的地址改变时的回调
            mTvShowLat.setText(getString(R.string.show_lat,String.valueOf(location.getLatitude())));
            mTvShowLang.setText(getString(R.string.show_lang,String.valueOf(location.getLongitude())));
            // 第一次时  设置时间定位到当前
            if (myLocation == null) {
                myLocation = location;
                if (carLocationLatlng == null) {
                    mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(myLocation.getLatitude(), myLocation.getLongitude()), 14));
                }
            }
        }else {
            UIUtils.showToast(getString(R.string.error_map_location));
        }
    }


    @Override
    protected void onDestroy() {
        // 这里切忌  有些执行的动作需要在 onDestroy()
        // 不然让onDestroy执行完了之后 有的信息都会被置空了
        if (mMapView != null) {
            mMapView.onDestroy();
        }
        super.onDestroy();
    }


}
