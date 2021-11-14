package com.jit.AgriIn.uinew.role_admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jit.AgriIn.R;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.model.bean.SensorUpdateBean;
import com.jit.AgriIn.model.response.SensorResponse;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.util.NetworkHelper;
import com.zxl.baselib.util.ui.UIUtils;

import butterknife.BindView;

/**
 * @author zxl on 2018/08/29.
 *         discription: 新增塘口配置
 */
public class SensorUpdateActivity extends BaseActivity<SensorUpdateView,SensorUpdatePresenter> implements SensorUpdateView {


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
    @BindView(R.id.tvAddr)
    TextView mTvAddr;
    @BindView(R.id.tvCellId)
    TextView mTvCellId;
    @BindView(R.id.tvSensorType)
    TextView mTvSensorType;

    private SensorResponse mSensorResponse;

    private String mSensorName;
    private int mSensorAddr;
    private int mSensorCellId;
    private String mSensorType;


    private int mTermType;
    private String mUserName;

    private int mItemIndex;
    private int mSensorId;
    private boolean isFromPondMain = false;

    @Override
    protected void init() {
        if (getIntent() != null){
            mSensorResponse = (SensorResponse) getIntent().getExtras().getSerializable(AppConstant.BUNDLE_POND_BEAN);

            mSensorCellId = mSensorResponse.getCellid();
            mSensorAddr = mSensorResponse.getAddr();
            mSensorName = mSensorResponse.getName();
            mSensorType = mSensorResponse.getType();

            mTermType = getIntent().getIntExtra("termType",0);
            mUserName = getIntent().getStringExtra("userName");

            mSensorId = mSensorResponse.getId();
            mItemIndex = getIntent().getIntExtra(AppConstant.EXTRA_ITEM_INDEX,-1);
            isFromPondMain = getIntent().getBooleanExtra(AppConstant.EXTRA_IS_FROM_POND_MAIN,false);
        }
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_sensor_add;
    }

    @Override
    protected SensorUpdatePresenter createPresenter() {
        return new SensorUpdatePresenter(this);
    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        mTvToolbarTitle.setText("修改传感器信息");
        mTvPublishNow.setVisibility(View.VISIBLE);
        mTvPublishNow.setText(R.string.submit);

        mEtName.setText(mSensorName);
        mTvCellId.setText(String.valueOf(mSensorCellId));
        mTvAddr.setText(String.valueOf(mSensorAddr));
        mTvSensorType.setText(mSensorType);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        mIvToolbarNavigation.setOnClickListener(v -> onBackPressed());


        mTvPublishNow.setOnClickListener(v -> requestSubmit());

        mTvCellId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, CellChooseActivity.class);
                intent.putExtra("username",mUserName);
                startActivityForResult(intent, 1);//带请求码打开activity (请求码自定 这里设为1
            }
        });


        if (mTermType == 1){

            mTvAddr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    new MaterialDialog.Builder(SensorUpdateActivity.this)
                            .title("输入485地址")// 标题
//                            .content("input content")// 内容
                            .inputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_NUMBER)// 输入类型
                            .input("请输入485地址", "", new MaterialDialog.InputCallback() {
                                @Override
                                public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                                    mTvAddr.setText(input);
                                }
                            }).show();

                }
            });
        }
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


        String addr = mTvAddr.getText().toString();
        if (TextUtils.isEmpty(addr)){
            UIUtils.showToast("请填写485地址");
            return;
        }




        if (!NetworkHelper.isNetworkAvailable(this)){
            UIUtils.showToast(getString(R.string.no_net));
            return;
        }else {
            doPondAddCommit(Integer.valueOf(cellId),Integer.valueOf(addr),mEtName.getText().toString());
        }
    }


    private void doPondAddCommit(int cellId,int addr,String sensorName) {
        mPresenter.updateSensor(mSensorId,cellId,addr,sensorName);
    }






    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){  //根据请求码可处理不同活动返回的数据
            case 1: //返回的请求码
                //操作
                if (data != null){
                    mSensorCellId  = data.getExtras().getInt("cellid");
                    mTvCellId.setText(String.valueOf(mSensorCellId));
                }
                break;
        }
    }

    @Override
    public void updateSensporSuccess() {
        SensorUpdateBean sensorUpdateBean = new SensorUpdateBean();
        sensorUpdateBean.setItemPosition(mItemIndex);

        mSensorResponse.setName(mEtName.getText().toString());
        mSensorResponse.setCellid(Integer.valueOf(mTvCellId.getText().toString()));
        mSensorResponse.setAddr(Integer.valueOf(mTvAddr.getText().toString()));

        sensorUpdateBean.setSensorResponse(mSensorResponse);
        mRxManager.post(AppConstant.RX_UPDATE_POND, sensorUpdateBean);
        finish();
    }

    @Override
    public void updateSensorFailure(String error) {
        UIUtils.showToast(error);
    }
}
