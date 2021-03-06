package com.jit.authority.serviceimpl;

import com.jit.authority.domain.Role;
import com.jit.authority.domain.User;
import com.jit.authority.dto.JwtUser;
import com.jit.authority.mapper.RoleMapper;
import com.jit.authority.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    RoleMapper roleMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.selectOne(User.of().setUsername(username));
        if (user != null) {
            List<Role> roles = roleMapper.getRoleByUserId(user.getId());
            return new JwtUser(user.getId(), user.getUsername(), user.getPassword(), roles, user.getRegister_time());
//            return new JwtUser(user.getId(),user.getUsername(),user.getRealName(),user.getPassword(),roles,user.getLastPasswordResetDate());
        } else {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        }
    }

}
