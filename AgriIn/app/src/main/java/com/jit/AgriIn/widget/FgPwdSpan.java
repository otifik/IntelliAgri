package com.jit.AgriIn.widget;

import android.text.TextPaint;
import android.text.style.CharacterStyle;

/**
 * @author zxl on 2018/8/24.
 *         discription:
 */

public class FgPwdSpan extends CharacterStyle {
    @Override
    public void updateDrawState(TextPaint tp) {
        tp.setUnderlineText(true);
        tp.clearShadowLayer();
    }
}
