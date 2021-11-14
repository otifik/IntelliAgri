package com.jit.AgriIn.uinew.first.data.autoctrl;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.allen.library.SuperTextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.xrecyclerview.XRecyclerView;
import com.jit.AgriIn.R;
import com.jit.AgriIn.model.response.AutoTimeStatusResponse;
import com.jit.AgriIn.widget.MyDialog;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BaseFragment;
import com.zxl.baselib.util.ui.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class TimeCtrlFragment extends BaseFragment<AutoTimeCtrlView,AutoTimeCtrlPresenter> implements AutoTimeCtrlView{
    @BindView(R.id.rvPond)
    XRecyclerView rvPond;
    TextView mTvTime;

    int mPosition = 0;
    int mCellId;
    private boolean isChecked = false;

    private BaseQuickAdapter<AutoTimeStatusResponse, BaseViewHolder> mPondMainAdapter;
    private List<AutoTimeStatusResponse> mEquipResponses = new ArrayList<>();
    private AutoTimeStatusResponse autoStatusResponse = new AutoTimeStatusResponse();


    @Override
    public void init() {
        if (getActivity().getIntent() != null){
            mCellId = getActivity().getIntent().getIntExtra("cellId",-100);
        }
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_autoctrl;
    }

    @Override
    protected AutoTimeCtrlPresenter createPresenter() {
        return new AutoTimeCtrlPresenter((BaseActivity) getActivity());
    }


    @Override
    public void initView(View rootView) {
        setAdapter();
    }




    private void setAdapter() {
        rvPond.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        mPondMainAdapter = new BaseQuickAdapter<AutoTimeStatusResponse, BaseViewHolder>(R.layout.item_autotime,mEquipResponses
        ) {
            @Override
            protected void convert(BaseViewHolder helper, AutoTimeStatusResponse item) {
                SuperTextView stvName = helper.getView(R.id.stvName);

                if (item.getAutofg() == 1){
                    stvName.setSwitchIsChecked(true);
                }else {
                    stvName.setSwitchIsChecked(false);
                }

                helper.setText(R.id.tvTime,item.getTime());
                if (item.getOpt() == 0){
                    helper.setText(R.id.tvOption,"自动关闭");
                }else if (item.getOpt() == 1){
                    helper.setText(R.id.tvOption,"自动打开");
                }

                if (item.getParam() != null){
                    helper.setText(R.id.tvType,"(" + item.getParam() +")");
                }

                stvName.setSwitchCheckedChangeListener(new SuperTextView.OnSwitchCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                        //判断是不是点击触发的，否则当我setChecked()时会触发此listener
                        if(!compoundButton.isPressed()){
                            return;
                        }else {
                            if (item.getAutofg() == 1){
                                isChecked = true;
                            }else {
                                isChecked = false;
                            }
                            String title = isChecked?"关闭":"打开";
                            int isOpen =  isChecked?0:1;
                            showMaterialDialog(null, "确定" + title + "设备?", "确定", "取消", new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    dialog.dismiss();
                                    mPresenter.CtrlAutoTime(item.getId(),item.getParam(),item.getTime(),item.getOpt(),isOpen);
                                }
                            }, new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    dialog.dismiss();
                                    stvName.setSwitchIsChecked(isChecked);
                                }
                            });
                        }
                    }
                });
            }
        };


        rvPond.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                //设置的刷新
//                mPage = 1;
                mPresenter.getAutoTimeStatus(mCellId);
            }

            @Override
            public void onLoadMore() {
                rvPond.noMoreLoading();
            }
        });



        rvPond.setAdapter(mPondMainAdapter);
        // 设置空布局
        View emptyView = LayoutInflater.from(getActivity()).inflate(R.layout.empty_pond_main, null, false);
        TextView tvEmptyDes =  emptyView.findViewById(R.id.tvEmptyDes);
        tvEmptyDes.setText("暂无控制设备");
        mPondMainAdapter.setEmptyView(emptyView);

    }





    @Override
    public void initData() {
//        mPage = 1;
        mPresenter.getAutoTimeStatus(mCellId);
    }

    @Override
    public void initListener() {

        mPondMainAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                autoStatusResponse = mEquipResponses.get(position-1);


                View latitudeView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_autotime_ctrl, null);
                MyDialog latitudeDialog = new MyDialog(getActivity(), R.style.MyDialog);
                Window window = latitudeDialog.getWindow();
                window.setGravity(Gravity.CENTER);
                window.setWindowAnimations(R.style.BottomDialog);
                window.getDecorView().setPadding(50, 0, 50, 0);
                WindowManager.LayoutParams lp = window.getAttributes();
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                window.setAttributes(lp);
                latitudeDialog.setContentView(latitudeView);

                TextView tvTitle = latitudeView.findViewById(R.id.tvTitle);
                mTvTime = latitudeView.findViewById(R.id.tvTime);
                TextView tvOption = latitudeView.findViewById(R.id.tvOption);

                TextView tvCancel = latitudeView.findViewById(R.id.tvCancel);
                TextView tvEnsure = latitudeView.findViewById(R.id.tvEnsure);

                tvTitle.setText("阈值设置（" + autoStatusResponse.getParam() + "）");
                if (autoStatusResponse.getOpt() == 0){
                    tvOption.setText("自动关闭");
                }else if (autoStatusResponse.getOpt() == 1){
                    tvOption.setText("自动打开");
                }

                mTvTime.setText(autoStatusResponse.getTime());
                mTvTime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showCustomDatePicker();
                    }
                });


                tvCancel.setOnClickListener(v -> latitudeDialog.dismiss());
                tvEnsure.setOnClickListener(v -> {
                    if (TextUtils.isEmpty(mTvTime.getText().toString().trim())) {
                        UIUtils.showToast("数据不能为空");
                        return;
                    }
                    mPresenter.CtrlAutoTime(autoStatusResponse.getId(), autoStatusResponse.getParam(), mTvTime.getText().toString().trim(),
                            autoStatusResponse.getOpt(), autoStatusResponse.getAutofg());
                    latitudeDialog.dismiss();
                });


                latitudeDialog.show();
            }
        });

    }





    @Override
    public void getAutoTimeStatusSuccess(List<AutoTimeStatusResponse> equipResponseList) {
        mEquipResponses.clear();
        mEquipResponses.addAll(equipResponseList);
        mPondMainAdapter.notifyDataSetChanged();
        // 刷新成功
        rvPond.refreshComplete();
    }

    @Override
    public void getAutoTimeStatusFailure(String error) {
        UIUtils.showToast(error);
        rvPond.refreshComplete();
    }

    @Override
    public void ctrlAutoTimeSuccess(Boolean isSucess) {
        if (isSucess){
            UIUtils.showToast("设置成功");
            mPresenter.getAutoTimeStatus(mCellId);
        }else {
            mPresenter.getAutoTimeStatus(mCellId);
            UIUtils.showToast("设置失败");
        }
    }

    @Override
    public void ctrlAutoTimeFailure(String error) {
        UIUtils.showToast(error);
    }


    public void showCustomDatePicker() {
        new MaterialDialog.Builder(getActivity())
                .title("时间选择")
                .customView(R.layout.dialog_timepicker, false)
                .positiveText(android.R.string.ok)
                .negativeText(android.R.string.cancel)
                .onPositive((dialog, which) -> {
                    TimePicker timePicker = (TimePicker) dialog.findViewById(R.id.timePicker);
                    mTvTime.setText(String.format("%02d:%02d", timePicker.getHour(), timePicker.getMinute()) + ":00");
                })
                .show();
    }
}
