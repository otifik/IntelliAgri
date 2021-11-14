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
import com.jit.AgriIn.model.response.EquipResponse;
import com.jit.AgriIn.model.response.EquipType;
import com.jit.AgriIn.model.response.TermResponse;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.util.NetworkHelper;
import com.zxl.baselib.util.ui.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author zxl on 2018/08/29.
 *         discription: 新增塘口配置
 */
public class EquipAddActivity extends BaseActivity<EquipAddView,EquipAddPresenter> implements EquipAddView {


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
    @BindView(R.id.etRoad)
    EditText mEtRoad;
    @BindView(R.id.tvAddr)
    TextView mTvAddr;
    @BindView(R.id.tvCellId)
    TextView mTvCellId;
    @BindView(R.id.tvEquipType)
    TextView mTvEquipType;




    private List<String> mTypeList = new ArrayList<>();


    private TermResponse mTermResponse;
    int mTermId;
    int mTermType;
    int mCellId;
    String mSensorType;

    @Override
    protected void init() {
        if (getIntent() != null){
            mTermResponse = (TermResponse) getIntent().getExtras().getSerializable(AppConstant.BUNDLE_POND_BEAN);

            mTermId = mTermResponse.getId();
            mTermType = mTermResponse.getType();
            mSensorType = mTermResponse.getProduct();
        }
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_equip_add;
    }

    @Override
    protected EquipAddPresenter createPresenter() {
        return new EquipAddPresenter(this);
    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        mTvToolbarTitle.setText("添加可控设备");
        mTvPublishNow.setVisibility(View.VISIBLE);
        mTvPublishNow.setText(R.string.submit);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        mIvToolbarNavigation.setOnClickListener(v -> onBackPressed());


        mTvPublishNow.setOnClickListener(v -> requestSubmit());

//        if (mTermType == 1){
            mTvEquipType.setOnClickListener(view -> mPresenter.getEquipType());

            mTvAddr.setOnClickListener(view -> new MaterialDialog.Builder(EquipAddActivity.this)
                    .title("输入485地址")// 标题
//                            .content("input content")// 内容
                    .inputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_NUMBER)// 输入类型
                    .input("请输入485地址", "", new MaterialDialog.InputCallback() {
                        @Override
                        public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                            mTvAddr.setText(input);
                        }
                    }).show());
//        }


        mTvCellId.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, CellChooseActivity.class);
            intent.putExtra("username",mTermResponse.getUsername());
            startActivityForResult(intent, 1);//带请求码打开activity (请求码自定 这里设为1
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

        String addr = mTvAddr.getText().toString();
        if (TextUtils.isEmpty(addr)){
            UIUtils.showToast("请填写485地址");
            return;
        }

        String equipType = mTvEquipType.getText().toString();
        if (TextUtils.isEmpty(equipType)){
            UIUtils.showToast("请选择传感器类型");
            return;
        }

        String road = mEtRoad.getText().toString();
        if (TextUtils.isEmpty(road)){
            UIUtils.showToast("请填写控制路数");
            return;
        }



        if (!NetworkHelper.isNetworkAvailable(this)){
            UIUtils.showToast(getString(R.string.no_net));
            return;
        }else {
            doPondAddCommit(Integer.valueOf(cellId),mTermId,Integer.valueOf(addr),Integer.valueOf(road),equipType,mEtName.getText().toString());
        }
    }


    private void doPondAddCommit(int cell_id,int term_id,int addr,int road,String equip_type,String equip_name) {
        mPresenter.addEquip(cell_id,term_id,addr,road,equip_type,equip_name);
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



    @Override
    public void addEquipSuccess(EquipResponse equipResponse) {
        if (equipResponse != null){
            mRxManager.post(AppConstant.RX_ADD_POND,equipResponse);
        }else {
            UIUtils.showToast("请勿重复添加");
        }

        finish();
    }

    @Override
    public void addEquipFailure(String error) {
        UIUtils.showToast(error);
    }

    @Override
    public void getEquipTypeSuccess(List<EquipType> equipType) {
        mTypeList.clear();
        for (int i=0;i<equipType.size();i++){
            mTypeList.add(equipType.get(i).getType());
        }

        new MaterialDialog.Builder(this)
                .title("可控设备类型")
                .items(mTypeList)
                .itemsCallback((dialog, view, which, text) -> mTvEquipType.setText(text))
                .positiveText(android.R.string.cancel)
                .show();
    }

    @Override
    public void getEquipTypeFailure(String error) {
        UIUtils.showToast(error);
    }
}
