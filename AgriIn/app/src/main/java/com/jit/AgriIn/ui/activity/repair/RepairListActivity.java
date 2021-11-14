package com.jit.AgriIn.ui.activity.repair;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jit.AgriIn.R;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.model.response.RepairStateResponse;
import com.jit.AgriIn.ui.presenter.repair.RepairListAtPresenter;
import com.jit.AgriIn.ui.view.repair.RepairListAtView;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.util.ui.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * @author crazyZhangxl on 2018-11-8 10:16:33.
 * Describe: 维修列表展示界面-----
 */

public class RepairListActivity extends BaseActivity<RepairListAtView, RepairListAtPresenter> implements RepairListAtView {

    @BindView(R.id.ivToolbarNavigation)
    ImageView mIvToolbarNavigation;
    @BindView(R.id.vToolbarDivision)
    View mVToolbarDivision;
    @BindView(R.id.ibAddMenu)
    ImageButton ibAddMenu;
    @BindView(R.id.tvToolbarTitle)
    TextView mTvToolbarTitle;
    @BindView(R.id.rvRepairList)
    RecyclerView mRvRepairList;



    private static final int[] IMAGES = {
            R.mipmap.bg_topic_1, R.mipmap.bg_topic_2, R.mipmap.bg_topic_3,
            R.mipmap.bg_topic_4, R.mipmap.bg_topic_5
    };


    private BaseQuickAdapter<RepairStateResponse,BaseViewHolder> mRepairListAdapter;
    private List<RepairStateResponse> mResponseList = new ArrayList<>();
    @Override
    protected void init() {

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_repair_list;
    }

    @Override
    protected RepairListAtPresenter createPresenter() {
        return new RepairListAtPresenter(this);
    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        ibAddMenu.setVisibility(View.VISIBLE);
        mTvToolbarTitle.setText("机器人维修");
        initAdapter();
    }

    /**
     * 问题技术支持的ID 缺少技术人信息
     * 机器人ID 缺少机器人名称
     * status状态对应的关系
     */
    private void initAdapter() {
        mRvRepairList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        if (mRepairListAdapter == null){
            mRepairListAdapter = new BaseQuickAdapter<RepairStateResponse, BaseViewHolder>(R.layout.item_repair_state,mResponseList) {
                @Override
                protected void convert(BaseViewHolder helper, RepairStateResponse item) {
                    helper.setText(R.id.tvStateShow,AppConstant.REPAIR_STATUS[item.getStatus()]);
                    helper.setText(R.id.tvRepairDescription,item.getDescription());
                    helper.setText(R.id.tvRobertName,String.valueOf(item.getRobert_id()));
                    helper.setText(R.id.tvRepairTime,item.getTime());
                }
            };
        }
        mRvRepairList.setAdapter(mRepairListAdapter);
    }

    @Override
    protected void initData() {
        mPresenter.doQueryAllRepairList();
        mRxManager.on(AppConstant.RX_ADD_REPAIR_INFO, (Consumer<Boolean>) aBoolean -> {
            if (aBoolean){
                mPresenter.doQueryAllRepairList();
            }
        });
    }

    @Override
    protected void initListener() {
        mIvToolbarNavigation.setOnClickListener(v -> onBackPressed());

        ibAddMenu.setOnClickListener(v -> jumpToActivity(RepairAddActivity.class));
    }

    @Override
    public void queryRepairListSuccess(List<RepairStateResponse> mLists) {
        if (mLists != null){
            mResponseList.clear();
            mResponseList.addAll(mLists);
            mRepairListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void queryRepairListFailure(String error) {
        UIUtils.showToast(error);
    }
}
