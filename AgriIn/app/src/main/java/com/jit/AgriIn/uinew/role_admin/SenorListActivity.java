package com.jit.AgriIn.uinew.role_admin;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
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
import com.example.xrecyclerview.XRecyclerView;
import com.jit.AgriIn.R;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.model.bean.SensorUpdateBean;
import com.jit.AgriIn.model.response.SensorResponse;
import com.jit.AgriIn.model.response.TermResponse;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.util.ui.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

public class SenorListActivity extends BaseActivity<SensorListView,SensorListPresenter> implements SensorListView{
    @BindView(R.id.ivToolbarNavigation)
    ImageView ivToolbarNavigation;
    @BindView(R.id.vToolbarDivision)
    View vToolbarDivision;
    @BindView(R.id.ibAddMenu)
    ImageButton ibAddMenu;
    @BindView(R.id.tvToolbarTitle)
    TextView tvToolbarTitle;
    @BindView(R.id.rvPond)
    XRecyclerView rvPond;

    int mPosition;
    int mTermId;

    private BaseQuickAdapter<SensorResponse, BaseViewHolder> mPondMainAdapter;
    private List<SensorResponse> mSensorResponses = new ArrayList<>();
    private TermResponse mTermResponse;
    @Override
    protected void init() {

        if (getIntent() != null){
            mTermResponse = (TermResponse) getIntent().getExtras().getSerializable(AppConstant.BUNDLE_POND_BEAN);
            mTermId = mTermResponse.getId();
        }
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_guding;
    }

    @Override
    protected SensorListPresenter createPresenter() {
        return new SensorListPresenter(this);
    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        ibAddMenu.setVisibility(View.VISIBLE);
        tvToolbarTitle.setText("传感器配置");
        setAdapter();
    }

    private void setAdapter() {
        rvPond.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mPondMainAdapter = new BaseQuickAdapter<SensorResponse, BaseViewHolder>(R.layout.item_sensor,mSensorResponses
        ) {
            @Override
            protected void convert(BaseViewHolder helper, SensorResponse item) {

                helper.setText(R.id.tvAddr,String.valueOf(item.getAddr()));

                if (item.getName() != null){
                    helper.setText(R.id.tvName,item.getName());
                }

                if (item.getType() != null){
                    helper.setText(R.id.tvType,item.getType());
                }

                helper.setText(R.id.tvCellId,String.valueOf(item.getCellid()));


            }
        };


        rvPond.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                //设置的刷新
//                mPage = 1;
                mPresenter.getAllSensors(mTermId);
            }

            @Override
            public void onLoadMore() {
                rvPond.noMoreLoading();
            }
        });



        rvPond.setAdapter(mPondMainAdapter);
        // 设置空布局
        View emptyView = LayoutInflater.from(this).inflate(R.layout.empty_pond_main, null, false);
        TextView tvEmptyDes =  emptyView.findViewById(R.id.tvEmptyDes);
        tvEmptyDes.setText("请添加传感器");
        mPondMainAdapter.setEmptyView(emptyView);

    }





    @Override
    protected void initData() {
//        mPage = 1;
        mPresenter.getAllSensors(mTermId);

        // 监听增加塘口信息
        mRxManager.on(AppConstant.RX_ADD_POND, (Consumer<SensorResponse>) pondMainResponse -> {
            mSensorResponses.add(pondMainResponse);
            mPondMainAdapter.notifyDataSetChanged();
        });

        mRxManager.on(AppConstant.RX_UPDATE_POND, (Consumer<SensorUpdateBean>) gudingUpdateBean -> {
            mSensorResponses.set(gudingUpdateBean.getItemPosition()-1,gudingUpdateBean.getSensorResponse());
            mPondMainAdapter.notifyItemChanged(gudingUpdateBean.getItemPosition());
        });
    }

    @Override
    protected void initListener() {
        ivToolbarNavigation.setOnClickListener(view -> onBackPressed());

        ibAddMenu.setOnClickListener(view -> {
            Intent intent = new Intent(SenorListActivity.this, SensorAddActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(AppConstant.BUNDLE_POND_BEAN,mTermResponse);
            intent.putExtras(bundle);
            jumpToActivity(intent);
        });

        /**
         * 条目点击事件
         */
        mPondMainAdapter.setOnItemClickListener((adapter, view, position) -> jumpToPondShow(position));

        /**
         * 条目长按事件
         */
        mPondMainAdapter.setOnItemLongClickListener((adapter, view, position) -> {
            mPosition = position-1;
            showChooseView(position-1);
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

        vDelete.setOnClickListener(view -> {
            dialog.dismiss();
            mPresenter.deleteSensor(String.valueOf(mSensorResponses.get(itemPosition).getId()));
        });
    }

    private void jumpToPondShow(int itemPosition){
//        PondMainResponse pondMainResponse = mIncomeResponses.get(itemPosition);
//        Intent intent = new Intent(this, PondShowActivity.class);
//        intent.putExtra(AppConstant.EXTRA_ITEM_INDEX,itemPosition);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable(AppConstant.BUNDLE_POND_BEAN,pondMainResponse);
//        intent.putExtras(bundle);
//        jumpToActivity(intent);
    }

    public void jumpToPondEdit(int itemPosition){
        SensorResponse sensorResponse = mSensorResponses.get(itemPosition);
        Intent intent = new Intent(this, SensorUpdateActivity.class);
        intent.putExtra(AppConstant.EXTRA_ITEM_INDEX,itemPosition+1);
        intent.putExtra(AppConstant.EXTRA_IS_FROM_POND_MAIN,true);
        intent.putExtra("termType",mTermResponse.getType());
        intent.putExtra("userName",mTermResponse.getUsername());
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppConstant.BUNDLE_POND_BEAN,sensorResponse);
        intent.putExtras(bundle);
        jumpToActivity(intent);
    }








    public void remove(int position) {
        mSensorResponses.remove(position);
        mPondMainAdapter.notifyItemRemoved(position+1);
        mPondMainAdapter.notifyItemRangeChanged(position+1,mSensorResponses.size()-position);
    }

    @Override
    public void deleteSensorSuccess() {
        remove(mPosition);
    }

    @Override
    public void deleteSensorFailure(String error) {
        UIUtils.showToast(error);
    }

    @Override
    public void getSensorSuccess(List<SensorResponse> sensorResponseList) {

            mSensorResponses.clear();
            mSensorResponses.addAll(sensorResponseList);
            mPondMainAdapter.notifyDataSetChanged();
            // 刷新成功
            rvPond.refreshComplete();
    }

    @Override
    public void getSensorFailure(String error) {
        UIUtils.showToast(error);
        rvPond.refreshComplete();
    }
}
