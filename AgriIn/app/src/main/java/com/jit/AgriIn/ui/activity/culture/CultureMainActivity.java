package com.jit.AgriIn.ui.activity.culture;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jit.AgriIn.R;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.model.response.PondMainResponse;
import com.jit.AgriIn.ui.presenter.culture.CulMainAtPresenter;
import com.jit.AgriIn.ui.view.culture.CulMainAtView;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.util.ui.ResHelper;
import com.zxl.baselib.util.ui.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author crazyZhangxl on 2018-10-10 9:29:03.
 * Describe: 塘口列表展示----下载查看---进入
 */

public class CultureMainActivity extends BaseActivity<CulMainAtView, CulMainAtPresenter> implements CulMainAtView {

    @BindView(R.id.ivToolbarNavigation)
    ImageView mIvToolbarNavigation;
    @BindView(R.id.vToolbarDivision)
    View mVToolbarDivision;
    @BindView(R.id.ibAddMenu)
    ImageButton ibAddMenu;
    @BindView(R.id.tvToolbarTitle)
    TextView mTvToolbarTitle;
    @BindView(R.id.rvDataMg)
    RecyclerView mRvDataMg;
    @BindView(R.id.tvRecordNum)
    TextView mTvRecordNum;

    private List<PondMainResponse> mPondResponseList = new ArrayList<>();
    private BaseQuickAdapter<PondMainResponse,BaseViewHolder> mDataAdapter;
    private int mSelectPosition = -1;
    @Override
    protected void init() {
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_culture_main;
    }

    @Override
    protected CulMainAtPresenter createPresenter() {
        return new CulMainAtPresenter(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ibAddMenu.setVisibility(View.VISIBLE);
        mTvToolbarTitle.setText(R.string.title_data_manage);
        initAdapter();
    }

    /**
     * 设置对应的adapter
     */
    private void initAdapter() {
        mRvDataMg.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mDataAdapter = new BaseQuickAdapter<PondMainResponse, BaseViewHolder>(R.layout.item_culture_main,mPondResponseList) {
            @Override
            protected void convert(BaseViewHolder helper, PondMainResponse item) {
                if (mSelectPosition == helper.getAdapterPosition()) {
                    helper.getView(R.id.llData).setBackgroundColor(ResHelper.getColor(R.color.item_select_color));
                }
                else {
                    helper.getView(R.id.llData).setBackgroundColor(ResHelper.getColor(R.color.white));
                }
                helper.setText(R.id.tvPondId,item.getName());
            }
        };
        mRvDataMg.setAdapter(mDataAdapter);
        setEmptyView();

    }

    private void setEmptyView() {
        View emptyView = LayoutInflater.from(this).inflate(R.layout.empty_pond_main, null, false);
        TextView tvEmptyDes =  emptyView.findViewById(R.id.tvEmptyDes);
        tvEmptyDes.setText(R.string.empty_add_pond);
        mDataAdapter.setEmptyView(emptyView);
    }

    @Override
    protected void initData() {
        mPresenter.loadPondMainData();
    }

    private void updateNum(){
        mTvRecordNum.setText(getString(R.string.format_logcat_num,String.valueOf(mPondResponseList.size())));
    }

    @Override
    protected void initListener() {
        mIvToolbarNavigation.setOnClickListener(view -> onBackPressed());

        ibAddMenu.setOnClickListener(view -> jumpToActivity(CultureSubmitActivity.class));

        mDataAdapter.setOnItemClickListener((adapter, view, position) -> {
             if (mSelectPosition == -1){
                mDataAdapter.notifyItemChanged(position);
            }else if (mSelectPosition != position){
                mDataAdapter.notifyItemChanged(mSelectPosition);
                mDataAdapter.notifyItemChanged(position);
            }
            mSelectPosition = position;
            Intent intent = new Intent(CultureMainActivity.this,CultureLogActivity.class);
            intent.putExtra(AppConstant.POND_ID_SELECTED,mPondResponseList.get(position).getId());
            intent.putExtra(AppConstant.POND_NAME_SELECTED,mPondResponseList.get(position).getName());
            intent.putExtra(AppConstant.POND_ADDRESS_SELECTED,mPondResponseList.get(position).getLocation());
            jumpToActivity(intent);
        });
    }

    @Override
    public void queryPondSuccess(List<PondMainResponse> responses) {
        if (responses != null && responses.size() != 0){
            mPondResponseList.clear();
            mPondResponseList.addAll(responses);
            mDataAdapter.notifyDataSetChanged();
            updateNum();
        }
    }

    @Override
    public void queryPondFailure(String error) {
        UIUtils.showToast(error);
    }

}
