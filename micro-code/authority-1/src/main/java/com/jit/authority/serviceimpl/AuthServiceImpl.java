package com.jit.authority.serviceimpl;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.jit.authority.dto.DriverInfo;
import com.jit.authority.mapper.RoleMapper;
import com.jit.authority.mapper.UserMapper;
import com.jit.authority.mapper.UserRoleMapper;
import com.jit.authority.responseResult.ResultCode;
import com.jit.authority.domain.User;
import com.jit.authority.domain.UserRole;
import com.jit.authority.dto.JwtUser;
import com.jit.authority.dto.RegisterDto;
import com.jit.authority.dto.UserDto;
import com.jit.authority.jwtsecurity.bean.JwtProperty;
import com.jit.authority.jwtsecurity.util.CustomPasswordEncoder;

import com.jit.authority.responseResult.exceptions.BusinessException;
import com.jit.authority.responseResult.result.WebProResult;
import com.jit.authority.serviceinterface.AuthService;
import com.jit.authority.serviceinterface.WxloginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;
import java.util.List;

import static com.jit.authority.jwtsecurity.util.JwtUtil.createToken;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    //    private String tokenHead="Bearer ";
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    UserDetailServiceImpl userDetailService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    UserRoleMapper userRoleMapper;

    @Autowired
    RoleMapper roleMapper;
    @Autowired
    private JwtProperty property;

    @Autowired
    WxloginService wxloginService;
    /**
     * 用户注册
     *
     * @param user 用户信息
     * @return
     */
    @Transactional
    @Override
    public RegisterDto register(RegisterDto user) {
        //默认角色为普通用户，即养殖户
        if (null == user.getRole() || user.getRole().trim().equals("")) {
            user.setRole("ROLE_USER");//
        }
        //用户名为空
        if (user.getUsername().trim().isEmpty()) {
            throw new BusinessException(ResultCode.DATA_IS_NULL);
        }
        //用户名已存在
        List<User> users = userMapper.selectList(new EntityWrapper<User>().eq("username", user.getUsername()));
        if (null != users && users.size() > 0) {
            throw new BusinessException(ResultCode.USER_ISEXITE);
        }
        //用户已存在
        List<User> users_ = userMapper.selectList(new EntityWrapper<User>().eq("tel", user.getTel()));
        if (null != users_ && users_.size() > 0) {
            throw new BusinessException(ResultCode.USER_TEL_ISEXITE);
        }
        //注册密码加密
        String passWord = user.getPassword();
        PasswordEncoder encoder = new CustomPasswordEncoder();
        user.setPassword(encoder.encode(passWord));
        User userDO = User.of();
        BeanUtils.copyProperties(user, userDO);
        userDO.setRegister_time(new Date());
        userMapper.insert(userDO);
        log.info("user register:=== {}", userDO);
        Integer roleId = roleMapper.getRoleId(user.getRole());
        if (roleId == null) {
            throw new BusinessException(ResultCode.ROLE_NOT_EXIST);
        }
        if (null != userDO.getId()) {
            userRoleMapper.insert(UserRole.of().setRoleId(roleId).setUserId(userDO.getId()));
        } else {
            throw new BusinessException(ResultCode.INTERFACE_INNER_INVOKE_ERROR);
        }
        return user;
    }

    @Override
    public UserDto login(String username, String password) {
        CustomPasswordEncoder encoder = new CustomPasswordEncoder();

        User userDO = userMapper.findByUsernameOrTel(username);
        if (userDO == null) {
            throw new BusinessException(ResultCode.RESULE_DATA_NONE);
        }
        String username_ = userDO.getUsername();

        if (!encoder.matches(password, userDO.getPassword())) {
            throw new BusinessException(ResultCode.USER_LOGIN_ERROR);
        }
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username_, password);
        Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        JwtUser userDetails = (JwtUser) authentication.getPrincipal();

        final String token = createToken(username_, property);
        final String token_ = "Bearer " + token;
        UserDto userDto = UserDto.of();

        BeanUtils.copyProperties(userDetails, userDto);

        userDto.setToken(token_)
                .setRole(userDetails.getRoles().get(0).getName())
                .setRoleId(userDetails.getRoles().get(0).getId())
                .setRegisterTime(userDO.getRegister_time())
                .setLastPasswordResetDate(userDO.getRegister_time())
                .setLoginTime(new Date())
                .setTel(userDO.getTel());
        return userDto;
    }


    @Override
    public DriverInfo weChatLogin(String tel, String role) {
        CustomPasswordEncoder encoder = new CustomPasswordEncoder();

        User userDO = userMapper.findByTelAndRole(tel,role);
        if (userDO == null) {
            throw new BusinessException(ResultCode.RESULE_DATA_NONE);
        }

        String username_ = userDO.getUsername();
        String password="123456";
        if (!encoder.matches(password, userDO.getPassword())) {
            throw new BusinessException(ResultCode.USER_LOGIN_ERROR);
        }

        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username_, password);
        Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        JwtUser userDetails = (JwtUser) authentication.getPrincipal();

        final String token = createToken(username_, property);
        final String token_ = "Bearer " + token;
        DriverInfo driverInfo = new DriverInfo();
        BeanUtils.copyProperties(userDO, driverInfo);

        driverInfo.setToken(token_);

        return driverInfo;
    }

    @Override
    public String refresh(String oldToken) {

        return null;
    }

    @Override
    public UserDto wxLogin(String code) {
        WebProResult<String> result = wxloginService.loginService(code);
        if(result.getCode() != 200 || result.getData() == null)
        {
            throw new BusinessException(ResultCode.INTERFACE_OUTTER_INVOKE_ERROR);
        }
        String openId = result.getData();
        User userDO = userMapper.findByUsernameOrTel(openId);
        if (userDO == null)
        {
            RegisterDto registerDto = new RegisterDto();
            registerDto.setUsername(openId);
            registerDto.setPassword(code);
            register(registerDto);
        }
        else
        {
            PasswordEncoder encoder = new CustomPasswordEncoder();
            userMapper.updatePassword(encoder.encode(code),openId);
        }
        return login(openId,code);
    }
}
