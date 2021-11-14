package com.jit.AgriIn.ui.fragment.role_expert;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.xrecyclerview.XRecyclerView;
import com.jit.AgriIn.R;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.model.bean.EBKFeedUpdateBean;
import com.jit.AgriIn.model.response.BaikeFeedBean;
import com.jit.AgriIn.model.response.PageResponse;
import com.jit.AgriIn.ui.activity.baike.BaikeShFeedActivity;
import com.jit.AgriIn.ui.activity.role_expert.EBKFeedAddActivity;
import com.jit.AgriIn.ui.activity.role_expert.EBKFeedUpdateActivity;
import com.jit.AgriIn.ui.activity.role_expert.RoleExpertActivity;
import com.jit.AgriIn.ui.presenter.baike.BaikeFeedFgPresenter;
import com.jit.AgriIn.ui.view.baike.BaikeFeedFgView;
import com.zxl.baselib.ui.base.BaseFragment;
import com.zxl.baselib.util.LoggerUtils;
import com.zxl.baselib.util.image.GlideLoaderUtils;
import com.zxl.baselib.util.ui.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * @author crazyZhangxl on 2018/10/29.
 * Describe: EBKFeedFragment
 */
public class EBKFeedFragment extends BaseFragment<BaikeFeedFgView, BaikeFeedFgPresenter> implements BaikeFeedFgView {

    @BindView(R.id.rec_baike)
    XRecyclerView mRecBaike;
    @BindView(R.id.btnAddBaike)
    Button mBtnAddBaike;
    private BaseQuickAdapter<BaikeFeedBean,BaseViewHolder> mAdapter;
    private List<BaikeFeedBean> mList = new ArrayList<>();
    /**
     * 用于保存当前获取页
     */
    private int mPage;
    /**
     * 用于记录对应的类别
     */
    private String mType;

    private boolean mIsFirst = true;



    @Override
    public void init() {
        mType = "所有";
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_role_expert_list_show;
    }


    @Override
    protected BaikeFeedFgPresenter createPresenter() {
        return new BaikeFeedFgPresenter((RoleExpertActivity) getActivity());
    }

    @Override
    public void initView(View rootView) {
        mBtnAddBaike.setText("添加投喂百科");
        initAdapter();
    }

    private void initAdapter() {
        mRecBaike.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        mAdapter = new BaseQuickAdapter<BaikeFeedBean, BaseViewHolder>(R.layout.item_baike_selection,mList) {
            @Override
            protected void convert(BaseViewHolder helper, BaikeFeedBean item) {
                ImageView imageView =  helper.getView(R.id.ivImg);
                GlideLoaderUtils.display(getActivity(),imageView,item.getImage());
                helper.setText(R.id.tvName,item.getName());
//                helper.setText(R.id.tvDes,item.getManualInstruct());
//                helper.setText(R.id.tvType,item.getSubKind());

            }
        };

        mRecBaike.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                //设置的刷新
                mPage = 1;
                mPresenter.queryKindBaike(mPage);
            }

            @Override
            public void onLoadMore() {
                // 加载更多
                mPage++;
                mPresenter.queryKindBaike(mPage);
            }
        });
        mRecBaike.setAdapter(mAdapter);
    }


    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            Intent intent = new Intent(getActivity(), BaikeShFeedActivity.class);
            intent.putExtra(AppConstant.EXTRA_BAIKE_ID,mList.get(position-1).getId());
            jumpToActivity(intent);
        });

        mAdapter.setOnItemLongClickListener((adapter, view, position) -> {
            showChooseView(position);
            return false;
        });

        mBtnAddBaike.setOnClickListener(v -> jumpToActivity(EBKFeedAddActivity.class));

        mRxManager.on(AppConstant.RX_ADD_FEED_BAIKE, (Consumer<String>) o -> {
            if (AppConstant.RX_ON_SUCCESS.equals(o)) {
                mPage = 1;
                mPresenter.queryKindBaike(mPage);
            }
        });

        mRxManager.on(AppConstant.RX_UPDATE_FEED_BAIKE, (Consumer<EBKFeedUpdateBean>) updateBean -> {
            mList.set(updateBean.getIndex()-1,updateBean.getBaikeFeedBean());
            mAdapter.notifyItemChanged(updateBean.getIndex());
        });

    }
    /**
     * 懒加载
     *
     * @param savedInstanceState
     */
    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        mPage = 1;
        mPresenter.queryKindBaike(mPage);
    }


    @Override
    public void queryBaikeSuccess(PageResponse<BaikeFeedBean> pageResponse) {
        LoggerUtils.logE("刷新","-----请求成功---");
        if (mIsFirst){
            if (pageResponse != null){
                mList.clear();
                mList.addAll(pageResponse.getList());
                mAdapter.notifyDataSetChanged();
            }
            mIsFirst = false;
        }else {
            if (mPage == 1){
                if (pageResponse != null){
                    mList.clear();
                    mList.addAll(pageResponse.getList());
                    mAdapter.notifyDataSetChanged();
                    // 刷新成功
                    mRecBaike.refreshComplete();
                }

            }else {
                if (pageResponse !=null && pageResponse.getList() != null && pageResponse.getList().size() >0){
                    mList.addAll(pageResponse.getList());
                    mAdapter.notifyDataSetChanged();
                    mRecBaike.refreshComplete();
                }else {
                    mRecBaike.noMoreLoading();
                }
            }
        }
    }

    @Override
    public void queryBaikeFailure(String error) {
        UIUtils.showToast(error);
        mRecBaike.refreshComplete();
        if (mIsFirst){
            mIsFirst = false;
        }

        if (mPage > 1) {
            mPage--;
        }
    }

    @Override
    public void deleteBaikeSuccess(int itemIndex) {
        mList.remove(itemIndex);
        mAdapter.notifyItemRemoved(itemIndex);
    }

    @Override
    public void deleteBaikeFailure(String error) {
        UIUtils.showToast(error);
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
            jumpToConfigEdit(itemPosition);
        });

        vCancel.setOnClickListener(view -> dialog.dismiss());

        // 删除百科信息
        vDelete.setOnClickListener(view -> {
            dialog.dismiss();
            mPresenter.deleteBaikeByID(mList.get(itemPosition-1).getId(),itemPosition);
        });
    }

    private void jumpToConfigEdit(int itemPosition) {
        Intent intent = new Intent(getActivity(), EBKFeedUpdateActivity.class);
        intent.putExtra(AppConstant.EXTRA_BAIKE_ID,mList.get(itemPosition -1).getId());
        intent.putExtra(AppConstant.EXTRA_ITEM_INDEX,itemPosition);
        jumpToActivity(intent);
    }

    private void showChooseView(int position) {
        Dialog dialog = new Dialog(getActivity(),R.style.MyDialog);
        View choose = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_choose, null);
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
        setViewAndListener(dialog,choose,position);
        dialog.show();
    }

}
