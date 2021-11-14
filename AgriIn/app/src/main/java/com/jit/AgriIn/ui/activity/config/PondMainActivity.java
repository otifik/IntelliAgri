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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jit.AgriIn.R;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.model.bean.PondUpdateBean;
import com.jit.AgriIn.model.response.PondMainResponse;
import com.jit.AgriIn.ui.presenter.config.PondMainAtPresenter;
import com.jit.AgriIn.ui.view.config.PondMainAtView;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.util.ui.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * @author zxl on 2018/08/29.
 * discription: 塘口设置总览
 */
public class PondMainActivity extends BaseActivity<PondMainAtView, PondMainAtPresenter> implements PondMainAtView {
    @BindView(R.id.ivToolbarNavigation)
    ImageView ivToolbarNavigation;
    @BindView(R.id.vToolbarDivision)
    View vToolbarDivision;
    @BindView(R.id.ibAddMenu)
    ImageButton ibAddMenu;
    @BindView(R.id.tvToolbarTitle)
    TextView tvToolbarTitle;
    @BindView(R.id.rvPond)
    RecyclerView rvPond;
    private BaseQuickAdapter<PondMainResponse,BaseViewHolder> mPondMainAdapter;
    private List<PondMainResponse> mPondMainList = new ArrayList<>();

    private static final int[] IMAGES = {
            R.mipmap.bg_topic_1, R.mipmap.bg_topic_2, R.mipmap.bg_topic_3,
            R.mipmap.bg_topic_4, R.mipmap.bg_topic_5
    };

    @Override
    protected void init() {

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_pond_main;
    }

    @Override
    protected PondMainAtPresenter createPresenter() {
        return new PondMainAtPresenter(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ibAddMenu.setVisibility(View.VISIBLE);
        tvToolbarTitle.setText(R.string.pond_manage);
        setAdapter();
    }

    private void setAdapter() {
        rvPond.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mPondMainAdapter = new BaseQuickAdapter<PondMainResponse, BaseViewHolder>(R.layout.item_pond,mPondMainList) {
            @Override
            protected void convert(BaseViewHolder helper, PondMainResponse item) {

              helper.setText(R.id.tvPondName,item.getName());
              helper.setText(R.id.tvPondWidth,getString(R.string.pond_measure_suffix,String.valueOf(item.getWidth())));
              helper.setText(R.id.tvPondLength,getString(R.string.pond_measure_suffix,String.valueOf(item.getLength())));
              helper.setText(R.id.tvPondDepth,getString(R.string.pond_measure_suffix,String.valueOf(item.getDepth())));
              helper.setText(R.id.tvPondLocation,item.getLocation());
            }
        };
        rvPond.setAdapter(mPondMainAdapter);
        // 设置空布局
        View emptyView = LayoutInflater.from(this).inflate(R.layout.empty_pond_main, null, false);
        TextView tvEmptyDes =  emptyView.findViewById(R.id.tvEmptyDes);
        tvEmptyDes.setText(R.string.empty_add_pond);
        mPondMainAdapter.setEmptyView(emptyView);
    }

    @Override
    protected void initData() {
        mPresenter.loadPondMainData();
        // 监听增加塘口信息
        mRxManager.on(AppConstant.RX_ADD_POND, new Consumer<PondMainResponse>() {
            @Override
            public void accept(PondMainResponse pondMainResponse) throws Exception {
                mPondMainList.add(pondMainResponse);
                mPondMainAdapter.notifyDataSetChanged();
            }
        });

        mRxManager.on(AppConstant.RX_UPDATE_POND, new Consumer<PondUpdateBean>() {
            @Override
            public void accept(PondUpdateBean pondUpdateBean) throws Exception {
                mPondMainList.set(pondUpdateBean.getItemPosition(),pondUpdateBean.getPondMainResponse());
                mPondMainAdapter.notifyItemChanged(pondUpdateBean.getItemPosition());

            }
        });
    }

    @Override
    protected void initListener() {
        ivToolbarNavigation.setOnClickListener(view -> onBackPressed());

        ibAddMenu.setOnClickListener(view -> jumpToActivity(PondAddActivity.class));

        /**
         * 条目点击事件
         */
        mPondMainAdapter.setOnItemClickListener((adapter, view, position) -> jumpToPondShow(position));

        /**
         * 条目长按事件
         */
        mPondMainAdapter.setOnItemLongClickListener((adapter, view, position) -> {
            showChooseView(position);
            return false;
        });
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
        vEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                jumpToPondEdit(itemPosition);
            }
        });

        vCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        vDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                mPresenter.delPondByID(mPondMainList.get(itemPosition).getId(),itemPosition);
            }
        });
    }

    private void jumpToPondShow(int itemPosition){
        PondMainResponse pondMainResponse = mPondMainList.get(itemPosition);
        Intent intent = new Intent(this,PondShowActivity.class);
        intent.putExtra(AppConstant.EXTRA_ITEM_INDEX,itemPosition);
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppConstant.BUNDLE_POND_BEAN,pondMainResponse);
        intent.putExtras(bundle);
        jumpToActivity(intent);
    }

    public void jumpToPondEdit(int itemPosition){
        PondMainResponse pondMainResponse = mPondMainList.get(itemPosition);
        Intent intent = new Intent(this,PondUpdateActivity.class);
        intent.putExtra(AppConstant.EXTRA_ITEM_INDEX,itemPosition);
        intent.putExtra(AppConstant.EXTRA_IS_FROM_POND_MAIN,true);
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppConstant.BUNDLE_POND_BEAN,pondMainResponse);
        intent.putExtras(bundle);
        jumpToActivity(intent);
    }


    @Override
    public void queryPondSuccess(List<PondMainResponse> responses) {
        mPondMainList.clear();
        mPondMainList.addAll(responses);
        mPondMainAdapter.notifyDataSetChanged();
    }

    @Override
    public void queryPondFailure(String error) {
        UIUtils.showToast(error);
    }

    @Override
    public void delPondSuccess(int itemPosition) {
        mPondMainList.remove(itemPosition);
        mPondMainAdapter.notifyItemRemoved(itemPosition);
    }

    @Override
    public void delPondFailure(String error) {
        UIUtils.showToast(error);
    }
}
