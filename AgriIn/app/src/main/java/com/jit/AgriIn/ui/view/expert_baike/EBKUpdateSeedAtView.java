package com.jit.AgriIn.ui.view.expert_baike;

import com.jit.AgriIn.model.response.BaikeSeedBean;
import com.zxl.baselib.ui.base.BaseView;

/**
 * @author crazyZhangxl on 2018/10/30.
 * Describe: 查询以及更新具体的View
 */
public interface EBKUpdateSeedAtView extends BaseView {

    void  queryBaikeShowSuccess(BaikeSeedBean baikeSeedBean);

    void queryBaikeShowFailure(String error);

    void updateSeedSuccess(BaikeSeedBean baikeSeedBean);

    void updateSeedFailure(String error);
}
