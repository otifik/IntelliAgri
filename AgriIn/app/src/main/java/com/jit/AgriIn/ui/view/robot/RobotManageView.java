package com.jit.AgriIn.ui.view.robot;

import com.jit.AgriIn.model.response.RobotPageResponse;
import com.zxl.baselib.ui.base.BaseView;

/**
 * @author crazyZhangxl on 2018/9/28.
 * Describe: 机器人管理的V层，提供查询删除的回调
 */
public interface RobotManageView  extends BaseView {

    void queryMyRobotSuccess(RobotPageResponse robotPageResponse);

    void queryMyRobotFailure(String error);

    void deleteRobotSuccess(int itemPosition);

    void deleteRobotFailure(String error);
}
