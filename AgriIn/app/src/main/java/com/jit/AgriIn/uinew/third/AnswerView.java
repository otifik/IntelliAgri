package com.jit.AgriIn.uinew.third;

import com.jit.AgriIn.model.bean.AnswerBean;
import com.jit.AgriIn.model.bean.QuestionListBean;
import com.zxl.baselib.ui.base.BaseView;

/**
 * @author crazyZhangxl on 2018/10/12.
 * Describe: 百科View层
 */
public interface AnswerView extends BaseView {

    void queryQuestionSuccess(QuestionListBean questionListBean);

    void queryQuestionFailure(String error);

    void answerSuccess(AnswerBean answerBean);

    void answerFailure(String error);
}
