package com.jit.AgriIn.ui.view.baike;

import com.jit.AgriIn.model.response.BaikeFeedBean;
import com.zxl.baselib.ui.base.BaseView;

/**
 * @author crazyZhangxl on 2018/10/15.
 * Describe:
 */
public interface BaikeFeedShowAtView extends BaseView {

    void  queryBaikeShowSuccess(BaikeFeedBean baikeSeedResponse);

    void queryBaikeShowFailure(String error);
}
