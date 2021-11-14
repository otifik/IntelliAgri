package com.jit.AgriIn.ui.view.expert_baike;

import com.jit.AgriIn.model.response.BaikeDiseaseBean;
import com.zxl.baselib.ui.base.BaseView;

/**
 * @author crazyZhangxl on 2018/10/30.
 * Describe: 查询以及更新具体的View
 */
public interface EBKUpdateDiseaseAtView extends BaseView {
    void  queryBaikeShowSuccess(BaikeDiseaseBean baikeDiseaseBean);

    void queryBaikeShowFailure(String error);

    void updateDisSuccess(BaikeDiseaseBean baikeDiseaseBean);

    void updateDisFailure(String error);
}
