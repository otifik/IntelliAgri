package com.jit.AgriIn.uinew.third;

import com.jit.AgriIn.model.bean.QuestionListBean;
import com.jit.AgriIn.model.response.PageResponse;
import com.zxl.baselib.ui.base.BaseView;

/**
 * @author crazyZhangxl on 2018/10/12.
 * Describe: 百科View层
 */
public interface QuestionListView extends BaseView {

    void queryQuestionSuccess(PageResponse<QuestionListBean> pageResponse);

    void queryQuestionFailure(String error);
}
