package com.jit.AgriIn.uinew.role_admin;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jit.AgriIn.R;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.model.response.ManuInfo;
import com.jit.AgriIn.model.response.PageResponse;
import com.jit.AgriIn.model.response.RoleUserInfo;
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
public class TermAddActivity extends BaseActivity<TermAddView,TermAddPresenter> implements TermAddView {


    @BindView(R.id.ivToolbarNavigation)
    ImageView mIvToolbarNavigation;
    @BindView(R.id.vToolbarDivision)
    View mVToolbarDivision;
    @BindView(R.id.tvToolbarTitle)
    TextView mTvToolbarTitle;
    @BindView(R.id.tv_publish_now)
    TextView mTvPublishNow;

    @BindView(R.id.etDeviui)
    EditText mEtDeviui;
    @BindView(R.id.etName)
    EditText mEtName;
    @BindView(R.id.tvType)
    TextView mTvType;
    @BindView(R.id.tvTypeName)
    TextView mTvTypeName;
    @BindView(R.id.tvManu)
    TextView mTvManu;
    @BindView(R.id.tvProduct)
    TextView mTvProduct;
    @BindView(R.id.tvUserName)
    TextView mTvUserName;

    private List<ManuInfo> mManuInfoList = new ArrayList<>();
    private List<String> mTypeNum = new ArrayList<>();
    private List<String> mProductList = new ArrayList<>();
    int mPos = 0;

    @Override
    protected void init() {

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_term_add;
    }

    @Override
    protected TermAddPresenter createPresenter() {
        return new TermAddPresenter(this);
    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        mTvToolbarTitle.setText("添加终端");
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

        mTvType.setOnClickListener(view -> mPresenter.getTermManus());


        mTvUserName.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, UserChooseActivity.class);
            startActivityForResult(intent, 100);//带请求码打开activity (请求码自定 这里设为1
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
        String termType = mTvType.getText().toString();
        if (TextUtils.isEmpty(termType)){
            UIUtils.showToast("请选择终端类型");
            return;
        }

        String termPro = mTvProduct.getText().toString();
        if (TextUtils.isEmpty(termPro)){
            UIUtils.showToast("请选择产品");
            return;
        }

        String userName = mTvUserName.getText().toString();
        if (TextUtils.isEmpty(userName)){
            UIUtils.showToast("请选择绑定用户");
            return;
        }

        String devui = mEtDeviui.getText().toString();
        if (!mTvType.getText().toString().equals("1")){
            if (TextUtils.isEmpty(devui)){
                UIUtils.showToast("请填写终端串号");
                return;
            }
        }



        if (!NetworkHelper.isNetworkAvailable(this)){
            UIUtils.showToast(getString(R.string.no_net));
            return;
        }else {
            doPondAddCommit(Integer.valueOf(termType),devui,mTvManu.getText().toString(),
                    termPro,mEtName.getText().toString(),mTvUserName.getText().toString());
        }
    }


    private void doPondAddCommit(int type,String deveui,String manu,String prod,String name,String user) {
        if (type == 1){
            mPresenter.addTerm(type,null,manu,prod,name,user);
        }else {
            mPresenter.addTerm(type,deveui,manu,prod,name,user);
        }

    }


    @Override
    public void addTermSuccess(TermResponse termResponse) {
        mRxManager.post(AppConstant.RX_ADD_POND,termResponse);
        finish();
    }

    @Override
    public void addTermFailure(String error) {
        UIUtils.showToast(error);
    }

    @Override
    public void getManuSuccess(List<ManuInfo> manuInfoList) {
        mManuInfoList.clear();
        if (manuInfoList != null){
            mManuInfoList = manuInfoList;

            mTypeNum.clear();
            for (ManuInfo manuInfo:mManuInfoList){
                mTypeNum.add(manuInfo.getType());
            }

            new MaterialDialog.Builder(this)
                    .title("选择产品类型")
                    .items(mTypeNum)
                    .itemsCallback(new MaterialDialog.ListCallback() { //点击回调
                        @Override
                        public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                            mPos = position;
                            mTvType.setText(String.valueOf(mManuInfoList.get(position).getId()));
                            mTvTypeName.setText(text);
                            mTvManu.setText(mManuInfoList.get(position).getManu());
                            chooseProduct();
                        }
                    })
                    .positiveText(android.R.string.cancel)
                    .show();

        }
    }

    private void chooseProduct() {
        mProductList.clear();
        for (String pro:mManuInfoList.get(mPos).getProducts()){
            mProductList.add(pro);
        }

        new MaterialDialog.Builder(this)
                .title("选择产品")
                .items(mProductList)
                .itemsCallback(new MaterialDialog.ListCallback() { //点击回调
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        mTvProduct.setText(text);
                    }
                })
                .positiveText(android.R.string.cancel)
                .show();
    }

    @Override
    public void getManuFailure(String error) {
        UIUtils.showToast(error);
    }

    @Override
    public void queryCustomersSuccess(PageResponse<RoleUserInfo> customerInfoPageResponse) {

    }

    @Override
    public void queryCustomerFailure(String error) {

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){  //根据请求码可处理不同活动返回的数据
            case 100: //返回的请求码
                //操作
                if (data != null){
                    String user = data.getExtras().getString("user");
                    if (user != null){
                        mTvUserName.setText(user);
                    }
                }
                break;
        }
    }



}
