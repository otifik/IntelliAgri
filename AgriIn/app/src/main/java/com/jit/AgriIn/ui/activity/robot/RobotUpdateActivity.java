package com.jit.AgriIn.ui.activity.robot;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.jit.AgriIn.R;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.model.bean.RobotUpdateBean;
import com.jit.AgriIn.model.response.RobotMainResponse;
import com.jit.AgriIn.ui.presenter.robot.RobotUpdatePresenter;
import com.jit.AgriIn.ui.view.robot.RobotUpdateView;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.util.ui.UIUtils;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * @author crazyZhangxl on 2018-9-28 19:55:06.
 * Describe:
 */

public class RobotUpdateActivity extends BaseActivity<RobotUpdateView, RobotUpdatePresenter> implements RobotUpdateView {

    @BindView(R.id.ivToolbarNavigation)
    ImageView mIvToolbarNavigation;
    @BindView(R.id.tvToolbarTitle)
    TextView mTvToolbarTitle;
    @BindView(R.id.etRobotNumber)
    EditText mEtRobotNumber;
    @BindView(R.id.spRobotType)
    MaterialSpinner mSpRobotType;
    @BindView(R.id.btnUpdateRobot)
    Button mBtnUpdateRobot;
    private int mItemIndex;
    private RobotMainResponse mRobotMainResponse;

    @Override
    protected void init() {
        if (getIntent() != null) {
            mItemIndex = getIntent().getIntExtra(AppConstant.EXTRA_ITEM_INDEX, -1);
            mRobotMainResponse = (RobotMainResponse) getIntent().getSerializableExtra(AppConstant.BUNDLE_ROBOT_BEAN);
        }
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_robot_update;
    }

    @Override
    protected RobotUpdatePresenter createPresenter() {
        return new RobotUpdatePresenter(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mTvToolbarTitle.setText("");
        mSpRobotType.setItems(AppConstant.ROBOT_TYPE);
    }

    @Override
    protected void initData() {
        List<String> robotList = Arrays.asList(AppConstant.ROBOT_TYPE);
        int index = robotList.indexOf(mRobotMainResponse.getType());
        mSpRobotType.setSelectedIndex(index);
        mEtRobotNumber.setText(mRobotMainResponse.getNumber());
        mEtRobotNumber.setSelection(mRobotMainResponse.getNumber().length());
    }

    @Override
    protected void initListener() {
        mIvToolbarNavigation.setOnClickListener(view -> onBackPressed());
        mBtnUpdateRobot.setOnClickListener(view -> {
            String robotNumber = mEtRobotNumber.getText().toString().trim();
            if (TextUtils.isEmpty(robotNumber)){
                UIUtils.showToast(getString(R.string.input_robot_number));
                return;
            }
            mPresenter.updateRobot(mRobotMainResponse.getId(),robotNumber,AppConstant.ROBOT_TYPE[mSpRobotType.getSelectedIndex()]);
        });
    }

    @Override
    public void robotUpdateSuccess(RobotMainResponse robotMainResponse) {
        RobotUpdateBean robotUpdateBean = new RobotUpdateBean();
        robotUpdateBean.setItemIndex(mItemIndex);
        robotUpdateBean.setRobotMainResponse(robotMainResponse);
        mRxManager.post(AppConstant.RX_UPDATE_ROBOT,robotUpdateBean);
        finish();
    }

    @Override
    public void robotUpdateFailure(String error) {
        UIUtils.showToast(error);
    }

    @Override
    public void onBackPressedSupport() {
        showMaterialDialog(getString(R.string.info_tips),
                getString(R.string.update_exit),
                getString(R.string.dialog_ensure),
                getString(R.string.dialog_cancel),
                (dialog, which) -> {
                    dialog.dismiss();
                    super.onBackPressedSupport();
                }, (dialog, which) -> dialog.dismiss());
    }


}
