package com.jit.usercenter.commons.util;


import java.lang.annotation.*;

/**
 * @author zhumaer
 * @desc 别名注解 用来为类的字段添加别名（备注：可重复注解，也可以为一个别名指定多个源类）
 * @since 7/6/2017 3:13 PM
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Repeatable(FieldAlias.FieldAliases.class)
public @interface FieldAlias {

    String value();

    Class<?>[] sourceClass() default {};

    /**
     * @author zhuamer
     * @desc 别名注解复数
     * @since 7/6/2017 3:13 PM
     */
    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface FieldAliases {

        FieldAlias[] value();

    }
}
