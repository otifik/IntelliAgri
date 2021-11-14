package com.jit.authority.serviceinterface;


import com.jit.authority.dto.DriverInfo;
import com.jit.authority.dto.RegisterDto;
import com.jit.authority.dto.UserDto;

public interface AuthService {
    RegisterDto register(RegisterDto userToAdd);

    UserDto login(String username, String password);

    DriverInfo weChatLogin(String tel, String role);

    String refresh(String oldToken);

    UserDto wxLogin(String code);
}
