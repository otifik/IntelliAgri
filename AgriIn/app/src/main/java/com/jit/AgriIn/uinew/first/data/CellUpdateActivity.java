package com.jit.AgriIn.uinew.first.data;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jit.AgriIn.R;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.model.bean.CellUpdateBean;
import com.jit.AgriIn.model.bean.FishPondUpdateBean;
import com.jit.AgriIn.model.cache.UserCache;
import com.jit.AgriIn.model.response.CellProTypeResponse;
import com.jit.AgriIn.model.response.CellResponse;
import com.jit.AgriIn.model.response.FishPondResponse;
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
public class CellUpdateActivity extends BaseActivity<CellUpdateView,CellUpdatePresenter> implements CellUpdateView {


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

    private FishPondResponse mFishPondResponse;

    private String fishPondName;
    private double fishPondLength;
    private double fishPondWidth;
    private double fishPondDepth;
    private String orientation;
    private double fishPondLongitude;
    private double fishPondLatitude;
    private String address;
    private String product;
    private String username;


    private List<String> mProTypeList = new ArrayList<>();
    int mPos = 0;

    private int mItemIndex;
    private int mFishPondID;
    private boolean isFromPondMain = false;

    private ArrayList<String> orientations = new ArrayList();

    @Override
    protected void init() {
        if (getIntent() != null){
            mFishPondResponse = (FishPondResponse) getIntent().getExtras().getSerializable(AppConstant.BUNDLE_POND_BEAN);

            fishPondName = mFishPondResponse.getName();
            fishPondWidth = mFishPondResponse.getWidth();
            fishPondLength = mFishPondResponse.getLength();
            fishPondDepth = mFishPondResponse.getDepth();
            orientation = mFishPondResponse.getOrientation();
            fishPondLongitude = mFishPondResponse.getLongitude();
            fishPondLatitude = mFishPondResponse.getLatitude();
            address = mFishPondResponse.getAddress();
            product = mFishPondResponse.getProduct();


            mFishPondID = mFishPondResponse.getId();

            mItemIndex = getIntent().getIntExtra(AppConstant.EXTRA_ITEM_INDEX,-1);
            isFromPondMain = getIntent().getBooleanExtra(AppConstant.EXTRA_IS_FROM_POND_MAIN,false);
        }
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_fish_pond_add;
    }

    @Override
    protected CellUpdatePresenter createPresenter() {
        return new CellUpdatePresenter(this);
    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        mTvToolbarTitle.setText("修改生产单元信息");
        mTvPublishNow.setVisibility(View.VISIBLE);
        mTvPublishNow.setText(R.string.submit);

        mEtFishPondName.setText(fishPondName);
        mEtFishPondLength.setText(String.valueOf(fishPondLength));
        mEtFishPondWidth.setText(String.valueOf(fishPondWidth));
        mEtFishPondDepth.setText(String.valueOf(fishPondDepth));
        mOrientation.setText(orientation);
        mEtFishPondLongitude.setText(String.valueOf(fishPondLongitude));
        mEtFishPondLatitude.setText(String.valueOf(fishPondLatitude));
        mEtFishPondAddress.setText(address);
        mTvFishPondProduct.setText(product);

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

        String name = mEtFishPondName.getText().toString();
        if (TextUtils.isEmpty(name)){
            UIUtils.showToast("请填写塘口名称");
            return;
        }

        String length = mEtFishPondLength.getText().toString();
        if (TextUtils.isEmpty(length)){
            UIUtils.showToast("请填写塘口长度");
            return;
        }

        String width = mEtFishPondWidth.getText().toString();
        if (TextUtils.isEmpty(width)){
            UIUtils.showToast("请填写塘口宽度");
            return;
        }

        String depth = mEtFishPondDepth.getText().toString();
        if (TextUtils.isEmpty(depth)){
            UIUtils.showToast("请填写塘口深度");
            return;
        }

        String orientation = mOrientation.getText().toString();
        if (TextUtils.isEmpty(orientation)){
            UIUtils.showToast("请填写塘口朝向");
            return;
        }

        String longitude = mEtFishPondLongitude.getText().toString();
        if (TextUtils.isEmpty(longitude)){
            UIUtils.showToast("请填写塘口经度");
            return;
        }

        String latitude = mEtFishPondLatitude.getText().toString();
        if (TextUtils.isEmpty(latitude)){
            UIUtils.showToast("请填写塘口纬度");
            return;
        }

        String address = mEtFishPondAddress.getText().toString();
        if (TextUtils.isEmpty(address)){
            UIUtils.showToast("请填写塘口地址");
            return;
        }

        String pro = mTvFishPondProduct.getText().toString();
        if (TextUtils.isEmpty(pro)){
            UIUtils.showToast("请填写产品名称");
            return;
        }

        if (!NetworkHelper.isNetworkAvailable(this)){
            UIUtils.showToast(getString(R.string.no_net));
            return;
        }else {
            doPondAddCommit(mFishPondID,name,Double.parseDouble(length),Double.parseDouble(width),Double.parseDouble(depth),orientation,Double.parseDouble(longitude),Double.parseDouble(latitude),address,product);
        }
    }


    private void doPondAddCommit(int id,String name,double length,double width,double depth,String orientation,double longitude,double latitude,String address,String product) {
        mPresenter.updateFishPond(id, name, length, width, depth, orientation, longitude, latitude, address, product);
    }


    @Override
    public void updateFishPondSuccess() {
        FishPondUpdateBean fishPondUpdateBean = new FishPondUpdateBean();
        fishPondUpdateBean.setItemPosition(mItemIndex);

        mFishPondResponse.setName(mEtFishPondName.getText().toString());
        mFishPondResponse.setLength(Double.parseDouble(mEtFishPondLength.getText().toString()));
        mFishPondResponse.setWidth(Double.parseDouble(mEtFishPondWidth.getText().toString()));
        mFishPondResponse.setDepth(Double.parseDouble(mEtFishPondDepth.getText().toString()));
        mFishPondResponse.setLongitude(Double.parseDouble(mEtFishPondLongitude.getText().toString()));
        mFishPondResponse.setLatitude(Double.parseDouble(mEtFishPondLatitude.getText().toString()));
        mFishPondResponse.setAddress(mEtFishPondAddress.getText().toString());
        mFishPondResponse.setProduct(mTvFishPondProduct.getText().toString());

        fishPondUpdateBean.setFishPondResponse(mFishPondResponse);
        mRxManager.post(AppConstant.RX_UPDATE_POND, fishPondUpdateBean);
        UIUtils.showToast("修改成功");
        finish();
    }

    @Override
    public void updateFishPondFailure(String error) {
        UIUtils.showToast(error);
    }

}
