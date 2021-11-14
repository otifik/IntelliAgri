package com.jit.AgriIn.uinew.first.data.ctrl;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.allen.library.SuperTextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.xrecyclerview.XRecyclerView;
import com.jit.AgriIn.R;
import com.jit.AgriIn.model.cache.UserCache;
import com.jit.AgriIn.model.response.CtrlResponse;
import com.jit.AgriIn.model.response.EquipStatusResponse;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.util.ui.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CtrlActivity extends BaseActivity<CtrlView,CtrlPresenter> implements CtrlView{
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

    int mPosition = 0;
    int mCellId;
    private boolean isChecked = false;

    private BaseQuickAdapter<EquipStatusResponse, BaseViewHolder> mPondMainAdapter;
    private List<EquipStatusResponse> mEquipResponses = new ArrayList<>();
    private EquipStatusResponse mEquipResponse = new EquipStatusResponse();
    @Override
    protected void init() {

        if (getIntent() != null){
            mCellId = getIntent().getIntExtra("cellId",-100);
        }
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_guding;
    }

    @Override
    protected CtrlPresenter createPresenter() {
        return new CtrlPresenter(this);
    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        ibAddMenu.setVisibility(View.VISIBLE);
        tvToolbarTitle.setText("设备列表");
        setAdapter();
    }

    private void setAdapter() {
        rvPond.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mPondMainAdapter = new BaseQuickAdapter<EquipStatusResponse, BaseViewHolder>(R.layout.item_equip_ctrl,mEquipResponses
        ) {
            @Override
            protected void convert(BaseViewHolder helper, EquipStatusResponse item) {
                SuperTextView stvName = helper.getView(R.id.stvName);
                if (item.getName() != null){
                    stvName.setLeftString(item.getName());
                }

                if (item.getStatus() == 1){
                    stvName.setSwitchIsChecked(true);
                }else {
                    stvName.setSwitchIsChecked(false);
                }

                if (item.getType() != null){
                    helper.setText(R.id.tvType,"(" + item.getType() +")");
                }

                stvName.setSwitchCheckedChangeListener(new SuperTextView.OnSwitchCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                        //判断是不是点击触发的，否则当我setChecked()时会触发此listener
                        if(!compoundButton.isPressed()){
                            return;
                        }else {
                            if (item.getStatus() == 1){
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
                                    mPresenter.ctrlEquip(UserCache.getUserUser_id(),item.getEpid(),isOpen);
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
                mPresenter.getCellEquips(mCellId);
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
        tvEmptyDes.setText("暂无控制设备");
        mPondMainAdapter.setEmptyView(emptyView);

    }





    @Override
    protected void initData() {
//        mPage = 1;
        mPresenter.getCellEquips(mCellId);
        ibAddMenu.setVisibility(View.GONE);
    }

    @Override
    protected void initListener() {
        ivToolbarNavigation.setOnClickListener(view -> onBackPressed());


//
//        mPondMainAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//
//                mEquipResponse = mEquipResponses.get(position - 1);
//                mPosition = position - 1;
//
//                if (mEquipResponse.getStatus() == 1){
//                    isChecked = true;
//                }else {
//                    isChecked = false;
//                }
//
//                String title = isChecked?"关闭":"打开";
//                int isOpen =  isChecked?0:1;
//                showMaterialDialog(null, "确定" + title + "设备?", "确定", "取消", new MaterialDialog.SingleButtonCallback() {
//                    @Override
//                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                                  dialog.dismiss();
//                                  mPresenter.ctrlEquip(UserCache.getUserUser_id(),mEquipResponse.getId(),isOpen);
//                    }
//                }, new MaterialDialog.SingleButtonCallback() {
//                    @Override
//                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                                  dialog.dismiss();
////                                  mPresenter.ctrlEquip(UserCache.getUserUser_id(),mEquipResponse.getId(),1);
//                    }
//                });
//
//
//            }
//        });




    }









    @Override
    public void getCellEquipStatusSuccess(List<EquipStatusResponse> equipResponseList) {
        mEquipResponses.clear();
        mEquipResponses.addAll(equipResponseList);
        mPondMainAdapter.notifyDataSetChanged();
        // 刷新成功
        rvPond.refreshComplete();
    }

    @Override
    public void getCellEquipStatusFailure(String error) {
        UIUtils.showToast(error);
        rvPond.refreshComplete();
    }

    @Override
    public void ctrlEquipSuccess(CtrlResponse ctrlResponse) {
         if (ctrlResponse != null){
             if (ctrlResponse.isIsctlsuss()){
                 isChecked = !isChecked;
                 UIUtils.showToast("控制成功");
                 mPresenter.getCellEquips(mCellId);
             }else {
                 mPresenter.getCellEquips(mCellId);
                 UIUtils.showToast("控制失败");
             }


         }else {
             UIUtils.showToast("设备异常");
         }
    }

    @Override
    public void ctrlEquipFailure(String error) {
             UIUtils.showToast(error);
    }
}
