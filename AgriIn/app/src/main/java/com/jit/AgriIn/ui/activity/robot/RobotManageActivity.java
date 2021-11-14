package com.jit.AgriIn.ui.activity.robot;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jit.AgriIn.R;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.model.bean.RobotUpdateBean;
import com.jit.AgriIn.model.response.RobotMainResponse;
import com.jit.AgriIn.model.response.RobotPageResponse;
import com.jit.AgriIn.ui.activity.config.ConfigAddActivity;
import com.jit.AgriIn.ui.presenter.robot.RobotManagePresenter;
import com.jit.AgriIn.ui.view.robot.RobotManageView;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.util.ui.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * @author crazyZhangxl on 2018-9-28 15:17:28.
 * Describe: 机器人统筹管理
 */

public class RobotManageActivity extends BaseActivity<RobotManageView, RobotManagePresenter> implements RobotManageView {
    @BindView(R.id.ivToolbarNavigation)
    ImageView mIvToolbarNavigation;
    @BindView(R.id.ibAddMenu)
    ImageButton ibAddMenu;
    @BindView(R.id.tvToolbarTitle)
    TextView mTvToolbarTitle;
    @BindView(R.id.rvRobot)
    RecyclerView mRvRobot;


    private BaseQuickAdapter<RobotMainResponse,BaseViewHolder> mRobotAdapter;
    private List<RobotMainResponse> mMainResponseList = new ArrayList<>();

    private int mCurrentPage;
    private int mMaxPage;
    private boolean isFirst;
    @Override
    protected void init() {

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_robot_manage;
    }

    @Override
    protected RobotManagePresenter createPresenter() {
        return new RobotManagePresenter(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ibAddMenu.setVisibility(View.VISIBLE);
        initTitle();
        initAdapter();
    }

    private void initAdapter() {
        mRvRobot.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mRobotAdapter = new BaseQuickAdapter<RobotMainResponse, BaseViewHolder>(R.layout.item_robot,mMainResponseList) {
            @Override
            protected void convert(BaseViewHolder helper, RobotMainResponse item) {
                helper.setText(R.id.tvRobotNumber,item.getNumber());
                helper.setText(R.id.tvRobotCg,item.getType());
                helper.setText(R.id.tvPond,item.getFeed_name());
            }
        };
        mRobotAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                new android.os.Handler().postDelayed(() -> {
                    if (mCurrentPage > mMaxPage) {
                        mRobotAdapter.loadMoreEnd();
                    } else {
                        mPresenter.queryAllRobotPage(mCurrentPage);
                    }
                }, 1000);
            }
        },mRvRobot);

        mRvRobot.setAdapter(mRobotAdapter);
        // 设置空布局
        View emptyView = LayoutInflater.from(this).inflate(R.layout.empty_pond_main, null, false);
        TextView tvEmptyDes =  emptyView.findViewById(R.id.tvEmptyDes);
        tvEmptyDes.setText(R.string.empty_add_robot);
        mRobotAdapter.setEmptyView(emptyView);
    }

    private void initTitle() {
        mTvToolbarTitle.setText(R.string.robot_manage);
    }

    @Override
    protected void initData() {
        // 第一次加载数据
        mCurrentPage = 1;
        isFirst = true;
        mPresenter.queryAllRobotPage(mCurrentPage);
    }

    @Override
    protected void initListener() {
        mIvToolbarNavigation.setOnClickListener(view -> onBackPressed());

        ibAddMenu.setOnClickListener(view -> jumpToActivity(RobotAddActivity.class));

        mRxManager.on(AppConstant.RX_ADD_ROBOT, (Consumer<RobotMainResponse>) robotMainResponse -> {
            mMainResponseList.add(robotMainResponse);
            mRobotAdapter.notifyDataSetChanged();
        });

        mRxManager.on(AppConstant.RX_UPDATE_ROBOT, (Consumer<RobotUpdateBean>) robotUpdateBean -> {
            mMainResponseList.set(robotUpdateBean.getItemIndex(),robotUpdateBean.getRobotMainResponse());
            mRobotAdapter.notifyItemChanged(robotUpdateBean.getItemIndex());
        });


        mRobotAdapter.setOnItemLongClickListener((adapter, view, position) -> {
            showChooseView(position);
            return false;
        });

        /**
         * 条目点击事件
         */
        mRobotAdapter.setOnItemClickListener((adapter, view, position) -> jumpRobotEdit(position));
    }


    private void showChooseView(int itemPosition) {
        Dialog dialog = new Dialog(this,R.style.MyDialog);
        View choose = LayoutInflater.from(this).inflate(R.layout.dialog_choose, null);
        dialog.setCancelable(true);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.AnimBottom);
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        dialog.setContentView(choose);
        setViewAndListener(dialog,choose,itemPosition);
        dialog.show();

    }

    /**
     * 设置弹出框的点击事件等
     * @param dialog
     * @param choose
     * @param itemPosition
     */
    private void setViewAndListener(Dialog dialog, View choose, int itemPosition) {
        View vEdit =  choose.findViewById(R.id.tvChooseEdit);
        View vCancel =  choose.findViewById(R.id.tvChooseCancel);
        View vDelete = choose.findViewById(R.id.tvChooseDel);
        vEdit.setOnClickListener(view -> {
            dialog.dismiss();
//            jumpRobotEdit(itemPosition);
            jumpToActivity(ConfigAddActivity.class);

        });

        vCancel.setOnClickListener(view -> dialog.dismiss());

        vDelete.setOnClickListener(view -> {
            dialog.dismiss();
            mPresenter.deleteRobot(mMainResponseList.get(itemPosition).getId(),itemPosition);
        });
    }


    public void jumpRobotEdit(int itemPosition){
        RobotMainResponse robotMainResponse = mMainResponseList.get(itemPosition);
        Intent intent = new Intent(this,RobotUpdateActivity.class);
        intent.putExtra(AppConstant.EXTRA_ITEM_INDEX,itemPosition);
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppConstant.BUNDLE_ROBOT_BEAN,robotMainResponse);
        intent.putExtras(bundle);
        jumpToActivity(intent);
    }

//    @Override
//    public void queryMyRobotSuccess(List<RobotMainResponse> robotMainResponseList) {
//        mMainResponseList.clear();
//        mMainResponseList.addAll(robotMainResponseList);
//        mRobotAdapter.notifyDataSetChanged();
//    }

    @Override
    public void queryMyRobotSuccess(RobotPageResponse robotPageResponse) {
        mMaxPage = robotPageResponse.getPages();
        if ( robotPageResponse.getPageNum() <= robotPageResponse.getPages()){
            mCurrentPage ++;
        }
        if (isFirst){
            mMainResponseList.clear();
            mMainResponseList.addAll(robotPageResponse.getList());
            mRobotAdapter.setNewData(mMainResponseList);
            isFirst = false;
        }else {
            if (mRobotAdapter.isLoading()){
                mRobotAdapter.loadMoreComplete();
            }
            // 可以加载 那么页数就得+1了
            mMainResponseList.addAll(robotPageResponse.getList());
            mRobotAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void queryMyRobotFailure(String error) {
        UIUtils.showToast(error);
    }

    @Override
    public void deleteRobotSuccess(int itemPosition) {
        mMainResponseList.remove(itemPosition);
        mRobotAdapter.notifyItemRemoved(itemPosition);
    }

    @Override
    public void deleteRobotFailure(String error) {
        UIUtils.showToast(error);
    }


}
