package com.jit.authority.serviceimpl;

import com.jit.authority.responseResult.result.WebProResult;
import com.jit.authority.serviceinterface.WxloginService;
import org.springframework.stereotype.Component;

@Component
public class WxloginServiceImpl implements WxloginService {
    @Override
    public WebProResult<String> loginService(String username) {
        return WebProResult.<String>builder()
                .code(201)
                .data("#")
                .message("断路")
                .build();
    }
}
