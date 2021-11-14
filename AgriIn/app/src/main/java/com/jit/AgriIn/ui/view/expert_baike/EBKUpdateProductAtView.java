package com.jit.AgriIn.ui.view.expert_baike;

import com.jit.AgriIn.model.response.BaikeProductBean;
import com.zxl.baselib.ui.base.BaseView;

/**
 * @author crazyZhangxl on 2018/10/30.
 * Describe: 查询以及更新具体的View
 */
public interface EBKUpdateProductAtView extends BaseView {
    void  queryBaikeShowSuccess(BaikeProductBean baikeProductBean);

    void queryBaikeShowFailure(String error);

    void updateProductSuccess(BaikeProductBean baikeProductBean);

    void updateProductFailure(String error);
}
