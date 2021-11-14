package com.jit.AgriIn.ui.view.user;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.zxl.baselib.ui.base.BaseView;
import com.zxl.baselib.widget.PaperButton;

/**
 * @author zxl on 2018/8/28.
 *         discription:
 */

public interface IRetrieveAtView extends BaseView {
    /**
     * @return EditText 手机号
     */
    EditText editPhone();

    /**
     *  @return EditText 验证码
     */
    EditText editCode();


    /**
     * @return imageView  手机号
     */
    ImageView iconPhone();

    /**
     * @return imageView 验证码
     */
    ImageView iconCode();


    /**
     * @return relative 手机号
     */
    RelativeLayout rlPhone();

    /**
     * @return relative 验证码
     */
    RelativeLayout rlCode();


    /**
     * @return 下一步按钮
     */
    Button buttonNext();



    /**
     * @return 倒计时
     */
    PaperButton paperButton();
}
