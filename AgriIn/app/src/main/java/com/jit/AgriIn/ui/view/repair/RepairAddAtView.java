package com.jit.AgriIn.ui.view.repair;

import com.jit.AgriIn.model.response.RobotPageResponse;
import com.zxl.baselib.ui.base.BaseView;

/**
 * @author crazyZhangxl on 2018/11/8.
 * Describe:
 */
public interface RepairAddAtView extends BaseView {

    void addRepairSuccess();

    void addRepairFailure(String msg);

    void queryMyRobotSuccess(RobotPageResponse robotPageResponse);

    void queryMyRobotFailure(String error);
}
