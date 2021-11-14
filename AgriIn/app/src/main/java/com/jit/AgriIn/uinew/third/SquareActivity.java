package com.jit.AgriIn.uinew.third;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.dycdyc.rtmp.demoffmpeg.utils.ffmpegContext;
import com.example.xrecyclerview.XRecyclerView;
import com.jit.AgriIn.R;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.model.bean.QuestionListBean;
import com.jit.AgriIn.model.response.PageResponse;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.util.ui.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by yuanyuan on 2018/10/29.
 */

public class SquareActivity extends BaseActivity<QuestionListView,QuestionListPresenter> implements QuestionListView{

    @BindView(R.id.ivToolbarNavigation)
    ImageView mIvToolbarNavigation;
    @BindView(R.id.vToolbarDivision)
    View mVToolbarDivision;
    @BindView(R.id.tvToolbarTitle)
    TextView mTvToolbarTitle;
    @BindView(R.id.ibAddMenu)
    ImageButton ibAddMenu;

    @BindView(R.id.rec_baike)
    XRecyclerView mRecExpertInfo;
    private QuestionListAdapter mAdapter;
    private List<QuestionListBean> mList = new ArrayList<>();

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
        return R.layout.activity_square;
    }

    @Override
    protected QuestionListPresenter createPresenter() {
        return new QuestionListPresenter(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mPage = 1;
        mPresenter.queryQuestionList(mPage);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ibAddMenu.setVisibility(View.VISIBLE);
        mTvToolbarTitle.setText("问答广场");
        initAdapter();
    }

    private void initAdapter() {
        mRecExpertInfo.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mAdapter = new QuestionListAdapter(R.layout.item_qa,mList);

        mRecExpertInfo.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                //设置的刷新
                mPage = 1;
                mPresenter.queryQuestionList(mPage);
            }

            @Override
            public void onLoadMore() {
                // 加载更多
                mPage++;
                mPresenter.queryQuestionList(mPage);
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

        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            QuestionListBean questionListBean = mList.get(position - 1);
            switch (view.getId()){
                case R.id.rl_qacollect:
                    UIUtils.showToast("功能暂未开放");
                    break;
                case R.id.rl_qashare:
                    UIUtils.showToast("功能暂未开放");
                    break;
                case R.id.rl_qaanswer:
                    Intent intent = new Intent(SquareActivity.this, AnswerActivity.class);
                    intent.putExtra(AppConstant.QUESTION_ID,questionListBean.getId());
                    jumpToActivity(intent);
                    break;
                case R.id.tv_checkall:
                    Intent intent1 = new Intent(SquareActivity.this, AnswerListActivity.class);
                    intent1.putExtra(AppConstant.QUESTION_ID,questionListBean.getId());
                    jumpToActivity(intent1);
                    break;
            }
        });


        ibAddMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumpToActivity(AskQuestionActivity.class);
            }
        });
    }

//    @Override
//    public void queryExpertInfoSuccess(PageResponse<ExpertInfoResponse> pageResponse) {
//        Log.e("error","成功" + pageResponse.getList().get(0).getCity());
//        if (mPage == 1){
//            if (pageResponse != null){
//                mList.clear();
//                mList.addAll(pageResponse.getList());
//                mAdapter.notifyDataSetChanged();
//                // 刷新成功
//                mRecExpertInfo.refreshComplete();
//            }
//
//        }else {
//            if (pageResponse !=null && pageResponse.getList() != null && pageResponse.getList().size() >0){
//                mList.addAll(pageResponse.getList());
//                mAdapter.notifyDataSetChanged();
//                mRecExpertInfo.refreshComplete();
//            }else {
//                mRecExpertInfo.noMoreLoading();
//            }
//        }
//    }
//
//    @Override
//    public void queryExpertInfoFailure(String error) {
//        Log.e("error",""+error);
//        UIUtils.showToast(error);
//        mRecExpertInfo.refreshComplete();
//        if (mPage > 1) {
//            mPage--;
//        }
//    }

    @Override
    public void queryQuestionSuccess(PageResponse<QuestionListBean> pageResponse) {
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
    public void queryQuestionFailure(String error) {
        Log.e("error",""+error);
        UIUtils.showToast(error);
        mRecExpertInfo.refreshComplete();
        if (mPage > 1) {
            mPage--;
        }
    }
}
