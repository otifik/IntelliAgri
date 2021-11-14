package com.jit.AgriIn.uinew.second.feeding;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
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
import com.jit.AgriIn.model.cache.UserCache;
import com.jit.AgriIn.model.response.FeedingFoodResponse;
import com.jit.AgriIn.model.response.FeedingFoodUpdateResponse;
import com.jit.AgriIn.model.response.FishPondResponse;
import com.jit.AgriIn.model.response.FishPondUpdateResponse;
import com.jit.AgriIn.uinew.first.data.CellUpdateActivity;
import com.jit.AgriIn.uinew.first.data.FishPondAddActivity;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BaseView;
import com.zxl.baselib.util.ui.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

public class FeedingFoodListActivity extends BaseActivity<FeedingFoodListView,FeedingFoodListPresenter> implements FeedingFoodListView {

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

    private BaseQuickAdapter<FeedingFoodResponse, BaseViewHolder> mAdapter;
    private List<FeedingFoodResponse> mFeedingFoodResponse = new ArrayList<>();

    private int mPage;
    private String mUserName;
    private int mFeedingFoodId;

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
    protected FeedingFoodListPresenter createPresenter() {
        return new FeedingFoodListPresenter(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ibAddMenu.setVisibility(View.VISIBLE);
        ivToolbarNavigation.setVisibility(View.VISIBLE);
        tvToolbarTitle.setText("投喂饲料记录");

        initAdapter();

        mPage = 1;
        mPresenter.getUserFeedingFood(mUserName,mPage,AppConstant.NORMAL_PAGE_SIZE);
    }

    @Override
    protected void initData() {
        mRxManager.on(AppConstant.RX_ADD_FEEDING_FOOD, (Consumer<FeedingFoodResponse>) pondMainResponse -> {
            mFeedingFoodResponse.add(pondMainResponse);
            mAdapter.notifyDataSetChanged();
        });

        mRxManager.on(AppConstant.RX_UPDATE_FEEDING_FOOD, (Consumer<FeedingFoodUpdateResponse>) gudingUpdateBean -> {
            mFeedingFoodResponse.set(gudingUpdateBean.getItemPosition()-1,gudingUpdateBean.getFeedingFoodResponse());
            mAdapter.notifyItemChanged(gudingUpdateBean.getItemPosition());
        });
    }

    @Override
    protected void initListener() {
        ivToolbarNavigation.setOnClickListener(v -> onBackPressed());

        ibAddMenu.setOnClickListener(view -> {
//            jumpToActivity(CellAddActivity.class);
//            jumpToActivity(InputAddActivity.class);
            jumpToActivity(FeedingFoodActivity.class);
        });

        mAdapter.setOnItemLongClickListener((adapter, view, position) -> {
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
            int a = mFeedingFoodResponse.get(itemPosition).getId();
            Log.e("test",Integer.toString(a));
            mPresenter.deleteFeedingFood(mFeedingFoodResponse.get(itemPosition).getId());
        });
    }

    public void jumpToPondEdit(int itemPosition){
        FeedingFoodResponse feedingFoodResponse = mFeedingFoodResponse.get(itemPosition);
        Intent intent = new Intent(this, FeedingFoodUpdateActivity.class);
        intent.putExtra(AppConstant.EXTRA_ITEM_INDEX,itemPosition+1);
        intent.putExtra(AppConstant.EXTRA_IS_FROM_FEEDING_FOOD_MAIN,true);
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppConstant.BUNDLE_FEEDING_FOOD_BEAN,feedingFoodResponse);
        intent.putExtras(bundle);
        jumpToActivity(intent);
    }

    private void initAdapter() {
        mRecCustom.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mAdapter = new BaseQuickAdapter<FeedingFoodResponse, BaseViewHolder>(R.layout.item_feeding_food,mFeedingFoodResponse) {
            @Override
            protected void convert(BaseViewHolder helper, FeedingFoodResponse item) {
                helper.setText(R.id.pond,item.getPond());
                helper.setText(R.id.name,item.getName());
                helper.setText(R.id.date,item.getTime());
                helper.setText(R.id.amount,String.valueOf(item.getAmount()));
                helper.setText(R.id.unit,item.getUnit());
                helper.setText(R.id.remarks,item.getRemarks());
            }
        };

        mRecCustom.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                //设置的刷新
                mPage = 1;
                mPresenter.getUserFeedingFood(mUserName,mPage,AppConstant.NORMAL_PAGE_SIZE);
            }

            @Override
            public void onLoadMore() {
                // 加载更多
                mPage++;
                mPresenter.getUserFeedingFood(mUserName,mPage,AppConstant.NORMAL_PAGE_SIZE);
            }
        });
        mRecCustom.setAdapter(mAdapter);

        // 设置空布局
        View emptyView = LayoutInflater.from(this).inflate(R.layout.empty_pond_main, null, false);
        TextView tvEmptyDes =  emptyView.findViewById(R.id.tvEmptyDes);
        tvEmptyDes.setText("请添加饲料投入");
        mAdapter.setEmptyView(emptyView);
    }

    public void remove(int position) {
        mFeedingFoodResponse.remove(position);
        mAdapter.notifyItemRemoved(position+1);
        mAdapter.notifyItemRangeChanged(position+1,mFeedingFoodResponse.size()-position);
    }


    @Override
    public void deleteFeedingFoodSuccess() {
        remove(mPosition);
        UIUtils.showToast("删除成功");
    }

    @Override
    public void deleteFeedingFoodFailure(String error) {
        UIUtils.showToast(error);
    }

    @Override
    public void getFeedingFoodSuccess(List<FeedingFoodResponse> feedingFoodResponseList) {
        if (mIsFirst){
            if (feedingFoodResponseList != null){
                mFeedingFoodResponse.clear();
                mFeedingFoodResponse.addAll(feedingFoodResponseList);
                mAdapter.notifyDataSetChanged();
            }
            mIsFirst = false;
        }else {
            if (mPage == 1){
                if (feedingFoodResponseList != null){
                    mFeedingFoodResponse.clear();
                    mFeedingFoodResponse.addAll(feedingFoodResponseList);
                    mAdapter.notifyDataSetChanged();
                    // 刷新成功
                    mRecCustom.refreshComplete();
                }

            }else {
                if (feedingFoodResponseList != null && feedingFoodResponseList.size() > 0){
                    mFeedingFoodResponse.addAll(feedingFoodResponseList);
                    mAdapter.notifyDataSetChanged();
                    mRecCustom.refreshComplete();
                }else {
                    mRecCustom.noMoreLoading();
                }
            }
        }
    }

    @Override
    public void getFeedingFoodFailure(String error) {
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