package com.jit.AgriIn.uinew.third;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jit.AgriIn.R;
import com.jit.AgriIn.model.bean.QuestionListBean;
import com.jit.AgriIn.model.cache.UserCache;
import com.zxl.baselib.util.image.GlideLoaderUtils;

import java.util.List;

public class QuestionListAdapter extends BaseQuickAdapter<QuestionListBean,BaseViewHolder> {


    public QuestionListAdapter(int layoutResId, @Nullable List<QuestionListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, QuestionListBean item) {
        ImageView ivPortrait = helper.getView(R.id.iv_portrait);
        ImageView ivQuestion = helper.getView(R.id.ivQuestion);
        TextView tvMainUser = helper.getView(R.id.tv_mainuser);
        TextView tvMainTime = helper.getView(R.id.tv_maintime);
        TextView tvUserType = helper.getView(R.id.tv_usertype);
        TextView tvQaContent = helper.getView(R.id.tv_content);

        LinearLayout llSub1 = helper.getView(R.id.ll_subcomment1);
        LinearLayout llSub2 = helper.getView(R.id.ll_subcomment2);
        LinearLayout llSub3 = helper.getView(R.id.ll_subcomment3);
        TextView tvUser1 = helper.getView(R.id.tv_user1);
        TextView tvUser2 = helper.getView(R.id.tv_user2);
        TextView tvUser3 = helper.getView(R.id.tv_user3);
        TextView tvSub1 = helper.getView(R.id.tv_sub1);
        TextView tvSub2 = helper.getView(R.id.tv_sub2);
        TextView tvSub3 = helper.getView(R.id.tv_sub3);
        TextView tvCheckAll = helper.getView(R.id.tv_checkall);

        helper.addOnClickListener(R.id.rl_qaanswer).addOnClickListener(R.id.rl_qacollect).addOnClickListener(R.id.rl_qashare)
        .addOnClickListener(R.id.tv_checkall);

        if (UserCache.getUserImage() != null){
            GlideLoaderUtils.display(mContext, ivPortrait, item.getUserImage());
        }

        if (item.getImage() != null){
            ivQuestion.setVisibility(View.VISIBLE);
            GlideLoaderUtils.display(mContext, ivQuestion, item.getImage());
        }else {
            ivQuestion.setVisibility(View.GONE);
        }

        tvMainUser.setText(item.getUsername());
        tvMainTime.setText(item.getPublishTime());
        if (item.getUserType() == 0){
            tvUserType.setText("普通用户");
        }else if (item.getUserType() == 1){
            tvUserType.setText("专家");
        }
        tvQaContent.setText(item.getDescription());


        if (item.getAnswerNum() == 0){
            llSub1.setVisibility(View.GONE);
            llSub2.setVisibility(View.GONE);
            llSub3.setVisibility(View.GONE);
            tvCheckAll.setVisibility(View.GONE);

        }else if (item.getAnswerNum() == 1){
            llSub1.setVisibility(View.VISIBLE);
            llSub2.setVisibility(View.GONE);
            llSub3.setVisibility(View.GONE);
            tvCheckAll.setVisibility(View.GONE);

            tvUser1.setText(item.getAnswers().get(0).getUsername());
            tvSub1.setText(item.getAnswers().get(0).getContent());
        }else if (item.getAnswerNum() == 2){
            llSub1.setVisibility(View.VISIBLE);
            llSub2.setVisibility(View.VISIBLE);
            llSub3.setVisibility(View.GONE);
            tvCheckAll.setVisibility(View.GONE);


            tvUser1.setText(item.getAnswers().get(0).getUsername());
            tvSub1.setText(item.getAnswers().get(0).getContent());
            tvUser2.setText(item.getAnswers().get(1).getUsername());
            tvSub2.setText(item.getAnswers().get(1).getContent());
        }else if (item.getAnswerNum() == 3){
            llSub1.setVisibility(View.VISIBLE);
            llSub2.setVisibility(View.VISIBLE);
            llSub3.setVisibility(View.VISIBLE);
            tvCheckAll.setVisibility(View.GONE);

            tvUser1.setText(item.getAnswers().get(0).getUsername());
            tvSub1.setText(item.getAnswers().get(0).getContent());
            tvUser2.setText(item.getAnswers().get(1).getUsername());
            tvSub2.setText(item.getAnswers().get(1).getContent());
            tvUser3.setText(item.getAnswers().get(2).getUsername());
            tvSub3.setText(item.getAnswers().get(2).getContent());
        }else if (item.getAnswerNum() > 3){
            llSub1.setVisibility(View.VISIBLE);
            llSub2.setVisibility(View.VISIBLE);
            llSub3.setVisibility(View.VISIBLE);
            tvCheckAll.setVisibility(View.VISIBLE);
            tvUser1.setText(item.getAnswers().get(0).getUsername());
            tvSub1.setText(item.getAnswers().get(0).getContent());
            tvUser2.setText(item.getAnswers().get(1).getUsername());
            tvSub2.setText(item.getAnswers().get(1).getContent());
            tvUser3.setText(item.getAnswers().get(2).getUsername());
            tvSub3.setText(item.getAnswers().get(2).getContent());
            tvCheckAll.setText("查看全部" + item.getAnswerNum() + "条回答");
        }




    }
}
