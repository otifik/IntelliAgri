package com.jit.AgriIn.uinew.third;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jit.AgriIn.R;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.model.bean.AnswerBean;
import com.jit.AgriIn.model.bean.QuestionListBean;
import com.jit.AgriIn.model.cache.UserCache;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.util.image.GlideLoaderUtils;
import com.zxl.baselib.util.ui.UIUtils;

import butterknife.BindView;

public class AnswerActivity extends BaseActivity<AnswerView,AnswerPresenter> implements AnswerView {

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
    @BindView(R.id.etDef)
    EditText mEtDef;

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
        return R.layout.activity_answer;
    }

    @Override
    protected AnswerPresenter createPresenter() {
        return new AnswerPresenter(this);
    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        mTvToolbarTitle.setText("回答问题");
        mTvPublishNow.setVisibility(View.VISIBLE);
        mTvPublishNow.setText("发布");
    }

    @Override
    protected void initData() {
        mPresenter.queryOneQuestion(mQuestionID);
    }

    @Override
    protected void initListener() {
        mIvToolbarNavigation.setOnClickListener(v -> onBackPressed());

        mTvPublishNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mEtDef.getText().toString().isEmpty()){
                    UIUtils.showToast("请填写对问题的回答");
                }else {
                    mPresenter.answerQuestion(mEtDef.getText().toString(),mQuestionID);
                }
            }
        });
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
    }

    @Override
    public void queryQuestionFailure(String error) {
        UIUtils.showToast(error);
    }

    @Override
    public void answerSuccess(AnswerBean answerBean) {
        UIUtils.showToast("更新成功");
        finish();
    }

    @Override
    public void answerFailure(String error) {
        UIUtils.showToast(error);
    }
}
