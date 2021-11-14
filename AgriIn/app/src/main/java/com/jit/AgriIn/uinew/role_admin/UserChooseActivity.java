package com.jit.AgriIn.uinew.role_admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.xrecyclerview.XRecyclerView;
import com.jit.AgriIn.R;
import com.jit.AgriIn.model.response.PageResponse;
import com.jit.AgriIn.model.response.RoleUserInfo;
import com.jit.AgriIn.ui.presenter.expert_baike.ECustomerFgPresenter;
import com.jit.AgriIn.ui.view.expert_baike.ECustomerFgView;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.util.image.GlideLoaderUtils;
import com.zxl.baselib.util.ui.UIUtils;
import com.zxl.baselib.widget.CustomDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author crazyZhangxl on 2018/10/29.
 * Describe: 用户客户列表Fragment
 */
public class UserChooseActivity extends BaseActivity<ECustomerFgView, ECustomerFgPresenter> implements ECustomerFgView {
    @BindView(R.id.recCustom)
    XRecyclerView mRecCustom;

    private BaseQuickAdapter<RoleUserInfo,BaseViewHolder> mAdapter;
    private List<RoleUserInfo> mList = new ArrayList<>();
    /**
     * 用于保存当前获取页
     */
    private int mPage;
    private String mUserName;

    private boolean mIsFirst = true;

    private View mUserDetailView;
    private CustomDialog mUserDetailDialog;
    @Override
    public void init() {

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_role_expert_cus_list;
    }

    @Override
    protected ECustomerFgPresenter createPresenter() {
        return new ECustomerFgPresenter(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initAdapter();
        mPage = 1;
        mPresenter.queryRoleUserInfo(mPage,2);
    }


    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        mAdapter.setOnItemClickListener((adapter, view, position) -> {

            mUserName = mList.get(position-1).getUsername();

            Intent intent = new Intent();
            intent.putExtra("user", mUserName); //放置要传出的数据
            //这里是在Recycleview的适配器里，传了一个Activity才能用方法setResult
            setResult(100,intent);
            finish(); //关闭活动

        });
    }

    private void initAdapter() {
        mRecCustom.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mAdapter = new BaseQuickAdapter<RoleUserInfo, BaseViewHolder>(R.layout.item_customer_info,mList) {
            @Override
            protected void convert(BaseViewHolder helper, RoleUserInfo item) {
                ImageView imageView =  helper.getView(R.id.iv_expert);
                GlideLoaderUtils.display(mContext,imageView,item.getImage());
                helper.setText(R.id.tv_name,item.getUsername());
                if (TextUtils.isEmpty(item.getRealName())) {
                    helper.setVisible(R.id.tvEmailTitle,false);
                }else {
                    helper.setText(R.id.tv_email, item.getRealName());
                }
//                helper.setText(R.id.tv_maj,item.getTel());
            }
        };

        mRecCustom.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                //设置的刷新
                mPage = 1;
                mPresenter.queryRoleUserInfo(mPage,2);
            }

            @Override
            public void onLoadMore() {
                // 加载更多
                mPage++;
                mPresenter.queryRoleUserInfo(mPage,2);
            }
        });
        mRecCustom.setAdapter(mAdapter);
    }


    @Override
    public void queryCustomersSuccess(PageResponse<RoleUserInfo> customerInfoPageResponse) {
        if (mIsFirst){
            if (customerInfoPageResponse != null){
                mList.clear();
                mList.addAll(customerInfoPageResponse.getList());
                mAdapter.notifyDataSetChanged();
            }
            mIsFirst = false;
        }else {
            if (mPage == 1){
                if (customerInfoPageResponse != null){
                    mList.clear();
                    mList.addAll(customerInfoPageResponse.getList());
                    mAdapter.notifyDataSetChanged();
                    // 刷新成功
                    mRecCustom.refreshComplete();
                }

            }else {
                if (customerInfoPageResponse !=null && customerInfoPageResponse.getList() != null && customerInfoPageResponse.getList().size() >0){
                    mList.addAll(customerInfoPageResponse.getList());
                    mAdapter.notifyDataSetChanged();
                    mRecCustom.refreshComplete();
                }else {
                    mRecCustom.noMoreLoading();
                }
            }
        }
    }

    @Override
    public void queryCustomerFailure(String error) {
        UIUtils.showToast(error);
        mRecCustom.refreshComplete();
        if (mIsFirst){
            mIsFirst = false;
        }

        if (mPage > 1) {
            mPage--;
        }
    }

//    @Override
//    public void onBackPressedSupport() {
//        super.onBackPressedSupport();
//        Intent intent = new Intent();
//        intent.putExtra("user", ""); //放置要传出的数据
//        //这里是在Recycleview的适配器里，传了一个Activity才能用方法setResult
//        setResult(-100,intent);
//        finish(); //关闭活动
//    }

}
