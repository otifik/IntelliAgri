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
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.allen.library.SuperTextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.xrecyclerview.XRecyclerView;
import com.jit.AgriIn.R;
import com.jit.AgriIn.model.response.AutoStatusResponse;
import com.jit.AgriIn.widget.MyDialog;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BaseFragment;
import com.zxl.baselib.util.ui.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class UpDownFragment extends BaseFragment<AutoCtrlView,AutoCtrlPresenter> implements AutoCtrlView{
    @BindView(R.id.rvPond)
    XRecyclerView rvPond;

    int mPosition = 0;
    int mCellId;
    private boolean isChecked = false;

    private BaseQuickAdapter<AutoStatusResponse, BaseViewHolder> mPondMainAdapter;
    private List<AutoStatusResponse> mEquipResponses = new ArrayList<>();
    private AutoStatusResponse autoStatusResponse = new AutoStatusResponse();


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
    protected AutoCtrlPresenter createPresenter() {
        return new AutoCtrlPresenter((BaseActivity) getActivity());
    }


    @Override
    public void initView(View rootView) {
        setAdapter();
    }




    private void setAdapter() {
        rvPond.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        mPondMainAdapter = new BaseQuickAdapter<AutoStatusResponse, BaseViewHolder>(R.layout.item_updown,mEquipResponses
        ) {
            @Override
            protected void convert(BaseViewHolder helper, AutoStatusResponse item) {
                SuperTextView stvName = helper.getView(R.id.stvName);

                if (item.getAutofg() == 1){
                    stvName.setSwitchIsChecked(true);
                }else {
                    stvName.setSwitchIsChecked(false);
                }

                helper.setText(R.id.tvWnUp,String.valueOf(item.getWnup()));
                helper.setText(R.id.tvWnDw,String.valueOf(item.getWndw()));
                helper.setText(R.id.tvAtUp,String.valueOf(item.getActup()));
                helper.setText(R.id.tvAtDw,String.valueOf(item.getActdw()));

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
                                    mPresenter.CtrlAuto(item.getId(),item.getParam(),item.getParamid(),item.getWnup(),item.getWndw(),item.getActup(),item.getActdw(),isOpen);
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
                mPresenter.getAutoStatus(mCellId);
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
        mPresenter.getAutoStatus(mCellId);
    }

    @Override
    public void initListener() {

        mPondMainAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                autoStatusResponse = mEquipResponses.get(position-1);


                View latitudeView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_updow_ctrl, null);
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
                EditText etWnup = latitudeView.findViewById(R.id.etWnup);
                EditText etWndw = latitudeView.findViewById(R.id.etWndw);
                EditText etAtUp = latitudeView.findViewById(R.id.etAtUp);
                EditText etAtDw = latitudeView.findViewById(R.id.etAtDw);
                TextView tvCancel = latitudeView.findViewById(R.id.tvCancel);
                TextView tvEnsure = latitudeView.findViewById(R.id.tvEnsure);

                tvTitle.setText("阈值设置（" + autoStatusResponse.getParam() + "）");
                etWnup.setText(String.valueOf(autoStatusResponse.getWnup()));
                etWndw.setText(String.valueOf(autoStatusResponse.getWndw()));
                etAtUp.setText(String.valueOf(autoStatusResponse.getActup()));
                etAtDw.setText(String.valueOf(autoStatusResponse.getActdw()));


                tvCancel.setOnClickListener(v -> latitudeDialog.dismiss());
                tvEnsure.setOnClickListener(v -> {
                    String wnUp = etWnup.getText().toString().trim();
                    String wnDw = etWndw.getText().toString().trim();
                    String atUp = etAtUp.getText().toString().trim();
                    String atDw = etAtDw.getText().toString().trim();
                    if (TextUtils.isEmpty(wnUp) || TextUtils.isEmpty(wnDw) || TextUtils.isEmpty(atUp) || TextUtils.isEmpty(atDw)) {
                        UIUtils.showToast("数据不能为空");
                        return;
                    }
                    mPresenter.CtrlAuto(autoStatusResponse.getId(), autoStatusResponse.getParam(), autoStatusResponse.getParamid(),
                            Float.valueOf(wnUp), Float.valueOf(wnDw), Float.valueOf(atUp), Float.valueOf(atDw), autoStatusResponse.getAutofg());
                    latitudeDialog.dismiss();
                });


                latitudeDialog.show();
            }
        });

    }










    @Override
    public void getAutoStatusSuccess(List<AutoStatusResponse> equipResponseList) {
        mEquipResponses.clear();
        mEquipResponses.addAll(equipResponseList);
        mPondMainAdapter.notifyDataSetChanged();
        // 刷新成功
        rvPond.refreshComplete();
    }

    @Override
    public void getAutoStatusFailure(String error) {
        UIUtils.showToast(error);
        rvPond.refreshComplete();
    }

    @Override
    public void ctrlAutoSuccess(Boolean isSuccess) {
        if (isSuccess){
            UIUtils.showToast("设置成功");
            mPresenter.getAutoStatus(mCellId);
        }else {
            mPresenter.getAutoStatus(mCellId);
            UIUtils.showToast("设置失败");
        }
    }

    @Override
    public void ctrlAutoFailure(String error) {
        UIUtils.showToast(error);
    }
}
