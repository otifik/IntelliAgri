package com.jit.AgriIn.ui.view.baike;

import com.jit.AgriIn.model.response.BaikeSeedBean;
import com.zxl.baselib.ui.base.BaseView;

/**
 * @author crazyZhangxl on 2018/10/15.
 * Describe:
 */
public interface BaikeShowAtView extends BaseView {

    void  queryBaikeShowSuccess(BaikeSeedBean baikeSeedBean);

    void queryBaikeShowFailure(String error);
}
