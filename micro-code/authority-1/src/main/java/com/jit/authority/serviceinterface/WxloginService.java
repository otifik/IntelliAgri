package com.jit.authority.serviceinterface;

import com.jit.authority.responseResult.result.WebProResult;
import com.jit.authority.serviceimpl.WxloginServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "user",fallback = WxloginServiceImpl.class)
public interface WxloginService {
    @PostMapping("/users/userempower/wxlogin")
    WebProResult<String> loginService(@RequestParam("code") String code);
}
