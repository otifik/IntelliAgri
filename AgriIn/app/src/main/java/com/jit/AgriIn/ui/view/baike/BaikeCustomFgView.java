package com.jit.AgriIn.ui.view.baike;

import com.jit.AgriIn.model.response.BaikeSeedBean;
import com.jit.AgriIn.model.response.PageResponse;
import com.zxl.baselib.ui.base.BaseView;

/**
 * @author crazyZhangxl on 2018/10/12.
 * Describe: 百科View层
 */
public interface BaikeCustomFgView extends BaseView {

    void queryBaikeSuccess(PageResponse<BaikeSeedBean> pageResponse);

    void queryBaikeFailure(String error);

    void deleteBaikeSuccess(int itemIndex);

    void deleteBaikeFailure(String error);
}
