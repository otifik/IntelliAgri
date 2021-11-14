package com.jit.AgriIn.ui.fragment.baike;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.xrecyclerview.XRecyclerView;
import com.jit.AgriIn.R;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.model.bean.EBKDisUpdateBean;
import com.jit.AgriIn.model.cache.UserCache;
import com.jit.AgriIn.model.response.BaikeDiseaseBean;
import com.jit.AgriIn.model.response.PageResponse;
import com.jit.AgriIn.ui.activity.baike.BaikeDetailActivity;
import com.jit.AgriIn.ui.activity.baike.BaikeShDiseaseActivity;
import com.jit.AgriIn.ui.activity.role_expert.EBKDisUpdateActivity;
import com.jit.AgriIn.ui.presenter.baike.BaikeDiseaseFgPresenter;
import com.jit.AgriIn.ui.view.baike.BaikeDiseaseFgView;
import com.jit.AgriIn.uinew.third.TypeChangeEvent;
import com.zxl.baselib.ui.base.BaseFragment;
import com.zxl.baselib.util.image.GlideLoaderUtils;
import com.zxl.baselib.util.ui.UIUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * @author crazyZhangxl on 2018/10/26.
 * Describe:
 */
public class BaikeDiseaseListFragment extends BaseFragment<BaikeDiseaseFgView,BaikeDiseaseFgPresenter> implements BaikeDiseaseFgView {
    private static final String TYPE = "param1";
    @BindView(R.id.rec_baike)
    XRecyclerView mRecBaike;
    private BaseQuickAdapter<BaikeDiseaseBean,BaseViewHolder> mAdapter;
    private List<BaikeDiseaseBean> mList = new ArrayList<>();
    private String mType;

    /**
     * 用于保存当前获取页
     */
    private int mPage;

    private boolean mIsFirst = true;

    @Override
    public void queryBaikeSuccess(PageResponse<BaikeDiseaseBean> pageResponse) {
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
        mRxManager.post(AppConstant.RX_ADD_DISEASE_BAIKE,AppConstant.RX_POST_SUCCESS);
//        mList.remove(itemIndex);
//        mAdapter.notifyItemRemoved(itemIndex);
//        if (itemIndex != mList.size())
//            mAdapter.notifyItemRangeChanged(itemIndex,mList.size() - itemIndex);
    }

    @Override
    public void deleteBaikeFailure(String error) {
        UIUtils.showToast(error);
    }

    public static BaikeDiseaseListFragment newInstance(String param1) {
        BaikeDiseaseListFragment fragment = new BaikeDiseaseListFragment();
        Bundle args = new Bundle();
        args.putString(TYPE, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void init() {
        if (getArguments() != null) {
            mType = getArguments().getString(TYPE);
            Log.e("疾病类型",mType);
        }
    }

    private void initAdapter() {
        mRecBaike.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        mAdapter = new BaseQuickAdapter<BaikeDiseaseBean, BaseViewHolder>(R.layout.item_baike_selection,mList) {
            @Override
            protected void convert(BaseViewHolder helper, BaikeDiseaseBean item) {
                ImageView imageView =  helper.getView(R.id.ivImg);
//                Log.e("疾病imageUrl",item.getImage());
                GlideLoaderUtils.display(getActivity(),imageView,item.getImage());
                helper.setText(R.id.tvName,item.getDiseaseName());
                helper.setText(R.id.tvDes,item.getSymptom().replaceAll("\r|\n*",""));
                helper.setText(R.id.tvSource,item.getSource());
                if ("所有".equals(mType)){
                    helper.setText(R.id.tvType,item.getBig_category());
                }
//                else {
//                    helper.setText(R.id.tvType,item.getSubKind());
//                }
            }
        };

        mRecBaike.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                //设置的刷新
                mPage = 1;
                mPresenter.queryDiseaseBaike(mPage);
            }

            @Override
            public void onLoadMore() {
                // 加载更多
                mPage++;
                mPresenter.queryDiseaseBaike(mPage);
            }
        });
        mRecBaike.setAdapter(mAdapter);
    }

    /**
     * 懒加载
     *
     * @param savedInstanceState
     */
    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        mPage = 1;
        mPresenter.queryDiseaseBaike(mPage);
    }


    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_baike_custom;
    }

    @Override
    protected BaikeDiseaseFgPresenter createPresenter() {
        return new BaikeDiseaseFgPresenter((BaikeDetailActivity)getActivity());
    }

    @Override
    public void initView(View rootView) {
        initAdapter();
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(), BaikeShDiseaseActivity.class);
                intent.putExtra(AppConstant.EXTRA_BAIKE_ID,mList.get(position-1).getId());
                jumpToActivity(intent);
            }
        });


        //对的 有头布局我凑
        mAdapter.setOnItemLongClickListener((adapter, view, position) -> {
            if (UserCache.getUserRole().contains("USER")){
                UIUtils.showToast("无相关权限");
            }else {
                showChooseView(position);
            }
            return false;
        });

        /**
         *
         */
        mRxManager.on(AppConstant.RX_ADD_DISEASE_BAIKE, (Consumer<String>) o -> {
            if (AppConstant.RX_ON_SUCCESS.equals(o)) {
                mPage = 1;
                mPresenter.queryDiseaseBaike( mPage);
            }
        });

        mRxManager.on(AppConstant.RX_UPDATE_DISEASE_BAIKE, (Consumer<EBKDisUpdateBean>) updateBean -> {
            mList.set(updateBean.getIndex()-1,updateBean.getBaikeDiseaseBean());
            mAdapter.notifyItemChanged(updateBean.getIndex());
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void refreshData(final TypeChangeEvent event){
        if (event.getContentPos() == 0){
            mType = event.getContentType();
            mPage = 1;
            mPresenter.queryDiseaseBaike(mPage);
        }
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
        Intent intent = new Intent(getActivity(), EBKDisUpdateActivity.class);
        intent.putExtra(AppConstant.EXTRA_BAIKE_ID,mList.get(itemPosition-1).getId());
        intent.putExtra(AppConstant.EXTRA_ITEM_INDEX,itemPosition);
        jumpToActivity(intent);
    }
}
