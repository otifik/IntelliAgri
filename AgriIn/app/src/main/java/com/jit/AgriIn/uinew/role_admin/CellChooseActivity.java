package com.jit.AgriIn.uinew.role_admin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.xrecyclerview.XRecyclerView;
import com.jit.AgriIn.R;
import com.jit.AgriIn.model.response.CellResponse;
import com.jit.AgriIn.model.response.PageResponse;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.util.ui.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author crazyZhangxl on 2018/10/29.
 * Describe: 用户客户列表Fragment
 */
public class CellChooseActivity extends BaseActivity<CellListView, CellListPresenter> implements CellListView {
    @BindView(R.id.recCustom)
    XRecyclerView mRecCustom;

    private BaseQuickAdapter<CellResponse,BaseViewHolder> mAdapter;
    private List<CellResponse> mList = new ArrayList<>();
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

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_role_expert_cus_list;
    }

    @Override
    protected CellListPresenter createPresenter() {
        return new CellListPresenter(this);
    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        initAdapter();
        mPage = 1;
        mPresenter.getAllUserCells(mUserName,mPage);
    }


    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        mAdapter.setOnItemClickListener((adapter, view, position) -> {

            mCellId = mList.get(position-1).getId();

            Intent intent = new Intent();
            intent.putExtra("cellid", mCellId); //放置要传出的数据
            //这里是在Recycleview的适配器里，传了一个Activity才能用方法setResult
            setResult(Activity.RESULT_OK,intent);
            finish(); //关闭活动

        });
    }

    private void initAdapter() {
        mRecCustom.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mAdapter = new BaseQuickAdapter<CellResponse, BaseViewHolder>(R.layout.item_cell_info,mList) {
            @Override
            protected void convert(BaseViewHolder helper, CellResponse item) {
                helper.setText(R.id.tvName,item.getName());
                helper.setText(R.id.tvPro,item.getProduct());
                helper.setText(R.id.tvType,item.getType());
                helper.setText(R.id.tvUserName,item.getUsername());

            }
        };

        mRecCustom.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                //设置的刷新
                mPage = 1;
                mPresenter.getAllUserCells(mUserName,mPage);
            }

            @Override
            public void onLoadMore() {
                // 加载更多
                mPage++;
                mPresenter.getAllUserCells(mUserName,mPage);
            }
        });
        mRecCustom.setAdapter(mAdapter);
    }




    @Override
    public void queryCellSuccess(PageResponse<CellResponse> cellResponseList) {
        if (mIsFirst){
            if (cellResponseList != null){
                mList.clear();
                mList.addAll(cellResponseList.getList());
                mAdapter.notifyDataSetChanged();
            }
            mIsFirst = false;
        }else {
            if (mPage == 1){
                if (cellResponseList != null){
                    mList.clear();
                    mList.addAll(cellResponseList.getList());
                    mAdapter.notifyDataSetChanged();
                    // 刷新成功
                    mRecCustom.refreshComplete();
                }

            }else {
                if (cellResponseList !=null && cellResponseList.getList() != null && cellResponseList.getList().size() >0){
                    mList.addAll(cellResponseList.getList());
                    mAdapter.notifyDataSetChanged();
                    mRecCustom.refreshComplete();
                }else {
                    mRecCustom.noMoreLoading();
                }
            }
        }
    }

    @Override
    public void queryCellFailure(String error) {
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
