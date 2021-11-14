package com.jit.AgriIn.uinew.role_admin;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jit.AgriIn.R;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.model.bean.TermUpdateBean;
import com.jit.AgriIn.model.response.PageResponse;
import com.jit.AgriIn.model.response.RoleUserInfo;
import com.jit.AgriIn.model.response.TermResponse;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.util.NetworkHelper;
import com.zxl.baselib.util.ui.UIUtils;

import butterknife.BindView;

/**
 * @author zxl on 2018/08/29.
 *         discription: 新增塘口配置
 */
public class TermUpdateActivity extends BaseActivity<TermUpdateView,TermUpdatePresenter> implements TermUpdateView {


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

    private TermResponse mTermResponse;

    private String mTermDeviui;
    private String mTermName;
    private String mTermType;
    private String mTermTypeName;
    private String mTermManu;
    private String mTermProduct;
    private String mTermUserName;


    private int mItemIndex;
    private int mTermId;
    private boolean isFromPondMain = false;

    @Override
    protected void init() {
        if (getIntent() != null){
            mTermResponse = (TermResponse) getIntent().getExtras().getSerializable(AppConstant.BUNDLE_POND_BEAN);

            mTermDeviui = mTermResponse.getDeveui();
            mTermName = mTermResponse.getName();
            mTermType = String.valueOf(mTermResponse.getType());
            mTermManu = mTermResponse.getManu();
            mTermProduct = mTermResponse.getProduct();
            mTermUserName = mTermResponse.getUsername();


            mTermId = mTermResponse.getId();
            mItemIndex = getIntent().getIntExtra(AppConstant.EXTRA_ITEM_INDEX,-1);
            isFromPondMain = getIntent().getBooleanExtra(AppConstant.EXTRA_IS_FROM_POND_MAIN,false);
        }
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_term_add;
    }

    @Override
    protected TermUpdatePresenter createPresenter() {
        return new TermUpdatePresenter(this);
    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        mTvToolbarTitle.setText("修改终端信息");
        mTvPublishNow.setVisibility(View.VISIBLE);
        mTvPublishNow.setText(R.string.submit);

        mEtDeviui.setText(mTermDeviui);
        mEtName.setText(mTermName);
        mTvType.setText(mTermType);
        mTvManu.setText(mTermManu);
        mTvProduct.setText(mTermProduct);
        mTvUserName.setText(mTermUserName);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        mIvToolbarNavigation.setOnClickListener(v -> onBackPressed());


        mTvPublishNow.setOnClickListener(v -> requestSubmit());

        mTvUserName.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, UserChooseActivity.class);
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
            doPondAddCommit(devui,mEtName.getText().toString(),userName);
        }
    }


    private void doPondAddCommit(String deveui,String name,String user) {
        mPresenter.updateTerm(mTermId,deveui,name,user);
    }



    @Override
    public void updateTermSuccess() {

        TermUpdateBean termUpdateBean = new TermUpdateBean();
        termUpdateBean.setItemPosition(mItemIndex);
        mTermResponse.setName(mEtName.getText().toString());
        mTermResponse.setDeveui(mEtDeviui.getText().toString());
        mTermResponse.setUsername(mTvUserName.getText().toString());
        termUpdateBean.setTermResponse(mTermResponse);
        mRxManager.post(AppConstant.RX_UPDATE_POND, termUpdateBean);
        finish();
    }

    @Override
    public void updateTermFailure(String error) {
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
            case 1: //返回的请求码
                //操作
                String user = data.getExtras().getString("user");
                mTvUserName.setText(user);
                break;
        }
    }
}
