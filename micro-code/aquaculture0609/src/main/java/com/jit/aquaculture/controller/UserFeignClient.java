//package edu.cn.microvehicles.feigns;
package com.jit.aquaculture.controller;
import com.jit.aquaculture.domain.User;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-center",fallbackFactory = UserFeignClientFallbackFactory.class)
public interface UserFeignClient {

    @RequestMapping(value = "/one/tel", method = RequestMethod.GET)
    User getOneUserbyTel(@RequestParam("tel") String tel);


}

@Component
@Slf4j
class UserFeignClientFallbackFactory implements FallbackFactory<UserFeignClient>{
   public static final Logger logger = LoggerFactory.getLogger(UserFeignClientFallbackFactory.class);

    @Override
    public UserFeignClient create(Throwable throwable) {
        return new UserFeignClient() {

            @Override
            public User getOneUserbyTel(String tel) {
                logger.error("进入回退",throwable);
                User user = User.of();
                user.setId(0);
                user.setBelongUserId(0);
                return user;
            }

        };
    }
}
