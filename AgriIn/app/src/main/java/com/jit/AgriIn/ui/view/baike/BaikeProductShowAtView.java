package com.jit.AgriIn.ui.view.baike;

import com.jit.AgriIn.model.response.BaikeProductBean;
import com.zxl.baselib.ui.base.BaseView;

/**
 * @author crazyZhangxl on 2018/10/15.
 * Describe:
 */
public interface BaikeProductShowAtView extends BaseView {

    void  queryBaikeShowSuccess(BaikeProductBean baikeSeedResponse);

    void queryBaikeShowFailure(String error);
}
