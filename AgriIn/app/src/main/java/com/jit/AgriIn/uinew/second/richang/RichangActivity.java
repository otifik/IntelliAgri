package com.jit.AgriIn.uinew.second.richang;

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
import com.jit.AgriIn.model.bean.RichangUpdateBean;
import com.jit.AgriIn.model.response.DailyThrowResponse;
import com.jit.AgriIn.model.response.TypeThrowResponse;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.util.ui.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

public class RichangActivity extends BaseActivity<RichangView,RichangPresenter> implements RichangView{
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
    String mUserName="";

    /**
     * 用于保存当前获取页
     */
    private int mPage;

    private BaseQuickAdapter<DailyThrowResponse, BaseViewHolder> mPondMainAdapter;
    private List<DailyThrowResponse> mIncomeResponses = new ArrayList<>();
    @Override
    protected void init() {
        if (getIntent() != null){
            mUserName = getIntent().getStringExtra("username");
//            Log.e("username",mUserName);
        }
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_guding;
    }

    @Override
    protected RichangPresenter createPresenter() {
        return new RichangPresenter(this);
    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        if (mUserName == null){
            ibAddMenu.setVisibility(View.VISIBLE);
        }else {
            ibAddMenu.setVisibility(View.GONE);
        }
        tvToolbarTitle.setText("日常投放");
        setAdapter();
    }

    private void setAdapter() {
        rvPond.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mPondMainAdapter = new BaseQuickAdapter<DailyThrowResponse, BaseViewHolder>(R.layout.item_richang,mIncomeResponses
        ) {
            @Override
            protected void convert(BaseViewHolder helper, DailyThrowResponse item) {

                helper.setText(R.id.tvRichangname,item.getType());
                helper.setText(R.id.tvRichangTotal,String.valueOf(item.getCount()) + item.getUnit());
                helper.setText(R.id.tvRichangTme,item.getSysTime().substring(0,16));
                if (item.getType().equals("种苗")){
                    helper.setText(R.id.tvDes,"描述（品种）：");
                }else if (item.getType().equals("药品")){
                    helper.setText(R.id.tvDes,"描述（投入方式）：");
                }

                TextView tvName = helper.getView(R.id.tvName);
                TextView tvMingcheng = helper.getView(R.id.tvMingCheng);
                if (item.getName() != null){
                    helper.setText(R.id.tvName,item.getName());
                }else {
                    tvName.setVisibility(View.GONE);
                    tvMingcheng.setVisibility(View.GONE);
                }
                if (item.getDescription() != null){
                    helper.setText(R.id.tvDesContent,item.getDescription());
                }


                helper.setText(R.id.tvTouruPrice,String.valueOf(item.getTotal()) + "元");


            }
        };


        rvPond.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                //设置的刷新
                mPage = 1;
                if (mUserName == null){
                    mPresenter.getRicahng(mPage);
                }else {
                    mPresenter.getRicahngByUser(mPage,mUserName);
                }
            }

            @Override
            public void onLoadMore() {
                // 加载更多
                mPage++;
                if (mUserName == null){
                    mPresenter.getRicahng(mPage);
                }else {
                    mPresenter.getRicahngByUser(mPage,mUserName);
                }
            }
        });



        rvPond.setAdapter(mPondMainAdapter);
        // 设置空布局
        View emptyView = LayoutInflater.from(this).inflate(R.layout.empty_pond_main, null, false);
        TextView tvEmptyDes =  emptyView.findViewById(R.id.tvEmptyDes);
        tvEmptyDes.setText("请添加日常投入物品");
        mPondMainAdapter.setEmptyView(emptyView);

    }





    @Override
    protected void initData() {
        mPage = 1;
        if (mUserName == null){
            mPresenter.getRicahng(mPage);
        }else {
            mPresenter.getRicahngByUser(mPage,mUserName);
        }

        // 监听增加塘口信息
        mRxManager.on(AppConstant.RX_ADD_POND, (Consumer<DailyThrowResponse>) pondMainResponse -> {
            mIncomeResponses.add(pondMainResponse);
            mPondMainAdapter.notifyDataSetChanged();
        });

        mRxManager.on(AppConstant.RX_UPDATE_POND, (Consumer<RichangUpdateBean>) richangUpdateBean -> {
            mIncomeResponses.set(richangUpdateBean.getItemPosition()-1,richangUpdateBean.getDailyThrowResponse());
            mPondMainAdapter.notifyItemChanged(richangUpdateBean.getItemPosition());
        });
    }

    @Override
    protected void initListener() {
        ivToolbarNavigation.setOnClickListener(view -> onBackPressed());

        ibAddMenu.setOnClickListener(view -> jumpToActivity(RichangAddActivity.class));

        /**
         * 条目点击事件
         */
        mPondMainAdapter.setOnItemClickListener((adapter, view, position) -> jumpToPondShow(position));

        /**
         * 条目长按事件
         */
        if (mUserName == null){
            mPondMainAdapter.setOnItemLongClickListener((adapter, view, position) -> {
                mPosition = position-1;
                showChooseView(position-1);
                return false;
            });
        }

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

        vCancel.setOnClickListener(view -> dialog.dismiss());

        vDelete.setOnClickListener(view -> {
            dialog.dismiss();
            mPresenter.deleteRichang(String.valueOf(mIncomeResponses.get(itemPosition).getId()));
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
        DailyThrowResponse dailyThrowResponse = mIncomeResponses.get(itemPosition);
        Intent intent = new Intent(this, RichangUpdateActivity.class);
        intent.putExtra(AppConstant.EXTRA_ITEM_INDEX,itemPosition+1);
        intent.putExtra(AppConstant.EXTRA_IS_FROM_POND_MAIN,true);
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppConstant.BUNDLE_POND_BEAN,dailyThrowResponse);
        intent.putExtras(bundle);
        jumpToActivity(intent);
    }





    @Override
    public void deleteRichangSuccess() {
        remove(mPosition);
    }

    @Override
    public void deleteRichangFailure(String error) {
        UIUtils.showToast(error);
    }

    @Override
    public void getRichangSuccess(TypeThrowResponse typeThrowResponse) {
        if (mIsFirst){
            if (typeThrowResponse != null){
                mIncomeResponses.clear();
                mIncomeResponses.addAll(typeThrowResponse.getList());
                mPondMainAdapter.notifyDataSetChanged();
            }
            mIsFirst = false;
        }else {
            if (mPage == 1){
                if (typeThrowResponse != null){
                    mIncomeResponses.clear();
                    mIncomeResponses.addAll(typeThrowResponse.getList());
                    mPondMainAdapter.notifyDataSetChanged();
                    // 刷新成功
                    rvPond.refreshComplete();
                }

            }else {
                if (typeThrowResponse !=null && typeThrowResponse.getList() != null && typeThrowResponse.getList().size() >0){
                    mIncomeResponses.addAll(typeThrowResponse.getList());
                    mPondMainAdapter.notifyDataSetChanged();
                    rvPond.refreshComplete();
                }else {
                    rvPond.noMoreLoading();
                }
            }
        }
    }

    @Override
    public void getRichangFailure(String error) {
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
