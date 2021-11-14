package com.jit.AgriIn.ui.view.baike;

import com.jit.AgriIn.model.response.BaikeDiseaseBean;
import com.zxl.baselib.ui.base.BaseView;

/**
 * @author crazyZhangxl on 2018/10/15.
 * Describe:
 */
public interface BaikeDiseaseShowAtView extends BaseView {

    void  queryBaikeShowSuccess(BaikeDiseaseBean baikeSeedResponse);

    void queryBaikeShowFailure(String error);
}
