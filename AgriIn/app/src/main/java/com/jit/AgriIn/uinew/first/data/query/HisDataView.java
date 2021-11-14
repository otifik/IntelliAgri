package com.jit.AgriIn.uinew.first.data.query;

import com.jit.AgriIn.model.response.HisResponse;
import com.jit.AgriIn.model.response.OrgResponse;
import com.zxl.baselib.ui.base.BaseView;

import java.util.List;

/**
 * @author crazyZhangxl on 2018/9/27.
 * Describe: 获得所有塘口信息的View层
 */
public interface HisDataView extends BaseView {
    void getHisDataSuccess(List<HisResponse> hisResponseList);
    void getHisDataFailure(String error);

    void getOrgDataSuccess(List<OrgResponse> orgResponseList);
    void getOrgDataFailure(String error);
}
