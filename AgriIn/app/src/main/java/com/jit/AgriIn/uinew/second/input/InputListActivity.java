package com.jit.AgriIn.uinew.second.input;

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
import com.jit.AgriIn.model.bean.InputUpdateBean;
import com.jit.AgriIn.model.cache.UserCache;
import com.jit.AgriIn.model.response.InputResponse;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.util.ui.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

public class InputListActivity extends BaseActivity<InputListView,InputListPresenter> implements InputListView{

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

    private BaseQuickAdapter<InputResponse, BaseViewHolder> mAdapter;
    private List<InputResponse> mInputResponse = new ArrayList<>();

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
    protected InputListPresenter createPresenter() {
        return new InputListPresenter(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ibAddMenu.setVisibility(View.VISIBLE);
        ivToolbarNavigation.setVisibility(View.VISIBLE);
        tvToolbarTitle.setText("投入品管理");

        initAdapter();

        mPage = 1;
        mPresenter.getUserInputs(mUserName,mPage,AppConstant.NORMAL_PAGE_SIZE);
    }

    @Override
    protected void initData() {
        mRxManager.on(AppConstant.RX_ADD_INPUT, (Consumer<InputResponse>) pondMainResponse -> {
            mInputResponse.add(pondMainResponse);
            mAdapter.notifyDataSetChanged();
        });

        mRxManager.on(AppConstant.RX_UPDATE_INPUT, (Consumer<InputUpdateBean>) gudingUpdateBean -> {
            mInputResponse.set(gudingUpdateBean.getItemPosition()-1,gudingUpdateBean.getmFishPondResponse());
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
                jumpToActivity(InputAddActivity.class);
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
            mPresenter.deleteInput(mInputResponse.get(itemPosition).getId());
        });
    }

    public void jumpToPondEdit(int itemPosition){
        InputResponse inputResponse = mInputResponse.get(itemPosition);
        Intent intent = new Intent(this, InputUpdateActivity.class);
        intent.putExtra(AppConstant.EXTRA_ITEM_INDEX,itemPosition+1);
        intent.putExtra(AppConstant.EXTRA_IS_FROM_INPUT_MAIN,true);
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppConstant.BUNDLE_INPUT_BEAN,inputResponse);
        intent.putExtras(bundle);
        jumpToActivity(intent);
    }

    private void initAdapter() {
        mRecCustom.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mAdapter = new BaseQuickAdapter<InputResponse, BaseViewHolder>(R.layout.item_input_info,mInputResponse) {
            @Override
            protected void convert(BaseViewHolder helper, InputResponse item) {
                helper.setText(R.id.tvType,item.getType());
                helper.setText(R.id.tvName,item.getName());
            }
        };
        mRecCustom.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                //设置的刷新
                mPage = 1;
                mPresenter.getUserInputs(mUserName,mPage,AppConstant.NORMAL_PAGE_SIZE);
            }

            @Override
            public void onLoadMore() {
                // 加载更多
                mPage++;
                mPresenter.getUserInputs(mUserName,mPage,AppConstant.NORMAL_PAGE_SIZE);
            }
        });
        mRecCustom.setAdapter(mAdapter);

        // 设置空布局
        View emptyView = LayoutInflater.from(this).inflate(R.layout.empty_pond_main, null, false);
        TextView tvEmptyDes =  emptyView.findViewById(R.id.tvEmptyDes);
        tvEmptyDes.setText("请添加投入品");
        mAdapter.setEmptyView(emptyView);
    }

    public void remove(int position) {
        mInputResponse.remove(position);
        mAdapter.notifyItemRemoved(position+1);
        mAdapter.notifyItemRangeChanged(position+1,mInputResponse.size()-position);
    }


    @Override
    public void deleteInputSuccess() {
        remove(mPosition);
        UIUtils.showToast("删除成功");
    }

    @Override
    public void deleteInputFailure(String error) {
        UIUtils.showToast(error);
    }

    @Override
    public void getInputSuccess(List<InputResponse> inputResponseList) {
        if (mIsFirst){
            if (inputResponseList != null){
                mInputResponse.clear();
                mInputResponse.addAll(inputResponseList);
                mAdapter.notifyDataSetChanged();
            }
            mIsFirst = false;
        }else {
            if (mPage == 1){
                if (inputResponseList != null){
                    mInputResponse.clear();
                    mInputResponse.addAll(inputResponseList);
                    mAdapter.notifyDataSetChanged();
                    // 刷新成功
                    mRecCustom.refreshComplete();
                }

            }else {
                if (inputResponseList != null && inputResponseList.size() > 0){
                    mInputResponse.addAll(inputResponseList);
                    mAdapter.notifyDataSetChanged();
                    mRecCustom.refreshComplete();
                }else {
                    mRecCustom.noMoreLoading();
                }
            }
        }
    }

    @Override
    public void getInputFailure(String error) {
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