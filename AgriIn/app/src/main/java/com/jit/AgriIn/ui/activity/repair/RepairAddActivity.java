package com.jit.AgriIn.ui.activity.repair;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.library.SuperTextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jit.AgriIn.R;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.model.response.RobotMainResponse;
import com.jit.AgriIn.model.response.RobotPageResponse;
import com.jit.AgriIn.ui.presenter.repair.RepairAddAtPresenter;
import com.jit.AgriIn.ui.view.repair.RepairAddAtView;
import com.jit.AgriIn.widget.MyDialog;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.util.ui.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author crazyZhangxl on 2018-11-8 11:09:56.
 * Describe:
 */

public class RepairAddActivity extends BaseActivity<RepairAddAtView, RepairAddAtPresenter> implements RepairAddAtView {

    @BindView(R.id.ivToolbarNavigation)
    ImageView mIvToolbarNavigation;
    @BindView(R.id.vToolbarDivision)
    View mVToolbarDivision;
    @BindView(R.id.tvToolbarTitle)
    TextView mTvToolbarTitle;
    @BindView(R.id.tv_publish_now)
    TextView mTvPublishNow;
    @BindView(R.id.stvRobotChoose)
    SuperTextView mStvRobotChoose;
    @BindView(R.id.etDescription)
    EditText mEtDescription;
    private List<RobotMainResponse> mRobotBeanList = new ArrayList<>();
    private int mCurrentPage;
    private int mMaxPage;
    private boolean isFirst;
    private int robotIDSelected = -1;
    private BaseQuickAdapter<RobotMainResponse, BaseViewHolder> mRobotAdapter;
    @Override
    protected void init() {

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_repair_add;
    }

    @Override
    protected RepairAddAtPresenter createPresenter() {
        return new RepairAddAtPresenter(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mTvToolbarTitle.setText("新增机器人维修");
        mTvPublishNow.setVisibility(View.VISIBLE);
        mTvPublishNow.setText(getString(R.string.submit));
    }

    private void showPageRobotList() {
        /*  弹出dialog,键入密码进行验证*/
        mCurrentPage = 1;
        isFirst = true;
        View rgScsView = LayoutInflater.from(this).inflate(R.layout.dialog_show_robot, null);
        MyDialog robotDialog = new MyDialog(this, R.style.MyDialog);
        Window window = robotDialog.getWindow();
        window.setGravity(Gravity.CENTER);
        window.setWindowAnimations(R.style.BottomDialog);
        window.getDecorView().setPadding(50, 0, 50, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        robotDialog.setContentView(rgScsView);
        TextView tvCancel = robotDialog.findViewById(R.id.tvCancel);
        RecyclerView recyclerView = robotDialog.findViewById(R.id.recyclerView);
        initDialogRecyclerView(recyclerView, robotDialog);
        tvCancel.setOnClickListener(v -> {
            mRobotBeanList.clear();
            robotDialog.dismiss();
        });
        refreshDialog();
        robotDialog.show();

    }

    private void refreshDialog() {
        mPresenter.queryAllRobotPage(mCurrentPage);
    }


    private void initDialogRecyclerView(RecyclerView recyclerView, Dialog dialog) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRobotAdapter = new BaseQuickAdapter<RobotMainResponse, BaseViewHolder>(R.layout.item_robot_select, mRobotBeanList) {
            @Override
            protected void convert(BaseViewHolder helper, RobotMainResponse item) {
                helper.setText(R.id.tvText, item.getNumber());
            }
        };
        mRobotAdapter.setOnLoadMoreListener(() -> new Handler().postDelayed(() -> {
            if (mCurrentPage > mMaxPage) {
                mRobotAdapter.loadMoreEnd();
            } else {
                mPresenter.queryAllRobotPage(mCurrentPage);
            }
        }, 1000), recyclerView);
        mRobotAdapter.setOnItemClickListener((adapter, view, position) -> {
            RobotMainResponse robotMainResponse = mRobotBeanList.get(position);
            mStvRobotChoose.setRightString(robotMainResponse.getNumber());
            robotIDSelected = robotMainResponse.getId();
            dialog.dismiss();
        });

        recyclerView.setAdapter(mRobotAdapter);
    }


    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        mIvToolbarNavigation.setOnClickListener(v -> onBackPressed());

        mStvRobotChoose.setOnSuperTextViewClickListener(superTextView -> showPageRobotList());

        mTvPublishNow.setOnClickListener(v -> checkAndSubmit());
    }

    private void checkAndSubmit() {
        if (robotIDSelected == -1) {
            UIUtils.showToast("暂无可操作机器人，请先进行添加");
            return;
        }

        String description = mEtDescription.getText().toString().trim();
        if (TextUtils.isEmpty(description)){
            UIUtils.showToast("请输入机器人详细的状况描述!");
            return;
        }

        mPresenter.doAddRepairRobert(robotIDSelected,description);
    }

    @Override
    public void addRepairSuccess() {
        mRxManager.post(AppConstant.RX_ADD_REPAIR_INFO,true);
        finish();
    }

    @Override
    public void addRepairFailure(String msg) {
        UIUtils.showToast(msg);
    }

    @Override
    public void queryMyRobotSuccess(RobotPageResponse robotPageResponse) {
        mMaxPage = robotPageResponse.getPages();
        if (robotPageResponse.getPageNum() <= robotPageResponse.getPages()) {
            mCurrentPage++;
        }
        if (isFirst) {
            mRobotBeanList.clear();
            mRobotBeanList.addAll(robotPageResponse.getList());
            mRobotAdapter.setNewData(mRobotBeanList);
            isFirst = false;
        } else {
            if (mRobotAdapter.isLoading()) {
                mRobotAdapter.loadMoreComplete();
            }
            // 可以加载 那么页数就得+1了
            mRobotBeanList.addAll(robotPageResponse.getList());
            mRobotAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void queryMyRobotFailure(String error) {
        UIUtils.showToast(error);
    }

}
