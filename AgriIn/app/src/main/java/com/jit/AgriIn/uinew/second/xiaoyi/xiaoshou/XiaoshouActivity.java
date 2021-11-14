package com.jit.AgriIn.uinew.second.xiaoyi.xiaoshou;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.xrecyclerview.XRecyclerView;
import com.jit.AgriIn.R;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.model.bean.GudingUpdateBean;
import com.jit.AgriIn.model.response.IncomeResponse;
import com.jit.AgriIn.model.response.TypeIncomeResponse;
import com.jit.AgriIn.uinew.second.guding.GudingPresenter;
import com.jit.AgriIn.uinew.second.guding.GudingView;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.util.ui.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

public class XiaoshouActivity extends BaseActivity<GudingView,GudingPresenter> implements GudingView{
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
    private boolean mIsFirst = true;

    /**
     * 用于保存当前获取页
     */
    private int mPage;

    private BaseQuickAdapter<IncomeResponse, BaseViewHolder> mPondMainAdapter;
    private List<IncomeResponse> mIncomeResponses = new ArrayList<>();
    @Override
    protected void init() {

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_guding;
    }

    @Override
    protected GudingPresenter createPresenter() {
        return new GudingPresenter(this);
    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        ibAddMenu.setVisibility(View.VISIBLE);
        tvToolbarTitle.setText("销售记账");
        setAdapter();
    }

    private void setAdapter() {
        rvPond.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mPondMainAdapter = new BaseQuickAdapter<IncomeResponse, BaseViewHolder>(R.layout.item_guding,mIncomeResponses
        ) {
            @Override
            protected void convert(BaseViewHolder helper, IncomeResponse item) {
                helper.setText(R.id.tvGoumai,"销售金额");
                helper.setText(R.id.tvGudingname,item.getName());
                helper.setText(R.id.tvGudingPrice,item.getTotal() + "元");
                helper.setText(R.id.tvGudingTme,item.getSysTime().substring(0,16));
                if (item.getRemark() != null){
                    LinearLayout llRemark = helper.getView(R.id.llRemark);
                    llRemark.setVisibility(View.VISIBLE);
                    helper.setText(R.id.tvRemark,item.getRemark());
                }
            }
        };


        rvPond.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                //设置的刷新
                mPage = 1;
                mPresenter.getIncome(2,mPage);
            }

            @Override
            public void onLoadMore() {
                // 加载更多
                mPage++;
                mPresenter.getIncome(2,mPage);
            }
        });



        rvPond.setAdapter(mPondMainAdapter);
        // 设置空布局
        View emptyView = LayoutInflater.from(this).inflate(R.layout.empty_pond_main, null, false);
        TextView tvEmptyDes =  emptyView.findViewById(R.id.tvEmptyDes);
        tvEmptyDes.setText("请添加销售金额");
        mPondMainAdapter.setEmptyView(emptyView);

    }





    @Override
    protected void initData() {
        mPage = 1;
        mPresenter.getIncome(2,mPage);

        // 监听增加塘口信息
        mRxManager.on(AppConstant.RX_ADD_POND, (Consumer<IncomeResponse>) pondMainResponse -> {
            mIncomeResponses.add(pondMainResponse);
            mPondMainAdapter.notifyDataSetChanged();
        });

        mRxManager.on(AppConstant.RX_UPDATE_POND, (Consumer<GudingUpdateBean>) gudingUpdateBean -> {
            mIncomeResponses.set(gudingUpdateBean.getItemPosition()-1,gudingUpdateBean.getIncomeResponse());
            mPondMainAdapter.notifyItemChanged(gudingUpdateBean.getItemPosition());
        });
    }

    @Override
    protected void initListener() {
        ivToolbarNavigation.setOnClickListener(view -> onBackPressed());

        ibAddMenu.setOnClickListener(view -> jumpToActivity(XiaoshouAddActivity.class));

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
        vEdit.setOnClickListener(view -> {
            dialog.dismiss();
            jumpToPondEdit(itemPosition);
        });

        vCancel.setOnClickListener(view -> dialog.dismiss());

        vDelete.setOnClickListener(view -> {
            dialog.dismiss();
            mPresenter.deleteIncome(String.valueOf(mIncomeResponses.get(itemPosition).getId()));
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
        IncomeResponse pondMainResponse = mIncomeResponses.get(itemPosition);
        Intent intent = new Intent(this, XiaoshouUpdateActivity.class);
        intent.putExtra(AppConstant.EXTRA_ITEM_INDEX,itemPosition+1);
        intent.putExtra(AppConstant.EXTRA_IS_FROM_POND_MAIN,true);
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppConstant.BUNDLE_POND_BEAN,pondMainResponse);
        intent.putExtras(bundle);
        jumpToActivity(intent);
    }

    @Override
    public void addIncomeSuccess() {

    }

    @Override
    public void addIncomeFailure(String error) {

    }

    @Override
    public void updateIncomeSuccess() {

    }

    @Override
    public void updateIncomeFailure(String error) {

    }

    @Override
    public void deleteIncomeSuccess() {
//        if (mIncomeResponses.size() == mPosition + 1){
//            mIncomeResponses.remove(mPosition+1);
//            mPondMainAdapter.notifyDataSetChanged();
//        }else {
//            mIncomeResponses.remove(mPosition+1);
//            mPondMainAdapter.notifyItemRemoved(mPosition+1);
//        }
        remove(mPosition);
    }

    @Override
    public void deleteIncomeFailure(String error) {
        UIUtils.showToast(error);
    }

    @Override
    public void getIncomeSuccess(TypeIncomeResponse typeIncomeResponse) {
//        mIncomeResponses.clear();
//        mIncomeResponses.addAll(typeIncomeResponse.getList());
//        mPondMainAdapter.notifyDataSetChanged();
//        rvPond.refreshComplete();

        if (mIsFirst){
            if (typeIncomeResponse != null){
                mIncomeResponses.clear();
                mIncomeResponses.addAll(typeIncomeResponse.getList());
                mPondMainAdapter.notifyDataSetChanged();
            }
            mIsFirst = false;
        }else {
            if (mPage == 1){
                if (typeIncomeResponse != null){
                    mIncomeResponses.clear();
                    mIncomeResponses.addAll(typeIncomeResponse.getList());
                    mPondMainAdapter.notifyDataSetChanged();
                    // 刷新成功
                    rvPond.refreshComplete();
                }

            }else {
                if (typeIncomeResponse !=null && typeIncomeResponse.getList() != null && typeIncomeResponse.getList().size() >0){
                    mIncomeResponses.addAll(typeIncomeResponse.getList());
                    mPondMainAdapter.notifyDataSetChanged();
                    rvPond.refreshComplete();
                }
                else {
                    rvPond.noMoreLoading();
                }


            }
        }
    }

    @Override
    public void getIncomeFailure(String error) {
        UIUtils.showToast(error);
        rvPond.refreshComplete();
        if (mIsFirst){
            mIsFirst = false;
        }

        if (mPage > 1) {
            mPage--;
        }
    }


    public void remove(int position) {
        mIncomeResponses.remove(position);
        mPondMainAdapter.notifyItemRemoved(position+1);
        mPondMainAdapter.notifyItemRangeChanged(position+1,mIncomeResponses.size()-position);
    }
}
