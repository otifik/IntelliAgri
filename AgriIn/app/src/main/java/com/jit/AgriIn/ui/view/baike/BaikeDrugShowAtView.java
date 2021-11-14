package com.jit.AgriIn.ui.view.baike;

import com.jit.AgriIn.model.response.BaikeDrugBean;
import com.zxl.baselib.ui.base.BaseView;

/**
 * @author crazyZhangxl on 2018/10/15.
 * Describe:
 */
public interface BaikeDrugShowAtView extends BaseView {

    void  queryBaikeShowSuccess(BaikeDrugBean baikeSeedResponse);

    void queryBaikeShowFailure(String error);
}
