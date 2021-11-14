package com.jit.AgriIn.ui.activity.config;

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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jit.AgriIn.R;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.model.bean.ConfigUpdateBean;
import com.jit.AgriIn.model.response.ConfigMainResponse;
import com.jit.AgriIn.ui.presenter.config.ConfigMainAtPresenter;
import com.jit.AgriIn.ui.view.config.ConfigMainAtView;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.util.ui.UIUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * @author crazyZhangxl on 2018-9-28 14:23:35.
 * Describe:
 */

public class ConfigMainActivity extends BaseActivity<ConfigMainAtView, ConfigMainAtPresenter> implements ConfigMainAtView {

    @BindView(R.id.ivToolbarNavigation)
    ImageView mIvToolbarNavigation;
    @BindView(R.id.vToolbarDivision)
    View mVToolbarDivision;
    @BindView(R.id.ibAddMenu)
    ImageButton ibAddMenu;
    @BindView(R.id.tvToolbarTitle)
    TextView mTvToolbarTitle;
    @BindView(R.id.rvConfig)
    RecyclerView mRvConfig;
    @BindView(R.id.btnAddConfig)
    Button mBtnAddConfig;
    private List<String> mSpeedList;

    private static final int[] IMAGES = {
            R.mipmap.bg_topic_1, R.mipmap.bg_topic_2, R.mipmap.bg_topic_3,
            R.mipmap.bg_topic_4, R.mipmap.bg_topic_5
    };


    private BaseQuickAdapter<ConfigMainResponse,BaseViewHolder> mConfigMainAdapter;
    private List<ConfigMainResponse> mMainResponseList = new ArrayList<>();
    private List<String> mCuriseMode;

    @Override
    protected void init() {
        mSpeedList = Arrays.asList(getResources().getStringArray(R.array.array_cruise_speed));
        mCuriseMode = Arrays.asList(getResources().getStringArray(R.array.array_cruise_mode));
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_config_main;
    }

    @Override
    protected ConfigMainAtPresenter createPresenter() {
        return new ConfigMainAtPresenter(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ibAddMenu.setVisibility(View.VISIBLE);
        mTvToolbarTitle.setText(R.string.config_main);
        initAdapter();
    }

    private void initAdapter() {
        mRvConfig.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mConfigMainAdapter = new BaseQuickAdapter<ConfigMainResponse, BaseViewHolder>(R.layout.item_config,mMainResponseList) {
            @Override
            protected void convert(BaseViewHolder helper, ConfigMainResponse item) {
                ImageView ivBackground =  helper.getView(R.id.ivBackground);
                ivBackground.setImageResource(IMAGES[helper.getAdapterPosition() % IMAGES.length]);
                helper.setText(R.id.tvPondName,item.getPound_name());
                helper.setText(R.id.tvConfigRobotNumber,item.getRobert_name());
                helper.setText(R.id.tvConfigType,mCuriseMode.get(item.getType()));
                helper.setText(R.id.tvConfigCircle,String.valueOf(item.getCircle()));
                helper.setText(R.id.tvConfigSpeed,mSpeedList.get(item.getCruise_velocity()));

            }
        };
        mRvConfig.setAdapter(mConfigMainAdapter);
        setEmptyView();
    }

    private void setEmptyView() {
        View emptyView = LayoutInflater.from(this).inflate(R.layout.empty_pond_main, null, false);
        TextView tvEmptyDes =  emptyView.findViewById(R.id.tvEmptyDes);
        tvEmptyDes.setText(R.string.empty_add_config);
        mConfigMainAdapter.setEmptyView(emptyView);
    }

    @Override
    protected void initData() {
        mPresenter.queryAllConfig();

        mRxManager.on(AppConstant.RX_ADD_CONFIG, (Consumer<ConfigMainResponse>) configMainResponse -> {
            mMainResponseList.add(configMainResponse);
            mConfigMainAdapter.notifyDataSetChanged();
        });

        mRxManager.on(AppConstant.RX_UPDATE_CONFIG, (Consumer<ConfigUpdateBean>) configUpdateBean -> {
            mMainResponseList.set(configUpdateBean.getIndex(),configUpdateBean.getConfigMainResponse());
            mConfigMainAdapter.notifyItemChanged(configUpdateBean.getIndex());
        });


    }

    @Override
    protected void initListener() {
        mIvToolbarNavigation.setOnClickListener(view -> onBackPressed());

        mBtnAddConfig.setOnClickListener(view -> jumpToActivity(ConfigAddActivity.class));

        mConfigMainAdapter.setOnItemClickListener((adapter, view, position) -> jumpToConfigShow(position));

        mConfigMainAdapter.setOnItemLongClickListener((adapter, view, position) -> {
            showChooseView(position);
            return false;
        });

    }

    private void showChooseView(int position) {
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
        setViewAndListener(dialog,choose,position);
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
            jumpToConfigEdit(itemPosition);
        });

        vCancel.setOnClickListener(view -> dialog.dismiss());

        vDelete.setOnClickListener(view -> {
            dialog.dismiss();
            mPresenter.deleteConfigByID(mMainResponseList.get(itemPosition).getId(),itemPosition);
        });
    }

    private void jumpToConfigEdit(int itemPosition) {
        ConfigMainResponse configMainResponse = mMainResponseList.get(itemPosition);
        Intent intent = new Intent(this,ConfigUpdateActivity.class);
        intent.putExtra(AppConstant.EXTRA_ITEM_INDEX,itemPosition);
        intent.putExtra(AppConstant.EXTRA_IS_FROM_CONFIG_MAIN,true);
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppConstant.BUNDLE_CONFIG_BEAN,configMainResponse);
        intent.putExtras(bundle);
        jumpToActivity(intent);
    }

    private void jumpToConfigShow(int position) {
        ConfigMainResponse configMainResponse = mMainResponseList.get(position);
        Intent intent = new Intent(this,ConfigShowActivity.class);
        intent.putExtra(AppConstant.EXTRA_ITEM_INDEX,position);
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppConstant.BUNDLE_CONFIG_BEAN,configMainResponse);
        intent.putExtras(bundle);
        jumpToActivity(intent);
    }

    @Override
    public void queryConfigSuccess(List<ConfigMainResponse> configMainResponseList) {
        mMainResponseList.clear();
        mMainResponseList.addAll(configMainResponseList);
        mConfigMainAdapter.notifyDataSetChanged();
    }

    @Override
    public void queryConfigFailure(String error) {
        UIUtils.showToast(error);
    }

    @Override
    public void deleteConfigSuccess(int itemIndex) {
        mMainResponseList.remove(itemIndex);
        mConfigMainAdapter.notifyItemRemoved(itemIndex);
    }

    @Override
    public void deleteConfigFailure(String error) {
        UIUtils.showToast(error);
    }

}
