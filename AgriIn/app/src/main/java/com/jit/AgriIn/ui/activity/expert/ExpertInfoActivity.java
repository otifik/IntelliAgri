package com.jit.AgriIn.ui.activity.expert;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dycdyc.rtmp.demoffmpeg.utils.ffmpegContext;
import com.example.xrecyclerview.XRecyclerView;
import com.jit.AgriIn.R;
import com.jit.AgriIn.model.response.PageResponse;
import com.jit.AgriIn.model.response.RoleUserInfo;
import com.jit.AgriIn.ui.presenter.expert.ExpertInfoPresenter;
import com.jit.AgriIn.ui.view.expert.ExpertInfoView;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.util.image.GlideLoaderUtils;
import com.zxl.baselib.util.ui.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by yuanyuan on 2018/10/29.
 */

public class ExpertInfoActivity extends BaseActivity<ExpertInfoView,ExpertInfoPresenter> implements ExpertInfoView{

    @BindView(R.id.ivToolbarNavigation)
    ImageView mIvToolbarNavigation;
    @BindView(R.id.vToolbarDivision)
    View mVToolbarDivision;
    @BindView(R.id.tvToolbarTitle)
    TextView mTvToolbarTitle;

    @BindView(R.id.rec_baike)
    XRecyclerView mRecExpertInfo;
    private BaseQuickAdapter<RoleUserInfo,BaseViewHolder> mAdapter;
    private List<RoleUserInfo> mList = new ArrayList<>();

    private  int IMAGES[] = {R.drawable.btn_rounded,R.drawable.btn_rounded_1,R.drawable.btn_rounded_2
            ,R.drawable.btn_rounded_3,R.drawable.btn_rounded_4};
    /**
     * 用于保存当前获取页
     */
    private int mPage;

    private Button mButton;
    ffmpegContext ffmpegctx;

    @Override
    protected void init() {

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_expert_info;
    }

    @Override
    protected ExpertInfoPresenter createPresenter() {
        return new ExpertInfoPresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPage = 1;
        mPresenter.queryExpertInfo(mPage);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mTvToolbarTitle.setText("专家列表");
        initAdapter();
    }

    private void initAdapter() {
        mRecExpertInfo.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mAdapter = new BaseQuickAdapter<RoleUserInfo, BaseViewHolder>(R.layout.item_expert_info,mList) {
            @Override
            protected void convert(BaseViewHolder helper, RoleUserInfo item) {
                ImageView imageView =  helper.getView(R.id.iv_expert);
                GlideLoaderUtils.display(ExpertInfoActivity.this,imageView,item.getImage());

                TextView tvName = helper.getView(R.id.tv_name);
                TextView tvRealName = helper.getView(R.id.tv_realname);
                TextView tvTel = helper.getView(R.id.tv_tel);

                if (item.getUsername() != null){
                    helper.setText(R.id.tv_name,"用户名：" + item.getUsername());
                }else {
                    tvName.setVisibility(View.GONE);
                }
                if (item.getRealName() != null){
                    helper.setText(R.id.tv_realname,"姓名：" + item.getUsername());
                }else {
                    tvRealName.setVisibility(View.GONE);
                }

//                helper.setText(R.id.tv_addr,item.get);
//                helper.setText(R.id.tv_tel,item.getT);

                Button btnMaj = helper.getView(R.id.btn_des);
                if (item.getDescription() != null){
                    helper.setText(R.id.btn_des,"简介：" + item.getDescription());
                    btnMaj.setBackgroundResource(IMAGES[helper.getAdapterPosition()%5]);
                }else {
                    btnMaj.setVisibility(View.GONE);
                }

//                Button btnMaj = helper.getView(R.id.btn_maj);
//                btnMaj.setText(item.getMajor());

            }
        };

        mRecExpertInfo.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                //设置的刷新
                mPage = 1;
                mPresenter.queryExpertInfo(mPage);
            }

            @Override
            public void onLoadMore() {
                // 加载更多
                mPage++;
                mPresenter.queryExpertInfo(mPage);
            }
        });
        mRecExpertInfo.setAdapter(mAdapter);
    }



    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        mIvToolbarNavigation.setOnClickListener(v -> onBackPressed());
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                showMaterialDialog("视频通话", "点击确认发起视频通话请求", "确认", "取消",
                        new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                                ffmpegctx = new ffmpegContext(getApplicationContext());
////                                ffmpegctx.setUrl("rtmp://223.2.197.244:8062/live/1159930365");
//                                ffmpegctx.setUrl("rtmp://39.106.54.222:1935/live/1159930365");
//                                Intent intent = new Intent(ExpertInfoActivity.this, CameraActivity.class);
//                                startActivity(intent);
                                UIUtils.showToast("功能暂未开放");
                            }
                        },
                        new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                            }
                        });
            }
        });
    }

    @Override
    public void queryExpertInfoSuccess(PageResponse<RoleUserInfo> pageResponse) {
//        Log.e("error","成功" + pageResponse.getList().get(0).getCity());
        if (mPage == 1){
            if (pageResponse != null){
                mList.clear();
                mList.addAll(pageResponse.getList());
                mAdapter.notifyDataSetChanged();
                // 刷新成功
                mRecExpertInfo.refreshComplete();
            }

        }else {
            if (pageResponse !=null && pageResponse.getList() != null && pageResponse.getList().size() >0){
                mList.addAll(pageResponse.getList());
                mAdapter.notifyDataSetChanged();
                mRecExpertInfo.refreshComplete();
            }else {
                mRecExpertInfo.noMoreLoading();
            }
        }
    }

    @Override
    public void queryExpertInfoFailure(String error) {
        Log.e("error",""+error);
        UIUtils.showToast(error);
        mRecExpertInfo.refreshComplete();
        if (mPage > 1) {
            mPage--;
        }
    }
}
