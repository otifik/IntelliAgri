package com.jit.aquaculture.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author croton
 * @create 2021/3/31 21:04
 */
@Component
public class PropertiesReadUtils implements InitializingBean {


    public static String PUT_IMG_PATH;




    @Value("${image.put.path}")
    private String putImgPath;


    @Override
    public void afterPropertiesSet() {

        PUT_IMG_PATH = putImgPath;

    }
}
