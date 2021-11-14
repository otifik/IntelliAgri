package com.jit.AgriIn.ui.view.expert_baike;

import com.jit.AgriIn.model.response.BaikeFeedBean;
import com.zxl.baselib.ui.base.BaseView;

/**
 * @author crazyZhangxl on 2018/10/30.
 * Describe:
 */
public interface EBKUpdateFeedAtView extends BaseView {
    void  queryBaikeShowSuccess(BaikeFeedBean baikeFeedBean);

    void queryBaikeShowFailure(String error);

    void updateFeedSuccess(BaikeFeedBean baikeFeedBean);

    void updateFeedFailure(String error);
}
