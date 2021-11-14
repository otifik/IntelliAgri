package com.jit.AgriIn.uinew.third;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.xrecyclerview.XRecyclerView;
import com.jit.AgriIn.R;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.model.bean.AnswerBean;
import com.jit.AgriIn.model.bean.QuestionListBean;
import com.jit.AgriIn.model.cache.UserCache;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.util.image.GlideLoaderUtils;
import com.zxl.baselib.util.ui.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class AnswerListActivity extends BaseActivity<AnswerView,AnswerPresenter> implements AnswerView {

    @BindView(R.id.ivToolbarNavigation)
    ImageView mIvToolbarNavigation;
    @BindView(R.id.vToolbarDivision)
    View mVToolbarDivision;
    @BindView(R.id.tvToolbarTitle)
    TextView mTvToolbarTitle;
    @BindView(R.id.tv_publish_now)
    TextView mTvPublishNow;

    @BindView(R.id.iv_portrait)
    ImageView ivPortrait;
    @BindView(R.id.ivQuestion)
    ImageView ivQuestion;
    @BindView(R.id.tv_mainuser)
    TextView tvMainUser;
    @BindView(R.id.tv_maintime)
    TextView tvMainTime;
    @BindView(R.id.tv_usertype)
    TextView tvUserType;
    @BindView(R.id.tv_content)
    TextView tvQaContent;

    @BindView(R.id.rec_baike)
    XRecyclerView mRecAnswerList;
    private BaseQuickAdapter<QuestionListBean.AnswersBean, BaseViewHolder> mAdapter;
    private List<QuestionListBean.AnswersBean> mList = new ArrayList<>();

    private int mQuestionID = 0;



    @Override
    protected void init() {
        if (getIntent() != null){
            mQuestionID = getIntent().getIntExtra(AppConstant.QUESTION_ID, -1);
            Log.e("问答id",String.valueOf(mQuestionID));
        }
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_answerlist;
    }

    @Override
    protected AnswerPresenter createPresenter() {
        return new AnswerPresenter(this);
    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        mTvToolbarTitle.setText("回答详情");
        initAdapter();
    }

    private void initAdapter() {
        mRecAnswerList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mAdapter = new BaseQuickAdapter<QuestionListBean.AnswersBean, BaseViewHolder>(R.layout.item_qalist,mList) {
            @Override
            protected void convert(BaseViewHolder helper, QuestionListBean.AnswersBean item) {

                ImageView ivSubPortrait = helper.getView(R.id.iv_subportrait);
                TextView tvSubMainUser = helper.getView(R.id.tv_submainuser);
                TextView tvSubMainTime = helper.getView(R.id.tv_submaintime);
                TextView tvSubUserType = helper.getView(R.id.tv_subusertype);
                TextView tvSubQaContent = helper.getView(R.id.tv_subcontent);

                if (UserCache.getUserImage() != null){
                    GlideLoaderUtils.display(mContext, ivSubPortrait, item.getUserImage());
                }


                tvSubMainUser.setText(item.getUsername());
                tvSubMainTime.setText(item.getPublishTime());
                if (item.getUserType() == 0){
                    tvSubUserType.setText("普通用户");
                }else if (item.getUserType() == 1){
                    tvSubUserType.setText("专家");
                }
                tvSubQaContent.setText(item.getContent());
            }
        };

        mRecAnswerList.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mPresenter.queryOneQuestion(mQuestionID);
            }

            @Override
            public void onLoadMore() {
                // 加载更多
//                mPage++;
//                mPresenter.queryExpertInfo(mPage);
                mRecAnswerList.noMoreLoading();
            }
        });
        mRecAnswerList.setAdapter(mAdapter);

    }

    @Override
    protected void initData() {
        mPresenter.queryOneQuestion(mQuestionID);
    }

    @Override
    protected void initListener() {
        mIvToolbarNavigation.setOnClickListener(v -> onBackPressed());

    }

    @Override
    public void queryQuestionSuccess(QuestionListBean questionListBean) {
        if (UserCache.getUserImage() != null){
            GlideLoaderUtils.display(mContext, ivPortrait, questionListBean.getUserImage());
        }

        if (questionListBean.getImage() != null){
            GlideLoaderUtils.display(mContext, ivQuestion, questionListBean.getImage());
        }else {
            ivQuestion.setVisibility(View.GONE);
        }

        tvMainUser.setText(questionListBean.getUsername());
        tvMainTime.setText(questionListBean.getPublishTime());
        if (questionListBean.getUserType() == 0){
            tvUserType.setText("普通用户");
        }else if (questionListBean.getUserType() == 1){
            tvUserType.setText("专家");
        }
        tvQaContent.setText(questionListBean.getDescription());

        mList.clear();
        mList.addAll(questionListBean.getAnswers());
        mAdapter.notifyDataSetChanged();
        // 刷新成功
        mRecAnswerList.refreshComplete();
    }

    @Override
    public void queryQuestionFailure(String error) {
        UIUtils.showToast(error);
    }

    @Override
    public void answerSuccess(AnswerBean answerBean) {
//        UIUtils.showToast("更新成功");
//        finish();
    }

    @Override
    public void answerFailure(String error) {
//        UIUtils.showToast(error);
    }
}
