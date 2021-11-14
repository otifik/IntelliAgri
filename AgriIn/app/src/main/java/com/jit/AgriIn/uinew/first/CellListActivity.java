package com.jit.AgriIn.uinew.first;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.xrecyclerview.XRecyclerView;
import com.jit.AgriIn.R;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.model.bean.CellUpdateBean;
import com.jit.AgriIn.model.cache.UserCache;
import com.jit.AgriIn.model.response.CellResponse;
import com.jit.AgriIn.model.response.FishPondResponse;
import com.jit.AgriIn.model.response.FishPondUpdateResponse;
import com.jit.AgriIn.model.response.PageResponse;
import com.jit.AgriIn.uinew.first.data.CellAddActivity;
import com.jit.AgriIn.uinew.first.data.CellListPresenter;
import com.jit.AgriIn.uinew.first.data.CellListView;
import com.jit.AgriIn.uinew.first.data.CellUpdateActivity;
import com.jit.AgriIn.uinew.first.data.FishPondAddActivity;
import com.jit.AgriIn.uinew.first.data.ctrl.CtrlActivity;
import com.jit.AgriIn.uinew.first.data.query.RealOrHisActivity;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.util.app.SharePreferenceUtils;
import com.zxl.baselib.util.ui.UIUtils;
import com.zxl.baselib.widget.CustomDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

public class CellListActivity extends BaseActivity <CellListView, CellListPresenter> implements CellListView{

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

    private View mSenOrCtrlView;
    private CustomDialog mSenOrCtrlDialog;

    int mPosition;

    private BaseQuickAdapter<FishPondResponse, BaseViewHolder> mAdapter;
    private List<FishPondResponse> mFishPondResponse = new ArrayList<>();
    /**
     * 用于保存当前获取页
     */
    private int mPage;
    private String mUserName;
    private int mCellId;

    private boolean mIsFirst = true;


    @Override
    public void init() {
        if (getIntent() != null){
            mUserName = getIntent().getStringExtra("username");
//            Log.e("username",mUserName);
        }
    }

//    public static CellListActivity newInstance() {
//
//        Bundle args = new Bundle();
//
//        CellListActivity fragment = new CellListActivity();
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_guding;
    }

    @Override
    protected CellListPresenter createPresenter() {
        return new CellListPresenter(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ibAddMenu.setVisibility(View.VISIBLE);
        ivToolbarNavigation.setVisibility(View.GONE);
        tvToolbarTitle.setText("数据管理");

        initAdapter();

        mPage = 1;
        mPresenter.getAllUserPonds(mUserName,mPage,AppConstant.NORMAL_PAGE_SIZE);
    }



    @Override
    public void initData() {
//        mPage = 1;
//        mPresenter.getAllEquips(mTermId);

        // 监听增加生产单元信息
        mRxManager.on(AppConstant.RX_ADD_POND, (Consumer<FishPondResponse>) pondMainResponse -> {
            mFishPondResponse.add(pondMainResponse);
            mAdapter.notifyDataSetChanged();
        });

        mRxManager.on(AppConstant.RX_UPDATE_POND, (Consumer<FishPondUpdateResponse>) gudingUpdateBean -> {
            mFishPondResponse.set(gudingUpdateBean.getItemPosition()-1,gudingUpdateBean.getFishPondResponse());
            mAdapter.notifyItemChanged(gudingUpdateBean.getItemPosition());
        });
    }

    @Override
    public void initListener() {
        ibAddMenu.setOnClickListener(view -> {
            if (!UserCache
            .getUserRole().equals("ROLE_USER")){
                UIUtils.showToast("无指定权限");
            }else {
//                jumpToActivity(CellAddActivity.class);
                jumpToActivity(FishPondAddActivity.class);
            }
        });

        /**
         * 条目点击事件
         */
//        mAdapter.setOnItemClickListener((adapter, view, position) -> jumpToPondShow(position));

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mPosition = position-1;

                if (mSenOrCtrlView == null){
                    mSenOrCtrlView = View.inflate(CellListActivity.this,R.layout.dialog_senorctrl_choose,null);
                    mSenOrCtrlDialog = new CustomDialog(CellListActivity.this,mSenOrCtrlView,R.style.MyDialog);
                    mSenOrCtrlView.findViewById(R.id.tvSen).setOnClickListener(v1 -> {
                        mSenOrCtrlDialog.dismiss();
                        jumpToPondShow(mPosition);
                    });

                    mSenOrCtrlView.findViewById(R.id.tvCtrl).setOnClickListener(v12 -> {
                        mSenOrCtrlDialog.dismiss();
                        if (!UserCache
                                .getUserRole().equals("ROLE_USER")){
                            UIUtils.showToast("无指定权限");
                        }else {
                            jumpToCtrlShow(mPosition);
                        }
                    });
                }
                mSenOrCtrlDialog.show();


            }
        });


        /**
         * 条目长按事件
         */
        mAdapter.setOnItemLongClickListener((adapter, view, position) -> {
            mPosition = position-1;
            if (!UserCache
                    .getUserRole().equals("ROLE_USER")){
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
            mPresenter.deleteFishpond(mFishPondResponse.get(itemPosition).getId());
        });
    }

    public void jumpToPondEdit(int itemPosition){
        FishPondResponse fishPondResponse = mFishPondResponse.get(itemPosition);
        Intent intent = new Intent(this, CellUpdateActivity.class);
        intent.putExtra(AppConstant.EXTRA_ITEM_INDEX,itemPosition+1);
        intent.putExtra(AppConstant.EXTRA_IS_FROM_POND_MAIN,true);
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppConstant.BUNDLE_POND_BEAN,fishPondResponse);
        intent.putExtras(bundle);
        jumpToActivity(intent);
    }

    private void initAdapter() {
        mRecCustom.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mAdapter = new BaseQuickAdapter<FishPondResponse, BaseViewHolder>(R.layout.item_pond_info,mFishPondResponse) {
            @Override
            protected void convert(BaseViewHolder helper, FishPondResponse item) {
                helper.setText(R.id.pond_name,item.getName());
                helper.setText(R.id.product,item.getProduct());
            }
        };

        mRecCustom.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                //设置的刷新
                mPage = 1;
                mPresenter.getAllUserPonds(mUserName,mPage,AppConstant.NORMAL_PAGE_SIZE);
            }

            @Override
            public void onLoadMore() {
//                // 加载更多
                mPage++;
                mPresenter.getAllUserPonds(mUserName,mPage,AppConstant.NORMAL_PAGE_SIZE);
            }
        });
        mRecCustom.setAdapter(mAdapter);

        // 设置空布局
        View emptyView = LayoutInflater.from(this).inflate(R.layout.empty_pond_main, null, false);
        TextView tvEmptyDes =  emptyView.findViewById(R.id.tvEmptyDes);
        tvEmptyDes.setText("请添加生产单元");
        mAdapter.setEmptyView(emptyView);
    }

    private void jumpToCtrlShow(int itemPosition){
        Intent intent = new Intent(this, CtrlActivity.class);
        intent.putExtra("cellId",mFishPondResponse.get(itemPosition).getId());
        Log.e("生产单元id",String.valueOf(mFishPondResponse.get(itemPosition).getId()));
        jumpToActivity(intent);
    }

    private void jumpToPondShow(int itemPosition){
        int cellID = mFishPondResponse.get(itemPosition).getId();
        SharePreferenceUtils.getInstance(this).putInt("cellId",cellID);
        jumpToActivity(RealOrHisActivity.class);
    }

    public void remove(int position) {

        mFishPondResponse.remove(position);
        mAdapter.notifyItemRemoved(position+1);
        mAdapter.notifyItemRangeChanged(position+1,mFishPondResponse.size()-position);
    }

    @Override
    public void deleteFishPondSuccess() {
        remove(mPosition);
    }

    @Override
    public void deleteFishPondFailure(String error) {
        UIUtils.showToast(error);
    }

    @Override
    public void getFishPondSuccess(List<FishPondResponse> fishPondResponseList) {
        if (mIsFirst){
            if (fishPondResponseList != null){
                mFishPondResponse.clear();
                mFishPondResponse.addAll(fishPondResponseList);
                mAdapter.notifyDataSetChanged();
            }
            mIsFirst = false;
        }else {
            if (mPage == 1){
                if (fishPondResponseList != null){
                    mFishPondResponse.clear();
                    mFishPondResponse.addAll(fishPondResponseList);
                    mAdapter.notifyDataSetChanged();
                    // 刷新成功
                    mRecCustom.refreshComplete();
                }

            }else {
                if (fishPondResponseList !=null && fishPondResponseList != null && fishPondResponseList.size() >0){
                    mFishPondResponse.addAll(fishPondResponseList);
                    mAdapter.notifyDataSetChanged();
                    mRecCustom.refreshComplete();
                }else {
                    mRecCustom.noMoreLoading();
                }
            }
        }
    }

    @Override
    public void getFishPondFailure(String error) {
        UIUtils.showToast(error);
        mRecCustom.refreshComplete();
        if (mIsFirst){
            mIsFirst = false;
        }

        if (mPage > 1) {
            mPage--;
        }
    }
}
