package com.jit.AgriIn.uinew.second.fishInput;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
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
import com.jit.AgriIn.model.bean.CellUpdateBean;
import com.jit.AgriIn.model.bean.FishInputUpdateBean;
import com.jit.AgriIn.model.cache.UserCache;
import com.jit.AgriIn.model.response.CellResponse;
import com.jit.AgriIn.model.response.FishInputResponse;
import com.jit.AgriIn.model.response.FishPondResponse;
import com.jit.AgriIn.model.response.InputResponse;
import com.jit.AgriIn.uinew.first.data.CellUpdateActivity;
import com.jit.AgriIn.uinew.first.data.FishPondAddActivity;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.util.ui.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

public class FishInputListActivity extends BaseActivity<FishInputListView,FishInputListPresenter> implements FishInputListView {

    @BindView(R.id.ivToolbarNavigation)
    ImageView ivToolbarNavigation;
    @BindView(R.id.vToolbarDivision)
    View vToolbarDivision;
    @BindView(R.id.ibAddMenu)
    ImageButton ibAddMenu;
    @BindView(R.id.tvToolbarTitle)
    TextView tvToolbarTitle;
    @BindView(R.id.rvPond)
    XRecyclerView mRecCustom;

    int mPosition;

    private BaseQuickAdapter<FishInputResponse, BaseViewHolder> mAdapter;
    private List<FishInputResponse> mFishInputResponse = new ArrayList<>();

    private int mPage;
    private String mUserName;

    private boolean mIsFirst = true;

    @Override
    protected void init() {
        mUserName = UserCache.getUserUsername();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_guding;
    }

    @Override
    protected FishInputListPresenter createPresenter() {
        return new FishInputListPresenter(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ibAddMenu.setVisibility(View.VISIBLE);
        ivToolbarNavigation.setVisibility(View.VISIBLE);
        tvToolbarTitle.setText("鱼苗投入管理");

        initAdapter();

        mPage = 1;
        mPresenter.getUserFishInput(mUserName,mPage,AppConstant.NORMAL_PAGE_SIZE);
    }

    @Override
    protected void initData() {
        mRxManager.on(AppConstant.RX_ADD_FISH_INPUT, (Consumer<FishInputResponse>) fishInputResponse -> {
            mFishInputResponse.add(fishInputResponse);
            mAdapter.notifyDataSetChanged();
        });

        mRxManager.on(AppConstant.RX_UPDATE_FISH_INPUT, (Consumer<FishInputUpdateBean>) gudingUpdateBean -> {
            mFishInputResponse.set(gudingUpdateBean.getItemPosition()-1,gudingUpdateBean.getFishInputResponse());
            mAdapter.notifyItemChanged(gudingUpdateBean.getItemPosition());
        });
    }

    @Override
    protected void initListener() {

        ivToolbarNavigation.setOnClickListener(v -> onBackPressed());

        ibAddMenu.setOnClickListener(view -> {
            jumpToActivity(FishInputAddActivity.class);
        });

        mAdapter.setOnItemLongClickListener((adapter, view, position) -> {
            mPosition = position-1;
            showChooseView(position-1);
            return false;
        });

        mAdapter.setOnItemLongClickListener((adapter, view, position) -> {
            mPosition = position-1;
            if (!UserCache.getUserRole().equals("ROLE_USER")){
                UIUtils.showToast("无指定权限");
            }else {
                showChooseView(position-1);
            }
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
            mPresenter.deleteFishInput(mFishInputResponse.get(itemPosition).getId());
        });
    }

    public void jumpToPondEdit(int itemPosition){
        FishInputResponse fishInputResponse = mFishInputResponse.get(itemPosition);
        Intent intent = new Intent(this, FishInputUpdateActivity.class);
        intent.putExtra(AppConstant.EXTRA_ITEM_INDEX,itemPosition+1);
        intent.putExtra(AppConstant.EXTRA_IS_FROM_FISH_INPUT_MAIN,true);
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppConstant.BUNDLE_FISH_INPUT_BEAN,fishInputResponse);
        intent.putExtras(bundle);
        jumpToActivity(intent);
    }

    private void initAdapter() {
        mRecCustom.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mAdapter = new BaseQuickAdapter<FishInputResponse, BaseViewHolder>(R.layout.item_fish_input_info,mFishInputResponse) {
            @Override
            protected void convert(BaseViewHolder helper, FishInputResponse item) {
                String temp = String.valueOf(item.getAmount())+item.getUnit();
                helper.setText(R.id.tvType,item.getType());
                helper.setText(R.id.tvAmount,temp);
                helper.setText(R.id.tvTime,item.getDate());
            }
        };

        mRecCustom.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                //设置的刷新
                mPage = 1;
                mPresenter.getUserFishInput(mUserName,mPage,AppConstant.NORMAL_PAGE_SIZE);
            }

            @Override
            public void onLoadMore() {
                // 加载更多
                mPage++;
                mPresenter.getUserFishInput(mUserName,mPage,AppConstant.NORMAL_PAGE_SIZE);
            }
        });
        mRecCustom.setAdapter(mAdapter);

        // 设置空布局
        View emptyView = LayoutInflater.from(this).inflate(R.layout.empty_pond_main, null, false);
        TextView tvEmptyDes =  emptyView.findViewById(R.id.tvEmptyDes);
        tvEmptyDes.setText("请添加鱼苗投入");
        mAdapter.setEmptyView(emptyView);
    }


    @Override
    public void getFishInputSuccess(List<FishInputResponse> fishInputResponseList) {
        if (mIsFirst){
            if (fishInputResponseList != null){
                mFishInputResponse.clear();
                mFishInputResponse.addAll(fishInputResponseList);
                mAdapter.notifyDataSetChanged();
            }
            mIsFirst = false;
        }else {
            if (mPage == 1){
                if (fishInputResponseList != null){
                    mFishInputResponse.clear();
                    mFishInputResponse.addAll(fishInputResponseList);
                    mAdapter.notifyDataSetChanged();
                    // 刷新成功
                    mRecCustom.refreshComplete();
                }

            }else {
                if (fishInputResponseList != null && fishInputResponseList.size() > 0){
                    mFishInputResponse.addAll(fishInputResponseList);
                    mAdapter.notifyDataSetChanged();
                    mRecCustom.refreshComplete();
                }else {
                    mRecCustom.noMoreLoading();
                }
            }
        }
    }

    public void remove(int position) {
        mFishInputResponse.remove(position);
        mAdapter.notifyItemRemoved(position+1);
        mAdapter.notifyItemRangeChanged(position+1,mFishInputResponse.size()-position);
        UIUtils.showToast("删除成功");
    }

    @Override
    public void getFishInputFailure(String error) {
        UIUtils.showToast(error);
    }

    @Override
    public void deleteFishInputSuccess() {
        remove(mPosition);
    }

    @Override
    public void deleteFishInputFailure(String error) {
        UIUtils.showToast(error);
    }

    @Override
    public void getFishPondSuccess(List<FishPondResponse> fishPondResponseList) {

    }

    @Override
    public void getFishPondFailure(String error) {

    }
}