package com.jit.AgriIn.ui.view.expert_baike;

import com.jit.AgriIn.model.response.BaikeDrugBean;
import com.zxl.baselib.ui.base.BaseView;

/**
 * @author crazyZhangxl on 2018/10/30.
 * Describe: 查询以及更新具体的View
 */
public interface EBKUpdateDrugAtView extends BaseView {
    void  queryBaikeShowSuccess(BaikeDrugBean baikeDrugBean);

    void queryBaikeShowFailure(String error);

    void updateDrugSuccess(BaikeDrugBean baikeDrugBean);

    void updateDrugFailure(String error);
}
