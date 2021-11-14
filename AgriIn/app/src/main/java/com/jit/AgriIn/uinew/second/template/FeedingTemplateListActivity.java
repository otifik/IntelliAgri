package com.jit.AgriIn.uinew.second.template;

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
import com.jit.AgriIn.model.bean.FeedingTemplateUpdateBean;
import com.jit.AgriIn.model.bean.ReagentInputUpdateBean;
import com.jit.AgriIn.model.cache.UserCache;
import com.jit.AgriIn.model.response.FeedingFoodResponse;
import com.jit.AgriIn.model.response.FeedingTemplateResponse;
import com.jit.AgriIn.model.response.ReagentInputResponse;
import com.jit.AgriIn.uinew.second.feeding.FeedingFoodListView;
import com.jit.AgriIn.uinew.second.input.InputAddActivity;
import com.jit.AgriIn.uinew.second.reagent.ReagentInputUpdateActivity;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.util.ui.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

public class FeedingTemplateListActivity extends BaseActivity<FeedingTemplateListView,FeedingTemplateListPresenter> implements FeedingTemplateListView {

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

    private BaseQuickAdapter<FeedingTemplateResponse, BaseViewHolder> mAdapter;
    private List<FeedingTemplateResponse> mFeedingTemplateResponse = new ArrayList<>();

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
    protected FeedingTemplateListPresenter createPresenter() {
        return new FeedingTemplateListPresenter(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ibAddMenu.setVisibility(View.VISIBLE);
        ivToolbarNavigation.setVisibility(View.VISIBLE);
        tvToolbarTitle.setText("投喂模板管理");

        initAdapter();

        mPage = 1;
        mPresenter.getUserFeedingTemplate(mUserName,mPage,AppConstant.NORMAL_PAGE_SIZE);
    }

    @Override
    protected void initData() {
        mRxManager.on(AppConstant.RX_ADD_FEEDING_TEMPLATE, (Consumer<FeedingTemplateResponse>) pondMainResponse -> {
            mFeedingTemplateResponse.add(pondMainResponse);
            mAdapter.notifyDataSetChanged();
        });

        mRxManager.on(AppConstant.RX_UPDATE_FEEDING_TEMPLATE, (Consumer<FeedingTemplateUpdateBean>) gudingUpdateBean -> {
            mFeedingTemplateResponse.set(gudingUpdateBean.getItemPosition()-1,gudingUpdateBean.getFeedingTemplateResponse());
            mAdapter.notifyItemChanged(gudingUpdateBean.getItemPosition());
        });
    }

    @Override
    protected void initListener() {
        ivToolbarNavigation.setOnClickListener(v -> onBackPressed());

        ibAddMenu.setOnClickListener(view -> {
            if (!UserCache.getUserRole().equals("ROLE_USER")){
                UIUtils.showToast("无指定权限");
            }else {
                jumpToActivity(FeedingTemplateAddActivity.class);
            }
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
            mPresenter.deleteFeedingTemplate(mFeedingTemplateResponse.get(itemPosition).getId());
        });
    }

    public void jumpToPondEdit(int itemPosition){
        FeedingTemplateResponse feedingTemplateResponse = mFeedingTemplateResponse.get(itemPosition);
        // reagentInputUpdate
        Intent intent = new Intent(this, FeedingTemplateUpdateActivity.class);
        intent.putExtra(AppConstant.EXTRA_ITEM_INDEX,itemPosition+1);
        intent.putExtra(AppConstant.EXTRA_IS_FROM_FEEDING_TEMPLATE_MAIN,true);
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppConstant.BUNDLE_FEEDING_TEMPLATE_BEAN,feedingTemplateResponse);
        intent.putExtras(bundle);
        jumpToActivity(intent);
    }

    private void initAdapter() {
        mRecCustom.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mAdapter = new BaseQuickAdapter<FeedingTemplateResponse, BaseViewHolder>(R.layout.item_feeding_template,mFeedingTemplateResponse) {
            @Override
            protected void convert(BaseViewHolder helper, FeedingTemplateResponse item) {
                helper.setText(R.id.templateName,item.getName());
                helper.setText(R.id.time,item.getTime());
            }
        };
        mRecCustom.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                //设置的刷新
                mPage = 1;
                mPresenter.getUserFeedingTemplate(mUserName,mPage,AppConstant.NORMAL_PAGE_SIZE);
            }

            @Override
            public void onLoadMore() {
                // 加载更多
                mPage++;
                mPresenter.getUserFeedingTemplate(mUserName,mPage,AppConstant.NORMAL_PAGE_SIZE);
            }
        });
        mRecCustom.setAdapter(mAdapter);

        // 设置空布局
        View emptyView = LayoutInflater.from(this).inflate(R.layout.empty_pond_main, null, false);
        TextView tvEmptyDes =  emptyView.findViewById(R.id.tvEmptyDes);
        tvEmptyDes.setText("请添加投喂模板");
        mAdapter.setEmptyView(emptyView);
    }

    public void remove(int position) {
        mFeedingTemplateResponse.remove(position);
        mAdapter.notifyItemRemoved(position+1);
        mAdapter.notifyItemRangeChanged(position+1,mFeedingTemplateResponse.size()-position);
    }

    @Override
    public void deleteFeedingTemplateSuccess() {
        remove(mPosition);
        UIUtils.showToast("删除成功");
    }

    @Override
    public void deleteFeedingTemplateFailure(String error) {
        UIUtils.showToast(error);
    }

    @Override
    public void getFeedingTemplateSuccess(List<FeedingTemplateResponse> feedingTemplateResponsesList) {
        if (mIsFirst){
            if (feedingTemplateResponsesList != null){
                mFeedingTemplateResponse.clear();
                mFeedingTemplateResponse.addAll(feedingTemplateResponsesList);
                mAdapter.notifyDataSetChanged();
            }
            mIsFirst = false;
        }else {
            if (mPage == 1){
                if (feedingTemplateResponsesList != null){
                    mFeedingTemplateResponse.clear();
                    mFeedingTemplateResponse.addAll(feedingTemplateResponsesList);
                    mAdapter.notifyDataSetChanged();
                    // 刷新成功
                    mRecCustom.refreshComplete();
                }

            }else {
                if (feedingTemplateResponsesList != null && feedingTemplateResponsesList.size() > 0){
                    mFeedingTemplateResponse.addAll(feedingTemplateResponsesList);
                    mAdapter.notifyDataSetChanged();
                    mRecCustom.refreshComplete();
                }else {
                    mRecCustom.noMoreLoading();
                }
            }
        }
    }

    @Override
    public void getFeedingTemplateFailure(String error) {
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