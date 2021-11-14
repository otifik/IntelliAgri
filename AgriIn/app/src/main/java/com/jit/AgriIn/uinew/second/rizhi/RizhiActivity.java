package com.jit.AgriIn.uinew.second.rizhi;

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

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.xrecyclerview.XRecyclerView;
import com.jit.AgriIn.R;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.model.bean.RizhiUpdateBean;
import com.jit.AgriIn.model.response.RizhiResponse;
import com.jit.AgriIn.model.response.TypeRizhiResponse;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.util.ui.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

public class RizhiActivity extends BaseActivity<RizhiView,RizhiPresenter> implements RizhiView{
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

    private BaseQuickAdapter<RizhiResponse, BaseViewHolder> mPondMainAdapter;
    private List<RizhiResponse> mRizhiResponse = new ArrayList<>();
    @Override
    protected void init() {

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_guding;
    }

    @Override
    protected RizhiPresenter createPresenter() {
        return new RizhiPresenter(this);
    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        ibAddMenu.setVisibility(View.VISIBLE);
        tvToolbarTitle.setText("巡视检查");
        setAdapter();
    }

    private void setAdapter() {
        rvPond.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mPondMainAdapter = new BaseQuickAdapter<RizhiResponse, BaseViewHolder>(R.layout.item_rizhi,mRizhiResponse
        ) {
            @Override
            protected void convert(BaseViewHolder helper, RizhiResponse item) {

                helper.setText(R.id.tvRizhiname,item.getName());
                helper.setText(R.id.tvRizhiContent,item.getContent());
                helper.setText(R.id.tvRizhiTime,item.getSysTime().substring(0,16));

            }
        };


        rvPond.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                //设置的刷新
                mPage = 1;
                mPresenter.getRizhi(mPage);
            }

            @Override
            public void onLoadMore() {
                // 加载更多
                mPage++;
                mPresenter.getRizhi(mPage);
            }
        });



        rvPond.setAdapter(mPondMainAdapter);
        // 设置空布局
        View emptyView = LayoutInflater.from(this).inflate(R.layout.empty_pond_main, null, false);
        TextView tvEmptyDes =  emptyView.findViewById(R.id.tvEmptyDes);
        tvEmptyDes.setText("请添加巡视日志");
        mPondMainAdapter.setEmptyView(emptyView);

    }





    @Override
    protected void initData() {
        mPage = 1;
        mPresenter.getRizhi(mPage);

        // 监听增加塘口信息
        mRxManager.on(AppConstant.RX_ADD_POND, (Consumer<RizhiResponse>) pondMainResponse -> {
            mRizhiResponse.add(pondMainResponse);
            mPondMainAdapter.notifyDataSetChanged();
        });

        mRxManager.on(AppConstant.RX_UPDATE_POND, (Consumer<RizhiUpdateBean>) gudingUpdateBean -> {
            mRizhiResponse.set(gudingUpdateBean.getItemPosition()-1,gudingUpdateBean.getRizhiResponse());
            mPondMainAdapter.notifyItemChanged(gudingUpdateBean.getItemPosition());
        });
    }

    @Override
    protected void initListener() {
        ivToolbarNavigation.setOnClickListener(view -> onBackPressed());

        ibAddMenu.setOnClickListener(view -> jumpToActivity(RizhiAddActivity.class));

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
            mPresenter.deleteRizhi(String.valueOf(mRizhiResponse.get(itemPosition).getId()));
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
        RizhiResponse pondMainResponse = mRizhiResponse.get(itemPosition);
        Intent intent = new Intent(this, RizhiUpdateActivity.class);
        intent.putExtra(AppConstant.EXTRA_ITEM_INDEX,itemPosition+1);
        intent.putExtra(AppConstant.EXTRA_IS_FROM_POND_MAIN,true);
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppConstant.BUNDLE_POND_BEAN,pondMainResponse);
        intent.putExtras(bundle);
        jumpToActivity(intent);
    }






    @Override
    public void deleteRizhiSuccess() {
        remove(mPosition);
    }

    @Override
    public void deleteRizhiFailure(String error) {
        UIUtils.showToast(error);
    }

    @Override
    public void getRizhiSuccess(TypeRizhiResponse typeRizhiResponse) {

        Log.e("rizhi","获取日志成功");

        if (mIsFirst){
            if (typeRizhiResponse != null){
                mRizhiResponse.clear();
                mRizhiResponse.addAll(typeRizhiResponse.getList());
                mPondMainAdapter.notifyDataSetChanged();
            }
            mIsFirst = false;
        }else {
            if (mPage == 1){
                if (typeRizhiResponse != null){
                    mRizhiResponse.clear();
                    mRizhiResponse.addAll(typeRizhiResponse.getList());
                    mPondMainAdapter.notifyDataSetChanged();
                    // 刷新成功
                    rvPond.refreshComplete();
                }

            }else {
                if (typeRizhiResponse !=null && typeRizhiResponse.getList() != null && typeRizhiResponse.getList().size() >0){
                    mRizhiResponse.addAll(typeRizhiResponse.getList());
                    mPondMainAdapter.notifyDataSetChanged();
                    rvPond.refreshComplete();
                }else {
                    rvPond.noMoreLoading();
                }
            }
        }
    }

    @Override
    public void getRizhiFailure(String error) {
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
        mRizhiResponse.remove(position);
        mPondMainAdapter.notifyItemRemoved(position+1);
        mPondMainAdapter.notifyItemRangeChanged(position+1,mRizhiResponse.size()-position);
    }
}
