package com.jit.AgriIn.ui.view.user;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.zxl.baselib.ui.base.BaseView;

/**
 * @author zxl on 2018/8/28.
 *         discription: 重置密码的View层 ---
 */

public interface IRestPwdAtView extends BaseView {
    /**
     * @return EditText 密码
     */
    EditText editPwd();

    /**
     * @return EditText确认密码
     */
    EditText editCfPwd();


    /**
     * @return imageView 密码
     */
    ImageView iconPwd();

    /**
     * @return imageView 验证密码
     */
    ImageView iconCfPwd();


    /**
     * @return relative  密码
     */
    RelativeLayout rlPwd();

    /**
     * @return relative 验证密码
     */
    RelativeLayout rlCfPwd();

    /**
     * @return 确定修改按钮
     */
    Button buttonEnsure();
}
