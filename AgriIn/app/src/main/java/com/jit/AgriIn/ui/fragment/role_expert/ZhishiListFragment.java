package com.jit.AgriIn.ui.fragment.role_expert;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.xrecyclerview.XRecyclerView;
import com.jit.AgriIn.R;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.model.cache.UserCache;
import com.jit.AgriIn.model.response.PageResponse;
import com.jit.AgriIn.uinew.third.ZhishiBean;
import com.jit.AgriIn.uinew.third.ZhishiDetailActivity;
import com.jit.AgriIn.uinew.third.ZhishiPresenter;
import com.jit.AgriIn.uinew.third.ZhishiView;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BaseFragment;
import com.zxl.baselib.util.image.GlideLoaderUtils;
import com.zxl.baselib.util.ui.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author crazyZhangxl on 2018/10/29.
 * Describe:
 */
public class ZhishiListFragment extends BaseFragment<ZhishiView, ZhishiPresenter> implements ZhishiView {
    @BindView(R.id.rec_baike)
    XRecyclerView mRecBaike;
    private BaseQuickAdapter<ZhishiBean, BaseViewHolder> mAdapter;
    private List<ZhishiBean> mList = new ArrayList<>();

    /**
     * 用于保存当前获取页
     */
    private int mPage;

    private boolean mIsFirst = true;

    @Override
    public void init() {

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_baike_custom;
    }

    @Override
    protected ZhishiPresenter createPresenter() {
        return new ZhishiPresenter((BaseActivity)getActivity());
    }

    @Override
    public void initView(View rootView) {
        mPage = 1;
        mPresenter.queryZhishi(mPage);
    }

    @Override
    public void initData() {
        initAdapter();
    }

    @Override
    public void initListener() {
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(), ZhishiDetailActivity.class);
                intent.putExtra(AppConstant.EXTRA_BAIKE_ID,mList.get(position-1).getId());
                jumpToActivity(intent);
            }
        });


        //对的 有头布局我凑
        mAdapter.setOnItemLongClickListener((adapter, view, position) -> {
            if (UserCache.getUserRole().contains("USER")){
                UIUtils.showToast("无相关权限");
            }else {
//                showChooseView(position);
            }
            return false;
        });
    }

    @Override
    public void queryBaikeSuccess(PageResponse<ZhishiBean> pageResponse) {
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

    private void initAdapter() {
        mRecBaike.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        mAdapter = new BaseQuickAdapter<ZhishiBean, BaseViewHolder>(R.layout.item_baike_selection,mList) {
            @Override
            protected void convert(BaseViewHolder helper, ZhishiBean item) {
                ImageView imageView =  helper.getView(R.id.ivImg);
//                Log.e("疾病imageUrl",item.getImage());
                GlideLoaderUtils.display(mContext,imageView,item.getImage());
                helper.setText(R.id.tvName,item.getName());
                helper.setText(R.id.tvDes, Html.fromHtml(item.getContent()));
                helper.setText(R.id.tvSource,item.getSource());
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
                mPresenter.queryZhishi(mPage);
            }

            @Override
            public void onLoadMore() {
                // 加载更多
                mPage++;
                mPresenter.queryZhishi(mPage);
            }
        });
        mRecBaike.setAdapter(mAdapter);
    }


//    private void showChooseView(int position) {
//        Dialog dialog = new Dialog(getActivity(),R.style.MyDialog);
//        View choose = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_choose, null);
//        dialog.setCancelable(true);
//        Window window = dialog.getWindow();
//        window.setGravity(Gravity.BOTTOM);
//        window.setWindowAnimations(R.style.AnimBottom);
//        window.getDecorView().setPadding(0, 0, 0, 0);
//        WindowManager.LayoutParams lp = window.getAttributes();
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        window.setAttributes(lp);
//        dialog.setContentView(choose);
//        setViewAndListener(dialog,choose,position);
//        dialog.show();
//    }
}
